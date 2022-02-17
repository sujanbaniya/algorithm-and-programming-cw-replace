package Hospital_Management;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class LabAdminDashboard extends JFrame implements ActionListener, MouseListener {
	JLabel titlelbl, titlelbl1, titlelbl2, titlelbl3;
	JLabel labreport_idlbl;
	JLabel patient_idlbl;
	JLabel patientnamelbl;
	JLabel reportfilelbl;
	JLabel datelbl;

	JTextField labreport_idtxt;
	JTextField patient_idtxt;
	JTextField patientnametxt;
	JTextField reportfile;
	JFormattedTextField txtdate;

	JButton savedetailsbtn, clearbtn, logoutbtn, selectfilebtn, updatefilebtn;

	JTable tbl;
	DefaultTableModel model;

	JTable tbl1;
	DefaultTableModel model1;

	File file;

	Connection conn = null;

	LabAdminDashboard() {
		setTitle("Welcome To Upload Lab Report");
		setLayout(null);

		titlelbl = new JLabel("Lab Report Of Patients");
		titlelbl.setBounds(480, 15, 400, 35);
		titlelbl.setFont(new Font("Stoneage BT", Font.BOLD, 35));
		titlelbl1 = new JLabel("Add Lab Report");
		titlelbl1.setBounds(120, 85, 400, 35);
		titlelbl1.setFont(new Font("Stoneage BT", Font.BOLD, 30));
		titlelbl2 = new JLabel("Lab Report Details");
		titlelbl2.setBounds(720, 65, 400, 30);
		titlelbl2.setFont(new Font("Forte", Font.PLAIN, 25));
		titlelbl3 = new JLabel("Patient Details");
		titlelbl3.setBounds(750, 335, 400, 30);
		titlelbl3.setFont(new Font("Forte", Font.PLAIN, 25));

		labreport_idlbl = new JLabel("Lab Report Id:");
		patient_idlbl = new JLabel("Patient Id:");
		patientnamelbl = new JLabel("Patient Name:");
		reportfilelbl = new JLabel("Upload Report:");

		labreport_idtxt = new JTextField(10);
		patient_idtxt = new JTextField(10);
		patientnametxt = new JTextField(10);
		reportfile = new JTextField(10);
		
		DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
		txtdate = new JFormattedTextField(dateFormat);
		datelbl = new JLabel("Date:");
		datelbl.setLabelFor(txtdate);
		txtdate.setValue(new Date());

		savedetailsbtn = new JButton("Save Details");
		savedetailsbtn.addActionListener(this);
		clearbtn = new JButton("Clear");
		clearbtn.addActionListener(this);
		logoutbtn = new JButton("Logout");
		logoutbtn.addActionListener(this);
		selectfilebtn = new JButton("Upload Report");
		selectfilebtn.addActionListener(this);
		updatefilebtn = new JButton("Update Report");
		updatefilebtn.addActionListener(this);

		labreport_idlbl.setBounds(40, 140, 100, 25);
		patient_idlbl.setBounds(40, 180, 100, 25);
		patientnamelbl.setBounds(40, 220, 100, 25);
		reportfilelbl.setBounds(40, 260, 100, 25);
		datelbl.setBounds(40, 360, 100, 25);

		labreport_idtxt.setBounds(180, 140, 200, 25);
		patient_idtxt.setBounds(180, 180, 200, 25);
		patientnametxt.setBounds(180, 220, 200, 25);
		reportfile.setBounds(180, 260, 200, 25);
		txtdate.setBounds(180, 360, 200, 25);

		selectfilebtn.setBounds(180, 300, 200, 25);

		savedetailsbtn.setBounds(40, 400, 150, 25);
		clearbtn.setBounds(220, 400, 150, 25);
		logoutbtn.setBounds(220, 440, 150, 25);
		
		updatefilebtn.setBounds(40, 440, 150, 25);

		add(titlelbl);
		add(titlelbl1);
		add(titlelbl2);
		add(titlelbl3);
		add(updatefilebtn);

		add(labreport_idlbl);
		add(labreport_idtxt);
		labreport_idtxt.setEditable(false);
		add(patient_idlbl);
		add(patient_idtxt);
		patient_idtxt.setEditable(false);
		add(patientnamelbl);
		add(patientnametxt);
		patientnametxt.setEditable(false);
		add(reportfilelbl);
		add(reportfile);
		reportfile.setEditable(false);
		add(datelbl);
		add(txtdate);
		
		add(savedetailsbtn);
		add(clearbtn);
		add(logoutbtn);
		add(selectfilebtn);

		String[] cols = { "LabReport_Id", "Patient_Id", "Patient_Name", "Report File Path", "Added Date"};
		model = new DefaultTableModel(cols, 0);
		tbl = new JTable(model);
		tbl.addMouseListener(this);

		String[] cols1 = { "Patient_Id", "Patient_Name", "Address", "Age", "Phone", "Gender" };
		model1 = new DefaultTableModel(cols1, 0);
		tbl1 = new JTable(model1);
		tbl1.addMouseListener(this);

		JScrollPane sp = new JScrollPane(tbl);
		sp.setBounds(400, 90, 850, 240);
		add(sp);

		JScrollPane sp1 = new JScrollPane(tbl1);
		sp1.setBounds(400, 365, 850, 240);
		add(sp1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 660);
		setVisible(true);

		// connection start
		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://localhost:3306/java_hospital","root","messi7");


		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		// connection end
		remove_table();
		display();
		remove_table1();
		display1();
	}
	
	public void display() {

		try {
			String sql = "select * from lab_report";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				String labreport_id = res.getString(1);
				String patient_id = res.getString(2);
				String patient_name = res.getString(3);
				String reportfile = res.getString(4);
				String added_date = res.getString(5);

				model.addRow(new Object[] { labreport_id, patient_id, patient_name, reportfile, added_date});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove_table() {
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
		}
	}


	public void display1() {

		try {
			String sql = "select * from patient_details";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				String patient_id = res.getString(1);
				String patient_name = res.getString(2);
				String address = res.getString(3);
				String phone = res.getString(4);
				String age = res.getString(5);
				String gender = res.getString(7);

				model1.addRow(new Object[] { patient_id, patient_name, address,phone, age, gender });
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove_table1() {
		if (model1.getRowCount() > 0) {
			for (int i = model1.getRowCount() - 1; i >= 0; i--) {
				model1.removeRow(i);
			}
		}
	}

//	public static void main(String[] args) {
//		LabAdminDashboard f = new LabAdminDashboard();
//
//	}
	public void reset() {
		labreport_idtxt.setText("");
		patient_idtxt.setText("");
		patientnametxt.setText("");
		reportfile.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == selectfilebtn) {
			JFileChooser fileChooser = new JFileChooser();

			fileChooser.setCurrentDirectory(
					new File("D:\\Course Files\\Third Semester\\JAVA PRACTICAL\\Batch28A\\Coursework2\\LabReport"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
			fileChooser.addChoosableFileFilter(filter);

			int response = fileChooser.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {
				reportfile.setText("");
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				reportfile.setText(file.toString());
			}

		}
		if (source == savedetailsbtn) {
			String patient_id = patient_idtxt.getText();
			String patient_name = patientnametxt.getText();
			String report_file = reportfile.getText();
			String added_date = txtdate.getText();

			if (patient_id.isEmpty() == false && patient_name.isEmpty() == false && report_file.isEmpty() == false
					&& added_date.isEmpty() == false) {

				String sql = "insert into lab_report(patient_id,patient_name,report_file,added_date) values(?,?,?,?)";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, patient_id);
					stmt.setString(2, patient_name);
					stmt.setString(3, report_file);
					stmt.setString(4, added_date);
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Report Uploaded Sucessfully");
						remove_table();
						display();
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Report Upload Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Report Upload Failed");
			}
		} else if (source == clearbtn) {
			reset();
		} else if (source == logoutbtn) {
	           Login f = new Login();
	           dispose();
		} else if (source == updatefilebtn) {
			String report_file = reportfile.getText();
			String labreport = labreport_idtxt.getText();
			
			if (report_file.isEmpty() == false
					&& labreport.isEmpty() == false) {

				String sql = "update lab_report set report_file=? where id=?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, report_file);
					stmt.setString(2, labreport);
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Report updated Sucessfully");
						remove_table();
						display();
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Report updated Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Report update Failed");
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == tbl1) {
			int row1 = tbl1.rowAtPoint(e.getPoint());
			patient_idtxt.setText(model1.getValueAt(row1, 0).toString());
			patientnametxt.setText(model1.getValueAt(row1, 1).toString());
		}
		if (source == tbl) {
			int row1 = tbl.rowAtPoint(e.getPoint());
			labreport_idtxt.setText(model.getValueAt(row1, 0).toString());
			patient_idtxt.setText(model.getValueAt(row1, 1).toString());
			patientnametxt.setText(model.getValueAt(row1, 2).toString());
			reportfile.setText(model.getValueAt(row1, 3).toString());
		}
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
