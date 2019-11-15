package com.company;

public class SimpleEulerMethod extends DifferentialEquationsSolver {

    @Override
    protected double getNextY(double x, double y, double h) {
        return y + h * f(x, y);
    }
}
