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

    /*SatuanPanel satuanPanel = new SatuanPanel();*/
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
        btnMaster.addActionListener((ae) -> {
            popupMenuMaster.show(btnMaster, 0, btnMaster.getSize().height);
        });
    }

}
