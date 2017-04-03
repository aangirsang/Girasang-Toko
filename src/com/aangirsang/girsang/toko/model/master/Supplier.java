/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.master;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="MST_SUPPLIER")
public class Supplier implements Serializable{
    @Id
    @Column(name="ID",length=6)
    private String id;
    
    @Column(name="NAMA_SUPPLIER",length=30)
    private String namaSupplier;
    
    @Column(name="ALAMAT_SUPPLIER",length=100)
    private String alamatSupplier;
    
    @Column(name="KONTAK_HP",length=20)
    private String hpSupplier;
    
    @Column(name="KONTAK_TELEPON",length=20)
    private String teleponSupplier;
    
    @Column(name="EMAIL",length=50)
    private String emailSupplier;
    
    @Column(name="KOTA",length=20)
    private String kotaSupplier;
    
    @Column(name="KODE_POS",length=10)
    private String kodePos;
    
    @Column(name="SALDO_HUTANG")
    private BigDecimal saldoHutang = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getAlamatSupplier() {
        return alamatSupplier;
    }

    public void setAlamatSupplier(String alamatSupplier) {
        this.alamatSupplier = alamatSupplier;
    }

    public String getHpSupplier() {
        return hpSupplier;
    }

    public void setHpSupplier(String hpSupplier) {
        this.hpSupplier = hpSupplier;
    }

    public String getTeleponSupplier() {
        return teleponSupplier;
    }

    public void setTeleponSupplier(String teleponSupplier) {
        this.teleponSupplier = teleponSupplier;
    }

    public String getEmailSupplier() {
        return emailSupplier;
    }

    public void setEmailSupplier(String emailSupplier) {
        this.emailSupplier = emailSupplier;
    }

    public String getKotaSupplier() {
        return kotaSupplier;
    }

    public void setKotaSupplier(String kotaSupplier) {
        this.kotaSupplier = kotaSupplier;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public BigDecimal getSaldoHutang() {
        return saldoHutang;
    }

    public void setSaldoHutang(BigDecimal saldoHutang) {
        this.saldoHutang = saldoHutang;
    }
    
    
}
