/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.security;

import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.twmacinta.util.MD5;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author GIRSANG PC
 */
public class LoginPanel extends javax.swing.JPanel {
    
    private List<Pengguna> penggunas;
    private Pengguna pengguna;

    int IndexTab = 0;
    int aktifPanel = 0;
    public int getIndexTab() {
        return IndexTab;
    }
    public void setIndexTab(int IndexTab) {
        this.IndexTab = IndexTab;
    }
    public int getAktifPanel() {
        return aktifPanel;
    }
    public void setAktifPanel(int aktifPanel) {
        this.aktifPanel = aktifPanel;
    }
    public LoginPanel() {
        initComponents();
        iniListener();
        txtUsername.setText("");
        txtPassword.setText("");
    }
    private boolean validateLogin(){
        if(txtUsername.equals("") || txtPassword.getPassword().equals("")){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                    "Isi Semua Data",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            String pass = new MD5(new String(txtPassword.getPassword())).asHex();
            pengguna = new Pengguna();
            pengguna = FrameUtama.getSecurityService().cariUserNamePengguna(txtUsername.getText());
            if(pengguna==null){
                JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                        "Username Tidak Terdaftar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                txtUsername.requestFocus();
                return false;
            }else if(pengguna!=null && pengguna.getPassword().equals(pass)){
                return true;
            }
        return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogin = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.jLabel2.text")); // NOI18N

        txtUsername.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.txtUsername.text")); // NOI18N

        btnLogin.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.btnLogin.text")); // NOI18N

        btnBatal.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.btnBatal.text")); // NOI18N

        txtPassword.setText(org.openide.util.NbBundle.getMessage(LoginPanel.class, "LoginPanel.txtPassword.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnLogin)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnLogin});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnBatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void iniListener(){
        btnLogin.addActionListener((ae) -> {
            if(validateLogin()){
                System.out.println("sukses");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
