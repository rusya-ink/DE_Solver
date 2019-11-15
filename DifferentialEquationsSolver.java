package com.company;

abstract public class DifferentialEquationsSolver {
    private double[] arrX;
    protected double x0, y0;

    public static double f(double x, double y) {
        return 3 * Math.pow(Math.abs(y), 2 / 3.);
    }

    public double[] solve(int N, double y0, double x0, double X) {
        double[] arrY = new double[N + 1];
        this.x0 = x0;
        this.y0 = y0;
        arrX = new double[N + 1];
        arrY[0] = y0;
        double y = y0;
        double x = x0;
        double h = (X - x0) / N;

        for (int i = 0; i < N; i++) {
            y = getNextY(x, y, h);
            arrY[i + 1] = y;
            x = getNextX(x, h);
        }

        return arrY;
    }

    public double[] getArrX(double x0, double X, int N) {
        double h = (X - x0) / N;
        double x = x0;
        arrX = new double[N + 1];
        for (int i = 0; i < N + 1; i++) {
            arrX[i] = x;
            x += h;
        }
        return arrX;
    }

    protected double getNextX(double x, double h) {
        return x + h;
    }

    protected abstract double getNextY(double x, double y, double h);
}
