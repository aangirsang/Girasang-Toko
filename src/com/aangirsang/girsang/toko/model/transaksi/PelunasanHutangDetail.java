/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="TR_PULNASAN_HUTANG_DETAIL")
public class PelunasanHutangDetail implements Serializable{
    @Id
    @Column(name="ID",length=20)
    private String id;

    @ManyToOne
    @JoinColumn(name = "PELUNASAN_HUTANG", nullable = false)
    private PelunasanHutang pelunasanHutang;
    
    @OneToOne
    @JoinColumn(name = "PEMBELIAN", nullable = false)
    private Pembelian pembelian;
    
    @Column(name = "SISA_HUTANG")
    private BigDecimal sisaHutang = BigDecimal.ZERO;
    
    @Column(name = "PEMBAYARAN")
    private BigDecimal pembayaran = BigDecimal.ZERO;
    
    @Column(name = "HUTANG_AKHIR")
    private BigDecimal hutangAkhir = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PelunasanHutang getPelunasanHutang() {
        return pelunasanHutang;
    }

    public void setPelunasanHutang(PelunasanHutang pelunasanHutang) {
        this.pelunasanHutang = pelunasanHutang;
    }

    public Pembelian getPembelian() {
        return pembelian;
    }

    public void setPembelian(Pembelian pembelian) {
        this.pembelian = pembelian;
    }

    public BigDecimal getSisaHutang() {
        return sisaHutang;
    }

    public void setSisaHutang(BigDecimal sisaHutang) {
        this.sisaHutang = sisaHutang;
    }

    public BigDecimal getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(BigDecimal pembayaran) {
        this.pembayaran = pembayaran;
    }

    public BigDecimal getHutangAkhir() {
        return hutangAkhir;
    }

    public void setHutangAkhir(BigDecimal hutangAkhir) {
        this.hutangAkhir = hutangAkhir;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PelunasanHutangDetail other = (PelunasanHutangDetail) obj;
        return !((this.id == null) ? (other.id != null) : !this.id.equals(other.id));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
