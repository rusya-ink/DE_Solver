package com.company;

public class ImprovedEulerMethod extends SimpleEulerMethod {

    @Override
    protected double getNextY(double x, double y, double h) {
        return y + h / 2 * (f(x, y) + f(getNextX(x, h), super.getNextY(x, y, h)));
    }
}
