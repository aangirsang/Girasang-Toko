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
@Table(name="MST_GOLONGAN_BARANG")
public class GolonganBarang implements Serializable{
    @Id
    @Column(name="ID",length=6)
    private String id;
    
    @Column(name="GOLONGAN_BARANG",length=50)
    private String golonganBarang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGolonganBarang() {
        return golonganBarang;
    }

    public void setGolonganBarang(String golonganBarang) {
        this.golonganBarang = golonganBarang;
    }

    
    
}
