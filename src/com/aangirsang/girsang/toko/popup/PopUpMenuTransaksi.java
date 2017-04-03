/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.popup;

import com.aangirsang.girsang.toko.ui.tansaksi.PelunasanHutangPanel;
import com.aangirsang.girsang.toko.ui.tansaksi.PembelianPanel;
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
public class PopUpMenuTransaksi extends AbstractButton {

    PembelianPanel pembelianPanel = new PembelianPanel();
    PelunasanHutangPanel hutangPanel = new PelunasanHutangPanel();
    JButton actionButton;
    JPopupMenu popupMenu;

    public PopUpMenuTransaksi(JTabbedPane TP, JPopupMenu popupMenuTransaksi, JButton btnTransaksi) {
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Pembelian") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                pembelianPanel.setName("Daftar Pembelian Barang");
                if (pembelianPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(pembelianPanel.getIndexTab());
                } else {
                    pembelianPanel.setAktifPanel(pembelianPanel.getAktifPanel() + 1);
                    TP.addTab(pembelianPanel.getName(), pembelianPanel);
                    pembelianPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(pembelianPanel.getIndexTab());

                    pembelianPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(pembelianPanel);
                        pembelianPanel.setAktifPanel(pembelianPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        popupMenuTransaksi.add(new JMenuItem(new AbstractAction("Pelunasan Hutang") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                hutangPanel.setName("Daftar Pelunasan Hutang");
                if (hutangPanel.getAktifPanel() == 1) {
                    TP.setSelectedIndex(hutangPanel.getIndexTab());
                } else {
                    hutangPanel.setAktifPanel(hutangPanel.getAktifPanel() + 1);
                    TP.addTab(hutangPanel.getName(), hutangPanel);
                    hutangPanel.setIndexTab(TP.getTabCount() - 1);
                    TP.setSelectedIndex(hutangPanel.getIndexTab());

                    hutangPanel.getToolbarDenganFilter1().getBtnKeluar().addActionListener((ae1) -> {
                        TP.remove(hutangPanel);
                        hutangPanel.setAktifPanel(hutangPanel.getAktifPanel() - 1);
                        TP.setSelectedIndex(0);
                    });
                }
            }
        }));
        btnTransaksi.addActionListener((ae) -> {
            popupMenuTransaksi.show(btnTransaksi, 0, btnTransaksi.getSize().height);
        });
    }

}
