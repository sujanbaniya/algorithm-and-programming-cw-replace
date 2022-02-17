package Hospital_Management;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableModel;

public class UpdatePatientDetails extends JFrame implements MouseListener, ActionListener {
    JLabel titlelbl;
    JLabel idlbl;
    JLabel usernamelbl;
    JLabel addresslbl;
    JLabel doblbl;
    JLabel doctorlbl;
    JLabel genderlbl;
    JLabel descriptionlbl;
    JLabel datelbl;
    JLabel phonelbl;
    
    
    JTextField txtid;
    JTextField txtname;
    JTextField txtaddress;
    JTextField txtdob;
    JComboBox txtdoctor;
    JTextField txtphone;
    JComboBox txtgender;
    JTextField txtdescription;
    JFormattedTextField txtdate;
    
    
    JButton backbtn;
    JButton updatebtn;
    JButton clearbtn;
    
    // For Table
    JTable tbl;
    DefaultTableModel model;
    
    JTextField txt1;
    // For Ending Table
    
    Connection conn = null;
    
    UpdatePatientDetails() {
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
        
    	
    	
        setTitle("Update Patient");
        setLayout(null);

        titlelbl=new JLabel("Update Patient");
        idlbl = new JLabel("Patient Id:");
        usernamelbl=new JLabel("Username:");
        addresslbl=new JLabel("Address:");
        phonelbl=new JLabel("Phone");
        doblbl=new JLabel("DOB:");
        doctorlbl=new JLabel("Doctor Name:");
        genderlbl=new JLabel("Gender:");
        descriptionlbl=new JLabel("Description:");
        
        txtid = new JTextField(10);
        txtname=new JTextField(10);
        txtaddress=new JTextField(10);
        txtphone=new JTextField(10);
        txtdob=new JTextField(10);
        txtdoctor=new JComboBox();
        String gender[]={"Male","Female","Others"}; 
        txtgender=new JComboBox(gender);
        txtdescription=new JTextField(10);
        
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
        
	     titlelbl.setBounds(110, 15, 200, 30);
	     titlelbl.setFont(new Font("Serif", Font.BOLD, 25));
	    
	     idlbl.setBounds(50, 50, 75, 25);
	     usernamelbl.setBounds(50, 100, 75, 25);
	     addresslbl.setBounds(50, 150, 75, 25);
	     phonelbl.setBounds(50, 200, 75, 25);
	     doblbl.setBounds(50, 250, 100, 25);
	     doctorlbl.setBounds(50, 300, 75, 25);
	     genderlbl.setBounds(50,350,75,25);
	     descriptionlbl.setBounds(50,400,75,25);
	     datelbl.setBounds(50,450,75,25);
	     
	     txtid.setBounds(170, 50, 200, 25);
	     txtname.setBounds(170, 100, 200, 25);
	     txtaddress.setBounds(170, 150, 200, 25);
	     txtphone.setBounds(170, 200, 200, 25);
	     txtdob.setBounds(170, 250, 200, 25);
	     txtdoctor.setBounds(170, 300, 200, 25);
	     txtgender.setBounds(170, 350, 200, 25);
	     txtdescription.setBounds(170, 400, 200, 25);
	     txtdate.setBounds(170, 450, 200, 25);
//	     txtdate.setEditable(false);
	//             today.setBounds();
	     
	     backbtn.setBounds(30, 500, 100, 25);
	     clearbtn.setBounds(160, 500, 100, 25);
	     updatebtn.setBounds(290, 500, 100, 25);
	     
	     
	     // Table
	     String [] cols= {"Patient_Id","Name","Address","Phone","DOB","Assigned Doctor","Gender","Description","Date"};
	     model=new DefaultTableModel(cols,0);
	     tbl=new JTable(model);
	     
	     JScrollPane sp=new JScrollPane(tbl);
	     sp.setBounds(400, 50, 750, 450);
	     add(sp);
	     tbl.addMouseListener(this);
	     
	     add(titlelbl);
	     add(idlbl);
	     add(txtid);
	     add(usernamelbl);
	     add(txtname);
	     add(phonelbl);
	     add(doctorlbl);
	     
	     add(addresslbl);
	     add(txtaddress);
	     add(txtphone);
	     add(doblbl);
	     add(txtdob);
	     add(txtdoctor);
	     add(genderlbl);
	     add(txtgender);
	     add(descriptionlbl);
	     add(txtdescription);
	
	     
	     add(datelbl);
	     add(txtdate);
	     
	     add(backbtn);
	     add(updatebtn);
	     add(clearbtn);
	     displayTable();
	     
	    
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setSize(1170,600);
	     setVisible(true);
         
	     displaydoctorname();
             
    }
    
    
    public void displayTable() {
        try {
            String sql="select * from patient_details";
            PreparedStatement stmt=conn.prepareStatement(sql);
            ResultSet result=stmt.executeQuery();
            
            while(result.next()) {
                
                String id=result.getString("id");
                String name=result.getString("name");
                String address=result.getString("address");
                String phone=result.getString("phone");
                String dob=result.getString("dob");
                String doctorName=result.getString("doctor_name");
                String gender = result.getString("gender");
                String description = result.getString("description");
                String date = result.getString("date");
                String medical_history = result.getString("medical_history");
                model.addRow(new Object[] {id,name,address,phone,dob,doctorName,gender,description,date,medical_history});
            }
          
        }
        catch(Exception ee) {
            ee.printStackTrace();
        }
    }
    
    public void remove_table() {
        for(int i=model.getRowCount() -1;i>=0;i--) {
            
            model.removeRow(i);
        }
       
   }
    public void displaydoctorname() {

		try {
			String sql = "select name from register where role='doctor'";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet res = stmt.executeQuery();
			while (res.next()) {

				String doctor = res.getString("name");
				txtdoctor.addItem(doctor);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
//    public static void main(String[] args) {
//        UpdatePatientDetails f = new UpdatePatientDetails();
//    }
 
    public void reset() {
    	txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txtphone.setText("");
        txtdob.setText("");
        txtdescription.setText("");
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source=e.getSource();
        
        if(source==updatebtn) {
            String name=txtname.getText();
            String address=txtaddress.getText();
            String phone=txtphone.getText();
            String dob=txtdob.getText();
            String doctorName=txtdoctor.getSelectedItem().toString();
            String gender=txtgender.getItemAt(txtgender.getSelectedIndex()).toString();
            String description=txtdescription.getText();
            String date = txtdate.getText();
            String id = txtid.getText();
            
            if (name.isEmpty() == false && address.isEmpty() == false && phone.isEmpty() == false && dob.isEmpty()==false && description.isEmpty()==false){
            
                String sql="UPDATE patient_details SET name=?, address=?,phone=?, dob=?,doctor_name=?, gender=?, description=?, date=? WHERE id=?";
                		
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
	                stmt.setString(9, id);
	                int ins=stmt.executeUpdate();
	                if(ins>0) {
		                JOptionPane.showMessageDialog(null, "Updated successfully");
		                reset();
		                remove_table();
		                displayTable();
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
            AdminDashboard f = new AdminDashboard();
            dispose();
        }

    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row=tbl.rowAtPoint(e.getPoint());
        
//        System.out.println(model.getValueAt(row, 0));
        txtid.setText(model.getValueAt(row, 0).toString());
        txtname.setText(model.getValueAt(row, 1).toString());
        txtaddress.setText(model.getValueAt(row, 2).toString());
        txtphone.setText(model.getValueAt(row, 3).toString());
        txtdob.setText(model.getValueAt(row, 4).toString());
        txtdoctor.setSelectedItem(model.getValueAt(row, 5));
        txtgender.setSelectedItem(model.getValueAt(row, 6));
        txtdescription.setText(model.getValueAt(row, 7).toString());
        txtdate.setText(model.getValueAt(row, 8).toString());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
