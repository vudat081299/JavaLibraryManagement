import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.util.Random;

public class RentManagement extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 974604795306221196L;
	/**
	 * @wbp.nonvisual location=50,111
	 */

	// UI
	JLabel formTitle;
	JLabel readerID;
	JTextField readerIDInput;
	JLabel dateBorrow;
	JTextField dateBorrowInput;
	JLabel dateReturnBack;
	JTextField dateReturnBackInput;
	JLabel amount;
	JTextField amountInput;

	JTextField searchInput;
	JButton confirmBorrow;
	JButton removeBook;
	JButton removeAll;
	
	JLabel errorLabel;
	JLabel titleTable;
	JScrollPane sp;
	JTable jt;
	

	DefaultTableModel model;

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	DefaultTableModel reader = new DefaultTableModel(new String[]{"Mã độc giả"}, 0);
	
	String url;
	String username;
	String password;
	String tableName;
	boolean isAddition;
	boolean isRemoval;
	boolean isSelectedBook;
	boolean isRemoveBookFromCart;
	String insertQuery;
	String removeQuery;
	String selectAllQuery;
	
	public RentManagement() {

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		isAddition = false;

		insertQuery = "insert into HireBookManagament (giveBackIntentDate, borrowedDate, bookId, readerId)"
					+ " values (?, ?, ?, ?)";
		removeQuery = "DELETE FROM HireBookManagament WHERE id = ?";
		selectAllQuery = "select * from HireBookManagament";

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Quản lý mượn trả");
		// initializers - not for array constants
		
		// force call 
		prerequisiteSetting();
		renderUIElement();
		prepareData();
		loadDataReader();

		confirmBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    System.out.println("-----");
				for (int i = reader.getRowCount() - 1; i > -1 ; i--) {
					if (((String) reader.getValueAt(i, 0)).equals(readerIDInput.getText())) {

					    System.out.println("--+++---");
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection(url + tableName, username, password);
							
							// 2. Create a statement
							myStmt = myConn.createStatement();
							
							for (int j = 0; j < model.getRowCount(); j++) {
								// the mysql insert statement
							    String query = insertQuery;
							    System.out.println("--+++---" + j);
							    // create the mysql insert preparedstatement
							    PreparedStatement preparedStmt = myConn.prepareStatement(query);
							    preparedStmt.setString (1, dateReturnBackInput.getText());
							    preparedStmt.setString (2, dateBorrowInput.getText());
							    preparedStmt.setString (3, model.getValueAt(j, 0).toString());
							    preparedStmt.setString (4, readerIDInput.getText());
				
							    // execute the preparedstatement
							    System.out.println(preparedStmt);
							    PreparedStatement addBorrowBookForm = myConn.prepareStatement("UPDATE Book SET state = '" + 
							    readerIDInput.getText() + "' WHERE id = " + model.getValueAt(j, 0));

							    System.out.println(addBorrowBookForm);
							    preparedStmt.execute();
							    addBorrowBookForm.execute();
							    isAddition = true;
							    model.setValueAt(readerIDInput.getText(), j, 7);
							}// force call last
						    myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				}
//				if (!validate("")) {
//					System.out.print(validate(""));
//					return;
//				}
				
			}
		});
	}
	
	void loadDataReader() {
//		model = null;
//		model = new DefaultTableModel(new String[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách"}, 0);
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from Reader");
			
			// 4. Process the result set
			while (myRs.next()) {
			    String a = myRs.getString("id");
			    reader.addRow(new Object[]{a});

			}

            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	// prerequisite setting for this dialog type view
	void prerequisiteSetting() {
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
	}
	
	void prepareData () {
//	    Object[] columnData = new Object[jt.getRowCount()];  // One entry for each row
//	    Object[] rowData = new Object [jt.getRowCount()];
	    for (int i = 0; i < InsertBookForm.model.getRowCount(); i++) {  // Loop through the rows
	        // Record the 5th column value (index 4)
//	    	stateText = (String) jt.getValueAt(i, 7);  

        	if (InsertBookForm.model.getValueAt(i, 7) == null || InsertBookForm.model.getValueAt(i, 7).equals("Đã được mượn") || InsertBookForm.model.getValueAt(i,  7).equals("")) {
        		System.out.println("000000");
        	} else if (InsertBookForm.model.getValueAt(i, 7).equals("Chọn")) {
        		System.out.println("111111");
			    int a = Integer.parseInt(InsertBookForm.model.getValueAt(i, 0).toString());
			    String b = (String) InsertBookForm.model.getValueAt(i, 1);
			    String c = (String) InsertBookForm.model.getValueAt(i, 2);
			    String d = (String) InsertBookForm.model.getValueAt(i, 3);
			    String e = (String) InsertBookForm.model.getValueAt(i, 4);
			    String f = (String) InsertBookForm.model.getValueAt(i, 5);
			    String g = (String) InsertBookForm.model.getValueAt(i, 6);
			    String h = (String) InsertBookForm.model.getValueAt(i, 7);
        		model.addRow(new Object[]{a, b, c, d, e, f ,g ,h});	
        	}
	     }
	}
	
	void renderUIElement() {

		formTitle = new JLabel("Form nhập thông tin mượn sách");
		formTitle.setBounds(10, 0, 1180, 50);
		//
		readerID = new JLabel("Mã độc giả");
		readerID.setBounds(10, 50, 100, 50);
		readerIDInput = new JTextField("", 5);
		readerIDInput.setBounds(130, 50, 200, 50);

		//
		dateBorrow = new JLabel("Ngày mượn");
		dateBorrow.setBounds(10, 100, 100, 50);
		dateBorrowInput = new JTextField("", 5);
		dateBorrowInput.setBounds(130, 100, 200, 50);

		//
		dateReturnBack = new JLabel("Ngày hẹn trả");
		dateReturnBack.setBounds(400, 100, 100, 50);
		dateReturnBackInput = new JTextField("", 5);
		dateReturnBackInput.setBounds(510, 100, 200, 50);
		
		//
		confirmBorrow = new JButton("Xác nhận mượn sách");
		confirmBorrow.setBounds(800, 50, 225, 50);
		removeBook = new JButton("Xoá");
		removeBook.setBounds(1025, 50, 85, 50);
		removeAll = new JButton("Huỷ mượn sách");
		removeAll.setBounds(800, 100, 310, 50);
		
		// 
		
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(10, 220, 1180, 50);
		errorLabel.setForeground(Color.RED);
		titleTable = new JLabel("Bảng danh sách các đầu sách đã chọn");
		titleTable.setBounds(10,-20,780,500);
		model = new DefaultTableModel(new Object[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách", "Trạng thái"}, 0);
//		model = cloneModel();
	    jt = new JTable(model);    
	    jt.setBounds(10,250,1180,500); 
	    jt.getColumnModel().getColumn(0).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(1).setPreferredWidth(120);
	    jt.getColumnModel().getColumn(2).setPreferredWidth(60);
	    jt.getColumnModel().getColumn(3).setPreferredWidth(120);
	    jt.getColumnModel().getColumn(4).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(5).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(6).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(7).setPreferredWidth(20);
	    jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    sp = new JScrollPane(jt); 
	    sp.setBounds(10,250,1180,450);

		add(formTitle);   
		add(readerID);
		add(readerIDInput);
		add(dateBorrow);
		add(dateBorrowInput);
		add(dateReturnBack);
		add(dateReturnBackInput);
	
		add(confirmBorrow); // button
		add(removeBook);
		add(removeAll);

		add(errorLabel);   
		add(titleTable);   
	    add(sp); // scroll pane
	    
	    // set centre header for table
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);         
//	    setVisible(true);    
	}	
	
	DefaultTableModel cloneModel () {
		return InsertBookForm.model;
	}

}
