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
                
                System.out.println(detail.getId());
                System.out.println(p.getNoRef());
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
                System.out.println(detail.getId());
                System.out.println(p.getNoRef());
            }
        }
        //update barang
        for (PembelianDetail detail : p.getPembelianDetails()) {
            
            Barang b = barangDao.barangBerdasarkanId(detail.getBarang().getPlu());
            if (detail.getUpdate() == true) {
                Double hargaBeli = Double.valueOf(TextComponentUtils.getValueFromTextNumber(
                        TextComponentUtils.formatNumber(detail.getHargaBarang())));
                Double isi = Double.valueOf(detail.getIsiPembelian());//harga beli di bagi isi
                Double hargaSatuan = hargaBeli / isi;
                BigDecimal hS = new BigDecimal(hargaSatuan, MathContext.DECIMAL64);
                System.out.println(b.getNamaBarang() + " : " + hargaBeli + " / " + isi + " = " + hargaSatuan);
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
                System.out.println("Stok Pembelian Toko" + b.getStokPembelianToko());
                System.out.println("Stok Pembelian Gudang" + b.getStokPembelianGudang());
            }
            
        }
        //updateSaldoStok(produkMasuk);
        pembelianDao.merge(p);
        simpanStokPembelian(p);
        simpanHutang();
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
    private void simpanHutang(){
        List<Supplier> suppliers = supplierDao.semua();
        for(Supplier s : suppliers){
            List<Pembelian> pembelians = pembelianDao.cariSupplier(s);
            BigDecimal saldoHutang = new BigDecimal(0);
            for(int i=0;i<pembelians.size();i++){
                Pembelian p = pembelians.get(i);
                saldoHutang = saldoHutang.add(p.getDaftarKredit().getSisaKredit());
            }
            s.setSaldoHutang(saldoHutang);
            System.out.println(s.getNamaSupplier() + " = " +saldoHutang);
            supplierDao.simpan(s);
        }
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

    @Override
    public void simpan(PelunasanHutang p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PelunasanHutang cariId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PelunasanHutang> semua() {
        return pelunasanHutangDao.semua();
    }

    @Override
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang p) {
        return pelunasanHutangDao.cariDetail(p);
    }

}
