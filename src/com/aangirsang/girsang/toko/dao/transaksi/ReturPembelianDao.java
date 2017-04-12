/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.transaksi;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.Supplier;
import com.aangirsang.girsang.toko.model.transaksi.Pembelian;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import com.aangirsang.girsang.toko.model.transaksi.ReturPembelian;
import com.aangirsang.girsang.toko.model.transaksi.ReturPembelianDetail;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ITSUSAHBRO
 */
@Repository
public class ReturPembelianDao extends BaseDaoHibernate<ReturPembelian>{
    public ReturPembelian cariBerdasarId(String id) {
        ReturPembelian returPembelian = (ReturPembelian) sessionFactory.getCurrentSession().get(ReturPembelian.class, id);
        if(returPembelian!=null){
            Hibernate.initialize(returPembelian.getReturPembelianDetails());
            for(ReturPembelianDetail d : returPembelian.getReturPembelianDetails()){
                Hibernate.initialize(d.getBarang());
            }
        }
        return returPembelian;
    }
    public List<Pembelian> semuaDesc() {
        return sessionFactory.getCurrentSession().createQuery("from ReturPembelian p order by p.noRef desc").list();
    }
    public List<PembelianDetail> cariIDReturBeli(ReturPembelian returPembelian) {
        return sessionFactory.getCurrentSession().createQuery("from ReturPembelianDetail p where p.returPembelian=:returPembelian order by p.id asc")
                .setParameter("returPembelian", returPembelian)
                .list();
    }
    public ReturPembelianDetail cariIdReturBeliDetail(String id) {
        return (ReturPembelianDetail) sessionFactory.getCurrentSession().createQuery("from ReturPembelianDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
    @Override
    public List<ReturPembelian> semua() {
        return sessionFactory.getCurrentSession().createQuery("from Pembelian p order by p.tanggal desc").list();
    }
    public List<PembelianDetail> cariBarang(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.barang=:barang order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
    public List<ReturPembelian> cariSupplier(Supplier s){
        return (List<ReturPembelian>) sessionFactory.getCurrentSession().createQuery(
                "from ReturPembelian p where p.supplier=:supplier")
                .setParameter("supplier", s)
                .list();
    }
}
