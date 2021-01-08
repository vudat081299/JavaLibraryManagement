import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import java.util.Vector;

import javax.swing.JOptionPane;

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
	JLabel readername;
	JTextField readerIDInput;
	JTextField readernameInput;
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

    Vector<String> selectedCells = new Vector<String>();

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
	String getLatestFormQuery;
	String getLatestMapQuery;
	String insertMappingQeury;
	
	public RentManagement() {
		
//		addWindowListener(new java.awt.event.WindowAdapter() {
//		    @Override
//		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//		        if (JOptionPane.showConfirmDialog(
//		            "Are you sure you want to close this window?", "Close Window?",
//		            JOptionPane.YES_NO_OPTION,
//		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//				    System.out.println("-----");
//		        }
//		    }
//		});

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		isAddition = false;

		insertQuery = "insert into Form (id, nameReader, idReader, dateBorrow, dateReturn, didReturn, sum)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
		insertMappingQeury = "insert into mapping (id, form, bookid, name, type, author, publisher, publishedDate, dataType, state)" 
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		removeQuery = "DELETE FROM HireBookManagament WHERE id = ?";
		getLatestFormQuery = "SELECT * FROM Form";
		getLatestMapQuery = "SELECT * FROM mapping";

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Quản lý mượn trả");
		// initializers - not for array constants
		
		// force call 
		prerequisiteSetting();
		renderUIElement();
		prepareData();
		loadDataReader();

		MouseListener tableMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isRemoval = false;
//				print("is single select mode!");
				selectedCells.clear();
				int row = jt.rowAtPoint(e.getPoint());
				String newEntry = "" + row;
				selectedCells.add(newEntry);
			}
		};
		jt.addMouseListener(tableMouseListener);
		
		removeAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int cellIndex = 0;
		    	String cellIndexString = "";
		    	cellIndex = Integer.parseInt(selectedCells.get(0));
		    	cellIndexString = selectedCells.get(0);
		    	int foo;
		    	try {
		    	   foo = Integer.parseInt(cellIndexString);
		    	}
		    	catch (NumberFormatException er)
		    	{
		    	   foo = 0;
		    	}
		    	
		    	if (model.getValueAt(foo, 7) == null || model.getValueAt(foo, 7).equals("")) {
		    	} else if (model.getValueAt(foo, 7).equals("Chọn")) {
		    		isRemoveBookFromCart = true;
					model.setValueAt("", foo, 7);
		    	}
			}
    	});

		confirmBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String formID = getNextID();
			    System.out.println(")))");
				for (int i = reader.getRowCount() - 1; i > -1 ; i--) {
					if (((String) reader.getValueAt(i, 0)).equals(readerIDInput.getText())) {
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection(url + tableName, username, password);
							
							// 2. Create a statement
							myStmt = myConn.createStatement();

									// the mysql insert statement
								    String query = insertQuery;
								    // create the mysql insert preparedstatement
								    PreparedStatement preparedStmt = myConn.prepareStatement(query);
								    preparedStmt.setString (1, formID);
								    preparedStmt.setString (2, readernameInput.getText());
								    preparedStmt.setString (3, readernameInput.getText());
								    preparedStmt.setString (4, dateBorrowInput.getText());
								    preparedStmt.setString (5, dateReturnBackInput.getText());
								    preparedStmt.setString (6, "Chưa trả");
								    preparedStmt.setInt (7, model.getRowCount());
								    preparedStmt.execute();
					
							for (int j = 0; j < model.getRowCount(); j++) {
								if (model.getValueAt(j, 7).equals("Chọn")) {

									myConn = DriverManager.getConnection(url + tableName, username, password);
									myStmt = myConn.createStatement();
								    // execute the preparedstatement
								    System.out.println(readerIDInput.getText());
								    PreparedStatement addBorrowBookForm = myConn.prepareStatement("UPDATE Book SET state = 'phiếu mượn id: " + formID + "' WHERE id = " + model.getValueAt(j, 0));
	
								    System.out.println(addBorrowBookForm);
								    addBorrowBookForm.execute();
								    isAddition = true;
								    model.setValueAt(readerIDInput.getText(), j, 7);
				
								    
									PreparedStatement addMapping = myConn.prepareStatement(insertMappingQeury);

								    
								    myRs = myStmt.executeQuery("select * from Book WHERE id = " + model.getValueAt(j, 0));
								    String a = "";
								    String b = "";
								    String c = "";
								    String d = "";
								    String ee = "";
								    String f = "";
								    String g = "";
								    String h = "";
								    while (myRs.next()) {
									     a = myRs.getString("id");
									     b = myRs.getString("name");
									     c = myRs.getString("type");
									     d = myRs.getString("author");
									     ee = myRs.getString("publisher");
									     f = myRs.getString("publishedDate");
									     g = myRs.getString("dataType");
									     h = myRs.getString("state");
									}

								    System.out.println(a);
								    System.out.println(b);
								    addMapping.setString (1, getNextIDMap());
								    addMapping.setString (2, formID);
								    addMapping.setString (3, a);
								    addMapping.setString (4, b);
								    addMapping.setString (5, c);
								    addMapping.setString (6, d);
								    addMapping.setString (7, ee);
								    addMapping.setString (8, f);
								    addMapping.setString (9, g);
								    addMapping.setString (10, h);
								    addMapping.execute();
								    
								    System.out.println(addMapping);
//								    insertMappingQeury
								    
								    
								    
								    

								}
							} // force call last
						    myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				}
				dispose();
//				if (!validate("")) {
//					System.out.print(validate(""));
//					return;
//				}
				
			}
		});
	}
	
	
	String getNextIDMap () {
		String a = "";

		System.out.print("___+_+");
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(getLatestMapQuery);
			
			// 4. Process the result set
			while (myRs.next()) {
			    a = myRs.getString("id");
			}
			if (a == null || a == "") {
				a = "1";
				System.out.println(a);
			} else {
				a = (Integer.parseInt(a) + 1) + "";
			}
			System.out.print(a);
			System.out.print(myRs.next());

            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return a;
	}
	
	String getNextID () {
		String a = "";

		System.out.print("___+_+");
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(getLatestFormQuery);
			
			// 4. Process the result set
			while (myRs.next()) {
			    a = myRs.getString("id");
			}
			if (a == null || a == "") {
				a = "1";
				System.out.println(a);
			} else {
				a = (Integer.parseInt(a) + 1) + "";
			}
			System.out.print(a);
			System.out.print(myRs.next());

            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		return a;
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
		readername = new JLabel("Tên độc giả");
		readername.setBounds(400, 50, 100, 50);
		readernameInput = new JTextField("", 5);
		readernameInput.setBounds(510, 50, 200, 50);

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
		removeBook = new JButton("");
		removeBook.setBounds(1025, 50, 85, 50);
		removeAll = new JButton("Huỷ chọn sách");
		removeAll.setBounds(800, 100, 225, 50);
		
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
		add(readername);
		add(readernameInput);
		add(dateBorrow);
		add(dateBorrowInput);
		add(dateReturnBack);
		add(dateReturnBackInput);
	
		add(confirmBorrow); // button
//		add(removeBook);
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
