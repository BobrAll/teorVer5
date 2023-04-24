package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int DATA_SIZE = 20;

    public static void main(String[] args) throws FileNotFoundException {
        double[] nums = new double[DATA_SIZE];
        Scanner scanner = new Scanner(new FileReader("src/main/resources/data"));

        System.out.println("Работу выполнил Бобрусь А.В., вариант №2");

        for (int i = 0; i < DATA_SIZE; i++)
            nums[i] = scanner.nextDouble();

        System.out.println("Исходные данные:\n" + Arrays.toString(nums));

        Calculator calculator = new Calculator(nums);
        calculator.printParameters();

        calculator.drawEmpiricFunction();
        calculator.drawFrequencyPolygon();
        calculator.drawHistogram(nums.length);
    }
}