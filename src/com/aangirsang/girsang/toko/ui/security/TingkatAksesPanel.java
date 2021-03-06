/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.security;

import com.aangirsang.girsang.toko.Launcher;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter;
import com.aangirsang.girsang.toko.ui.security.dialog.DialogTingkatAkses;
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
public class TingkatAksesPanel extends javax.swing.JPanel {

    private List<TingkatAkses> tingkatAkseses;
    private TingkatAkses tingkatAkses;

    int IndexTab = 0;
    int aktifPanel = 0;
    String title, idSelect;

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
    public ToolbarTanpaFilter getToolbar() {
        return toolbar;
    }

    public TingkatAksesPanel() {
        initComponents();
        initListener();
        tabel.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tabel.setDefaultRenderer(Date.class, new DateRenderer());
        tabel.setDefaultRenderer(Integer.class, new IntegerRenderer());
        isiTabelKategori();
    }
    private void ukuranTabel() {
        tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabel.getColumnModel().getColumn(0).setPreferredWidth(100);//ID Tingkat Akses
        tabel.getColumnModel().getColumn(1).setPreferredWidth(150);//Nama Tingkat Akses
        tabel.getColumnModel().getColumn(2).setPreferredWidth(350);//Keterangan
    }
    private void isiTabelKategori() {
        tingkatAkseses = Launcher.getSecurityService().semuaTingkatAkses();
        RowSorter<TableModel> sorter = new TableRowSorter<>(new TabelModel(tingkatAkseses));
        tabel.setRowSorter(sorter);
        tabel.setModel(new TabelModel(tingkatAkseses));
        toolbar.getTxtCari().setText("");
        ukuranTabel();
        lblJumlahData.setText(tingkatAkseses.size() + " Data Pembelian");
        idSelect = "";
    }
    private void cariSelect() {
        tingkatAkses = new TingkatAkses();
        tingkatAkses = Launcher.getSecurityService().cariIdTingkatAkses(idSelect);
    }
    private class TabelModel extends AbstractTableModel {
        private final List<TingkatAkses> daftarTingkatAkses;
        public TabelModel(List<TingkatAkses> daftarTingkatAkses) {
            this.daftarTingkatAkses = daftarTingkatAkses;
        }
        @Override
        public int getRowCount() {
            return daftarTingkatAkses.size();
        }
        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0:return "ID Tingkat Akses";
                case 1:return "Tingkat Akses";
                case 2:return "Keterangan";
                default:return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            TingkatAkses t = tingkatAkseses.get(rowIndex);
            switch (colIndex) {
                case 0:return t.getId();
                case 1:return t.getNamaTingkatAkses();
                case 2:return t.getKetTingkatAkses();
                default:return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                default:return String.class;
            }
        }
    }
    private void exportExcel(List<Barang> dataList) throws IOException {
        if (dataList != null && !dataList.isEmpty()) {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet sheet = workBook.createSheet();
            HSSFSheet worksheet = workBook.createSheet("Sheet 0");
            // Nama Field
            Row judul = sheet.createRow((short) 0);
            Cell cell = judul.createCell((short) 0);
            cell.setCellValue("This is a test of merging");
            HSSFRow headingRow = sheet.createRow((short) 2);
            headingRow.createCell((short) 0).setCellValue("ID");
            headingRow.createCell((short) 1).setCellValue("BARCODE 1");
            headingRow.createCell((short) 2).setCellValue("BARCODE 2");
            headingRow.createCell((short) 3).setCellValue("NAMA BARANG");
            headingRow.createCell((short) 4).setCellValue("GOLONGAN");
            headingRow.createCell((short) 5).setCellValue("SAT. JUAL");
            headingRow.createCell((short) 6).setCellValue("ST. TOKO");
            headingRow.createCell((short) 7).setCellValue("ST. GUDANG");
            headingRow.createCell((short) 8).setCellValue("SAT. BELI");
            headingRow.createCell((short) 9).setCellValue("ISI PEM.");
            headingRow.createCell((short) 10).setCellValue("HRG PEM.");
            headingRow.createCell((short) 11).setCellValue("HRG NORMAL");
            headingRow.createCell((short) 12).setCellValue("HRG MEMBER");
            headingRow.createCell((short) 13).setCellValue("JUAL");
            int panjang = headingRow.getLastCellNum() - 1;
            short rowNo = 3;

            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    panjang //last column  (0-based)
            ));
            CellStyle styleData = workBook.createCellStyle();
            styleData.setBorderBottom(CellStyle.BORDER_THIN);
            styleData.setBorderRight(CellStyle.BORDER_THIN);
            styleData.setBorderLeft(CellStyle.BORDER_THIN);
            for (Barang b : dataList) {
                HSSFRow row = sheet.createRow(rowNo);
                String jual;
                if (b.getJual() == true) {
                    jual = "Jual";
                } else {
                    jual = "Tidak";
                }
                row.createCell((short) 0).setCellValue(b.getPlu());
                row.createCell((short) 1).setCellValue(b.getBarcode1());
                row.createCell((short) 2).setCellValue(b.getBarcode2());
                row.createCell((short) 3).setCellValue(b.getNamaBarang());
                row.createCell((short) 4).setCellValue(b.getGolonganBarang().getGolonganBarang());
                row.createCell((short) 5).setCellValue(b.getSatuan());
                row.createCell((short) 6).setCellValue(b.getStokToko());
                row.createCell((short) 7).setCellValue(b.getStokGudang());
                row.createCell((short) 8).setCellValue(b.getSatuanPembelian());
                row.createCell((short) 9).setCellValue(b.getIsiPembelian());
                row.createCell((short) 10).setCellValue(TextComponentUtils.formatNumber(b.getHargaBeli()));
                row.createCell((short) 11).setCellValue(TextComponentUtils.formatNumber(b.getHargaNormal()));
                row.createCell((short) 12).setCellValue(TextComponentUtils.formatNumber(b.getHargaMember()));
                row.createCell((short) 13).setCellValue(jual);
                for (int i = 0; i <= 13; i++) {
                    row.getCell((short) i).setCellStyle(styleData);
                }
                rowNo++;
            }
            for (int i = 0; i <= 13; i++) {
                sheet.autoSizeColumn(i);
            }
            Font font = workBook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            //style judul
            CellStyle styleTitle = workBook.createCellStyle();
            styleTitle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
            styleTitle.setFont(font);
            judul.getCell(0).setCellStyle(styleTitle);

            //judul field
            CellStyle styleHeading = workBook.createCellStyle();
            styleHeading.setFont(font);
            styleHeading.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
            styleHeading.setBorderBottom(CellStyle.BORDER_THIN);
            styleHeading.setBorderTop(CellStyle.BORDER_THIN);
            styleHeading.setBorderRight(CellStyle.BORDER_THIN);
            styleHeading.setBorderLeft(CellStyle.BORDER_THIN);
            for (int i = 0; i < headingRow.getLastCellNum(); i++) {//For each cell in the row 
                headingRow.getCell(i).setCellStyle(styleHeading);//Set the style
            }
            String file = "D:/Student_detais.xls";
            try {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workBook.write(fos);
                }
                JOptionPane.showMessageDialog(null, "Sukses");
            } catch (FileNotFoundException e) {
                System.out.println("Invalid directory or file not found");
            } catch (IOException e) {
                System.out.println("Error occurred while writting excel file to directory");
            }
        }
    }
    private void loadFormToModel(TingkatAkses t) {
        tingkatAkses = t;
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
        tabel = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/user Level 64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Data Tingkat Akses Pengguna");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data tingkat Akses pengguna");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabel);

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblJumlahData, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 421, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(5, 5, 5))
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
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
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListener(){
        tabel.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tabel.getSelectedRow() >= 0) {
                idSelect = tabel.getValueAt(tabel.getSelectedRow(), 0).toString();
            }
        });
        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    title = "Edit Data Tingkat Akses";
                    if ("".equals(idSelect)) {
                        JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
                    } else {
                        cariSelect();
                        TingkatAkses t = new DialogTingkatAkses().showDialog(tingkatAkses, title);
                        tingkatAkses = new TingkatAkses();
                        if (t != null) {
                            loadFormToModel(t);
                            Launcher.getSecurityService().simpan(t);
                            isiTabelKategori();
                            JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                                    "Penyimpanan Sukses",
                                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            title = null;
                        }
                    }
                }
            }
        });
        toolbar.getBtnBaru().addActionListener((ae) -> {
            tingkatAkses = null;
            title = "Tambah Data Tingkat Akses";
            TingkatAkses t = new DialogTingkatAkses().showDialog(tingkatAkses, title);
            tingkatAkses = new TingkatAkses();
            if(t!=null){
                loadFormToModel(t);
                tingkatAkses.setId("");
                Launcher.getSecurityService().simpan(tingkatAkses);
                isiTabelKategori();
                JOptionPane.showMessageDialog(FrameUtama.getInstance(),
                        "Penyimpanan Sukses",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                title = null;
            }
        });
        toolbar.getBtnEdit().addActionListener((ae) -> {
            title = "Edit Data Tingkat Akses";
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
            } else {
                cariSelect();
                TingkatAkses t = new DialogTingkatAkses().showDialog(tingkatAkses, title);
                tingkatAkses = new TingkatAkses();
                if (t != null) {
                    loadFormToModel(t);
                    Launcher.getSecurityService().simpan(t);
                    isiTabelKategori();
                    JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                            "Penyimpanan Sukses",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    title = null;
                }
            }
        });
        toolbar.getBtnHapus().addActionListener((ae) -> {
            if ("".equals(idSelect)) {
                JOptionPane.showMessageDialog(null, "Data Tingkat Akses Belum Terpilih");
            } else {
                cariSelect();
                Launcher.getSecurityService().hapus(tingkatAkses);
                isiTabelKategori();
                JOptionPane.showMessageDialog(FrameUtama.getInstance(), 
                        "Hapus Sukses",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tabel;
    private com.aangirsang.girsang.toko.toolbar.ToolbarTanpaFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
