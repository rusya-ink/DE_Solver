package com.company;

import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    //graphics data
    private Double[] arrX;
    private Double[] arrY1;
    private Double[] arrY2;
    private Double[] arrY3;
    private Double[] arrY4;

    private Double[] arrErr1;
    private Double[] arrErr2;
    private Double[] arrErr3;

    private Double[] arrLocalErr1;
    private Double[] arrLocalErr2;
    private Double[] arrLocalErr3;

    private Double[] arrN;
    private Double[] arrErrN1;
    private Double[] arrErrN2;
    private Double[] arrErrN3;
    private int N = 0;

    private int index = 0;

    public RPanel() {
        super();
        setMinimumSize(new Dimension(715, 625));
        setMaximumSize(new Dimension(715, 625));
        setPreferredSize(new Dimension(715, 625));
    }

    public void saveData(Double[][] arrayFunct, Double[][] arrayErrors, Double[][] arrayLocalErrors,
                         Double[][] arrayErrorN, int N) {
        arrX = arrayFunct[0];
        arrY1 = arrayFunct[1];
        arrY2 = arrayFunct[2];
        arrY3 = arrayFunct[3];
        arrY4 = arrayFunct[4];
        arrErr1 = arrayErrors[0];
        arrErr2 = arrayErrors[1];
        arrErr3 = arrayErrors[2];
        arrLocalErr1 = arrayLocalErrors[0];
        arrLocalErr2 = arrayLocalErrors[1];
        arrLocalErr3 = arrayLocalErrors[2];

        arrN = arrayErrorN[0];
        arrErrN1 = arrayErrorN[1];
        arrErrN2 = arrayErrorN[2];
        arrErrN3 = arrayErrorN[3];

        this.N = N;
    }

    //graphics window borders
    private double xS = 60, xF = 540, yS = 30, yF = 560, off = 5, height = 625;

    private void drawHelperLines(Graphics gh, double x, double y, double dx, double dy, int nLines) {
        double stepX = dx / nLines, stepY = dy / nLines;
        for (int i = 0; i <= nLines; i++) {

            gh.setColor(Color.BLACK);
            gh.drawString(String.format("%.1f", x), (int) Math.round(xS + i * (xF - xS) / 10 - 2 * off),
                    (int) Math.round(height - yS + 3 * off));
            gh.drawString(String.format("%.1f", y), (int) Math.round(xS - 10 * off),
                    (int) Math.round(height - yS - i * (yF - yS) / 10));
            if (i < nLines) {
                gh.setColor(Color.LIGHT_GRAY);
                gh.drawLine((int) Math.round(xS - off),
                        (int) Math.round(height - yS - (i + 1) * (yF - yS) / nLines),
                        (int) Math.round(xF + off), (int) Math.round(height - yS - (i + 1) * (yF - yS) / nLines));
                gh.drawLine((int) Math.round(xS + (i + 1) * (xF - xS) / nLines),
                        (int) Math.round(height - yF - off),
                        (int) Math.round(xS + (i + 1) * (xF - xS) / nLines), (int) Math.round(height - yS + off));
            }
            x += stepX;
            y += stepY;
        }
    }

    private double maxOfMatrix(Double[][] matrix) {
        double max = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }

    private double minOfMatrix(Double[][] matrix) {
        double min = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
        }
        return min;
    }

    private void drawPlusLegenda(Graphics gh, int[][] points, String[] names, Color[] colors,
                                 String horAxis, String verAxis) {
        //Axis
        gh.setColor(Color.BLACK);
        gh.drawLine((int) xS, (int) (height - yS), (int) xS, (int) (height - yF - 50));
        gh.drawLine((int) xS - 5, (int) (height - yF - 35), (int) xS, (int) (height - yF - 50));
        gh.drawLine((int) xS + 5, (int) (height - yF - 35), (int) xS, (int) (height - yF - 50));
        gh.drawString(verAxis, (int) xS - 20, (int) (height - yF - 35));
        gh.drawLine((int) xS, (int) (height - yS), (int) xF + 50, (int) (height - yS));
        gh.drawLine((int) xF + 35, (int) (height - yS + 5), (int) xF + 50, (int) (height - yS));
        gh.drawLine((int) xF + 35, (int) (height - yS - 5), (int) xF + 50, (int) (height - yS));
        gh.drawString(horAxis, (int) xF + 35, (int) (height - yS + 20));

        //functions+legend
        for (int i = 0; i < points.length - 1; i++) {
            gh.setColor(colors[i]);
            if (points[i + 1].length == 1) {
                gh.drawLine((int) xS, points[i + 1][0], (int) xF, points[i + 1][0]);
            }
            gh.drawPolyline(points[0], points[i + 1], points[0].length);
            gh.fillRect((int) xF + 20, 100 + i * 50, 10, 10);
            gh.setColor(Color.BLACK);
            gh.drawString(names[i], (int) xF + 40, 110 + i * 50);
        }


    }

    @Override
    protected void paintComponent(Graphics gh) {
        super.paintComponent(gh);


        //((Graphics2D)gh).drawLine(xF+off, 605-8*69, 575, 605-69*8);
        if (N != 0) {
            if (index == 0) {
                //drawing functions

                //find range of functions
                double dx = arrX[arrX.length - 1] - arrX[0];
                double maxY, minY;

                maxY = maxOfMatrix(new Double[][]{arrY1, arrY2, arrY3, arrY4});
                minY = minOfMatrix(new Double[][]{arrY1, arrY2, arrY3, arrY4});
                double dy = maxY - minY;

                double x = arrX[0], y = minY;
                drawHelperLines(gh, x, y, dx, dy, 10);
                int[] newXs = getXFromXs(arrX, dx);
                int[] newY1 = getYFromYs(arrY1, dy, minY);
                int[] newY2 = getYFromYs(arrY2, dy, minY);
                int[] newY3 = getYFromYs(arrY3, dy, minY);
                int[] newY4 = getYFromYs(arrY4, dy, minY);
                drawPlusLegenda(gh,
                        new int[][]{newXs, newY1, newY2, newY3, newY4},
                        new String[]{"Simple Euler Solution", "Improved Euler Solution",
                                "Runge-Kutta Solution", "Exact Solution"},
                        new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA},
                        "X",
                        "Y");
            } else if (index == 1) {
                //drawing errors graphics
                double dx = arrX[arrX.length - 1] - arrX[0];
                double maxY = arrErr1[0], minY = maxY;
                maxY = maxOfMatrix(new Double[][]{arrErr1, arrErr2, arrErr3});
                minY = minOfMatrix(new Double[][]{arrErr1, arrErr2, arrErr3});
                double dy = maxY - minY;

                double x = arrX[0], y = minY, stepX = dx / 10, stepY = dy / 10;
                drawHelperLines(gh, x, y, dx, dy, 10);

                int[] newXs = getXFromXs(arrX, dx);
                int[] newE1 = getYFromYs(arrErr1, dy, minY);
                int[] newE2 = getYFromYs(arrErr2, dy, minY);
                int[] newE3 = getYFromYs(arrErr3, dy, minY);
                drawPlusLegenda(gh,
                        new int[][]{newXs, newE1, newE2, newE3},
                        new String[]{"Simple Euler Error", "Improved Euler Error",
                                "Runge-Kutta Error"},
                        new Color[]{Color.RED, Color.GREEN, Color.BLUE},
                        "X",
                        "Y");
            } else if (index == 2) {
                //drawing local errors graphics
                double dx = arrX[arrX.length - 1] - arrX[0];
                double maxY = arrLocalErr1[0], minY = maxY;
                maxY = maxOfMatrix(new Double[][]{arrLocalErr1, arrLocalErr2, arrLocalErr3});
                minY = minOfMatrix(new Double[][]{arrLocalErr1, arrLocalErr2, arrLocalErr3});
                double dy = maxY - minY;

                double x = arrX[0], y = minY, stepX = dx / 10, stepY = dy / 10;
                drawHelperLines(gh, x, y, dx, dy, 10);

                int[] newXs = getXFromXs(arrX, dx);
                int[] newL1 = getYFromYs(arrLocalErr1, dy, minY);
                int[] newL2 = getYFromYs(arrLocalErr2, dy, minY);
                int[] newL3 = getYFromYs(arrLocalErr3, dy, minY);
                drawPlusLegenda(gh,
                        new int[][]{newXs, newL1, newL2, newL3},
                        new String[]{"Simple Euler Error", "Improved Euler Error",
                                "Runge-Kutta Error"},
                        new Color[]{Color.RED, Color.GREEN, Color.BLUE},
                        "X",
                        "Y");
            } else if (index == 3) {
                //drawing errors graphics
                double dn = arrN[arrN.length - 1] - arrN[0];
                double maxY = arrErrN1[0], minY = maxY;
                maxY = maxOfMatrix(new Double[][]{arrErrN1, arrErrN2, arrErrN3});
                minY = minOfMatrix(new Double[][]{arrErrN1, arrErrN2, arrErrN3});
                double dy = maxY - minY;

                double n = arrN[0], y = minY, stepX = dn / 10, stepY = dy / 10;
                drawHelperLines(gh, n, y, dn, dy, 10);

                int[] newNs = getXFromXs(arrN, dn);
                int[] newN1 = getYFromYs(arrErrN1, dy, minY);
                int[] newN2 = getYFromYs(arrErrN2, dy, minY);
                int[] newN3 = getYFromYs(arrErrN3, dy, minY);
                drawPlusLegenda(gh,
                        new int[][]{newNs, newN1, newN2, newN3},
                        new String[]{"Simple Euler Error", "Improved Euler Error",
                                "Runge-Kutta Error"},
                        new Color[]{Color.RED, Color.GREEN, Color.BLUE},
                        "N",
                        "Y");
            }
        }
    }

    private int[] getXFromXs(Double[] Xs, double dx) {
        int[] newXs = new int[Xs.length];
        for (int i = 0; i < Xs.length; i++) {

            newXs[i] = (int) Math.round(((Xs[i] - Xs[0]) / dx) * (xF - xS)) + (int) Math.round(xS);
            //System.out.println(Xs[i]+" "+dx+" "+newXs[i]);
        }
        return newXs;
    }

    private int[] getYFromYs(Double[] Ys, double dy, double minY) {
        int[] newYs = new int[Ys.length];
        for (int i = 0; i < Ys.length; i++) {
            newYs[i] = (int) Math.round(height - yS) - (int) Math.round(((Ys[i] - minY) / dy) * (yF - yS));
        }
        return newYs;
    }

    public void drawSelected(int selectedIndex) {
        index = selectedIndex;
        repaint();
    }
}
