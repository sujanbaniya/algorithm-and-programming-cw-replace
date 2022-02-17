package Hospital_Management;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class ShowLabReport extends JFrame implements MouseListener, ActionListener {

	JLabel titlelbl, titlelbl1, titlelbl2;
	JButton backbtn;
	String doctor_name;

	JLabel sp1;
	Border border;
	JTable tbl;
	DefaultTableModel model;

	Connection conn = null;

	ShowLabReport(String name) {
		doctor_name=name;
		setTitle("View Pateint Lab Report");
		setLayout(null);

		titlelbl = new JLabel("View Pateint Lab Report");
		titlelbl.setFont(new Font("StopD", Font.BOLD, 40));
		titlelbl1 = new JLabel("All Patients Lab Reports");
		titlelbl1.setFont(new Font("StopD", Font.BOLD, 25));
		titlelbl2 = new JLabel("View Lab Report");
		titlelbl2.setFont(new Font("StopD", Font.BOLD, 25));

		backbtn = new JButton("Back to dashboard");

		titlelbl.setBounds(460, 10, 1110, 60);
		titlelbl1.setBounds(180, 70, 1110, 60);
		titlelbl2.setBounds(880, 70, 1110, 60);

		backbtn.setBounds(580, 740, 200, 30);

		add(titlelbl);
		add(titlelbl1);
		add(titlelbl2);

		add(backbtn);

		sp1 = new JLabel();
		border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
		sp1.setBorder(border);

		String[] cols = { "Lab Report Id", "Patient Id", "Patient Name", "File Name", "Uploaded Date" };

		model = new DefaultTableModel(cols, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tbl = new JTable(model);

		// add jtable to scroll pane
		JScrollPane sp = new JScrollPane(tbl);
		sp.setBounds(40, 120, 600, 600);
		sp1.setBounds(680, 120, 600, 600);

		add(sp);
		add(sp1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1340, 820);
		setVisible(true);

		backbtn.addActionListener(this);

		tbl.addMouseListener(this);

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

		displaypatientlabreport();

	}

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(sp1.getWidth(), sp1.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public void displaypatientlabreport() {
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

				model.addRow(new Object[] { labreport_id, patient_id, patient_name, reportfile, added_date });
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	public static void main(String[] args) {
//		ShowLabReport f = new ShowLabReport("doc1");
//	}

	public void table_remove() {
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == backbtn) {
			DoctorDashboard f = new DoctorDashboard(doctor_name);
			dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tbl.rowAtPoint(e.getPoint());
		sp1.setIcon(ResizeImage(model.getValueAt(row, 3).toString()));

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