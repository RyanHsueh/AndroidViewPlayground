package com.example.ryanhsueh.androidviewplayground;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ryanhsueh.androidviewplayground.chartView.BaseChartView;
import com.example.ryanhsueh.androidviewplayground.chartView.ChartTimeUnit;
import com.example.ryanhsueh.androidviewplayground.chartView.data.BarChartData;
import com.example.ryanhsueh.androidviewplayground.chartView.data.ChartData;

import java.util.Random;

public class BarChartActivity extends AppCompatActivity {

    private BaseChartView mChartView;
    private ChartTimeUnit mChartTimeUnit = ChartTimeUnit.DAILY;;
    private ChartData mChartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        mChartData = new BarChartData(genDailyEmpty(), 100);

        mChartView = findViewById(R.id.chart_view);
        mChartView.setChartTimeUnit(mChartTimeUnit);
        mChartView.setChartData(mChartData);
        mChartView.enableAnimation(false);
        mChartView.draw();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mChartView.releaseResource();
    }

    private int[] genDailyEmpty() {
        int[] values = new int[144];

        for (int i=0 ; i<144 ; i++) {
            int val = 0;
            values[i] = val;
        }

        return values;
    }

    private int[] genData() {
        int size;
        switch (mChartTimeUnit) {
            case DAILY:
                size = 144;
                break;
            case WEEKLY:
                size = 7;
                break;
            case MONTHLY:
                size = 30;
                break;
            case YEARLY:
                size = 12;
                break;
            default:
                size = 144;
        }

        int[] values = new int[size];

        Random random = new Random();
        for (int i=0 ; i<size ; i++) {
            int val = random.nextInt(150);
            values[i] = val;
        }

        return values;
    }

    private void drawChartView() {
        mChartView.setChartTimeUnit(mChartTimeUnit);
        mChartView.setChartData(mChartData);
        mChartView.draw();
    }

    protected void onDaily(View view) {
        mChartTimeUnit = ChartTimeUnit.DAILY;
        mChartData = new BarChartData(genData(), 100);

        mChartView.enableAnimation(false);
        drawChartView();
    }

    protected void onWeekly(View view) {
        mChartTimeUnit = ChartTimeUnit.WEEKLY;
        mChartData = new BarChartData(genData(), 100);

        mChartView.enableAnimation(true);
        drawChartView();
    }

    protected void onMonthly(View view) {
        mChartTimeUnit = ChartTimeUnit.MONTHLY;
        mChartData = new BarChartData(genData(), 100);

        mChartView.enableAnimation(true);
        drawChartView();
    }

    protected void onYearly(View view) {
        mChartTimeUnit = ChartTimeUnit.YEARLY;
        mChartData = new BarChartData(genData(), 100);

        mChartView.enableAnimation(true);
        drawChartView();
    }
}
