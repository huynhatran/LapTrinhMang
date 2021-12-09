package laptrinhmang_doan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ThongKeCovidPanel extends javax.swing.JPanel {
    DefaultTableModel modelTable;
    String clickButtonColor = "#3399FF";
    String ButtonColor = "#999999";
    
    public ThongKeCovidPanel() {
        initComponents();
        cardPanel1.imageIcon = new ImageIcon("src/Icons/total-sales-30.png");
        cardPanel4.imageIcon = new ImageIcon("src/Icons/verified-account-30.png");
        cardPanel2.imageIcon = new ImageIcon("src/Icons/health-book-30.png");
        cardPanel3.imageIcon = new ImageIcon("src/Icons/heart-30.png");
        Button_TheGioi.setBackground(Color.decode(clickButtonColor));
        Button_VietNam.setBackground(Color.decode(ButtonColor));
        
        styleTable(table);
        callServer("thongkecovidthegioi#");
//        TableRowSorter<TableModel> sorter =  new TableRowSorter<> (table.getModel());
//        table.setRowSorter(sorter);
//        List<RowSorter.SortKey> sortKeys = new ArrayList<>(50);
//        sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING));
//        //sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//        sorter.setSortKeys(sortKeys);
    }

    public void callServer(String tenChucNang){
        MainFrame._CONNECT_SERVER.senData(MainFrame._SERCURITY_CLIENT.maHoaAES(tenChucNang, MainFrame._SESSION_KEY));  
        //nhận và giải mã dữ liệu nhận được từ server
        String ketquaNhan = MainFrame._SERCURITY_CLIENT.giaiMaAES(MainFrame._CONNECT_SERVER.receiveData(), MainFrame._SESSION_KEY);
        setDataTable(ketquaNhan);
    }
    public void showPieChart(Double khoiBenh, Double tuVong, Double caMoi){
        //create dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Ca mới", caMoi);
        dataset.setValue("Khỏi bệnh", khoiBenh);
        dataset.setValue("Tử vong", tuVong);
        
        //create chart
        JFreeChart piechar = ChartFactory.createPieChart("Hôm nay", dataset, true, true, true);
        PiePlot piePlot = (PiePlot) piechar.getPlot();
        
        //changing pie chart blocks colors
        piePlot.setSectionPaint("Ca mới", new Color(255,204,0));
        piePlot.setSectionPaint("Khỏi bệnh", new Color(102,255,102));
        piePlot.setSectionPaint("Tử vong", new Color(255,102,153));
        
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel chartPanel = new ChartPanel(piechar);
        chartPanel.setMouseWheelEnabled(true);
        //chartPanel.setPreferredSize(new Dimension(200,100));
        PanelPieChart.removeAll();
        //PanelPieChart.setLayout(new BorderLayout());
        PanelPieChart.add(chartPanel, BorderLayout.CENTER);
        
        PanelPieChart.repaint();
        PanelPieChart.validate();

    }
    public void showLineChart(String ngay1, Double soCa1, String ngay2, Double soCa2, String ngay3, Double soCa3, String ngay4, Double soCa4, String ngay5, Double soCa5, String ngay6, Double soCa6, String ngay7, Double soCa7){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(soCa1, "Số ca", ngay1);
        dataset.setValue(soCa2, "Số ca", ngay2);
        dataset.setValue(soCa3, "Số ca", ngay3);
        dataset.setValue(soCa4, "Số ca", ngay4);
        dataset.setValue(soCa5, "Số ca", ngay5);
        dataset.setValue(soCa6, "Số ca", ngay6);
        dataset.setValue(soCa7, "Số ca", ngay7);
        JFreeChart linechar = ChartFactory.createLineChart("Số ca nhiễm 7 ngày qua", "Ngày", "Số ca", dataset, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot linecatagoryPlot = linechar.getCategoryPlot();
        linecatagoryPlot.setBackgroundPaint(Color.white);
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRender = (LineAndShapeRenderer) linecatagoryPlot.getRenderer();
        Color lineChartColor = new Color(0,153,255);
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
//        PanelBarChart.removeAll();
//        PanelBarChart.add(chartPanel, BorderLayout.CENTER);
//        PanelBarChart.repaint();
//        PanelBarChart.validate();
    }
    public void setDataTable(String ketquaNhan){
        JSONArray arr = null;
        try{
            JSONParser parse = new JSONParser();
            JSONObject data = (JSONObject) parse.parse(ketquaNhan);
            if(data.get("status").equals("success")){
                Vector header = new Vector();
                if(data.get("function").toString().equals("tracuucovidthegioi")){
                    Label_Title.setText("Thế Giới");
                    arr = (JSONArray) data.get("TheGioi");
                    header.add("Quốc gia");
                }else{
                    Label_Title.setText("Việt Nam");
                    arr = (JSONArray) data.get("VietNam");
                    header.add("Tỉnh thành");
                }
                //card
                JSONObject getLastArray = (JSONObject) arr.get(arr.size()-1);//thống kê chung của TG/VN nằm cuối mảng
                setDataCard(getLastArray);
                
                //table
                header.add("Tổng số ca mắc");
                header.add("Tổng số ca hồi phục");
                header.add("Tổng số ca tử vong");
                header.add("Số ca mới trong ngày");
                header.add("Số ca hồi phục trong ngày");
                header.add("Tử vong trong ngày");
                
                modelTable = new DefaultTableModel(header,0);
                
                for(int i = 0;i<arr.size()-1;i++) {
                    JSONObject object = (JSONObject) arr.get(i);
                    Vector row = new Vector();
                    row.add(object.get("province_name").toString());
                    row.add(object.get("confirmed").toString());
                    row.add(object.get("recovered").toString());
                    row.add(object.get("deaths").toString());
                    row.add(object.get("increase_confirmed").toString());
                    row.add(object.get("increase_recovered").toString());
                    row.add(object.get("increase_deaths").toString());
                    
//                    row.add(dinhDangSo(Float.parseFloat(object.get("increase_confirmed").toString())));
//                    row.add(dinhDangSo(Float.parseFloat(object.get("increase_recovered").toString())));
//                    row.add(dinhDangSo(Float.parseFloat(object.get("increase_deaths").toString())));
//                    row.add(dinhDangSo(Float.parseFloat(object.get("confirmed").toString())));
//                    row.add(dinhDangSo(Float.parseFloat(object.get("recovered").toString())));
//                    row.add(dinhDangSo(Float.parseFloat(object.get("deaths").toString())));
                    modelTable.addRow(row);
                }
               table.setModel(modelTable);
               //chart
               JSONArray arrayChart = (JSONArray) data.get("ThongKeBayNgay");
               String ngay1, ngay2,ngay3, ngay4, ngay5, ngay6, ngay7;
               Double soCa1,soCa2,soCa3,soCa4,soCa5,soCa6,soCa7;
               for(int i =0;i<arrayChart.size();i++){
                   JSONObject ob = (JSONObject) arrayChart.get(i);
                   StringTokenizer st = new StringTokenizer(ob.get("Date").toString(), "T");
                   ngay1 = st.nextToken();
               }
                showLineChart(new StringTokenizer(((JSONObject) arrayChart.get(0)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(0)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(1)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(1)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(2)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(2)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(3)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(3)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(4)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(4)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(5)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(5)).get("NewConfirmed").toString()),
                            new StringTokenizer(((JSONObject) arrayChart.get(6)).get("Date").toString(), "T").nextToken(),Double.parseDouble(((JSONObject) arrayChart.get(6)).get("NewConfirmed").toString()));
                //new SimpleDateFormat("dd/MM/yyyy").parse(new StringTokenizer(((JSONObject) arrayChart.get(6)).get("Date").toString(), "T").nextToken()).toString(),7.0);
            }else{
                if(data.get("status").equals("updating")){
                    //tạo frame đang update dữ liệu
                    jPanel1.setVisible(false);
                    jPanel3.setVisible(false);
                    Panel_Updating.setBounds(0, 0, 1130, 840);
                    this.add(Panel_Updating);
                }else{
                    JOptionPane.showMessageDialog(null, "Lấy dữ liệu từ server fail.");
                }
            }
            
        }catch(HeadlessException | NumberFormatException | ParseException e){
            System.err.println(e.getMessage());
        }
    }
    public void styleTable(JTable table){
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(Color.decode("#00CCFF"));
        table.getTableHeader().setFont(new Font("Tahoma", 1, 13));
        //table.setFont(new Font("Tahoma", 1, 13));
        table.setRowHeight(30);
//        table.setSelectionBackground(new Color(255, 255, 0));//màu nền khi click chuột vào một row trên table
//        table.setGridColor(Color.decode("#00CCFF"));
//        table.getColumnModel().getColumn(0).setPreferredWidth(40);//độ rộng của cột
//        table.getColumnModel().getColumn(2).setPreferredWidth(60);
    }
    public void setDataCard(JSONObject ob){
        //chung
        cardPanel1.setSubText(" "+ dinhDangSo(Float.parseFloat(ob.get("confirmed").toString())));
        cardPanel1.setHomNayText("Hôm nay: "+ dinhDangSo(Float.parseFloat(ob.get("increase_confirmed").toString())));
        //khỏi bệnh
        cardPanel4.setSubText(" "+ dinhDangSo(Float.parseFloat(ob.get("recovered").toString())));
        cardPanel4.setHomNayText("Hôm nay: "+ dinhDangSo(Float.parseFloat(ob.get("increase_recovered").toString())));
        //đang điều trị
        int tongSoCaDieuTri = Integer.parseInt(ob.get("confirmed").toString()) - (Integer.parseInt(ob.get("recovered").toString()) + Integer.parseInt(ob.get("deaths").toString()));
        cardPanel2.setSubText(" "+ dinhDangSo(Float.parseFloat(String.valueOf(tongSoCaDieuTri))));
        //int tongSoCaDieuTriTrongNgay = Integer.parseInt(ob.get("increase_confirmed").toString()) - (Integer.parseInt(ob.get("recovered").toString()) + Integer.parseInt(ob.get("deaths").toString()));
        cardPanel2.setHomNayText("");
        //tử vong
        cardPanel3.setSubText(" "+ dinhDangSo(Float.parseFloat(ob.get("deaths").toString())));
        cardPanel3.setHomNayText("Hôm nay: "+ dinhDangSo(Float.parseFloat(ob.get("increase_deaths").toString())));
        showPieChart(Double.parseDouble(ob.get("increase_recovered").toString()), Double.parseDouble(ob.get("increase_deaths").toString()), Double.parseDouble(ob.get("increase_confirmed").toString()));
    }
    public String dinhDangSo(Float str){
        // tạo 1 NumberFormat để định dạng số theo tiêu chuẩn của nước Anh
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);

        // đối với số có kiểu long được định dạng theo chuẩn của nước Anh
        // thì phần ngàn của số được phân cách bằng dấu phẩy
        float longNumber = str;
        String str1 = en.format(longNumber);
        return str1;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Updating = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cardPanel1 = new laptrinhmang_doan.CardPanel();
        cardPanel4 = new laptrinhmang_doan.CardPanel();
        cardPanel2 = new laptrinhmang_doan.CardPanel();
        cardPanel3 = new laptrinhmang_doan.CardPanel();
        Label_Title = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        PanelLineChart = new javax.swing.JPanel();
        PanelPieChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        Button_TheGioi = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Button_VietNam = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        Panel_Updating.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Server thống kê đang cập nhật dữ liệu, vui lòng trở lại trong vài phút tới!");

        javax.swing.GroupLayout Panel_UpdatingLayout = new javax.swing.GroupLayout(Panel_Updating);
        Panel_Updating.setLayout(Panel_UpdatingLayout);
        Panel_UpdatingLayout.setHorizontalGroup(
            Panel_UpdatingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1089, Short.MAX_VALUE)
        );
        Panel_UpdatingLayout.setVerticalGroup(
            Panel_UpdatingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_UpdatingLayout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(365, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1130, 840));
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(51, 204, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());
        add(jPanel1);
        jPanel1.setBounds(31, 19, 0, 0);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1130, 840));

        jPanel4.setBackground(new java.awt.Color(51, 204, 0));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        cardPanel1.setColor1(new java.awt.Color(255, 204, 0));
        cardPanel1.setColor2(new java.awt.Color(255, 204, 102));
        cardPanel1.setSubText(" 1.234.234.12345");
        cardPanel1.setTitleText(" Tổng số ca");

        cardPanel4.setColor1(new java.awt.Color(51, 255, 0));
        cardPanel4.setColor2(new java.awt.Color(0, 204, 0));
        cardPanel4.setSubText(" 1.234.234.12345");
        cardPanel4.setTitleText(" Khỏi bệnh");

        cardPanel2.setColor1(new java.awt.Color(0, 204, 255));
        cardPanel2.setColor2(new java.awt.Color(0, 153, 255));
        cardPanel2.setSubText(" 1.234.234.12345");
        cardPanel2.setTitleText(" Đang điều trị");

        cardPanel3.setColor1(new java.awt.Color(255, 51, 51));
        cardPanel3.setColor2(new java.awt.Color(255, 102, 102));
        cardPanel3.setSubText(" 1.234.234.12345");
        cardPanel3.setTitleText(" Tử vong");

        Label_Title.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        Label_Title.setText("Thế Giới");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(cardPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(cardPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(cardPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(cardPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_Title, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(Label_Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("THỐNG KÊ COVID-19 ");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(null);

        PanelLineChart.setBackground(new java.awt.Color(204, 204, 204));
        PanelLineChart.setLayout(new java.awt.BorderLayout());
        jPanel6.add(PanelLineChart);
        PanelLineChart.setBounds(10, 0, 780, 250);

        PanelPieChart.setBackground(new java.awt.Color(204, 204, 204));
        PanelPieChart.setLayout(new java.awt.BorderLayout());
        jPanel6.add(PanelPieChart);
        PanelPieChart.setBounds(800, 0, 250, 250);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(table);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(0, 43, 1057, 249);

        Button_TheGioi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_TheGioiMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/globe-30.png"))); // NOI18N
        jLabel3.setText("Thế Giới");

        javax.swing.GroupLayout Button_TheGioiLayout = new javax.swing.GroupLayout(Button_TheGioi);
        Button_TheGioi.setLayout(Button_TheGioiLayout);
        Button_TheGioiLayout.setHorizontalGroup(
            Button_TheGioiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Button_TheGioiLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        Button_TheGioiLayout.setVerticalGroup(
            Button_TheGioiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Button_TheGioi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(Button_TheGioi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8);
        jPanel8.setBounds(0, 0, 105, 35);

        Button_VietNam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_VietNamMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/viet-nam-flag-24px.gif"))); // NOI18N
        jLabel1.setText("Việt Nam");

        javax.swing.GroupLayout Button_VietNamLayout = new javax.swing.GroupLayout(Button_VietNam);
        Button_VietNam.setLayout(Button_VietNamLayout);
        Button_VietNamLayout.setHorizontalGroup(
            Button_VietNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Button_VietNamLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        );
        Button_VietNamLayout.setVerticalGroup(
            Button_VietNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Button_VietNamLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(Button_VietNam);
        Button_VietNam.setBounds(112, 0, 104, 35);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng số ca mắc", "Tổng số ca hồi phục", "Tổng số ca tử vong", "Số ca mới trong ngày", "Số ca hồi phục trong ngày", "Tử vong trong ngày" }));
        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(732, 0, 197, 36);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm dần", "Tăng dần" }));
        jPanel2.add(jComboBox2);
        jComboBox2.setBounds(936, 0, 121, 36);

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setText("Sắp xếp:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(667, 0, 60, 36);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3);
        jPanel3.setBounds(0, 0, 1130, 840);
    }// </editor-fold>//GEN-END:initComponents

    private void Button_TheGioiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_TheGioiMouseClicked
        Button_TheGioi.setBackground(Color.decode(clickButtonColor));
        Button_VietNam.setBackground(Color.decode(ButtonColor));
        callServer("thongkecovidthegioi#");
    }//GEN-LAST:event_Button_TheGioiMouseClicked

    private void Button_VietNamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_VietNamMouseClicked
        Button_TheGioi.setBackground(Color.decode(ButtonColor));
        Button_VietNam.setBackground(Color.decode(clickButtonColor));
        callServer("thongkecovidvietnam#");
        //cardPanel3.setLabel_Sub("al");
    }//GEN-LAST:event_Button_VietNamMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Button_TheGioi;
    private javax.swing.JPanel Button_VietNam;
    private javax.swing.JLabel Label_Title;
    private javax.swing.JPanel PanelLineChart;
    private javax.swing.JPanel PanelPieChart;
    private javax.swing.JPanel Panel_Updating;
    private laptrinhmang_doan.CardPanel cardPanel1;
    private laptrinhmang_doan.CardPanel cardPanel2;
    private laptrinhmang_doan.CardPanel cardPanel3;
    private laptrinhmang_doan.CardPanel cardPanel4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
