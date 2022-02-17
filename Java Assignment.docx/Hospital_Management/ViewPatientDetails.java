package Hospital_Management;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewPatientDetails extends JFrame implements MouseListener, ActionListener{
	
	JLabel titlelbl;
	JLabel patient_namelbl;
    JLabel medical_historylbl;
    
    JTextField txtpatient_id;
    JTextField txtmedical_history;
   
	JButton backbtn;
	JButton addDiagnosisInfoButton;
	
	String doctor_name;
	 
	
	 Connection conn;
	 
	 JTable tbl;
	 DefaultTableModel model;

     ViewPatientDetails(String name) {
    	 doctor_name = name;
    	 
	   setTitle("Patient Details");
       setLayout(null);
       
       patient_namelbl = new JLabel("Patient Id:");
       medical_historylbl=new JLabel("Medical History: ");
       
       txtpatient_id = new JTextField(10);
       txtmedical_history=new JTextField(10);
       
       titlelbl = new JLabel("Patient Details");
       titlelbl.setBounds(480, 15, 400, 30);
       titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
       
       patient_namelbl.setBounds(30, 80, 75, 25);
       medical_historylbl.setBounds(30, 130, 150, 25);
	   
       txtpatient_id.setBounds(170, 80, 200, 25);
       txtmedical_history.setBounds(170, 130, 200, 25);
       
       add(titlelbl);
       add(patient_namelbl);
       add(medical_historylbl);
       add(txtpatient_id);
       add(txtmedical_history);
       
       
       
       addDiagnosisInfoButton=new JButton("Add Medical History");
       backbtn=new JButton("Back");
       
       addDiagnosisInfoButton.addActionListener(this);
       backbtn.addActionListener(this);
       
       backbtn.setBounds(170, 250, 120, 50);
       addDiagnosisInfoButton.setBounds(30, 250, 120, 50);
       
       
       String [] cols= {"Patient_Id","Name","Address","Phone","DOB","Gender","Description","Date","Medical_History"};
       model=new DefaultTableModel(cols,0);
       tbl=new JTable(model);
       
       JScrollPane sp=new JScrollPane(tbl);
       sp.setBounds(400, 80, 850, 240);
       
       tbl.addMouseListener(this);
       
       add(backbtn);
       add(addDiagnosisInfoButton);
       add(sp);

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(1350,450);
       setVisible(true); 
       
       
       
       try {
           conn =
                   DriverManager.getConnection("jdbc:mysql://localhost:3306/java_hospital","root","messi7");



       } catch (SQLException ex) {
           // handle any errors
           System.out.println("SQLException: " + ex.getMessage());
           System.out.println("SQLState: " + ex.getSQLState());
           System.out.println("VendorError: " + ex.getErrorCode());
       }
       remove_table();
       displayTable();
       
   	}
   
   public void displayTable() {
       
       try {
           String sql="select * from patient_details where doctor_name=?";
           
           PreparedStatement stmt=conn.prepareStatement(sql);
           stmt.setString(1, doctor_name);
           ResultSet result=stmt.executeQuery();
           
           while(result.next()) {
               
               String id=result.getString("id");
               String name=result.getString("name");
               String address=result.getString("address");
               String phone=result.getString("phone");
               String dob=result.getString("dob");
               String gender = result.getString("gender");
               String description = result.getString("description");
               String date = result.getString("date");
               String medical_history = result.getString("medical_history");
               model.addRow(new Object[] {id,name,address,phone,dob,gender,description,date,medical_history});
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
            
   
//   public static void main(String[] args) {
//	   ViewPatientDetails f = new ViewPatientDetails("doc1");
//   }

   @Override
   public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();
       
		if (source == addDiagnosisInfoButton) {
			String medical_history = txtmedical_history.getText();
			String patient_id = txtpatient_id.getText();
			
			

			if (medical_history.isEmpty() == false) {

				String sql = "update patient_details set medical_history=? where id=?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, medical_history);
					stmt.setString(2, patient_id);
					
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Medical history Updated Successfully");
						remove_table();
						displayTable();
//						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Medical history Update Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Medical history Update Failed");
			}
		} else if (source == backbtn) {
			DoctorDashboard f = new DoctorDashboard(doctor_name);
	           dispose();
		} 


   }

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	int row=tbl.rowAtPoint(e.getPoint());
	
	txtpatient_id.setText(model.getValueAt(row, 0).toString());
	txtmedical_history.setText(model.getValueAt(row, 8).toString());
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