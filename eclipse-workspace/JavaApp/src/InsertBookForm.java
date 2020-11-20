import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

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

public class InsertBookForm extends JDialog {
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
	
	JLabel titleTable;
	JScrollPane sp;
	JTable jt;
	
	// init default data for variables, definitions
	boolean pressingCTRL=false;//flag, if pressing CTRL it is true, otherwise it is false.
//	Vector<int[]> selectedCells = new Vector<int[]>();
	Vector<int[]> selectedCells = new Vector<int[]>();
	DefaultTableModel model;
	int countRow = 0; // total row of table 
	
	
	// info of DB
	String listColumn[] = {"id", "name", "type", "author", "publisher", "publishedDate", "dataType"}; // list column of DB
	String url;
	String username;
	String password;
	String tableName;
	// DB instance
	Connection myConn;
	Statement myStmt;
	ResultSet myRs;
	
	boolean isAddition;
	String insertQuery;
	String removeQuery;
	String selectAllQuery;
	
	public InsertBookForm() {
		// initializers - not for array constants

		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		tableName = "LibraryManagementDB";
		isAddition = false;

		insertQuery = "insert into Book (id, name, type, author, publisher, publishedDate, dataType)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
		removeQuery = "DELETE FROM Table WHERE name = ?";
		selectAllQuery = "select * from Book";
		
		// force call 
		prerequisiteSetting();
		renderUIElement();
		loadDataFromDB();
		
		//
		   KeyListener tableKeyListener = (KeyListener) new KeyAdapter() {
			      @Override
			      public void keyPressed(KeyEvent e) {
			    	  System.out.println(e);
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
			    	  
						print("mouse click!");
			            System.out.println(jt.getSelectedRowCount());
			            System.out.println(pressingCTRL);
			         if(pressingCTRL == true){//check if user is pressing CTRL key
			            int row = jt.rowAtPoint(e.getPoint());//get mouse-selected row
			            int[] newEntry = new int[]{row};//{row,col}=selected cell
			            if(selectedCells.contains(newEntry)){
			               //cell was already selected, deselect it
			               selectedCells.remove(newEntry);
							print("----++++");
			            } else {
			               //cell was not selected
			               selectedCells.add(newEntry);
			         
			            }
//			            for (int i = 0; i < jt.getSelectedRowCount(); i++) {
//			            	System.out.println(selectedCells.get(0));
//			            }
			         if (selectedCells.size() > 0) {
							print("----");
		            	System.out.println(selectedCells.get(0));
						print("----");
		            }
			         }
			      }
			   };
			   jt.addKeyListener(tableKeyListener);
			   jt.addMouseListener(tableMouseListener);
		
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validate("")) {
					print("----");
					System.out.print(validate(""));
					return;
				}

				print("-------");
				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection(url + tableName, username, password);
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					
					// the mysql insert statement
				    String query = insertQuery;

				    // create the mysql insert preparedstatement
				    PreparedStatement preparedStmt = myConn.prepareStatement(query);
				    preparedStmt.setInt    (1, ++countRow);
				    preparedStmt.setString (2, nameInput.getText());
				    preparedStmt.setString (3, typeInput.getText());
				    preparedStmt.setString (4, authorInput.getText());
				    preparedStmt.setString (5, nxbInput.getText());
				    preparedStmt.setString (6, publisedhDateInput.getText());
				    preparedStmt.setString (7, dataTypeInput.getText());
	
				    // execute the preparedstatement
				    preparedStmt.execute();
				    isAddition = true;
				    model.addRow(new Object[]{countRow, nameInput.getText(), typeInput.getText(), authorInput.getText(), 
				    		nxbInput.getText(), publisedhDateInput.getText(), dataTypeInput.getText()});	

					// force call last
				    myConn.close();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		removeBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
print("-----");
System.out.println(selectedCells.size());
	            for (int i = 0; i < selectedCells.size(); i++) {
	            	System.out.println(selectedCells.get(i)[0]);
	            	System.out.println(model.getValueAt(selectedCells.get(i)[0], 0));
	            	print("-----==");
	            }

//				try {
//					// 1. Get a connection to database
//					myConn = DriverManager.getConnection(url + tableName, username, password);
//					
//					// 2. Create a statement
//					myStmt = myConn.createStatement();
//					
//					// the mysql insert statement
//				    String query = removeQuery;
//
//				    // create the mysql insert preparedstatement
//				    PreparedStatement preparedStmt = myConn.prepareStatement(query);
//				    preparedStmt.setString (1, nameInput.getText());
//	
//				    // execute the preparedstatement
//				    preparedStmt.execute();
//				    isAddition = true;
//				    model.addRow(new Object[]{countRow, nameInput.getText(), typeInput.getText(), authorInput.getText(), 
//				    		nxbInput.getText(), publisedhDateInput.getText(), dataTypeInput.getText()});	
//
//					// force call last
//				    myConn.close();
//				} catch (Exception exc) {
//					exc.printStackTrace();
//				}
			}
		});
		
	    jt.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				
				if (isAddition) {
					isAddition = false;
				} else {
					// TODO Auto-generated method stub
			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        if (column == 0) {
			        	return;
			        }
			        
			        String sqlupdate = "UPDATE Book SET " + listColumn[column] + " = '" + model.getValueAt(row, column) + "' WHERE id = " + ++row;
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
	public boolean validate(String string) {
	    if (nameInput.getText().length() == 0 || 
	    		typeInput.getText().length() == 0 || 
	    		authorInput.getText().length() == 0 || 
	    		nxbInput.getText().length() == 0 ||
	    		publisedhDateInput.getText().length() == 0 || 
	    		dataTypeInput.getText().length() == 0) {
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
			myRs = myStmt.executeQuery(selectAllQuery);
			
			// 4. Process the result set
			while (myRs.next()) {
			    String a = myRs.getString("id");
			    String b = myRs.getString("name");
			    String c = myRs.getString("type");
			    String d = myRs.getString("author");
			    String e = myRs.getString("publisher");
			    String f = myRs.getString("publishedDate");
			    String g = myRs.getString("dataType");
			    model.addRow(new Object[]{a, b, c, d, e, f ,g});

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
		addBookButton.setBounds(825, 0, 200, 50);
		removeBookButton = new JButton("Xoá");
		removeBookButton.setBounds(1025, 0, 60, 50);
		
		//
		searchButton = new JButton("Tìm kiếm ");
		searchButton.setBounds(825, 50, 200, 50);
		
		// 
		titleTable = new JLabel("Bảng danh sách các đầu sách trong thư viện");
		titleTable.setBounds(10,-20,780,500);
		model = new DefaultTableModel(new Object[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách"}, 0);
	    jt = new JTable(model);    
	    jt.setBounds(10,250,1180,500); 
	    sp = new JScrollPane(jt); 
	    sp.setBounds(10,250,1180,440);
		
		add(name);
		add(nameInput);
		add(type);
		add(typeInput);
		add(author);
		add(authorInput);
		add(nxb);
		add(nxbInput);
		add(publisedhDate);
		add(publisedhDateInput);
		add(dataType);
		add(dataTypeInput);
		add(searchButton);
		add(addBookButton); // button
		add(removeBookButton);
		add(titleTable);   
	    add(sp); // scroll pane
	    
	    // set centre header for table
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);         
//	    setVisible(true);    
	}

}
