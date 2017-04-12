/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.master.constant;

/**
 *
 * @author ifnu
 */
public enum MasterRunningNumberEnum {

    GOLONGAN("GOL", 3),
    SATUAN("SAT", 3),
    SUPPLIER("SUP", 3),
    BARANG("PLU",5),
    HPP("HPP",5),
    PENGGUNA("USER",3),
    TINGKATAKSES("TA",3);
    private String id;
    private Integer digit;

    private MasterRunningNumberEnum(String name, Integer digit) {
        this.id = name;
        this.digit = digit;
    }

    public String getId() {
        return id;
    }

    public Integer getDigit() {
        return digit;
    }
}
