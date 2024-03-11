/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

/**
 *
 * @author hossi
 */
public class ConvertPhoneToRightFormat {

    public static String makePhone(String phone) {
        int numExtra = phone.length() - 10;
        return "0" + phone.substring(numExtra, phone.length());
    }

}
