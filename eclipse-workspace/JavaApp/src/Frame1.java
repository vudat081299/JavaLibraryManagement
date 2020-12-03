import java.awt.EventQueue;
//import com.sql,DriverManager;
//import com.mysql.jdbc.Connection;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame1 {
//	public static 
	private JFrame frame;
	static int week = 0;
	boolean renderLibraryUI = true;
	int app = 1; // 0 is homework | 1 is Library Management | 2 is Hotel Management
	String titleOfAppFrame = "Quản lý thư viện";
	String titleOfBookManagementFrame = "Quản lý đầu sách";
	String titleOfReaderManagementFrame = "Quản lý độc giả";
	String titleOfRentingBookFrame = "Quản lý mượn/trả sách";
	
	String titleHotelManagement = "Quản lý khách sạn";
	String titleRoomManagement = "Quản lý phòng khách sạn";
	String titleBookingRoomManagement = "Quản lý đặt phòng ";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		Connection myConn = null;
//		Statement myStmt = null;
//		ResultSet myRs = null;
//		
//		try {
//			// 1. Get a connection to database
//			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
//			
//			// 2. Create a statement
//			myStmt = myConn.createStatement();
//			
//			// 3. Execute SQL query
//			myRs = myStmt.executeQuery("select * from Book");
//			
//			// 4. Process the result set
//			while (myRs.next()) {
//				System.out.println(myRs.getString("name") + ", " + myRs.getString("author"));
//			}
//		}
//		catch (Exception exc) {
//			exc.printStackTrace();
//		}

		// uncomment block below to run UI execute 50 exercise Java
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		if (app == 0) {
			initialize();
		} else if (app == 1) {
			initLibraryManagementUI();
		} else if (app == 2) {
			initHotelManagementProject();
		}
	}
	
	private void initHotelManagementProject () {
		frame = new JFrame();
		frame.setBounds(455, 150, 750, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(titleHotelManagement);

		JButton roomManagementButton = new JButton(titleRoomManagement);
		roomManagementButton.setBounds(50, 50, 300, 200);
		roomManagementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomManagement week1 = new RoomManagement();
				week1.pack();
				week1.setBounds(230, 150, 1200, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(roomManagementButton);
		
		JButton bookingRoomManagementButton = new JButton(titleBookingRoomManagement);
		bookingRoomManagementButton.setBounds(400, 50, 300, 200);
		bookingRoomManagementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomManagement week1 = new RoomManagement();
				week1.pack();
				week1.setSize(800, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().add(bookingRoomManagementButton);
		
		JLabel ownerMark = new JLabel();
	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		ownerMark.setBounds(10, 500, 750, 30);
		frame.getContentPane().add(ownerMark);
	}
	
	private void initLibraryManagementUI() {
		frame = new JFrame();
		frame.setBounds(455, 150, 750, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(titleOfAppFrame);
		
		JButton insertBookFormButton = new JButton(titleOfBookManagementFrame);
		insertBookFormButton.setBounds(50, 50, 300, 200);
		insertBookFormButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertBookForm week1 = new InsertBookForm();
				week1.pack();
				week1.setBounds(230, 150, 1200, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(insertBookFormButton);
		

		JButton insertReaderButton = new JButton(titleOfReaderManagementFrame);
		insertReaderButton.setBounds(400, 50, 300, 200);
		insertReaderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertReader week1 = new InsertReader();
				week1.pack();
				week1.setSize(1400, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().add(insertReaderButton);

		JButton rentingManagementButton = new JButton(titleOfRentingBookFrame);
		rentingManagementButton.setBounds(50, 300, 300, 200);
		rentingManagementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffManagement week1 = new StaffManagement();
				week1.pack();
				week1.setSize(800, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().add(rentingManagementButton);
		
		JLabel ownerMark = new JLabel();
	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		ownerMark.setBounds(10, 500, 750, 30);
		frame.getContentPane().add(ownerMark);
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 131);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnNewButton = new JButton("Bài tập Java tuần 1");
		btnNewButton.setBounds(0, 60, 200, 50);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				week = 1;
				Week1Dialog week1 = new Week1Dialog();
				week1.pack();
				week1.setSize(800, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Bài tập Java tuần 2");
		btnNewButton_1.setBounds(200, 60, 200, 50);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				week = 2;
				Week1Dialog week1 = new Week1Dialog();
				week1.pack();
				week1.setSize(800, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Bài tập Java tuần 3");
		btnNewButton_2.setBounds(400, 60, 200, 50);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				week = 3;
				Week1Dialog week1 = new Week1Dialog();
				week1.pack();
				week1.setSize(800, 800);
				week1.setVisible(true);
			}
		});
		frame.getContentPane().add(btnNewButton_2);

		JLabel infoStudentName = new JLabel("Vũ Quý Đạt - MSSV: 20176082");
		infoStudentName.setBounds(10, -20, 600, 80);
		frame.getContentPane().add(infoStudentName);
		JLabel infoStudentClass = new JLabel("Lớp: Vuwit16b");
		infoStudentClass.setBounds(10, 0, 600, 80);
		frame.getContentPane().add(infoStudentClass);
	}
}
