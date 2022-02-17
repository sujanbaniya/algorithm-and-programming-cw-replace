package Hospital_Management;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	 JLabel titlelbl;
	 JLabel usernamelbl;
	 JLabel rolelbl;
	 JLabel passwordlbl;
	   
	 JTextField txtname;
	 JComboBox roletxt;
	 JPasswordField txtpassword;
	   
	 JButton loginbtn;
	 JButton clearbtn;
	 JButton registerbtn;
   
   Connection conn = null;
   
   Login() {
   	setTitle("Login");
       setLayout(null);

       titlelbl=new JLabel("Login");
       usernamelbl=new JLabel("Username:");
       rolelbl=new JLabel("Role:");
       passwordlbl=new JLabel("Password:");
       
       txtname=new JTextField(10);
       String role[]={"Admin","Doctor","Nurse","LabAdmin"}; 
       roletxt = new JComboBox(role);
       txtpassword=new JPasswordField(10);
       
       loginbtn=new JButton("Login");
       clearbtn=new JButton("Clear");
       registerbtn=new JButton("Register");
       
       loginbtn.addActionListener(this);
       clearbtn.addActionListener(this);
       registerbtn.addActionListener(this);
       
       titlelbl = new JLabel("Login");
       titlelbl.setBounds(150, 40, 400, 30);
       titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
      
       usernamelbl.setBounds(50, 150, 75, 25);
       rolelbl.setBounds(50, 100, 75, 25);
       passwordlbl.setBounds(50, 200, 75, 25);
       
       txtname.setBounds(170, 150, 200, 25);
       roletxt.setBounds(170, 100, 200, 25);
       txtpassword.setBounds(170, 200, 200, 25);
       
       clearbtn.setBounds(150, 250, 80, 25);
       loginbtn.setBounds(50, 250, 80, 25);
       registerbtn.setBounds(250, 250, 85, 25);
       
       add(titlelbl);
       add(usernamelbl);
       add(txtname);
       
       add(rolelbl);
       add(roletxt);
       
       add(passwordlbl);
       add(txtpassword);
       
       add(loginbtn);
       add(clearbtn);
       add(registerbtn);
       
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(420,480);
       setVisible(true); 
            
            //connection start
            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/java_hospital","root","messi7");

            } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
            //connection end
   }
   
   public static void main(String[] args) {
       Login f = new Login();
   }

   public void reset() {
       txtname.setText("");
       txtpassword.setText("");
       }
   
   @Override
   public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();
       
       if(source==loginbtn) {
           String name=txtname.getText();
           String role=roletxt.getItemAt(roletxt.getSelectedIndex()).toString();
           String password=txtpassword.getText();
           
           String sql = "select id from register where name=? and role=? and password=?";
           
           try {
               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, name);
               stmt.setString(2, role);
               stmt.setString(3, password);
                  			
               ResultSet rs = stmt.executeQuery();
//               if  (!rs.next()){
//            	   JOptionPane.showMessageDialog(null, "Incorrect username or password");}
               while (rs.next()) {
            	  
                   String id = rs.getString("id");
                   JOptionPane.showMessageDialog(null, "Login successful");
//                   System.out.println("!!!!! Login Success !!!!!");
                   if (role.equals("Admin")) {
                       AdminDashboard f = new AdminDashboard();
                       dispose();
                   }else if (role.equals("Doctor")) {
                   	DoctorDashboard f = new DoctorDashboard(name);
                       dispose();
                   }else if (role.equals("Nurse")) {
                   	NurseDashboard f = new NurseDashboard(name);
                       dispose();
                   }else if (role.equals("LabAdmin")) {
                   	LabAdminDashboard f = new LabAdminDashboard();
                       dispose();
                   }
                   reset();
               
               }
              
           } catch (Exception ee) {
               ee.printStackTrace();
               
           }
           



       } else if (source == clearbtn) {
           reset();
       } else if (source == registerbtn) {
           Register register = new Register();
           dispose();
       }



   }
}