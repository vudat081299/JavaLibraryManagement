import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class FactoryForms extends JDialog {

	JButton confirmButton;
	int id;

	// DB instance
	Connection myConn;
	Statement myStmt;
	ResultSet myRs;
	
	// info of DB
	String listColumn[] = {"id", "name", "type", "author", "publisher", "publishedDate", "dataType", "state"}; // list column of DB
	String url;
	String username;
	String password;
	String dbName;
	
	String listTableName[] = {"phieu_thu", "phieu_chi", "phieu_nhap", "phieu_xuat",
			"chung_tu_nhap", "chung_tu_xuat", "danh_muc_khach_hang", "danh_muc_hang_hoa"};
	
	// query
	String insertQuery;
	String selectAllQuery;
	
	String[][] inputFormTupleLabel = { 
			{"Mã phiếu", "Ngày thu", "Mã khách hàng", "Số tiền"},
			{"Mã phiếu", "Ngày chi", "Mã khách hàng", "Số tiền"},
			{"Mã hoá đơn", "Ngày nhập", "Số thanh toán", "Mã khách hàng"},
			{"Mã hoá đơn", "Ngày xuất", "Số thanh toán", "Mã khách hàng"},
			
			{"Mã chứng từ", "Mã hoá đơn", "Số lượng", "Đơn giá", "Mã hàng"},
			{"Mã chứng từ", "Mã hoá đơn", "Số lượng", "Đơn giá", "Mã hàng"},
			{"Mã khách hàng", "Tên khách hàng", "Địa chỉ", 
				"Mã số thuế", "Số điện thoại", "Email"},
			{"Mã hàng", "Tên hàng", "Đơn vị tính", "Giá chuẩn"}
	};
	
	String[][] columnForQuery = {
			{" (ma_phieu, ngay_thu, ma_khach_hang, so_tien) ", "values (?, ?, ?, ?)"},
			{" (ma_phieu, ngay_chi, ma_khach_hang, so_tien) ", "values (?, ?, ?, ?)"},
			{" (ma_hoa_don, ngay_nhap, so_thanh_toan, ma_khach_hang) ", "values (?, ?, ?, ?)"},
			{" (ma_hoa_don, ngay_xuat, so_thanh_toan, ma_khach_hang) ", "values (?, ?, ?, ?)"},
			
			{" (ma_chung_tu, ma_hoa_don, so_luong, don_gia, ma_hang) ", "values (?, ?, ?, ?, ?)"},
			{" (ma_chung_tu, ma_hoa_don, so_luong, don_gia, ma_hang) ", "values (?, ?, ?, ?, ?)"},
			{" (ma_khach_hang, ten_khach_hang, dia_chi, ma_so_thue, sdt, email) ", "values (?, ?, ?, ?, ?, ?)"},
			{" (ma_hang, ten_hang, don_vi_tinh, gia_chuan) ", "values (?, ?, ?, ?)"}
	};
	
	// table ui
	JScrollPane sp;
	JTable jt;
	static DefaultTableModel model;
	DefaultTableModel[] listModel = {
			new DefaultTableModel(new Object[]{"Mã phiếu", "Ngày thu", "Mã khách hàng", "Số tiền"}, 0),
			new DefaultTableModel(new Object[]{"Mã phiếu", "Ngày chi", "Mã khách hàng", "Số tiền"}, 0),
			new DefaultTableModel(new Object[]{"Mã hoá đơn", "Ngày nhập", "Số thanh toán", "Mã khách hàng"}, 0),
			new DefaultTableModel(new Object[]{"Mã hoá đơn", "Ngày xuất", "Số thanh toán", "Mã khách hàng"}, 0),
			
			new DefaultTableModel(new Object[]{"Mã chứng từ", "Mã hoá đơn", "Số lượng", "Đơn giá", "Mã hàng"}, 0),
			new DefaultTableModel(new Object[]{"Mã chứng từ", "Mã hoá đơn", "Số lượng", "Đơn giá", "Mã hàng"}, 0),
			new DefaultTableModel(new Object[]{"Mã khách hàng", "Tên khách hàng", "Địa chỉ", 
					"Mã số thuế", "Số điện thoại", "Email"}, 0),
			new DefaultTableModel(new Object[]{"Mã hàng", "Tên hàng", "Đơn vị tính", "Giá chuẩn"}, 0)
	};

	public FactoryForms(int formId) {
		id = formId;
		prerequisiteSetting();
		form0(inputFormTupleLabel[formId]);
		
		url = "jdbc:mysql://localhost:3306/";
		username = "root";
		password = "Iviundhacthi8987m";
		dbName = "quan_ly_kho";
		
		// set up query
		insertQuery = "insert into " + listTableName[id] + columnForQuery[id][0] + columnForQuery[id][1];
		selectAllQuery = "select * from " + listTableName[id];
		
		loadDataFromDB();
	}
	
	// form 0: tao phieu thu.
	void form0 (String[] labelsList) {

		confirmButton = new JButton(Frame1.functionList[id]);
		JTextField[] textFields = new JTextField[8];
		JLabel[] labels = new JLabel[8];
		for (int i = 0; i < labelsList.length; i++) {
			labels[i] = new JLabel(labelsList[i]);
			labels[i].setText(labelsList[i]);
			labels[i].setBounds(20, 20 + 50 * i, 150, 40);
			add(labels[i]);

			textFields[i] = new JTextField("");
			textFields[i].setBounds(250, 20 + 50 * i, 200, 40);
			add(textFields[i]);
			if (i == labelsList.length - 1) {
				confirmButton.setBounds(150, 80  + 50 * i, 180, 40);
				confirmButton.setBackground(Color.BLUE);

			}
		}
				add(confirmButton);
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							// 1. Get a connection to database
							myConn = DriverManager.getConnection(url + dbName, username, password);
							
							// 2. Create a statement
							myStmt = myConn.createStatement();
							
							// the mysql insert statement
						    String query = insertQuery;

						    // create the mysql insert preparedstatement
						    PreparedStatement preparedStmt = myConn.prepareStatement(query);
						    
						    for (int i = 0; i < labelsList.length; i++) {
						    	preparedStmt.setString(i + 1, textFields[i].getText());
						    }
						    
						    // execute the preparedstatement
						    preparedStmt.execute();
						    
							// force call last
						    myConn.close();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
						loadDataFromDB();
					}
				});
		
	}

	// prerequisite setting for this dialog type view
	void prerequisiteSetting() {
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
		setUpTable();
	}
	
	void loadDataFromDB() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(url + dbName, username, password);

			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery(selectAllQuery);
			
			// 4. Process the result set
		    if (id == 0) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_phieu");
				    String b = myRs.getString("ngay_thu");
				    String c = myRs.getString("ma_khach_hang");
				    String d = myRs.getString("so_tien");
				    model.addRow(new Object[]{a, b, c, d});
				}
		    } else if (id == 1) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_phieu");
				    String b = myRs.getString("ngay_chi");
				    String c = myRs.getString("ma_khach_hang");
				    String d = myRs.getString("so_tien");
				    model.addRow(new Object[]{a, b, c, d});
				}
		    } else if (id == 2) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_hoa_don");
				    String b = myRs.getString("ngay_nhap");
				    String c = myRs.getString("so_thanh_toan");
				    String d = myRs.getString("ma_khach_hang");
				    model.addRow(new Object[]{a, b, c, d});
				}
		    } else if (id == 3) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_hoa_don");
				    String b = myRs.getString("ngay_xuat");
				    String c = myRs.getString("so_thanh_toan");
				    String d = myRs.getString("ma_khach_hang");
				    model.addRow(new Object[]{a, b, c, d});
				}
		    } else if (id == 4) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_chung_tu");
				    String b = myRs.getString("ma_hoa_don");
				    String c = myRs.getString("so_luong");
				    String d = myRs.getString("don_gia");
				    String e = myRs.getString("ma_hang");
				    model.addRow(new Object[]{a, b, c, d, e});
				}
		    } else if (id == 5) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_chung_tu");
				    String b = myRs.getString("ma_hoa_don");
				    String c = myRs.getString("so_luong");
				    String d = myRs.getString("don_gia");
				    String e = myRs.getString("ma_hang");
				    model.addRow(new Object[]{a, b, c, d, e});
				}
		    } else if (id == 6) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_khach_hang");
				    String b = myRs.getString("ten_khach_hang");
				    String c = myRs.getString("dia_chi");
				    String d = myRs.getString("ma_so_thue");
				    String e = myRs.getString("sdt");
				    String f = myRs.getString("email");
				    model.addRow(new Object[]{a, b, c, d, e, f});
				}
		    } else if (id == 7) {
				while (myRs.next()) {
				    String a = myRs.getString("ma_hang");
				    String b = myRs.getString("ten_hang");
				    String c = myRs.getString("don_vi_tinh");
				    String d = myRs.getString("gia_chuan");
				    model.addRow(new Object[]{a, b, c, d});
				}
		    }

			// force call last
            myConn.close();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	void setUpTable() {
		model = listModel[id];
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
	    jt.setBounds(10,250,1180,500);
	    jt.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	    sp = new JScrollPane(jt); 
	    sp.setBounds(470,20,900,800);
	    add(sp);
	}
}
