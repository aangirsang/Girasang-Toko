/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aangirsang.girsang.toko.dao.master;

import com.aangirsang.girsang.toko.model.master.RunningNumber;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.util.StringUtils;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifnu
 */
@Repository
public class RunningNumberDao {

    @Autowired private SessionFactory sessionFactory;

    public void simpan(RunningNumber p){
        sessionFactory.getCurrentSession()
                .saveOrUpdate(p);
    }

    public List<RunningNumber> semua(){
        return sessionFactory.getCurrentSession()
                .createCriteria(RunningNumber.class)
                .list();
    }

    public void hapus(RunningNumber p){
        sessionFactory.getCurrentSession().delete(p);
    }

    public String ambilBerikutnya(MasterRunningNumberEnum id){
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if(r==null){
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(0);
            sessionFactory.getCurrentSession().save(r);
        }
        return id.getId() + StringUtils.padWithZero(r.getNumber() + 1, id.getDigit());
    }

    public String ambilBerikutnyaDanSimpan(MasterRunningNumberEnum id) {
        RunningNumber r = (RunningNumber) sessionFactory.getCurrentSession().get(RunningNumber.class, id.getId());
        if(r == null){
            r = new RunningNumber();
            r.setId(id.getId());
            r.setNumber(1);
        } else {
            r.setNumber(r.getNumber()+1);
        }
        sessionFactory.getCurrentSession().saveOrUpdate(r);
        return id.getId() + StringUtils.padWithZero(r.getNumber(), id.getDigit());
    }

}
