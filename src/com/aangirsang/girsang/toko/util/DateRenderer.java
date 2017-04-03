/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aangirsang.girsang.toko.util;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ifnu
 */
public class DateRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(value instanceof Date){
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
            label.setText(f.format(value));
        }
        return label;
    }

}
