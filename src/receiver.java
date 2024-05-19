import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
public class receiver extends javax.swing.JFrame {
    private JPanel imagePanel;
    public receiver() {
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt){
        databse db=new databse();
        String username=transaction.getreceiver();
        try {        
        String sql="SELECT *FROM user WHERE name= '"+username+"'";
        ResultSet rs=db.stm.executeQuery(sql);
        while(rs.next()){
            recname.setText(username);
            byte[] imageData = rs.getBytes("img");
                if (imageData != null) {
                    ImageIcon icon = new ImageIcon(imageData);
                    Image img = icon.getImage();
                int width = image1.getWidth();
                int height = image1.getHeight();
                
                // Maintain a 2:2 aspect ratio
                if (width > height) {
                    height = width * 2 / 2;
                } else {
                    width = height * 2 / 2;
                }
                
                Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                image1.setIcon(new ImageIcon(resizedImg));
                }
        }
        
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recname = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        send = new javax.swing.JButton();
        image1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        recname.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        recname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Enter amount");

        jLabel2.setText("Receiver Name");

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        image1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(send))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(amount, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(recname, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(image1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(image1, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(recname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(send)
                .addGap(38, 38, 38))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        // TODO add your handling code here:
    databse db = new databse();
    String receiverName = transaction.getreceiver();
    String senderName = login.getloggedinuser();

    try {
        // Fetch the sender's balance
        String senderQuery = "SELECT * FROM user WHERE name = '" + senderName + "'";
        ResultSet senderRS = db.stm.executeQuery(senderQuery);
        double senderBalance = 0.0;
        while (senderRS.next()) {
            senderBalance = senderRS.getDouble("balance");
        }

        // Fetch the receiver's information
        String receiverQuery = "SELECT * FROM user WHERE name = '" + receiverName + "'";
        ResultSet receiverRS = db.stm.executeQuery(receiverQuery);
        double receiverBalance = 0.0;
        while (receiverRS.next()) {
            receiverBalance = receiverRS.getDouble("balance");
        }

        // Get the transaction amount
        double transferAmount = Double.parseDouble(amount.getText());

        // Check if the sender has enough balance
        if (senderBalance >= transferAmount) {
            // Update the sender's balance
            double newSenderBalance = senderBalance - transferAmount;
            String updateSenderQuery = "UPDATE user SET balance = " + newSenderBalance + " WHERE name = '" + senderName + "'";
            db.stm.executeUpdate(updateSenderQuery);

            // Update the receiver's balance
            double newReceiverBalance = receiverBalance + transferAmount;
            String updateReceiverQuery = "UPDATE user SET balance = " + newReceiverBalance + " WHERE name = '" + receiverName + "'";
            db.stm.executeUpdate(updateReceiverQuery);
            dispose();
            JOptionPane.showMessageDialog(this, "Transfer successful!");
            
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient balance!");
        }
    } catch (SQLException ex) {
        Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(receiver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new receiver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amount;
    private javax.swing.JLabel image1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel recname;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables
}
