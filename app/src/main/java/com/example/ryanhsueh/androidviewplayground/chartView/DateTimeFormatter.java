package com.example.ryanhsueh.androidviewplayground.chartView;

import android.content.Context;

import com.example.ryanhsueh.androidviewplayground.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class DateTimeFormatter {

    public enum DateFormat {
        DMY,
        DM,
        MY,
        Y,
        HMa,
        HMSa,
        HMS_24,
        YMD_HMS_24,
        YMD_HMS_12,
    }

    public static String calendarToString(Context context, Calendar cal, DateFormat format) {
        if (cal == null) {
            return "";
        }
        return dateToString(context, cal.getTime(), format);
    }

    public static String millisecondToString(Context context, long millisecond, DateFormat format) {
        return dateToString(context, new Date(millisecond), format);
    }

    public static String dateToString(Context context, Date date, DateFormat format) {
        if (date == null) {
            return "";
        }

        int id;

        switch (format) {
            case DMY:
                id = R.string.date_format_dmy;
                break;
            case DM:
                id = R.string.date_format_dm;
                break;
            case MY:
                id = R.string.date_format_my;
                break;
            case Y:
                id = R.string.date_format_y;
                break;
            case HMa:
                id = R.string.date_format_hma;
                break;
            case HMSa:
                id = R.string.date_format_hmsa;
                break;
            case HMS_24:
                id = R.string.date_format_hms_24;
                break;
            case YMD_HMS_24:
                id = R.string.date_format_ymd_hms_24;
                break;
            case YMD_HMS_12:
                id = R.string.date_format_ymd_hms_12;
                break;
            default:
                id = 0;
        }

        if (id == 0) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(id), Locale.getDefault());

        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        symbols.setAmPmStrings(new String[] {
                context.getString(R.string.am),
                context.getString(R.string.pm) });
        dateFormat.setDateFormatSymbols(symbols);

        return dateFormat.format(date);
    }

}
