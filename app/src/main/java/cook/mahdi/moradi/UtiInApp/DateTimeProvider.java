package cook.mahdi.moradi.UtiInApp;

import java.util.Calendar;

public class DateTimeProvider {
    public static String getDateShamsi() {
        String jalali = gregorian_to_jalali(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return jalali;
    }

    public static String getTime(boolean withSec) {
        String currentTime = String.valueOf(Calendar.getInstance().get(Calendar.HOUR)) + "-" +
                String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        if (withSec) {
            currentTime = currentTime + "-" + String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
        }
        return currentTime;
    }

    public static String jalali_to_gregorian(int jy, int jm, int jd) {
        jy += 1595;
        int days = -355668 + (365 * jy) + (((int) (jy / 33)) * 8) + ((int) (((jy % 33) + 3) / 4)) + jd + ((jm < 7) ? (jm - 1) * 31 : ((jm - 7) * 30) + 186);
        int gy = 400 * ((int) (days / 146097));
        days %= 146097;
        if (days > 36524) {
            gy += 100 * ((int) (--days / 36524));
            days %= 36524;
            if (days >= 365)
                days++;
        }
        gy += 4 * ((int) (days / 1461));
        days %= 1461;
        if (days > 365) {
            gy += (int) ((days - 1) / 365);
            days = (days - 1) % 365;
        }
        int gd = days + 1;
        int[] sal_a = {0, 31, ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int gm;
        for (gm = 0; gm < 13 && gd > sal_a[gm]; gm++) gd -= sal_a[gm];
        //int[] gregorian = { gy, gm, gd };
        String gregorian = String.valueOf(gy) + "/" + String.valueOf(gm) + "/" + String.valueOf(gd);
        return gregorian;
    }

    public static String gregorian_to_jalali(int gy, int gm, int gd) {
        int[] g_d_m = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int gy2 = (gm > 2) ? (gy + 1) : gy;
        int days = 355666 + (365 * gy) + ((int) ((gy2 + 3) / 4)) - ((int) ((gy2 + 99) / 100)) + ((int) ((gy2 + 399) / 400)) + gd + g_d_m[gm - 1];
        int jy = -1595 + (33 * ((int) (days / 12053)));
        days %= 12053;
        jy += 4 * ((int) (days / 1461));
        days %= 1461;
        if (days > 365) {
            jy += (int) ((days - 1) / 365);
            days = (days - 1) % 365;
        }
        int jm = (days < 186) ? 1 + (int) (days / 31) : 7 + (int) ((days - 186) / 30);
        int jd = 1 + ((days < 186) ? (days % 31) : ((days - 186) % 30));
        //int[] jalali = { jy, jm, jd };
        String jalali = String.valueOf(jy) + "/" + String.valueOf(jm) + "/" + String.valueOf(jd);
        return jalali;
    }
    public static String getDayPersian(){
        String persian_day = "";
        int  day = 0;
        day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:
                persian_day = "دوشنبه";
                break;
            case Calendar.TUESDAY:
                persian_day = "سه شنبه";
                break;
            case Calendar.WEDNESDAY:
                persian_day = "چهارشنبه";
                break;
            case Calendar.THURSDAY:
                persian_day = "پنجشنبه";
                break;
            case Calendar.FRIDAY:
                persian_day = "جمعه";
                break;
            case Calendar.SATURDAY:
                persian_day = "شنبه";
                break;
            case Calendar.SUNDAY:
                persian_day = "یک شنبه";
                break;
            default:
                persian_day = "نا مشخص";
                break;
        }
        return persian_day;
    }
    public static String getDayPersian(int day){
        String persian_day = "";
        day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day){
            case 1:
                persian_day = "دوشنبه";
                break;
            case 2:
                persian_day = "سه شنبه";
                break;
            case 3:
                persian_day = "چهارشنبه";
                break;
            case 4:
                persian_day = "پنج شنبه";
                break;
            case 5:
                persian_day = "جمعه";
                break;
            case 6:
                persian_day = "شنبه";
                break;
            case 7:
                persian_day = "یک شنبه";
                break;
            default:
                persian_day = "نا مشخص";
                break;
        }
        return persian_day;
    }

}
