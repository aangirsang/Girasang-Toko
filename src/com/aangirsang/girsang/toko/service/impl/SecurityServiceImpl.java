/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service.impl;

import com.aangirsang.girsang.toko.dao.master.RunningNumberDao;
import com.aangirsang.girsang.toko.dao.security.PenggunaDao;
import com.aangirsang.girsang.toko.dao.security.TingkatAksesDao;
import com.aangirsang.girsang.toko.model.master.constant.MasterRunningNumberEnum;
import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import com.aangirsang.girsang.toko.service.SecurityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIRSANG PC
 */
@Service("SecurityService")
@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService{
    
    @Autowired PenggunaDao penggunaDao;
    @Autowired TingkatAksesDao tingkatAksesDao;
    @Autowired RunningNumberDao runningNumberDao;

    //<editor-fold defaultstate="collapsed" desc="Pengguna">
    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Pengguna p) {
        if(p.getIdPengguna().equals(""))p.setIdPengguna(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.PENGGUNA));
        penggunaDao.simpan(p);
    }
    
    @Override
    @Transactional
    public void hapus(Pengguna p) {
        penggunaDao.hapus(p);
    }
    
    @Override
    public Pengguna cariIdPengguna(String id) {
        return penggunaDao.cariID(id);
    }
    
    @Override
    public Pengguna cariUserNamePengguna(String userName) {
        return penggunaDao.cariUsername(userName);
    }
    
    @Override
    public Pengguna login(String userName, String password) {
        return penggunaDao.login(userName, password);
    }
    
    @Override
    public List<Pengguna> semuaPengguna() {
        return penggunaDao.semua();
    }
    
    @Override
    public List<Pengguna> cariNamaLengkap(String namaLengkap) {
        return penggunaDao.cariNamaLengkap(namaLengkap);
    }
    
    @Override
    public List<Pengguna> cariTingkatAkses(TingkatAkses tingkatAkses) {
        return penggunaDao.cariTingkatAkses(tingkatAkses);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TingkatAkses">
    @Override
    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(TingkatAkses tA) {
        if("".equals(tA.getId()))tA.setId(runningNumberDao.ambilBerikutnyaDanSimpan(MasterRunningNumberEnum.TINGKATAKSES));
        tingkatAksesDao.simpan(tA);
    }
    
    @Override
    @Transactional
    public void hapus(TingkatAkses tA) {
        tingkatAksesDao.hapus(tA);
    }
    
    @Override
    public TingkatAkses cariIdTingkatAkses(String id) {
        return tingkatAksesDao.cariId(id);
    }
    
    @Override
    public List<TingkatAkses> semuaTingkatAkses() {
        return tingkatAksesDao.semua();
    }
//</editor-fold>
    
}
