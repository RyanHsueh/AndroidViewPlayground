package com.example.ryanhsueh.androidviewplayground.chartView;

import com.example.ryanhsueh.androidviewplayground.chartView.data.TimeSectionHM;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ryanhsueh on 2018/8/13
 */
public class Utility {

    public static final int MILLIS_PER_SECOND = 1000;
    public static final int SECOND_PER_HOUR = 3600;
    public static final int SECOND_PER_MINUTE = 60;
    public static final int MINUTE_PER_HOUR = 60;
    public static final int HOUR_PER_DAY = 24;

    public static String getStringOfNumber(long number) {
        if (number > 100000) {
            int thousand = (int)(number / 1000);
            StringBuilder sb = new StringBuilder();
            sb.append(thousand).append("k");
            return sb.toString();
        } else {
            return String.valueOf(number);
        }
    }

    public static long getMillisForIntTime(int timeInMinute) {
        TimeSectionHM timeHM = getTimeSectionHM(timeInMinute);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, timeHM.hour);
        calendar.set(Calendar.MINUTE, timeHM.minute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    public static TimeSectionHM getTimeSectionHM(int timeInMinute) {
        return new TimeSectionHM(
                timeInMinute / MINUTE_PER_HOUR,
                timeInMinute % MINUTE_PER_HOUR);
    }

}
