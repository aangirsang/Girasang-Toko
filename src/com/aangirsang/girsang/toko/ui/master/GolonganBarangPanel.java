/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master;

import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter;
import com.aangirsang.girsang.toko.ui.master.dialog.GolonganBarangDialog;
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
public class GolonganBarangPanel extends javax.swing.JPanel {

    private List<GolonganBarang> golonganBarangs;
    private GolonganBarang golonganBarang;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect = "";

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
        return toolbar;
    }

    /**
     * Creates new form KategoriPanel
     */
    public GolonganBarangPanel() {
        initComponents();
        initListener();
        isiTabelKategori();
    }

    private void ukuranTabelBarang() {
        tbGolonganBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tbGolonganBarang.getColumnModel().getColumn(0).setPreferredWidth(120);//ID
        tbGolonganBarang.getColumnModel().getColumn(1).setPreferredWidth(350);//Kategori
    }

    private void isiTabelKategori() {
        golonganBarangs = FrameUtama.getMasterService().golonganBarangAsc();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(golonganBarangs));
        tbGolonganBarang.setRowSorter(sorter);
        tbGolonganBarang.setModel(new kategoriTabelModel(golonganBarangs));
        toolbar.getTxtCari().setText("");
        ukuranTabelBarang();
        lblJumlahData.setText(golonganBarangs.size() + " Data Golongan Barang");
        idSelect = "";
    }

    private class kategoriTabelModel extends AbstractTableModel {

        private final List<GolonganBarang> listKategori;
        String columnNames[] = {"ID Golongan", "Golongan Barang"};

        public kategoriTabelModel(List<GolonganBarang> listKategori) {
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
            GolonganBarang p = listKategori.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getId();
                case 1:
                    return p.getGolonganBarang();
                default:
                    return "";
            }
        }

    }

    private void cariSelect() {
        golonganBarang = new GolonganBarang();
        golonganBarang = FrameUtama.getMasterService().golonganBarangBerdasarkanId(idSelect);
    }

    private void initListener() {
        tbGolonganBarang.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tbGolonganBarang.getSelectedRow() >= 0) {
                idSelect = tbGolonganBarang.getValueAt(tbGolonganBarang.getSelectedRow(), 0).toString();
            }
        });
        tbGolonganBarang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Golongan";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
                    } else {
                        cariSelect();
                        GolonganBarang g = new GolonganBarangDialog().showDialog(golonganBarang, title);
                        golonganBarang = new GolonganBarang();
                        if (g != null) {
                            golonganBarang.setId(g.getId());
                            golonganBarang.setGolonganBarang(g.getGolonganBarang());

                            FrameUtama.getMasterService().simpan(golonganBarang);
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
                    golonganBarangs = FrameUtama.getMasterService().cariNamaGolonganBarang(toolbar.getTxtCari().getText());
                    tbGolonganBarang.setModel(new kategoriTabelModel(golonganBarangs));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new kategoriTabelModel(golonganBarangs));
                    tbGolonganBarang.setRowSorter(sorter);
                    ukuranTabelBarang();
                    int jml = golonganBarangs.size();
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
            golonganBarang = null;
            title = "Tambah Data Golongan";
            GolonganBarang k = new GolonganBarangDialog().showDialog(golonganBarang, title);
            golonganBarang = new GolonganBarang();
            if (k != null) {
                golonganBarang.setGolonganBarang(k.getGolonganBarang());

                FrameUtama.getMasterService().simpan(golonganBarang);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                title = null;
            }
            golonganBarang = null;
        });

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
            title = "Edit Data Golongan";
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                cariSelect();
                GolonganBarang g = new GolonganBarangDialog().showDialog(golonganBarang, title);
                golonganBarang = new GolonganBarang();
                if (g != null) {
                    golonganBarang.setId(g.getId());
                    golonganBarang.setGolonganBarang(g.getGolonganBarang());

                    FrameUtama.getMasterService().simpan(golonganBarang);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                    title = null;
                }
            }
        });

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (golonganBarang == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                FrameUtama.getMasterService().hapus(golonganBarang);
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
        tbGolonganBarang = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/GolonganBarang 63X63.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Golongan Produk");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data golongan produk");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbGolonganBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Golongan", "Golongan Produk"
            }
        ));
        jScrollPane1.setViewportView(tbGolonganBarang);
        if (tbGolonganBarang.getColumnModel().getColumnCount() > 0) {
            tbGolonganBarang.getColumnModel().getColumn(1).setResizable(false);
        }

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1)
                .addGap(5, 5, 5))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
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
    private javax.swing.JTable tbGolonganBarang;
    private com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
