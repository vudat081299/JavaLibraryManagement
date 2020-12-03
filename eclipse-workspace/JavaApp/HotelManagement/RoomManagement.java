import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//import javax.swing.table.TableModel;
//import javax.swing.table.DefaultTableCellRenderer;

//import java.util.Arrays; 
//import java.util.ArrayList; 
//import java.util.List;

import java.sql.*;
import java.util.Vector;
//import java.util.*; 
//import java.util.Arrays; 
import java.util.Collections;
//import java.util.Comparator; 

public class RoomManagement extends JDialog {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// variables, definitions
//	private static final TableModelListener InsertBookForm = null;
	
	// UI
	JLabel name;
	JTextField nameInput;
	JLabel type;
	JTextField typeInput;
	JLabel author;
	JTextField authorInput;
	JButton addBookButton;
	JButton removeBookButton;

	JTextField searchInput;
	JButton searchButton;
	JButton comfirmBorrowButton;
	JButton addBookIntoCartButton;
	JButton removeBookFromCartButton;
	JButton returnBookButton;

	JLabel errorLabel;
	JLabel titleTable;
	JScrollPane sp;
	JTable jt;
	JLabel ownerMark;
	
	// init default data for variables, definitions
	boolean pressingCTRL=false;//flag, if pressing CTRL it is true, otherwise it is false.
//	Vector<int[]> selectedCells = new Vector<int[]>();
//	Vector<int> selectedCells = new Vector<int>();
    Vector<String> selectedCells = new Vector<String>();

	static DefaultTableModel model;
	int countRow = 0; // total row of table 
	int countSelectedBook = 0;
	
	
	// info of DB
	String listColumn[] = {"id", "name", "nameTypeRoom", "description"}; // list column of DB
	String url;
	String username;
	String password;
	String tableName;
	// DB instance
	Connection myConn;
	Statement myStmt;
	ResultSet myRs;
	
	boolean isAddition;
	boolean isRemoval;
	boolean isSelectedBook;
	boolean isRemoveBookFromCart;
	String insertQueryRM;
	String insertQueryTRM;
	String removeQuery;
	String selectAllQueryRM;
	String selectAllQueryTRM;
	
	public RoomManagement() {

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Quản lý loại phòng khách sạn");
		// initializers - not for array constants

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "HotelManagement";
		isAddition = false;

		insertQueryRM = "insert into RoomManagement (id, name, nameTypeRoom, description)"
					+ " values (?, ?, ?, ?)";
		insertQueryTRM = "insert into TypeRoomManagement (id, name, price, description)"
				+ " values (?, ?, ?, ?)";
		removeQuery = "DELETE FROM RoomManagement WHERE id = ?";
		selectAllQueryRM = "select * from RoomManagement";
		selectAllQueryTRM = "select * from TypeRoomManagement";
		
		// force call 
		prerequisiteSetting();
		renderUIElement();
		loadDataFromDB();
		
		//
		KeyListener tableKeyListener = (KeyListener) new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    isRemoval = true;
				if(e.getKeyCode()==157){//check if user is pressing CTRL key
					pressingCTRL=true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			    isRemoval = true;
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
				    isRemoval = true;
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
				    isRemoval = false;
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
		
		//
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validate("")) {
					System.out.print(validate(""));
					return;
				}

				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection(url + tableName, username, password);
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					
					// the mysql insert statement
				    String query = insertQueryTRM;

				    // create the mysql insert preparedstatement
				    PreparedStatement preparedStmt = myConn.prepareStatement(query);
				    preparedStmt.setInt    (1, nextCreatingIDBook());
				    preparedStmt.setString (2, nameInput.getText());
				    preparedStmt.setString (3, typeInput.getText());
				    preparedStmt.setString (4, authorInput.getText());
	
				    // execute the preparedstatement
				    System.out.println(preparedStmt);
				    preparedStmt.execute();
				    isAddition = true;
				    model.addRow(new Object[]{nextCreatingIDBook(), nameInput.getText(), typeInput.getText(), authorInput.getText()});	

					// force call last
				    myConn.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
	    jt.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
            	print("---------");
				System.out.println(e);
				if (isAddition || isRemoval || isSelectedBook || isRemoveBookFromCart) {
					print("can not edit row!");
					isAddition = false;
					isRemoval = false;
					isSelectedBook = false;
					isRemoveBookFromCart = false;
				} else {
					// TODO Auto-generated method stub
					print("editting row!");
			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        if (column == 0) {
			        	return;
			        }
			        
			        String sqlupdate = "UPDATE Book SET " + listColumn[column] + " = '" + model.getValueAt(row, column) + "' WHERE id = " + model.getValueAt(row, 0);
			        print(sqlupdate);
					if(column != 0) {
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
			}
        });
	}
	


	// methods
	int nextCreatingIDBook () {
		if (model.getRowCount() == 0) {
			return 1;
		}
		return Integer.parseInt(model.getValueAt(model.getRowCount() - 1, 0).toString()) + 1;
	}
	
	public boolean validate(String string) {
	    if (nameInput.getText().length() == 0 || 
	    		typeInput.getText().length() == 0 || 
	    		authorInput.getText().length() == 0) {
	    	print("Validate false!");
	    	return false;
	    } else {
	    	print("Validate true!");
			return true;
	    }
	}

	void print(String string) {
		System.out.println(string);
	}
	void loadDataFromDB() {
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(url + tableName, username, password);
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(selectAllQueryTRM);
			
			// 4. Process the result set
			while (myRs.next()) {
			    String a = myRs.getString("id");
			    String b = myRs.getString("name");
			    String c = myRs.getString("price");
			    String d = myRs.getString("description");
			    model.addRow(new Object[]{a, b, c, d});

			}
			countRow = model.getRowCount();
			
			// force call last
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
	
	void renderUIElement() {
		//
		name = new JLabel("Tên loại phòng");
		name.setBounds(10, 0, 100, 50);
		nameInput = new JTextField("", 5);
		nameInput.setBounds(130, 0, 200, 50);

		//
		type = new JLabel("Giá loại phòng");
		type.setBounds(10, 50, 100, 50);
		typeInput = new JTextField("", 5);
		typeInput.setBounds(130, 50, 200, 50);
		
		//
		author = new JLabel("Mô tả phòng");
		author.setBounds(10, 100, 100, 50);
		authorInput = new JTextField("", 5);
		authorInput.setBounds(130, 100, 200, 50);

//		//
//		nxb = new JLabel("Nhà xuất bản");
//		nxb.setBounds(400, 0, 100, 50);
//		nxbInput = new JTextField("", 5);
//		nxbInput.setBounds(510, 0, 200, 50);
//
//		//
//		publisedhDate = new JLabel("Ngày xuất bản");
//		publisedhDate.setBounds(400, 50, 100, 50);
//		publisedhDateInput = new JTextField("", 5);
//		publisedhDateInput.setBounds(510, 50, 200, 50);
//
//		//
//		dataType = new JLabel("Kiểu sách");
//		dataType.setBounds(400, 100, 100, 50);
//		dataTypeInput = new JTextField("", 5);
//		dataTypeInput.setBounds(510, 100, 200, 50);

		// button
		addBookButton = new JButton("Thêm loại phòng");
		addBookButton.setBounds(400, 0, 225, 50);
		removeBookButton = new JButton("Xoá loại phòng");
		removeBookButton.setBounds(400, 50, 225, 50);
		
		// 
		
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(10, 170, 1180, 50);
		errorLabel.setForeground(Color.RED);
		titleTable = new JLabel("Bảng danh sách loại phòng trong khách sạn");
		titleTable.setBounds(10,-20,780,500);
		model = new DefaultTableModel(new Object[]{"ID Loại phòng", "Tên loại phòng", "Mô tả", "Giá"}, 0);
//		listSelectedItem = new DefaultTableModel(new Object[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách", "Trạng thái"}, 0);
	    jt = new JTable(model);    
	    jt.setBounds(10,250,1180,500); 
//	    jt.getColumnModel().getColumn(0).setPreferredWidth(20);
//	    jt.getColumnModel().getColumn(1).setPreferredWidth(120);
//	    jt.getColumnModel().getColumn(2).setPreferredWidth(60);
//	    jt.getColumnModel().getColumn(3).setPreferredWidth(120);
//	    jt.getColumnModel().getColumn(4).setPreferredWidth(20);
//	    jt.getColumnModel().getColumn(5).setPreferredWidth(20);
//	    jt.getColumnModel().getColumn(6).setPreferredWidth(20);
//	    jt.getColumnModel().getColumn(7).setPreferredWidth(20);
	    jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    sp = new JScrollPane(jt); 
	    sp.setBounds(10,250,1180,440);

	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		ownerMark.setBounds(10,700,1180,30);
	    
		add(name);
		add(nameInput);
		add(type);
		add(typeInput);
		add(author);
		add(authorInput);
//		add(nxb);
//		add(nxbInput);
//		add(publisedhDate);
//		add(publisedhDateInput);
//		add(dataType);
//		add(dataTypeInput);
	
		add(addBookButton); // button
		add(removeBookButton);
		
		add(errorLabel);   
		add(titleTable);   
	    add(sp); // scroll pane
	    add(ownerMark);
	    
	    // set centre header for table
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);         
//	    setVisible(true);    
	}

}
