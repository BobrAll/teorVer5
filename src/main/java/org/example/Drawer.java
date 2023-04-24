package org.example;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.theme.Theme;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.knowm.xchart.style.lines.SeriesLines.NONE;

public class Drawer {
    private final ArrayList<Double> xs = new ArrayList<>();
    private final ArrayList<Double> ys = new ArrayList<>();
    private final XYChart chart;

    public Drawer(String X, String Y, String Title) {
        chart = new XYChartBuilder().theme(Styler.ChartTheme.Matlab)
                .title(Title).xAxisTitle(X).yAxisTitle(Y)
                .build();

        chart.setCustomXAxisTickLabelsFormatter((x) -> String.format("%.2f", x));
        chart.setCustomYAxisTickLabelsFormatter((y) -> String.format("%.2f", y));
        chart.getStyler().setSeriesLines(new BasicStroke[]{NONE});
    }

    public void addChart(String name, double a, double b, double h) {
        xs.clear();
        ys.clear();

        this.xs.add(a);
        this.xs.add(b);
        this.ys.add(h);
        this.ys.add(h);

        chart.getStyler().setMarkerSize(5);
        chart.addSeries(name, this.xs, this.ys).setLineWidth(2);
    }

    public void PolygonalChart(double a, double h) {
        xs.add(a);
        ys.add(h);

        chart.getStyler().setMarkerSize(10);
    }

    public void addHistogram(String name, double a, double b, double h) {
        xs.clear();
        ys.clear();
        xs.add(a);
        xs.add(b);
        ys.add(h);
        ys.add(h);

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        chart.getStyler().setMarkerSize(7);
        chart.addSeries(name, xs, ys).setLineWidth(1);
    }

    public void plotPolygon(String name) {
        chart.addSeries(name, this.xs, this.ys).setLineWidth(2);
        try {
            BitmapEncoder.saveBitmap(chart, name, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void plot(String name) {
        try {
            BitmapEncoder.saveBitmap(chart, name, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}