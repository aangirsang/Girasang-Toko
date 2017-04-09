/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.master;

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
@Table(name="SATUANUMUM")
public class SatuanBarang implements Serializable{
    @Id
    @Column(name="ID_SATUANUMUM",length=6)
    private String id;
    
    @Column(name="NAMA_SATUANUMUM",length=50)
    private String satuanBarang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSatuanBarang() {
        return satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
    }
    
}
