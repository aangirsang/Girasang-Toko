/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.security.dialog;

import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author GIRSANG PC
 */
public class DialogPengguna extends javax.swing.JDialog {
    
    private Pengguna pengguna;
    private TingkatAkses tingkatAkses;
    private List<TingkatAkses> daftarTingkatAkses;
    private String title;

    /**
     * Creates new form DialogTingkatAkses
     */
    public DialogPengguna() {
        super(FrameUtama.getInstance(), true);
        setTitle(title);
        initComponents();
        initListener();
        TextComponentUtils.setAutoUpperCaseText(30, txtUserName);
        TextComponentUtils.setAutoUpperCaseText(100, txtNamaLengkap);
        txtId.setEditable(false);
        txtUserName.requestFocus();
    }
    public Pengguna showDialog(Pengguna p ,String title){
        if(p==null){
            clear();
        }else{
            isiComboTingkatAkses();
            txtId.setText(p.getIdPengguna());
            txtUserName.setText(p.getUserName());
            txtNamaLengkap.setText(p.getNamaLengkap());
            cboTingkatAkses.setSelectedItem(p.getTingkatAkses().getNamaTingkatAkses());
            txtPassword.setText(p.getPassword());
            txtPasswordHint.setText(p.getPasswordHint());
            txtAlamat.setText(p.getAlamat());
            txtHP.setText(p.getHp());
            txtTelepon.setText(p.getTelepon());
            jcbStatus.setSelected(p.getStatus());
            
            tingkatAkses = p.getTingkatAkses();
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        return this.pengguna;
    }
    
    private void clear(){
        txtId.setText(FrameUtama.getMasterService().ambilBerikutnya(MasterRunningNumberEnum.PENGGUNA));
        txtUserName.setText("");
        txtNamaLengkap.setText("");
        txtPassword.setText("");
        txtPasswordHint.setText("");
        txtAlamat.setText("");
        txtHP.setText("");
        txtTelepon.setText("");
        jcbStatus.setSelected(true);
        isiComboTingkatAkses();
        cboTingkatAkses.setSelectedItem(null);
    }
    private boolean validateSimpan(){
        if("".equals(txtUserName.getText())){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Nama Tingkat Akses Harus Di Isi",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtUserName.requestFocus();
            return false;
        }
        if(tingkatAkses==null){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                    "Tingkat Akses Harus Di Isi",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cboTingkatAkses.requestFocus();
            return false;
        }
        return true;
    }
    private void isiComboTingkatAkses() {
        cboTingkatAkses.removeAllItems();
        daftarTingkatAkses = FrameUtama.getSecurityService().semuaTingkatAkses();
        for (int i = 0; i < daftarTingkatAkses.size(); i++) {
            cboTingkatAkses.addItem(daftarTingkatAkses.get(i).getNamaTingkatAkses());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtNamaLengkap = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboTingkatAkses = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        txtPasswordHint = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        txtHP = new javax.swing.JTextField();
        txtTelepon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jcbStatus = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtId.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtId.text")); // NOI18N

        txtUserName.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtUserName.text")); // NOI18N

        txtNamaLengkap.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtNamaLengkap.text")); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel3.text")); // NOI18N

        cboTingkatAkses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        txtPassword.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtPassword.text")); // NOI18N

        txtPasswordHint.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtPasswordHint.text")); // NOI18N

        txtAlamat.setColumns(20);
        txtAlamat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        txtHP.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtHP.text")); // NOI18N

        txtTelepon.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.txtTelepon.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel5.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel6.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel7.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel8.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jLabel9.text")); // NOI18N

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.btnSimpan.text")); // NOI18N

        btnBatal.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.btnBatal.text")); // NOI18N

        jcbStatus.setText(org.openide.util.NbBundle.getMessage(DialogPengguna.class, "DialogPengguna.jcbStatus.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaLengkap, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(txtPassword)
                            .addComponent(txtPasswordHint, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cboTingkatAkses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnSimpan});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboTingkatAkses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPasswordHint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCari, cboTingkatAkses});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnCariActionPerformed

    private void initListener(){
        btnSimpan.addActionListener((ae) -> {
            if(validateSimpan()){
                pengguna = new Pengguna();
                pengguna.setIdPengguna(txtId.getText());
                pengguna.setUserName(txtUserName.getText());
                pengguna.setNamaLengkap(txtNamaLengkap.getText());
                pengguna.setTingkatAkses(tingkatAkses);
                pengguna.setPassword(new String(txtPassword.getPassword()));
                pengguna.setPasswordHint(txtPasswordHint.getText());
                pengguna.setAlamat(txtAlamat.getText());
                pengguna.setHp(txtHP.getText());
                pengguna.setTelepon(txtTelepon.getText());
                pengguna.setStatus(jcbStatus.isSelected());

                dispose();
            }
        });
        btnBatal.addActionListener((ae) -> {
            pengguna = null;
            dispose();
        });
        btnCari.addActionListener((ae) -> {
            String judul = "Pilih Tingkat Akses Pengguna";
            TingkatAkses t = (TingkatAkses) new PilihTingkatAkses().showDialog(judul);
            isiComboTingkatAkses();
            if (t != null) {
                cboTingkatAkses.setSelectedItem(t.getNamaTingkatAkses());
                tingkatAkses = new TingkatAkses();
                tingkatAkses = t;
            }
        });
        cboTingkatAkses.addItemListener((ie) -> {
            tingkatAkses = new TingkatAkses();
            if(cboTingkatAkses.getSelectedIndex() >= 0){
                tingkatAkses = daftarTingkatAkses.get(cboTingkatAkses.getSelectedIndex());
            }else{
                tingkatAkses = null;
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cboTingkatAkses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox jcbStatus;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtHP;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNamaLengkap;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPasswordHint;
    private javax.swing.JTextField txtTelepon;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
