/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.GolonganBarang;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class GolonganBarangDao extends BaseDaoHibernate<GolonganBarang>{
    public GolonganBarang golonganBarangBerdasarkanId (String id){
        return (GolonganBarang) sessionFactory.getCurrentSession().get(GolonganBarang.class, id);
    }
    
    public List<GolonganBarang> golonganBarangBerdasarkanNama(String GolonganBarang) {
        return sessionFactory.getCurrentSession().createQuery("from GolonganBarang k where k.golonganBarang LIKE :GolonganBarang")
                .setParameter("GolonganBarang",  "%" + GolonganBarang.toUpperCase() + "%")
                .list();
    }
    
    public List<GolonganBarang> golonganBarangsAsc(){
        return sessionFactory.getCurrentSession().createQuery("from GolonganBarang g order by g.golonganBarang Asc")
                .list();
    }
        
}
