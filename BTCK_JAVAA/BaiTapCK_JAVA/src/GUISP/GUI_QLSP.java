package GUISP;
/**
 *
 * @author Nguyen Viet Anh Quyen
 */
import GUILogin_And_Register.GUILogin;
import GUISP.GUI_QLSP;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class GUI_QLSP extends JFrame {
//PART 1 : KHAI BÁO CÁC BIẾN, TẠO CÁC PHƯƠNG THỨC ĐỂ XỬ LÍ DỮ LIỆU VÀ TẠO CONSTRUCTOR
    
    //1 - Khai báo các thành phần kết nối CSDL
        String URL = "jdbc:sqlserver://localhost:1433;databaseName=Product;user=sa;password=quyen230504";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    //2 - Khai báo các thuộc tính
        String idSP, nameSP, nhaSX,namSX,loaiSP, xuatxu, gianhapkho, giaban,daban, dangban, GNK,GB;
        DefaultTableModel model;
    
    //3 - Tạo các phương thức
    
        //a. Phương thức thêm sản phẩm vào bảng và CSDL
        public boolean addProduct(){
        model = (DefaultTableModel) jTable1.getModel();
        idSP = idSP_text.getText();
        nameSP = nameSP_text.getText();
        nhaSX = nhaSX_text.getText();
        namSX = namSX_combobox.getSelectedItem().toString();
        loaiSP = loaiSP_combobox.getSelectedItem().toString();
        xuatxu = xuatxu_combobox.getSelectedItem().toString();
        gianhapkho = giaNhap_text.getText();
            GNK = giaNhap_text.getText();
        giaban = giaBan_text.getText();
            GB = giaBan_text.getText();
        daban = daBan_text.getText();
        dangban = dangBan_text.getText();
        //Xóa bỏ các dấu chấm phẩy trong ô giá nhập kho và giá bán trước khi đưa vào cơ sở dữ liệu
        for (int i = 0 ; i < gianhapkho.length(); i++)
            if ( gianhapkho.charAt(i) == '.') gianhapkho = gianhapkho.substring(0,i) + gianhapkho.substring(i+1);
        for (int i = 0 ; i < giaban.length(); i++)
            if ( giaban.charAt(i) == '.') giaban = giaban.substring(0,i) + giaban.substring(i+1);
        //Thêm dữ liệu vào cơ sở dữ liệu
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement("insert into Information (ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban) values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, idSP);
            ps.setString(2, nameSP);
            ps.setString(3, nhaSX);
            ps.setInt(4,Integer.valueOf(namSX));
            ps.setString(5,loaiSP);
            ps.setString(6, xuatxu);
            ps.setInt(7, Integer.valueOf(gianhapkho));
            ps.setInt(8, Integer.valueOf(giaban));
            ps.setInt(9, Integer.valueOf(daban));
            ps.setInt(10,Integer.valueOf(dangban));
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
        //b. Phương thức xóa một sản phẩm ra khỏi bảng và CSDL
        public void deleteProduct(){
        if ( jTable1.getSelectedRowCount() == 1){
            try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(URL);
                int index = jTable1.getSelectedRow();
                String id_deleted = jTable1.getModel().getValueAt(index, 0).toString();
                //Hỏi trước khi xóa một sản phẩm
                int ans = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn xóa sản phẩm này không ?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ans == 0 ){
                    PreparedStatement ps = conn.prepareStatement("delete from Information where ID = '" + id_deleted + "'");
                    ps.executeUpdate();
                    model.removeRow(index);
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
           
         } else {
             if (jTable1.getRowCount() == 0 ){
                 JOptionPane.showMessageDialog(rootPane, "Không có dữ liệu", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
             } else JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một dòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
         }
    }
    
        //c. Phương thức hiển thị tất cả các dữ liệu từ SQL ra Table
        public void showAll(){
        try{
            jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select*from Information");
            model = (DefaultTableModel) jTable1.getModel();            
            while (rs.next()){      
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch(ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex){
                ex.printStackTrace();
        }        
    }
    
        //d. Phương thức xóa hết tất cả dữ liệu từ SQL và trong bảng
        public void deleteAll(){
        try{
            if (jTable1.getRowCount() == 0 ){
                JOptionPane.showMessageDialog(rootPane, "Không có dữ liệu","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            st.executeUpdate("delete from Information");
            jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        } catch(ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex){
                ex.printStackTrace();
        }
    }
    
        //e. Phương thức làm mới dữ liệu nhập vào nhưng không xóa dữ liệu trong cơ sở dữ liệu
        public void resetData(){
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
    }
    
        //f. Sự kiện cập nhật thông tin sản phẩm trong cơ sở dữ liệu và bảng
        public boolean updateInformation(){
            idSP = idSP_text.getText();
            nameSP = nameSP_text.getText();
            nhaSX = nhaSX_text.getText();
            namSX = namSX_combobox.getSelectedItem().toString();
            loaiSP = loaiSP_combobox.getSelectedItem().toString();
            xuatxu = xuatxu_combobox.getSelectedItem().toString();
            gianhapkho = giaNhap_text.getText();
            giaban = giaBan_text.getText();
            daban = daBan_text.getText();
            dangban = dangBan_text.getText();
            model = (DefaultTableModel) jTable1.getModel();
            String oldID = model.getValueAt(jTable1.getSelectedRow(), 0).toString();
            //Xóa dấu chấm của giá tiền trước khi đưa vào cơ sở dữ liệu
            for (int i = 0 ; i < gianhapkho.length(); i++)
                if ( gianhapkho.charAt(i) == '.') gianhapkho = gianhapkho.substring(0,i) + gianhapkho.substring(i+1);
            for (int i = 0 ; i < giaban.length(); i++)
                if ( giaban.charAt(i) == '.') giaban = giaban.substring(0,i) + giaban.substring(i+1);
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement("update Information set  "
                    + "ID = '" +idSP+ "', nameSP = N'" +nameSP+ "', nhaSX = N'" +nhaSX+ "', namSX = " +Integer.parseInt(namSX)+ ", loaiSP = N'" +loaiSP+ "', xuatxu = N'"+xuatxu
                    + "', gianhapkho = " +Integer.parseInt(gianhapkho)+ ", giaban = " +Integer.parseInt(giaban)+ ", daban = " +Integer.parseInt(daban)+ ", dangban = "+ Integer.parseInt(dangban)
            + "where ID = '" +oldID+ "'");
            return ps.executeUpdate() > 0;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
        //g. Phương thức tìm kiếm thông tin sản phẩm
        public void searchInfor(int a, String s1, String s2){       
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban from Information where namSX = " + a + " and loaiSP = N'" + s1 + "' and xuatxu = N'" + s2 + "'");
            model = (DefaultTableModel) jTable1.getModel();
            while (rs.next()){
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        //h. Phương thức sắp xếp dữ liệu
        public void sort(String property, String type){
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban from Information order by " + property + " " + type);
            model = (DefaultTableModel) jTable1.getModel();
            while (rs.next()){
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
        //**Phụ ---- Phương thức thêm dấu chấm vào trong giá tiền
        public StringBuilder addDot(String s){
        StringBuilder strBuilder = new StringBuilder(s);
        int i = strBuilder.length() - 1, j;
        j = i - 2;
        while( i > 0 ){
            if ( i == j ){
                strBuilder.insert(j, '.');
                i--;
                j = i - 2;
            } else i--;
        }
        return strBuilder;
    }
    
        //**Phụ ---- chuyển đổi dữ liệu trong jComboBox ở hộp thoại
        public void getData(String property, String type){
        if( property.equals("Năm sản xuất") && type.equals("Tăng dần")) {
            sort("namSX","ASC");
            return;
        }
        if( property.equals("Năm sản xuất") && type.equals("Giảm dần")) {
            sort("namSX","DESC");
            return;
        } 
        
        if( property.equals("Giá nhập kho") && type.equals("Tăng dần")) {
            sort("gianhapkho","ASC");
            return;
        }
        if( property.equals("Giá nhập kho") && type.equals("Giảm dần")) {
            sort("gianhapkho","DESC");
            return;
        }
        
        if( property.equals("Giá bán") && type.equals("Tăng dần")) {
            sort("giaban","ASC");
            return;
        }
        if( property.equals("Giá bán") && type.equals("Giảm dần")) {
            sort("giaban","DESC");
            return;
        }
        
        if( property.equals("Đã bán") && type.equals("Tăng dần")) {
            sort("daban","ASC");
            return;
        }
        if( property.equals("Đã bán") && type.equals("Giảm dần")) {
            sort("daban","DESC");
            return;
        }
        
        if( property.equals("Đang bán") && type.equals("Tăng dần")) {
            sort("dangban","ASC");
            return;
        }
        if( property.equals("Đang bán") && type.equals("Giảm dần")) {
            sort("dangban","DESC");
            return;
        }
    }
    
    //4 - Tạo các phương thức thể hiện biểu đồ
    
        //a. Phương thức lấy số liệu các loại sản phẩm trong CSDL để thể hiện biểu đồ tròn
        public void getTypeProduct(){
        //Khai báo các biến lấy số liệu
        int countCPU = 0, countKeyboard = 0, countMouse = 0, countStorage = 0, countCard = 0;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select*from Information");
            while (rs.next()){
                if ( rs.getString("loaiSP").equals("CPU")) countCPU ++;
                if ( rs.getString("loaiSP").equals("Bàn phím")) countKeyboard ++;
                if ( rs.getString("loaiSP").equals("Chuột")) countMouse ++;
                if ( rs.getString("loaiSP").equals("Thiết bị lưu trữ")) countStorage ++;
                if ( rs.getString("loaiSP").equals("Card đồ họa")) countCard ++;
            }
            //Tạo biểu đồ và đưa số liệu vào biểu đồ
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("CPU", new Double(countCPU));
        dataset.setValue("Bàn phím", new Double(countKeyboard));
        dataset.setValue("Chuột", new Double(countMouse));
        dataset.setValue("Thiết bị lưu trữ", new Double(countStorage));
        dataset.setValue("Card đồ họa", new Double(countCard));
        JFreeChart chart = ChartFactory.createPieChart(
                "Biểu đồ về số lượng sản phẩm các loại trong cửa hàng".toUpperCase(),
                dataset, true, true, true);
        JFreeChart pieChart = chart;
        ChartPanel chartPanel = new ChartPanel(pieChart);
        JFrame frame = new JFrame();
        frame.setTitle("Biểu đồ phân loại sản phẩm có trong cửa hàng");
        frame.add(chartPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        } catch(ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex){
                ex.printStackTrace();
        }
    }
    
        //b. Phương thức lấy số liệu số tiền các sản phẩm đã bán theo phân loại để thể hiện biểu đồ cột
        public void getMoneyTypeProduct(){
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select*from Information");
            //Khai báo các biến lưu dữ liệu
            double moneyCPU = 0, moneyKeyboard = 0, moneyMouse = 0, moneyStorage = 0, moneyCard = 0;
            //Lấy dữ liệu trong CSDL
            while (rs.next()){
                if (rs.getString("loaiSP").equals("CPU")){
                    double gb = (double) rs.getInt("giaban");
                    moneyCPU += (gb * rs.getInt("daban"))/ 1000000;
                }
                if (rs.getString("loaiSP").equals("Bàn phím")){
                    double gb = (double) rs.getInt("giaban");
                    moneyKeyboard += (gb * rs.getInt("daban"))/ 1000000;
                }
                if (rs.getString("loaiSP").equals("Chuột")){
                    double gb = (double) rs.getInt("giaban");
                    moneyMouse += (gb * rs.getInt("daban"))/ 1000000;
                }
                if (rs.getString("loaiSP").equals("Thiết bị lưu trữ")){
                    double gb = (double) rs.getInt("giaban");
                    moneyStorage += (gb * rs.getInt("daban"))/ 1000000;
                }
                if (rs.getString("loaiSP").equals("Card đồ họa")){
                    double gb = (double) rs.getInt("giaban");
                    moneyCard += (gb * rs.getInt("daban"))/ 1000000;
                }
            }
            //Tạo biểu đồ cột
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(moneyCPU, "triệu VNĐ", "CPU");
            dataset.addValue(moneyKeyboard, "triệu VNĐ", "Bàn Phím");
            dataset.addValue(moneyMouse, "triệu VNĐ", "Chuột");
            dataset.addValue(moneyStorage, "triệu VNĐ", "Thiết bị lưu trữ");
            dataset.addValue(moneyCard, "triệu VNĐ", "Card đồ họa");
            JFreeChart barchart = ChartFactory.createBarChart(
                "Biểu đồ thể hiện doanh thu theo phân loại sản phẩm đã bán trong cửa hàng".toUpperCase(),
                "Phân loại", "Doanh thu (triệu VNĐ)", dataset,
                PlotOrientation.VERTICAL, false, true, false);
            ChartPanel chartPanel = new ChartPanel(barchart);
            chartPanel.setPreferredSize(new  Dimension(560, 367));
            JFrame frame = new JFrame();
            frame.setTitle("Biểu đồ doanh thu theo phân loại sản phẩm");
            frame.add(chartPanel);
            frame.setSize(560, 367);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);
        } catch(ClassNotFoundException ex) {
                ex.printStackTrace();
        } catch (SQLException ex){
                ex.printStackTrace();
        }
    }
    
        //c. Phương thức tạo biểu đồ đường thể hiện doanh thu cửa hàng qua các năm
        public void getRevenueYear(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(Double.parseDouble("3.4"), "DOANH THU ( TỶ VNĐ )", "2018");
        dataset.addValue(Double.parseDouble("2.8"), "DOANH THU ( TỶ VNĐ )", "2019");
        dataset.addValue(Double.parseDouble("1.4"), "DOANH THU ( TỶ VNĐ )", "2020");
        dataset.addValue(Double.parseDouble("2.1"), "DOANH THU ( TỶ VNĐ )", "2021");
        dataset.addValue(Double.parseDouble("2.4"), "DOANH THU ( TỶ VNĐ )", "2022");
        JFreeChart lineChart = ChartFactory.createLineChart(
                "BIỂU ĐỒ DOANH THU CỦA CỬA HÀNG QUA CÁC NĂM TỪ 2018 - 2022",
                "Năm", "DOANH THU ( TỶ VNĐ )",
                dataset, PlotOrientation.VERTICAL, false, true, false);        
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        JFrame frame = new JFrame();
        frame.setTitle("Biểu đồ thể hiện doanh thu cửa hàng qua các năm");
        frame.add(chartPanel);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
    

    //5 - Tạo Constructor giao diện QLNV
        public GUI_QLSP() {
            initComponents();
            setLocationRelativeTo(null);
        }

//PART 2: BỐ TRÍ CÁC THÀNH PHẦN GIAO DIỆN                         
    
    private void initComponents() {
        //Tạo khung cho JFrame
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lí sản phẩm");
        setUndecorated(true);

//TẠO JPANEL 1: TIÊU ĐỀ CHO ỨNG DỤNG
        //1. Tạo JPanel chứa tiêu đề và hai nút đóng và ẩn cho ứng dụng (Thiết lập màu sắc, cỡ chữ, phông chữ)
        title_panel.setBackground(new java.awt.Color(0, 51, 51));
        title_panel.setForeground(new java.awt.Color(242, 242, 242));

        title_label.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        title_label.setForeground(new java.awt.Color(255, 255, 255));
        title_label.setHorizontalAlignment( SwingConstants.CENTER);
        title_label.setText("QUẢN LÍ SẢN PHẨM");

        hide_button.setBackground(new java.awt.Color(0, 51, 51));
        hide_button.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        hide_button.setForeground(new java.awt.Color(255, 255, 255));
        hide_button.setText("-");
        hide_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hide_buttonActionPerformed(evt);
            }
        });

        close_button.setBackground(new java.awt.Color(0, 51, 51));
        close_button.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        close_button.setForeground(new java.awt.Color(255, 255, 255));
        close_button.setText("x");
        close_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close_buttonActionPerformed(evt);
            }
        });
        //2. Sử dụng GroupLayout để đặt các thành phần vào trong title_panel
        GroupLayout title_panelLayout = new  GroupLayout(title_panel);
        title_panel.setLayout(title_panelLayout);
        title_panelLayout.setHorizontalGroup(
            title_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(title_panelLayout.createSequentialGroup()
                .addComponent(title_label,  GroupLayout.PREFERRED_SIZE, 199,  GroupLayout.PREFERRED_SIZE)
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hide_button,  GroupLayout.PREFERRED_SIZE, 35,  GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(close_button,  GroupLayout.PREFERRED_SIZE, 35,  GroupLayout.PREFERRED_SIZE))
        );
        title_panelLayout.setVerticalGroup(
            title_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(title_panelLayout.createSequentialGroup()
                .addGroup(title_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(hide_button,  GroupLayout.PREFERRED_SIZE, 35,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(close_button,  GroupLayout.PREFERRED_SIZE, 35,  GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(title_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_label,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
//TẠO JPANEL 2 : THÔNG TIN SẢN PHẨM
        //1. Thiết lập màu chữ, cỡ chữ, phông chữ cho các thành phần
        in4_panel.setBackground(new java.awt.Color(0, 102, 102));

        in4_label.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        in4_label.setForeground(new java.awt.Color(255, 255, 255));
        in4_label.setHorizontalAlignment( SwingConstants.CENTER);
        in4_label.setText("THÔNG TIN SẢN PHẨM");

        idSP_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        idSP_label.setForeground(new java.awt.Color(255, 255, 255));
        idSP_label.setHorizontalAlignment( SwingConstants.RIGHT);
        idSP_label.setText("Mã sản phẩm");

        nameSP_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        nameSP_label.setForeground(new java.awt.Color(255, 255, 255));
        nameSP_label.setHorizontalAlignment( SwingConstants.RIGHT);
        nameSP_label.setText("Tên sản phẩm");

        nhaSX_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        nhaSX_label.setForeground(new java.awt.Color(255, 255, 255));
        nhaSX_label.setHorizontalAlignment( SwingConstants.RIGHT);
        nhaSX_label.setText("Nhà SX");

        namSX_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        namSX_label.setForeground(new java.awt.Color(255, 255, 255));
        namSX_label.setHorizontalAlignment( SwingConstants.RIGHT);
        namSX_label.setText("Năm SX");

        loai_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        loai_label.setForeground(new java.awt.Color(255, 255, 255));
        loai_label.setHorizontalAlignment( SwingConstants.RIGHT);
        loai_label.setText("Loại");

        xuatxu_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        xuatxu_label.setForeground(new java.awt.Color(255, 255, 255));
        xuatxu_label.setHorizontalAlignment( SwingConstants.RIGHT);
        xuatxu_label.setText("Xuất xứ");

        tiennhap_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tiennhap_label.setForeground(new java.awt.Color(255, 255, 255));
        tiennhap_label.setHorizontalAlignment( SwingConstants.RIGHT);
        tiennhap_label.setText("Giá nhập kho");

        tienban_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tienban_label.setForeground(new java.awt.Color(255, 255, 255));
        tienban_label.setHorizontalAlignment( SwingConstants.RIGHT);
        tienban_label.setText("Giá bán");

        daban_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        daban_label.setForeground(new java.awt.Color(255, 255, 255));
        daban_label.setHorizontalAlignment( SwingConstants.RIGHT);
        daban_label.setText("Đã bán");

        dangban_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        dangban_label.setForeground(new java.awt.Color(255, 255, 255));
        dangban_label.setHorizontalAlignment( SwingConstants.RIGHT);
        dangban_label.setText("Đang bán");

        idSP_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        nameSP_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        nhaSX_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        giaNhap_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        giaBan_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        daBan_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        dangBan_text.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        
        //**** Đổ dữ liệu vào các JComboBox
        namSX_combobox.setModel(new  DefaultComboBoxModel<>(new String[] { "2021", "2022", "2023" }));

        loaiSP_combobox.setModel(new  DefaultComboBoxModel<>(new String[] { "CPU", "Bàn phím", "Chuột", "Thiết bị lưu trữ", "Card đồ họa" }));

        xuatxu_combobox.setModel(new  DefaultComboBoxModel<>(new String[] { "Việt Nam", "Trung Quốc", "Thái Lan", "Nhật Bản", "Singapore", "Anh", "Pháp", "Mỹ" }));

        tongSP_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        tongSP_label.setForeground(new java.awt.Color(255, 255, 255));
        tongSP_label.setHorizontalAlignment( SwingConstants.RIGHT);
        tongSP_label.setText("Tổng số sản phẩm");

        tongSP_text.setEditable(false);
        tongSP_text.setHorizontalAlignment( JTextField.CENTER);
        tongSP_text.setText("0");

        currency1_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        currency1_label.setForeground(new java.awt.Color(255, 255, 255));
        currency1_label.setHorizontalAlignment( SwingConstants.CENTER);
        currency1_label.setText("VNĐ");

        logoutButton.setBackground(new java.awt.Color(255, 204, 0));
        logoutButton.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        logoutButton.setText("Đăng xuất");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        currency2_label.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        currency2_label.setForeground(new java.awt.Color(255, 255, 255));
        currency2_label.setHorizontalAlignment( SwingConstants.CENTER);
        currency2_label.setText("VNĐ");

        search_button.setBackground(new java.awt.Color(255, 204, 0));
        search_button.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        search_button.setText("Tìm kiếm");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        searchYear_button.setBackground(new java.awt.Color(255, 102, 0));
        searchYear_button.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        searchYear_button.setForeground(new java.awt.Color(255, 255, 255));
        searchYear_button.setText("Tìm");
        searchYear_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchYear_buttonActionPerformed(evt);
            }
        });

        searchType_button.setBackground(new java.awt.Color(255, 102, 0));
        searchType_button.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        searchType_button.setForeground(new java.awt.Color(255, 255, 255));
        searchType_button.setText("Tìm");
        searchType_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchType_buttonActionPerformed(evt);
            }
        });

        searchOrigin_button.setBackground(new java.awt.Color(255, 102, 0));
        searchOrigin_button.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        searchOrigin_button.setForeground(new java.awt.Color(255, 255, 255));
        searchOrigin_button.setText("Tìm");
        searchOrigin_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchOrigin_buttonActionPerformed(evt);
            }
        });

        sort_button.setBackground(new java.awt.Color(255, 204, 0));
        sort_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        sort_button.setText("Sắp xếp dữ liệu");
        sort_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sort_buttonActionPerformed(evt);
            }
        });
        //2. Sử dựng GroupLayout để đặt các thành phần vào trong in4_panel
        GroupLayout in4_panelLayout = new  GroupLayout(in4_panel);
        in4_panel.setLayout(in4_panelLayout);
        in4_panelLayout.setHorizontalGroup(
            in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(in4_panelLayout.createSequentialGroup()
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(in4_label,  GroupLayout.PREFERRED_SIZE, 381,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(idSP_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(idSP_text,  GroupLayout.PREFERRED_SIZE, 230,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameSP_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nameSP_text,  GroupLayout.PREFERRED_SIZE, 230,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                            .addGroup(in4_panelLayout.createSequentialGroup()
                                .addComponent(sort_button)
                                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logoutButton,  GroupLayout.PREFERRED_SIZE, 122,  GroupLayout.PREFERRED_SIZE))
                            .addGroup(in4_panelLayout.createSequentialGroup()
                                .addComponent(tongSP_label)
                                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                                    .addGroup(in4_panelLayout.createSequentialGroup()
                                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tongSP_text,  GroupLayout.PREFERRED_SIZE, 64,  GroupLayout.PREFERRED_SIZE))
                                    .addGroup(in4_panelLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(search_button,  GroupLayout.PREFERRED_SIZE, 126,  GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(in4_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.TRAILING, false)
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(loai_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(loaiSP_combobox, 0,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(namSX_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(namSX_combobox, 0,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(nhaSX_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nhaSX_text,  GroupLayout.PREFERRED_SIZE, 230,  GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                            .addComponent(searchYear_button,  GroupLayout.PREFERRED_SIZE, 73,  GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchType_button,  GroupLayout.PREFERRED_SIZE, 73,  GroupLayout.PREFERRED_SIZE)))
                    .addGroup(in4_panelLayout.createSequentialGroup()
                        .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.TRAILING, false)
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(xuatxu_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(xuatxu_combobox, 0,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(dangban_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dangBan_text))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(daban_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(daBan_text))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(tienban_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(giaBan_text))
                            .addGroup( GroupLayout.Alignment.LEADING, in4_panelLayout.createSequentialGroup()
                                .addComponent(tiennhap_label,  GroupLayout.PREFERRED_SIZE, 100,  GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(giaNhap_text,  GroupLayout.PREFERRED_SIZE, 230,  GroupLayout.PREFERRED_SIZE)))
                        .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                            .addGroup(in4_panelLayout.createSequentialGroup()
                                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                                    .addComponent(currency1_label,  GroupLayout.PREFERRED_SIZE, 57,  GroupLayout.PREFERRED_SIZE)
                                    .addComponent(currency2_label,  GroupLayout.PREFERRED_SIZE, 57,  GroupLayout.PREFERRED_SIZE)))
                            .addGroup(in4_panelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(searchOrigin_button,  GroupLayout.PREFERRED_SIZE, 73,  GroupLayout.PREFERRED_SIZE))))))
        );
        
        in4_panelLayout.setVerticalGroup(
            in4_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(in4_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(in4_label,  GroupLayout.PREFERRED_SIZE, 30,  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(idSP_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(idSP_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(nameSP_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameSP_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(nhaSX_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(nhaSX_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(namSX_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(namSX_combobox,  GroupLayout.PREFERRED_SIZE, 30,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchYear_button))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(loai_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(loaiSP_combobox,  GroupLayout.PREFERRED_SIZE, 30,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchType_button))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.TRAILING)
                    .addComponent(xuatxu_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                        .addComponent(xuatxu_combobox,  GroupLayout.PREFERRED_SIZE, 30,  GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchOrigin_button)))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(tiennhap_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(giaNhap_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(currency1_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(tienban_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(giaBan_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(currency2_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(daban_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(daBan_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(dangban_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(dangBan_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_button,  GroupLayout.PREFERRED_SIZE, 37,  GroupLayout.PREFERRED_SIZE)
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(tongSP_label,  GroupLayout.PREFERRED_SIZE, 25,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(tongSP_text,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(in4_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton,  GroupLayout.PREFERRED_SIZE, 48,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(sort_button,  GroupLayout.PREFERRED_SIZE, 48,  GroupLayout.PREFERRED_SIZE))
                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
//TẠO JPANEL 3: CHỨC NĂNG CỦA ỨNG DỤNG
        //1. Thiết lập các thuộc tính cơ bản cho các JButton
        func_panel.setBackground(new java.awt.Color(0, 153, 51));
        func_panel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2), "Dữ liệu",  javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,  javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        add_button.setBackground(new java.awt.Color(255, 255, 255));
        add_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        add_button.setForeground(new java.awt.Color(0, 153, 0));
        add_button.setText("Thêm");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        update_button.setBackground(new java.awt.Color(255, 255, 255));
        update_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        update_button.setForeground(new java.awt.Color(0, 153, 0));
        update_button.setText("Cập nhật");
        update_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_buttonActionPerformed(evt);
            }
        });

        delete_button.setBackground(new java.awt.Color(255, 255, 255));
        delete_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        delete_button.setForeground(new java.awt.Color(0, 153, 0));
        delete_button.setText("Xóa");
        delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_buttonActionPerformed(evt);
            }
        });

        deleteAll_button.setBackground(new java.awt.Color(255, 255, 255));
        deleteAll_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        deleteAll_button.setForeground(new java.awt.Color(0, 153, 0));
        deleteAll_button.setText("Xóa hết dữ liệu");
        deleteAll_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAll_buttonActionPerformed(evt);
            }
        });

        reset_button.setBackground(new java.awt.Color(255, 255, 255));
        reset_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        reset_button.setForeground(new java.awt.Color(0, 153, 0));
        reset_button.setText("Làm mới dữ liệu");
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });

        showAll_button.setBackground(new java.awt.Color(255, 255, 255));
        showAll_button.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        showAll_button.setForeground(new java.awt.Color(0, 153, 0));
        showAll_button.setText("Toàn bộ sản phẩm");
        showAll_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAll_buttonActionPerformed(evt);
            }
        });
        
        //2. Sử dụng GroupLayout để đặt các thành phần vào trong func_panel
        
        GroupLayout func_panelLayout = new  GroupLayout(func_panel);
        func_panel.setLayout(func_panelLayout);
        func_panelLayout.setHorizontalGroup(
            func_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(func_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(func_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
                    .addComponent(add_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(showAll_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(func_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
                    .addComponent(deleteAll_button,  GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(update_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(func_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
                    .addComponent(reset_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        func_panelLayout.setVerticalGroup(
            func_panelLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(func_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(func_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(add_button)
                    .addComponent(update_button)
                    .addComponent(delete_button))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(func_panelLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteAll_button,  GroupLayout.PREFERRED_SIZE, 29,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(reset_button,  GroupLayout.PREFERRED_SIZE, 29,  GroupLayout.PREFERRED_SIZE)
                    .addComponent(showAll_button))
                .addContainerGap(16, Short.MAX_VALUE))
        );

//TẠO JSCROLLPANE: CHỨA BẢNG ĐỂ HIỂN THỊ DỮ LIỆU
        scrollpane_table.setForeground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        //1. Thiết lập tên cột, kiểu dữ liệu cột và các thuộc tính khác cho bảng
        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new  DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Nhà SX", "Năm SX", "Loại", "Xuất xứ", "Giá nhập kho", "Giá bán", "Đã bán", "Đang bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        //****Thêm bảng vào trong jScrollPane3 để có thể cuộn
        jScrollPane3.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(600);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(600);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(400);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(400);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(200);
        }
        //2. Bố trí bảng trong jPanel2
        GroupLayout jPanel2Layout = new  GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3,  GroupLayout.Alignment.TRAILING,  GroupLayout.DEFAULT_SIZE, 2029, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3,  GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        //Thêm jPanel2 vào trong scrollpane_table để có thể cuộn cả jPanel2
        scrollpane_table.setViewportView(jPanel2);

//TẠO JPANEL 3 : CHỨC NĂNG HIỂN THỊ BIỂU ĐỒ
        //1. Thiết lập các thuộc tính cho các nút
        graphics.setBackground(new java.awt.Color(0, 153, 51));
        graphics.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2), "Thống kê",  javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,  javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        typeSP_button.setBackground(new java.awt.Color(255, 255, 255));
        typeSP_button.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        typeSP_button.setForeground(new java.awt.Color(0, 153, 0));
        typeSP_button.setText("Loại sản phẩm");
        typeSP_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeSP_buttonActionPerformed(evt);
            }
        });

        revenueStore_button.setBackground(new java.awt.Color(255, 255, 255));
        revenueStore_button.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        revenueStore_button.setForeground(new java.awt.Color(0, 153, 0));
        revenueStore_button.setText("Doanh thu cửa hàng qua các năm");
        revenueStore_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueStore_buttonActionPerformed(evt);
            }
        });

        revenueType_button.setBackground(new java.awt.Color(255, 255, 255));
        revenueType_button.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        revenueType_button.setForeground(new java.awt.Color(0, 153, 0));
        revenueType_button.setText("Doanh thu phân loại");
        revenueType_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueType_buttonActionPerformed(evt);
            }
        });
        
        //2. Đặt các thành phần vào trong JPanel graphics
        GroupLayout graphicsLayout = new  GroupLayout(graphics);
        graphics.setLayout(graphicsLayout);
        graphicsLayout.setHorizontalGroup(
            graphicsLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(graphicsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(graphicsLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addComponent(revenueStore_button,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(graphicsLayout.createSequentialGroup()
                        .addComponent(typeSP_button,  GroupLayout.PREFERRED_SIZE, 166,  GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(revenueType_button))))
        );
        graphicsLayout.setVerticalGroup(
            graphicsLayout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(graphicsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(graphicsLayout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(typeSP_button)
                    .addComponent(revenueType_button))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(revenueStore_button)
                .addContainerGap( GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

//ĐẶT CÁC CONTAINER TRÊN VÀO TRONG MỘT JFRAME ĐỂ HOÀN THÀNH GIAO DIỆN
        GroupLayout layout = new  GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addComponent(title_panel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(in4_panel,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpane_table,  GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(func_panel,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(graphics,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(title_panel,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollpane_table,  GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING, false)
                            .addComponent(func_panel,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE)
                            .addComponent(graphics,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(in4_panel,  GroupLayout.DEFAULT_SIZE,  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }                    

//PART 3: XỬ LÍ CÁC SỰ KIỆN
    
    //1. Sự kiện ẩn giao diện QLSP
    private void hide_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        setState(GUI_QLSP.ICONIFIED);
    }                                           
    
    //2. Sự kiện đóng giao diện QLSP   
    private void close_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int ans = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn đóng ứng dụng không ?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if ( ans == 0 ){
            System.exit(0);
        }  
    }                                            

    //3. Sự kiện đăng xuất khỏi ứng dụng
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int ans = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn đăng xuất khỏi ứng dụng không ?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if ( ans == 0 ){ 
            this.setVisible(false);
            new GUILogin().setVisible(true);
        }
    }                                            
    //4. Sự kiện thêm sản phẩm vào bảng và CSDL
    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        //Kiểm tra đã điền đầy đủ thông tin sản phẩm chưa
        if (idSP_text.getText().equals("") ||nameSP_text.getText().equals("") || 
            nhaSX_text.getText().equals("") ||giaNhap_text.getText().equals("") ||
            giaBan_text.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Chưa nhập đầy đủ thông tin của sản phẩm");
            if ( daBan_text.getText().equals("") ) daBan_text.setText("0");
            if ( dangBan_text.getText().equals("")) dangBan_text.setText("0");
        } 
        else 
            //Nếu nhập thông tin đầy đủ rồi thì tiến hành thêm dữ liệu
            if (addProduct()) {
                JOptionPane.showMessageDialog(null, "Success");
                model.addRow(new Object[]{idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,GNK,GB,dangban,daban});
                tongSP_text.setText(String.valueOf(jTable1.getRowCount()));
            }else 
                JOptionPane.showMessageDialog(null, "Mã sản phẩm đã tồn tại hoặc quá dài ( tối đa 10 kí tự )", "Lỗi", JOptionPane.ERROR_MESSAGE);             
    }                                          
    //6. Sự kiện xóa một sản phẩm ra khỏi bảng và CSDL
    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        deleteProduct();
        tongSP_text.setText(String.valueOf(jTable1.getRowCount()));
    }                                             
    //7. Sự kiện nhấp chuột vào một dòng và hiển thị thông tin
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        idSP_text.setText(model.getValueAt(row, 0).toString());
        nameSP_text.setText(model.getValueAt(row, 1).toString());
        nhaSX_text.setText(model.getValueAt(row, 2).toString());
        
        //Lấy giá trị từ bảng hiển thị sang ComboBox
        String combo_namSX = model.getValueAt(row, 3).toString();
        for (int i = 0; i < namSX_combobox.getItemCount(); i++)
            if ( namSX_combobox.getItemAt(i).toString().equalsIgnoreCase(combo_namSX))
                namSX_combobox.setSelectedIndex(i);
        
        String combo_loaiSP = model.getValueAt(row, 4).toString();
        for (int i = 0; i < loaiSP_combobox.getItemCount(); i++)
            if ( loaiSP_combobox.getItemAt(i).toString().equalsIgnoreCase(combo_loaiSP))
                loaiSP_combobox.setSelectedIndex(i);
        
        String combo_xuatxu = model.getValueAt(row, 5).toString();
        for (int i = 0; i < xuatxu_combobox.getItemCount(); i++)
            if ( xuatxu_combobox.getItemAt(i).toString().equalsIgnoreCase(combo_xuatxu))
                xuatxu_combobox.setSelectedIndex(i);
        
        giaNhap_text.setText(model.getValueAt(row, 6).toString());
        giaBan_text.setText(model.getValueAt(row, 7).toString());
        daBan_text.setText(model.getValueAt(row, 8).toString());
        dangBan_text.setText(model.getValueAt(row, 9).toString());
        
    }                                    

    //8. Sự kiện xóa hết dữ liệu trong cơ sở dữ liệu và trong bảng
    private void deleteAll_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        int ans = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xóa hết sản phẩm không ?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if ( ans == 0 ){
            deleteAll();
            tongSP_text.setText(String.valueOf(jTable1.getRowCount()));
        }
    }                                                
    //9. Sự kiện làm mới dữ liệu trên giao diện nhưng không xóa dữ liệu trong cơ sở dữ liệu
    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        resetData();
    }                                            

    //10. Sự kiện hiển thị biểu đồ đường doanh thu cửa hàng qua các năm
    private void revenueStore_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        getRevenueYear();
    }                                                   
    //11. Sự kiện ấn phím mũi tên hiển thị thông tin sản phẩm
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {                                   

    }                                  
    //12. Sự kiện hiển thị biểu đồ tròn cho Loại sản phẩm
    private void typeSP_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        getTypeProduct();
    }                                             
    //13. Sự kiện hiển thị biểu đồ cột cho doanh thu của cửa hàng theo phân loại sản phẩm
    private void revenueType_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        getMoneyTypeProduct();
    }                                                  
    //14. Sự kiện cập nhật thông tin sản phẩm
    private void update_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        //Kiểm tra xem có dòng nào được chọn chưa
        if ( jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Không có dữ liệu để cập nhật","Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Hộp thoại xác nhận cập nhật thông tin
        int ans = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn thay đổi thông tin sản phẩm ?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if ( ans == 0 ){
            if (updateInformation()){
                JOptionPane.showMessageDialog(null, "Thông tin sản phẩm đã được thay đổi","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                model = (DefaultTableModel) jTable1.getModel();
                int row = jTable1.getSelectedRow();
                model.setValueAt(idSP_text.getText(), row, 0);
                model.setValueAt(nameSP_text.getText(), row, 1);
                model.setValueAt(nhaSX_text.getText(), row, 2);
                model.setValueAt(Integer.valueOf(namSX_combobox.getSelectedItem().toString()), row, 3);
                model.setValueAt(loaiSP_combobox.getSelectedItem().toString(), row, 4);
                model.setValueAt(xuatxu_combobox.getSelectedItem().toString(), row, 5);
                model.setValueAt(giaNhap_text.getText(), row, 6);
                model.setValueAt(giaBan_text.getText(), row, 7);
                model.setValueAt(daBan_text.getText(), row, 8);
                model.setValueAt(dangBan_text.getText(), row, 9);
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin không thành công","Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                             

    private void showAll_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        showAll();
        tongSP_text.setText(String.valueOf(model.getRowCount()));
    }                                              
    //15. Sự kiện tìm kiếm sản phẩm bị ràng buộc bởi ba điều kiện
    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        //Hiển thị giới hạn tìm kiếm
        if (giaNhap_text.getText().equals("")==false || giaBan_text.getText().equals("")==false || 
            idSP_text.getText().equals("")==false || nameSP_text.getText().equals("")==false ||
            nhaSX_text.getText().equals("")==false || daBan_text.getText().equals("")== false ||
            dangBan_text.getText().equals("")== false ||
           
           (giaNhap_text.getText().equals("")==false && giaBan_text.getText().equals("")==false && 
            idSP_text.getText().equals("")==false && nameSP_text.getText().equals("")==false &&
            nhaSX_text.getText().equals("")==false && daBan_text.getText().equals("")== false &&
            dangBan_text.getText().equals("")== false))
            JOptionPane.showMessageDialog(rootPane, "Chúng tôi không hỗ trợ tính năng này\nChúng tôi chỉ hỗ trợ tìm kiếm : Năm sản xuất, loại sản phẩm và xuất xứ\nXin lỗi vì sự bất tiện này","Thông báo", JOptionPane.INFORMATION_MESSAGE);
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        searchInfor(Integer.parseInt(namSX_combobox.getSelectedItem().toString()), loaiSP_combobox.getSelectedItem().toString(), xuatxu_combobox.getSelectedItem().toString());       
        if (jTable1.getRowCount() == 0 )
            JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sản phẩm", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }                                             
    //16. Sự kiện tìm kiếm năm sản xuất của các sản phẩm
    private void searchYear_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban from Information where namSX = " + Integer.parseInt(namSX_combobox.getSelectedItem().toString()));
            model = (DefaultTableModel) jTable1.getModel();
            while (rs.next()){
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (jTable1.getRowCount() == 0 )
            JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sản phẩm", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }                                                 
    //17. Sự kiện tìm kiếm loại sản phẩm
    private void searchType_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban from Information where loaiSP = N'" +loaiSP_combobox.getSelectedItem().toString() +"'" );
            model = (DefaultTableModel) jTable1.getModel();
            while (rs.next()){
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (jTable1.getRowCount() == 0 )
            JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sản phẩm", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }                                                 
    //18. Sự kiện tìm kiếm nguồn gốc của các loại sản phẩm
    private void searchOrigin_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        jTable1.setModel(new DefaultTableModel(null, new String[]{"Mã sản phẩm","Tên sản phẩm","Nhà SX","Năm SX","Loại","Xuất xứ","Giá nhập kho","Giá bán","Đã bán","Đang bán"}));
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(URL);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select ID,nameSP,nhaSX,namSX,loaiSP,xuatxu,gianhapkho,giaban,daban,dangban from Information where xuatxu = N'"+xuatxu_combobox.getSelectedItem().toString()+"'" );
            model = (DefaultTableModel) jTable1.getModel();
            while (rs.next()){
                idSP = rs.getString("ID");
                nameSP = rs.getString("nameSP");
                nhaSX = rs.getString("nhaSX");
                namSX = String.valueOf(rs.getInt("namSX"));
                loaiSP = rs.getString("loaiSP");
                xuatxu = rs.getString("xuatxu");
                gianhapkho = String.valueOf(rs.getInt("gianhapkho"));
                giaban = String.valueOf(rs.getInt("giaban"));
                daban = String.valueOf(rs.getInt("daban"));
                dangban = String.valueOf(rs.getInt("dangban"));
                //Thêm dấu chấm vào trong giá tiền trước khi xuất ra bảng dữ liệu
                StringBuilder str1 = new StringBuilder(gianhapkho);
                str1 = addDot(gianhapkho);
                StringBuilder str2 = new StringBuilder(giaban);
                str2 = addDot(giaban);
                //Đưa dữ liệu từ CSDL vào trong Table              
                Object []row = {idSP,nameSP,nhaSX,namSX,loaiSP,xuatxu,str1,str2,daban,dangban};
                model.addRow(row);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (jTable1.getRowCount() == 0 )
            JOptionPane.showMessageDialog(rootPane, "Không tìm thấy sản phẩm", "Thông báo",JOptionPane.INFORMATION_MESSAGE);
    }                                                   
    //19. Sự kiện hiển thị lựa chọn để sắp xếp dữ liệu
    private void sort_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        //Tạo lựa chọn cho thuộc tính sản phẩm và cách sắp xếp
        String properties[] = {"Năm sản xuất","Giá nhập kho","Giá bán","Đã bán","Đang bán"};
        String type[] = {"Giảm dần","Tăng dần"};
        
        JComboBox cb1 = new JComboBox(properties);
        JComboBox cb2 = new JComboBox(type);
        //Thực hiện lựa chọn
        String prop;
        String tpe;
        
        int input1 = JOptionPane.showConfirmDialog(this, cb1, "Chọn thuộc tính sản phẩm", JOptionPane.DEFAULT_OPTION);
        int input2 = JOptionPane.showConfirmDialog(this, cb2, "Chọn cách sắp xếp", JOptionPane.DEFAULT_OPTION);
        
        if (input1 == JOptionPane.OK_OPTION && input2 == JOptionPane.OK_OPTION){
            //Lấy lựa chọn trong jCombobox
            prop = cb1.getSelectedItem().toString();
            tpe = cb2.getSelectedItem().toString();
            //Thực hiện sắp xếp
            getData(prop, tpe);
        }
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for ( UIManager.LookAndFeelInfo info :  UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                     UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_QLSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_QLSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_QLSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch ( UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_QLSP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        new GUI_QLSP().setVisible(true);
    }

    // Variables declaration - do not modify                     
        private JPanel title_panel = new  JPanel();
        private JLabel title_label = new  JLabel();
        private JButton hide_button = new  JButton();
        private JButton  close_button = new  JButton();
        private JPanel  in4_panel = new  JPanel();
        private JLabel  in4_label = new  JLabel();
        private JLabel  idSP_label = new  JLabel();
        private JLabel  nameSP_label = new  JLabel();
        private JLabel  nhaSX_label = new  JLabel();
        private JLabel  namSX_label = new  JLabel();
        private JLabel  loai_label = new  JLabel();
        private JLabel  xuatxu_label = new  JLabel();
        private JLabel  tiennhap_label = new  JLabel();
        private JLabel  tienban_label = new  JLabel();
        private JLabel  daban_label = new  JLabel();
        private JLabel  dangban_label = new  JLabel();
        private JTextField idSP_text = new  JTextField();
        private JTextField  nameSP_text = new  JTextField();
        private JTextField  nhaSX_text = new  JTextField();
        private JTextField  giaNhap_text = new  JTextField();
        private JTextField  giaBan_text = new  JTextField();
        private JTextField  daBan_text = new  JTextField();
        private JTextField  dangBan_text = new  JTextField();
        private JComboBox namSX_combobox = new  JComboBox<>();
        private JComboBox  loaiSP_combobox = new  JComboBox<>();
        private JComboBox  xuatxu_combobox = new  JComboBox<>();
        private JLabel  tongSP_label = new  JLabel();
        private JTextField  tongSP_text = new  JTextField();
        private JLabel  currency1_label = new  JLabel();
        private JButton  logoutButton = new  JButton();
        private JLabel  currency2_label = new  JLabel();
        private JButton  search_button = new  JButton();
        private JButton  searchYear_button = new  JButton();
        private JButton  searchType_button = new  JButton();
        private JButton  searchOrigin_button = new  JButton();
        private JButton  sort_button = new  JButton();
        private JPanel func_panel = new  JPanel();
        private JButton  add_button = new  JButton();
        private JButton  update_button = new  JButton();
        private JButton  delete_button = new  JButton();
        private JButton  deleteAll_button = new  JButton();
        private JButton  reset_button = new  JButton();
        private JButton  showAll_button = new  JButton();
        private JScrollPane scrollpane_table = new  JScrollPane();
        private JPanel  jPanel2 = new  JPanel();
        private JScrollPane jScrollPane3 = new  JScrollPane();
        private JTable jTable1 = new  JTable();
        private JPanel graphics = new  JPanel();
        private JButton  typeSP_button = new  JButton();
        private JButton  revenueStore_button = new  JButton();
        private JButton  revenueType_button = new  JButton();
    // End of variables declaration                   
}
