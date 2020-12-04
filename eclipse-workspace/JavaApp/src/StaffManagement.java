import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.DefaultTableCellRenderer;

import java.util.Arrays; 
//import java.util.ArrayList; 
//import java.util.List;
import java.util.Vector;

public class StaffManagement extends JDialog {
	private static final TableModelListener StaffManagement = null;
	int countRow = 0;

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	DefaultTableModel model = new DefaultTableModel(new String[]{"id", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Mã sách", "Tên sách", "Mã người mượn"}, 0);

    Vector<String> selectedCells = new Vector<String>();
	String listColumn[] = {"id", "giveBackIntentDate", "borrowedDate", "giveBackDate", "bookId", "nameBook", "readerId"};
	static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	
	String url;
	String username;
	String password;
	String tableName;
	
	JTable jt;
	boolean pressingCTRL=false;//flag, if pressing CTRL it is true, otherwise it is false.
	// UI
	JTextField bookIdInput;
	JTextField bookNameInput;
	JTextField readerIdInput;
	JTextField dateBorrow;
	JTextField dateReturnIntent;
	JTextField dateReturn;
	
	JLabel bookId;
	JLabel bookName;
	JLabel readerId;
	
	JButton searchButton;
	JButton doneSearchButton;
	JButton confirmReturnBook;
	
	boolean isSearching;
	String searchField;

	JLabel ownerMark;
	
	void loadData() {
//		model = null;
//		model = new DefaultTableModel(new String[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách"}, 0);

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from HireBookManagament");
			
			// 4. Process the result set
			while (myRs.next()) {

			    isSearching = true;
			    String a = myRs.getString("id");
			    String b = myRs.getString("giveBackIntentDate");
			    String c = myRs.getString("borrowedDate");
			    String d = myRs.getString("giveBackDate");
			    String e = myRs.getString("bookId");
			    String f = myRs.getString("nameBook");
			    String g = myRs.getString("readerId");
			    model.addRow(new Object[]{a, b, c, d, e, f, g});
			}
			countRow = model.getRowCount();

            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public StaffManagement() {

		setTitle("Quản lý phiếu mượn/trả");
	    jt = new JTable(model);

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		
		loadData();
		renderUI();

		//
		KeyListener tableKeyListener = (KeyListener) new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==157){//check if user is pressing CTRL key
					pressingCTRL=true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==157){//check if user released CTRL key
					pressingCTRL=false;
				}
			}
		};
		
		MouseListener tableMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("keyevent: " + e);
				if (pressingCTRL == true) { //check if user is pressing CTRL key
					int row = jt.rowAtPoint(e.getPoint()); //get mouse-selected row
					String newEntry = "" + row; //{row,col}=selected cell
					if (selectedCells.contains(newEntry)) {
						//cell was already selected, deselect it
						selectedCells.remove(newEntry);
//						print("is contain!");
					} else {
						//cell was not selected
						selectedCells.add(newEntry);
//						print("is not contain!");
					}
				} else {
//					print("is single select mode!");
				    selectedCells.clear();
					int row = jt.rowAtPoint(e.getPoint());
					String newEntry = "" + row;
				    selectedCells.add(newEntry);
				}
			    System.out.println("list cell selected" + selectedCells);
			}
		};
		jt.addKeyListener(tableKeyListener);
		jt.addMouseListener(tableMouseListener);

		doneSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = model.getRowCount() - 1; i > -1 ; i--) {
					isSearching = true;
					model.removeRow(i);
				}
				loadData();
			}
		});

		confirmReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = selectedCells.size() - 1; i > -1 ; i--) {
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection(url + tableName, username, password);
							
							// 2. Create a statement
							myStmt = myConn.createStatement();
							
									// the mysql insert statemen tmodel.setValueAt("Chọn", foo, 7);
							int foo = Integer.parseInt(selectedCells.get(i));
							Object idInHM = model.getValueAt(foo, 0);
							Object bookId = model.getValueAt(foo, 4);
						    String updateDateReturnQuery = "UPDATE HireBookManagament SET giveBackDate = '4/12/2020' WHERE id = " + idInHM;
						    String updateBookStateQuery = "UPDATE Book SET state = '' WHERE id = " + bookId;
								    // create the mysql insert preparedstatement
						    PreparedStatement preparedStmt = myConn.prepareStatement(updateDateReturnQuery);
						    PreparedStatement preparedStmt2 = myConn.prepareStatement(updateBookStateQuery);
//								    preparedStmt.setString (1, dateReturnBackInput.getText());
//								    preparedStmt.setString (2, dateBorrowInput.getText());
//								    preparedStmt.setString (3, model.getValueAt(j, 0).toString());
//								    preparedStmt.setString (4, model.getValueAt(j, 1).toString());
//								    preparedStmt.setString (5, readerIDInput.getText());
//					
//								    // execute the preparedstatement
//								    System.out.println(updateDateReturnQuery);
//								    PreparedStatement addBorrowBookForm = myConn.prepareStatement("UPDATE Book SET state = '" + 
//								    readerIDInput.getText() + "' WHERE id = " + model.getValueAt(j, 0));
//	
//								    System.out.println(addBorrowBookForm);
								    preparedStmt.execute();
								    preparedStmt2.execute();
//								    isAddition = true;
								    model.setValueAt("4/12/2020", foo, 3);
								
						    myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
				}
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = model.getRowCount() - 1; i > -1 ; i--) {
					isSearching = true;
					model.removeRow(i);
				}
				
				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					getSearchText();
					// the mysql insert statement
				    String query = "SELECT * FROM HireBookManagament WHERE " + searchField + " LIKE " + getSearchText();

					System.out.println(query);
					// 3. Execute SQL query
					myRs = myStmt.executeQuery("select * from HireBookManagament WHERE " + searchField + " LIKE '" + getSearchText() + "'");// 4. Process the result set
					while (myRs.next()) {
						isSearching = true;
					    String a = myRs.getString("id");
					    String b = myRs.getString("giveBackIntentDate");
					    String c = myRs.getString("borrowedDate");
					    String d = myRs.getString("giveBackDate");
					    String ee = myRs.getString("bookId");
					    String f = myRs.getString("nameBook");
					    String g = myRs.getString("readerId");
					    model.addRow(new Object[]{a, b, c, d, ee, f, g});

					}
					countRow = model.getRowCount();

		            myConn.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
		
		//
		dateBorrow = new JTextField("", 5);
		dateBorrow.setBounds(160, 0, 200, 50);
		add(dateBorrow);

		JLabel name = new JLabel("Ngày mượn");
		name.setBounds(10, 0, 100, 50);
		add(name);
		//
		dateReturnIntent = new JTextField("", 5);
		dateReturnIntent.setBounds(160, 50, 200, 50);
		add(dateReturnIntent);

		JLabel type = new JLabel("Ngày hẹn trả");
		type.setBounds(10, 50, 100, 50);
		add(type);
		
		//
		dateReturn = new JTextField("", 5);
		dateReturn.setBounds(510, 0, 200, 50);
		add(dateReturn);

		JLabel nxb = new JLabel("Ngày trả");
		nxb.setBounds(370, 0, 100, 50);
		add(nxb);

//		JButton addBookButton = new JButton("Đăng kí nhân viên");
//		addBookButton.setBounds(300, 150, 200, 50);
//		addBookButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					// 1. Get a connection to database
//					myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
//					
//					// 2. Create a statement
//					myStmt = myConn.createStatement();
//					
//					  // the mysql insert statement
//				      String query = " insert into HireBookManagament (id, name, dateOfBirth, phonenumber)"
//				        + " values (?, ?, ?, ?)";
//
//			      // create the mysql insert preparedstatement
//			      PreparedStatement preparedStmt = myConn.prepareStatement(query);
//			      preparedStmt.setInt    (1, ++countRow);
//			      preparedStmt.setString (2, nameInput.getText());
//			      preparedStmt.setString (3, typeInput.getText());
//			      preparedStmt.setString (4, nxbInput.getText());
//
//			      // execute the preparedstatement
//			      preparedStmt.execute();
//			      
//			      myConn.close();
//
////					loadData();
////					model.addRow(new Object[]{1, "Column 2", "Column 3", "Column 3", "Column 3"});
//				    model.addRow(new Object[]{countRow, nameInput.getText(), typeInput.getText(), nxbInput.getText()});
//				} catch (Exception exc) {
//					exc.printStackTrace();
//				}
//			}
//		});

//		add(addBookButton);
		

		ownerMark.setBounds(10,700,1180,30);
		confirmReturnBook.setBounds(560, 150, 150, 50);
		doneSearchButton.setBounds(470, 150, 50, 50);
		readerId.setBounds(10, 100, 100, 50);
		bookIdInput.setBounds(510, 50, 200, 50);
		JLabel titleTable = new JLabel("Bảng danh sách mượn trả");
		titleTable.setBounds(10,-20,1180,500);
		add(titleTable);    
	    jt.setBounds(10,250,1180,500);          
	    jt.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub

				if (isSearching) {
					isSearching = false;
				} else {

			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        if (column == 0) {
			        	return;
			        }
			        
			        String sqlupdate = "UPDATE HireBookManagament SET " + listColumn[column] + " = '" + model.getValueAt(row, column) + "' WHERE id = " + model.getValueAt(row, 0);
			        
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection(url + tableName, username, password);
							
							// 2. Create a statement
							myStmt = myConn.createStatement();
							
				            PreparedStatement pst = myConn.prepareStatement(sqlupdate);
				            pst.executeUpdate();
	
							// force call last
				            myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
				}
			}
        });
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);
	    JScrollPane sp = new JScrollPane(jt); 
	    sp.setBounds(10,250,1180,440);      
	    add(sp);             
//	    setVisible(true);    
	}
	
	String getSearchText() {
		if (bookIdInput.getText() != null && !bookIdInput.getText().contentEquals("")) {
			searchField = "bookId";
			return bookIdInput.getText();
		} else if (bookNameInput.getText() != null && !bookNameInput.getText().contentEquals("")) {
			searchField = "nameBook";
			return bookNameInput.getText();
		} else if (readerIdInput.getText() != null && !readerIdInput.getText().contentEquals("")) {
			searchField = "readerId";
			return readerIdInput.getText();
		} else if (dateBorrow.getText() != null && !dateBorrow.getText().contentEquals("")) {
			searchField = "borrowedDate";
			return dateBorrow.getText();
		} else if (dateReturnIntent.getText() != null && !dateReturnIntent.getText().contentEquals("")) {
			searchField = "giveBackDate";
			return dateReturnIntent.getText();
		} else if (dateReturn.getText() != null && !dateReturn.getText().contentEquals("")) {
			searchField = "giveBackDate";
			return dateReturn.getText();
		}
		return "";
	}
	
	void renderUI () {

		bookIdInput = new JTextField("", 5);
		bookNameInput = new JTextField("", 5);
		readerIdInput = new JTextField("", 5);

		bookId = new JLabel("Mã sách");
		bookName = new JLabel("Tên sách");
		readerId = new JLabel("Mã độc giả");
		
		bookNameInput.setBounds(510, 100, 200, 50);
		readerIdInput.setBounds(160, 100, 200, 50);
		bookId.setBounds(370, 50, 100, 50);
		bookName.setBounds(370, 100, 100, 50);
		
		searchButton = new JButton("Tìm kiếm");
		searchButton.setBounds(370, 150, 100, 50);
		doneSearchButton = new JButton("Xong");
		doneSearchButton.setBounds(470, 150, 50, 50);
		confirmReturnBook = new JButton("Xác nhận trả sách");
		
		add(bookIdInput);
		add(bookNameInput);
		add(readerIdInput);
		add(bookId);
		add(bookName);
		add(readerId);
		add(searchButton);
		add(doneSearchButton);
		add(confirmReturnBook);

	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		add(ownerMark);
	}

}
