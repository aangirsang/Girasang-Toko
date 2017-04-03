/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service.impl;

import com.aangirsang.girsang.toko.dao.master.BarangDao;
import com.aangirsang.girsang.toko.dao.master.GolonganBarangDao;
import com.aangirsang.girsang.toko.dao.master.HPPDao;
import com.aangirsang.girsang.toko.dao.master.RunningNumberDao;
import com.aangirsang.girsang.toko.dao.master.SatuanBarangDao;
import com.aangirsang.girsang.toko.dao.master.SupplierDao;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import com.aangirsang.girsang.toko.model.master.HPPBarang;
import com.aangirsang.girsang.toko.model.master.RunningNumber;
import com.aangirsang.girsang.toko.model.master.SatuanBarang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import com.aangirsang.girsang.toko.service.MasterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIRSANG PC
 */
@Service("MasterService")
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService{

    @Autowired GolonganBarangDao golonganBarangDao;
    @Autowired RunningNumberDao runningNumberDao;
    @Autowired SatuanBarangDao satuanBarangDao;
    @Autowired SupplierDao supplierDao;
    @Autowired BarangDao barangDao;
    @Autowired HPPDao hPPDao;

    //<editor-fold defaultstate="collapsed" desc="Golongan Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(GolonganBarang golonganBarang) {
        if (golonganBarang.getId()==null){
            golonganBarang.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.GOLONGAN));
        }
        golonganBarangDao.simpan(golonganBarang);
    }
    @Override
    @Transactional
    public void hapus(GolonganBarang golonganBarang) {
        golonganBarangDao.hapus(golonganBarang);
    }
    @Override
    public List<GolonganBarang> semuaGolonganBarang() {
        return golonganBarangDao.semua();
    }
    @Override
    public List<GolonganBarang> cariNamaGolonganBarang(String GolonganBarang) {
        return golonganBarangDao.golonganBarangBerdasarkanNama(GolonganBarang);
    }
    @Override
    public GolonganBarang golonganBarangBerdasarkanId(String id) {
        return golonganBarangDao.golonganBarangBerdasarkanId(id);
    }
    @Override
    public List<GolonganBarang> golonganBarangAsc() {
        return golonganBarangDao.golonganBarangsAsc();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Running Number">
    @Override
    @Transactional
    public void simpan(RunningNumber p) {
        runningNumberDao.simpan(p);
    }
    
    @Override
    public List<RunningNumber> semuaRunningNumber() {
        return runningNumberDao.semua();
    }
    
    @Override
    @Transactional
    public String ambilBerikutnya(MasterRunningNumberEnum id) {
        return runningNumberDao.ambilBerikutnya(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Satuan Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(SatuanBarang satuanBarang) {
        if (satuanBarang.getId()==null)satuanBarang.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.SATUAN));
        satuanBarangDao.simpan(satuanBarang);
    }
    
    @Override
    @Transactional
    public void hapus(SatuanBarang satuanBarang) {
        satuanBarangDao.hapus(satuanBarang);
    }
    
    @Override
    public List<SatuanBarang> semuaSatuanBarang() {
        return satuanBarangDao.semua();
    }
    
    @Override
    public List<SatuanBarang> cariNamaSatuanBarang(String SatuanBarang) {
        return satuanBarangDao.satuanBarangBerdasarkanNama(SatuanBarang);
    }
    
    @Override
    public SatuanBarang satuanBarangBerdasarkanId(String id) {
        return satuanBarangDao.satuanBarangBerdasarkanId(id);
    }
    @Override
    public List<SatuanBarang> satuanBarangAsc() {
        return satuanBarangDao.satuanBarangsAsc();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Supplier">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(Supplier supplier) {
        if("".equals(supplier.getId()))supplier.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.SUPPLIER));
        supplierDao.simpan(supplier);
    }
    
    @Override
    @Transactional
    public void hapus(Supplier supplier) {
        supplierDao.hapus(supplier);
    }
    
    @Override
    public List<Supplier> semuaSupplier() {
        return supplierDao.semua();
    }
    
    @Override
    public List<Supplier> cariNamaSupplier(String Supplier) {
        return supplierDao.supplierBerdasarkanNama(Supplier);
    }
    
    @Override
    public Supplier supplierBerdasarkanId(String id) {
        return supplierDao.supplierBerdasarkanId(id);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Barang">
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(Barang barang) {
        if("".equals(barang.getPlu()))barang.setPlu(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.BARANG));
        barangDao.simpan(barang);
    }
    
    @Override
    @Transactional
    public void hapus(Barang barang) {
        barangDao.hapus(barang);
    }
    
    @Override
    public List<Barang> semuaBarang() {
        return barangDao.semua();
    }
    
    @Override
    public List<Barang> cariNamaBarang(String Barang) {
        return barangDao.barangBerdasarkanNama(Barang);
    }
    
    @Override
    public Barang barangBerdasarkanId(String id) {
        return barangDao.barangBerdasarkanId(id);
    }
    @Override
    public List<Barang> cariStokTokoBarang(int min, int max) {
        return barangDao.barangStokToko(min, max);
    }
    @Override
    public List<PembelianDetail> historyPembelian(Barang barang) {
        return barangDao.historyPembelian(barang);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HPP Barang">
    
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void simpan(HPPBarang hPPBarang) {
        if("".equals(hPPBarang.getIdHpp()))hPPBarang.setIdHpp(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.HPP));
        
        Barang b = barangDao.barangBerdasarkanId(hPPBarang.getBarang().getPlu());
        b.setHargaBeli(hPPBarang.getHppSatuan());
        b.setIsiPembelian(hPPBarang.getIsi());
        
        barangDao.simpan(b);
        System.out.println(b.getNamaBarang());
        hPPDao.simpan(hPPBarang);
        
    }
    
    @Override
    @Transactional
    public void hapus(HPPBarang hPPBarang) {
        hPPDao.hapus(hPPBarang);
    }
    
    @Override
    public List<HPPBarang> hPPBarangs(Barang barang) {
        return (List<HPPBarang>) hPPDao.semua(barang);
    }
    
    @Override
    public HPPBarang hPPBarangBerdasarkanId(String id) {
        return hPPDao.hppBerdasarkanId(id);
    }
    
//</editor-fold>
    
}
