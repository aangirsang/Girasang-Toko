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
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutang;
import com.aangirsang.girsang.toko.model.transaksi.PelunasanHutangDetail;
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
public class PelunasanHutangDao extends BaseDaoHibernate<PelunasanHutang>{
    public PelunasanHutang cariId(String id) {
        PelunasanHutang pelunasanHutang = (PelunasanHutang) sessionFactory.
                getCurrentSession().get(PelunasanHutang.class, id);
        if(pelunasanHutang!=null){
            Hibernate.initialize(pelunasanHutang.getPelunasanHutangDetails());
            for(PelunasanHutangDetail d : pelunasanHutang.getPelunasanHutangDetails()){
                Hibernate.initialize(d.getPembelian());
            }
        }
        return pelunasanHutang;
    }
    @Override
    public List<PelunasanHutang> semua() {
        return sessionFactory.getCurrentSession().createQuery("from PelunasanHutang p order by p.noRef desc").list();
    }
    public List<PelunasanHutang> cariSupplier(Supplier s) {
        return sessionFactory.getCurrentSession().createQuery("from PelunasanHutang p where p.supplier=:s order by p.id asc")
                .setParameter("s", s)
                .list();
    }
    public List<PelunasanHutangDetail> cariDetail(PelunasanHutang pelunasanHutang) {
        return sessionFactory.getCurrentSession().createQuery("from PelunasanHutangDetail p where p.pelunasanHutang=:pelunasanHutang order by p.id asc")
                .setParameter("pelunasanHutang", pelunasanHutang)
                .list();
    }
    public PelunasanHutangDetail cariIDPelunasanHutangDetail(String id) {
        return (PelunasanHutangDetail) sessionFactory.getCurrentSession().createQuery("from PelunasanHutangDetail p where p.id=:id order by p.id asc")
                .setParameter("id", id);
    }
    public List<PelunasanHutangDetail> cariPembelian(Pembelian pembelian){
        return sessionFactory.getCurrentSession().createQuery(
                "from PelunasanHutangDetail p where p.pembelian=:pembelian order by p.id asc")
                .setParameter("pembelian", pembelian)
                .list();
    }
}
