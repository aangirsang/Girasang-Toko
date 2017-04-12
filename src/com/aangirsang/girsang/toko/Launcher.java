/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko;

import com.aangirsang.girsang.toko.model.security.Pengguna;
import com.aangirsang.girsang.toko.service.MasterService;
import com.aangirsang.girsang.toko.service.SecurityService;
import com.aangirsang.girsang.toko.service.TransaksiService;
import com.aangirsang.girsang.toko.ui.security.LoginPanel;
import com.aangirsang.girsang.toko.ui.utama.FrameUtama;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.openide.util.Exceptions;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ITSUSAHBRO
 */
public class Launcher {
    private static final Logger log = Logger.getLogger(Launcher.class);
    private static Launcher instance;
    private static MasterService masterService;
    private static TransaksiService transaksiService;
    private static SecurityService securityService;
    private static Pengguna penggunaAktif;
    
    LoginPanel loginPanel = new LoginPanel();

    

    public static MasterService getMasterService() {
        return masterService;
    }

    public static TransaksiService getTransaksiService() {
        return transaksiService;
    }

    public static SecurityService getSecurityService() {
        return securityService;
    }

    public static Pengguna getPenggunaAktif() {
        return penggunaAktif;
    }

    public static void setPenggunaAktif(Pengguna penggunaAktif) {
        Launcher.penggunaAktif = penggunaAktif;
    }
    public static void jam(JDateChooser jdc) {
        Thread t = new Thread(() -> {
            while(true){
                jdc.setDateFormatString("EEE, MMM dd yyyy HH:mm:ss");
                jdc.setDate(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
        t.start();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", Boolean.FALSE);

            AbstractApplicationContext ctx
                    = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
            ctx.registerShutdownHook();

            masterService = (MasterService) ctx.getBean("MasterService");
            transaksiService = (TransaksiService) ctx.getBean("TransaksiService");
            securityService = (SecurityService) ctx.getBean("SecurityService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Terjadi Masalah Pada Database","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(0);
        }    
        
        java.awt.EventQueue.invokeLater(() -> {
            FrameUtama fu = new FrameUtama();
            fu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            fu.setVisible(true);
            fu.jam();
            
        });
    }
}
