/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi;

import com.aangirsang.girsang.toko.model.master.Barang;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ITSUSAHBRO
 */
@Entity
@Table(name="DETAILRETURPEMBELIAN")
public class ReturPembelianDetail implements Serializable{
    @Id
    @Column(name="ID",length=20)
    private String id;

    @ManyToOne
    @JoinColumn(name = "RETURPEMBELIAN", nullable = false)
    private ReturPembelian returPembelian;
    
    @ManyToOne
    @JoinColumn(name = "BARANG", nullable = false)
    private Barang barang;
    
    @Column(name = "KUANTITAS_RETURPEMBELIAN", nullable = false)
    private Integer kuantitas = 0;
    
    @Column(name="SATUAN_RETURPEMBELIAN",length=20)
    private String satuanPembelian;

    @Column(name = "HARGA_BELI", nullable = false)
    private BigDecimal hargaBarang = BigDecimal.ZERO;
    
    @Column(name = "SUB_TOTAL", nullable = false)
    private BigDecimal subTotal = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReturPembelian getReturPembelian() {
        return returPembelian;
    }

    public void setReturPembelian(ReturPembelian returPembelian) {
        this.returPembelian = returPembelian;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Integer getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(Integer kuantitas) {
        this.kuantitas = kuantitas;
    }

    public String getSatuanPembelian() {
        return satuanPembelian;
    }

    public void setSatuanPembelian(String satuanPembelian) {
        this.satuanPembelian = satuanPembelian;
    }

    public BigDecimal getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(BigDecimal hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReturPembelianDetail other = (ReturPembelianDetail) obj;
        return !((this.id == null) ? (other.id != null) : !this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
