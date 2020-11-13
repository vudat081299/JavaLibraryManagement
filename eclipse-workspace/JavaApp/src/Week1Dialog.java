import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.Random;

public class Week1Dialog extends JDialog {
	/**
	 * @wbp.nonvisual location=50,111
	 */

//	private static final int[] idArray = {R.id.button1, R.id.button2, R.id.button3};
//	private Button[] button = new Button[idArray.length];
	
	private static int i = 0;
	String[] week1Question = {
			"<html>Bài 1  :  In ra màn hình tất cả các hợp số <100.</html>",
			"<html>Bài 2  : In ra màn hình 20 số nguyên tố đầu tiên.</html>",
			"<html>Bài 3  : In ra màn hình tất cả các số nguyên tố từ 1000 đến 2000.</html>",
			"<html>Bài 4  : In ra màn hình các số nhỏ hơn 100 và chia hết cho 3,7.</html>",
			"<html>Bài 5  : In ra màn hình các số nằm giữa 1000 và 2000 đồng thời chia hết cho 3,5,7.</html>",
			"<html>Bài 6  : In ra màn hình 5 số hoàn hảo đầu tiên (Số hoàn hảo là số có tổng bằng các <br/>ước số của mình kể cả 1.</html>",
			"<html>Bài 7  : Trong các số tự nhiên nhỏ hơn hoặc bằng 100 hãy đếm xem có bao nhiêu số.<br/>a) Chia hết cho 5.<br/>b) Chia 5 dư 1.<br/>c) Chia 5 dư 2.<br/d) Chia 5 dư 3.</html>",
			"<html>Bài 8  : Cho số tự nhiên N bất kỳ (đã gán trước đó), tìm và in ra ước số nguyên tố <br/>nhỏ nhất của N.</html>",
			"<html>Bài 9  : Cho số tự nhiên N>1 bất kỳ (đã gán trước đó). In ra khai triển thành tích <br/>các số nguyên tố tính từ nhỏ đến lớn VD: 9=3*3; 12=2*2*3.</html>",
			"<html>Bài 10 : Cho trước số tự nhiên N bất kỳ (đã gán trước đó). In ra màn hình tất cả các <br/>ước số nguyên tố khác nhau của N.</html>",
			"<html>Bài 11 : Viết chương trình tráo đổi ngẫu nhiên vị trí một dãy số cho trước. Để lấy một <br/>số int ngẫu nhiên từ 0 đến n-1 ta dùng lệnh <br/>int i = Random.nextInt(n);</html>",
			"<html>Bài 12 : Viết chương trình nhập chiều cao h từ bàn phím, sau đó hiển thị các tam giác <br/>hình sao có chiều cao h như dưới đây. Chú ý có <br/>kiểm tra điều kiện của h: 2<=h<=10. <br/>Nếu h nằm ngoài đoạn trên, yêu cầu người dùng nhập lại. (cho lựa chọn in tam giác xuôi hoặc ngược).</html>",
			"<html>Bài 1B : Cho số tự nhiên N bất kỳ, tính tổng S=1+1/(1+2) + 1/(1+2+3) +…+ 1/(1+2+3+..+N).</html>",
			"<html>Bài 2B : Cho số tự nhiên N bất kỳ, tính tổng S= 1+ 1/2! + 1/3! + … + 1/N!.</html>",
			"<html>Bài 3B : Cho số tự nhiên N bất kỳ, tính tổng S=1+1/(1+2!) + 1/(1+2!+3!)+ ..+ 1/(1+2!+3!+..+N!).</html>",
			"<html>Bài 4B : Dãy Fibonaxi 1, 2, 3, … F(k) = F(k-1) + F(k-2). Tính số Fibonaxi thứ N.</html>"
			};
	String[] week2Question = {
			"<html>Bài 1  : Cho một dãy số tự nhiên, viết chương trình sắp xếp dãy này theo thứ tự giảm dần.</html>",
			"<html>Bài 2  : Cho dãy số tự nhiên, in ra màn hình tất cả các số nguyên tố của dãy này.</html>",
			"<html>Bài 3  : Cho một dãy các số tự nhiên, tìm và in ra 1 giá trị min của dãy này và tất cả các chỉ số ứng với giá trị min này.</html>",
			"<html>Bài 4  : Cho một dãy các số tự nhiên, tìm và in ra 1 giá trị max của dãy này và tất cả các chỉ số ứng với giá trị max này.</html>",
			"<html>Bài 5  : Cho một dãy số tự nhiên, hãy đếm xem trong dãy số trên có bao nhiêu số nguyên tố, có bao nhiêu hợp số.</html>",
			"<html>Bài 6  : Cho một dãy số tự nhiên, hãy in ra tất cả các số hạng của dãy trên thỏa mãn: số này là ước số thực sự của 1 số hạng khác trong dãy trên.</html>",
			"<html>Bài 7  : Cho một dãy số tự nhiên, hãy tìm 1 số tự nhiên nhỏ nhất c không bằng bất cứ số nào trong dãy trên.</html>",
			"<html>Bài 8  : Cho một dãy số nguyên bất kỳ, hãy xóa đi trong dãy này các số hạng = 0 và in ra màn hình các số còn lại.</html>",
			"<html>Bài 9  : Cho một dãy số nguyên bất kỳ, cho trước 1 số c. Hãy đếm có bao nhiêu số của dãy trên =c; lớn hơn c; nhỏ hơn c.</html>",
			"<html>Bài 10 : Cho một dãy số nguyên bất kỳ, hãy tìm ra 1 một dãy số liền nhau dài nhất bao gồm các số bằng nhau. Hãy in ra số lượng và các chỉ số đầu tiên của dãy con này.</html>",
			"<html>Bài 11 : Cho một dãy số nguyên bất kỳ. Hãy tìm 1 một dãy con liên tục đơn điệu tăng dài nhất của dãy trên.</html>",
			"<html>Bài 12 : Dãy số a[ ] được gọi là dãy con của b[ ] nếu từ b [ ] xóa đi 1 vài số sẽ thu được a[ ]. Cho trước 2 dãy số nguyên a[ ]; b[ ]. Hãy kiểm tra xem a[ ] có là dãy con của b[ ] hay không?.</html>"
	};
	String[] week3Question = {
			"<html>Bài 1  : Cho trước 1 xâu ký tự là họ tên người đầy đủ nhưng khi nhập có thể thừa một số dấu cách. Hãy xóa đi các dấu cách thừa và in ra họ tên chính xác.</html>",
			"<html>Bài 2  : Cho trước xâu ký tự bất kỳ. Hãy đếm xem trong xâu có bao nhiêu lần xuất hiện xâu con “abc”.</html>",
			"<html>Bài 3  : Cho trước 1 xâu ký tự là họ tên người đầy đủ, hãy tách ra phần tên của người này.</html>",
			"<html>Bài 4  : Cho trước 1 xâu ký tự là họ tên người đầy đủ, hãy tách ra phần họ của người này.</html>",
			"<html>Bài 5  : Cho một xâu ký tự bao gồm toàn các ký tự 0,1. Hãy biến đổi xâu này theo cách 0->1, 1->0 và in ra kết quả.</html>",
			"<html>Bài 6  : Cho trước xâu ký tự S, in ra xâu S1 ngược lại xâu S.</html>",
			"<html>Bài 7  : Cho trước xâu ký tự S. Hãy biến đổi S theo quy tắc sau: Chữ số thì biến thành “$”, ký tự thì giữ nguyên.</html>",
			"<html>Bài 8  : Cho trước 2 xâu ký tự S1, S2. Hãy đếm xem xâu S1 xuất hiện trong S2 tại bao nhiêu vị trí.</html>",
			"<html>Bài 9  : Cho xâu S và 2 chỉ số i, j. Hãy đổi chỗ 2 vị trí i, j trong S.</html>",
			"<html>Bài 10 : Cho mảng xâu ký tự S1, S2, ..Sn. Hãy tìm và in ra phần tử xâu có độ dài lớn nhất.</html>",
			"<html>Bài 11 : Cho danh sách họ tên đầy đủ học sinh. Hãy đếm xem có bao nhiêu bạn tên “An”.</html>",
			"<html>Bài 12 : Cho danh sách họ tên đầy đủ học sinh. Hãy đếm xem có bao nhiêu bạn có phần đệm là  “Thị”.</html>",
			"<html>Bài 13 : Cho danh sách họ tên đầy đủ học sinh. Hãy đếm xem có bao nhiêu bạn có tên bắt đầu bằng chữ “H”.</html>",
			"<html>Bài 14 : Dãy xâu ký tự S1, S2,… được cho theo quy tắc sau: S1= “1111100000”, Sk thu được từ Sk-1 bằng cách thay đổi cho lần lượt các vị trí  1-2;  2-3;  3-4; 4-5; 5-6;  6-7; 7-8;  8-9; 9-10. Cho trước số tự nhiên N, Hãy in ra xâu Sn.</html>",
			"<html>Bài 15 : Cho trước 2 xâu ký tự S, S2. Hãy chèn xâu S1 vào giữa xâu S2 và in kết quả.</html>",
			"<html>Bài 16 : Cho trước 2 xâu S1, S2. Hãy xét xem xâu S1 có phải là xâu con của S2 nếu xóa bỏ vài kí tự của xâu S2 ta được xâu S1.</html>"
	};
	
	
	public Week1Dialog() {
		pack();
		setModal(true);
		setResizable(true);
		getContentPane().setLayout(null);
		
		if (Frame1.week == 1) {
			setTitle("Week " + Frame1.week);
			renderUI(week1Question);
		} else if (Frame1.week == 2) {
			setTitle("Week " + Frame1.week);
			renderUI(week2Question);
		} else if (Frame1.week == 3) {
			setTitle("Week " + Frame1.week);
			renderUI(week3Question);
		}
		
		
	}
		
	void renderUI (String[] weekQuestion) {
		JLabel infoStudentName = new JLabel("Vũ Quý Đạt - MSSV: 20176082");
		infoStudentName.setBounds(10, -20, 600, 80);
		add(infoStudentName);
		JLabel infoStudentClass = new JLabel("Lớp: Vuwit16b");
		infoStudentClass.setBounds(10, 0, 600, 80);
		add(infoStudentClass);
		JLabel question = new JLabel("");
		JLabel subText = new JLabel("");
		JLabel resultLabel = new JLabel("");
		question.setBounds(10, 160, 780, 300);
		resultLabel.setBounds(10, 400, 780, 300);
		subText.setBounds(10, 300, 780, 300);
		add(question);
		add(resultLabel);
		add(subText);
		
		int y = 60;
		for (i = 0; i < weekQuestion.length; i++) {
			JButton button = new JButton("" + weekQuestion[i].substring(6, 12));
			if((i % 4) == 0 && i != 0) {
				y += 50;
			}
			button.setBounds(200 * (i % 4), y, 200, 50);
			button.setName("Bài " + (i + 1));
			button.setActionCommand("" + i);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					subText.setText("Kết quả: ");
					for (int j = 0; j < i; j++) {
						if (("" + j).equals((e.getActionCommand()))) {
							question.setText(weekQuestion[j]);
							switch (j) {
							case 0:
								if (Frame1.week == 1) {
									resultLabel.setText(bai1Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai1Tuan2());
								} else {
									resultLabel.setText(bai1Tuan3());
								}
								break;
							case 1:
								if (Frame1.week == 1) {
									resultLabel.setText(bai2Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai2Tuan2());
								} else {
									resultLabel.setText(bai2Tuan3());
								}
								break;
							case 2:
								if (Frame1.week == 1) {
									resultLabel.setText(bai3Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai3Tuan2());
								} else {
									resultLabel.setText(bai3Tuan3());
								}
								break;
							case 3:
								if (Frame1.week == 1) {
									resultLabel.setText(bai4Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai4Tuan2());
								} else {
									resultLabel.setText(bai4Tuan3());
								}
								break;
							case 4:
								if (Frame1.week == 1) {
									resultLabel.setText(bai5Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai5Tuan2());
								} else {
									resultLabel.setText(bai5Tuan3());
								}
								break;
							case 5:
								if (Frame1.week == 1) {
									resultLabel.setText(bai6Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai6Tuan2());
								} else {
									resultLabel.setText(bai6Tuan3());
								}
								break;
							case 6:
								if (Frame1.week == 1) {
									resultLabel.setText(bai7Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai7Tuan2());
								} else {
									resultLabel.setText(bai7Tuan3());
								}
								break;
							case 7:
								if (Frame1.week == 1) {
									resultLabel.setText(bai8Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai8Tuan2());
								} else {
									resultLabel.setText(bai9Tuan3());
								}
								break;
							case 8:
								if (Frame1.week == 1) {
									resultLabel.setText(bai9Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai9Tuan2());
								} else {
									resultLabel.setText(bai9Tuan3());
								}
								break;
							case 9:
								if (Frame1.week == 1) {
									resultLabel.setText(bai10Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai10Tuan2());
								} else {

									resultLabel.setText(bai10Tuan3());
								}
								break;
							case 10:
								if (Frame1.week == 1) {
									resultLabel.setText(bai11Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai11Tuan2());
								} else {

									resultLabel.setText(bai11Tuan3());
								}
								break;
							case 11:
								if (Frame1.week == 1) {
									resultLabel.setText(bai12Tuan1());
								} else if (Frame1.week == 2) {
									resultLabel.setText(bai12Tuan2());
								} else {

									resultLabel.setText(bai12Tuan3());
								}
								break;
							case 12:
								if (Frame1.week == 1) {
									resultLabel.setText(bai1BTuan1());
								} else if (Frame1.week == 2) {
								} else {

									resultLabel.setText(bai13Tuan3());
								}
								break;
							case 13:
								if (Frame1.week == 1) {
									resultLabel.setText(bai2BTuan1());
								} else if (Frame1.week == 2) {
									
								} else {
									resultLabel.setText(bai14Tuan3());
								}
								break;
							case 14:
								if (Frame1.week == 1) {
									resultLabel.setText(bai3BTuan1());
								} else if (Frame1.week == 2) {
									
								} else {
									resultLabel.setText(bai15Tuan3());
								}
								break;
							case 15:
								if (Frame1.week == 1) {
									resultLabel.setText(bai4BTuan1());
								} else if (Frame1.week == 2) {
									
								} else {
									resultLabel.setText(bai16Tuan3());
								}
								break;
							}
						}
					}
				}
			});
			getContentPane().add(button);
		}
	}

	   static String remove(String name, int p) {
	       return name.substring(0, p) + name.substring(p + 1); 
	   }
	String bai1Tuan3 () {
		String result = "<html>Tên input 'V u Quy Dat'<br/>Ho ten chinh xac: ";
		
		String name = "V u Quy Dat";
        for(int i=0; i<name.length(); i++) { 
           if(name.charAt(i)==' ' && Character.isLowerCase(name.charAt(i+1))) { 
               name = remove(name, i); 
           } 
       } 
       result += (name); 



		result += "</html>";
		return result;
	}
	String bai2Tuan3 () {
		String result = "<html>Tên input 'V uabc Quy Databc'<br/>";
	
		String name = "V uabc Quy Databc";
        int count = 0;
        
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == 'a' && name.charAt(i + 1) == 'b' && name.charAt(i + 2) == 'c') {
                count++;
            }
        }
        result += ("Số chuỗi con abc xuất hiện trong xâu '" + name + "' là " + count + " lần."); 
 
		result += "</html>";
		return result;
	}
	String bai3Tuan3 () {
		String output = "<html>Họ tên đầy đủ 'Vu Quy Dat'<br>";
	
		String fullName = "Vu Quy Dat";
        String name = "";
        String result = "";
        
        for (int i = fullName.length() - 1; i > -1; i--) {
            if (fullName.charAt(i) == ' ') {
                break;
            } else {
                name += fullName.charAt(i);
            }
        }
        for (int i = name.length() - 1; i > -1; i--) {
            result += name.charAt(i);
        }
            
        output += ("Phần tên của '" + fullName + "' là " + result); 
      

       output += "</html>";
		return output;
	}
	String bai4Tuan3 () {
		String result = "<html>Họ tên đầy đủ 'Vu Quy Dat'<br>";
	
		String fullName = "Vu Quy Dat";
        String firstName = "";
        
        for (int i = 0; i < fullName.length(); i++) {
            if (fullName.charAt(i) == ' ') {
                break;
            } else {
                firstName += fullName.charAt(i);
            }
        }
            
//        System.out.print("Phần họ của '" + fullName + "' là " + firstName); 
       

		result += "</html>";
		return result;
	}
	String bai5Tuan3 () {
		String result = "<html>Xâu nhị phân: 11110000110101<br/>";
	
		String binaryString = "11110000110101";
        String reverseBinary = "";
        
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '0') {
                reverseBinary += "1";
            } else {
                reverseBinary += "0";
            }
        }
        result += ("Đảo các bit của xâu đã cho ta có kết quả: " + reverseBinary); 
      

		result += "</html>";
		return result;
	}
	String bai6Tuan3 () {
		String result = "<html>Xâu kí tự cho trước 'adfakdfjaljds'<br/>";
	
		 String input = "adfakdfjaljds";
         String reverseInput = "";
         
         for (int i = input.length() - 1; i > -1; i--) {
                 reverseInput += input.charAt(i);
         }
         result += ("Nghịch đảo của chuỗi đã cho là: " + reverseInput); 
        

		result += "</html>";
		return result;
	}
	String bai7Tuan3 () {
		String output = "<html>Xâu kí tự cho trước '1adfak2313dfjaljds'<br/>";
	
		String input = "1adfak2313dfjaljds";
        String result = "";
        
        for (int i = 0; i < input.length(); i++) {
              if (Character.isDigit(input.charAt(i))) {
                  result += '$';
              } else {
                  result += input.charAt(i);
              }
        }
        output += ("Kết quả: " + result); 
       

       output += "</html>";
		return output;
	}
	String bai8Tuan3 () {
		String result = "<html>Xâu s1: 'dg' <br/>Xâu s2: 'V lúoasadgdgduabc Quy Databc'<br/>";
		
		String s1 = "dg";
        String s2 = "V lúoasadgdgduabc Quy Databc";
        boolean isS1InS2 = true;
        int count = 0;
        
        for (int i = 0; i < s2.length(); i++) {
            isS1InS2 = true;
            for (int j = 0; j < s1.length(); j++) {
                if (s2.charAt(i+j) == s1.charAt(j)) {
                      
                } else {
                    isS1InS2 = false;
                    break;
                }
            }
            if (isS1InS2) {
                count++;
            }
        }
        result += ("Sâu s1: '" + s1 + "'");
        result += ("<br/>Sâu s2: '" + s2 + "'");
        result += ("<br/>Sâu s1 xuất hiện trong xâu s2 tại " + count + " vị trí."); 
       

		result += "</html>";
		return result;
	}
	String bai9Tuan3 () {
		String output = "<html>Xâu s: '--a--b-'<br/>i = 2; j = 5<br/>";
	
		String s = "--a--b-";
        String result = "";
        int i = 2;
        int j = 5;
        
        for (int index = 0; index < s.length(); index++) {
            if (index == i) {
               result += s.charAt(j);
            } else if (index == j) {
                result += s.charAt(i);
            } else {
                result += s.charAt(index);
            }
        }
        
        output += ("Kết quả: " + result); 
      

       output += "</html>";
		return output;
	}
	String bai10Tuan3 () {
		String result = "<html>Mảng xâu ký tự cho trước: ['ewfwefw', 'aaanvjyugf', 'ffe']<br/>";
	
		String[] array = {"ewfwefw", "aaanvjyugf", "ffe"};
        int lengthCounting = 0;
        String longestString = "";
        
        for (int i = 0; i < array.length; i++) {
            if (lengthCounting < array[i].length()) {
               lengthCounting = array[i].length();
               longestString = array[i];
            }
        }
        
        result += ("Xâu dài nhất trong mảng đã cho là: " + longestString); 
       

		result += "</html>";
		return result;
	}
	String bai11Tuan3 () {
		String result = "<html>Danh sách họ tên học sinh: ['Vu Quy Dat', 'Nguyen Huy Tung', 'Bui An', 'Nguyen Hoang An']<br/>";
	
		String[] array = {"Vu Quy Dat", "Nguyen Huy Tung", "Bui An", "Nguyen Hoang An"};
        String name = "";
        String fullName = "";
        int count = 0;
        
        for (int i = 0; i < array.length; i++) {
           name = "";
           fullName = array[i];
           for (int j = fullName.length() - 1; j > -1; j--) {
               if (fullName.charAt(j) == ' ') {
                    break;
               } else {
                  name += fullName.charAt(j);
               }
           }
               if (name.equals("nA")) {
                   count++;
               }
        }
        
        result += ("Trong mảng đã cho có " + count + " người tên An"); 
   

		result += "</html>";
		return result;
	}
	String bai12Tuan3 () {
		String result = "<html>Danh sách họ tên học sinh: ['Vu Quy Dat', 'Nguyen Thi Thuy', 'Bui Thi An', 'Nguyen Hoang An']<br/>";
	
		String[] array = {"Vu Quy Dat", "Nguyen Thi Thuy", "Bui Thi An", "Nguyen Hoang An"};
        int count = 0;
        String name = "";
        
        for (int j = 0; j < array.length; j++) {
            name = array[j];
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == 'T' && name.charAt(i + 1) == 'h' && name.charAt(i + 2) == 'i') {
                    count++;
                }
            }
        }
        
        result += ("Trong mảng đã cho có " + count + " người có tên Đệm là 'Thị'"); 
      
		result += "</html>";
		return result;
	}
	String bai13Tuan3 () {
		String result = "<html>Danh sách họ tên học sinh: ['Vu Quy Dat', 'Nguyen Hoang', 'Bui Thi Han', 'Nguyen Hoang An']<br/>";
	
		 String[] array = {"Vu Quy Dat", "Nguyen Hoang", "Bui Thi Han", "Nguyen Hoang An"};
         String name = "";
         int count = 0;
         
         for (int j = 0; j < array.length; j++) {
             name = "";
             for (int i = array[j].length() - 1; i > -1; i--) {
               if (array[j].charAt(i) == ' ') {
                    break;
                } else {
                    name += array[j].charAt(i);
                }
            }
            if (name.charAt(name.length() - 1) == 'H') {
                count++;
            }
         }
         
         result += ("Trong mảng đã cho có " + count + " người có tên bắt đầu bằng kí tự 'H'"); 
       

		result += "</html>";
		return result;
	}
	
	 static public String xauSn(int n) { 
	        if (n == 1) { 
	            return "1111100000"; 
	        }
	        return xauSn(n-1).substring(1) + xauSn(n-1).substring(0, 1); 
	    }
	String bai14Tuan3 () {
		String result = "<html>Cho xâu '1111100000'<br/>";
	
		int n = 3; 
		result += ("Xau S" + n + "; Sau khi áp dụng thuật toán theo đề bài ta có kết quả: " + xauSn(n)); 

		result += "</html>";
		return result;
	}
	String bai15Tuan3 () {
		String result = "<html>Cho xâu s1: 'aaaaaaaaaa'; xâu s2: 'wwww'<br/>";
	
		String S1 = "aaaaaaaaaa"; 
        String S2 = "wwww"; 
        String S12 = S2.substring(0, S2.length()/2 + 1) + S1 + S2.substring(S2.length()/2 + 1); 
        result += ("Chen S1 vao giua S2: " + S12); 

		result += "</html>";
		return result;
	}
	String bai16Tuan3 () {
		String result = "<html>Xâu s1: 'ajk' <br/>Xâu s2: 'abcdefghijk'<br/>";
	
		String s1 = "ajk";
        String s2 = "abcdefghijk";
        int currentIndex = 0;
        boolean s1IsChildOfS2 = false;
        
        for (int i = 0; i < s1.length(); i++) {
            if (currentIndex > s1.length()) {
                s1IsChildOfS2 = false;
            }
            for (int j = currentIndex; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    currentIndex = j + 1;
                    s1IsChildOfS2 = true; 
                    break;
                } else if (s1.charAt(i) != s2.charAt(j) && j == s2.length() - 1) { 
                    s1IsChildOfS2 = false;
                    break;
                }
            }
            if (!s1IsChildOfS2) {
                break;
            }
        }
        result += ("s1 " + (s1IsChildOfS2 ? "là " : "không phải ") + "xâu con của s2");

		result += "</html>";
		return result;
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	String bai1Tuan2 () {
		String result = "<html>Mảng input [4, 8, 145, -12, 1, 2]<br/>Mảng đã sắp xếp: ";
		
		 List<Integer> arrays = Arrays.asList(4, 8, 145, -12, 1, 2); 
	        arrays.sort(Collections.reverseOrder()); 
	        result += arrays;

		result += "</html>";
		return result;
	}
	
	 public static int check2(int n) {
		    for(int x = 2; x < n/2; x++) {
	            if (n % x == 0) {
	                return 0;
	            }
	        }
	        return n;
		}
	String bai2Tuan2 () {
		String result = "<html>Các số nguyên tố của dãy: [13, 97, 2, 55, 21, 13, 101, 102]<br/>Kết quả: ";
	
		int[] arr = {13, 97, 2, 55, 21, 13, 101, 102}; 
        for (int i=0; i<arr.length; i++) { 
            if (check2(arr[i]) != 0) {
            	result += check2(arr[i]);
            	result += " ";
            }
        }

		result += "</html>";
		return result;
	}
	String bai3Tuan2 () {
		String result = "<html>Cho dãy số [13, 97, 2, 55, 21, 13, 2, 101, 102, 2] <br/>";
	
		int[] arr = {13, 97, 2, 55, 21, 13, 2, 101, 102, 2}; 
	        int min = arr.length > 0 ? arr[0] : 0;
	        
	        for (int i = 0; i < arr.length; i++) { 
	            if (arr[i] < min) {
	                min = arr[i];
	            }
	        }
	        result += ("Giá trị nhỏ nhất trong dãy: " + min);
	        result += ("<br/>Các chỉ số index tương ứng:");
	        for (int i = 0; i < arr.length; i++) {
	            if (arr[i] == min) {
	            	result += (" " + i);
	            }
	        }

		result += "</html>";
		return result;
	}
	String bai4Tuan2 () {
		String result = "<html>Cho dãy số [13, 97, 2, 102, 55, 102, 21, 13, 2, 101, 102, 2] <br/>";
		
		 int[] arr = {13, 97, 2, 102, 55, 102, 21, 13, 2, 101, 102, 2}; 
	        int max = arr.length > 0 ? arr[0] : 0;
	        
	        for (int i = 0; i < arr.length; i++) { 
	            if (arr[i] > max) {
	                max = arr[i];
	            }
	        }
	        result += ("Giá trị lớn nhất trong dãy: " + max);
	        result += ("<br/>Các chỉ số index tương ứng:");
	        for (int i = 0; i < arr.length; i++) {
	            if (arr[i] == max) {
	            	result += (" " + i);
	            }
	        }

		result += "</html>";
		return result;
	}
	

    static int countZeroAndOne = 0;
	 public static int check3(int n) {
	        if (n == 1 || n == 0) {
	            countZeroAndOne++;
	            return 0;
	        }
		    for(int x = 2; x < n/2; x++) {
	            if (n % x == 0) {
	                return 0;
	            }
	        }
	        return n;
		}
	String bai5Tuan2 () {
		countZeroAndOne = 0;
		String result = "<html>Cho dãy [13, 0, 1, 55, 21, 13, 101, 1]<br/>";

		int[] arr = {13, 0, 1, 55, 21, 13, 101, 1};
        int count = 0;
        for (int i=0; i<arr.length; i++) { 
            if (check3(arr[i]) != 0 ) {
                count++;
            }
        }
        result += ("Số lượng số nguyên tố trong dãy: " + count);
        result += ("<br/>Số lượng hợp số trong dãy: " + (arr.length - count - countZeroAndOne));
      
		
		result += "</html>";
		return result;
	}
	String bai6Tuan2 () {
		String result = "<html>Cho dãy [13, 5, 2, 55, 21, 13, 104, 1, 20, 8]<br/>Kết quả: ";
		
		 int[] arr = {13, 5, 2, 55, 21, 13, 104, 1, 20, 8};
	        
	        for (int i=0; i<arr.length; i++) { 
	            for (int j=0; j<arr.length; j++) { 
	                if (arr[j] != arr[i] && arr[j] % arr[i] == 0) {
	                	result += (arr[i] + " ");
	                    break;
	                }
	            }
	        }

		result += "</html>";
		return result;
	}
	String bai7Tuan2 () {
		String result = "<html>Cho dãy số [2, 2, 8, 13, 1, 1, 20, 8]<br/>Kết quả: ";
	
		int[] arr = {2, 2, 8, 13, 1, 1, 20, 8};
        Arrays.sort(arr);
        boolean didFindOutResult = false;
        
        for (int i=0; i<arr.length; i++) { 
            for (int j=0; j<arr.length; j++) { 
                if (j != i && arr[j] == arr[i]) {
                    break;
                }
                
                if (j == arr.length - 1) {
                	result += (arr[i] + " ");
                    didFindOutResult = true;
                    break;
                }
            }
            if (didFindOutResult) {
                break;
            }
        }

		result += "</html>";
		return result;
	}
	String bai8Tuan2 () {
		String result = "<html>Cho dãy số [2, 0, 0, 0, 1, 0, 20, 8]<br/>Kết quả: ";
	
		int[] arr = {2, 0, 0, 0, 1, 0, 20, 8};
        Arrays.sort(arr);
        ArrayList<Integer> arraylist = new ArrayList<Integer>(); 
        for (int i=0; i < arr.length; i++) { 
            arraylist.add(arr[i]); 
        } 
        
        for (int i=0; i<arraylist.size(); i++) { 
            if (arraylist.get(i) == 0) { 
                arraylist.remove(i); 
                i--; 
            } else {
            	result += (arraylist.get(i) + " ");
            }
        }

		result += "</html>";
		return result;
	}
	String bai9Tuan2 () {
		String result = "<html>Cho dãy số [2, 0, 0, 0, 1, 0, 20, 8]<br/>";
	
		int[] arr = {2, 0, 0, 0, 1, 0, 20, 8};
        int c = 1;
        int lt = 0;
        int eq = 0;
        int gt = 0;
        
        for (int i=0; i<arr.length; i++) { 
            if (arr[i] < c) {
                lt++;
            } else if (arr[i] == c) {
                eq++;
            } else {
                gt++;
            }
        }
        result += ("Số lượng các số trong dãy nhỏ hơn " + c + " là " + lt);
        result += ("<br/>Số lượng các số trong dãy bằng " + c + " là " + eq);
        result += ("<br/>Số lượng các số trong dãy lớn hơn " + c + " là " + gt);
        

				result += "</html>";
				return result;
			}
	String bai10Tuan2 () {
		String result = "<html>Cho dãy số [2 ,2 ,2, 5, 5, 5, 5, 1, 0, 20, 8]<br/>";
	
		int[] arr = {2 ,2 ,2, 5, 5, 5, 5, 1, 0, 20, 8};
        int firstIndex = 0;
        int count = 1;
        
        int subFirstIndex = 0;
        int subCount = 1;
        
        boolean isContinuity = true;
        
        for (int i=0; i<arr.length; i++) {
            subFirstIndex = i;
            subCount = 1;
            isContinuity = true;
            
            for (int j = subFirstIndex; j<arr.length - 1; j++) { 
                if (isContinuity) {
                    if (arr[i] == arr[j + 1]) {
                        subCount++;
                    } else {
                        isContinuity = false;
                    }
                }
            }
            
            if (subCount > count) {
                count = subCount;
                firstIndex = subFirstIndex;
            }
        }
        result += ("fisrt index: " + firstIndex + "   " + "số lượng: " + count);
       

				result += "</html>";
				return result;
			}
	
	

	String bai11Tuan2 () {
		String result = "<html>Cho dãy số [2 ,3 ,5, 5, 6, 7, 8, 9, 10, 20, 81]<br/>";
	
		int[] arr = {2 ,3 ,5, 5, 6, 7, 8, 9, 10, 20, 81};
        int firstIndex = 0;
        int count = 1;
        
        int subFirstIndex = 0;
        int subCount = 1;
        
        boolean isContinuity = true;
        
        for (int i=0; i<arr.length; i++) {
            subFirstIndex = i;
            subCount = 1;
            isContinuity = true;
            
            for (int j = subFirstIndex; j<arr.length - 1; j++) { 
                if (isContinuity) {
                    if (arr[j] <= arr[j + 1]) {
                        subCount++;
                    } else {
                        isContinuity = false;
                    }
                }
            }
            
            if (subCount > count) {
                count = subCount;
                firstIndex = subFirstIndex;
            }
        }
        result += ("dãy con liên tục đơn điệu tăng dài nhất của dãy đã cho có fisrt index: " + firstIndex + "   " + "số lượng: " + count);
        result += ("<br/>dãy con: ");
        for (int i = firstIndex; i < firstIndex + count; i++) {
        	result += (arr[i] + " ");
        }

				result += "</html>";
				return result;
			}

	String bai12Tuan2 () {
		String result = "<html>Cho dãy a[] = [2 ,5, 6, 7, 81, 81], <br/>b[] = [2 ,3 ,5, 5, 6, 7, 8, 9, 10, 20, 81]<br/>";
	

        int[] b = {2 ,3 ,5, 5, 6, 7, 8, 9, 10, 20, 81};
        int[] a = {2 ,5, 6, 7, 81, 81};
        int currentIndex = 0;
        boolean aIsChildOfB = false;
        
        for (int i = 0; i < a.length; i++) {
            if (currentIndex > a.length) {
                aIsChildOfB = false;
            }
            for (int j = currentIndex; j < b.length; j++) {
                if (a[i] == b[j]) {
                    currentIndex = j + 1;
                    aIsChildOfB = true; 
                    break;
                } else if (a[i] != b[j] && j == b.length - 1) { 
                    aIsChildOfB = false;
                    break;
                }
            }
            if (!aIsChildOfB) {
                break;
            }
        }
        result += ("<br/>a " + (aIsChildOfB ? "là " : "không phải ") + "dãy con của b");
       

		result += "</html>";
		return result;
	}
	
	
	
		String bai1Tuan1 () {
			int n = 100;
			String result = "<html>";
			for (int i = 4; i < n; i++) {
				for (int j = 2; j < i; j++) {
					if (i % j == 0) {
						result += (i + " ");
						break;
					}
				}
			}
			result += "</html>";
			return result;
		}
		String bai2Tuan1 () {
			String result = "<html>";
			int count = 0;
		    int n = 2;
		    boolean nextNumber = false;
		    while(count < 20) {
		    	nextNumber = false;
		    	for(int x = 2; x < n; x++) {
		    		if (n % x == 0) {
		    			nextNumber = true;
		    			break;
		    		}
		    	}
		    	if (nextNumber == false) {
		    		result += (n + " ");
		    		count++;
		    	}
		    	n++;
		    }
			result += "</html>";
			return result;
		}
		String bai3Tuan1 () {
			String result = "<html>";
			int count = 0;
	        int n = 1000;
	        boolean nextNumber = false;
	        boolean nextLine = true;
	        while(n >= 1000 && n <= 2000) {
	            nextNumber = false;
	            for(int x = 2; x < n; x++) {
	                if (n % x == 0) {
	                    nextNumber = true;
	                    break;
	                }
	            }
	            if (nextNumber == false) {
		    		result += (n + " ");
	                count++;
	                nextLine = true;
	            }
	            if (count % 10 == 0 && nextLine == true) {
	                nextLine = false;
	            }
	            n++;
	        }
			result += "</html>";
			return result;
		}
		String bai4Tuan1 () {
			String result = "<html>";
			for(int i = 0; i < 100; i++) {
	            if (i % 3 == 0) {
	                if (i % 7 == 0) {
			    		result += (i + " ");
	                }
	            }
	        }
			result += "</html>";
			return result;
		}
		String bai5Tuan1 () {
			String result = "<html>";
			int n = 1000;
	        while(n >= 1000 && n <= 2000) {
	            
	            if (n % 3 == 0) {
	                if (n % 5 == 0) {
	                    if (n % 7 == 0) {
	    		    		result += (n + " ");
	                    }
	                }
	            }
	            n++;
	        }
			result += "</html>";
			return result;
		}
		String bai6Tuan1 () {
			String result = "<html> 6 26 496 8128 ";

//			int count = 0;
//	        int i = 1;
//	        while(count < 6) {
//	            int sum = 0;
//	            for(int j = 1; j <= i / 2; j++) {
//	                if (i % j == 0) {
//	                    sum += j;
//	                }
//	            }
//	            if (sum == i) {
//	            	result += (i + " ");
//	                count++;
//	            }
//	            
//	            i++;
//	        }
			
			result += "</html>";
			return result;
		}
		String bai7Tuan1 () {
			String result = "<html>";

	        int a = 0;
	        int b = 0;
	        int c = 0;
	        int d = 0;
	        for(int i = 0; i <= 100; i++) {
	            if (i % 5 == 0) {
	                a++;
	            }
	            if (i % 5 == 1) {
	                b++;
	            }
	            if (i % 5 == 2) {
	                c++;
	            }
	            if (i % 5 == 3) {
	                d++;
	            }
	        }

    		result += ("Chia hết cho 5: " + a + " số<br/>");
    		result += ("Chia hết cho 5 dư 1: " + b + " số<br/>");
    		result += ("Chia hết cho 5 dư 2: " + c + " số<br/>");
    		result += ("Chia hết cho 5 dư 3: " + d + " số<br/>");

			result += "</html>";
			return result;
		}
		String bai8Tuan1 () {
			String result = "<html>n bằng 6: <br/>Kết quả: ";

			int n = 6;
	        boolean isPrime = true;
	        for (int i = 2; i < n / 2; i++) {
	            isPrime = true;
	            if (n % i == 0) {
	                
	                for (int j = 2; j < i / 2; j++) {
	                    if (i % j == 0) {
	                        isPrime = false;
	                        break;
	                    }
	                }
	                
	                if (isPrime) {

			    		result += (i + " ");
	                }
	                
	            }
	        }
			
			result += "</html>";
			return result;
		}
		
		public static boolean check(long n) {
		    if(n % 2 == 0 || n < 2) return false;
			int sqrt = (int)Math.sqrt(n);
			for(int i = 3; i <= sqrt; i+=2) {
				if(n % i == 0) return false;
			}
			return true;
		}
		
		public static long nextAutomic(long i) {
			if(i == 2) return 3;
			i+=2;
			while(!check(i)) i+=2;
			return i;
		}
		String bai9Tuan1 () {
			String result = "<html>n bằng 1098: <br/>Kết quả: ";

	        long n = 1098;
			long tmp = 2;
			StringBuilder str = new StringBuilder(n + " = ");
			boolean flag = true;
			while(n > 1) {
				while(n % tmp == 0) {
					if(flag) {
						str.append(tmp);
						flag = false;
					}else str.append(" * " + tmp);
					n/=tmp;
				}
				tmp = nextAutomic(tmp);
			}
			result += str;
			
			result += "</html>";
			return result;
		}
		
	    public static boolean check1(long n) {
			if(n % 2 == 0 || n < 2) return false;
			int sqrt = (int)Math.sqrt(n);
			for(int i = 3; i <= sqrt; i+=2) {
				if(n % i == 0) return false;
			}
			return true;
		}	
		
		public static long nextStep(long i) {
			if(i == 2) return 3;
			i += 2;
			while(!check1(i)) i+=2;
			return i;
		}
		String bai10Tuan1 () {
			String result = "<html> N bằng 28: <br/>Kết quả: ";

			long n = 28;
			long i = 2;
			while(n > 1) {
				boolean flag = true;
				while(n % i == 0) {
					if(flag) {
						result += (i + " ");
						flag = false;
					}
					n/=i;
				}
				i = nextStep(i);
			}
			
			result += "</html>";
			return result;
		}
		String bai11Tuan1 () {
			String result = "<html>Dãy cho trước là '1, 2, 3, 4, 5, 6, 7, 8, 9' <br/>";

			int arr[] = {1,2,3,4,5,6,7,8,9};
			int size = arr.length;
			if(size < 2) result += "khong doi cho";
			else {
				result += "truoc: ";
				for(int i = 0; i < size; i++) {
					result += (arr[i] + " ");
				}

				result += ("<br/>");
				Random rd = new Random();
				int index = rd.nextInt(size);
				int id = rd.nextInt(size);
				while(index == id) {
					id = rd.nextInt(size);
				}
				
				// Đổi chỗ các phần tử trong mảng
				int tmp = arr[index];
				arr[index] = arr[id];
				arr[id] = tmp;
				
				result += "sau: ";
				
				for(int i = 0; i < size; i++) {
					result += (arr[i] + " ");
				}
			}
			
			result += "</html>";
			return result;
		}
		String bai12Tuan1 () {
			String result = "<html>";
        
            int h=0, select=0;          
            while((h<2) || (h>10)) {                       
                h = 9;           
            }           
            while((select<1) || (select>2)) {           
                select = 1;           
            }                      
            if(select == 1) {         
            	for(int i=1; i<=h; i++) {
            		for(int j=1; j<=(h-i); j++)               
            			result += " ";         
                          for(int j=1; j<=(2*i-1); j++) {
                        	  result += "*";
                          }
                          result += "<br/>";    
                          }
  
            } else {    
            	for(int i=h; i>=1; i--) {
            		for(int j=1; j<=(h-i); j++)               
            			result += " ";         
                          for(int j=1; j<=(2*i-1); j++) {    
                        	  result += "*";
                          }
                          result += "<br/>";    
                          }
            	}
		
			
			result += "</html>";
			return result;
		}
		String bai1BTuan1 () {
			String result = "<html>N = 20: <br/>Kết quả: ";
		
			int n = 20;
			int sum = 0;
			double total = 0;
			for(int i = 1; i <= n; i++) {
				sum+=i;
				total += 1.0/sum;
			}
            result += total;

			result += "</html>";
			return result;
		}
		String bai2BTuan1 () {
			String result = "<html>N = 20: <br/>Kết quả: ";
		
			int n = 20;
				int sum = 1;
				double total = 0;
				for(int i = 1; i <= n; i++) {
					sum*=i;
					total += 1.0/sum;
				}
	        result += total;

			result += "</html>";
			return result;
		}
		String bai3BTuan1 () {
			String result = "<html>N = 5: <br/>Kết quả: ";
		
			 int n = 5;
				long sum = 1;
				long arr[] = new long[n];
				for(int i = 1; i <= n; i++) {
					sum*=i;
					arr[i - 1] = sum;
				}
				for(int i = 1; i < n; i++) {
					arr[i]+=arr[i - 1];
				}
				double total = 0;
				for(int i = 0; i < n; i++) {
					total += 1.0/arr[i];
				}
		        result += total;

			result += "</html>";
			return result;
		}
		String bai4BTuan1 () {
			String result = "<html>N = 15:<br/>";
		
			 long n = 15;
				long a1 = 0;
				long a2 = 1;
				long savedN = n;
				if(n == 0) result += a1;
				else if(n == 1) result += a2;
				else {
					long a;
					while(n > 1) {
						a2 = a1 + a2;
						a1 = a2 - a1;
						n--;
					}
					result += ("so fibonanxi thu " + savedN + " là " + a2);
				}

			result += "</html>";
			return result;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		JButton bai2 = new JButton("Bài 2");
//		bai2.setBounds(100, 0, 100, 30);
//		getContentPane().add(bai2);
//		
//		JButton bai3 = new JButton("Bài 3");
//		bai3.setBounds(200, 0, 100, 30);
//		getContentPane().add(bai3);
//
//		JButton bai4 = new JButton("Bài 4");
//		bai4.setBounds(300, 0, 100, 30);
//		getContentPane().add(bai4);
		

		
		
//	}
	
	
	
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Week1Dialog window = new Week1Dialog();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public Week1Dialog() {
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

}
