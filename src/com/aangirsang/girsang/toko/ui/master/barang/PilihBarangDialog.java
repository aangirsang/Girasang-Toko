/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.barang;

import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * @author GIRSANG PC
 */
public class PilihBarangDialog extends javax.swing.JDialog {

    private List<Barang> barangs;
    private Barang barang;
    String title, idSelect;
    /**
     * Creates new form PilihBarangDialog
     */
    public PilihBarangDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        tblBarang.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabelBarang();
        initListener();
    }
    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return barang;
    }
    private void ukuranTabelBarang() {
        tblBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBarang.getColumnModel().getColumn(0).setPreferredWidth(100);//PLU
        tblBarang.getColumnModel().getColumn(1).setPreferredWidth(150);//Barcode
        tblBarang.getColumnModel().getColumn(2).setPreferredWidth(350);//Nama
        tblBarang.getColumnModel().getColumn(3).setPreferredWidth(150);//Satuan
        tblBarang.getColumnModel().getColumn(4).setPreferredWidth(100);//Stok
        tblBarang.getColumnModel().getColumn(5).setPreferredWidth(100);//Stok Gudang
        tblBarang.getColumnModel().getColumn(6).setPreferredWidth(100);//Harga Beli
        tblBarang.getColumnModel().getColumn(7).setPreferredWidth(100);//Harga Normal
        tblBarang.getColumnModel().getColumn(8).setPreferredWidth(100);//Harga Member
        tblBarang.getColumnModel().getColumn(9).setPreferredWidth(90);//Status Jual
    }
    private void isiTabelBarang() {
        barangs = FrameUtama.getMasterService().semuaBarang();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new BarangTabelModel(barangs));
        tblBarang.setRowSorter(sorter);
        tblBarang.setModel(new BarangTabelModel(barangs));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(barangs.size() + " Data Barang");
        idSelect = "";
    }
    private void loadFormToModel(Barang b) {
        barang.setPlu(b.getPlu());
        barang.setNamaBarang(b.getNamaBarang());
        barang.setBarcode1(b.getBarcode1());
        barang.setBarcode2(b.getBarcode2());
        barang.setIsiPembelian(b.getIsiPembelian());
        barang.setHargaBeli(b.getHargaBeli());
        barang.setHargaNormal(b.getHargaNormal());
        barang.setHargaMember(b.getHargaMember());
        barang.setStokToko(b.getStokToko());
        barang.setStokGudang(b.getStokGudang());
        barang.setStokMax(b.getStokMax());
        barang.setStokMin(b.getStokMin());

        barang.setGolonganBarang(b.getGolonganBarang());
        barang.setSatuan(b.getSatuan());
        barang.setSatuanPembelian(b.getSatuanPembelian());

        barang.setJual(b.getJual());
        barang.setLimitWarning(b.getLimitWarning());
    }
    private void cariSelect() {
        barang = new Barang();
        barang = FrameUtama.getMasterService().barangBerdasarkanId(idSelect);
    }
    private class BarangTabelModel extends AbstractTableModel {

        private final List<Barang> daftarProduk;

        public BarangTabelModel(List<Barang> daftarBarang) {
            this.daftarProduk = daftarBarang;
        }

        @Override
        public int getRowCount() {
            return daftarProduk.size();
        }

        @Override
        public int getColumnCount() {
            return 10;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "PLU";
                case 1: return "Barcode";
                case 2: return "Nama Barang";
                case 3: return "Satuan";
                case 4: return "Stok Toko";
                case 5: return "Stok Gudang";
                case 6: return "Harga Beli";
                case 7: return "Harga Normal";
                case 8: return "Harga Member";
                case 9: return "Status Jual";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Barang p = barangs.get(rowIndex);
            switch (colIndex) {
                case 0: return p.getPlu();
                case 1: return p.getBarcode1();
                case 2: return p.getNamaBarang();
                case 3: return p.getSatuan();
                case 4: return p.getStokToko();
                case 5: return p.getStokGudang();
                case 6: return p.getHargaBeli();
                case 7: return p.getHargaNormal();
                case 8: return p.getHargaMember();
                case 9: return p.getJual();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 3: return BigDecimal.class;
                case 4: return BigDecimal.class;
                case 5: return BigDecimal.class;
                case 6: return BigDecimal.class;
                case 7: return BigDecimal.class;
                case 8: return BigDecimal.class;
                case 9: return Boolean.class;
                default: return String.class;
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

        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelect();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Supplier", "Nama Supplier", "Alamat", "HP", "Telepon", "Saldo Hutang", "Email", "Kota", "Kode Pos"
            }
        ));
        jScrollPane1.setViewportView(tblBarang);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText(org.openide.util.NbBundle.getMessage(PilihBarangDialog.class, "PilihBarangDialog.lblJumlahData.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(421, 421, 421))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJumlahData)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void initListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, 
                        "Apakah Anda Yakin Ingin Membatalkan Pemilihan Barang", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    barang = null;
                    dispose();
                }
            }
        });
        tblBarang.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblBarang.getSelectedRow() >= 0) {
                idSelect = tblBarang.getValueAt(tblBarang.getSelectedRow(), 0).toString();
            }
        });
        tblBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    cariSelect();
                    if (barang == null) {
                        JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
                    } else {
                        cariSelect();
                        dispose();
                    }
                }
            }
        });
        toolbar.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolbar.getTxtCari().getText())) {
                    isiTabelBarang();
                } else {
                    barangs = FrameUtama.getMasterService().cariNamaBarang(toolbar.getTxtCari().getText());
                    tblBarang.setModel(new BarangTabelModel(barangs));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new BarangTabelModel(barangs));
                    tblBarang.setRowSorter(sorter);
                    ukuranTabelBarang();
                    int jml = barangs.size();
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
            isiTabelBarang();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
            isiTabelBarang();
            barang = null;
            title = "Tambah Data Barang";
            Barang s = new BarangDialog().showDialog(barang, title);
            barang = new Barang();
            if (s != null) {
                loadFormToModel(s);
                barang.setPlu("");
                FrameUtama.getMasterService().simpan(barang);
                isiTabelBarang();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            barang = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Barang";
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
            } else {
                cariSelect();
                Barang s = new BarangDialog().showDialog(barang, title);
                barang = new Barang();
                if (s != null) {
                    loadFormToModel(s);
                    FrameUtama.getMasterService().simpan(barang);
                    isiTabelBarang();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                    title = null;
                }
            }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (barang == null) {
                JOptionPane.showMessageDialog(null, "Data Barang Belum Terpilih");
            } else {
                FrameUtama.getMasterService().hapus(barang);
                isiTabelBarang();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
        toolbar.getBtnPilih().addActionListener(((ae) -> {
            cariSelect();
            if (barang == null) {
                JOptionPane.showMessageDialog(null, "Data Barang Belum Dipilih");
            } else {
                cariSelect();
                dispose();
            }
        }));
        toolbar.getBtnKeluar().addActionListener((ae) -> {
            String ObjButtons[] = {"Ya", "Tidak"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Ingin Membatalkan Pemilihan Barang", 
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    barang = null;
                    dispose();
                }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblBarang;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelect toolbar;
    // End of variables declaration//GEN-END:variables
}
