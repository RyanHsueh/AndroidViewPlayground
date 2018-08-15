package com.example.ryanhsueh.androidviewplayground.chartView.data;

import java.util.Arrays;

/**
 * Created by ryanhsueh on 2018/8/14
 */
public class BarChartData implements ChartData {

    protected int[] barValues;
    private int maxBar;
    private int minBar;
    private int dailyGoal;

    public BarChartData(int[] barValues, int dailyGoal) {
        if (barValues != null && barValues.length > 0) {
            this.barValues = barValues.clone();

            Arrays.sort(barValues);
            maxBar = barValues[barValues.length - 1];
            minBar = barValues[0];
        }

        this.dailyGoal = dailyGoal;
    }

    public final int[] getBarValues() {
        return barValues;
    }

    public final int getBarValue(int i) {
        return barValues[i];
    }

    public int getDailyGoal() {
        return dailyGoal;
    }

    public final int getMaxBar() {
        return maxBar;
    }

    public final int getMinBar() {
        return minBar;
    }

    @Override
    public int size() {
        return barValues == null ? 0 : barValues.length;
    }

    @Override
    public int max() {
        return maxBar;
    }

}

