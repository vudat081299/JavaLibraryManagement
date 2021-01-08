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

import java.util.Arrays; 
//import java.util.ArrayList; 
//import java.util.List;

import java.sql.*;
import java.util.Vector;
//import java.util.*; 
//import java.util.Arrays; 
import java.util.Collections;
//import java.util.Comparator; 

public class InsertReader extends JDialog {
	private static final TableModelListener InsertReader = null;
	
	// UI
	JTextField nameInput;
	JTextField phonenumberInput;
	JTextField birthInput;
	JTextField cmndInput;
	JTextField addressInput;
	JTextField idCardInput;
	JTextField expireInput;

	JButton addReaderButton;
	JButton removeReaderButton;
	
	// UI search
	JLabel titleSearch;
	
	JTextField nameInputSearch;
	JTextField phonenumberInputSearch;
	JTextField birthInputSearch;
	JTextField cmndInputSearch;
	JTextField idCardInputSearch;
	JTextField expireInputSearch;

	JLabel nameSearch;
	JLabel phonenumberSearch;
	JLabel birthSearch;
	JLabel cmndSearch;
	JLabel idCardSearch;
	JLabel exprireSearch;

	JButton searchButton;
	JButton doneSearchButton;
	
	// Listener
	KeyListener tableKeyListener;
	MouseListener tableMouseListener;
	
	int countRow = 0;

	boolean isAddition;
	boolean isSearching;
	boolean isReloadingData;

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	String listColumn[] = {"id", "name", "phonenumber", "birth", "cmnd", "address", "idCard", "expire"};
	DefaultTableModel model = new DefaultTableModel(new String[]{"Mã độc giả", "Họ và Tên", "Số điện thoại", "Ngày sinh", "CMND", "Địa chỉ", "Mã thẻ mượn sách", "Ngày hết hạn"}, 0);
    Vector<String> selectedCells = new Vector<String>();
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
	String removeQuery;
	String searchField;
	boolean isRemoval;
	JTable jt;
	// init default data for variables, definitions
	boolean pressingCTRL=false;//flag, if pressing CTRL it is true, otherwise it is false.

	JLabel ownerMark;
	//
	
	// load data table
	void loadData() {
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
				System.out.println(myRs.getString("phonenumber") + ", " + myRs.getString("name"));
				isSearching = false;
				isReloadingData = true;
			    String a = myRs.getString("id");
			    String b = myRs.getString("name");
			    String c = myRs.getString("phonenumber");
			    String d = myRs.getString("birth");
			    String e = myRs.getString("cmnd");
			    String f = myRs.getString("address");
			    String g = myRs.getString("idCard");
			    String h = myRs.getString("expire");
			    model.addRow(new Object[]{a, b, c, d, e, f, g, h});

			}
			countRow = model.getRowCount();

            myConn.close();
            isReloadingData = false;
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public InsertReader() {

		setTitle("Quản lý độc giả - Vũ Quý Đạt 20176082");
		// Table mouse and keypressed listener
		tableKeyListener = (KeyListener) new KeyAdapter() {
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
		tableMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    System.out.println("mouseClicked");

				System.out.println(model.getRowCount());
				System.out.println("keyevoent: " + e);
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
				    System.out.println("++++++");
				    isRemoval = false;
//					print("is single select mode!");
				    selectedCells.clear();
					int row = jt.rowAtPoint(e.getPoint());
					String newEntry = "" + row;
				    selectedCells.add(newEntry);
				    System.out.println(newEntry);
				}
			    System.out.println("list cell selected" + selectedCells);
			}
		};
		setupFunction();

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		
		loadData();
		
		removeQuery = "DELETE FROM Reader WHERE id = ?";
		
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
		
		renderUI();
		
		removeReaderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedCells.size() > 0) {
					
				} else {
					return;
				}

				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection(url + tableName, username, password);
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					
					// the mysql insert statement
				    String query = removeQuery;
				    System.out.println(selectedCells);
		            for (int i = selectedCells.size() - 1; i > -1; i--) {
		            	int cellIndex = 0;
		            	String cellIndexString = "";
		            	for (int j = 0; j < selectedCells.size(); j++) {
		            		if (Integer.parseInt(selectedCells.get(j)) > cellIndex) {
		            			cellIndex = Integer.parseInt(selectedCells.get(j));
		            			cellIndexString = selectedCells.get(j);
		            		}
		            	}
		            	selectedCells.remove(cellIndexString);
					    isRemoval = true;
					    PreparedStatement preparedStmt = myConn.prepareStatement(query);
		            	int foo;
		            	try {
		            	   foo = Integer.parseInt(cellIndexString);
		            	}
		            	catch (NumberFormatException er)
		            	{
		            	   foo = 0;
		            	}
		            	
			            if (i == 0 && selectedCells.size() > 0) {
			            	preparedStmt.setInt (1, Integer.parseInt(model.getValueAt(Integer.parseInt(selectedCells.get(0)), 0).toString()));
			            	preparedStmt.executeUpdate();
				            model.removeRow(foo);
			            	selectedCells.remove(selectedCells.get(0));
						    isRemoval = false;
			            } else {
			            	preparedStmt.setInt (1, Integer.parseInt(model.getValueAt(foo, 0).toString()));
			            	preparedStmt.executeUpdate();
				            model.removeRow(foo);
			            }
		            }

	            
					// force call last
				    myConn.close();
				} catch (Exception exc) {
					System.out.println(exc);
					exc.printStackTrace();
				}
			}
		});
		
		doneSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("doneSearchButton");
				for (int i = model.getRowCount() - 1; i > -1 ; i--) {
					isSearching = true;
					model.removeRow(i);
				}
				loadData();
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("searchButton");
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
				    String query = "SELECT * FROM Reader WHERE " + searchField + " LIKE " + getSearchText();

					System.out.println(query);
					// 3. Execute SQL query
					myRs = myStmt.executeQuery("select * from Reader WHERE " + searchField + " LIKE '" + getSearchText() + "'");// 4. Process the result set
					while (myRs.next()) {
						System.out.println(myRs.getString("phonenumber") + ", " + myRs.getString("name"));
						isSearching = true;
					    String a = myRs.getString("id");
					    String b = myRs.getString("name");
					    String c = myRs.getString("phonenumber");
					    String d = myRs.getString("birth");
					    String ee = myRs.getString("cmnd");
					    String f = myRs.getString("address");
					    String g = myRs.getString("idCard");
					    String h = myRs.getString("expire");
					    model.addRow(new Object[]{a, b, c, d, ee, f, g, h});

					}
					countRow = model.getRowCount();

		            myConn.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});

		addReaderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("actionPerformed");
				if (!validate("")) {
					System.out.print(validate(""));
					return;
				}
				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					
					  // the mysql insert statement
				      String query = "insert into Reader (id, name, phonenumber, birth, cmnd, address, idCard, expire)"
				        + " values (?, ?, ?, ?, ?, ?, ?, ?)";

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = myConn.prepareStatement(query);
			      preparedStmt.setInt    (1, nextCreatingIDBook());
			      preparedStmt.setString (2, nameInput.getText());
			      preparedStmt.setString (3, phonenumberInput.getText());
			      preparedStmt.setString (4, birthInput.getText());
			      
			      preparedStmt.setString (5, cmndInput.getText());
			      preparedStmt.setString (6, addressInput.getText());
			      preparedStmt.setString (7, idCardInput.getText());
			      preparedStmt.setString (8, expireInput.getText());

			      // execute the preparedstatement
			      isAddition = true;
			      preparedStmt.execute();
			      
			      myConn.close();

//					loadData();
//					model.addRow(new Object[]{1, "Column 2", "Column 3", "Column 3", "Column 3"});
				    model.addRow(new Object[]{nextCreatingIDBook(), nameInput.getText(), phonenumberInput.getText(), birthInput.getText(), 
				    		cmndInput.getText(), addressInput.getText(), 
				    		idCardInput.getText(), expireInput.getText()});
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});

	    jt.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.print("tableChanged");
				if (isAddition || isRemoval || isSearching || isReloadingData) {
					isAddition = false;
					isRemoval = false;
					isSearching = false;
					isReloadingData = false;
					System.out.print(isReloadingData);
				} else {
					// TODO Auto-generated method stub

			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        if (column == 0) {
			        	return;
			        }
			        Object data = model.getValueAt(row, column);

					System.out.print("33333");
			        String sqlupdate = "UPDATE Reader SET " + listColumn[column] + " = '" + data + "' WHERE id = " + model.getValueAt(row, 0);;
					System.out.print(sqlupdate);
			        
					if(column != 0) {
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");

							System.out.print("66666");
							// 2. Create a statement
							myStmt = myConn.createStatement();
							
				            PreparedStatement pst = myConn.prepareStatement(sqlupdate);
				            pst.executeUpdate();
				            myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				}
			}
        });

	    
	    
//	    setVisible(true);    
	}
	
	// methods
	int nextCreatingIDBook () {
		if (model.getRowCount() == 0) {
			return 1;
		}
		return Integer.parseInt(model.getValueAt(model.getRowCount() - 1, 0).toString()) + 1;
	}
	
	void setupFunction () {
	    jt = new JTable(model) {
	    	private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
	    	        return false;
				}
				return true;
	   	    }
	    };
		jt.addKeyListener(tableKeyListener);
		jt.addMouseListener(tableMouseListener);
	}
	
	void renderUI () {
		JLabel titleTable = new JLabel("Bảng danh sách độc giả đã đăng kí");
		titleTable.setBounds(10,30,1380,500);
		add(titleTable);
	    jt.setBounds(10,300,1380,500);
	    
	    JScrollPane sp = new JScrollPane(jt); 
	    sp.setBounds(10,300,1380,440);      
	    add(sp);      

		//
		nameInput = new JTextField("", 5);
		nameInput.setBounds(160, 0, 200, 50);

		JLabel name = new JLabel("Họ và tên");
		JLabel phonenumber = new JLabel("Số điện thoại");
		JLabel publisedhDate = new JLabel("Mã thẻ");
		JLabel cmnd = new JLabel("Số CMND");
		JLabel birth = new JLabel("Ngày sinh");
		JLabel expire = new JLabel("Ngày hết hạn");
		JLabel address = new JLabel("Địa chỉ");
		name.setBounds(10, 0, 100, 50);

		//
		phonenumberInput = new JTextField("", 5);
		phonenumberInput.setBounds(160, 50, 200, 50);

		phonenumber.setBounds(10, 50, 100, 50);

		//
		idCardInput = new JTextField("", 5);
		idCardInput.setBounds(510, 0, 200, 50);

		publisedhDate.setBounds(370, 0, 100, 50);

		//
		cmndInput = new JTextField("", 5);
		cmndInput.setBounds(510, 50, 200, 50);

		cmnd.setBounds(370, 50, 100, 50);

		//
		birthInput = new JTextField("", 5);
		birthInput.setBounds(160, 100, 200, 50);

		birth.setBounds(10, 100, 100, 50);

		//
		expireInput = new JTextField("", 5);
		expireInput.setBounds(510, 100, 200, 50);

		expire.setBounds(370, 100, 100, 50);

		//
		addressInput = new JTextField("", 5);
		addressInput.setBounds(160, 150, 550, 50);

		address.setBounds(10, 150, 100, 50);

		// button
		addReaderButton = new JButton("Đăng kí thông tin người đọc");
		addReaderButton.setBounds(260, 200, 200, 50);
		removeReaderButton = new JButton("Xoá");
		removeReaderButton.setBounds(460, 200, 50, 50);
//		JButton searchButton = new JButton("Tìm kiếm");
//		searchButton.setBounds(610, 200, 100, 50);
		
		add(nameInput);
		add(name);
		add(phonenumberInput);
		add(phonenumber);
		add(idCardInput);
		add(publisedhDate);
		add(cmndInput);
		add(cmnd);
		add(birthInput);
		add(birth);
		add(expireInput);
		add(expire);
		add(addressInput);
		add(address);
		add(addReaderButton);
		add(removeReaderButton);
//		add(searchButton);

		// search
		titleSearch = new JLabel("Tìm kiếm độc giả (nhập 1 trong các trường bên dưới để tìm kiếm)");

		nameInputSearch = new JTextField("", 5);
		phonenumberInputSearch = new JTextField("", 5);
		birthInputSearch = new JTextField("", 5);
		cmndInputSearch = new JTextField("", 5);
		idCardInputSearch = new JTextField("", 5);
		expireInputSearch = new JTextField("", 5);
		
		nameSearch = new JLabel("Tên độc giả");
		phonenumberSearch = new JLabel("Số điện thoại");
		birthSearch = new JLabel("Ngày sinh");
		cmndSearch = new JLabel("Số CMND");
		idCardSearch = new JLabel("Mã thẻ mượn");
		exprireSearch = new JLabel("Ngày hết hạn thẻ");

		searchButton = new JButton("Tìm kiếm");
		doneSearchButton = new JButton("Xong");

		titleSearch.setBounds(750,0,500,50);

		nameSearch.setBounds(730,50,150,50);
		nameInputSearch.setBounds(850,50,200,50);
		phonenumberSearch.setBounds(1060,50,150,50);
		phonenumberInputSearch.setBounds(1180,50,200,50);
		birthSearch.setBounds(730,100,150,50);
		birthInputSearch.setBounds(850,100,200,50);
		cmndSearch.setBounds(1060,100,150,50);
		cmndInputSearch.setBounds(1180,100,200,50);
		idCardSearch.setBounds(730,150,150,50);
		idCardInputSearch.setBounds(850,150,200,50);
		exprireSearch.setBounds(1060,150,150,50);
		expireInputSearch.setBounds(1180,150,200,50);
		
		searchButton.setBounds(980,200,150,50);
		doneSearchButton.setBounds(1130,200,50,50);

		add(titleSearch);
		add(nameInputSearch);
		add(phonenumberInputSearch);
		add(birthInputSearch);
		add(cmndInputSearch);
		add(idCardInputSearch);
		add(expireInputSearch);
		add(nameSearch);
		add(phonenumberSearch);
		add(birthSearch);
		add(cmndSearch);
		add(idCardSearch);
		add(exprireSearch);

		add(searchButton);
		add(doneSearchButton);
		

	    ownerMark = new JLabel("Vũ Quý Đạt - MSSV: 20176082 - Lớp: Vuwit16b");
		ownerMark.setBounds(10,740,1180,30);
		add(ownerMark);
	}

	String getSearchText() {
		if (nameInputSearch.getText() != null && !nameInputSearch.getText().contentEquals("")) {
			searchField = "name";
			return nameInputSearch.getText();
		} else if (phonenumberInputSearch.getText() != null && !phonenumberInputSearch.getText().contentEquals("")) {
			searchField = "phonenumber";
			return phonenumberInputSearch.getText();
		} else if (birthInputSearch.getText() != null && !birthInputSearch.getText().contentEquals("")) {
			searchField = "birth";
			return birthInputSearch.getText();
		} else if (cmndInputSearch.getText() != null && !cmndInputSearch.getText().contentEquals("")) {
			searchField = "cmnd";
			return cmndInputSearch.getText();
		} else if (idCardInputSearch.getText() != null && !idCardInputSearch.getText().contentEquals("")) {
			searchField = "idCard";
			return idCardInputSearch.getText();
		} else if (expireInputSearch.getText() != null && !expireInputSearch.getText().contentEquals("")) {
			searchField = "expire";
			return expireInputSearch.getText();
		}
		return "";
	}
	
	public boolean validate(String string) {
	    if (nameInput.getText().length() == 0 || 
	    		phonenumberInput.getText().length() == 0 || 
	    		birthInput.getText().length() == 0 || 
	    		cmndInput.getText().length() == 0 ||
	    		addressInput.getText().length() == 0 || 
	    		expireInput.getText().length() == 0) {
	    	return false;
	    } else {
			return true;
	    }
	}

}
