package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RFrame extends JFrame {

    private JTextField textX0 = new JTextField();
    private JTextField textX = new JTextField();
    private JTextField textY0 = new JTextField();
    private JTextField textN = new JTextField();
    private JTable tableFunct;
    private JTable tableTotalErrors;
    private JTable tableLocalErrors;
    private JTable tableErrorN;
    private RPanel panel = new RPanel();

    public RFrame () {
        super("DE_Solver. Equation: y'=3y^(2/3)");
        GridBagConstraints c = new GridBagConstraints();
        JPanel jcp = new JPanel(new GridBagLayout());
        setContentPane(jcp);


        //initial conditions
        textX0.setText("2");
        textX.setText("10");
        textY0.setText("1");
        textN.setText("10");


        //size of texts
        textX0.setMinimumSize(new Dimension(70, 20));
        textX0.setMaximumSize(new Dimension(70, 20));
        textX0.setPreferredSize(new Dimension(70, 20));
        textX.setMinimumSize(new Dimension(70, 20));
        textX.setMaximumSize(new Dimension(70, 20));
        textX.setPreferredSize(new Dimension(70, 20));
        textY0.setMinimumSize(new Dimension(70, 20));
        textY0.setMaximumSize(new Dimension(70, 20));
        textY0.setPreferredSize(new Dimension(70, 20));
        textN.setMinimumSize(new Dimension(70, 20));
        textN.setMaximumSize(new Dimension(70, 20));
        textN.setPreferredSize(new Dimension(70, 20));
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;

        //labels
        JLabel labelX0 = new JLabel("x0 = ");
        JLabel labelX = new JLabel(" X = ");
        JLabel labelY0 = new JLabel("y0 = ");
        JLabel labelN = new JLabel(" N = ");
        labelX0.setHorizontalAlignment(SwingConstants.RIGHT);
        labelX.setHorizontalAlignment(SwingConstants.RIGHT);
        labelY0.setHorizontalAlignment(SwingConstants.RIGHT);
        labelN.setHorizontalAlignment(SwingConstants.RIGHT);
        labelX0.setMinimumSize(new Dimension(30, 20));
        labelX0.setMaximumSize(new Dimension(30, 20));
        labelX0.setPreferredSize(new Dimension(30, 20));
        labelX.setMinimumSize(new Dimension(30, 20));
        labelX.setMaximumSize(new Dimension(30, 20));
        labelX.setPreferredSize(new Dimension(30, 20));
        labelY0.setMinimumSize(new Dimension(30, 20));
        labelY0.setMaximumSize(new Dimension(30, 20));
        labelY0.setPreferredSize(new Dimension(30, 20));
        labelN.setMinimumSize(new Dimension(30, 20));
        labelN.setMaximumSize(new Dimension(30, 20));
        labelN.setPreferredSize(new Dimension(30, 20));
        c.weighty=1;
        c.weightx=0;
        c.gridy=0;
        c.gridheight=1;
        c.gridwidth=1;

        c.gridx=0;
        jcp.add(labelX0, c);
        c.gridx=2;
        jcp.add(labelX, c);
        c.gridx=4;
        jcp.add(labelY0, c);
        c.gridx=6;
        jcp.add(labelN, c);



        //input texts
        c.gridy=0;
        c.weightx=1;

        c.gridx=1;
        jcp.add(textX0, c);
        c.gridx=3;
        jcp.add(textX, c);
        c.gridx=5;
        jcp.add(textY0, c);
        c.gridx=7;
        jcp.add(textN, c);

        //button
        JButton button = new JButton();
        button.setText("Compute");
        button.setMinimumSize(new Dimension(85, 20));
        button.setMaximumSize(new Dimension(85, 20));
        button.setPreferredSize(new Dimension(85, 20));
        button.addActionListener(new RListener());
        c.gridwidth=1;
        c.gridx=8;
        c.gridy=0;
        jcp.add(button, c);

        //table
        tableLocalErrors = new JTable(new Object[][]{}, new String[]{"X", "Simple Euler Local Error",
                "Improved Euler Local Error", "Runge-Kutta Local Error"});
        tableErrorN = new JTable(new Object[][]{}, new String[]{"N", "Error of N"});
        tableFunct = new JTable(new Object[][]{}, new String[]{"X", "Simple Euler",
                "Improved Euler", "Runge-Kutta", "Exact"});
        tableTotalErrors = new JTable(new Object[][]{}, new String[]{"X", "Simple Euler Error",
                "Improved Euler Error", "Runge-Kutta Error"});
        final JTabbedPane tabbedPane = new JTabbedPane();
        JScrollPane tableF=new JScrollPane(tableFunct), tableE=new JScrollPane(tableTotalErrors);
        JScrollPane tableL=new JScrollPane(tableLocalErrors), tableN=new JScrollPane(tableErrorN);
        tabbedPane.addTab("Solutions", tableF);
        tabbedPane.addTab("Total Errors", tableE);
        tabbedPane.addTab("Local Errors", tableL);
        tabbedPane.addTab("Error of N", tableN);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                panel.drawSelected(tabbedPane.getSelectedIndex());
            }
        });


        tabbedPane.setMinimumSize(new Dimension(485, 600));
        tabbedPane.setMaximumSize(new Dimension(485, 600));
        tabbedPane.setPreferredSize(new Dimension(485, 600));
        c.weighty=8;
        c.gridy=1;
        c.gridx=0;
        c.gridheight=8;
        c.gridwidth=9;
        c.weightx=9;
        jcp.add(tabbedPane, c);

        //function graphic

        c.weighty=9;
        c.gridx=9;
        c.gridy=0;
        c.gridheight=9;
        c.gridwidth=9;
        c.weightx=9;
        jcp.add(panel, c);

        jcp.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });




        jcp.setBackground(Color.gray);
        setSize(1250, 680);
        setMinimumSize(new Dimension(1250, 680));
        setMaximumSize(new Dimension(1250, 680));
        setPreferredSize(new Dimension(1250, 680));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private double round(double num){
        return (double)Math.round(num * 10000000d) / 10000000d;
    }
    public class RListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            double x0 = 0, X = 0, y0 = 0;
            int N = 0;
            try {
                x0 = Double.parseDouble(textX0.getText());
                X = Double.parseDouble(textX.getText());
                y0 = Double.parseDouble(textY0.getText());
                N = Integer.parseInt(textN.getText());
            } catch (Exception ex) {
                System.out.println("--");
            }
            //getting data from solvers
            double[] Xs = new SimpleEulerMethod().getArrX(x0, X, N);
            double[] simpleEulerMethodSolution = new SimpleEulerMethod().solve(N, y0, x0, X);
            double[] exactSolution = new ExactSolution().solve(N, y0, x0, X);
            double[] improvedEulerMethodSolution = new ImprovedEulerMethod().solve(N, y0, x0, X);
            double[] rungeKuttaMethodSolution = new RungeKuttaMethod().solve(N, y0, x0, X);
            Double[][] rowsFunctions = new Double[N+1][5];
            Double[][] rowsTotalErrors = new Double[N+1][4];
            Double[][] columnsFunctions = new Double[5][N+1];
            Double[][] columnsTotalErrors = new Double[3][N+1];
            Double[][] rowsLocalErrors = new Double[N+1][4];
            Double[][] columnsLocalErrors = new Double[3][N+1];
            Double[][] rowsErrorN = new Double[N][4];
            Double[][] columnsErrorN = new Double[4][N];


            //reformatting data
            double[] errorN, correctN;
            for (int i = 0; i < N + 1; i++) {

                rowsFunctions[i][0] = round(Xs[i]);
                rowsFunctions[i][1] = round(simpleEulerMethodSolution[i]);
                rowsFunctions[i][2] = round(improvedEulerMethodSolution[i]);
                rowsFunctions[i][3] = round(rungeKuttaMethodSolution[i]);
                rowsFunctions[i][4] = round(exactSolution[i]);
                rowsTotalErrors[i][0] = rowsFunctions[i][0];
                rowsTotalErrors[i][1] = round(Math.abs(rowsFunctions[i][1]-rowsFunctions[i][4]));
                rowsTotalErrors[i][2] = round(Math.abs(rowsFunctions[i][2]-rowsFunctions[i][4]));
                rowsTotalErrors[i][3] = round(Math.abs(rowsFunctions[i][3]-rowsFunctions[i][4]));
                rowsLocalErrors[i][0]=rowsFunctions[i][0];
                if(i>0) {

                    rowsLocalErrors[i][1] = round(Math.abs(rowsTotalErrors[i][1] - rowsTotalErrors[i - 1][1]));
                    rowsLocalErrors[i][2] = round(Math.abs(rowsTotalErrors[i][2] - rowsTotalErrors[i - 1][2]));
                    rowsLocalErrors[i][3] = round(Math.abs(rowsTotalErrors[i][3] - rowsTotalErrors[i - 1][3]));
                }else{
                    rowsLocalErrors[i][1]=0.;
                    rowsLocalErrors[i][2]=0.;
                    rowsLocalErrors[i][3]=0.;
                }
                if(i<N){
                    rowsErrorN[i][0]=round(i+1);
                    correctN=new ExactSolution().solve(i+1, y0, x0, X);
                    errorN=new SimpleEulerMethod().solve(i+1, y0, x0, X);
                    rowsErrorN[i][1]=round(Math.abs(correctN[correctN.length-1]-errorN[errorN.length-1]));
                    errorN=new ImprovedEulerMethod().solve(i+1, y0, x0, X);
                    rowsErrorN[i][2]=round(Math.abs(correctN[correctN.length-1]-errorN[errorN.length-1]));
                    errorN=new RungeKuttaMethod().solve(i+1, y0, x0, X);
                    rowsErrorN[i][3]=round(Math.abs(correctN[correctN.length-1]-errorN[errorN.length-1]));
                    columnsErrorN[0][i] = rowsErrorN[i][0];
                    columnsErrorN[1][i] = rowsErrorN[i][1];
                    columnsErrorN[2][i] = rowsErrorN[i][2];
                    columnsErrorN[3][i] = rowsErrorN[i][3];
                }
                columnsFunctions[0][i] = rowsFunctions[i][0];
                columnsFunctions[1][i] = rowsFunctions[i][1];
                columnsFunctions[2][i] = rowsFunctions[i][2];
                columnsFunctions[3][i] = rowsFunctions[i][3];
                columnsFunctions[4][i] = rowsFunctions[i][4];
                columnsTotalErrors[0][i] = rowsTotalErrors[i][1];
                columnsTotalErrors[1][i] = rowsTotalErrors[i][2];
                columnsTotalErrors[2][i] = rowsTotalErrors[i][3];
                columnsLocalErrors[0][i] = rowsLocalErrors[i][1];
                columnsLocalErrors[1][i] = rowsLocalErrors[i][2];
                columnsLocalErrors[2][i] = rowsLocalErrors[i][3];



            }
            panel.saveData(columnsFunctions, columnsTotalErrors, columnsLocalErrors, columnsErrorN, N);

            tableFunct.setModel(new DefaultTableModel());
            tableTotalErrors.setModel(new DefaultTableModel());
            tableLocalErrors.setModel(new DefaultTableModel());
            tableErrorN.setModel(new DefaultTableModel());
            TableModel model1 = tableFunct.getModel();
            TableModel model2 = tableTotalErrors.getModel();
            TableModel model3 = tableLocalErrors.getModel();
            TableModel model4 = tableErrorN.getModel();

            //update tables
            ((DefaultTableModel)model1).setDataVector(
                    rowsFunctions,
                    new String[]{"X", "Simple Euler", "Improved Euler", "Runge-Kutta", "Exact"});
            ((DefaultTableModel)model2).setDataVector(
                    rowsTotalErrors,
                    new String[]{"X", "Simple Euler Error", "Improved Euler Error", "Runge-Kutta Error"});
            ((DefaultTableModel)model3).setDataVector(
                    rowsLocalErrors,
                    new String[]{"X", "Simple Euler Error", "Improved Euler Error", "Runge-Kutta Error"});
            ((DefaultTableModel)model4).setDataVector(
                    rowsErrorN,
                    new String[]{"N", "Simple Euler Error", "Improved Euler Error", "Runge-Kutta Error"});
            repaint();
        }
    }
}
