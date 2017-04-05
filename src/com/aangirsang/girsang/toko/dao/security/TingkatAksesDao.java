/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.security;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class TingkatAksesDao extends BaseDaoHibernate<TingkatAkses>{
    public TingkatAkses cariId(String id){
        return (TingkatAkses) sessionFactory.getCurrentSession().get(TingkatAkses.class, id);
    }
    @Override
    public List<TingkatAkses> semua(){
        return sessionFactory.getCurrentSession().createQuery(
                "From TingkatAkses t order By t.namaTingkatAkses asc")
                .list();
    }
    
}
