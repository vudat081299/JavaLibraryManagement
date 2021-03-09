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

public class DetailForm extends JDialog {
	/**
	 * 
	 */
	/**
	 * @wbp.nonvisual location=50,111
	 */

	// UI
	JLabel name;
	JTextField nameInput;
	JLabel type;
	JTextField typeInput;
	JLabel author;
	JTextField authorInput;
	JLabel nxb;
	JTextField nxbInput;
	JLabel publisedhDate;
	JTextField publisedhDateInput;
	JLabel dataType;
	JTextField dataTypeInput;
	JButton addBookButton;
	JButton removeBookButton;

	JTextField searchInput;
	JButton searchButton;
	JButton comfirmBorrowButton;
	JButton addBookIntoCartButton;
	JButton removeBookFromCartButton;
	JButton doneSearchButton;
	JButton reload;

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
	String searchField;
	
	
	// info of DB
	String listColumn[] = {"form", "bookid", "name", "type", "author", "publisher", "publishedDate", "dataType", "state"}; // list column of DB
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
	boolean isSearching;
	String insertQuery;
	String removeQuery;
	String selectAllQuery;
	
	public DetailForm() {
		
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

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Chi tiết phiếu mượn - Vũ Quý Đạt 20176082");
		// initializers - not for array constants

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		isAddition = false;

		insertQuery = "insert into Book (id, bookid, name, type, author, publisher, publishedDate, dataType, state)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		removeQuery = "DELETE FROM Book WHERE id = ?";
		selectAllQuery = "select * from mapping WHERE form = " + StaffManagement.formID;
		
		// initializers - not for array constants
		
		// force call 
		prerequisiteSetting();
		renderUIElement();
		loadDataFromDB();
		

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
	};
		

		void loadDataFromDB() {
			while (model.getRowCount() > 0) {
				isAddition = true;
				model.removeRow(0);
			}
			try {
				// 1. Get a connection to database
				myConn = DriverManager.getConnection(url + tableName, username, password);
				
				// 2. Create a statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				myRs = myStmt.executeQuery(selectAllQuery);
				
				// 4. Process the result set
				while (myRs.next()) {

					isAddition = true; 
				    String a = myRs.getString("form");
				    String b = myRs.getString("bookid");
				    String c = myRs.getString("name");
				    String d = myRs.getString("type");
				    String e = myRs.getString("author");
				    String f = myRs.getString("publisher");
				    String g = myRs.getString("publishedDate");
				    String h = myRs.getString("dataType");
				    String i = myRs.getString("state");
				    model.addRow(new Object[]{a, b, c, d, e, f , g, h, i});

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
		name = new JLabel("Tên đầu sách");
		name.setBounds(10, 0, 100, 50);
		nameInput = new JTextField("", 5);
		nameInput.setBounds(130, 0, 200, 50);

		//
		type = new JLabel("Loại sách");
		type.setBounds(10, 50, 100, 50);
		typeInput = new JTextField("", 5);
		typeInput.setBounds(130, 50, 200, 50);
		
		//
		author = new JLabel("Tác giả");
		author.setBounds(10, 100, 100, 50);
		authorInput = new JTextField("", 5);
		authorInput.setBounds(130, 100, 200, 50);

		//
		nxb = new JLabel("Nhà xuất bản");
		nxb.setBounds(400, 0, 100, 50);
		nxbInput = new JTextField("", 5);
		nxbInput.setBounds(510, 0, 200, 50);

		//
		publisedhDate = new JLabel("Ngày xuất bản");
		publisedhDate.setBounds(400, 50, 100, 50);
		publisedhDateInput = new JTextField("", 5);
		publisedhDateInput.setBounds(510, 50, 200, 50);

		//
		dataType = new JLabel("Kiểu sách");
		dataType.setBounds(400, 100, 100, 50);
		dataTypeInput = new JTextField("", 5);
		dataTypeInput.setBounds(510, 100, 200, 50);

		// button
		addBookButton = new JButton("Thêm đầu sách vào thư viện");
		addBookButton.setBounds(800, 0, 225, 50);
		removeBookButton = new JButton("Xoá");
		removeBookButton.setBounds(1025, 0, 85, 50);
		
		//
		searchButton = new JButton("Tìm kiếm");
		searchButton.setBounds(800, 50, 90, 50);
		comfirmBorrowButton = new JButton("Giỏ sách (" + countSelectedBook + ")");
		comfirmBorrowButton.setBounds(1006, 50, 104, 50);
		doneSearchButton = new JButton("Tìm kiếm xong");
		doneSearchButton.setBounds(890, 50, 116, 50);
		
		//
		addBookIntoCartButton = new JButton("Thêm sách vào giỏ sách");
		addBookIntoCartButton.setBounds(800, 100, 225, 50);
		removeBookFromCartButton = new JButton("Bỏ chọn");
		removeBookFromCartButton.setBounds(1025, 100, 85, 50);
		
		// 
		
		errorLabel = new JLabel("", SwingConstants.CENTER);
		errorLabel.setBounds(10, 170, 1180, 50);
		errorLabel.setForeground(Color.RED);
		titleTable = new JLabel("Bảng danh sách các đầu sách của phiếu mượn");
		reload =  new JButton("Reload danh sách");
		reload.setBounds(1040, 220, 150, 30);
		titleTable.setBounds(10,-230,780,500);
		model = new DefaultTableModel(new Object[]{"Mã phiếu", "Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách", "Trạng thái"}, 0);
//		listSelectedItem = new DefaultTableModel(new Object[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách", "Trạng thái"}, 0);
	    jt = new JTable(model) {
	    	private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 7) {
	    	        return false;
				}
				return true;
	   	    }
	    };
	    jt.getTableHeader().setReorderingAllowed(false);
	    jt.setBounds(10,0,1180,500); 
	    jt.getColumnModel().getColumn(0).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(1).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(2).setPreferredWidth(60);
	    jt.getColumnModel().getColumn(3).setPreferredWidth(120);
	    jt.getColumnModel().getColumn(4).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(5).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(6).setPreferredWidth(20);
	    jt.getColumnModel().getColumn(7).setPreferredWidth(20);
	    jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    sp = new JScrollPane(jt); 
	    sp.setBounds(10,50,1180,440);

	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		ownerMark.setBounds(10,500,1180,30);
		
	    
//		add(name);
//		add(nameInput);
//		add(type);
//		add(typeInput);
//		add(author);
//		add(authorInput);
//		add(nxb);
//		add(nxbInput);
//		add(publisedhDate);
//		add(publisedhDateInput);
//		add(dataType);
//		add(dataTypeInput);
//	
//		add(addBookButton); // button
//		add(removeBookButton);
//		
//		add(searchButton);
//		add(comfirmBorrowButton);
//		add(doneSearchButton);
//		
//		add(addBookIntoCartButton);
//		add(removeBookFromCartButton);

		add(errorLabel);   
		add(titleTable);   
//		add(reload);   
	    add(sp); // scroll pane
	    add(ownerMark);
	    
	    // set centre header for table
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);         
//	    setVisible(true);  
	}

}
