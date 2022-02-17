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

public class ViewAdmitDetails extends JFrame implements MouseListener, ActionListener{
	
	JLabel titlelbl;
	JLabel patient_namelbl;
    JLabel medical_diagnosislbl;
    
    JTextField txtpatient_id;
    JTextField txtmedical_diagnosis;
   
	JButton backbtn;
	JButton addDiagnosisInfoButton;
	
	String doctor_name;
	 
	
	 Connection conn;
	 
	 JTable tbl;
	 DefaultTableModel model;

     ViewAdmitDetails(String name) {
    	 doctor_name=name;
    	 
	   setTitle("Patient Details");
       setLayout(null);
       
       titlelbl = new JLabel("Admitted Patient Details");
       titlelbl.setBounds(480, 15, 400, 30);
       titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
       
       patient_namelbl = new JLabel("Patient Id:");
       medical_diagnosislbl=new JLabel("Medical Diagnosis: ");
       
       txtpatient_id = new JTextField(10);
       txtmedical_diagnosis=new JTextField(10);
       
       patient_namelbl.setBounds(30, 80, 75, 25);
       medical_diagnosislbl.setBounds(30, 130, 150, 25);
	   
       txtpatient_id.setBounds(170, 80, 200, 25);
       txtmedical_diagnosis.setBounds(170, 130, 200, 25);
       
       
       add(titlelbl);
       add(patient_namelbl);
       add(medical_diagnosislbl);
       add(txtpatient_id);
       add(txtmedical_diagnosis);
       
       
       
       addDiagnosisInfoButton=new JButton("Add Diagnosis");
       backbtn=new JButton("Back");
       
       addDiagnosisInfoButton.addActionListener(this);
       backbtn.addActionListener(this);
       
       backbtn.setBounds(170, 250, 120, 50);
       addDiagnosisInfoButton.setBounds(30, 250, 120, 50);
       
       
       String[] cols = { "id", "Patient_Name", "Bed No.", "Doctor Assigned", "Nurse Assigned",
				"Ward Assigned", "Admitted Date", "Medical Diagnosis" };
       model=new DefaultTableModel(cols,0);
       tbl=new JTable(model);
       
       JScrollPane sp=new JScrollPane(tbl);
       sp.setBounds(400, 80, 850, 240);
       
       tbl.addMouseListener(this);
       
       add(backbtn);
       add(addDiagnosisInfoButton);
       add(sp);

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(1300,400);
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
			String sql = "select * from assignward where doctor_name=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, doctor_name);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				String id = res.getString(1);
				String patient_name = res.getString(2);
				String bed_no = res.getString(3);
				String doctor_name = res.getString(4);
				String nurse_name = res.getString(5);
				String assign_ward = res.getString(6);
				String admitted_date = res.getString(7);
				String medical_diagnosis = res.getString(8);

				model.addRow(new Object[] { id, patient_name, bed_no, doctor_name, nurse_name,
						assign_ward, admitted_date, medical_diagnosis });
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
//	   ViewAdmitDetails f = new ViewAdmitDetails("doc1");
//   }

   @Override
   public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();
       
		if (source == addDiagnosisInfoButton) {
			String medical_diagnosis = txtmedical_diagnosis.getText();
			String patient_id = txtpatient_id.getText();
			
			

			if (medical_diagnosis.isEmpty() == false) {

				String sql = "update assignward set medical_diagnosis=? where id=?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, medical_diagnosis);
					stmt.setString(2, patient_id);
					
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Medical diagnosis Updated Successfully");
						remove_table();
						displayTable();
//						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Medical diagnosis Update Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Medical diagnosis Update Failed");
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
	txtmedical_diagnosis.setText(model.getValueAt(row, 7).toString());
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