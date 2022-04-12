package org.lsmr.selfcheckout.software.gui;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JFrame;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.SelfCheckoutSystemSoftwareObserver;

/**
 * Presents the GUI frame for choosing payment options, swiping membership card,
 * and checking total due/paid.
 */
public class PaymentGUI implements SelfCheckoutSystemSoftwareObserver {
	private static final String PAY_CARD_TEXT = "Please scan, swipe, or insert card.";
	private static final String PAY_CASH_TEXT = "Please insert coins and dollars.";
	private static final String MEMBERSHIP_TEXT = "Please swipe membership card.";

	// Move out
	static int[] bnDenoms = new int[] { 5, 10, 20, 50, 100 };
	static BigDecimal[] coinDenoms = new BigDecimal[] { new BigDecimal("0.05"), new BigDecimal("0.10"),
			new BigDecimal("0.25"), new BigDecimal("1.00"), new BigDecimal("2.00") };
	static Currency currency = Currency.getInstance(Locale.CANADA);

	public enum PaymentGUIState {
		SELECTION, CASH, DEBIT, CREDIT, GIFT, MEMBERSHIP
	}

	CardLayout cardLayout;

	private PaymentGUIState state = PaymentGUIState.SELECTION;
	public Boolean membershipCardScanned = false;

	private SelfCheckoutStationSoftware software;
	private JFrame frame;
	PaymentTesterGUI paymentTester;

	/**
	 * Creates new form PaymentGUI
	 */
	public PaymentGUI(SelfCheckoutStationSoftware software) {
		this.software = software;
		software.attach(this);
		frame = software.scs.screen.getFrame();

		initComponents();

		// Get the card layout so we can quickly swap cards
		cardLayout = (CardLayout) (jPanelPaymentTop.getLayout());

		jTextAreaInfo.setWrapStyleWord(true);
		jTextAreaInfo.setLineWrap(true);

		initTesting();
		updatePaymentLabels();

		frame.setLocation(1000, 300);
		frame.setVisible(true);
	}

	private void initTesting() {
		// Testing
		software.amountDue = software.total();
		software.cardSoftware.paymentAmount = new BigDecimal("100.0");
		software.cardSoftware.cardIssuer = new CardIssuer("Test Financing");
		paymentTester = new PaymentTesterGUI(software);
		paymentTester.setVisible(true);
		paymentTester.setLocation(400, 300);
	}

	private void finishPayment() {
		if (software.getAmountPaid().compareTo(software.amountDue) >= 0) {
			System.out.println("Done!");
			frame.dispose();
			paymentTester.dispose();
			try {
				software.returnChange(software.getAmountPaid().subtract(software.amountDue));
			} catch (OverloadException | EmptyException | DisabledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PaymentCompleteGUI complete = new PaymentCompleteGUI(software);
			complete.setVisible(true);
		}
	}

	public PaymentGUIState getState() {
		return state;
	}

	public void reset() {
		frame.setVisible(true);
		updatePaymentLabels();
	}

	public void updatePaymentLabels() {
		jLabelAmountDue.setText("Amount Due: $" + software.amountDue);
		jLabelAmountPaid.setText("Amount Paid: $" + software.getAmountPaid());
	}

	private void switchToPaymentPanel() {
		// Change help text depending on payment method chosen
		String s;
		if (state == PaymentGUIState.CREDIT || state == PaymentGUIState.DEBIT || state == PaymentGUIState.GIFT)
			s = PAY_CARD_TEXT;
		else if (state == PaymentGUIState.CASH)
			s = PAY_CASH_TEXT;
		else
			s = MEMBERSHIP_TEXT;

		jTextAreaInfo.setText(s);

		cardLayout.last(jPanelPaymentTop);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanelPayment = new javax.swing.JPanel();
		jPanelPaymentTop = new javax.swing.JPanel();
		jPanelPaymentSelection = new javax.swing.JPanel();
		jPanelPaymentMethods = new javax.swing.JPanel();
		jButtonPayGiftcard = new javax.swing.JButton();
		jButtonPayDebit = new javax.swing.JButton();
		jButtonPayCash = new javax.swing.JButton();
		jButtonPayCredit = new javax.swing.JButton();
		jLabel3 = new javax.swing.JLabel();
		jButtonScanMembershipCard = new javax.swing.JButton();
		jButtonEnterMembershipNumber = new javax.swing.JButton();
		jButtonPaymentGoBack = new javax.swing.JButton();
		jPanelPaymentInfoScreen = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextAreaInfo = new javax.swing.JTextArea();
		jButtonInfoScreenBack = new javax.swing.JButton();
		jPanelPaymentInfo = new javax.swing.JPanel();
		jLabelAmountDue = new javax.swing.JLabel();
		jLabelAmountPaid = new javax.swing.JLabel();

		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanelPaymentTop.setLayout(new java.awt.CardLayout());

		jPanelPaymentMethods.setLayout(new java.awt.GridLayout(2, 2, 64, 32));

		jButtonPayGiftcard.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonPayGiftcard.setText("Gift Card");
		jButtonPayGiftcard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPayGiftcardActionPerformed(evt);
			}
		});
		jPanelPaymentMethods.add(jButtonPayGiftcard);

		jButtonPayDebit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonPayDebit.setText("Debit");
		jButtonPayDebit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPayDebitActionPerformed(evt);
			}
		});
		jPanelPaymentMethods.add(jButtonPayDebit);

		jButtonPayCash.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonPayCash.setText("Cash");
		jButtonPayCash.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPayCashActionPerformed(evt);
			}
		});
		jPanelPaymentMethods.add(jButtonPayCash);

		jButtonPayCredit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonPayCredit.setText("Credit");
		jButtonPayCredit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPayCreditActionPerformed(evt);
			}
		});
		jPanelPaymentMethods.add(jButtonPayCredit);

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("Select Payment Method");

		jButtonScanMembershipCard.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonScanMembershipCard.setText("Scan Membership");
		jButtonScanMembershipCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonScanMembershipCardActionPerformed(evt);
			}
		});

		jButtonPaymentGoBack.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonPaymentGoBack.setText("Go Back");
		jButtonPaymentGoBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonPaymentGoBackActionPerformed(evt);
				frame.dispose();
				software.continueScanGUI();

			}
		});

		jButtonEnterMembershipNumber.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonEnterMembershipNumber.setText("Enter Membership Number");
		jButtonEnterMembershipNumber.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonEnterMembershipNumberActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanelPaymentSelectionLayout = new javax.swing.GroupLayout(jPanelPaymentSelection);
		jPanelPaymentSelection.setLayout(jPanelPaymentSelectionLayout);
		jPanelPaymentSelectionLayout.setHorizontalGroup(jPanelPaymentSelectionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelPaymentSelectionLayout.createSequentialGroup().addContainerGap()
						.addGroup(jPanelPaymentSelectionLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanelPaymentSelectionLayout.createSequentialGroup()
										.addGap(0, 195, Short.MAX_VALUE)
										.addGroup(jPanelPaymentSelectionLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
												.addComponent(jPanelPaymentMethods,
														javax.swing.GroupLayout.PREFERRED_SIZE, 383,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel3))
										.addGap(0, 196, Short.MAX_VALUE))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanelPaymentSelectionLayout.createSequentialGroup()
												.addComponent(jButtonPaymentGoBack)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jButtonEnterMembershipNumber)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jButtonScanMembershipCard)))
						.addContainerGap()));
		jPanelPaymentSelectionLayout.setVerticalGroup(jPanelPaymentSelectionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelPaymentSelectionLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanelPaymentMethods, javax.swing.GroupLayout.PREFERRED_SIZE, 160,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(49, 49, 49)
						.addGroup(jPanelPaymentSelectionLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButtonScanMembershipCard).addComponent(jButtonPaymentGoBack)
								.addComponent(jButtonEnterMembershipNumber))
						.addContainerGap()));

		jPanelPaymentTop.add(jPanelPaymentSelection, "card2");

		jTextAreaInfo.setEditable(false);
		jTextAreaInfo.setColumns(20);
		jTextAreaInfo.setFont(new java.awt.Font("Monospaced", 0, 36)); // NOI18N
		jTextAreaInfo.setRows(5);
		jTextAreaInfo.setText("Hello World.");
		jScrollPane1.setViewportView(jTextAreaInfo);

		jButtonInfoScreenBack.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jButtonInfoScreenBack.setText("Go Back");
		jButtonInfoScreenBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonInfoScreenBackActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanelPaymentInfoScreenLayout = new javax.swing.GroupLayout(jPanelPaymentInfoScreen);
		jPanelPaymentInfoScreen.setLayout(jPanelPaymentInfoScreenLayout);
		jPanelPaymentInfoScreenLayout.setHorizontalGroup(jPanelPaymentInfoScreenLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanelPaymentInfoScreenLayout.createSequentialGroup().addGap(75, 75, 75)
						.addGroup(jPanelPaymentInfoScreenLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButtonInfoScreenBack, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(83, Short.MAX_VALUE)));
		jPanelPaymentInfoScreenLayout.setVerticalGroup(
				jPanelPaymentInfoScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelPaymentInfoScreenLayout.createSequentialGroup().addGap(51, 51, 51)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
								.addComponent(jButtonInfoScreenBack).addContainerGap()));

		jPanelPaymentTop.add(jPanelPaymentInfoScreen, "card3");

		jPanelPaymentInfo.setBackground(new java.awt.Color(153, 153, 255));

		jLabelAmountDue.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
		jLabelAmountDue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelAmountDue.setText("Amount Due: $129.99");

		jLabelAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jLabelAmountPaid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabelAmountPaid.setText("Amount Paid: 120");

		javax.swing.GroupLayout jPanelPaymentInfoLayout = new javax.swing.GroupLayout(jPanelPaymentInfo);
		jPanelPaymentInfo.setLayout(jPanelPaymentInfoLayout);
		jPanelPaymentInfoLayout.setHorizontalGroup(
				jPanelPaymentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelPaymentInfoLayout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabelAmountDue)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelPaymentInfoLayout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabelAmountPaid)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanelPaymentInfoLayout
				.setVerticalGroup(jPanelPaymentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanelPaymentInfoLayout.createSequentialGroup().addGap(17, 17, 17)
								.addComponent(jLabelAmountDue).addGap(18, 18, 18).addComponent(jLabelAmountPaid)
								.addContainerGap(30, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanelPaymentLayout = new javax.swing.GroupLayout(jPanelPayment);
		jPanelPayment.setLayout(jPanelPaymentLayout);
		jPanelPaymentLayout
				.setHorizontalGroup(jPanelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanelPaymentInfo, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanelPaymentLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
										jPanelPaymentTop, javax.swing.GroupLayout.PREFERRED_SIZE, 794,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanelPaymentLayout.setVerticalGroup(
				jPanelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanelPaymentLayout.createSequentialGroup()
								.addComponent(jPanelPaymentTop, javax.swing.GroupLayout.PREFERRED_SIZE, 386,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanelPaymentInfo, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanelPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanelPayment,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		frame.pack();
	}// </editor-fold>

	private void jButtonInfoScreenBackActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.SELECTION;
		cardLayout.first(jPanelPaymentTop);
	}

	private void jButtonPayGiftcardActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.GIFT;
		switchToPaymentPanel();
	}

	private void jButtonPayDebitActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.DEBIT;
		switchToPaymentPanel();
	}

	private void jButtonPayCashActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.CASH;
		switchToPaymentPanel();
	}

	private void jButtonPayCreditActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.CREDIT;
		switchToPaymentPanel();
	}

	private void jButtonPaymentGoBackActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		// Should link to adding items+scanning GUI part
	}

	private void jButtonScanMembershipCardActionPerformed(java.awt.event.ActionEvent evt) {
		state = PaymentGUIState.MEMBERSHIP;
		switchToPaymentPanel();
	}

	private void jButtonEnterMembershipNumberActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			new PaymentGUI(
					new SelfCheckoutStationSoftware(SelfCheckoutStationSetup.createSelfCheckoutStationFromInit()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Variables declaration - do not modify
	public javax.swing.JButton jButtonEnterMembershipNumber;
	public javax.swing.JButton jButtonInfoScreenBack;
	public javax.swing.JButton jButtonPayCash;
	public javax.swing.JButton jButtonPayCredit;
	public javax.swing.JButton jButtonPayDebit;
	public javax.swing.JButton jButtonPayGiftcard;
	public javax.swing.JButton jButtonPaymentGoBack;
	public javax.swing.JButton jButtonScanMembershipCard;
	public javax.swing.JLabel jLabelAmountDue;
	public javax.swing.JLabel jLabelAmountPaid;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanelPayment;
	private javax.swing.JPanel jPanelPaymentInfo;
	private javax.swing.JPanel jPanelPaymentInfoScreen;
	private javax.swing.JPanel jPanelPaymentMethods;
	private javax.swing.JPanel jPanelPaymentSelection;
	private javax.swing.JPanel jPanelPaymentTop;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTextArea jTextAreaInfo;
	// End of variables declaration

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub

	}

	// Observer overrides

	@Override
	public void amountPaid(SelfCheckoutStationSoftware software, BigDecimal amount) {
		System.out.println("amount paid: " + amount);
		updatePaymentLabels();
		// An amount was paid

		if (software.amountDue.compareTo(software.getAmountPaid()) <= 0)
			finishPayment();

		if (state != PaymentGUIState.CASH) {
			state = PaymentGUIState.SELECTION;
			cardLayout.first(jPanelPaymentTop); // Go back to the previous screen
		}
	}

	@Override
	public void membershipCardScanned(SelfCheckoutStationSoftware software, String memberCN) {
		// Membership card was scanned
		jButtonScanMembershipCard.setEnabled(false);
		membershipCardScanned = true;
		cardLayout.first(jPanelPaymentTop);
	}
}
