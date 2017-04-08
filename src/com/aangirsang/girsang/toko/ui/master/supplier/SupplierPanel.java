/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.supplier;

import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter;
import com.aangirsang.girsang.toko.ui.master.supplier.SupplierDialog;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ITSUSAHBRO
 */
public class SupplierPanel extends javax.swing.JPanel {

    private List<Supplier> suppliers;
    private Supplier supplier;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title;
    ToolbarTanpaFilter toolBar = new ToolbarTanpaFilter();

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

    public ToolbarTanpaFilter getToolbarTanpaFilter1() {
        return toolbarTanpaFilter1;
    }

    /**
     * Creates new form KategoriPanel
     */
    public SupplierPanel() {
        initComponents();
        initListener();
        tblSupplier.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabelKategori();
    }

    private void ukuranTabelProduk() {
        tblSupplier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblSupplier.getColumnModel().getColumn(0).setPreferredWidth(100);//Kode SUpplier
        tblSupplier.getColumnModel().getColumn(1).setPreferredWidth(200);//Nama
        tblSupplier.getColumnModel().getColumn(2).setPreferredWidth(350);//Alamat
        tblSupplier.getColumnModel().getColumn(3).setPreferredWidth(100);//HP
        tblSupplier.getColumnModel().getColumn(4).setPreferredWidth(100);//telepon
        tblSupplier.getColumnModel().getColumn(5).setPreferredWidth(100);//Saldo
        tblSupplier.getColumnModel().getColumn(6).setPreferredWidth(200);//Email
        tblSupplier.getColumnModel().getColumn(7).setPreferredWidth(100);//Kota
    }

    private void isiTabelKategori() {
        suppliers = FrameUtama.getMasterService().semuaSupplier();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(suppliers));
        tblSupplier.setRowSorter(sorter);
        tblSupplier.setModel(new kategoriTabelModel(suppliers));
        toolbarTanpaFilter1.getTxtCari().setText("");
        ukuranTabelProduk();
        lblJumlahData.setText(suppliers.size() + " Data Supplier");
    }

    private void loadFormToModel(Supplier s){
        supplier.setId(s.getId());
        supplier.setNamaSupplier(s.getNamaSupplier());
        supplier.setAlamatSupplier(s.getAlamatSupplier());
        supplier.setHpSupplier(s.getHpSupplier());
        supplier.setTeleponSupplier(s.getTeleponSupplier());
        supplier.setEmailSupplier(s.getEmailSupplier());
        supplier.setKotaSupplier(s.getKotaSupplier());
        supplier.setKodePos(s.getKodePos());
        supplier.setSaldoHutang(s.getSaldoHutang());
    }
    private class kategoriTabelModel extends AbstractTableModel {

        private final List<Supplier> listKategori;
        String columnNames[] = 
        {"Kode Supplier",
            "Nama Supplier",
            "Alamat",
            "HP",
            "Telepon",
            "Saldo Hutang",
            "Email",
            "Kota",
            "Kode POS"};

        public kategoriTabelModel(List<Supplier> listKategori) {
            this.listKategori = listKategori;
        }

        @Override
        public int getRowCount() {
            return listKategori.size();
        }

        @Override
        public int getColumnCount() {
            return 9;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Supplier p = suppliers.get(rowIndex);
            switch (columnIndex) {
                case 0: return p.getId();
                case 1: return p.getNamaSupplier();
                case 2: return p.getAlamatSupplier();
                case 3: return p.getHpSupplier();
                case 4: return p.getTeleponSupplier();
                case 5: return p.getSaldoHutang();
                case 6: return p.getEmailSupplier();
                case 7: return p.getKotaSupplier();
                case 8: return p.getKodePos();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 5 : return BigDecimal.class;
                default : return String.class;
            }
        }
    }

    private void initListener() {
        tblSupplier.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblSupplier.getSelectedRow() >= 0) {
                String id = tblSupplier.getValueAt(tblSupplier.getSelectedRow(), 0).toString();
                supplier = new Supplier();
                supplier = FrameUtama.getMasterService().supplierBerdasarkanId(id);
            }
        });
        tblSupplier.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Supplier";
                    if (supplier == null) {
                        JOptionPane.showMessageDialog(null, "Data Supplier Belum Terpilih");
                    } else {
                        Supplier s = new SupplierDialog().showDialog(supplier, title);
                        supplier = new Supplier();
                        if (s != null) {
                            loadFormToModel(s);
                            FrameUtama.getMasterService().simpan(supplier);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                            title = null;
                        }
                    }
                }
            }
        });
        toolbarTanpaFilter1.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolbarTanpaFilter1.getTxtCari().getText())) {
                    isiTabelKategori();
                } else {
                    suppliers = FrameUtama.getMasterService().cariNamaSupplier(toolbarTanpaFilter1.getTxtCari().getText());
                    tblSupplier.setModel(new kategoriTabelModel(suppliers));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(suppliers));
                    tblSupplier.setRowSorter(sorter);
                    ukuranTabelProduk();
                    int jml = suppliers.size();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }
        });

        toolbarTanpaFilter1.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolbarTanpaFilter1.getBtnBaru().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
            supplier = null;
            title = "Tambah Data Supplier";
            Supplier s = new SupplierDialog().showDialog(supplier, title);
            supplier = new Supplier();
            if (s != null) {
                loadFormToModel(s);
                supplier.setId("");
                FrameUtama.getMasterService().simpan(supplier);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            supplier = null;
        });

        toolbarTanpaFilter1.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Supplier";
            if (supplier == null) {
                JOptionPane.showMessageDialog(null, "Data Supplier Belum Terpilih");
            } else {
                Supplier s = new SupplierDialog().showDialog(supplier, title);
                supplier = new Supplier();
                if (s != null) {
                    loadFormToModel(s);
                    FrameUtama.getMasterService().simpan(supplier);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                    title = null;
                }
            }
        });

        toolbarTanpaFilter1.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (supplier == null) {
                JOptionPane.showMessageDialog(null, "Data Supplier Belum Terpilih");
            } else {
                FrameUtama.getMasterService().hapus(supplier);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
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
        tblSupplier = new javax.swing.JTable();
        toolbarTanpaFilter1 = new com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter();
        lblJumlahData = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Supplier 63X63.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Supplier");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data supplier");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Supplier", "Nama Supplier", "Alamat", "HP", "Telepon", "Saldo Hutang", "Email", "Kota", "Kode Pos"
            }
        ));
        jScrollPane1.setViewportView(tblSupplier);
        if (tblSupplier.getColumnModel().getColumnCount() > 0) {
            tblSupplier.getColumnModel().getColumn(0).setResizable(false);
            tblSupplier.getColumnModel().getColumn(1).setResizable(false);
        }

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(toolbarTanpaFilter1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(5, 5, 5))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolbarTanpaFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblSupplier;
    private com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter toolbarTanpaFilter1;
    // End of variables declaration//GEN-END:variables
}
