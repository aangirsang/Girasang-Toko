/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aangirsang.girsang.toko.model.transaksi.constant;

/**
 *
 * @author ITSUSAHBRO
 */
public enum TransaksiRunningNumberEnum {
    PEMBELIAN("BLI", 4),
    PENJUALAN("JLA", 4),
    PEMBAYARAN("BYR", 5),
    SESI_KASSA("SES", 5),
    SALDO_STOK("SAL", 5),
    HUTANG("HTG", 4);

    private final String id;
    private final Integer digit;

    private TransaksiRunningNumberEnum(String id, Integer digit) {
        this.id = id;
        this.digit = digit;
    }

    public Integer getDigit() {
        return digit;
    }

    public String getId() {
        return id;
    }
}
