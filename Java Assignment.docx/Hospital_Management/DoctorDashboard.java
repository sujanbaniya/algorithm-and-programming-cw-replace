package Hospital_Management;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class DoctorDashboard extends JFrame implements ActionListener{

     JButton viewPatientDetailsbtn;
     JButton viewAdmitDetailsbtn;
     JButton logoutbtn;
     JButton showLabReportbtn;
     String doctor_name;

     DoctorDashboard(String name) {
    	 doctor_name = name;
    	 
       setTitle("Doctor");
       setLayout(null);

       viewAdmitDetailsbtn=new JButton("View Admit Details");
       viewPatientDetailsbtn=new JButton("View Patient Details");
       showLabReportbtn=new JButton("Show Lab Report");
       logoutbtn=new JButton("Logout");
       

       viewAdmitDetailsbtn.addActionListener(this);
       viewPatientDetailsbtn.addActionListener(this);
       logoutbtn.addActionListener(this);
       showLabReportbtn.addActionListener(this);


       viewAdmitDetailsbtn.setBounds(120, 50, 150, 50);
       viewPatientDetailsbtn.setBounds(120, 120,  150, 50);
       showLabReportbtn.setBounds(120, 200,  150, 50);
       logoutbtn.setBounds(120, 280, 150, 50);
       

       add(viewAdmitDetailsbtn);
       add(viewPatientDetailsbtn);
       add(logoutbtn);
       add(showLabReportbtn);

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(420,600);
       setVisible(true); 

   }

//   public static void main(String[] args) {
//       DoctorDashboard f = new DoctorDashboard("doc2");
//   }

   @Override
   public void actionPerformed(ActionEvent e) {
       Object source=e.getSource();

       if(source==viewAdmitDetailsbtn) {
           ViewAdmitDetails f = new ViewAdmitDetails(doctor_name);
           dispose();
       }else if (source == logoutbtn) {
           Login f = new Login();
           dispose();
       }else if (source == viewPatientDetailsbtn) {
           ViewPatientDetails f = new ViewPatientDetails(doctor_name);
           dispose();
       }
       else if (source == showLabReportbtn) {
    	   ShowLabReport f = new ShowLabReport(doctor_name);
           dispose();
       }

   }


}
