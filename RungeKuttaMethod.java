package com.company;

public class RungeKuttaMethod extends DifferentialEquationsSolver {
    @Override
    protected double getNextY(double x, double y, double h) {
        double k1, k2, k3, k4;
        k1 = f(x, y);
        k2 = f(x + h / 2, y + h / 2 * k1);
        k3 = f(x + h / 2, y + h / 2 * k2);
        k4 = f(x + h, y + h * k3);
        return y + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }
}
