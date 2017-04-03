/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.transaksi;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.transaksi.Kredit;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class PembelianDao extends BaseDaoHibernate<Pembelian>{
    public Pembelian cariBerdasarId(String id) {
        Pembelian pembelian = (Pembelian) sessionFactory.getCurrentSession().get(Pembelian.class, id);
        if(pembelian!=null){
            Hibernate.initialize(pembelian.getPembelianDetails());
            for(PembelianDetail d : pembelian.getPembelianDetails()){
                Hibernate.initialize(d.getBarang());
            }
        }
        return pembelian;
    }
    public PembelianDetail penjualanTerakhir(Barang barang, Pembelian pembelian) {
        List<PembelianDetail> pembelianDetails =
                sessionFactory.getCurrentSession()
                .createQuery("from PembelianDetail p " +
                "where p.barang=:barang " +
                "and p.pembelian<>:pembelian " +
                "order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .setParameter("pembelian", pembelian)
                .setFirstResult(0)
                .setMaxResults(1)
                .list();
        if(pembelianDetails.isEmpty()){
            return null;
        } else {
            return pembelianDetails.get(0);
        }
    }
    public List<Pembelian> semuaDesc() {
        return sessionFactory.getCurrentSession().createQuery("from Pembelian p order by p.noRef desc").list();
    }
    public List<PembelianDetail> cariBerdasarkanIDPembelian(Pembelian pembelian) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.pembelian=:pembelian order by p.id asc")
                .setParameter("pembelian", pembelian)
                .list();
    }
    public PembelianDetail cariBerdasarkanIDPembelianDetail(String id) {
        return (PembelianDetail) sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
    public List<Kredit> kredit(BigDecimal sisaKredit) {
        return sessionFactory.getCurrentSession().createQuery("from kredit k where p.sisaKredit=:sisaKredit")
                .setParameter("sisaKredit", sisaKredit)
                .list();
    }
    @Override
    public List<Pembelian> semua() {
        return sessionFactory.getCurrentSession().createQuery("from Pembelian p order by p.tanggal desc").list();
    }
    public List<PembelianDetail> cariBarang(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.barang=:barang order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
    public List<Pembelian> hutangPembelian(Supplier supplier){
        BigDecimal hutang = new BigDecimal(0);
        return (List<Pembelian>) sessionFactory.getCurrentSession().createQuery(
                "from Pembelian p where p.supplier=:supplier and p.daftarKredit.sisaKredit>:hutang")
                .setParameter("supplier", supplier)
                .setParameter("hutang", hutang)
                .list();
    }
    public List<Pembelian> cariSupplier(Supplier s){
        return (List<Pembelian>) sessionFactory.getCurrentSession().createQuery(
                "from Pembelian p where p.supplier=:supplier")
                .setParameter("supplier", s)
                .list();
    }
}
