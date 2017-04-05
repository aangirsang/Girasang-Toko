/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.ui.master.barang;

import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import com.aangirsang.girsang.toko.model.master.HPPBarang;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import com.aangirsang.girsang.toko.ui.master.dialog.PilihGolonganBarangDialog;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.aangirsang.girsang.toko.util.BigDecimalRenderer;
import com.aangirsang.girsang.toko.util.DateRenderer;
import com.aangirsang.girsang.toko.util.IntegerRenderer;
import com.aangirsang.girsang.toko.util.Perhitungan;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author GIRSANG PC
 */

public class BarangDialog extends javax.swing.JDialog {

    private Barang barang;
    private List<GolonganBarang> golonganBarangs;
    private List<PembelianDetail> listHistory;
    private List<HPPBarang> listHPP;
    private HPPBarang hPPBarang;
    private GolonganBarang golonganBarang;
    int stokToko, stokPembelianToko, stokPenjualanToko = 0;
    int stokGudang, stokPembelianGudang, stokPenjualanGudang = 0;
    
    public void getTxtStokToko(String sT){
        txtStokOpname.setText(sT);
    }

    public void getTxtStokGudang(String sG){
        txtStokGudang.setText(sG);
    }
    
    public BarangDialog() {
        super(FrameUtama.getInstance(), true);
        initComponents();
        initListener();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        tblHistoryPembelian.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblHistoryPembelian.setDefaultRenderer(Date.class, new DateRenderer());
        tblHistoryPembelian.setDefaultRenderer(Integer.class, new IntegerRenderer());
        tblHPP.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());
        tblHPP.setDefaultRenderer(Date.class, new DateRenderer());
        tblHPP.setDefaultRenderer(Integer.class, new IntegerRenderer());
        TextComponentUtils.setAutoUpperCaseText(100, txtNamaBarang);
        TextComponentUtils.setNumericTextOnly(txtStokGudang);
        TextComponentUtils.setNumericTextOnly(txtStokOpname);
        TextComponentUtils.setNumericTextOnly(txtStokMax);
        TextComponentUtils.setNumericTextOnly(txtStokMin);
        TextComponentUtils.setNumericTextOnly(txtIsi);
        TextComponentUtils.setNumericTextOnly(txtHargaMemberPersen);
        TextComponentUtils.setNumericTextOnly(txtHargaTokoPersen);
        TextComponentUtils.setNumericTextOnly(txtIsiHpp);
        TextComponentUtils.setCurrency(txtHargaBeli);
        TextComponentUtils.setCurrency(txtHargaToko);
        TextComponentUtils.setCurrency(txtHargaMember);
        TextComponentUtils.setCurrency(txtHPP);
        Clear();
    }

    public Barang showDialog(Barang p, String Title) {
        setTitle(Title);
        Clear();
        if (p == null) {
            txtPLU.setText(FrameUtama.getMasterService().ambilBerikutnya(MasterRunningNumberEnum.BARANG));
        } else {
            loadModelToForm(p);
            isiTabelHistory(p);
            isiTabelHPP(p);
        }
        setPreferredSize(new Dimension(729, 580));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        return this.barang;
    }

    private void aktifHPP(Boolean aktif){
        jdcTanggalHPP.setEnabled(aktif);
        txtHPP.setEditable(aktif);
        txtIsiHpp.setEditable(aktif);
    }
    private void clearHPP(){
        btnBaruHPP.setText("Baru");
            jdcTanggalHPP.setDate(new Date());
            jdcTanggalHPP.setDateFormatString("dd MMMM yyyy");
            txtHPP.setText("");
            txtIsiHpp.setText("");
            lblHPP.setText("0");
            btnBaruHPP.setText("Baru");
            aktifHPP(false);
    }
    private void Clear() {
        isiCombo();
        txtPLU.setText("");
        txtNamaBarang.setText("");
        txtBarcode1.setText("");
        txtBarcode2.setText("");
        txtIsi.setText("0");
        txtHargaBeli.setText("0");
        txtHargaToko.setText("0");
        txtHargaTokoPersen.setText("0");
        txtHargaMember.setText("0");
        txtHargaMemberPersen.setText("0");
        txtStokOpname.setText("0");
        txtStokGudang.setText("0");
        txtTotalStok.setText("0");
        txtNilaiStok.setText("0");
        txtStokMax.setText("0");
        txtStokMin.setText("0");
        
        clearHPP();

        cboGolongan.setSelectedItem(null);
        cboSatuan.setSelectedItem(null);
        cboSatBeli.setSelectedItem(null);

        jcbJual.setSelected(true);
        jcbLimitWarning.setSelected(false);
        
        txtTotalStok.setEditable(false);
        txtNilaiStok.setEditable(false);
        txtPLU.setEditable(false);
        
        if (jcbLimitWarning.isSelected()) {
            txtStokMax.setEnabled(true);
            txtStokMin.setEnabled(true);
        } else {
            txtStokMax.setEnabled(false);
            txtStokMin.setEnabled(false);
        }
        isiLabelSatuan("");
        lblHargaBeliPerItem.setText("0");
        hargaSatuan();
        totalStok();
    }
    private void isiCombo() {
        cboSatuan.removeAllItems();
        cboSatuan.addItem("Botol");
        cboSatuan.addItem("Box");
        cboSatuan.addItem("Buah");
        cboSatuan.addItem("Dusin");
        cboSatuan.addItem("Pack");
        cboSatuan.addItem("PCS");
        cboSatuan.addItem("Toples");

        cboSatBeli.removeAllItems();
        cboSatBeli.addItem("Botol");
        cboSatBeli.addItem("Box");
        cboSatBeli.addItem("Buah");
        cboSatBeli.addItem("Dusin");
        cboSatBeli.addItem("Pack");
        cboSatBeli.addItem("PCS");
        cboSatBeli.addItem("Toples");

        cboSatuan.setSelectedItem(null);
        cboSatBeli.setSelectedItem(null);
        isiComboGolongan();
    }
    private void isiComboGolongan() {

        cboGolongan.removeAllItems();
        golonganBarangs = FrameUtama.getMasterService().semuaGolonganBarang();
        for (int i = 0; i < golonganBarangs.size(); i++) {
            cboGolongan.addItem(golonganBarangs.get(i).getGolonganBarang());
        }
    }
    private void isiLabelSatuan(String s) {
        lblSatuan1.setText(s);
        lblSatuan2.setText(s);
        lblSatuan3.setText(s);
        lblSatuan4.setText(s);
        lblSatuan5.setText(s);
        lblSatuan6.setText(s);
        lblSatuan7.setText(s);
        lblSatuan8.setText("/");
    }
    private void totalStok() {
        int totalStok;
        if ("".equals(txtStokOpname.getText()) || 
                "".equals(txtStokGudang.getText())) {
            totalStok = 0;
        } else {
            totalStok = Perhitungan.tambahInteger(txtStokOpname.getText(), txtStokGudang.getText());
        }
        txtTotalStok.setText(String.valueOf(totalStok));
        nilaiStok();
    }
    private void hargaSatuan() {
        if ("".equals(txtHargaBeli.getText()) ||
                "".equals(txtIsi.getText()) || 
                "0".equals(txtHargaBeli.getText()) ||
                "0".equals(txtIsi.getText())) {
            lblHargaBeliPerItem.setText("0");
            lblRp.setText("Rp ");
        } else {
            String isi = txtIsi.getText();
            //BigDecimal hrgSatuan = TextComponentUtils.parseNumberToBigDecimal(txtHargaBeli.getText()).divide(new BigDecimal(isi, MathContext.DECIMAL64));
            Double hargaSatuan = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtHargaBeli)) / Double.valueOf(txtIsi.getText());//harga beli di bagi isi
            lblHargaBeliPerItem.setText(TextComponentUtils.formatNumber(hargaSatuan));
            lblRp.setText("Rp ");
            persen(txtHargaTokoPersen, txtHargaToko);
            persen(txtHargaMemberPersen, txtHargaMember);
            nilaiStok();
        }
    }
    private void hargaSatuanHpp() {
        if ("".equals(txtHPP.getText()) ||
                "".equals(txtIsiHpp.getText()) || 
                "0".equals(txtHPP.getText()) ||
                "0".equals(txtIsiHpp.getText())) {
            lblHPP.setText("0");
        } else {
            //BigDecimal hrgSatuan = TextComponentUtils.parseNumberToBigDecimal(txtHargaBeli.getText()).divide(new BigDecimal(isi, MathContext.DECIMAL64));
            Double hargaSatuan = Double.valueOf(TextComponentUtils.getValueFromTextNumber(txtHPP)) / Double.valueOf(txtIsiHpp.getText());//harga beli di bagi isi
            lblHPP.setText(TextComponentUtils.formatNumber(hargaSatuan));
        }
    }
    private void TotalHargaBeli() {
        if ("".equals(lblHargaBeliPerItem.getText()) ||
                "".equals(txtIsi.getText()) || 
                "0".equals(lblHargaBeliPerItem.getText()) ||
                "0".equals(txtIsi.getText())) {
            lblHargaBeliPerItem.setText("0");
            lblRp.setText("Rp ");
        } else {
            Double hargaSatuan = Double.valueOf(TextComponentUtils.getValueFromTextNumber(lblHargaBeliPerItem)) * Double.valueOf(txtIsi.getText());//harga beli di bagi isi
            BigDecimal b = new BigDecimal(hargaSatuan, MathContext.DECIMAL64);
            txtHargaBeli.setText(TextComponentUtils.formatNumber(b));
            lblRp.setText("Rp ");
            persen(txtHargaTokoPersen, txtHargaToko);
            persen(txtHargaMemberPersen, txtHargaMember);
            nilaiStok();
        }
    }
    private void nilaiStok() {
        BigDecimal hargaSatuan = TextComponentUtils.parseNumberToBigDecimal(lblHargaBeliPerItem.getText());//harga beli di bagi isi
        BigDecimal harga = ((TextComponentUtils.parseNumberToBigDecimal(txtTotalStok.getText())
                .multiply(hargaSatuan)));
        txtNilaiStok.setText(TextComponentUtils.formatNumber(harga));
    }
    private void persen(JTextField textPersen, JTextField textHarga) {
        if ("0".equals(lblHargaBeliPerItem.getText())) {
            textPersen.setText("0");
        } else {
            if (!"".equals(textHarga.getText())) {
                DecimalFormat df = new DecimalFormat("#.#");
                Double hargaSatuan = Double.valueOf(TextComponentUtils.getValueFromTextNumber(lblHargaBeliPerItem));
                Double selisihHarga = Double.valueOf(TextComponentUtils.getValueFromTextNumber(textHarga)) - hargaSatuan;
                Double persen = (selisihHarga / hargaSatuan) * 100;
                textPersen.setText(String.valueOf(df.format(persen)));
            } else {
                textPersen.setText("0");
            }
        }
    }
    private void harga(JTextField textPersen, JTextField textHarga) {
        if ("0".equals(lblHargaBeliPerItem.getText())) {
            textPersen.setText("0");
        } else {
            if (!"".equals(textPersen.getText())) {
                BigDecimal hargaSatuan = TextComponentUtils.parseNumberToBigDecimal(lblHargaBeliPerItem.getText());//harga beli di bagi isi
                BigDecimal harga = ((TextComponentUtils.parseNumberToBigDecimal(textPersen.getText())
                        .divide(TextComponentUtils.parseNumberToBigDecimal("100"))))//persen di kali 100
                        .multiply(hargaSatuan)
                        .add(hargaSatuan);
                textHarga.setText(TextComponentUtils.formatNumber(harga));
            } else {
                textHarga.setText("0");
            }
        }
    }
    private void loadModelToForm(Barang p) {

        txtPLU.setText(p.getPlu());
        txtNamaBarang.setText(p.getNamaBarang());
        txtBarcode1.setText(p.getBarcode1());
        txtBarcode2.setText(p.getBarcode2());
        txtIsi.setText(String.valueOf(p.getIsiPembelian()));
        lblHargaBeliPerItem.setText(TextComponentUtils.formatNumber(p.getHargaBeli()));
        txtHargaToko.setText(TextComponentUtils.formatNumber(p.getHargaNormal()));
        txtHargaMember.setText(TextComponentUtils.formatNumber(p.getHargaMember()));
        txtStokOpname.setText(String.valueOf(p.getKalkulasiStokToko()));
        txtStokGudang.setText(String.valueOf(p.getKalkulasiStokGudang()));
        txtStokMax.setText(String.valueOf(p.getStokMax()));
        txtStokMin.setText(String.valueOf(p.getStokMin()));
        
        stokToko = p.getStokToko();
        stokGudang = p.getStokGudang();
        stokPembelianToko = p.getStokPembelianToko();
        stokPembelianGudang = p.getStokPembelianGudang();
        stokPenjualanToko = p.getStokPenjualanToko();
        stokPenjualanGudang = p.getStokPenjualanGudang();

        cboGolongan.setSelectedItem(p.getGolonganBarang().getGolonganBarang());
        cboSatuan.setSelectedItem(p.getSatuan());
        cboSatBeli.setSelectedItem(p.getSatuanPembelian());

        jcbJual.setSelected(p.getJual());
        jcbLimitWarning.setSelected(p.getLimitWarning());
        TotalHargaBeli();
        totalStok();
        barang = new Barang();
        barang = p;
    }
    private void loadFormToModel() {
        barang.setPlu(txtPLU.getText());
        barang.setNamaBarang(txtNamaBarang.getText());
        barang.setBarcode1(txtBarcode1.getText());
        barang.setBarcode2(txtBarcode2.getText());
        barang.setIsiPembelian(Integer.valueOf(txtIsi.getText()));
        barang.setHargaBeli(TextComponentUtils.parseNumberToBigDecimal(lblHargaBeliPerItem.getText()));
        barang.setHargaNormal(TextComponentUtils.parseNumberToBigDecimal(txtHargaToko.getText()));
        barang.setHargaMember(TextComponentUtils.parseNumberToBigDecimal(txtHargaMember.getText()));
        barang.setStokToko(stokToko);
        barang.setStokGudang(stokGudang);
        barang.setStokMax(Integer.valueOf(txtStokMax.getText()));
        barang.setStokMin(Integer.valueOf(txtStokMin.getText()));

        barang.setGolonganBarang(golonganBarang);
        barang.setSatuan((String) cboSatuan.getSelectedItem());
        barang.setSatuanPembelian((String) cboSatBeli.getSelectedItem());

        barang.setJual(jcbJual.isSelected());
        barang.setLimitWarning(jcbLimitWarning.isSelected());
        
        barang.setStokPembelianToko(stokPembelianToko);
        barang.setStokPembelianGudang(stokPembelianGudang);
        barang.setStokPenjualanToko(stokPenjualanToko);
        barang.setStokPenjualanGudang(stokPenjualanGudang);
        
        hargaSatuan();
    }
    private class HistoryTabelModel extends AbstractTableModel {

        private final List<PembelianDetail> pembelianDetails;

        public HistoryTabelModel(List<PembelianDetail> details) {
            this.pembelianDetails = details;
        }

        @Override
        public int getRowCount() {
            return pembelianDetails.size();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Tanggal";
                case 1: return "Supplier";
                case 2: return "Qty";
                case 3: return "Sat Pembelian";
                case 4: return "Isi";
                case 5: return "Harga Beli";
                case 6: return "Total";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            PembelianDetail p = pembelianDetails.get(rowIndex);
            switch (colIndex) {
                case 0: return p.getPembelian().getTanggal();
                case 1: 
                    if(p.getPembelian().getSupplier()!=null){
                        return p.getPembelian().getSupplier().getNamaSupplier();
                    }else{
                        return "-";
                    }
                case 2: return p.getKuantitas();
                case 3: return p.getSatuanPembelian();
                case 4: return p.getIsiPembelian();
                case 5: return p.getHargaBarang();
                case 6: return p.getSubTotal();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Date.class;
                case 2: return Integer.class;
                case 4: return Integer.class;
                case 5: return BigDecimal.class;
                case 6: return BigDecimal.class;
                default: return String.class;
            }
        }
    }
    private class HPPTabelModel extends AbstractTableModel {

        private final List<HPPBarang> ListHPPBarang;

        public HPPTabelModel(List<HPPBarang> ListHPPBarang) {
            this.ListHPPBarang = ListHPPBarang;
        }

        @Override
        public int getRowCount() {
            return ListHPPBarang.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Tanggal";
                case 1: return "HPP";
                case 2: return "Isi";
                case 3: return "HPP Satuan";
                default: return "";
            }

        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {
            HPPBarang h = ListHPPBarang.get(rowIndex);
            switch (colIndex) {
                case 0: return h.getTanggal();
                case 1: return h.getHpp();
                case 2: return h.getIsi();
                case 3: return h.getHppSatuan();
                default: return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0: return Date.class;
                case 1: return BigDecimal.class;
                case 2: return Integer.class;
                case 3: return BigDecimal.class;
                default: return String.class;
            }
        }
    }
    private void isiTabelHistory(Barang barang) {
        listHistory = FrameUtama.getTransaksiService().cariBarang(barang);
        tblHistoryPembelian.setModel(new HistoryTabelModel(listHistory));
        ukuranTabelHistory();
    }
    private void isiTabelHPP(Barang barang) {
        listHPP = FrameUtama.getMasterService().hPPBarangs(barang);
        tblHPP.setModel(new HPPTabelModel(listHPP));
        //ukuranTabelHistory();
    }
    private void ukuranTabelHistory() {
        tblHistoryPembelian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblHistoryPembelian.getColumnModel().getColumn(0).setPreferredWidth(130);//Tanngal
        tblHistoryPembelian.getColumnModel().getColumn(1).setPreferredWidth(200);//Supplier
        tblHistoryPembelian.getColumnModel().getColumn(2).setPreferredWidth(50);//Qty
        tblHistoryPembelian.getColumnModel().getColumn(3).setPreferredWidth(100);//Satuan Pembelian
        tblHistoryPembelian.getColumnModel().getColumn(4).setPreferredWidth(50);//Isi
        tblHistoryPembelian.getColumnModel().getColumn(5).setPreferredWidth(100);//Harga Beli
        tblHistoryPembelian.getColumnModel().getColumn(6).setPreferredWidth(100);//Total
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPLU = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtBarcode1 = new javax.swing.JTextField();
        txtBarcode2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboGolongan = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JButton();
        cboSatuan = new javax.swing.JComboBox<>();
        jcbJual = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboSatBeli = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtIsi = new javax.swing.JTextField();
        txtHargaBeli = new javax.swing.JTextField();
        lblSatuan1 = new javax.swing.JLabel();
        lblRp = new javax.swing.JLabel();
        lblHargaBeliPerItem = new javax.swing.JLabel();
        lblSatuan7 = new javax.swing.JLabel();
        lblSatuan8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtHargaToko = new javax.swing.JTextField();
        txtHargaTokoPersen = new javax.swing.JTextField();
        txtHargaMember = new javax.swing.JTextField();
        txtHargaMemberPersen = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtStokOpname = new javax.swing.JTextField();
        txtStokGudang = new javax.swing.JTextField();
        txtTotalStok = new javax.swing.JTextField();
        txtNilaiStok = new javax.swing.JTextField();
        lblSatuan2 = new javax.swing.JLabel();
        lblSatuan3 = new javax.swing.JLabel();
        lblSatuan4 = new javax.swing.JLabel();
        btnTambahStok = new javax.swing.JButton();
        btnKurangStok = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jcbLimitWarning = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtStokMax = new javax.swing.JTextField();
        txtStokMin = new javax.swing.JTextField();
        lblSatuan5 = new javax.swing.JLabel();
        lblSatuan6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistoryPembelian = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jdcTanggalHPP = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtHPP = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtIsiHpp = new javax.swing.JTextField();
        lblHPP = new javax.swing.JLabel();
        btnBaruHPP = new javax.swing.JButton();
        btnHapusHPP = new javax.swing.JButton();
        btnBatalHpp = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHPP = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel2.border.title"))); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel4.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel5.text")); // NOI18N

        txtPLU.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtPLU.text")); // NOI18N

        txtNamaBarang.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtNamaBarang.text")); // NOI18N

        txtBarcode1.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtBarcode1.text")); // NOI18N

        txtBarcode2.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtBarcode2.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel6.text")); // NOI18N

        cboGolongan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboGolongan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboGolonganItemStateChanged(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/find-icon16.png"))); // NOI18N
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        cboSatuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSatuan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSatuanItemStateChanged(evt);
            }
        });

        jcbJual.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jcbJual.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbJual)
                    .addComponent(txtPLU, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txtBarcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBarcode2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(cboGolongan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPLU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBarcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBarcode2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cboGolongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbJual))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCari, cboGolongan});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel3.border.title"))); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel7.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel8.text")); // NOI18N

        cboSatBeli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel9.text")); // NOI18N

        txtIsi.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtIsi.text")); // NOI18N
        txtIsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIsiKeyReleased(evt);
            }
        });

        txtHargaBeli.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHargaBeli.text")); // NOI18N
        txtHargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaBeliKeyReleased(evt);
            }
        });

        lblSatuan1.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan1.text")); // NOI18N

        lblRp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRp.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblRp.text")); // NOI18N

        lblHargaBeliPerItem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHargaBeliPerItem.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblHargaBeliPerItem.text")); // NOI18N

        lblSatuan7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSatuan7.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan7.text")); // NOI18N

        lblSatuan8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSatuan8.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan8.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSatBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtIsi, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSatuan1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblRp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHargaBeliPerItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSatuan8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSatuan7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboSatBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtIsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHargaBeliPerItem)
                    .addComponent(lblSatuan7)
                    .addComponent(lblRp)
                    .addComponent(lblSatuan8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel4.border.title"))); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel12.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel13.text")); // NOI18N

        txtHargaToko.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHargaToko.text")); // NOI18N
        txtHargaToko.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaTokoKeyReleased(evt);
            }
        });

        txtHargaTokoPersen.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHargaTokoPersen.text")); // NOI18N
        txtHargaTokoPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaTokoPersenKeyReleased(evt);
            }
        });

        txtHargaMember.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHargaMember.text")); // NOI18N
        txtHargaMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaMemberKeyReleased(evt);
            }
        });

        txtHargaMemberPersen.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHargaMemberPersen.text")); // NOI18N
        txtHargaMemberPersen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHargaMemberPersenKeyReleased(evt);
            }
        });

        jLabel14.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel14.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel15.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHargaToko, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(txtHargaMember))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHargaMemberPersen, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(txtHargaTokoPersen, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addContainerGap(319, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtHargaToko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaTokoPersen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtHargaMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaMemberPersen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel6.border.title"))); // NOI18N

        jLabel16.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel16.text")); // NOI18N

        jLabel17.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel17.text")); // NOI18N

        jLabel18.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel18.text")); // NOI18N

        jLabel19.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel19.text")); // NOI18N

        txtStokOpname.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtStokOpname.text")); // NOI18N
        txtStokOpname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStokOpnameKeyReleased(evt);
            }
        });

        txtStokGudang.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtStokGudang.text")); // NOI18N
        txtStokGudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStokGudangKeyReleased(evt);
            }
        });

        txtTotalStok.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtTotalStok.text")); // NOI18N

        txtNilaiStok.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtNilaiStok.text")); // NOI18N

        lblSatuan2.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan2.text")); // NOI18N

        lblSatuan3.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan3.text")); // NOI18N

        lblSatuan4.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan4.text")); // NOI18N

        btnTambahStok.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnTambahStok.text")); // NOI18N
        btnTambahStok.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambahStok.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTambahStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahStokActionPerformed(evt);
            }
        });

        btnKurangStok.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnKurangStok.text")); // NOI18N
        btnKurangStok.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKurangStok.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKurangStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKurangStokActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtNilaiStok, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 189, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStokOpname, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(txtStokGudang, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(txtTotalStok, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(lblSatuan2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTambahStok))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSatuan4)
                                    .addComponent(lblSatuan3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnKurangStok)))
                        .addContainerGap())))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnKurangStok, btnTambahStok});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtStokGudang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTotalStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtNilaiStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnTambahStok, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKurangStok, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel7.border.title"))); // NOI18N

        jcbLimitWarning.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jcbLimitWarning.text")); // NOI18N
        jcbLimitWarning.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbLimitWarningItemStateChanged(evt);
            }
        });

        jLabel23.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel23.text")); // NOI18N

        jLabel24.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel24.text")); // NOI18N

        txtStokMax.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtStokMax.text")); // NOI18N

        txtStokMin.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtStokMin.text")); // NOI18N

        lblSatuan5.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan5.text")); // NOI18N

        lblSatuan6.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblSatuan6.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbLimitWarning)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStokMax, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(txtStokMin, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSatuan5)
                            .addComponent(lblSatuan6))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jcbLimitWarning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtStokMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtStokMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSatuan6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel8.border.title"))); // NOI18N

        tblHistoryPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "Supplier", "Qty", "Sat. Pembelian", "Isi", "Total"
            }
        ));
        jScrollPane1.setViewportView(tblHistoryPembelian);
        if (tblHistoryPembelian.getColumnModel().getColumnCount() > 0) {
            tblHistoryPembelian.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title0")); // NOI18N
            tblHistoryPembelian.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title1")); // NOI18N
            tblHistoryPembelian.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title2")); // NOI18N
            tblHistoryPembelian.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title3")); // NOI18N
            tblHistoryPembelian.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title4")); // NOI18N
            tblHistoryPembelian.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHistoryPembelian.columnModel.title5")); // NOI18N
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel5.TabConstraints.tabTitle"), jPanel5); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel10.border.title"))); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel10.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel11.text")); // NOI18N

        txtHPP.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtHPP.text")); // NOI18N

        jLabel20.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel20.text")); // NOI18N

        txtIsiHpp.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.txtIsiHpp.text")); // NOI18N

        lblHPP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHPP.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.lblHPP.text")); // NOI18N

        btnBaruHPP.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnBaruHPP.text")); // NOI18N

        btnHapusHPP.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnHapusHPP.text")); // NOI18N

        btnBatalHpp.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnBatalHpp.text")); // NOI18N

        tblHPP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "HPP", "Isi", "HPP Satuan"
            }
        ));
        jScrollPane2.setViewportView(tblHPP);
        if (tblHPP.getColumnModel().getColumnCount() > 0) {
            tblHPP.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHPP.columnModel.title0")); // NOI18N
            tblHPP.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHPP.columnModel.title1")); // NOI18N
            tblHPP.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHPP.columnModel.title2")); // NOI18N
            tblHPP.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.tblHPP.columnModel.title3")); // NOI18N
        }

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jLabel21.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jdcTanggalHPP, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtHPP, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIsiHpp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addGap(2, 2, 2)
                        .addComponent(lblHPP, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBaruHPP, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusHPP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatalHpp)))
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBaruHPP, btnBatalHpp, btnHapusHPP});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(jdcTanggalHPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtHPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtIsiHpp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHPP)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalHpp)
                    .addComponent(btnHapusHPP)
                    .addComponent(btnBaruHPP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBaruHPP, btnBatalHpp, btnHapusHPP});

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.jPanel9.TabConstraints.tabTitle"), jPanel9); // NOI18N

        btnSimpan.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnSimpan.text")); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText(org.openide.util.NbBundle.getMessage(BarangDialog.class, "BarangDialog.btnBatal.text")); // NOI18N
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBatal, btnSimpan});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initListener() {
        //<editor-fold defaultstate="collapsed" desc="close Windows">
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Ya", "Tidak"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Apakah Anda Yakin Ingin Membatalkan Editing", "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    barang = null;
                    dispose();
                }
            }
        });
        //</editor-fold>
        tblHPP.getSelectionModel().addListSelectionListener((ListSelectionEvent lse) -> {
            if (tblHPP.getSelectedRow() >= 0) {
                hPPBarang = new HPPBarang();
                hPPBarang = listHPP.get(tblHPP.getSelectedRow());
                jdcTanggalHPP.setDate(hPPBarang.getTanggal());
                txtHPP.setText(TextComponentUtils.formatNumber(hPPBarang.getHpp()));
                txtIsiHpp.setText(String.valueOf(hPPBarang.getIsi()));
                hargaSatuanHpp();
                
                aktifHPP(true);
                btnBaruHPP.setText("Simpan");
            }
        });
        //<editor-fold defaultstate="collapsed" desc="btnBaru">
        btnBaruHPP.addActionListener((ActionEvent ae) -> {
            if("Baru".equals(btnBaruHPP.getText())){
                btnBaruHPP.setText("Simpan");
                aktifHPP(true);
                txtHPP.requestFocus();
            }else{
                if(hPPBarang == null){
                    hPPBarang = new HPPBarang();
                    hPPBarang.setIdHpp("");
                }
                hPPBarang.setBarang(barang);
                hPPBarang.setHpp(TextComponentUtils.parseNumberToBigDecimal(txtHPP.getText()));
                hPPBarang.setIsi(Integer.valueOf(txtIsiHpp.getText()));
                hPPBarang.setHppSatuan(TextComponentUtils.parseNumberToBigDecimal(lblHPP.getText()));
                hPPBarang.setTanggal(new Date());
                
                FrameUtama.getMasterService().simpan(hPPBarang);
                isiTabelHPP(barang);
                
                clearHPP();
                
            }
        });
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="btnBatal">
        btnBatalHpp.addActionListener((ActionEvent ae) -> {
            clearHPP();
        });
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="txtHPP">
        txtHPP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }
            
            @Override
            public void keyPressed(KeyEvent ke) {
                
            }
            
            @Override
            public void keyReleased(KeyEvent ke) {
                hargaSatuanHpp();
            }
        });
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="txtIsiHpp">
        txtIsiHpp.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }
            
            @Override
            public void keyPressed(KeyEvent ke) {
            }
            
            @Override
            public void keyReleased(KeyEvent ke) {
                hargaSatuanHpp();
            }
        });
        //</editor-fold>
    }
    
    private void cboSatuanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSatuanItemStateChanged
        // TODO add your handling code here:
        isiLabelSatuan((String) cboSatuan.getSelectedItem());
    }//GEN-LAST:event_cboSatuanItemStateChanged
    private void jcbLimitWarningItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbLimitWarningItemStateChanged
        // TODO add your handling code here:
        if (jcbLimitWarning.isSelected()) {
            txtStokMax.setEnabled(true);
            txtStokMin.setEnabled(true);
        } else {
            txtStokMax.setEnabled(false);
            txtStokMin.setEnabled(false);
        }
    }//GEN-LAST:event_jcbLimitWarningItemStateChanged
    private void txtStokOpnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStokOpnameKeyReleased
        // TODO add your handling code here:
        totalStok();
    }//GEN-LAST:event_txtStokOpnameKeyReleased
    private void txtStokGudangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStokGudangKeyReleased
        // TODO add your handling code here:
        totalStok();
    }//GEN-LAST:event_txtStokGudangKeyReleased
    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        barang = null;
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed
    private void txtHargaTokoPersenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaTokoPersenKeyReleased
        // TODO add your handling code here:
        harga(txtHargaTokoPersen, txtHargaToko);
    }//GEN-LAST:event_txtHargaTokoPersenKeyReleased
    private void txtHargaMemberPersenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaMemberPersenKeyReleased
        // TODO add your handling code here:
        harga(txtHargaMemberPersen, txtHargaMember);
    }//GEN-LAST:event_txtHargaMemberPersenKeyReleased
    private void txtIsiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIsiKeyReleased
        // TODO add your handling code here:
        hargaSatuan();
    }//GEN-LAST:event_txtIsiKeyReleased
    private void txtHargaBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliKeyReleased
        // TODO add your handling code here:
        hargaSatuan();
    }//GEN-LAST:event_txtHargaBeliKeyReleased
    private void txtHargaTokoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaTokoKeyReleased
        // TODO add your handling code here:
        persen(txtHargaTokoPersen, txtHargaToko);
    }//GEN-LAST:event_txtHargaTokoKeyReleased
    private void txtHargaMemberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaMemberKeyReleased
        // TODO add your handling code here:
        persen(txtHargaMemberPersen, txtHargaMember);
    }//GEN-LAST:event_txtHargaMemberKeyReleased
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String judul = "Pilih Kategori Produk";
        GolonganBarang g = (GolonganBarang) new PilihGolonganBarangDialog().showDialog(judul);
        isiComboGolongan();
        if (g != null) {
            cboGolongan.setSelectedItem(g.getGolonganBarang());
        }
    }//GEN-LAST:event_btnCariActionPerformed
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        barang = new Barang();
        loadFormToModel();
        dispose();
    }//GEN-LAST:event_btnSimpanActionPerformed
    private void cboGolonganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboGolonganItemStateChanged
        // TODO add your handling code here:
        if (cboGolongan.getSelectedIndex() >= 0) {
            golonganBarang = golonganBarangs.get(cboGolongan.getSelectedIndex());
        } else {
            golonganBarang = null;
        }
    }//GEN-LAST:event_cboGolonganItemStateChanged
    private void btnTambahStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahStokActionPerformed
        String title = "Tambah Stok Barang";
        String status = "Tambah";
        if(barang==null){
            barang = new Barang();
            barang.setPlu(txtNamaBarang.getText());
        }
        Barang b = new MutasiStokDialog().showDialog(barang, title, status);
        if(b!=null){
            barang.setStokToko(b.getStokToko());
            barang.setStokGudang(b.getStokGudang());
            
            stokToko = b.getStokToko();
            stokGudang = b.getStokGudang();
            
            txtStokOpname.setText(String.valueOf(barang.getKalkulasiStokToko()));
            txtStokGudang.setText(String.valueOf(barang.getKalkulasiStokGudang()));
            totalStok();
        }
    }//GEN-LAST:event_btnTambahStokActionPerformed

    private void btnKurangStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKurangStokActionPerformed
        String title = "Kurangi Stok Barang";
        String status = "Kurang";
        
        if(barang==null){
            barang = new Barang();
            barang.setPlu(txtNamaBarang.getText());
        }
        Barang b = new MutasiStokDialog().showDialog(barang, title, status);
        if(b!=null){
            barang.setStokToko(b.getStokToko());
            barang.setStokGudang(b.getStokGudang());
            
            stokToko = b.getStokToko();
            stokGudang = b.getStokGudang();
            
            txtStokOpname.setText(String.valueOf(barang.getStokToko()));
            txtStokGudang.setText(String.valueOf(barang.getStokGudang()));
            totalStok();
        }
    }//GEN-LAST:event_btnKurangStokActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaruHPP;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnBatalHpp;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapusHPP;
    private javax.swing.JButton btnKurangStok;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahStok;
    private javax.swing.JComboBox<String> cboGolongan;
    private javax.swing.JComboBox<String> cboSatBeli;
    private javax.swing.JComboBox<String> cboSatuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox jcbJual;
    private javax.swing.JCheckBox jcbLimitWarning;
    private com.toedter.calendar.JDateChooser jdcTanggalHPP;
    private javax.swing.JLabel lblHPP;
    private javax.swing.JLabel lblHargaBeliPerItem;
    private javax.swing.JLabel lblRp;
    private javax.swing.JLabel lblSatuan1;
    private javax.swing.JLabel lblSatuan2;
    private javax.swing.JLabel lblSatuan3;
    private javax.swing.JLabel lblSatuan4;
    private javax.swing.JLabel lblSatuan5;
    private javax.swing.JLabel lblSatuan6;
    private javax.swing.JLabel lblSatuan7;
    private javax.swing.JLabel lblSatuan8;
    private javax.swing.JTable tblHPP;
    private javax.swing.JTable tblHistoryPembelian;
    private javax.swing.JTextField txtBarcode1;
    private javax.swing.JTextField txtBarcode2;
    private javax.swing.JTextField txtHPP;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaMember;
    private javax.swing.JTextField txtHargaMemberPersen;
    private javax.swing.JTextField txtHargaToko;
    private javax.swing.JTextField txtHargaTokoPersen;
    private javax.swing.JTextField txtIsi;
    private javax.swing.JTextField txtIsiHpp;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNilaiStok;
    private javax.swing.JTextField txtPLU;
    private javax.swing.JTextField txtStokGudang;
    private javax.swing.JTextField txtStokMax;
    private javax.swing.JTextField txtStokMin;
    private javax.swing.JTextField txtStokOpname;
    private javax.swing.JTextField txtTotalStok;
    // End of variables declaration//GEN-END:variables
}
