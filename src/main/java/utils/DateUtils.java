package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static String pattern = "yyyy-MM-dd HH:mm";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static Date parseDate(String strDate) {
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Invalid format");
            return null;
        }
    }

    public static String convertDateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date plusTime(Date startTime, long durationTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        c.add(Calendar.MINUTE, (int) durationTime);

        Date endTime = c.getTime();
        return endTime;
    }


//public static Date plusTime(Date startTime, long durationTime) {
//    if (startTime == null) {
//        // xử lý lỗi ở đây, ví dụ:
//        throw new IllegalArgumentException("startTime cannot be null");
//    }
//
//    Calendar c = Calendar.getInstance();
//    c.setTime(startTime);
//    c.add(Calendar.MINUTE, (int) durationTime);
//
//    Date endTime = c.getTime();
//    return endTime;
//}

    public static Date minusTime(Date startTime, long durationTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        c.add(Calendar.MINUTE, (int) -durationTime);

        Date beforeStartTime = c.getTime();
        return beforeStartTime;
    }
    public static boolean validateDateFormat(String date) {
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
