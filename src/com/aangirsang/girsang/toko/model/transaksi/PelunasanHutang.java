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
 * @author GIRSANG PC
 */
@Entity
@Table(name="TR_PELUNASAN_HUTANG")
public class PelunasanHutang implements Serializable{
    @Id
    @Column(name="NO_REF",length=15)
    private String noRef;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TANGGAL")
    private Date tanggal;
    
    @Column(name="NO_KWITANSI",length=20)
    private String noKwitansi;
    
    @ManyToOne
    @JoinColumn(name="SUPPLIER")
    private Supplier supplier;

    @Column(name = "JLH_BAYAR")
    private BigDecimal jlhBayar = BigDecimal.ZERO;

    @Column(name="PEMBUAT")
    private String pembuat;
    
    @OneToMany(mappedBy = "pelunasanHutang", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<PelunasanHutangDetail> pelunasanHutangDetails = new ArrayList<PelunasanHutangDetail>();

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

    public String getNoKwitansi() {
        return noKwitansi;
    }

    public void setNoKwitansi(String noKwitansi) {
        this.noKwitansi = noKwitansi;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getJlhBayar() {
        return jlhBayar;
    }

    public void setJlhBayar(BigDecimal jlhBayar) {
        this.jlhBayar = jlhBayar;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
    }

    public List<PelunasanHutangDetail> getPelunasanHutangDetails() {
        return pelunasanHutangDetails;
    }

    public void setPelunasanHutangDetails(List<PelunasanHutangDetail> pelunasanHutangDetails) {
        this.pelunasanHutangDetails = pelunasanHutangDetails;
        if(pelunasanHutangDetails !=null && !pelunasanHutangDetails.isEmpty()){
            for(PelunasanHutangDetail detail : pelunasanHutangDetails){
                detail.setPelunasanHutang(this);
            }
        }
    }
    public void addPembelianDetails(PelunasanHutangDetail detail){
        if(pelunasanHutangDetails==null){
            pelunasanHutangDetails = new ArrayList<PelunasanHutangDetail>();
        }
        pelunasanHutangDetails.add(detail);
        detail.setPelunasanHutang(this);
    }

    public void removePembelianDetails(PelunasanHutangDetail detail){
        if(pelunasanHutangDetails==null){
            pelunasanHutangDetails = new ArrayList<PelunasanHutangDetail>();
        }
        pelunasanHutangDetails.remove(detail);
        detail.setPelunasanHutang(null);
    }
}
