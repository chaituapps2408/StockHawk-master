package com.sam_chordas.android.stockhawk.ui;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by Chaiy on 8/9/2016.
 */
public class HelloChartUIHelper {

    private static final ValueShape shape = ValueShape.CIRCLE;

    public static void initChart(LineChartView chartView, List<HistoricalQuote> historicalData) {

        LineChartData chartData = generateChartLineData(historicalData, chartView);

        setUpChartData(chartData, chartView);
        chartView.setVisibility(View.VISIBLE);
    }


    private static LineChartData generateChartLineData(List<HistoricalQuote> historicalData, LineChartView chartView) {
        float[] minMax = new float[2];

        int numberOfPoints = historicalData.size();

        if (historicalData.size() > 0) {
            minMax[0] = minMax[1] = historicalData.get(0).getLow().floatValue();
        }

        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();

        for (int j = numberOfPoints - 1; j > -1; j--) {

            float close = historicalData.get(j).getClose().floatValue();
            float high = historicalData.get(j).getClose().floatValue();
            float low = historicalData.get(j).getClose().floatValue();

            minMax[0] = Math.min(minMax[0], low);
            minMax[1] = Math.max(minMax[1], high);

            values.add(new PointValue((numberOfPoints - 1) - j, close));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(shape);
        line.setPointRadius(4);
        line.setCubic(false);
        line.setHasPoints(true);

        lines.add(line);

        //resetViewport(chartView, minMax);

        LineChartData chartData = new LineChartData(lines);


        return chartData;
    }

    private static void resetViewport(LineChartView chart, float[] minMax) {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 20;
        //v.right = 14;
        //v.left = 0;
        // You have to set max and current viewports separately.
        chart.setMaximumViewport(v);
        // I changing current viewport with animation in this case.
        chart.setCurrentViewport(v);
    }

    private static void setUpChartData(LineChartData chartData, LineChartView chartView) {


        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true).setAutoGenerated(true).setHasTiltedLabels
                (true);

        axisX.setName("Weeks");
        axisY.setName("Price");

        chartData.setAxisXBottom(axisX);
        chartData.setAxisYLeft(axisY);
        //chartView.setZoomType(ZoomType.VERTICAL);
        chartView.setMaxZoom(4);
        //chartData.setBaseValue(Float.NEGATIVE_INFINITY);

        //chartView.animationDataFinished();
        chartView.setLineChartData(chartData);

    }

}