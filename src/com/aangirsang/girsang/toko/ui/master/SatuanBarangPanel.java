/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master;

import com.aangirsang.girsang.toko.Launcher;
import com.aangirsang.girsang.toko.model.master.SatuanBarang;
import com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter;
import com.aangirsang.girsang.toko.ui.master.dialog.SatuanBarangDialog;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class SatuanBarangPanel extends javax.swing.JPanel {

    private List<SatuanBarang> satuanBarangs;
    private SatuanBarang satuanBarang;

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
    public SatuanBarangPanel() {
        initComponents();
        initListener();
        isiTabelKategori();
        tblKategori.setFillsViewportHeight(true);
    }

    private void ukuranTabelBarang() {
        tblKategori.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblKategori.getColumnModel().getColumn(0).setPreferredWidth(120);//ID
        tblKategori.getColumnModel().getColumn(1).setPreferredWidth(350);//Kategori
    }

    private void isiTabelKategori() {
        satuanBarangs = Launcher.getMasterService().satuanBarangAsc();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(satuanBarangs));
        tblKategori.setRowSorter(sorter);
        tblKategori.setModel(new kategoriTabelModel(satuanBarangs));
        toolbarTanpaFilter1.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(satuanBarangs.size() + " Data Satuan Barang");
    }

    private class kategoriTabelModel extends AbstractTableModel {

        private final List<SatuanBarang> listKategori;
        String columnNames[] = {"ID Satuan", "Satuan Barang"};

        public kategoriTabelModel(List<SatuanBarang> listKategori) {
            this.listKategori = listKategori;
        }

        @Override
        public int getRowCount() {
            return listKategori.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SatuanBarang p = listKategori.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getId();
                case 1:
                    return p.getSatuanBarang().toUpperCase();
                default:
                    return "";
            }
        }
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return col >= 1;
        }
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            SatuanBarang p = listKategori.get(rowIndex);
            switch (columnIndex) {
            case 1:
                p.setSatuanBarang((String) aValue);
                fireTableCellUpdated(rowIndex, 1); // Total may also have changed
                Launcher.getMasterService().simpan(p);
                break;
            }
        }
        private SatuanBarang getValueAt(int row) {
            return listKategori.get(row);
        }

    }
    private void initListener() {
        tblKategori.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblKategori.getSelectedRow() >= 0) {
                String id = tblKategori.getValueAt(tblKategori.getSelectedRow(), 0).toString();
                satuanBarang = new SatuanBarang();
                satuanBarang = Launcher.getMasterService().satuanBarangBerdasarkanId(id);
            }
        });
        tblKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Satuan";
                    if (satuanBarang == null) {
                        JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
                    } else {
                        SatuanBarang k = new SatuanBarangDialog().showDialog(satuanBarang, title);
                        satuanBarang = new SatuanBarang();
                        if (k != null) {
                            satuanBarang.setId(k.getId());
                            satuanBarang.setSatuanBarang(k.getSatuanBarang());

                            Launcher.getMasterService().simpan(satuanBarang);
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
                    satuanBarangs = Launcher.getMasterService().cariNamaSatuanBarang(toolbarTanpaFilter1.getTxtCari().getText());
                    tblKategori.setModel(new kategoriTabelModel(satuanBarangs));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(satuanBarangs));
                    tblKategori.setRowSorter(sorter);
                    ukuranTabelBarang();
                    int jml = satuanBarangs.size();
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
            satuanBarang = null;
            title = "Tambah Data Satuan";
            SatuanBarang k = new SatuanBarangDialog().showDialog(satuanBarang, title);
            satuanBarang = new SatuanBarang();
            if (k != null) {
                satuanBarang.setSatuanBarang(k.getSatuanBarang());

                Launcher.getMasterService().simpan(satuanBarang);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            satuanBarang = null;
        });

        toolbarTanpaFilter1.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Satuan";
            if (satuanBarang == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                SatuanBarang k = new SatuanBarangDialog().showDialog(satuanBarang, title);
                satuanBarang = new SatuanBarang();
                if (k != null) {
                    satuanBarang.setId(k.getId());
                    satuanBarang.setSatuanBarang(k.getSatuanBarang());

                    Launcher.getMasterService().simpan(satuanBarang);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                    title = null;
                }
            }
        });

        toolbarTanpaFilter1.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (satuanBarang == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                Launcher.getMasterService().hapus(satuanBarang);
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
        tblKategori = new javax.swing.JTable();
        toolbarTanpaFilter1 = new com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter();
        lblJumlahData = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Satuan 64X64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Satuan Barang");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data satuan Barang");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblKategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Golongan", "Golongan Produk"
            }
        ));
        jScrollPane1.setViewportView(tblKategori);
        if (tblKategori.getColumnModel().getColumnCount() > 0) {
            tblKategori.getColumnModel().getColumn(1).setResizable(false);
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
    private javax.swing.JTable tblKategori;
    private com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter toolbarTanpaFilter1;
    // End of variables declaration//GEN-END:variables
}
