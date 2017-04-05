/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service.impl;

import com.aangirsang.girsang.toko.dao.master.BarangDao;
import com.aangirsang.girsang.toko.dao.master.HPPDao;
import com.aangirsang.girsang.toko.dao.master.RunningNumberDao;
import com.aangirsang.girsang.toko.dao.master.SupplierDao;
import com.aangirsang.girsang.toko.dao.transaksi.PelunasanHutangDao;
import com.aangirsang.girsang.toko.dao.transaksi.PembelianDao;
import com.aangirsang.girsang.toko.dao.transaksi.RunningNumberTransaksiDao;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.HPPBarang;
import com.aangirsang.girsang.toko.model.master.RunningNumber;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutang;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutangDetail;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import com.aangirsang.girsang.toko.model.transaksi.constant.TransaksiRunningNumberEnum;
import com.aangirsang.girsang.toko.service.TransaksiService;
import com.aangirsang.girsang.toko.util.TextComponentUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ITSUSAHBRO
 */
@Service("TransaksiService")
@Transactional(readOnly = true)
public class TransaksiServiceImpl implements TransaksiService {

    @Autowired PembelianDao pembelianDao;
    @Autowired BarangDao barangDao;
    @Autowired RunningNumberTransaksiDao runningNumberTransaksiDao;
    @Autowired RunningNumberDao runningNumberDao;
    @Autowired HPPDao hPPDao;
    @Autowired SupplierDao supplierDao;
    @Autowired PelunasanHutangDao pelunasanHutangDao;

    //<editor-fold defaultstate="collapsed" desc="Running Number">
    @Override
    @Transactional
    public void simpan(RunningNumber r) {
        runningNumberTransaksiDao.simpan(r);
    }
    
    @Override
    public List<RunningNumber> semuaRunningNumber() {
        return runningNumberTransaksiDao.semua();
    }
    
    @Override
    @Transactional
    public String ambilBerikutnya(TransaksiRunningNumberEnum id) {
        return runningNumberTransaksiDao.ambilBerikutnya(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Pembelian">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(Pembelian p) {
        Pembelian pembelian = pembelianDao.cariBerdasarId(p.getNoRef());
        if (pembelian == null) {
            p.setNoRef(runningNumberTransaksiDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PEMBELIAN));
            int i = 1;
            for (PembelianDetail detail : p.getPembelianDetails()) {
                detail.setId(p.getNoRef() + i++);
            }
        } else {
            int i = 1;
            for (PembelianDetail detail : p.getPembelianDetails()) {
                try {
                    i++;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                } catch (Exception e) {
                    i = i + 1;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                }
                if (detail.getId() == null) {
                    detail.setId(p.getNoRef() + i++);
                }
            }
        }
        //update barang
        //<editor-fold defaultstate="collapsed" desc="Update Barang">
        for (PembelianDetail detail : p.getPembelianDetails()) {
            
            Barang b = barangDao.barangBerdasarkanId(detail.getBarang().getPlu());
            if (detail.getUpdate() == true) {
                Double hargaBeli = Double.valueOf(TextComponentUtils.getValueFromTextNumber(
                        TextComponentUtils.formatNumber(detail.getHargaBarang())));
                Double isi = Double.valueOf(detail.getIsiPembelian());//harga beli di bagi isi
                Double hargaSatuan = hargaBeli / isi;
                BigDecimal hS = new BigDecimal(hargaSatuan, MathContext.DECIMAL64);
                b.setSatuanPembelian(detail.getSatuanPembelian());
                b.setHargaBeli(BigDecimal.valueOf(hargaSatuan));
                b.setIsiPembelian(detail.getIsiPembelian());
                
                //Simpan HPP Barang
                HPPBarang hPPBarang = new HPPBarang();
                hPPBarang.setIdHpp(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.HPP));
                hPPBarang.setTanggal(detail.getPembelian().getTanggal());
                hPPBarang.setBarang(b);
                hPPBarang.setHpp(detail.getHargaBarang());
                hPPBarang.setIsi(detail.getIsiPembelian());
                hPPBarang.setHppSatuan(BigDecimal.valueOf(hargaSatuan));
                
                barangDao.simpan(b);
                hPPDao.simpan(hPPBarang);
            }
            
        }
        //</editor-fold>
        pembelianDao.merge(p);
        simpanStokPembelian(p);
        simpanHutang();
    }
    
    
    @Override
    public Pembelian cariPembelian(String id) {
        return pembelianDao.cariBerdasarId(id);
    }
    
    @Override
    public List<Pembelian> semuaPembelian() {
        return pembelianDao.semua();
    }
    
    @Override
    public List<Pembelian> descPembelian() {
        return pembelianDao.semuaDesc();
    }
    
    @Override
    public List<Pembelian> hutangPembelian(Supplier s){
        return pembelianDao.hutangPembelian(s);
    }
    
    @Override
    public List<PembelianDetail> cariPembelianDetail(Pembelian p) {
        return pembelianDao.cariBerdasarkanIDPembelian(p);
    }
    
    @Override
    public List<PembelianDetail> cariBarang(Barang barang) {
        return pembelianDao.cariBarang(barang);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Pelunasan Hutang">
    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(PelunasanHutang p) {
        PelunasanHutang pelunasanHutang = pelunasanHutangDao.cariId(p.getNoRef());
        if(pelunasanHutang==null){
            p.setNoRef(runningNumberTransaksiDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.HUTANG));
            int i = 1;
            for(PelunasanHutangDetail detail : p.getPelunasanHutangDetails()){
                detail.setId(p.getNoRef() + i++);
            }
        } else {
            int i = 1;
            for (PelunasanHutangDetail detail : p.getPelunasanHutangDetails()) {
                try {
                    i++;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                } catch (Exception e) {
                    i = i + 1;
                    if (detail.getId() == null) {
                        detail.setId(p.getNoRef() + i++);
                    }
                }
                if (detail.getId() == null) {
                    detail.setId(p.getNoRef() + i++);
                }
            }
        }
        pelunasanHutangDao.merge(p);
        simpanHutang();
    }
    @Override
    @Transactional
    public void hapus(PelunasanHutang p) {
        pelunasanHutangDao.hapus(p);
        simpanHutang();
    }
    
    @Override
    public PelunasanHutang cariId(String id) {
        return pelunasanHutangDao.cariId(id);
    }
    
    @Override
    public List<PelunasanHutang> semua() {
        return pelunasanHutangDao.semua();
    }
    @Override
    public List<PelunasanHutang> cariSupplier(Supplier s) {
        return pelunasanHutangDao.cariSupplier(s);
    }
    
    @Override
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang p) {
        return pelunasanHutangDao.cariDetail(p);
    }
    
    @Override
    public List<PelunasanHutangDetail> cariPembelian(Pembelian pembelian) {
        return pelunasanHutangDao.cariPembelian(pembelian);
    }
//</editor-fold>

    
    private void simpanHutang(){
        List<Supplier> suppliers = supplierDao.semua();
        for(Supplier s : suppliers){
            List<Pembelian> pembelians = pembelianDao.cariSupplier(s);
            List<PelunasanHutang> hutangs = pelunasanHutangDao.cariSupplier(s);
            
            BigDecimal hutangPembelian = new BigDecimal(0);
            BigDecimal pembayaranHutang = new BigDecimal(0);
            
            for(int i=0;i<pembelians.size();i++){
                Pembelian p = pembelians.get(i);
                hutangPembelian = hutangPembelian.add(p.getDaftarKredit().getSisaKredit());
            }
            
            for(int i=0;i<hutangs.size();i++){
                PelunasanHutang pH = hutangs.get(i);
                pembayaranHutang = pembayaranHutang.add(pH.getJlhBayar());
            }
            s.setSaldoHutang(hutangPembelian.subtract(pembayaranHutang));
            
            supplierDao.simpan(s);
        }
    }
    
    private void simpanStokPembelian(Pembelian p) {
        Integer stokToko = 0;
        Integer stokGudang = 0;
        for (PembelianDetail detail : p.getPembelianDetails()) {
            Barang b = barangDao.barangBerdasarkanId(detail.getBarang().getPlu());
            List<PembelianDetail> PD = pembelianDao.cariBarang(detail.getBarang());
            for (PembelianDetail PD1 : PD) {
                if("Toko".equals(PD1.getPembelian().getLokasi())){
                    stokToko = stokToko + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }else if("Gudang".equals(PD1.getPembelian().getLokasi())){
                    stokGudang = stokGudang + (PD1.getKuantitas() * PD1.getIsiPembelian());
                }
            }
            b.setStokPembelianToko(stokToko);
            b.setStokPembelianGudang(stokGudang);
            
            barangDao.simpan(b);
        }
    }

}
