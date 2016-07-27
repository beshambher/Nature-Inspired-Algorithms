/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aco;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author wmc
 */
public class ACO extends javax.swing.JFrame {

    private ACO_v3 A;
    private SourceFile_v2 s;

    /**
     * Creates new form ACO
     */
    public ACO() {
        initComponents();
        setTitle("Minor Project");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\mms\\icons\\folder.jpg");
        setIconImage(icon.getImage());
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        A = new ACO_v3();
        s = new SourceFile_v2();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rfd = new javax.swing.JButton();
        aco = new javax.swing.JButton();
        cuckoo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(154, 244, 151));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        rfd.setText("RFD");
        rfd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rfd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rfdMouseClicked(evt);
            }
        });

        aco.setText("ACO");
        aco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acoMouseClicked(evt);
            }
        });

        cuckoo.setText("CUCKOO");
        cuckoo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cuckoo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cuckooMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(aco, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addGap(83, 83, 83)
                .addComponent(rfd, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addGap(86, 86, 86))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(cuckoo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aco, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rfd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(cuckoo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rfdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rfdMouseClicked
        // TODO add your handling code here:
        this.setEnabled(false);
        System.out.println("RFD Clicked!");
        try {
            this.dispose();
            s.text = "rfd";
            s.showFile();
            System.out.println("RFD running!");
        } catch (Exception e) {
            System.err.println("in RFD " + e);
        }
    }//GEN-LAST:event_rfdMouseClicked

    private void acoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acoMouseClicked
        // TODO add your handling code here:
        System.out.println("ACO Clicked!");
        try {
            this.dispose();
            s.text = "aco";
            s.showFile();
            System.out.println("ACO running!");
        } catch (Exception e) {
            System.err.println("in ACO " + e);
        }
        //this.setEnabled(false);
        //this.dispose();
    }//GEN-LAST:event_acoMouseClicked

    private void cuckooMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuckooMouseClicked
        // TODO add your handling code here:
        System.out.println("CUCKOO Clicked!");
        try {
            this.dispose();
            s.text = "coo";
            s.showFile();
            System.out.println("CUCKOO running!");
        } catch (Exception e) {
            System.err.println("in CUCKOO " + e);
        }

    }//GEN-LAST:event_cuckooMouseClicked

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
            java.util.logging.Logger.getLogger(ACO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ACO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ACO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ACO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ACO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aco;
    private javax.swing.JButton cuckoo;
    private javax.swing.JButton rfd;
    // End of variables declaration//GEN-END:variables
}