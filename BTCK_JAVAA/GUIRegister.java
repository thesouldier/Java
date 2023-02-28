package GUILogin_And_Register;
/**
 *
 * @author Nguyen Viet Anh Quyen
 */
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
public class GUIRegister extends javax.swing.JFrame {
    ArrayList<Account> list = new ArrayList<Account>();
    //Phương thức kiểm tra mật khẩu có thỏa mãn yêu cầu không: ít nhất 1 hoa đứng đầu, 1 đặc biệt, tối thiểu 8 kí tự
    public boolean checkPass(String pass){
        String pan_pass = "[A-Z][A-Za-z0-9!@#$%^&*]{8,}";
        if (pass.matches(pan_pass) == true ) return true;
        else return false;
    }
    //Phương thức kiểm tra số điện thoại có hợp lệ không
    public boolean checkPhone(String numberPhone){
        String pan_numberPhone = "0[9|8|7|1|3|5]([0-9]){8,8}";
        if (numberPhone.matches(pan_numberPhone) == true) return true;
        else return false;
    }
    //Phương thức kiểm tra email có đúng theo yêu cầu không
    public boolean checkEmail(String email){
        String pan_email = "[a-zA-Z][a-zA-Z0-9]+(@gmail.com)";
        if ( email.matches(email_text.getText()) == true ) return true;
        else return  false;
    }
    //Truy cập cơ sở dữ liệu để kiểm tra xem tài khoản vừa đăng kí có tồn tại không
    public boolean checkUser() {
    	Connection conn;
    	String sql = "select*from Signin";
    	try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Accounts;user=sa;password=quyen230504");
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
            	Account acc = new Account();
            	acc.setFullname(rs.getString("Fullname"));
            	acc.setUsername(rs.getString("Username"));
            	acc.setPassword(rs.getString("Password"));
            	acc.setNumberphone(rs.getString("NumberPhone"));
            	acc.setEmail(rs.getString("Email"));
            	list.add(acc);
            }
            
    	} catch (Exception e1){
            e1.printStackTrace();
        }
    	for (int i = 0 ; i < list.size(); i++) {
    		if ( username_text.getText().equals(list.get(i).getUsername())) {
    			return true;
    		}
    	}
    	//Nếu chưa tồn tại tài khoản thì thêm tài khoản vào cơ sở dữ liệu
    	Account acc = new Account();
		acc.setFullname(fullname_text.getText());
		acc.setUsername(username_text.getText());
		acc.setPassword(jPasswordField1.getText());
		acc.setNumberphone(phone_text.getText());
		acc.setEmail(email_text.getText());
		list.add(acc);
		sql = "insert into Signin(Fullname,Username,Password,NumberPhone,Email) values (?,?,?,?,?)";
        try {
        	conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Accounts;user=sa;password=quyen230504");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,acc.getFullname());
            ps.setString(2,acc.getUsername());
            ps.setString(3,acc.getPassword());
            ps.setString(4,acc.getNumberphone());
            ps.setString(5,acc.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return false;
    }
    public GUIRegister() {
        initComponents();
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        hideLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        fullname = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        confirmpass = new javax.swing.JLabel();
        phone = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        fullname_text = new javax.swing.JTextField();
        username_text = new javax.swing.JTextField();
        phone_text = new javax.swing.JTextField();
        email_text = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        signupButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đăng kí");

        closeLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        closeLabel.setForeground(new java.awt.Color(255, 255, 255));
        closeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeLabel.setText("X");
        closeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });

        hideLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        hideLabel.setForeground(new java.awt.Color(255, 255, 255));
        hideLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hideLabel.setText("-");
        hideLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        hideLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hideLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(closeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(hideLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        fullname.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        fullname.setForeground(new java.awt.Color(255, 255, 255));
        fullname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fullname.setText("Họ và tên");

        username.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        username.setText("Tên đăng nhập");

        password.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        password.setText("Mật khẩu");

        confirmpass.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        confirmpass.setForeground(new java.awt.Color(255, 255, 255));
        confirmpass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        confirmpass.setText("Xác nhận mật khẩu");

        phone.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        phone.setForeground(new java.awt.Color(255, 255, 255));
        phone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        phone.setText("SĐT");

        email.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        email.setText("Email");

        fullname_text.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N

        username_text.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N

        phone_text.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N

        email_text.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        email_text.setText("@gmail.com");

        signupButton.setBackground(new java.awt.Color(51, 51, 51));
        signupButton.setForeground(new java.awt.Color(255, 255, 255));
        signupButton.setText("Đăng kí");
        signupButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(51, 51, 51));
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setText("Hủy");
        cancelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        cancelButton.setPreferredSize(new java.awt.Dimension(88, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(email_text, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(phone_text, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(confirmpass, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPasswordField2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPasswordField1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(username_text, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fullname_text, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(signupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullname_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(signupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>                        
    
    //Sự kiện ẩn giao diện đăng kí
    private void hideLabelMouseClicked(java.awt.event.MouseEvent evt) {                                       
        setState(GUIRegister.ICONIFIED);
    }                                      
    //Sự kiện đóng cửa sổ giao diện đăng kí
    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {                                        
        System.exit(0);
    }                                       
    //Sự kiện hủy đăng kí và quay lại giao diện đăng nhập
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        this.setVisible(false);
        new GUILogin().setVisible(true);
    }                                            
    //Sự kiện đăng kí tài khoản
    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        //Kiểm tra xem có ô nào bị bỏ trống không
        if (fullname_text.getText().equals("") || username_text.getText().equals("") ||
                jPasswordField1.getText().equals("") || jPasswordField2.getText().equals("") ||
                phone_text.getText().equals("") || email_text.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane,"Không được bỏ trống tất cả các ô thông tin" ,"Lỗi",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Kiểm tra xem mật khẩu có hợp lệ không
        if (checkPass(jPasswordField1.getText()) == false) {
                JOptionPane.showMessageDialog(rootPane, "Mật khẩu không hợp lệ!!!\n Mật khẩu gồm : ít nhất có 1 chữ cái hoa đầu dòng, ít nhất có 1 kí tự đặc biệt, ít nhất có 1 chữ số và tối thiểu 8 kí tự","Lỗi",JOptionPane.ERROR_MESSAGE);
                jPasswordField1.setText("");
                jPasswordField2.setText("");
                return;
        }
        //Kiểm tra xem xác nhận lại mật khẩu có đúng không 
        if (jPasswordField2.getText().equals(jPasswordField1.getText()) == false) {
                JOptionPane.showMessageDialog(rootPane, "Mật khẩu không trùng khớp","Lỗi",JOptionPane.ERROR_MESSAGE);
                return;
        }
        //Kiểm tra xem số điện thoại có hợp lệ không
        if (checkPhone(phone_text.getText()) == false) {
                JOptionPane.showMessageDialog(rootPane, "Số điện thoại không hợp lệ","Lỗi",JOptionPane.ERROR_MESSAGE);
                phone_text.setText("");
                return;
        }
        //Kiểm tra xem email có hợp lệ không
        if (checkEmail(email_text.getText()) == false) {
                JOptionPane.showMessageDialog(rootPane, "Email không hợp lệ","Lỗi",JOptionPane.ERROR_MESSAGE);
                email_text.setText("@gmail.com");
                return;
        }
        //Kiểm tra xem có bị trùng tài khoản không
        if ( checkUser() == false ) {
                JOptionPane.showMessageDialog(rootPane,"Đăng kí thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                new GUILogin().setVisible(true);
        } else {
               JOptionPane.showMessageDialog(rootPane,"Tài khoản đã tồn tại","Lỗi",JOptionPane.ERROR_MESSAGE);
               return;
        }
    }                                            

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel confirmpass;
    private javax.swing.JLabel email;
    private javax.swing.JTextField email_text;
    private javax.swing.JLabel fullname;
    private javax.swing.JTextField fullname_text;
    private javax.swing.JLabel hideLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JLabel password;
    private javax.swing.JLabel phone;
    private javax.swing.JTextField phone_text;
    private javax.swing.JButton signupButton;
    private javax.swing.JLabel username;
    private javax.swing.JTextField username_text;
    // End of variables declaration                   
}
