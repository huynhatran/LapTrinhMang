package laptrinhmang_doan;

import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ThongKeCovidPanel extends javax.swing.JPanel {

    public ThongKeCovidPanel() {
        initComponents();
        showPieChart();
        showLineChart();
        showBarChart();
    }

    public void showPieChart(){
        //create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("NewConfirmed", new Double(20));
        dataset.setValue("Active", new Double(40));
        dataset.setValue("NewDeaths", new Double(30));
        dataset.setValue("NewRecovered", new Double(10));
        
        //create chart
        JFreeChart piechar = ChartFactory.createPieChart("Global", dataset, true, true, true);
        PiePlot piePlot = (PiePlot) piechar.getPlot();
        
        //changing pie chart blocks colors
        piePlot.setSectionPaint("NewConfirmed", new Color(255,255,102));
        piePlot.setSectionPaint("Active", new Color(102,255,102));
        piePlot.setSectionPaint("NewDeaths", new Color(255,102,153));
        piePlot.setSectionPaint("NewRecovered", new Color(0,254,204));
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel chartPanel = new ChartPanel(piechar);
        chartPanel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(200,100));
        jPanel1.removeAll();
        //PanelPieChart.setLayout(new BorderLayout());
        jPanel1.add(chartPanel, BorderLayout.CENTER);
        
        jPanel1.repaint();
        jPanel1.validate();

    }
    public void showLineChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart linechar = ChartFactory.createLineChart("Daily New Cases", "monthly", "amount", dataset, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot linecatagoryPlot = linechar.getCategoryPlot();
        linecatagoryPlot.setBackgroundPaint(Color.white);
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRender = (LineAndShapeRenderer) linecatagoryPlot.getRenderer();
        Color lineChartColor = new Color(204,0,51);
        lineRender.setSeriesPaint(0, lineChartColor);
        
        ChartPanel chartPanel = new ChartPanel(linechar);
        PanelLineChart.removeAll();
        PanelLineChart.add(chartPanel, BorderLayout.CENTER);
        PanelLineChart.repaint();
        PanelLineChart.validate();
    }
    public void showBarChart(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        JFreeChart barchar = ChartFactory.createBarChart("Cases", "monthly", "amount", dataset, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot catagoryPlot = barchar.getCategoryPlot();
        catagoryPlot.setBackgroundPaint(Color.white);
        BarRenderer renderer = (BarRenderer) catagoryPlot.getRenderer();
        Color lineChartColor = new Color(204,0,51);
        renderer.setSeriesPaint(0, lineChartColor);
        ChartPanel chartPanel = new ChartPanel(barchar);
        PanelBarChart.removeAll();
        PanelBarChart.add(chartPanel, BorderLayout.CENTER);
        PanelBarChart.repaint();
        PanelBarChart.validate();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        PanelPieChart = new javax.swing.JPanel();
        PanelLineChart = new javax.swing.JPanel();
        PanelBarChart = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1130, 840));
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(51, 204, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1);
        jPanel1.setBounds(31, 19, 330, 285);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1130, 840));
        jPanel3.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(51, 204, 0));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4);
        jPanel4.setBounds(31, 19, 0, 0);

        PanelPieChart.setBackground(new java.awt.Color(204, 204, 204));
        PanelPieChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(PanelPieChart);
        PanelPieChart.setBounds(30, 20, 330, 285);

        PanelLineChart.setBackground(new java.awt.Color(204, 204, 204));
        PanelLineChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(PanelLineChart);
        PanelLineChart.setBounds(400, 20, 330, 285);

        PanelBarChart.setBackground(new java.awt.Color(204, 204, 204));
        PanelBarChart.setLayout(new java.awt.BorderLayout());
        jPanel3.add(PanelBarChart);
        PanelBarChart.setBounds(770, 20, 330, 285);

        add(jPanel3);
        jPanel3.setBounds(0, 0, 1130, 840);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBarChart;
    private javax.swing.JPanel PanelLineChart;
    private javax.swing.JPanel PanelPieChart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
