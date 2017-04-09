/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="HPP")
public class HPPBarang implements Serializable{
    @Id
    @Column(name="ID_HPP",length=8)
    private String idHpp;
    
    @ManyToOne
    @JoinColumn(name = "BARANG", nullable = false)
    private Barang barang;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TANGGAL")
    private Date tanggal;
    
    @Column(name = "HPP", nullable = false)
    private BigDecimal hpp = BigDecimal.ZERO;
    
    @Column(name="ISI",nullable=false)
    private Integer isi;
    
    @Column(name = "HPP_Satuan", nullable = false)
    private BigDecimal hppSatuan = BigDecimal.ZERO;

    public String getIdHpp() {
        return idHpp;
    }

    public void setIdHpp(String idHpp) {
        this.idHpp = idHpp;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public BigDecimal getHpp() {
        return hpp;
    }

    public void setHpp(BigDecimal hpp) {
        this.hpp = hpp;
    }

    public Integer getIsi() {
        return isi;
    }

    public void setIsi(Integer isi) {
        this.isi = isi;
    }

    public BigDecimal getHppSatuan() {
        return hppSatuan;
    }

    public void setHppSatuan(BigDecimal hppSatuan) {
        this.hppSatuan = hppSatuan;
    }
    
    
}
