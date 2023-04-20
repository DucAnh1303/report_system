package com.example.itspower.util;
import com.example.itspower.type.RegexType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.*;

@Component
public class ConverterUtils {




    public static String formatNumberToString(long number) {
        String pattern = "###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }

    public static String formatDecimalToString(double number, int digitLength) {
        var a = String.valueOf(number).split("\\.");
        if (digitLength == 0) return formatNumberToString((long) number);

        StringBuilder i = new StringBuilder(".");
        if (a.length == 1) i.append("0".repeat(digitLength));
        else {
            if (a[1].length() >= digitLength) i.append(a[1], 0, digitLength);
            else i.append(a[1]).append("0".repeat(digitLength - a[1].length()));
        }
        return formatNumberToString((long) number).concat(i.toString());
    }

    public static String roundToString(double number, int digitLength) {
        if (digitLength < 0) return null;
        int temp = java.lang.Integer.parseInt("1".concat("0".repeat(digitLength)));
        double rs = Math.round(number * temp) * 1.0 / temp;
        return formatDecimalToString(rs, digitLength);
    }
    public static String floorToString(double number, int digitLength) {
        if (digitLength < 0) return null;
        int temp = java.lang.Integer.parseInt("1".concat("0".repeat(digitLength)));
        double rs = Math.floor(number * temp) / temp;
        return formatDecimalToString(rs, digitLength);
    }

    public static String formatDecimalByCalculateTypeToString(double number, int digitLength, int calculateTypeId) {
        switch (calculateTypeId) {
            case 1:
                return roundToString(number, digitLength);
            case 2:
                return floorToString(number, digitLength);
            default:
                return null;
        }
    }
    public static java.util.Date formatStringToDate(String str) {
        if (str == null) return null;

        if (!RegexType.DATE_PATTERN.matcher(str).matches()) try {
            return new java.util.Date(Long.parseLong(str));
        } catch (Exception ignored) {
            return null;
        }
        var arr = Arrays.stream(str.split("[/-]")).map(java.lang.Integer::parseInt).toArray(java.lang.Integer[]::new);
        if (arr.length != 3) return null;
        var year = arr[0];
        var month = arr[1];
        var day = arr[2];
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Long formatDateToTime(Date date) {
        if (date == null) return null;
        return date.getTime();
    }
    public static Long formatDateToTime(Instant date) {
        if (date == null) return null;
        return date.toEpochMilli();
    }
    public static Long formatDateToTime(Timestamp date) {
        if (date == null) return null;
        return date.getTime();
    }
    public static Instant formatStringToInstant(String time) {
        if (time == null) return null;
        try {
            return Instant.ofEpochMilli(Long.parseLong(time));
        } catch (Exception ignored) {
            return null;
        }
    }

    public static java.lang.Integer convertStringToNumber(String number) {
        try {
            if (Objects.equals(number, "") || number == null) return null;
            return java.lang.Integer.parseInt(number);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static long convertTimeByZone(Instant instant, ZoneId zoneId) {
        var zone = zoneId.getRules().getOffset(instant).getTotalSeconds() * 1000L;
        var df = ZoneId.systemDefault().getRules().getOffset(instant).getTotalSeconds() * 1000L;
        return instant.toEpochMilli() - zone + df;
    }

    public static ZoneId getZoneId(String zone) {
        try {
            return ZoneId.of(zone);
        } catch (Exception e) {
            return ZoneId.of("Asia/Tokyo");
        }
    }

}
