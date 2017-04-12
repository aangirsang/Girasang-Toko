/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi;

import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.security.Pengguna;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author ITSUSAHBRO
 */
@Entity
@Table(name="RETURPEMBELIAN")
public class ReturPembelian implements Serializable{
    @Id
    @Column(name="NO_REF",length=15)
    private String noRef;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TANGGAL")
    private Date tanggal;
        
    @ManyToOne
    @JoinColumn(name="PEMBELIAN")
    private Pembelian pembelian;
    
    @ManyToOne
    @JoinColumn(name="SUPPLIER")
    private Supplier supplier;
        
    @Column(name = "TOTALRETUR")
    private Integer total = 0;
    
    @ManyToOne
    @JoinColumn(name="PEMBUAT")
    private Pengguna pembuat;
    
    @OneToMany(mappedBy = "returPembelian", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<ReturPembelianDetail> returPembelianDetails = new ArrayList<ReturPembelianDetail>();

    public String getNoRef() {
        return noRef;
    }

    public void setNoRef(String noRef) {
        this.noRef = noRef;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Pengguna getPembuat() {
        return pembuat;
    }

    public void setPembuat(Pengguna pembuat) {
        this.pembuat = pembuat;
    }

    public List<ReturPembelianDetail> getReturPembelianDetails() {
        return returPembelianDetails;
    }

    public void setReturPembelianDetails(List<ReturPembelianDetail> returPembelianDetails) {
        this.returPembelianDetails = returPembelianDetails;
        if(returPembelianDetails !=null && !returPembelianDetails.isEmpty()){
            for(ReturPembelianDetail detail : returPembelianDetails){
                detail.setReturPembelian(this);
            }
        }
    }
    
    public void addReturPembelianDetails(ReturPembelianDetail detail){
        if(returPembelianDetails==null){
            returPembelianDetails = new ArrayList<ReturPembelianDetail>();
        }
        returPembelianDetails.add(detail);
        detail.setReturPembelian(this);
    }

    public Pembelian getPembelian() {
        return pembelian;
    }

    public void setPembelian(Pembelian pembelian) {
        this.pembelian = pembelian;
    }

    public void removePembelianDetails(ReturPembelianDetail detail){
        if(returPembelianDetails==null){
            returPembelianDetails = new ArrayList<ReturPembelianDetail>();
        }
        returPembelianDetails.remove(detail);
        detail.setReturPembelian(null);
    }
}
