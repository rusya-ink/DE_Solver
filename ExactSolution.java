package com.company;


public class ExactSolution extends DifferentialEquationsSolver {
    private double C;

    @Override
    protected double getNextY(double x, double y, double h) {
        return f(x + h);
    }

    private double asinh(double x) {
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }

    private double acosh(double x) {
        return Math.log(x + Math.sqrt(x * x - 1.0));
    }

    private boolean constAvailable = false;

    private double f(double x) {
        if (!constAvailable) {
            //solving equation via Vieta
            double C;
            double d = 1 / 27., a = x0 / 3., b = x0 * x0, c = x0 * x0 * x0 - y0;
            a /= d;
            b /= d;
            c /= d;
            double q = (a * a - 3 * b) / 9.;
            double r = (2 * a * a * a - 9 * a * b + 27 * c) / 54.;
            double s = q * q * q - r * r;
            if (s > 0) {
                double phi = 1 / 3. * Math.acos(r / Math.sqrt(q * q * q));
                C = -2 * Math.sqrt(q) * Math.cos(phi) - a / 3.;
            } else if (s != 0) {
                if (q > 0) {
                    double phi = 1 / 3. * acosh(Math.abs(r) / Math.sqrt(q * q * q));
                    C = -2 * Math.signum(r) * Math.sqrt(q) * Math.cosh(phi) - a / 3.;
                } else if (q < 0) {
                    double phi = 1 / 3. * asinh(Math.abs(r) / Math.sqrt(Math.abs(q * q * q)));
                    C = -2 * Math.signum(r) * Math.sqrt(Math.abs(q)) * Math.sinh(phi) - a / 3;
                } else {

                    C = -Math.signum(c - a * a * a / 27.) * Math.pow(Math.abs(c - a * a * a / 27.), 1 / 3.) - a / 3.;
                }
            } else {
                throw new IllegalArgumentException("Equation is incorrect!");
            }
            constAvailable = true;
            this.C = C;
            return x * x * x + C * x * x + C * C * x / 3. + C * C * C / 27.;
        } else {
            return x * x * x + C * x * x + C * C * x / 3. + C * C * C / 27.;
        }
    }
}
