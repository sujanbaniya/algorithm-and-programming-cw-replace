package Hospital_Management;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class UpdateAssignedWard extends JFrame implements ActionListener {
    JLabel titlelbl;
    JLabel assignedIdlbl;
    JLabel patientNamelbl;
    JLabel bedNolbl;
    JLabel doctorNamelbl;
    JLabel nurseNamelbl;
    JLabel datelbl;
    
    JTextField txtassignedId;
    JTextField txtpatientName;
    JTextField txtbedNo;
    JTextField txtdoctorName;
    JTextField txtnurseName;
//    JComboBox txtgender;
    JFormattedTextField txtdate;
    
    
    JButton backbtn;
    JButton updatebtn;
    JButton clearbtn;
    
    Connection conn = null;
    
    UpdateAssignedWard() {
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
    	
    	
        setTitle("Update Assigned Ward");
        setLayout(null);

        titlelbl=new JLabel("Update Assigned Ward");
        assignedIdlbl = new JLabel("Assigned Id:");
        patientNamelbl = new JLabel("Patient Name:");
        bedNolbl=new JLabel("Bed Number:");
        doctorNamelbl=new JLabel("Doctor Name:");
        nurseNamelbl=new JLabel("Nurse Name:");
        
        txtassignedId = new JTextField(10);
        txtpatientName = new JTextField(10);
        txtbedNo=new JTextField(10);
        txtdoctorName=new JTextField(10);
        txtnurseName=new JTextField(10);
//        String gender[]={"Male","Female","Others"}; 
//        txtgender=new JComboBox(gender);
        
        DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
        txtdate = new JFormattedTextField(dateFormat);
        datelbl = new JLabel("Date:");
        datelbl.setLabelFor(txtdate);
        txtdate.setValue(new Date());
        
        backbtn=new JButton("Back");
        updatebtn=new JButton("Update");
        clearbtn=new JButton("Clear");
        
        backbtn.addActionListener(this);
        updatebtn.addActionListener(this);
        clearbtn.addActionListener(this);
        
	     titlelbl.setBounds(110, 15, 300, 30);
	     titlelbl.setFont(new Font("Serif", Font.BOLD, 25));
	     
	     assignedIdlbl.setBounds(50, 50, 100, 25);
	     patientNamelbl.setBounds(50, 100, 100, 25);
	     bedNolbl.setBounds(50, 150, 100, 25);
	     doctorNamelbl.setBounds(50, 200, 100, 25);
	     nurseNamelbl.setBounds(50, 250, 100, 25);
	     datelbl.setBounds(50, 300, 100, 25);
	     
	     txtassignedId.setBounds(170, 50, 200, 25);
	     txtpatientName.setBounds(170, 100, 200, 25);
	     txtbedNo.setBounds(170, 150, 200, 25);
	     txtdoctorName.setBounds(170, 200, 200, 25);
	     txtnurseName.setBounds(170, 250, 200, 25);
	     
	     txtdate.setBounds(170, 300, 200, 25);
//	     txtdate.setEditable(false);
	     
	//             today.setBounds();
	     backbtn.setBounds(30, 350, 100, 25);
	     clearbtn.setBounds(160, 350, 100, 25);
	     updatebtn.setBounds(290, 350, 100, 25);
	     
	     add(titlelbl);
	     add(assignedIdlbl);
	     add(txtassignedId);
	     add(patientNamelbl);
	     add(txtpatientName);
	     add(bedNolbl);
	     add(txtbedNo);
	     
	     add(doctorNamelbl);
	     add(txtdoctorName);
	     add(nurseNamelbl);
	     add(txtnurseName);
	

         add(datelbl);
         add(txtdate);
         
	     
	     add(backbtn);
	     add(updatebtn);
	     add(clearbtn);
	    
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setSize(420,620);
	     setVisible(true);
             
             
    }
    
//    public static void main(String[] args) {
//        UpdateAssignedWard f = new UpdateAssignedWard();
//    }
// 
    public void reset() {
    	txtassignedId.setText("");
    	txtpatientName.setText("");
    	txtbedNo.setText("");
    	txtdoctorName.setText("");
    	txtnurseName.setText("");
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        
        if(source==updatebtn) {
        	String assignedId = txtassignedId.getText();
            String patientName=txtpatientName.getText();
            String bedNo=txtbedNo.getText();
            String doctorName=txtdoctorName.getText();
//            String gender=txtgender.getItemAt(txtgender.getSelectedIndex()).toString();
            String nurseName=txtnurseName.getText();
            
            if (patientName.isEmpty() == false && bedNo.isEmpty() == false && doctorName.isEmpty()==false && nurseName.isEmpty()==false){
            
                String sql="UPDATE assignWard"
                		+ "SET patientName = '" + patientName + "', bedNo= '" + bedNo + "', doctorName = '" + doctorName + "', nurseName='" + nurseName + "'"
                		+ "WHERE CustomerID = '" + assignedId + "' ;";
                		
                try {
                PreparedStatement stmt=conn.prepareStatement(sql);
//                stmt.setString(1, name);
//                stmt.setString(3, address);
//                stmt.setString(4, dob);
//                stmt.setString(5, gender);
//                stmt.setString(6, description);
                int ins=stmt.executeUpdate();
                if(ins>0) {
                JOptionPane.showMessageDialog(null, "Updated successfully");
                reset();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Patient Update failed");
                }
                }
                catch(Exception ee) {ee.printStackTrace();}    
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Patient Update failed");
            }                                
        }
        else if(source==clearbtn) {
            reset();
        }
        else if(source==backbtn) {
            AssignWard f = new AssignWard();
            dispose();
        }

    }
}
