/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.tansaksi;

import com.aangirsang.girsang.toko.Launcher;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import com.aangirsang.girsang.toko.ui.tansaksi.dialog.PembelianDialog;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
import com.aangirsang.girsang.toko.util.DateRenderer;
import com.aangirsang.girsang.toko.util.IntegerRenderer;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ITSUSAHBRO
 */
public class PembelianPanel extends javax.swing.JPanel {

    private List<Pembelian> pembelians;
    private Pembelian pembelian;
    private Supplier supplier;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect;
    ToolbarDenganFilter toolBar = new ToolbarDenganFilter();

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
    public ToolbarDenganFilter getToolbarDenganFilter1() {
        return toolbar;
    }

    public PembelianPanel() {
        initComponents();
        initListener();
        tblPembelian.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblPembelian.setDefaultRenderer(Date.class, new DateRenderer());
        tblPembelian.setDefaultRenderer(Integer.class, new IntegerRenderer());
        isiTabelKategori();
    }
    private void ukuranTabelBarang() {
        tblPembelian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblPembelian.getColumnModel().getColumn(0).setPreferredWidth(200);//Tanggal
        tblPembelian.getColumnModel().getColumn(1).setPreferredWidth(100);//No. Ref
        tblPembelian.getColumnModel().getColumn(2).setPreferredWidth(100);//No. Faktur
        tblPembelian.getColumnModel().getColumn(3).setPreferredWidth(350);//Supplier
        tblPembelian.getColumnModel().getColumn(4).setPreferredWidth(50);//Kredit
        tblPembelian.getColumnModel().getColumn(5).setPreferredWidth(100);//Tgl. Tempo
        tblPembelian.getColumnModel().getColumn(6).setPreferredWidth(100);//Jlh. Pembelian
        tblPembelian.getColumnModel().getColumn(7).setPreferredWidth(300);//Pembuat
    }
    private void isiTabelKategori() {
        pembelians = Launcher.getTransaksiService().semuaPembelian();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new PembelianTabelModel(pembelians));
        tblPembelian.setRowSorter(sorter);
        tblPembelian.setModel(new PembelianTabelModel(pembelians));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(pembelians.size() + " Data Pembelian");
        idSelect = "";
    }
    private void loadFormToModel(Pembelian p) {
        pembelian.setNoRef(p.getNoRef());
        pembelian.setTanggal(p.getTanggal());
        pembelian.setNoFaktur(p.getNoFaktur());
        pembelian.setSupplier(p.getSupplier());
        pembelian.setKredit(p.getKredit());
        pembelian.setDaftarKredit(p.getDaftarKredit());
        pembelian.setTotal(p.getTotal());
        pembelian.setPembuat(p.getPembuat());
        pembelian.setPembelianDetails(p.getPembelianDetails());
        pembelian.setLokasi(p.getLokasi());
    }
    private void cariSelect() {
        pembelian = new Pembelian();
        pembelian = Launcher.getTransaksiService().cariPembelian(idSelect);
    }
    private class PembelianTabelModel extends AbstractTableModel {
        private final List<Pembelian> daftarPembelian;
        public PembelianTabelModel(List<Pembelian> daftarPembelian) {
            this.daftarPembelian = daftarPembelian;
        }
        @Override
        public int getRowCount() {
            return daftarPembelian.size();
        }
        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "Tanggal";
                case 1:return "No. Ref";
                case 2:return "No. Faktur";
                case 3:return "Supplier";
                case 4:return "Kredit";
                case 5:return "Tgl. Tempo";
                case 6:return "Jlh. Pembelian";
                case 7:return "Pembuat";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Pembelian p = pembelians.get(rowIndex);
            switch (colIndex) {
                case 0:return p.getTanggal();
                case 1:return p.getNoRef();
                case 2:
                    if(p.getNoFaktur()!=""){
                        return p.getNoFaktur();
                    }else{
                        return "-";
                    }
                case 3:
                    if(p.getSupplier()!=null){
                        return p.getSupplier().getNamaSupplier();
                    }else{
                        return "-";
                    }
                case 4:return p.getKredit();
                case 5:
                if(p.getKredit()==true){
                    return p.getDaftarKredit().getTanggalTempo();
                }else{
                    return "-";
                }
                case 6:return p.getTotal();
                case 7:return p.getPembuat().getNamaLengkap();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:return Date.class;
                case 4:return Boolean.class;
                case 6:return BigDecimal.class;
                default:return String.class;
            }
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPembelian = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/1411950132.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Transaksi Pembelian");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Transaksi Pembelian");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "No. Ref", "No. Faktur", "Supplier", "Kredit", "Tgl. Tempo", "Jumlah Pembelian", "Pembuat"
            }
        ));
        jScrollPane1.setViewportView(tblPembelian);
        if (tblPembelian.getColumnModel().getColumnCount() > 0) {
            tblPembelian.getColumnModel().getColumn(0).setResizable(false);
            tblPembelian.getColumnModel().getColumn(1).setResizable(false);
        }

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListener() {
        tblPembelian.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblPembelian.getSelectedRow() >= 0) {
                idSelect = tblPembelian.getValueAt(tblPembelian.getSelectedRow(), 1).toString();
            }
        });
        tblPembelian.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Pembelian";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        Pembelian p = new PembelianDialog().showDialog(pembelian,pembelian.getSupplier(), title);
                        pembelian = new Pembelian();
                        if (p != null) {
                            loadFormToModel(p);
                            Launcher.getTransaksiService().simpan(pembelian);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
                }
            }
        });
        toolbar.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolbar.getTxtCari().getText())) {
                    isiTabelKategori();
                } else {
                    pembelians = (List<Pembelian>) Launcher.getTransaksiService().cariPembelian(toolbar.getTxtCari().getText());
                    tblPembelian.setModel(new PembelianTabelModel(pembelians));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new PembelianTabelModel(pembelians));
                    tblPembelian.setRowSorter(sorter);
                    ukuranTabelBarang();
                    int jml = pembelians.size();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }
        });

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
            pembelian = null;
            supplier = null;
            title = "Tambah Data Barang";
            Pembelian p = new PembelianDialog().showDialog(pembelian, supplier, title);
            pembelian = new Pembelian();
            if (p != null) {
                loadFormToModel(p);
                pembelian.setNoRef("");
                Launcher.getTransaksiService().simpan(pembelian);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            pembelian = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Barang";
            if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Pembelian Belum Terpilih");
                    } else {
                        cariSelect();
                        Pembelian p = new PembelianDialog().showDialog(pembelian, pembelian.getSupplier(), title);
                        pembelian = new Pembelian();
                        if (p != null) {
                            loadFormToModel(p);
                            Launcher.getTransaksiService().simpan(pembelian);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            /*if (pembelian == null) {
            JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
            } else {
            FrameUtama.getMasterService().hapus(pembelian);
            isiTabelKategori();
            JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }*/
        });
        toolbar.getBtnFilter().addActionListener((ActionEvent ae) -> {
            /*List <Barang> list = new FilterBarang().showDialog();
            System.out.println("Fiter Barang");*/
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblPembelian;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
