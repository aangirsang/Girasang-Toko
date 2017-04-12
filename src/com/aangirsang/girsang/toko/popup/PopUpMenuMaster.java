/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.popup;

import com.aangirsang.girsang.toko.ui.master.barang.BarangPanel;
import com.aangirsang.girsang.toko.ui.master.GolonganBarangPanel;
import com.aangirsang.girsang.toko.ui.master.SatuanBarangPanel;
import com.aangirsang.girsang.toko.ui.master.supplier.SupplierPanel;
import com.aangirsang.girsang.toko.ui.security.PenggunaPanel;
import com.aangirsang.girsang.toko.ui.security.TingkatAksesPanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author ITSUSAHBRO
 */
public class PopUpMenuMaster extends AbstractButton {

    TingkatAksesPanel aksesPanel = new TingkatAksesPanel();
    PenggunaPanel penggunaPanel = new PenggunaPanel();
    GolonganBarangPanel golonganBarangPanel = new GolonganBarangPanel();
    SatuanBarangPanel satuanBarangPanel = new SatuanBarangPanel();
    SupplierPanel supplierPanel = new SupplierPanel();
    BarangPanel barangPanel = new BarangPanel();
    JButton actionButton;
    JPopupMenu popupMenu;

    public PopUpMenuMaster(JTabbedPane TP, JPopupMenu popupMenuMaster, JButton btnMaster) {
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Golongan Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                golonganBarangPanel.setName("Daftar Kategori Barang");
                if (golonganBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(golonganBarangPanel.getIndexTab());
                } else {
                    golonganBarangPanel.setAktifPanel(golonganBarangPanel.getAktifPanel() + 1);
                    TP.addTab(golonganBarangPanel.getName(), golonganBarangPanel);
                    golonganBarangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(golonganBarangPanel.getIndexTab());
                    
                    golonganBarangPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(golonganBarangPanel);
                        golonganBarangPanel.setAktifPanel(golonganBarangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                barangPanel.setName("Daftar Barang");
                if (barangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(barangPanel.getIndexTab());
                } else {
                    barangPanel.setAktifPanel(barangPanel.getAktifPanel() + 1);
                    TP.addTab(barangPanel.getName(), barangPanel);
                    barangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(barangPanel.getIndexTab());
                    
                    barangPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(barangPanel);
                        barangPanel.setAktifPanel(barangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Satuan Barang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                satuanBarangPanel.setName("Daftar Satuan Barang");
                if (satuanBarangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(satuanBarangPanel.getIndexTab());
                } else {
                    satuanBarangPanel.setAktifPanel(satuanBarangPanel.getAktifPanel() + 1);
                    TP.addTab(satuanBarangPanel.getName(), satuanBarangPanel);
                    satuanBarangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(satuanBarangPanel.getIndexTab());
                    
                    satuanBarangPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(satuanBarangPanel);
                        satuanBarangPanel.setAktifPanel(satuanBarangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Supplier") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                supplierPanel.setName("Daftar Supplier");
                if (supplierPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(supplierPanel.getIndexTab());
                } else {
                    supplierPanel.setAktifPanel(supplierPanel.getAktifPanel() + 1);
                    TP.addTab(supplierPanel.getName(), supplierPanel);
                    supplierPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(supplierPanel.getIndexTab());
                    
                    supplierPanel.getToolbarTanpaFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(supplierPanel);
                        supplierPanel.setAktifPanel(supplierPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JPopupMenu.Separator());
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Pengguna") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                penggunaPanel.setName("Daftar Pengguna");
                if (penggunaPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(penggunaPanel.getIndexTab());
                } else {
                    penggunaPanel.setAktifPanel(penggunaPanel.getAktifPanel() + 1);
                    TP.addTab(penggunaPanel.getName(), penggunaPanel);
                    penggunaPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(penggunaPanel.getIndexTab());
                    
                    penggunaPanel.getToolbarDenganFilter().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(penggunaPanel);
                        penggunaPanel.setAktifPanel(penggunaPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuMaster.add(new JMenuItem(new AbstractAction("Tingkat Akses") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                aksesPanel.setName("Daftar Tingkat Akses");
                if (aksesPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(aksesPanel.getIndexTab());
                } else {
                    aksesPanel.setAktifPanel(aksesPanel.getAktifPanel() + 1);
                    TP.addTab(aksesPanel.getName(), aksesPanel);
                    aksesPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(aksesPanel.getIndexTab());
                    
                    aksesPanel.getToolbar().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(aksesPanel);
                        aksesPanel.setAktifPanel(aksesPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        btnMaster.addActionListener((ae) -> {
            popupMenuMaster.show(btnMaster, 0, btnMaster.getSize().height);
        });
    }

}
