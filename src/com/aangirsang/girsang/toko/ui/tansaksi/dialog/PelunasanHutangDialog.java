/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.tansaksi.dialog;

import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutang;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutangDetail;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.model.transaksi.constant.TransaksiRunningNumberEnum;
import com.aangirsang.girsang.toko.ui.master.supplier.PilihSupplierDialog;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
import com.aangirsang.girsang.toko.util.DateRenderer;
import com.aangirsang.girsang.toko.util.POSTableCellRenderer;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author GIRSANG PC
 */
public class PelunasanHutangDialog extends javax.swing.JDialog {
    private Supplier supplier;
    private PelunasanHutang pelunasanHutang;
    private PelunasanHutangDetail pelunasanHutangDetail;
    private List<PelunasanHutangDetail> daftarPelunasanHutangDetail = new ArrayList<>();
    private List<Pembelian> pembelians;
    private Pembelian pembelian;

    BigDecimal 
            totalBayar = new BigDecimal(0), 
            hutangAkhir = new BigDecimal(0);
    String pembuat = "AAN";
    public PelunasanHutangDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        table.setDefaultRenderer(Object.class, new POSTableCellRenderer());
        table.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        table.setDefaultRenderer(Date.class, new DateRenderer());
        jdcTanggal.setDateFormatString("EE, dd MMMM yyyy");
        
    }
    public PelunasanHutang showDialog(PelunasanHutang p, String Title){
        pelunasanHutang = new PelunasanHutang();
        if(p==null){
            clearAll();
        } else{
            loadModelToForm(p);
        }
        setTitle(Title);

        setLocationRelativeTo(null);
        setVisible(true);
        return this.pelunasanHutang;
    }
    private void clearAll(){
        txtNoRef.setText(FrameUtama.getTransaksiService().ambilBerikutnya(TransaksiRunningNumberEnum.HUTANG));
        txtKodeSupplier.setText("");
        txtNamaSupplier.setText("");
        txtKwitansi.setText("");
        jdcTanggal.setDate(new Date());
        isiLblKeterangan();
    }
    private void isiLblKeterangan(){
        DecimalFormat df = new DecimalFormat("#.###");
        lblKeterangan.setText("Total Pembayaran Hutang = Rp."+TextComponentUtils.formatNumber(totalBayar)+
                " Sisa Hutang = Rp."+TextComponentUtils.formatNumber(hutangAkhir));
    }
    private boolean validateSimpan() {
        if(totalBayar==new BigDecimal(0)){
            JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                     "Tidak Ada Pembyaran",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void loadModelToForm(PelunasanHutang p){
        txtNoRef.setText(p.getNoRef());
        jdcTanggal.setDate(p.getTanggal());
        txtKodeSupplier.setText(p.getSupplier().getId());
        txtNamaSupplier.setText(p.getSupplier().getNamaSupplier());
        txtKwitansi.setText(p.getNoKwitansi());
        
        daftarPelunasanHutangDetail = FrameUtama.getTransaksiService().cariDetail(p);
        table.setModel(new TabelModel(daftarPelunasanHutangDetail));
        
        supplier = new Supplier();
        supplier = p.getSupplier();
        
        kalkulasiHutang(daftarPelunasanHutangDetail);
    }
    private void tampilDetails(Supplier s){
        daftarPelunasanHutangDetail = new ArrayList<>();
        if (s != null) {
            txtKodeSupplier.setText(s.getId());
            txtNamaSupplier.setText(s.getNamaSupplier());
            List<Pembelian> hutangPembelian = FrameUtama.getTransaksiService().hutangPembelian(s);

            BigDecimal totalHutang = new BigDecimal(0);
            BigDecimal totalPembayaran;
            for(Pembelian beli : hutangPembelian){
                List<PelunasanHutangDetail> pelunasanHutangs = FrameUtama.getTransaksiService().cariPembelian(beli);
                totalPembayaran = new BigDecimal(0);
                for(PelunasanHutangDetail detail : pelunasanHutangs){
                    totalPembayaran = totalPembayaran.add(detail.getPembayaran());
                }
                totalHutang = beli.getDaftarKredit().getSisaKredit().subtract(totalPembayaran);
                double d = totalHutang.doubleValue();
                if(totalHutang.doubleValue() >0){
                    
                pelunasanHutangDetail = new PelunasanHutangDetail();
                    pembelian = beli;
                    pelunasanHutangDetail.setPelunasanHutang(pelunasanHutang);
                    pelunasanHutangDetail.setPembelian(pembelian);
                    pelunasanHutangDetail.setSisaHutang(totalHutang);
                    pelunasanHutangDetail.setPembayaran(new BigDecimal(0));
                    pelunasanHutangDetail.setHutangAkhir(pelunasanHutangDetail.getSisaHutang().
                            subtract(pelunasanHutangDetail.getPembayaran()));

                    daftarPelunasanHutangDetail.add(pelunasanHutangDetail);
                }
            }
            table.setModel(new TabelModel(daftarPelunasanHutangDetail));
            kalkulasiHutang(daftarPelunasanHutangDetail);
        }
    }
    private void loadFormToModel(){
        pelunasanHutang.setNoRef(txtNoRef.getText());
        pelunasanHutang.setTanggal(new Date());
        pelunasanHutang.setNoKwitansi(txtKwitansi.getText());
        pelunasanHutang.setSupplier(supplier);
        pelunasanHutang.setJlhBayar(totalBayar);
        pelunasanHutang.setPembuat(pembuat);
        pelunasanHutang.setPelunasanHutangDetails(daftarPelunasanHutangDetail);
        
    }
    private void kalkulasiHutang(List<PelunasanHutangDetail> ps){
        totalBayar = new BigDecimal(0); 
        hutangAkhir = new BigDecimal(0);
        for(int i=0;i<ps.size();i++){
            PelunasanHutangDetail p = ps.get(i);
            totalBayar = totalBayar.add(p.getPembayaran());
            hutangAkhir = hutangAkhir.add(p.getHutangAkhir());
        }
        isiLblKeterangan();
    }
    private class TabelModel extends AbstractTableModel {
        private final List<PelunasanHutangDetail> daftarPelunasanHutangDetails;
        BigDecimal hutangAkhir, pembayaran = new BigDecimal(0);
        public TabelModel(List<PelunasanHutangDetail> daftarhutang) {
            this.daftarPelunasanHutangDetails = daftarhutang;
        }
        @Override
        public int getRowCount() {
            return daftarPelunasanHutangDetails.size();
        }
        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Ref Pembelian";
                case 1:return "No. Faktur";
                case 2:return "Tanggal";
                case 3:return "Sisa Hutang";
                case 4:return "Pembayaran";
                case 5:return "Hutang Akhir";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            PelunasanHutangDetail p = daftarPelunasanHutangDetails.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getPembelian().getNoRef();
                case 1:return p.getPembelian().getNoFaktur();
                case 2:return p.getPembelian().getTanggal();
                case 3:return p.getSisaHutang();
                case 4:return p.getPembayaran();
                case 5:
                    if(p.getPembayaran()==new BigDecimal(0)){
                        return p.getSisaHutang();
                    }else{
                        return p.getHutangAkhir();
                    }
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 2:return Date.class;
                case 3:return BigDecimal.class;
                case 4:return BigDecimal.class;
                case 5:return BigDecimal.class;
                default:return String.class;
            }
        }
        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            if (columnIndex == 4){
                return true;
            }else{
                return false;
            }
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            PelunasanHutangDetail p = daftarPelunasanHutangDetails.get(rowIndex);
            switch (columnIndex) {
            case 4:
                p.setPembayaran((BigDecimal) aValue);
                p.setHutangAkhir(p.getSisaHutang().subtract(p.getPembayaran()));
                fireTableCellUpdated(rowIndex, columnIndex); // Total may also have changed
                fireTableCellUpdated(rowIndex, 5); // Total may also have changed
                break;
            }
            kalkulasiHutang(daftarPelunasanHutangDetails);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdcTanggal = new com.toedter.calendar.JDateChooser();
        txtKwitansi = new javax.swing.JTextField();
        txtKodeSupplier = new javax.swing.JTextField();
        txtNamaSupplier = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtNoRef = new javax.swing.JTextField();
        lblKeterangan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jLabel4.text")); // NOI18N

        txtKwitansi.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.txtKwitansi.text")); // NOI18N

        txtKodeSupplier.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.txtKodeSupplier.text")); // NOI18N
        txtKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSupplierActionPerformed(evt);
            }
        });

        txtNamaSupplier.setBackground(new java.awt.Color(255, 255, 204));
        txtNamaSupplier.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.txtNamaSupplier.text")); // NOI18N

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ref Pembelian", "No. Faktur", "Tanggal", "Sisa Hutang", "Pembayaran", "Hutang Akhir"
            }
        ));
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title0")); // NOI18N
            table.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title1")); // NOI18N
            table.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title2")); // NOI18N
            table.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title3")); // NOI18N
            table.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title4")); // NOI18N
            table.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.table.columnModel.title5")); // NOI18N
        }

        jButton1.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtNoRef.setBackground(new java.awt.Color(255, 255, 204));
        txtNoRef.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.txtNoRef.text")); // NOI18N

        lblKeterangan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKeterangan.setText(org.openide.util.NbBundle.getMessage(PelunasanHutangDialog.class, "PelunasanHutangDialog.lblKeterangan.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtKwitansi, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblKeterangan)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(jLabel1)
                    .addComponent(txtNoRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtKwitansi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jdcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKeterangan)
                .addGap(25, 25, 25)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initListener() {
        
        txtNoRef.setEditable(false);
        txtNamaSupplier.setEditable(false);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Apakah Anda Yakin Ingin Membatalkan Editing", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    pelunasanHutang = null;
                    dispose();
                }
            }
        });
    }
    private void txtKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeSupplierActionPerformed
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String judul = "Pilih Supplier Pembelian";
        Supplier s = (Supplier) new PilihSupplierDialog().showDialog(judul);
        tampilDetails(s);
        supplier = new Supplier();
        supplier = FrameUtama.getMasterService().supplierBerdasarkanId(s.getId());
    }//GEN-LAST:event_btnCariActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        pelunasanHutang = null;
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(validateSimpan()){
            pelunasanHutang = new PelunasanHutang();
            loadFormToModel();
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private com.toedter.calendar.JDateChooser jdcTanggal;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JTable table;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtKwitansi;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoRef;
    // End of variables declaration//GEN-END:variables
}
