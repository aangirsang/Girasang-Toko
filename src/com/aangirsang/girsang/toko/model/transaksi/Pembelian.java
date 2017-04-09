/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi;

import com.aangirsang.girsang.toko.model.master.Supplier;
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
@Table(name="PEMBELIAN")
public class Pembelian implements Serializable{
    @Id
    @Column(name="NO_REF",length=15)
    private String noRef;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TANGGAL")
    private Date tanggal;
    
    @Column(name="NO_FAKTUR",length=20)
    private String noFaktur;
    
    @ManyToOne
    @JoinColumn(name="SUPPLIER")
    private Supplier supplier;
    
    @Column(name="KREDIT",nullable=false)
    private Boolean kredit = Boolean.TRUE;
    
    private Kredit daftarKredit;
    
    @Column(name = "TOTAL")
    private BigDecimal total = BigDecimal.ZERO;
    
    @Column(name="PEMBUAT")
    private String pembuat;
    
    @Column(name="LOKASI")
    private String lokasi;

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
    
    @OneToMany(mappedBy = "pembelian", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<PembelianDetail> pembelianDetails = new ArrayList<PembelianDetail>();

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

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Boolean getKredit() {
        return kredit;
    }

    public void setKredit(Boolean kredit) {
        this.kredit = kredit;
    }

    public Kredit getDaftarKredit() {
        return daftarKredit;
    }

    public void setDaftarKredit(Kredit daftarKredit) {
        this.daftarKredit = daftarKredit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
    }

    public List<PembelianDetail> getPembelianDetails() {
        return pembelianDetails;
    }

    public void setPembelianDetails(List<PembelianDetail> pembelianDetails) {
        this.pembelianDetails = pembelianDetails;
        if(pembelianDetails !=null && !pembelianDetails.isEmpty()){
            for(PembelianDetail detail : pembelianDetails){
                detail.setPembelian(this);
            }
        }
    }
    
    public void addPembelianDetails(PembelianDetail detail){
        if(pembelianDetails==null){
            pembelianDetails = new ArrayList<PembelianDetail>();
        }
        pembelianDetails.add(detail);
        detail.setPembelian(this);
    }

    public void removePembelianDetails(PembelianDetail detail){
        if(pembelianDetails==null){
            pembelianDetails = new ArrayList<PembelianDetail>();
        }
        pembelianDetails.remove(detail);
        detail.setPembelian(null);
    }
}
