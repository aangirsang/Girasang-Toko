/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ITSUSAHBRO
 */
@Embeddable
public class Kredit implements Serializable{
    @Temporal(TemporalType.DATE)
    @Column(name = "TANGGAL_TEMPO")
    private Date tanggalTempo;
    
    @Column(name = "JUMLAH_KREDIT")
    private BigDecimal jumlahKredit = BigDecimal.ZERO;
    
    @Column(name = "JUMLAH_BAYAR")
    private BigDecimal jumlahBayar = BigDecimal.ZERO;
    
    @Column(name = "SISA_KREDIT")
    private BigDecimal sisaKredit = BigDecimal.ZERO;

    public Date getTanggalTempo() {
        return tanggalTempo;
    }

    public void setTanggalTempo(Date tanggalTempo) {
        this.tanggalTempo = tanggalTempo;
    }

    public BigDecimal getJumlahKredit() {
        return jumlahKredit;
    }

    public void setJumlahKredit(BigDecimal jumlahKredit) {
        this.jumlahKredit = jumlahKredit;
    }

    public BigDecimal getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(BigDecimal jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public BigDecimal getSisaKredit() {
        return sisaKredit;
    }

    public void setSisaKredit(BigDecimal sisaKredit) {
        this.sisaKredit = sisaKredit;
    }
    
    
}
