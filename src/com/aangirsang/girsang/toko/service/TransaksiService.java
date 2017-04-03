/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service;

import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.RunningNumber;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutang;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutangDetail;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import com.aangirsang.girsang.toko.model.transaksi.constant.TransaksiRunningNumberEnum;
import java.util.List;

/**
 *
 * @author ITSUSAHBRO
 */
public interface TransaksiService {
    //Running Number
    public void simpan(RunningNumber r);
    public List<RunningNumber> semuaRunningNumber();
    public String ambilBerikutnya(TransaksiRunningNumberEnum id);
    //Pembelian
    public void simpan(Pembelian p);
    public Pembelian cariPembelian(String id);
    public List<Pembelian> semuaPembelian();
    public List<Pembelian> descPembelian();
    public List<Pembelian> hutangPembelian(Supplier s);
    public List<PembelianDetail> cariPembelianDetail(Pembelian p);
    public List<PembelianDetail> cariBarang(Barang barang);
    
    public void simpan(PelunasanHutang p);
    public PelunasanHutang cariId(String id);
    public List<PelunasanHutang> semua();
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang p);
    
}
