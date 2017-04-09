/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.master;

import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="BARANG")
public class Barang implements Serializable{
    @Id
    @Column(name="PLU",length=8)
    private String plu;
    
    @Column(name="NAMA_BARANG",length=100)
    private String namaBarang;

    @Column(name="BARCODE1",length=30)
    private String barcode1;
    
    @Column(name="BARCODE2",length=30)
    private String barcode2;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="GOLONGAN",referencedColumnName="ID_GOLONGANBARANG")
    private GolonganBarang golonganBarang;
    
    @Column(name="SATUAN",length=20)
    private String satuan;
    
    @Column(name="SATUAN_PEMBELIAN",length=20)
    private String satuanPembelian;
    
    @Column(name="JUAL",nullable=false)
    private Boolean jual = Boolean.TRUE;
    
    @Column(name="HARGA_BELI")
    private BigDecimal hargaBeli = BigDecimal.ZERO;
    
    @Column(name="HARGA_NORMAL")
    private BigDecimal hargaNormal = BigDecimal.ZERO;
    
    @Column(name="HARGA_MEMBER")
    private BigDecimal hargaMember = BigDecimal.ZERO;
    
    @Column(name="ISI_PEMBELIAN",nullable=false)
    private Integer isiPembelian=0;
    
    @Column(name="STOK_Max",nullable=false)
    private Integer stokMax=0;
    
    @Column(name="STOK_Min",nullable=false)
    private Integer stokMin=0;
    
    @Column(name="LIMIT_WARNING",nullable=false)
    private Boolean limitWarning = Boolean.FALSE;
    
    @Column(name="STOK_GUDANG",nullable=false)
    private Integer stokGudang=0;
    
    @Column(name="STOK_TOKO",nullable=false)
    private Integer stokToko=0;
    
    @Column(name="TOTAL_KUANTITAS_PEMBELIAN_TOKO",nullable=false)
    private Integer stokPembelianToko=0;
    
    @Column(name="TOTAL_KUANTITAS_PEMBELIAN_GUDANG",nullable=false)
    private Integer stokPembelianGudang=0;
    
    @Column(name="TOTAL_KUANTITAS_PENJUALAN_TOKO",nullable=false)
    private Integer stokPenjualanToko=0;
    
    @Column(name="TOTAL_KUANTITAS_PENJUALAN_GUDANG",nullable=false)
    private Integer stokPenjualanGudang=0;
    
    public Integer getKalkulasiStokToko(){
        return 
                stokToko + 
                stokPembelianToko - 
                stokPenjualanToko;
    }
    
    public Integer getKalkulasiStokGudang(){
        return 
                stokGudang +
                stokPembelianGudang -
                stokPenjualanGudang;
    }

    public String getPlu() {
        return plu;
    }

    public void setPlu(String plu) {
        this.plu = plu;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getBarcode1() {
        return barcode1;
    }

    public void setBarcode1(String barcode1) {
        this.barcode1 = barcode1;
    }

    public String getBarcode2() {
        return barcode2;
    }

    public void setBarcode2(String barcode2) {
        this.barcode2 = barcode2;
    }

    public GolonganBarang getGolonganBarang() {
        return golonganBarang;
    }

    public void setGolonganBarang(GolonganBarang golonganBarang) {
        this.golonganBarang = golonganBarang;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getSatuanPembelian() {
        return satuanPembelian;
    }

    public void setSatuanPembelian(String satuanPembelian) {
        this.satuanPembelian = satuanPembelian;
    }

    public Boolean getJual() {
        return jual;
    }

    public void setJual(Boolean jual) {
        this.jual = jual;
    }

    public BigDecimal getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(BigDecimal hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public BigDecimal getHargaNormal() {
        return hargaNormal;
    }

    public void setHargaNormal(BigDecimal hargaNormal) {
        this.hargaNormal = hargaNormal;
    }

    public BigDecimal getHargaMember() {
        return hargaMember;
    }

    public void setHargaMember(BigDecimal hargaMember) {
        this.hargaMember = hargaMember;
    }

    public Integer getIsiPembelian() {
        return isiPembelian;
    }

    public void setIsiPembelian(Integer isiPembelian) {
        this.isiPembelian = isiPembelian;
    }

    public Integer getStokMax() {
        return stokMax;
    }

    public void setStokMax(Integer stokMax) {
        this.stokMax = stokMax;
    }

    public Integer getStokMin() {
        return stokMin;
    }

    public void setStokMin(Integer stokMin) {
        this.stokMin = stokMin;
    }

    public Boolean getLimitWarning() {
        return limitWarning;
    }

    public void setLimitWarning(Boolean limitWarning) {
        this.limitWarning = limitWarning;
    }

    public Integer getStokGudang() {
        return stokGudang;
    }

    public void setStokGudang(Integer stokGudang) {
        this.stokGudang = stokGudang;
    }

    public Integer getStokToko() {
        return stokToko;
    }

    public void setStokToko(Integer stokToko) {
        this.stokToko = stokToko;
    }

    public Integer getStokPembelianToko() {
        return stokPembelianToko;
    }

    public void setStokPembelianToko(Integer stokPembelianToko) {
        this.stokPembelianToko = stokPembelianToko;
    }

    public Integer getStokPembelianGudang() {
        return stokPembelianGudang;
    }

    public void setStokPembelianGudang(Integer stokPembelianGudang) {
        this.stokPembelianGudang = stokPembelianGudang;
    }

    public Integer getStokPenjualanToko() {
        return stokPenjualanToko;
    }

    public void setStokPenjualanToko(Integer stokPenjualanToko) {
        this.stokPenjualanToko = stokPenjualanToko;
    }

    public Integer getStokPenjualanGudang() {
        return stokPenjualanGudang;
    }

    public void setStokPenjualanGudang(Integer stokPenjualanGudang) {
        this.stokPenjualanGudang = stokPenjualanGudang;
    }
}
