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
import javax.persistence.Table;

/**
 *
 * @author GIRSANG PC
 */
@Entity
@Table(name="TINGKATAKSES")
public class TingkatAkses implements Serializable{
    @Id
    @Column(name="ID_TINGKATAKSES", length=6, nullable=false)
    private String id;
    
    @Column(name="NAMA_TINGKATAKSES", length=30, nullable=false)
    private String namaTingkatAkses;
    
    @Column(name="KET_TINGKATAKSES")
    private String ketTingkatAkses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaTingkatAkses() {
        return namaTingkatAkses;
    }

    public void setNamaTingkatAkses(String namaTingkatAkses) {
        this.namaTingkatAkses = namaTingkatAkses;
    }

    public String getKetTingkatAkses() {
        return ketTingkatAkses;
    }

    public void setKetTingkatAkses(String ketTingkatAkses) {
        this.ketTingkatAkses = ketTingkatAkses;
    }
}
