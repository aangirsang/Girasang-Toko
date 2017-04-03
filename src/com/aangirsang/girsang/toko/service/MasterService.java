/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service;

import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import com.aangirsang.girsang.toko.model.master.HPPBarang;
import com.aangirsang.girsang.toko.model.master.RunningNumber;
import com.aangirsang.girsang.toko.model.master.SatuanBarang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import java.util.List;

/**
 *
 * @author GIRSANG PC
 */
public interface MasterService {
    //Running Number
    public void simpan(RunningNumber p);
    public List<RunningNumber> semuaRunningNumber();
    public String ambilBerikutnya(MasterRunningNumberEnum id);
    //Golongan Barang
    public void simpan (GolonganBarang golonganBarang);
    public void hapus (GolonganBarang golonganBarang);
    public List<GolonganBarang> semuaGolonganBarang();
    public List<GolonganBarang> cariNamaGolonganBarang(String GolonganBarang);
    public GolonganBarang golonganBarangBerdasarkanId(String id);
    public List<GolonganBarang> golonganBarangAsc();
    //Satuan Barang
    public void simpan (SatuanBarang satuanBarang);
    public void hapus (SatuanBarang satuanBarang);
    public List<SatuanBarang> semuaSatuanBarang();
    public List<SatuanBarang> cariNamaSatuanBarang(String SatuanBarang);
    public SatuanBarang satuanBarangBerdasarkanId(String id);
    public List<SatuanBarang> satuanBarangAsc();
    //Supplier
    public void simpan (Supplier supplier);
    public void hapus (Supplier supplier);
    public List<Supplier> semuaSupplier();
    public List<Supplier> cariNamaSupplier(String Supplier);
    public Supplier supplierBerdasarkanId(String id);
    //Barang
    public void simpan (Barang barang);
    public void hapus (Barang barang);
    public List<Barang> semuaBarang();
    public List<Barang> cariNamaBarang(String Barang);
    public Barang barangBerdasarkanId(String id);
    public List<Barang> cariStokTokoBarang(int min, int max);
    public List<PembelianDetail> historyPembelian(Barang barang);
    
    public void simpan (HPPBarang hPPBarang);
    public void hapus (HPPBarang hPPBarang);
    public List<HPPBarang> hPPBarangs(Barang barang);
    public HPPBarang hPPBarangBerdasarkanId(String id);
}
