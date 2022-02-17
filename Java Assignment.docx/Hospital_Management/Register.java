package Hospital_Management;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class Register extends JFrame implements ActionListener {
    JLabel titlelbl;
    JLabel usernamelbl;
    JLabel rolelbl;
    JLabel addresslbl;
    JLabel phonelbl;
    JLabel emaillbl;
    JLabel passwordlbl;
    JLabel cpasswordlbl;
    
    JTextField txtname;
    JComboBox roletxt;
    JTextField txtaddress;
    JTextField txtphone;
    JTextField txtemail;
    JPasswordField txtpassword;
    JPasswordField txtcpassword;
    
    JButton registerbtn;
    JButton loginbtn;
    JButton clearbtn;
    
    Connection conn = null;
    
    Register() {
             setTitle("Register");
             setLayout(null);

            titlelbl=new JLabel("Register");
            usernamelbl=new JLabel("Username:");
            rolelbl=new JLabel("Role:");
            addresslbl=new JLabel("Address:");
            phonelbl=new JLabel("Phone:");
            emaillbl=new JLabel("Email:");
            passwordlbl=new JLabel("Password:");
            cpasswordlbl=new JLabel("Confirm Password:");
            
            txtname=new JTextField(10);
            String role[]={"Admin","Doctor","Nurse","LabAdmin"}; 
            roletxt = new JComboBox(role);
            txtaddress=new JTextField(10);
            txtphone=new JTextField(10);
            txtemail=new JTextField(10);
            txtpassword=new JPasswordField(10);
            txtcpassword=new JPasswordField(10);
            
            registerbtn=new JButton("Register");
            loginbtn=new JButton("Back to Login");
            clearbtn=new JButton("Clear");
            
            registerbtn.addActionListener(this);
            loginbtn.addActionListener(this);
            clearbtn.addActionListener(this);
            
             titlelbl.setBounds(110, 15, 100, 30);
             titlelbl.setFont(new Font("Serif", Font.BOLD, 25));
            
             usernamelbl.setBounds(50, 50, 75, 25);
             rolelbl.setBounds(50, 100, 75, 25);
             addresslbl.setBounds(50, 150, 75, 25);
             phonelbl.setBounds(50, 200, 75, 25);
             emaillbl.setBounds(50, 250, 75, 25);
             passwordlbl.setBounds(50, 300, 75, 25);
             cpasswordlbl.setBounds(50, 350, 75, 25);
             
             txtname.setBounds(170, 50, 200, 25);
             roletxt.setBounds(170, 100, 200, 25);
             txtaddress.setBounds(170, 150, 200, 25);
             txtphone.setBounds(170, 200, 200, 25);
             txtemail.setBounds(170, 250, 200, 25);
             txtpassword.setBounds(170, 300, 200, 25);
             txtcpassword.setBounds(170, 350, 200, 25);
             
             registerbtn.setBounds(110, 400, 200, 25);
             clearbtn.setBounds(110, 450, 200, 25);
             loginbtn.setBounds(110, 500, 200, 25);
             
             add(titlelbl);
             add(usernamelbl);
             add(txtname);
             
             add(rolelbl);
             add(roletxt);
             
             add(addresslbl);
             add(txtaddress);
             add(phonelbl);
             add(txtphone);
             add(emaillbl);
             add(txtemail);
             add(passwordlbl);
             add(txtpassword);

             add(cpasswordlbl);
             add(txtcpassword);
             
             add(registerbtn);
             add(loginbtn);
             add(clearbtn);
            
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             setSize(420,620);
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
    
//    public static void main(String[] args) {
//        Register f = new Register();
//    }
 
    public void reset() {
        txtname.setText("");
        txtaddress.setText("");
        txtphone.setText("");
        txtemail.setText("");
        txtpassword.setText("");
        txtcpassword.setText("");
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        
        if(source==registerbtn) {
            String name=txtname.getText();
            String role=roletxt.getItemAt(roletxt.getSelectedIndex()).toString();
            String address=txtaddress.getText();
            String phone=txtphone.getText();
            String email=txtemail.getText();
            String password=txtpassword.getText();
            String cpassword=txtcpassword.getText();
            
            if (password.equals(cpassword) && name.isEmpty() == false && address.isEmpty() == false && phone.isEmpty()==false && email.isEmpty()==false){
            
                String sql="insert into register(name,role,address,phone,email,password) values(?,?,?,?,?,?)";
                try {
                PreparedStatement stmt=conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, role);
                stmt.setString(3, address);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, password);
                int ins=stmt.executeUpdate();
                if(ins>0) {
                JOptionPane.showMessageDialog(null, "Registration successful");
                reset();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Register Failed");
                }
                }
                catch(Exception ee) {ee.printStackTrace();}    
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Register Failed");
            }                                
        }
        else if(source==clearbtn) {
            reset();
        }
        else if(source==loginbtn) {
            Login f = new Login();
            dispose();
        }

    }
}