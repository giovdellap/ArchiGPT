/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.view.customizations;

import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.util.ConstantValues;
import ir.shenakht.paint.util.JalaliCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hossien
 */
public class AgeCalculate {

    public static List<Participants> value(List<Participants> ps) {
        Date date = ConstantValues.ConstantFunction.getCurrentDate(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        JalaliCalendar.YearMonthDate yearMonthDate = new JalaliCalendar.YearMonthDate(year, month, day);
        JalaliCalendar.YearMonthDate gregorianToJalali = JalaliCalendar.gregorianToJalali(yearMonthDate);
        for (Participants p : ps) {
            if (p.getBirthYear() != null) {
                p.setAge(gregorianToJalali.getYear() -  p.getBirthYear());
            }
        }
        return ps;

    }

}
