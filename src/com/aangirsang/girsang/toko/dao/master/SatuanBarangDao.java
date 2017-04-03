/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.SatuanBarang;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class SatuanBarangDao extends BaseDaoHibernate<SatuanBarang>{
    public SatuanBarang satuanBarangBerdasarkanId (String id){
        return (SatuanBarang) sessionFactory.getCurrentSession().get(SatuanBarang.class, id);
    }
    
    public List<SatuanBarang> satuanBarangBerdasarkanNama(String SatuanBarang) {
        return sessionFactory.getCurrentSession().createQuery("from SatuanBarang k where k.satuanBarang LIKE :SatuanBarang")
                .setParameter("SatuanBarang",  "%" + SatuanBarang.toUpperCase() + "%")
                .list();
    }
    public List<SatuanBarang> satuanBarangsAsc(){
        return sessionFactory.getCurrentSession().createQuery("from SatuanBarang s order by s.satuanBarang Asc")
                .list();
    }
}
