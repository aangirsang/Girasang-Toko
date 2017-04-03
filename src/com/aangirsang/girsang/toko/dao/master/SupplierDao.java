/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.master.Supplier;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class SupplierDao extends BaseDaoHibernate<Supplier>{
    public Supplier supplierBerdasarkanId (String id){
        return  (Supplier) sessionFactory.getCurrentSession().get(Supplier.class, id);
    }
    
    public List<Supplier> supplierBerdasarkanNama(String supplier) {
        return sessionFactory.getCurrentSession().createQuery("from Supplier s where s.namaSupplier LIKE :namaSupplier")
                .setParameter("namaSupplier",  "%" + supplier.toUpperCase() + "%")
                .list();
    }
}
