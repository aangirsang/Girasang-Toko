/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.Barang;
import com.aangirsang.girsang.toko.model.master.HPPBarang;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class HPPDao extends BaseDaoHibernate<HPPBarang>{
    public HPPBarang hppBerdasarkanId(String id){
        return (HPPBarang) sessionFactory.getCurrentSession().get(HPPBarang.class, id);
    }
    public List<HPPBarang> semua(Barang barang){
        return sessionFactory.getCurrentSession().createQuery("from HPPBarang h where h.barang =:barang order by h.tanggal desc")
                .setParameter("barang", barang)
                .list();
    }
}
