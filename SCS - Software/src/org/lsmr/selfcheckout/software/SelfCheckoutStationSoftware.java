package org.lsmr.selfcheckout.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.software.ReceiptPrint;
import org.lsmr.selfcheckout.software.gui.SelfCheckoutSystemSoftwareGUI;
import org.lsmr.selfcheckout.software.Data;

public class SelfCheckoutStationSoftware extends AbstractDevice<SelfCheckoutSystemSoftwareObserver> {
	public Data d = Data.getInstance();

	public enum SCSStatus {
		GOOD,
		WARNING,
		OFF
	}

	public BigDecimal totalGUI;
	public SelfCheckoutStation scs;
	public TestDatabase db; // in an actual system this would connect to a db or something
	public ElectronicScaleSoftware ess;
	public BarcodeScannerSoftware bss;
	public BanknoteSlotSoftware banknoteSlotSoftware;
	public CoinSlotSoftware coinSlotSoftware;
	public ScanMembershipCard memberCardObserver;
	public TouchScreenSoftware touchSnObserver;
	public CardSoftware cardSoftware;
	public ScanAndBag scanAndBag;
	public AvailableFunds funds;
	public SelfCheckoutSystemSoftwareGUI softwareGUI;

	public ReceiptPrint rp; // added receipt print
	protected AttendantStation as; // added attendant stationæ
	protected AttendantActions at; // added attendant actions
	private String memberNumber;
	public MembersDatabase membersRecord;
	public int stationNumber;
	
	public Boolean isShutdown = true;
	public Boolean isBlocked = true;
	public SCSStatus status = SCSStatus.GOOD;

	// self checkout station software
	// NOTE: Any objects that are not primitive types are passed to other classes by
	// reference.
	// Thus, passing the following vars to the other classes will give us an updated
	// vars.
	public ArrayList<ItemInfo> itemsScanned;
	public final double weightThreshold = 10;
	public BigDecimal amountDue;
	private BigDecimal[] amountPaid;
	public int bagsUsed;
	public int maximumBags;
	public BigDecimal priceOfBags;
	public ReturnChangeSoftware returnChangeSoftware;
	public BigDecimal amountReturned;

	public SelfCheckoutStationSoftware(SelfCheckoutStation scs) throws Exception {
		this.itemsScanned = new ArrayList<ItemInfo>();
		this.amountDue = BigDecimal.ZERO;
		this.amountPaid = new BigDecimal[1];
		this.amountPaid[0] = BigDecimal.ZERO;
		this.bagsUsed = 0;
		this.maximumBags = 10;
		this.priceOfBags = new BigDecimal("0.05");

		this.scs = scs;
		this.rp = new ReceiptPrint(scs);
		this.as = new AttendantStation();
		this.db = new TestDatabase();
		this.ess = new ElectronicScaleSoftware(scs);
		this.bss = new BarcodeScannerSoftware(db, ess, itemsScanned, weightThreshold);
		this.banknoteSlotSoftware = new BanknoteSlotSoftware(this);
		this.membersRecord = new MembersDatabase();
		this.memberCardObserver = new ScanMembershipCard(this);
		this.cardSoftware = new CardSoftware(this);
		this.funds = new AvailableFunds(scs);
		this.scanAndBag = new ScanAndBag(scs, db, this);

		// This Touch Screen Observer is meant for a SelfCheckoutStation.
		// There is another constructor that uses an Attendant Station.
		this.touchSnObserver = new TouchScreenSoftware(this.scs);

		this.coinSlotSoftware = new CoinSlotSoftware(this);
		this.returnChangeSoftware = new ReturnChangeSoftware(scs);

		// attach the ess and bss to the selfcheckout hardware
		this.scs.mainScanner.attach(bss);
		this.scs.baggingArea.attach(ess);
		this.scs.banknoteValidator.attach(banknoteSlotSoftware);
		this.scs.coinValidator.attach(coinSlotSoftware);
		this.scs.cardReader.attach(memberCardObserver);
		this.scs.screen.attach(touchSnObserver);

		// Connect software to attendant station
		AttendantStation aStation = new AttendantStation();

		// Assign the station a number
		this.stationNumber = aStation.assignStationNumber();
		
		// Connect the software to the attendant station
		aStation.connectToAttendantStation(scs,this, ess, bss, banknoteSlotSoftware, scanAndBag);
		// create new change class
	}

	public void startScanGUI() {
		scanAndBag.startGUI();
	}
	
	public void continueScanGUI() {
		scanAndBag.continueGUI();
	}
	
	public BigDecimal total() {
		BigDecimal total = BigDecimal.ZERO;
		itemsScanned = scanAndBag.getItemsScanned();
		for (ItemInfo i : itemsScanned) {
			total = total.add(i.price);
		}
		ArrayList<BarcodedProduct> bcItems = d.getBarcodeScanned();
		for (BarcodedProduct p : bcItems){
			total = total.add(p.getPrice());
		}

		ArrayList<PLUCodedProduct> pluItems = d.getPluScanned();
		for (PLUCodedProduct p : pluItems){
			total = total.add(p.getPrice());
		}

		total = total.add(priceOfBags.multiply(new BigDecimal(Integer.toString(bagsUsed))));
		setTotalGUI(total);
		return total;
	}

	public BigDecimal getAmountReturned() {
		return amountReturned;
	}
	
	public BigDecimal getAmountLeftToPay() {
		return amountDue.subtract(amountPaid[0]);
	}
	
	public void addAmountPaid(BigDecimal amount) {
		amountPaid[0] = amountPaid[0].add(amount);
		notifyAmountPaid(amount);
	}
	
	public BigDecimal getAmountPaid() {
		return amountPaid[0];
	}
	
	public int getPercentageInkLeft() {
		return rp.noInk ? 0 : 100; // Receipt printer doesn't give an actual count and the ReceiptPrinter class doesn't keep track automatically
	}
	
	public int getPercentagePaperLeft() {
		return rp.noPaper ? 0 : 100;
	}

	/**
	 * Purpose: return the correct amount of change to the user, giving them the
	 * highest denominations first.
	 * 
	 * @param totalChange is the amount of change that should be provided to the
	 *                    user.
	 * @return void
	 */
	public void returnChange(BigDecimal totalChange) throws OverloadException, EmptyException, DisabledException {
		// create a local variable to be updated whenever a coin/banknote is emitted.
		BigDecimal amountToBeReturned = totalChange;
		amountReturned = BigDecimal.ZERO;
		// dispense coin/banknote while there is still change left to give to the
		// customer
		// Case 1: change to be returned is greater than a toonie - emit banknotes.
		while (amountToBeReturned.compareTo(BigDecimal.valueOf(5)) > 0) {
			int counter = returnChangeSoftware.getBanknoteDenomination().size() - 1;
			while (counter > -1) {
				// store the banknote in a variable.
				int removed = returnChangeSoftware.getBanknoteDenomination().get(counter);
				// check to see if the amount to be returned is greater than or equal to the
				// denomination
				// because if it is not then we cannot give that note to the customer.
				if (amountToBeReturned.compareTo(BigDecimal.valueOf(removed)) >= 0) {
					// get the dispenser that holds the denomination we need and emit the banknote.
					this.scs.banknoteDispensers.get(removed).emit();
					// remove the banknote so we can release another one.
					this.scs.banknoteOutput.removeDanglingBanknotes();
					// update amountToBeReturned
					amountToBeReturned = amountToBeReturned.subtract(BigDecimal.valueOf(removed));
					amountReturned = amountReturned.add(BigDecimal.valueOf(removed));
					// break to start again at the highest denomination.
					break;
				}
				counter--;
			}
		}
		// dispense coin/banknote while there is still change left to give to the
		// customer
		// Case 2: change to be returned is equal to or less than a $5 - emit coins.
		while (amountToBeReturned.compareTo(BigDecimal.valueOf(5)) < 0) {
			if (amountToBeReturned.compareTo(BigDecimal.valueOf(0)) != 0) {
				int counter = this.scs.coinDenominations.size() - 1;
				while (counter > -1) {
					// store the coin in a variable
					BigDecimal removed = this.scs.coinDenominations.get(counter);
					// check to see if the amount to be returned is greater than or equal to the
					// denomination
					// because if it is not then we cannot give that coin to the customer.
					if (amountToBeReturned.compareTo(removed) >= 0) {
						// get the dispenser that holds the denomination we need and emit the banknote.
						this.scs.coinDispensers.get(removed).emit();
						// update amountToBeReturned
						amountToBeReturned = amountToBeReturned.subtract(removed);
						amountReturned = amountReturned.add(removed);
						// break to start again at the highest denomination.
						break;
					}
					// update amountToBeReturned based on the nearest $0.05
					if (amountToBeReturned.compareTo(BigDecimal.valueOf(0.02)) <= 0) {
						amountToBeReturned = BigDecimal.ZERO;
					} else if (amountToBeReturned.compareTo(BigDecimal.valueOf(0.05)) < 0) {
						amountToBeReturned = BigDecimal.valueOf(0.05);
					}
					counter--;
				}

			} else {
				break;
			}
		}

		// check to see if amountLeft is equal to 0, if it is, the correct change has
		// been dispensed and notify the user.
		if (amountToBeReturned.compareTo(BigDecimal.ZERO) == 0) {
			System.out.println("Please take your change: $" + amountReturned);
		}
	}

	public void detectLowInkPaper(int inkNeeded, int paperNeeded) throws InterruptedException {

		if (inkNeeded == 0) {
			rp.detectLowPaper(paperNeeded);
		} else if (paperNeeded == 0) {
			rp.detectLowInk(inkNeeded);
		} else {
			rp.detectLowInk(inkNeeded);
			rp.detectLowPaper(paperNeeded);
		}
	}

	private void print(BigDecimal total) throws EmptyException, OverloadException, InterruptedException {

		ReceiptPrint ReceiptPrint = new ReceiptPrint(scs);

		
		int paper = ReceiptPrint.paperAmount;
		int ink = ReceiptPrint.inkAmount;
		System.out.println(ink);
		System.out.println(paper);

		// implementation for replacing ink and paper with new rolls/cartridges
		int widthOfReceipt = 60;
		int spaceBetweenPriceAndDesc = 3;

		String header = String.format("%32s\n%s\n%-4s%56s\n%-4s%56s", "START OF THE RECEIPT",
				"------------------------------------------------------------",
				"Item", "Price", "----", "----\n");

		detectLowInkPaper(header.toCharArray().length, 1);
		for (char c : header.toCharArray()) {
			scs.printer.print(c);
			ink--;
			paper--;

		}
		System.out.println(header);

		if(this.memberNumber == null) {
			memberNumber = "0";
		}
		
		detectLowInkPaper(0, itemsScanned.size());
		for (ItemInfo i : itemsScanned) {
			// creates 60 white spaced string
			String receiptLine = "";

			int descSpaceLength = widthOfReceipt - (i.price.toString().length() + spaceBetweenPriceAndDesc);

			String description = "";
			if (i.description.length() > descSpaceLength) {
				description = i.description.substring(0, descSpaceLength);
				String whitespace = new String(new char[spaceBetweenPriceAndDesc]).replace("\0", " ");
				receiptLine = description.substring(0, description.length()) + whitespace + i.price.toString() + "\n";

				detectLowInkPaper(receiptLine.toCharArray().length, 1);
				for (char c : receiptLine.toCharArray()) {
					scs.printer.print(c);
					ink--;
					paper--;
				}
				System.out.print(receiptLine);
			} else {
				int whitespaceLength = widthOfReceipt - i.description.length() - i.price.toString().length()
						- ("\n".length());
				String whitespace = new String(new char[whitespaceLength]).replace("\0", " ");
				receiptLine = i.description + whitespace + i.price.toString() + '\n';

				detectLowInkPaper(receiptLine.toCharArray().length, 1);
				for (char c : receiptLine.toCharArray()) {
					scs.printer.print(c);
					ink--;
					paper--;
				}
				System.out.print(receiptLine);
			}
		}

		String totalLine = "Total: " + total;
		detectLowInkPaper(totalLine.toCharArray().length, 1);
		for (char c : totalLine.toCharArray()) {
			scs.printer.print(c);
			ink--;
			paper--;
		}
		System.out.println(totalLine);

		String cashLine = "Cash: " + this.amountPaid[0];
		detectLowInkPaper(cashLine.toCharArray().length, 1);
		for (char c : cashLine.toCharArray()) {
			scs.printer.print(c);
			ink--;
			paper--;
		}
		System.out.println(cashLine);

		String changeLine = "Change: " + getAmountReturned();
		detectLowInkPaper(changeLine.toCharArray().length, 1);
		for (char c : changeLine.toCharArray()) {
			scs.printer.print(c);
			ink--;
			paper--;
		}
		System.out.println(changeLine);

		// Member Card Number portion for the receipt.
		String memberHeader = "Member Number:";
		detectLowInkPaper(memberHeader.toCharArray().length, 1);
		for (char c : memberHeader.toCharArray()) {
		scs.printer.print(c);
		ink--;
		paper--;
		}
		System.out.println(memberHeader);

		// memberNumber would have been set by either swipping the
		// member card or entering manually through Touchscreen.
		detectLowInkPaper(memberNumber.toCharArray().length, 1);
		for(char c : memberNumber.toCharArray()) {
		scs.printer.print(c);
		ink--;
		paper--;
		}
		System.out.println(memberNumber);

		// So we know on the command line when it ends.
		String footerReceipt = String.format("%32s\n%s", "END OF THE RECEIPT",
				"------------------------------------------------------------");
		System.out.println(footerReceipt);
		System.out.println(ink);
		System.out.println(paper);
		scs.printer.cutPaper();
	}

	public void checkout() throws EmptyException, OverloadException, InterruptedException {
		//promptForBags();

		BigDecimal total = total();
		BigDecimal totalChange = this.amountPaid[0].subtract(total);
		if (total.compareTo(this.amountPaid[0]) <= 0) {
			try {
				returnChange(totalChange);
			} catch (OverloadException | EmptyException | DisabledException e) {
				e.printStackTrace();
			}
			System.out.println("Amount paid is greater than total. Printing receipt");
			rp.detectLowInk(rp.getInkAmount());
			rp.detectLowPaper(rp.getPaperAmount());
			print(total);

			resetVars();
		} else {
			System.out.println("Insufficient funds to complete checkout!");
		}
	}

	public boolean checkBaggingAreaAll() throws OverloadException {
		double expectedWeight = 0;
		double sensitivity = this.scs.baggingArea.getSensitivity();
		double currentWeight = this.scs.baggingArea.getCurrentWeight();

		System.out.println("Weights: ");
		for (ItemInfo i : itemsScanned) {
			expectedWeight = Double.sum(i.weight, expectedWeight);
			System.out.println(i.weight);
		}

		System.out.println("Expected weight:");
		System.out.println(expectedWeight);
		System.out.println(currentWeight);

		// If expected weight is not within sensitivity threshold of actual weight
		if (currentWeight > expectedWeight + sensitivity || currentWeight < expectedWeight - sensitivity) {
			System.out.println("Please place all items in the bagging area");
			// If bagging area is not as it should be return false
			return false;
		}
		return true;

	}

	public boolean checkBaggingAreaItem(ItemInfo item) throws OverloadException {
		double itemWeight = item.weight;
		double sensitivity = this.scs.baggingArea.getSensitivity();
		double currentWeight = this.scs.baggingArea.getCurrentWeight();
		double previousWeight = this.ess.getWeightAtLastEvent();

		boolean existsInScannedList = false;

		for (ItemInfo i : itemsScanned) {
			if (item.weight == i.weight) {
				existsInScannedList = true;
				System.out.format("%f == %f", i.weight, item.weight);
			}
		}

		System.out.println("Previous weight:");
		System.out.println(previousWeight);
		System.out.println(currentWeight);
		System.out.println(itemWeight);
		System.out.println(existsInScannedList);

		// Item isn't in scanned list and we should therefore return true indicating
		// bagging area is fine
		if (existsInScannedList == false)
			return true;
		// If expected weight is not within sensitivity threshold of actual weight
		if (previousWeight + itemWeight > currentWeight + sensitivity
				|| previousWeight + itemWeight < currentWeight - sensitivity) {
			System.out.println("Please place last item in the bagging area");
			// If bagging area is not as it should be return false
			return false;
		}
		return true;
	}

	public void resetVars() {
		
		this.itemsScanned = new ArrayList<ItemInfo>();
		this.amountDue = BigDecimal.ZERO;
		this.amountPaid = new BigDecimal[1];
		this.amountPaid[0] = BigDecimal.ZERO;
		this.bagsUsed = 0;
		this.maximumBags = 10;
		this.priceOfBags = new BigDecimal("0.05");
		System.out.println(amountPaid[0]);
	}

	public void startUpGUI() {
		// does nothing for now
	}

	public String getMemberCardNumber() {
		return this.memberNumber;
	}

	public void setMemberCardNumber(String memberCN) {
		this.memberNumber = memberCN;
		notifyMembershipCardScanned(memberCN);
	}

	public BigDecimal getTotalGUI() {
		return totalGUI;
	}

	public void setTotalGUI(BigDecimal totalGUI) {
		this.totalGUI = totalGUI;
	}
	
	public void startUp() {
		funds.attachAll();
		isShutdown = false;
		isBlocked = true;
		status = SCSStatus.GOOD;
		
		this.scanAndBag.startGUI();
		//softwareGUI = new SelfCheckoutSystemSoftwareGUI(scs, this);
		//softwareGUI.setVisible(true);
	}
	
	public void shutDown() {
		funds.detachAll();
		isShutdown = true;
		status = SCSStatus.OFF;
		
		if(softwareGUI != null)
			softwareGUI.setVisible(false);
	}
	
	private void notifyAmountPaid(BigDecimal amount) {
		for(SelfCheckoutSystemSoftwareObserver l: observers)
			l.amountPaid(this, amount);
	}
	
	private void notifyMembershipCardScanned(String memberCN) {
		for(SelfCheckoutSystemSoftwareObserver l: observers)
			l.membershipCardScanned(this, memberCN);
	}

}
