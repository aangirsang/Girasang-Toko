/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.supplier;

import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.toolbar.ToolBarSelect;
import com.aangirsang.girsang.toko.ui.master.supplier.SupplierPanel;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * @author ITSUSAHBRO
 */
public class PilihSupplierDialog extends javax.swing.JDialog {

    private List datas;
    private Object selectedProduk;
    private boolean isOk = false;

    private List<Supplier> suppliers;
    private Supplier supplier;
    private String id, title;

    public PilihSupplierDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        isiTabelKategori();
        TextComponentUtils.setAutoUpperCaseText(50, toolbar.getTxtCari());

        tblSupplier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    public Object showDialog(String judul) {
        pack();
        setTitle(judul);
        setLocationRelativeTo(null);
        setVisible(true);
        return supplier;
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
        RowSorter<TableModel> sorter = new TableRowSorter<>(new SupplierTabelModel(suppliers));
        tblSupplier.setRowSorter(sorter);
        tblSupplier.setModel(new SupplierTabelModel(suppliers));
        toolbar.getTxtCari().setText("");
        ukuranTabelProduk();
        lblJumlahData1.setText(suppliers.size() + " Data Supplier");
    }
    private void loadFormToModel(Supplier s) {
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
    public class SupplierTabelModel extends AbstractTableModel {

        private final List<Supplier> listKategori;
        String columnNames[]
                = {"Kode Supplier",
                    "Nama Supplier",
                    "Alamat",
                    "HP",
                    "Telepon",
                    "Saldo Hutang",
                    "Email",
                    "Kota",
                    "Kode POS"};

        public SupplierTabelModel(List<Supplier> listKategori) {
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
            switch (columnIndex) {
                case 5:
                    return BigDecimal.class;
                default:
                    return String.class;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolBarSelect();
        lblJumlahData1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSupplier.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblSupplier);

        lblJumlahData1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData1.setText(org.openide.util.NbBundle.getMessage(PilihSupplierDialog.class, "PilihSupplierDialog.lblJumlahData1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(toolbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblJumlahData1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblJumlahData1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void initListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, 
                        "Apakah Anda Yakin Ingin Membatalkan Pemilihan Supplier", 
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    supplier = null;
                    dispose();
                }
            }
        });
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
                    if (supplier == null) {
                        JOptionPane.showMessageDialog(null, "Supplier Belum Dipilih");
                    } else {
                        dispose();
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
                    suppliers = FrameUtama.getMasterService().cariNamaSupplier(toolbar.getTxtCari().getText());
                    tblSupplier.setModel(new SupplierTabelModel(suppliers));
                    RowSorter<TableModel> sorter = new TableRowSorter<>(new SupplierTabelModel(suppliers));
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

        toolbar.getBtnRefresh().addActionListener((ActionEvent ae) -> {
            isiTabelKategori();
        });

        toolbar.getBtnBaru().addActionListener((ActionEvent ae) -> {
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

        toolbar.getBtnEdit().addActionListener((ActionEvent ae) -> {
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

        toolbar.getBtnHapus().addActionListener((ActionEvent ae) -> {
            if (supplier == null) {
                JOptionPane.showMessageDialog(null, "Data Supplier Belum Terpilih");
            } else {
                FrameUtama.getMasterService().hapus(supplier);
                isiTabelKategori();
                JOptionPane.showMessageDialog(null, "Hapus Data Berhasil");
            }
        });
        toolbar.getBtnPilih().addActionListener((ActionEvent ae) -> {
            if (supplier == null) {
                JOptionPane.showMessageDialog(null, "Supplier Belum Dipilih");
            } else {
                dispose();
            }
        });
        toolbar.getBtnKeluar().addActionListener(((ae) -> {
            String ObjButtons[] = {"Ya", "Tidak"};
            int PromptResult = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Ingin Membatalkan Pemilihan Supplier", 
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    supplier = null;
                    dispose();
                }
        }));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData1;
    private javax.swing.JTable tblSupplier;
    private com.aangirsang.girsang.toko.toolbar.ToolBarSelect toolbar;
    // End of variables declaration//GEN-END:variables
}
