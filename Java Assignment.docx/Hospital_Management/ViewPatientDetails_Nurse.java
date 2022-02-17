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

public class ViewPatientDetails_Nurse extends JFrame implements MouseListener, ActionListener{
   
	 JButton backbtn;
	 Connection conn;
	 String nurse_name;
	 JLabel titlelbl;
	 
	 JTable tbl;
	 DefaultTableModel model;

     ViewPatientDetails_Nurse(String name) {
    	 nurse_name = name;
	   setTitle("Patient Details");
       setLayout(null);

       backbtn=new JButton("Back");
       
       titlelbl = new JLabel("Patient Details");
		titlelbl.setBounds(300, 15, 400, 30);
		titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
      
       backbtn.addActionListener(this);
       backbtn.setBounds(20, 90, 120, 30);
       
       String [] cols= {"Patient_Id","Name","Address","DOB","Gender","Description","Date","Medical_History"};
       model=new DefaultTableModel(cols,0);
       tbl=new JTable(model);
       
       JScrollPane sp=new JScrollPane(tbl);
       sp.setBounds(20, 120, 850, 240);
       
       tbl.addMouseListener(this);
       
       add(backbtn);
       add(titlelbl);
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
           String sql="select * from patient_details";
           PreparedStatement stmt=conn.prepareStatement(sql);
           ResultSet result=stmt.executeQuery();
           
           while(result.next()) {
               
               String id=result.getString("id");
               String name=result.getString("name");
               String address=result.getString("address");
               String dob=result.getString("dob");
               String gender = result.getString("gender");
               String description = result.getString("description");
               String date = result.getString("date");
               String medical_history = result.getString("medical_history");
               model.addRow(new Object[] {id,name,address,dob,gender,description,date,medical_history});
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
	   ViewPatientDetails_Nurse f = new ViewPatientDetails_Nurse("nurse");
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