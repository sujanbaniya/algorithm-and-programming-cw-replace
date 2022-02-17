package Hospital_Management;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class  AddPatient extends JFrame implements ActionListener {
    JLabel titlelbl;
    JLabel usernamelbl;
    JLabel addresslbl;
    JLabel doblbl;
    JLabel genderlbl;
    JLabel descriptionlbl;
    JLabel datelbl;
    JLabel doctorname;
    JLabel phonelbl;
    
    JTextField txtname;
    JComboBox txtdoctorname;
    JTextField txtaddress;
    JTextField txtdob;
    JComboBox txtgender;
    JTextField txtdescription;
    JTextField txtphone;
    JFormattedTextField txtdate;
    
    
    JButton backbtn;
    JButton addbtn;
    JButton clearbtn;
    
    Connection conn = null;
    
    AddPatient() {
             setTitle("Add Patient");
             setLayout(null);

            titlelbl=new JLabel("Add Patient");
            usernamelbl=new JLabel("Name:");
            addresslbl=new JLabel("Address:");
            phonelbl=new JLabel("Phone");
            doblbl=new JLabel("DOB:");
            doctorname=new JLabel("Doctor Name:");
            genderlbl=new JLabel("Gender:");
            descriptionlbl=new JLabel("Description:");
            
            txtname=new JTextField(10);
            txtaddress=new JTextField(10);
            txtphone=new JTextField(10);
            txtdob=new JTextField(10);
            txtdoctorname = new JComboBox();
            String gender[]={"Male","Female","Others"}; 
            txtgender=new JComboBox(gender);
            txtdescription=new JTextField(10);
            
            DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
            txtdate = new JFormattedTextField(dateFormat);
            datelbl = new JLabel("Date:");
            datelbl.setLabelFor(txtdate);
            txtdate.setValue(new Date());
            
            backbtn=new JButton("Back");
            addbtn=new JButton("Add");
            clearbtn=new JButton("Clear");
            
            backbtn.addActionListener(this);
            addbtn.addActionListener(this);
            clearbtn.addActionListener(this);
            
             titlelbl.setBounds(110, 15, 170, 30);
             titlelbl.setFont(new Font("Serif", Font.BOLD, 25));
            
             usernamelbl.setBounds(50, 50, 75, 25);
             addresslbl.setBounds(50, 100, 75, 25);
             phonelbl.setBounds(50, 150, 75, 25);
             doblbl.setBounds(50, 200, 100, 25);
             doctorname.setBounds(50, 250, 75, 25);
             genderlbl.setBounds(50,300,75,25);
             descriptionlbl.setBounds(50,350,75,25);
             datelbl.setBounds(50,400,75,25);
             
             txtname.setBounds(170, 50, 200, 25);
             txtaddress.setBounds(170, 100, 200, 25);
             txtphone.setBounds(170, 150, 200, 25);
             txtdob.setBounds(170, 200, 200, 25);
             txtdoctorname.setBounds(170, 250, 200, 25);
             txtgender.setBounds(170, 300, 200, 25);
             txtdescription.setBounds(170, 350, 200, 25);
             txtdate.setBounds(170, 400, 200, 25);
//             txtdate.setEditable(false);
             
//             today.setBounds();
             
             backbtn.setBounds(110, 450, 200, 25);
             clearbtn.setBounds(110, 500, 200, 25);
             addbtn.setBounds(110, 550, 200, 25);
             
             add(titlelbl);
             add(usernamelbl);
             add(txtname);
             
             add(addresslbl);
             add(phonelbl);
             add(txtaddress);
             add(doblbl);
             add(doctorname);
             add(txtdob);
             add(txtphone);
             add(genderlbl);
             add(txtgender);
             add(descriptionlbl);
             add(txtdescription);
             add(txtdoctorname);

             
             add(datelbl);
             add(txtdate);
             
             add(backbtn);
             add(addbtn);
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
             displaydoctorname();
    }
    public void displaydoctorname() {

		try {
			String sql = "select name from register where role='doctor'";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet res = stmt.executeQuery();
			while (res.next()) {

				String doctor = res.getString("name");
				txtdoctorname.addItem(doctor);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
//    public static void main(String[] args) {
//        AddPatient f = new AddPatient();
//    }
 
    public void reset() {
        txtname.setText("");
        txtaddress.setText("");
        txtphone.setText("");
        txtdob.setText("");
        txtdescription.setText("");
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        
        if(source==addbtn) {
            String name=txtname.getText();
            String address=txtaddress.getText();
            String phone=txtphone.getText();
            String dob=txtdob.getText();
            String doctorName=txtdoctorname.getSelectedItem().toString();
            String gender=txtgender.getItemAt(txtgender.getSelectedIndex()).toString();
            String description=txtdescription.getText();
            String date = txtdate.getText();
            
            if (name.isEmpty() == false && phone.isEmpty() == false && address.isEmpty() == false && dob.isEmpty()==false && doctorName.isEmpty()==false && description.isEmpty()==false){
            
                String sql="insert into patient_details(name,address,phone,dob,doctor_name,gender,description,date) values(?,?,?,?,?,?,?,?)";
                try {
                PreparedStatement stmt=conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, phone);
                stmt.setString(4, dob);
                stmt.setString(5, doctorName);
                stmt.setString(6, gender);
                stmt.setString(7, description);
                stmt.setString(8, date);
               
                int ins=stmt.executeUpdate();
                if(ins>0) {
                JOptionPane.showMessageDialog(null, "Added successfully");
                reset();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Patient addition failed");
                }
                }
                catch(Exception ee) {ee.printStackTrace();}    
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Patient addition failed");
            }                                
        }
        else if(source==clearbtn) {
            reset();
        }
        else if(source==backbtn) {
            AdminDashboard f = new AdminDashboard();
            dispose();
        }

    }
}
