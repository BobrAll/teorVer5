package org.example;

import java.util.*;

public class Calculator {
    double[] nums;
    double expectedValue, dispersion, scope;

    ArrayList<Double> x_i = new ArrayList<>();
    ArrayList<Integer> n_i = new ArrayList<>();
    ArrayList<Double> p_i = new ArrayList<>();

    public Calculator(double[] nums) {
        this.nums = nums;
        Arrays.sort(this.nums);

        expectedValue = calcExpectedValue();
        dispersion = calcDispersion();
        scope = calcScope();
    }

    void printParameters() {
        System.out.println("Отсортированные данные: \n" + Arrays.toString(nums));
        System.out.printf("Экстремальные значения: [%.2f, %.2f]\n", nums[0], nums[nums.length - 1]);
        System.out.printf("Размах выборки: %.2f\n", scope);
        System.out.printf("Мат. ожидание: %.2f\n", expectedValue);
        System.out.printf("Дисперсия: %.2f\n", dispersion);
        System.out.printf("Cреднеквадратическое отклонение: %.2f\n", Math.sqrt(dispersion));
    }

    private double calcScope() {
        return nums[nums.length - 1] - nums[0];
    }
    private double calcExpectedValue() {
        Arrays.stream(nums).distinct().forEach(x -> {
            int count = 0;
            x_i.add(x);

            for (double element : nums)
                if (element == x) count++;

            n_i.add(count);
            p_i.add(((double) count / (double) nums.length));
        });

        double expectedValue = 0;
        for (int i = 0; i < x_i.size(); i++)
            expectedValue += x_i.get(i) * p_i.get(i);

        return expectedValue;
    }
    private double calcDispersion() {
        double dispersion = 0;

        for (int i = 0; i < x_i.size(); i++)
            dispersion += Math.pow((x_i.get(i) - expectedValue), 2) * n_i.get(i);

        return dispersion / nums.length;
    }

    public double getH() {
        return scope / (1 + ((Math.log(nums.length) / Math.log(2))));
    }

    public int getM() {
        return (int) Math.ceil(1 + (Math.log(nums.length) / Math.log(2)));
    }

    public void drawEmpiricFunction() {
        Drawer drawChart = new Drawer("x", "f(X)", "Эмпирическая функция");

        double h = p_i.get(0);
        drawChart.addChart("x <= " + x_i.get(0), x_i.get(0) - 0.5, x_i.get(0), 0);
        for (int i = 0; i < x_i.size() - 1; i++) {
            drawChart.addChart(x_i.get(i) + " < x <= " + x_i.get(i + 1), x_i.get(i), x_i.get(i + 1), h);
            h += p_i.get(i + 1);
        }
        drawChart.addChart(x_i.get(x_i.size() - 1) + " < x", x_i.get(x_i.size() - 1), x_i.get(x_i.size() - 1) + 1, h);
        drawChart.plot("EmpiricFunc");
    }

    public void drawFrequencyPolygon() {
        Drawer frequencyPolygon = new Drawer("x", "p_i", "Полигон частот");

        double x_start = nums[0] - getH() / 2;
        for (int i = 0; i < getM(); i++) {
            int count = 0;
            for (double value : nums)
                if (value >= x_start && value < (x_start + getH()))
                    count++;

            frequencyPolygon.PolygonalChart(x_start + getH() / 2, (double) count / (double) nums.length);

            x_start += getH();
        }
        frequencyPolygon.plotPolygon( "FrequencyPolygon");
    }

    public void drawHistogram(int size) {
        Drawer Histogram = new Drawer("x", "p_i / h", "Гистограмма частот");
        double x_start = nums[0] - getH() / 2;
        for (int i = 0; i < getM(); i++) {
            int s = 0;
            for (double value : nums)
                if (value >= x_start && value < (x_start + getH())) {
                    s++;
                }

            Histogram.addHistogram(x_start + " : " + x_start + getH(), x_start, x_start + getH(),
                    ((double) s / (double) size) / getH());
            x_start += getH();
        }
        Histogram.plot("Histogram");
    }
}