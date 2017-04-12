/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.dao.security;

import com.aangirsang.girsang.toko.dao.BaseDaoHibernate;
import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIRSANG PC
 */
@Repository
public class PenggunaDao extends BaseDaoHibernate<Pengguna>{
    public Pengguna cariID(String Id){
        return (Pengguna) sessionFactory.getCurrentSession().get(Pengguna.class, Id);
    }
    public Pengguna cariUsername(String userName){
        return (Pengguna) sessionFactory.getCurrentSession().createQuery(
                "from Pengguna p where p.userName=:userName")
                .setString("userName", userName)
                .uniqueResult();
    }
    public Pengguna login(String userName, String password){
        return (Pengguna) sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.userName=:userName and"
                        + " p.password=:password")
                .setString("userName", userName)
                .setString("password", password)
                .uniqueResult();
    }
    @Override
    public List<Pengguna> semua(){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p Order By p.namaLengkap asc")
                .list();
    }
    public List<Pengguna> cariNamaLengkap(String namaLengkap){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.namaLengkap LIKE :namaLengkap "
                        + "Order By p.tingkatAkses asc And Order By p.namaLengkap asc")
                .setParameter("namaLengkap", namaLengkap)
                .list();
    }
    public List<Pengguna> cariTingkatAkses(TingkatAkses tingkatAkses){
        return sessionFactory.getCurrentSession().createQuery(
                "From Pengguna p where p.tingkatAkses=:tingkatAkses "
                        + "order By p.namaLengkap asc")
                .setParameter("tingkatAkses", tingkatAkses)
                .list();
    }
}
