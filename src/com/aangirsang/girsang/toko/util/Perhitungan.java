/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.util;

import java.math.BigDecimal;

/**
 *
 * @author GIRSANG PC
 */
public class Perhitungan {
    public static BigDecimal kaliDecimal(String a, String b){
        BigDecimal isiDecimal = new BigDecimal(b);
        BigDecimal hasilDecimal = new BigDecimal(a);
        BigDecimal hasil = hasilDecimal.multiply(isiDecimal);
        return hasil;
    }
    public static BigDecimal bagiDecimal(String a, String b){
        BigDecimal isiDecimal = new BigDecimal(b);
        BigDecimal hasilDecimal = new BigDecimal(a);
        BigDecimal hasil = hasilDecimal.divide(isiDecimal);
        return hasil;
    }
    public static BigDecimal tambahDecimal(String a, String b){
        BigDecimal isiDecimal = new BigDecimal(b);
        BigDecimal hasilDecimal = new BigDecimal(a);
        BigDecimal hasil = hasilDecimal.add(isiDecimal);
        return hasil;
    }
    public static BigDecimal kurangDecimal(String a, String b){
        BigDecimal isiDecimal = new BigDecimal(b);
        BigDecimal hasilDecimal = new BigDecimal(a);
        BigDecimal hasil = hasilDecimal.subtract(isiDecimal);
        return hasil;
    }
    public static Integer kaliInteger(String a, String b){
        Integer aInt = new Integer(b);
        Integer bInt = new Integer(a);
        Integer hasil = aInt * bInt;
        return hasil;
    }
    public static Integer bagiInteger(String a, String b){
        Integer aInt = new Integer(a);
        Integer bInt = new Integer(b);
        Integer hasil = aInt / bInt;
        return hasil;
    }
    public static Integer tambahInteger(String a, String b){
        Integer aInt = new Integer(a);
        Integer bInt = new Integer(b);
        Integer hasil = aInt + bInt;
        return hasil;
    }
    public static Integer kurangInteger(String a, String b){
        Integer aInt = new Integer(a);
        Integer bInt = new Integer(b);
        Integer hasil = aInt - bInt;
        return hasil;
    }
    public static void main(String[] args) {
        String a = "10";
        String b = "10";
        System.out.println(a + " X " + b + " = " +kaliInteger(a, b));
        System.out.println(a + " / " + b + " = " +bagiInteger(a, b));
        System.out.println(a + " + " + b + " = " +tambahInteger(a, b));
        System.out.println(a + " : " + b + " = " +kurangInteger(a, b));
        System.out.println(a + " X " + b + " = " +kaliInteger(a, b));
        System.out.println(a + " / " + b + " = " +bagiInteger(a, b));
        System.out.println(a + " + " + b + " = " +tambahInteger(a, b));
        System.out.println(a + " : " + b + " = " +kurangInteger(a, b));
    }
}
