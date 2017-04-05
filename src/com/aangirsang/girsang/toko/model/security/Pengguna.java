/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="PENGGUNA")
public class Pengguna implements Serializable{
    @Id
    @Column(name="ID_PENGGUNA", length=7, nullable=false)
    private String idPengguna;
    
    @Column(name="USER_NAME", length=30, nullable=false, unique=true)
    private String userName;
    
    @Column(name="NAMA_LENGKAP", length=30, nullable=false)
    private String namaLengkap;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="TINGKATAKSES",referencedColumnName="ID")
    private TingkatAkses tingkatAkses;
    
    @Column(name="PASSWORD", length=50, nullable=false)
    private String password;
    
    @Column(name="PASSWORD_HINT")
    private String passwordHint;
    
    @Column(name="STATUS", nullable=false)
    private Boolean status = true;
    
    @Column(name="ALAMAT")
    private String alamat;
    
    @Column(name="KONTAK_HP",length=20)
    private String hp;
    
    @Column(name="KONTAK_TELEPON",length=20)
    private String telepon;

    public String getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public TingkatAkses getTingkatAkses() {
        return tingkatAkses;
    }

    public void setTingkatAkses(TingkatAkses tingkatAkses) {
        this.tingkatAkses = tingkatAkses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
