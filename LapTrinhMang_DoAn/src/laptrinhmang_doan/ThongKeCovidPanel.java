package laptrinhmang_doan;

import EventComponentCustomize.SearchCallBack;
import EventComponentCustomize.SearchEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        searchComponent1.hintText = "Nhập vào tên quốc gia ...";

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

        searchComponent1 = new laptrinhmang_doan.SearchComponent();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        PanelAllCovid = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        BieuDolabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        soCaTulabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tenQGlabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        soCaNhiemlabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        soCaNhiemHQlabel = new javax.swing.JLabel();
        nameQGlabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1130, 840));
        setLayout(null);
        add(searchComponent1);
        searchComponent1.setBounds(80, 0, 970, 41);

        jPanel1.setBackground(new java.awt.Color(51, 204, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1);
        jPanel1.setBounds(31, 19, 0, 0);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1130, 840));
        jPanel3.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(51, 204, 0));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4);
        jPanel4.setBounds(31, 19, 0, 0);

        PanelAllCovid.setLayout(null);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/covid.png"))); // NOI18N
        jLabel10.setText("jLabel10");
        PanelAllCovid.add(jLabel10);
        jLabel10.setBounds(0, 0, 1130, 270);

        jPanel3.add(PanelAllCovid);
        PanelAllCovid.setBounds(0, 0, 1130, 270);

        jPanel2.setLayout(null);

        jLabel3.setText("số ca nhiễm mới (14 ngày): ");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(20, 20, 250, 20);

        BieuDolabel.setText(" ");
        jPanel2.add(BieuDolabel);
        BieuDolabel.setBounds(20, 50, 270, 90);

        jLabel4.setText("dd/mm/yy-dd/mm/yy: +000000");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 150, 290, 40);

        jPanel3.add(jPanel2);
        jPanel2.setBounds(410, 270, 300, 200);

        jPanel5.setLayout(null);

        jLabel5.setText("Số ca tử vong: ");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(20, 10, 110, 30);

        soCaTulabel.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        soCaTulabel.setText("00000");
        jPanel5.add(soCaTulabel);
        soCaTulabel.setBounds(20, 60, 180, 60);

        jLabel6.setText("Số ca ghi nhận ngày hôm qua:");
        jPanel5.add(jLabel6);
        jLabel6.setBounds(20, 150, 230, 30);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("0000");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(250, 150, 70, 30);

        jPanel3.add(jPanel5);
        jPanel5.setBounds(780, 270, 320, 200);

        jPanel6.setLayout(null);

        tenQGlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tenQGlabel.setText("Quốc gia: ");
        jPanel6.add(tenQGlabel);
        tenQGlabel.setBounds(10, 10, 120, 40);

        jLabel2.setText("Tổng số ca nhiễm:");
        jPanel6.add(jLabel2);
        jLabel2.setBounds(10, 50, 150, 20);

        soCaNhiemlabel.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        soCaNhiemlabel.setText("00000000");
        jPanel6.add(soCaNhiemlabel);
        soCaNhiemlabel.setBounds(10, 80, 200, 40);

        jLabel1.setText("Số ca nhiễm hôm qua: ");
        jPanel6.add(jLabel1);
        jLabel1.setBounds(10, 160, 170, 30);

        soCaNhiemHQlabel.setText("+000000");
        jPanel6.add(soCaNhiemHQlabel);
        soCaNhiemHQlabel.setBounds(180, 160, 90, 30);

        nameQGlabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        nameQGlabel.setText("$Name");
        jPanel6.add(nameQGlabel);
        nameQGlabel.setBounds(140, 10, 150, 40);

        jPanel3.add(jPanel6);
        jPanel6.setBounds(40, 270, 300, 200);

        jPanel7.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Số ca nhiễm");
        jPanel7.add(jLabel8);
        jLabel8.setBounds(10, 10, 150, 40);

        jPanel3.add(jPanel7);
        jPanel7.setBounds(0, 530, 550, 260);

        jPanel8.setLayout(null);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Số ca tử vong");
        jPanel8.add(jLabel9);
        jLabel9.setBounds(20, 10, 170, 40);

        jPanel3.add(jPanel8);
        jPanel8.setBounds(550, 530, 580, 260);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        jLabel11.setText("Biểu đồ");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(10, 470, 170, 60);

        add(jPanel3);
        jPanel3.setBounds(0, 50, 1130, 790);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BieuDolabel;
    private javax.swing.JPanel PanelAllCovid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel nameQGlabel;
    private laptrinhmang_doan.SearchComponent searchComponent1;
    private javax.swing.JLabel soCaNhiemHQlabel;
    private javax.swing.JLabel soCaNhiemlabel;
    private javax.swing.JLabel soCaTulabel;
    private javax.swing.JLabel tenQGlabel;
    // End of variables declaration//GEN-END:variables
}
