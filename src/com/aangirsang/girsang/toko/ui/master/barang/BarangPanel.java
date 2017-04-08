/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.barang;

import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
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
import org.openide.util.Exceptions;

/**
 *
 * @author ITSUSAHBRO
 */
public class BarangPanel extends javax.swing.JPanel {

    private List<Barang> barangs;
    private Barang barang;

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
    /**
     * Creates new form KategoriPanel
     */
    public BarangPanel() {
        initComponents();
        initListener();
        tblBarang.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        isiTabelBarang();
    }
    private void ukuranTabelBarang() {
        tblBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBarang.getColumnModel().getColumn(0).setPreferredWidth(100);//PLU
        tblBarang.getColumnModel().getColumn(1).setPreferredWidth(350);//Nama
        tblBarang.getColumnModel().getColumn(2).setPreferredWidth(150);//Satuan
        tblBarang.getColumnModel().getColumn(3).setPreferredWidth(150);//Stok Toko
        tblBarang.getColumnModel().getColumn(4).setPreferredWidth(100);//Stok Gudang
        tblBarang.getColumnModel().getColumn(5).setPreferredWidth(150);//Barcode
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
    private void exportExcel(List<Barang> dataList) throws IOException{
        if(dataList != null && !dataList.isEmpty()){
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet sheet = workBook.createSheet();
            HSSFSheet worksheet = workBook.createSheet("Sheet 0");
            // Nama Field
            Row judul = sheet.createRow((short) 0);
            Cell cell = judul.createCell((short) 0);
            cell.setCellValue("This is a test of merging");
            HSSFRow headingRow = sheet.createRow((short)2);
            headingRow.createCell((short)0).setCellValue("ID");
            headingRow.createCell((short)1).setCellValue("BARCODE 1");
            headingRow.createCell((short)2).setCellValue("BARCODE 2");
            headingRow.createCell((short)3).setCellValue("NAMA BARANG");
            headingRow.createCell((short)4).setCellValue("GOLONGAN");
            headingRow.createCell((short)5).setCellValue("SAT. JUAL");
            headingRow.createCell((short)6).setCellValue("ST. TOKO");
            headingRow.createCell((short)7).setCellValue("ST. GUDANG");
            headingRow.createCell((short)8).setCellValue("SAT. BELI");
            headingRow.createCell((short)9).setCellValue("ISI PEM.");
            headingRow.createCell((short)10).setCellValue("HRG PEM.");
            headingRow.createCell((short)11).setCellValue("HRG NORMAL");
            headingRow.createCell((short)12).setCellValue("HRG MEMBER");
            headingRow.createCell((short)13).setCellValue("JUAL");
            int panjang = headingRow.getLastCellNum() - 1;
            short rowNo = 3;
 
            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    panjang  //last column  (0-based)
            ));
            CellStyle styleData = workBook.createCellStyle();
            styleData.setBorderBottom(CellStyle.BORDER_THIN);
            styleData.setBorderRight(CellStyle.BORDER_THIN);
            styleData.setBorderLeft(CellStyle.BORDER_THIN);
            for (Barang b : dataList) {
                HSSFRow row = sheet.createRow(rowNo);
                String jual;
                if(b.getJual()==true){
                    jual = "Jual";
                }else{
                    jual = "Tidak";
                }
                row.createCell((short)0).setCellValue(b.getPlu());
                row.createCell((short)1).setCellValue(b.getBarcode1());
                row.createCell((short)2).setCellValue(b.getBarcode2());
                row.createCell((short)3).setCellValue(b.getNamaBarang());
                row.createCell((short)4).setCellValue(b.getGolonganBarang().getGolonganBarang());
                row.createCell((short)5).setCellValue(b.getSatuan());
                row.createCell((short)6).setCellValue(b.getStokToko());
                row.createCell((short)7).setCellValue(b.getStokGudang());
                row.createCell((short)8).setCellValue(b.getSatuanPembelian());
                row.createCell((short)9).setCellValue(b.getIsiPembelian());
                row.createCell((short)10).setCellValue(TextComponentUtils.formatNumber(b.getHargaBeli()));
                row.createCell((short)11).setCellValue(TextComponentUtils.formatNumber(b.getHargaNormal()));
                row.createCell((short)12).setCellValue(TextComponentUtils.formatNumber(b.getHargaMember()));
                row.createCell((short)13).setCellValue(jual);
                for(int i = 0;i<=13;i++){
                    row.getCell((short)i).setCellStyle(styleData);
                }
                rowNo++;
            }
            for(int i = 0;i<=13;i++){
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
            for(int i = 0; i < headingRow.getLastCellNum(); i++){//For each cell in the row 
                headingRow.getCell(i).setCellStyle(styleHeading);//Set the style
            }
            String file = "D:/Student_detais.xls";
            try{
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workBook.write(fos);
                }
                JOptionPane.showMessageDialog(null, "Sukses");
            }catch(FileNotFoundException e){
                System.out.println("Invalid directory or file not found");
            }catch(IOException e){
                System.out.println("Error occurred while writting excel file to directory");
            }
        }
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
        
        barang.setStokPembelianToko(b.getStokPembelianToko());
        barang.setStokPembelianGudang(b.getStokPembelianGudang());
        barang.setStokPenjualanToko(b.getStokPenjualanToko());
        barang.setStokPenjualanGudang(b.getStokPenjualanGudang());
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
                case 1: return "Nama Barang";
                case 2: return "Satuan";
                case 3: return "Stok Toko";
                case 4: return "Stok Gudang";
                case 5: return "Barcode";
                case 6: return "Harga Beli";
                case 7: return "Harga Normal";
                case 8: return "Harga Member";
                case 9: return "Status Jual";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            Barang b = barangs.get(rowIndex);
            switch (colIndex) {
                case 0: return b.getPlu();
                case 1: return b.getNamaBarang();
                case 2: return b.getSatuan();
                case 3: return b.getKalkulasiStokToko();
                case 4: return b.getKalkulasiStokGudang();
                case 5: 
                    if(b.getBarcode1()!=null){
                        return b.getBarcode1();
                    } else {
                        return "-";
                    }
                case 6: return b.getHargaBeli();
                case 7: return b.getHargaNormal();
                case 8: return b.getHargaMember();
                case 9: return b.getJual();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 3: return BigDecimal.class;
                case 4: return BigDecimal.class;
                case 2: return BigDecimal.class;
                case 6: return BigDecimal.class;
                case 7: return BigDecimal.class;
                case 8: return BigDecimal.class;
                case 9: return Boolean.class;
                default: return String.class;
            }
        }
    }
    private void initListener() {
        tblBarang.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblBarang.getSelectedRow() >= 0) {
                idSelect = tblBarang.getValueAt(tblBarang.getSelectedRow(), 0).toString();
            }
        });
        tblBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
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
        toolbar.getBtnFilter().addActionListener((ActionEvent ae) ->{
            List <Barang> list = new FilterBarang().showDialog();
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
        tblBarang = new javax.swing.JTable();
        lblJumlahData = new javax.swing.JLabel();
        toolbar = new com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter();
        btnExcell = new javax.swing.JButton();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Barang 63X63.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setText("Daftar Barang");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel3.setText("Disini anda bisa menambah, mengedit atau menghapus data Barang");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Supplier", "Nama Supplier", "Alamat", "HP", "Telepon", "Saldo Hutang", "Email", "Kota", "Kode Pos"
            }
        ));
        jScrollPane1.setViewportView(tblBarang);
        if (tblBarang.getColumnModel().getColumnCount() > 0) {
            tblBarang.getColumnModel().getColumn(0).setResizable(false);
            tblBarang.getColumnModel().getColumn(1).setResizable(false);
        }

        lblJumlahData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblJumlahData.setText("jLabel4");

        btnExcell.setText("Export to Excel");
        btnExcell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcellActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcell, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcell)
                    .addComponent(lblJumlahData))
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

    private void btnExcellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcellActionPerformed
        BarangPanel exporter = new BarangPanel();
        barangs = FrameUtama.getMasterService().semuaBarang();
        try {
            exporter.exportExcel(barangs);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_btnExcellActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcell;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJumlahData;
    private javax.swing.JTable tblBarang;
    private com.aangirsang.girsang.toko.toolbar.ToolbarDenganFilter toolbar;
    // End of variables declaration//GEN-END:variables
}
