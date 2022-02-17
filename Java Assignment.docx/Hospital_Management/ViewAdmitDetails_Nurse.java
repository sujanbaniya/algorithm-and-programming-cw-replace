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

public class ViewAdmitDetails_Nurse extends JFrame implements MouseListener, ActionListener{
   
	 JButton backbtn;
	 Connection conn;
	 String nurse_name;
	 JLabel titlelbl;
	 
	 JTable tbl;
	 DefaultTableModel model;

     ViewAdmitDetails_Nurse(String name) {
    	 nurse_name=name;
	   setTitle("Assigned Patient Details");
       setLayout(null);

       backbtn=new JButton("Back");
       
       titlelbl = new JLabel("Assigned Patient Details");
		titlelbl.setBounds(300, 15, 400, 30);
		titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
       
       backbtn.addActionListener(this);
       backbtn.setBounds(20, 90, 120, 30);
       
       String[] cols = { "id", "Patient_Name", "Bed No.", "Doctor Assigned", "Nurse Assigned",
				"Ward Assigned", "Admitted Date", "Medical Diagnosis" };
       model=new DefaultTableModel(cols,0);
       tbl=new JTable(model);
       
       JScrollPane sp=new JScrollPane(tbl);
       sp.setBounds(20, 120, 850, 240);
       
       tbl.addMouseListener(this);
       
       add(titlelbl);
       add(backbtn);
       add(sp);

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(900,450);
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
			String sql = "select * from assignward where nurse_name=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nurse_name);
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
            
   
   public static void main(String[] args) {
	   ViewAdmitDetails_Nurse f = new ViewAdmitDetails_Nurse("nurse");
   }

   @Override
   public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();
       if (source == backbtn) {
			NurseDashboard f = new NurseDashboard(nurse_name);
	           dispose();
		} 

   }

@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	int row=tbl.rowAtPoint(e.getPoint());
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