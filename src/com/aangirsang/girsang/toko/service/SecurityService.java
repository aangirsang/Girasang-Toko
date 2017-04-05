/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.service;

import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.model.security.TingkatAkses;
import java.util.List;

/**
 *
 * @author GIRSANG PC
 */
public interface SecurityService {
    
    public void simpan(Pengguna p);
    public void hapus(Pengguna p);
    public Pengguna cariIdPengguna(String id);
    public Pengguna cariUserNamePengguna(String userName);
    public Pengguna login(String userName, String password);
    public List<Pengguna> semuaPengguna();
    public List<Pengguna> cariNamaLengkap(String namaLengkap);
    public List<Pengguna> cariTingkatAkses(TingkatAkses tingkatAkses);
    
    public void simpan(TingkatAkses tA);
    public void hapus(TingkatAkses tA);
    public TingkatAkses cariIdTingkatAkses(String id);
    public List<TingkatAkses> semuaTingkatAkses();
}
