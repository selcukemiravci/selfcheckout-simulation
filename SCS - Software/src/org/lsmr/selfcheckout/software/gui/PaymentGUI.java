package org.lsmr.selfcheckout.software.gui;

import java.awt.CardLayout;

public class PaymentGUI extends javax.swing.JFrame {
    CardLayout cardLayout;

    /**
     * Creates new form PaymentGUI
     */
    public PaymentGUI() {
        initComponents();
        
        cardLayout = (CardLayout)(jPanelPaymentTop.getLayout());
        cardLayout.last(jPanelPaymentTop);
        
        setLocation(100, 300);
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
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButtonScanMembershipCard = new javax.swing.JButton();
        jButtonPaymentGoBack = new javax.swing.JButton();
        jPanelPaymentInfoScreen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInfo = new javax.swing.JTextArea();
        jButtonInfoScreenBack = new javax.swing.JButton();
        jPanelPaymentInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelPaymentTop.setLayout(new java.awt.CardLayout());

        jPanelPaymentMethods.setLayout(new java.awt.GridLayout(2, 2, 64, 32));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton3.setText("Gift Card");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanelPaymentMethods.add(jButton3);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("Debit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanelPaymentMethods.add(jButton2);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("Cash");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanelPaymentMethods.add(jButton1);

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton4.setText("Credit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanelPaymentMethods.add(jButton4);

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
            }
        });

        javax.swing.GroupLayout jPanelPaymentSelectionLayout = new javax.swing.GroupLayout(jPanelPaymentSelection);
        jPanelPaymentSelection.setLayout(jPanelPaymentSelectionLayout);
        jPanelPaymentSelectionLayout.setHorizontalGroup(
            jPanelPaymentSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentSelectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPaymentSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPaymentSelectionLayout.createSequentialGroup()
                        .addGap(0, 195, Short.MAX_VALUE)
                        .addGroup(jPanelPaymentSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jPanelPaymentMethods, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 196, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPaymentSelectionLayout.createSequentialGroup()
                        .addComponent(jButtonPaymentGoBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonScanMembershipCard)))
                .addContainerGap())
        );
        jPanelPaymentSelectionLayout.setVerticalGroup(
            jPanelPaymentSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentSelectionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPaymentMethods, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanelPaymentSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonScanMembershipCard)
                    .addComponent(jButtonPaymentGoBack))
                .addContainerGap())
        );

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
        jPanelPaymentInfoScreenLayout.setHorizontalGroup(
            jPanelPaymentInfoScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentInfoScreenLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanelPaymentInfoScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInfoScreenBack, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanelPaymentInfoScreenLayout.setVerticalGroup(
            jPanelPaymentInfoScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentInfoScreenLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButtonInfoScreenBack)
                .addContainerGap())
        );

        jPanelPaymentTop.add(jPanelPaymentInfoScreen, "card3");

        jPanelPaymentInfo.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Amount Due: $129.99");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Amount Paid: 120");

        javax.swing.GroupLayout jPanelPaymentInfoLayout = new javax.swing.GroupLayout(jPanelPaymentInfo);
        jPanelPaymentInfo.setLayout(jPanelPaymentInfoLayout);
        jPanelPaymentInfoLayout.setHorizontalGroup(
            jPanelPaymentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPaymentInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelPaymentInfoLayout.setVerticalGroup(
            jPanelPaymentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPaymentInfoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelPaymentLayout = new javax.swing.GroupLayout(jPanelPayment);
        jPanelPayment.setLayout(jPanelPaymentLayout);
        jPanelPaymentLayout.setHorizontalGroup(
            jPanelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPaymentInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPaymentLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelPaymentTop, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelPaymentLayout.setVerticalGroup(
            jPanelPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPaymentLayout.createSequentialGroup()
                .addComponent(jPanelPaymentTop, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelPaymentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void jButtonInfoScreenBackActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
        cardLayout.first(jPanelPaymentTop);
    }                                                     

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButtonPaymentGoBackActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void jButtonScanMembershipCardActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        // TODO add your handling code here:
    }                                                         

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonInfoScreenBack;
    private javax.swing.JButton jButtonPaymentGoBack;
    private javax.swing.JButton jButtonScanMembershipCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelPayment;
    private javax.swing.JPanel jPanelPaymentInfo;
    private javax.swing.JPanel jPanelPaymentInfoScreen;
    private javax.swing.JPanel jPanelPaymentMethods;
    private javax.swing.JPanel jPanelPaymentSelection;
    private javax.swing.JPanel jPanelPaymentTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaInfo;
    // End of variables declaration                   
}
