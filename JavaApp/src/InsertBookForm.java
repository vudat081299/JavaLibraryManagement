import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class InsertBookForm extends JDialog {
	private static final TableModelListener InsertBookForm = null;
	int countRow = 0;

	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;
	DefaultTableModel model = new DefaultTableModel(new String[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách"}, 0);
	
	String listColumn[] = {"id", "name", "type", "author", "publisher", "publishedDate", "dataType"};
	static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	
	void loadData() {
//		model = null;
//		model = new DefaultTableModel(new String[]{"Mã sách", "Tên sách", "Thể loại", "Tác giả", "Nhà xuất bản", "Ngày xuất bản", "Kiểu sách"}, 0);

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from Book");
			
			// 4. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("name") + ", " + myRs.getString("author"));

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

            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public InsertBookForm() {
		loadData();
		
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
		
		//
		JTextField nameInput = new JTextField("", 5);
		nameInput.setBounds(160, 0, 200, 50);
		add(nameInput);

		JLabel name = new JLabel("Tên đầu sách");
		name.setBounds(10, 0, 100, 50);
		add(name);

		//
		JTextField typeInput = new JTextField("", 5);
		typeInput.setBounds(160, 50, 200, 50);
		add(typeInput);

		JLabel type = new JLabel("Loại sách");
		type.setBounds(10, 50, 100, 50);
		add(type);
		
		//
		JTextField authorInput = new JTextField("", 5);
		authorInput.setBounds(160, 100, 200, 50);
		add(authorInput);

		JLabel author = new JLabel("Tác giả");
		author.setBounds(10, 100, 100, 50);
		add(author);
		
		//
		JTextField nxbInput = new JTextField("", 5);
		nxbInput.setBounds(510, 0, 200, 50);
		add(nxbInput);

		JLabel nxb = new JLabel("Nhà xuất bản");
		nxb.setBounds(370, 0, 100, 50);
		add(nxb);

		//
		JTextField publisedhDateInput = new JTextField("", 5);
		publisedhDateInput.setBounds(510, 50, 200, 50);
		add(publisedhDateInput);

		JLabel publisedhDate = new JLabel("Ngày xuất bản");
		publisedhDate.setBounds(370, 50, 100, 50);
		add(publisedhDate);

		//
		JTextField dataTypeInput = new JTextField("", 5);
		dataTypeInput.setBounds(510, 100, 200, 50);
		add(dataTypeInput);

		JLabel dataType = new JLabel("Kiểu sách");
		dataType.setBounds(370, 100, 100, 50);
		add(dataType);

		JButton addBookButton = new JButton("Thêm đầu sách vào thư viện");
		addBookButton.setBounds(300, 150, 200, 50);
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 1. Get a connection to database
					myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
					
					// 2. Create a statement
					myStmt = myConn.createStatement();
					
					  // the mysql insert statement
				      String query = " insert into Book (id, name, type, author, publisher, publishedDate, dataType)"
				        + " values (?, ?, ?, ?, ?, ?, ?)";

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
			      
			      myConn.close();

//					loadData();
//					model.addRow(new Object[]{1, "Column 2", "Column 3", "Column 3", "Column 3"});
//				    model.addRow(new Object[]{countRow, nameInput.getText(), typeInput.getText(), authorInput.getText(), nxbInput.getText(), publisedhDateInput.getText(), dataTypeInput.getText()});
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		add(addBookButton);
		

		JLabel titleTable = new JLabel("Bảng danh sách đầu sách trong thư viện");
		titleTable.setBounds(10,-20,780,500);
		add(titleTable);
	    JTable jt = new JTable(model);    
	    jt.setBounds(10,250,780,500);          
	    jt.getModel().addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub

		        int row = e.getFirstRow();
		        int column = e.getColumn();
		        if (column == 0) {
		        	return;
		        }
		        Object data = model.getValueAt(row, column);
		        
		        String sqlupdate = "UPDATE Book SET " + listColumn[column] + " = '" + data + "' WHERE id = " + ++row;
				if(column != 0) {
					try {
						// 1. Get a connection to database
						myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementDB", "root" , "Iviundhacthi8987m");
						
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
        });
//	    DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jt.getTableHeader().getDefaultRenderer();
//	    renderer.setHorizontalAlignment(0);
	    JScrollPane sp = new JScrollPane(jt); 
	    sp.setBounds(10,250,780,440);      
	    add(sp);             
//	    setVisible(true);    
	}

}
