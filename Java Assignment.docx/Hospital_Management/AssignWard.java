package Hospital_Management;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AssignWard extends JFrame implements ActionListener, MouseListener {
	JLabel titlelbl, titlelbl1, titlelbl2, titlelbl3;
	JLabel admitpatient_idlbl;
	JLabel patientnamelbl;
	JLabel assignbednolbl;
	JLabel assigndoctorlbl;
	JLabel assignnurselbl;
	JLabel assignwardtypelbl;
	JLabel datelbl;

	JTextField admitpatient_idtxt;
	JTextField patientnametxt;
	JTextField assignbednotxt;
	JComboBox assigndoctortxt;
	JComboBox assignnursetxt;
	JComboBox assignwardtypetxt;
	JFormattedTextField txtdate;

	JButton savedetailsbtn, clearbtn, backbtn, updatebtn;

	JTable tbl;
	DefaultTableModel model;

	JTable tbl1;
	DefaultTableModel model1;

	Connection conn = null;

	AssignWard() {
		setTitle("Welcome To Assign Ward");
		setLayout(null);

		titlelbl = new JLabel("Welcome To Assign Ward");
		titlelbl.setBounds(480, 15, 400, 30);
		titlelbl.setFont(new Font("Arial", Font.BOLD, 30));
		titlelbl1 = new JLabel("Admit Patient");
		titlelbl1.setBounds(120, 80, 400, 30);
//		titlelbl1.setFont(new Font("Forte", Font.BOLD, 30));
		titlelbl2 = new JLabel("Admitted Patient Details");
		titlelbl2.setBounds(700, 55, 400, 30);
//		titlelbl2.setFont(new Font("Forte", Font.PLAIN, 25));
		titlelbl3 = new JLabel("Patient Details");
		titlelbl3.setBounds(750, 325, 400, 30);
//		titlelbl3.setFont(new Font("Forte", Font.PLAIN, 25));

		admitpatient_idlbl = new JLabel("Admit Patient Id:");
		patientnamelbl = new JLabel("Patient Name:");
		assignbednolbl = new JLabel("Assign Bed No:");
		assigndoctorlbl = new JLabel("Assign Doctor:");
		assignnurselbl = new JLabel("Assign Nurse:");
		assignwardtypelbl = new JLabel("Assign Ward Type:");

		admitpatient_idtxt = new JTextField(10);
		patientnametxt = new JTextField(10);
		assignbednotxt = new JTextField(10);
		assigndoctortxt = new JComboBox();
		assignnursetxt = new JComboBox();
		String ward[] = { "General Ward1", "General Ward2", "ICU", "CCU" };
		assignwardtypetxt = new JComboBox(ward);

		DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
		txtdate = new JFormattedTextField(dateFormat);
		datelbl = new JLabel("Date:");
		datelbl.setLabelFor(txtdate);
		txtdate.setValue(new Date());

		savedetailsbtn = new JButton("Save Details");
		savedetailsbtn.addActionListener(this);
		clearbtn = new JButton("Clear");
		clearbtn.addActionListener(this);
		backbtn = new JButton("Back To Dashboard");
		backbtn.addActionListener(this);
		updatebtn = new JButton("Update Details");
		updatebtn.addActionListener(this);

		admitpatient_idlbl.setBounds(40, 120, 100, 25);
		patientnamelbl.setBounds(40, 160, 100, 25);
		assignbednolbl.setBounds(40, 200, 100, 25);
		assigndoctorlbl.setBounds(40, 240, 100, 25);
		assignnurselbl.setBounds(40, 280, 100, 25);
		assignwardtypelbl.setBounds(40, 320, 100, 25);
		datelbl.setBounds(40, 360, 100, 25);

		admitpatient_idtxt.setBounds(180, 120, 200, 25);
		patientnametxt.setBounds(180, 160, 200, 25);
		assignbednotxt.setBounds(180, 200, 200, 25);
		assigndoctortxt.setBounds(180, 240, 200, 25);
		assignnursetxt.setBounds(180, 280, 200, 25);
		assignwardtypetxt.setBounds(180, 320, 200, 25);
		txtdate.setBounds(180, 360, 200, 25);

		savedetailsbtn.setBounds(40, 400, 150, 25);
		clearbtn.setBounds(220, 400, 150, 25);
		updatebtn.setBounds(40, 440, 150, 25);
		backbtn.setBounds(220, 440, 150, 25);

		add(titlelbl);
		add(titlelbl1);
		add(titlelbl2);
		add(titlelbl3);

		add(admitpatient_idlbl);
		add(admitpatient_idtxt);
		admitpatient_idtxt.setEditable(false);
		add(patientnamelbl);
		add(patientnametxt);
		patientnametxt.setEditable(false);
		add(assignbednolbl);
		add(assignbednotxt);
		add(assigndoctorlbl);
		add(assigndoctortxt);
		add(assignnurselbl);
		add(assignnursetxt);
		add(assignwardtypelbl);
		add(assignwardtypetxt);
		add(datelbl);
		add(txtdate);

		add(savedetailsbtn);
		add(clearbtn);
		add(updatebtn);
		add(backbtn);

		String[] cols = { "id", "Patient_Name", "Bed No.", "Doctor Assigned", "Nurse Assigned",
				"Ward Assigned", "Admitted Date" };
		model = new DefaultTableModel(cols, 0);
		tbl = new JTable(model);
		tbl.addMouseListener(this);

		String[] cols1 = { "Patient_Id", "Patient_Name", "Address", "Age", "Phone", "Gender" };
		model1 = new DefaultTableModel(cols1, 0);
		tbl1 = new JTable(model1);
		tbl1.addMouseListener(this);

		JScrollPane sp = new JScrollPane(tbl);
		sp.setBounds(400, 80, 850, 240);
		add(sp);

		JScrollPane sp1 = new JScrollPane(tbl1);
		sp1.setBounds(400, 355, 850, 240);
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
		displaydoctorname();
		displaynursename();
		remove_table();
		display();
		remove_table1();
		display1();

	}

	public void displaydoctorname() {

		try {
			String sql = "select name from register where role='doctor'";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet res = stmt.executeQuery();
			while (res.next()) {

				String doctor = res.getString("name");
				assigndoctortxt.addItem(doctor);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displaynursename() {

		try {
			String sql = "select name from register where role='Nurse'";
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet res = stmt.executeQuery();
			while (res.next()) {

				String nurse = res.getString("name");
				assignnursetxt.addItem(nurse);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void display() {

		try {
			String sql = "select * from assignward";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				String id = res.getString(1);
				String patient_name = res.getString(2);
				String bed_no = res.getString(3);
				String doctor_name = res.getString(4);
				String nurse_name = res.getString(5);
				String assign_ward = res.getString(6);
				String admitted_date = res.getString(7);

				model.addRow(new Object[] { id, patient_name, bed_no, doctor_name, nurse_name,
						assign_ward, admitted_date });
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

				model1.addRow(new Object[] { patient_id, patient_name, address, age, phone, gender });
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
//		AssignWard f = new AssignWard();
//
//	}

	public void reset() {
		admitpatient_idtxt.setText("");
		patientnametxt.setText("");
		assignbednotxt.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();

		if (source == savedetailsbtn) {
			String patient_name = patientnametxt.getText();
			String bed_no = assignbednotxt.getText();
			String doctor_name = assigndoctortxt.getSelectedItem().toString();
			String nurse_name = assignnursetxt.getSelectedItem().toString();
			String ward_type = assignwardtypetxt.getItemAt(assignwardtypetxt.getSelectedIndex()).toString();
			String admitted_date = txtdate.getText();

			if (patient_name.isEmpty() == false && bed_no.isEmpty() == false && doctor_name.isEmpty() == false
					&& nurse_name.isEmpty() == false && ward_type.isEmpty() == false) {

				String sql = "insert into assignward(patient_name,bed_no,doctor_name,nurse_name,ward_type,date) values(?,?,?,?,?,?)";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, patient_name);
					stmt.setString(2, bed_no);
					stmt.setString(3, doctor_name);
					stmt.setString(4, nurse_name);
					stmt.setString(5, ward_type);
					stmt.setString(6, admitted_date);
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Patient Admitted Successfully");
						remove_table();
						display();
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Patient Admit Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Patient Admit Failed");
			}
		}
		if (source == updatebtn) {
			String admitpatient_id = admitpatient_idtxt.getText();
			String patient_name = patientnametxt.getText();
			String bed_no = assignbednotxt.getText();
			String doctor_name = assigndoctortxt.getSelectedItem().toString();
			String nurse_name = assignnursetxt.getSelectedItem().toString();
			String ward_type = assignwardtypetxt.getItemAt(assignwardtypetxt.getSelectedIndex()).toString();
//			String admitted_date = txtdate.getText();

			if (admitpatient_id.isEmpty() == false && patient_name.isEmpty() == false
					&& bed_no.isEmpty() == false && doctor_name.isEmpty() == false
					&& nurse_name.isEmpty() == false && ward_type.isEmpty() == false) {

				String sql = "update assignward set patient_name=?,bed_no=?,doctor_name=?,nurse_name=?,ward_type=? where id=?";
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, patient_name);
					stmt.setString(2, bed_no);
					stmt.setString(3, doctor_name);
					stmt.setString(4, nurse_name);
					stmt.setString(5, ward_type);
					stmt.setString(6, admitpatient_id);
					int ins = stmt.executeUpdate();
					if (ins > 0) {
						JOptionPane.showMessageDialog(null, "Admit Patient Updated Successfully");
						remove_table();
						display();
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "Admit Patient Update Failed");
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Admit Patient Update Failed");
			}
		} else if (source == clearbtn) {
			reset();
		} else if (source == backbtn) {
			AdminDashboard f = new AdminDashboard();
			dispose();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		
		if(source == tbl) {
		int row = tbl.rowAtPoint(e.getPoint());
		admitpatient_idtxt.setText(model.getValueAt(row, 0).toString());
		patientnametxt.setText(model.getValueAt(row, 1).toString());
		assignbednotxt.setText(model.getValueAt(row, 2).toString());
		assigndoctortxt.setSelectedItem(model.getValueAt(row, 3));
		assignnursetxt.setSelectedItem(model.getValueAt(row, 4));
		assignwardtypetxt.setSelectedItem(model.getValueAt(row, 5));
		}
		if(source == tbl1) {
			int row1 = tbl1.rowAtPoint(e.getPoint());
			patientnametxt.setText(model1.getValueAt(row1, 1).toString());	
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
