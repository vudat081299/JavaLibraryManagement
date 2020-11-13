////import java.awt.Dimension;
////
////import javax.swing.ImageIcon;
////import javax.swing.JButton;
////import javax.swing.JFrame;
////import javax.swing.JLabel;
////import java.awt.ActiveEvent;
////import java.awt.*;
////import javax.swing.*;
////public class Test {
////	JFrame frame;
////	ImageIcon image1;
////	ImageIcon image2;
////	JButton button;
////	JLabel label;
////	
////	public Test () {
////		button = new JButton("click ");
////		button.setBounds(150,50,100,60);
////		frame = new JFrame("vudat81299");
////	frame.setSize(new Dimension(420, 420));
////	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////	frame.add(button);
////	frame.setVisible(true);
////	}
////	
////}
//
   import java.util.Scanner;
      public class Bai12 {

             public static void printStar(int h, int i) {           
                 for(int j=1; j<=(h-i); j++)               
                 System.out.print(" ");           
                 for(int j=1; j<=(2*i-1); j++)              
                 System.out.print("*");           
                 System.out.println();      
                 }      
                 public static void main(String[] args) {           
                     System.out.println("Vũ Quý Đạt 20176082");           
                     Scanner sc = new Scanner(System.in);          
                     int h=0, select=0;          
                     while((h<2) || (h>10)) {              
                         System.out.print("Nhập h thuộc [2;10]: ");              
                         h = sc.nextInt();           
                     }           
                     while((select<1) || (select>2)) {               
                         System.out.println("\n1.In xuôi 2.In ngược");              
                         System.out.print("Bạn chọn: ");               
                         select = sc.nextInt();           
                     }           
                     sc.close();           
                     if(select == 1)               
                     for(int i=1; i<=h; i++)                   
                     printStar(h,i);           
                     else               
                     for(int i=h; i>=1; i--)                   
                     printStar(h,i);      
                      
             
        
     }
}