/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.dialog;

import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import com.aangirsang.girsang.toko.toolbar.ToolBarSelect;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ITSUSAHBRO
 */
public class PilihGolonganBarangDialog extends javax.swing.JDialog {

    private List datas;
    private Object selectedProduk;
    private boolean isOk = false;

    private List<GolonganBarang> golonganBarangs;
    private GolonganBarang golonganBarang;
    private String id;
    ToolBarSelect toolBar = new ToolBarSelect();

    /**
     * Creates new form KategoriProdukDialogPanel
     */
    public PilihGolonganBarangDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        isiTabelKategori();
        TextComponentUtils.setAutoUpperCaseText(50, toolBarSelect1.getTxtCari());

        tblGolonganBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return golonganBarang;
    }

    private void isiTabelKategori() {
        golonganBarangs = FrameUtama.getMasterService().semuaGolonganBarang();
        tblGolonganBarang.setModel(new kategoriTabelModel(golonganBarangs));
        toolBarSelect1.getTxtCari().setText("");
        tblGolonganBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblGolonganBarang.getColumnModel().getColumn(0).setPreferredWidth(120);//ID
        tblGolonganBarang.getColumnModel().getColumn(1).setPreferredWidth(350);//Kategori
        id = null;
    }

    private class kategoriTabelModel extends AbstractTableModel {

        private final List<GolonganBarang> listGolonganBarang;
        String columnNames[] = {"ID Golongan", "Golongan Barang"};

        public kategoriTabelModel(List<GolonganBarang> listGolonganBarang) {
            this.listGolonganBarang = listGolonganBarang;
        }

        @Override
        public int getRowCount() {
            return listGolonganBarang.size();
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
            GolonganBarang g = golonganBarangs.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return g.getId();
                case 1:
                    return g.getGolonganBarang();
                default:
                    return "";
            }
        }

    }

    private void initListener() {
        tblGolonganBarang.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblGolonganBarang.getSelectedRow() >= 0) {
                id = (String) tblGolonganBarang.getValueAt(tblGolonganBarang.getSelectedRow(), 0);
            }
        });
        tblGolonganBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mE) {
                if (mE.getClickCount() == 2) {
                    if (id == null) {
                        JOptionPane.showMessageDialog(null, "Kategori Belum Dipilih");
                    } else {
                        golonganBarang = FrameUtama.getMasterService().golonganBarangBerdasarkanId(id);
                        dispose();

                    }
                }
            }
        });
        toolBarSelect1.getTxtCari().addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ("".equals(toolBarSelect1.getTxtCari().getText())) {
                    isiTabelKategori();
                } else {
                    golonganBarangs = FrameUtama.getMasterService().cariNamaGolonganBarang(toolBarSelect1.getTxtCari().getText());
                    tblGolonganBarang.setModel(new kategoriTabelModel(golonganBarangs));
                    tblGolonganBarang.getColumnModel().getColumn(0).setPreferredWidth(220);
                    tblGolonganBarang.getColumnModel().getColumn(1).setPreferredWidth(520);
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }
        });

        toolBarSelect1.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolBarSelect1.getBtnPilih().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Kategori Belum Dipilih");
            } else {
                golonganBarang = FrameUtama.getMasterService().golonganBarangBerdasarkanId(id);
                dispose();

            }
        });

        toolBarSelect1.getBtnBaru().addActionListener((ActionEvent ae) -> {
            golonganBarang = null;
            String judul = "Penambahan Data";
            GolonganBarang g = new GolonganBarangDialog().showDialog(golonganBarang, judul);
            golonganBarang = new GolonganBarang();
            if (g != null) {
                golonganBarang.setId(g.getId());
                golonganBarang.setGolonganBarang(g.getGolonganBarang());

                FrameUtama.getMasterService().simpan(golonganBarang);
                isiTabelKategori();
            }
        });

        toolBarSelect1.getBtnEdit().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                golonganBarang = FrameUtama.getMasterService().golonganBarangBerdasarkanId(id);
                String judul = "Penambahan Data";
                GolonganBarang g = new GolonganBarangDialog().showDialog(golonganBarang, judul);
                golonganBarang = new GolonganBarang();
                if (g != null) {
                    golonganBarang.setId(g.getId());
                    golonganBarang.setGolonganBarang(g.getGolonganBarang());

                    FrameUtama.getMasterService().simpan(golonganBarang);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
                }

            }
        });

        toolBarSelect1.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (id == null) {
                JOptionPane.showMessageDialog(null, "Data Kategori Belum Terpilih");
            } else {
                golonganBarang = FrameUtama.getMasterService().golonganBarangBerdasarkanId(id);
                FrameUtama.getMasterService().hapus(golonganBarang);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
        toolBarSelect1.getBtnKeluar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                golonganBarang = null;
                dispose();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblGolonganBarang = new javax.swing.JTable();
        toolBarSelect1 = new com.aangirsang.girsang.toko.toolbar.ToolBarSelect();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblGolonganBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Kategori", "Kategori"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGolonganBarang.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblGolonganBarang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(toolBarSelect1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBarSelect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGolonganBarang;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelect toolBarSelect1;
    // End of variables declaration//GEN-END:variables
}
