/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.transaksi.PembelianDetail;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class BarangDao extends BaseDaoHibernate<Barang> {

    public Barang barangBerdasarkanId(String id) {
        Barang b = (Barang) sessionFactory.getCurrentSession().get(Barang.class, id);
        return b;
    }

    public List<Barang> barangBerdasarkanNama(String brg) {
        List<Barang> b = sessionFactory.getCurrentSession().createQuery("from Barang b where b.namaBarang LIKE :namaBarang")
                .setParameter("namaBarang", "%" + brg.toUpperCase() + "%")
                .list();
        return b;
    }

    @Override
    public List<Barang> semua() {
        List<Barang> b = sessionFactory.getCurrentSession().createQuery("from Barang b order by b.namaBarang asc")
                .list();
        return b;
    }

    public List<Barang> barangStokToko(int min, int max) {
        return sessionFactory.getCurrentSession().createQuery("FROM Barang b WHERE b.stokOpname BETWEEN :min AND :max")
                .setParameter("max", max)
                .setParameter("min", max)
                .list();
    }

    public List<PembelianDetail> historyPembelian(Barang barang) {
        return sessionFactory.getCurrentSession().createQuery("from PembelianDetail p where p.barang =:barang order by p.pembelian.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
}
