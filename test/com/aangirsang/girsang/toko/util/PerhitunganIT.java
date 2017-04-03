/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GIRSANG PC
 */
public class PerhitunganIT {
    
    public PerhitunganIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of kaliDecimal method, of class Perhitungan.
     */
    @Test
    public void testKaliDecimal() {
        System.out.println("kaliDecimal");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        BigDecimal expResult = new BigDecimal(200);
        BigDecimal result = instance.kaliDecimal(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of bagiDecimal method, of class Perhitungan.
     */
    @Test
    public void testBagiDecimal() {
        System.out.println("bagiDecimal");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        BigDecimal expResult = new BigDecimal(2);
        BigDecimal result = instance.bagiDecimal(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of tambahDecimal method, of class Perhitungan.
     */
    @Test
    public void testTambahDecimal() {
        System.out.println("tambahDecimal");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        BigDecimal expResult = new BigDecimal(30);
        BigDecimal result = instance.tambahDecimal(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of kurangDecimal method, of class Perhitungan.
     */
    @Test
    public void testKurangDecimal() {
        System.out.println("kurangDecimal");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        BigDecimal expResult = new BigDecimal(10);
        BigDecimal result = instance.kurangDecimal(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of kaliInteger method, of class Perhitungan.
     */
    @Test
    public void testKaliInteger() {
        System.out.println("kaliInteger");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        Integer expResult = new Integer(200);
        Integer result = instance.kaliInteger(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of bagiInteger method, of class Perhitungan.
     */
    @Test
    public void testBagiInteger() {
        System.out.println("bagiInteger");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        Integer expResult = new Integer(2);;
        Integer result = instance.bagiInteger(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of tambahInteger method, of class Perhitungan.
     */
    @Test
    public void testTambahInteger() {
        System.out.println("tambahInteger");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        Integer expResult = new Integer(30);;
        Integer result = instance.tambahInteger(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of kurangInteger method, of class Perhitungan.
     */
    @Test
    public void testKurangInteger() {
        System.out.println("kurangInteger");
        String a = "20";
        String b = "10";
        Perhitungan instance = new Perhitungan();
        Integer expResult = new Integer(10);;
        Integer result = instance.kurangInteger(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of main method, of class Perhitungan.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Perhitungan.main(args);
    }
    
}
