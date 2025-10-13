
package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Login extends JFrame implements ActionListener{
    JButton login,signup,clear;
    JTextField cardNO;
    JPasswordField pinNO;
    Login(){
        setTitle("AUTOMATED TELLER MACHINE");
        setLayout(null);
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2=i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel label=new JLabel(i3);
        label.setBounds(70,10,100,100);
        add(label);
        
        JLabel text=new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward",Font.BOLD,38));
        text.setBounds(200,40,400,40);
        add(text);
        
        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Osward", Font.BOLD, 14));
        dateLabel.setBounds(150, 100, 600, 40);
        add(dateLabel);

        Timer timer = new Timer(1000, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a");
        String currentTime = sdf.format(new java.util.Date());
        dateLabel.setText("Current Date and Time: " + currentTime);
             }
        });
        timer.start();

        
        JLabel cardno=new JLabel("Card No: ");
        cardno.setFont(new Font("Osward",Font.BOLD,28));
        cardno.setBounds(120,150,400,40);
        add(cardno);
        
        JLabel pin=new JLabel("PIN:");
        pin.setFont(new Font("Osward",Font.BOLD,28));
        pin.setBounds(120,220,400,40);
        add(pin);
        
        cardNO=new JTextField();
        cardNO.setBounds(300,150,230,30);
        add(cardNO);
        
        pinNO=new JPasswordField();
        pinNO.setBounds(300,220,230,30);
        add(pinNO);
        
        login=new JButton("Sign IN");
        login.setBounds(300,270,100,30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        clear=new JButton("Clear");
        clear.setBounds(430,270,100,30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);
        
        signup=new JButton("SIGN UP");
        signup.setBounds(300,320,230,30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);
        
        getContentPane().setBackground(Color.white);
        
        
        
        
        setSize(600,400);
        setVisible(true);
        setLocation(350,200);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== clear){
            cardNO.setText("");
            pinNO.setText("");
            
        }
        else if(ae.getSource()== signup){
            setVisible(false);
            new SignUPOne().setVisible(true);
        }
        else if(ae.getSource()== login){
           Conn c=new Conn();
           String cardnum=cardNO.getText();
           String pinnum=pinNO.getText();
           
            if (cardnum.matches("^$") || pinnum.matches("^$"))  {
            JOptionPane.showMessageDialog(null,"Card No or PIN is empty. Please enter to proceed");
            }
            else if (cardnum.matches(".*[a-zA-Z!@#$%^&*].*")) {
            JOptionPane.showMessageDialog(null,"Card number cannot contain alphabets or special symbols.");
            }
            else if (cardnum.matches("^\\s+|\\s+$|.*\\s+.*")) {
            JOptionPane.showMessageDialog(null,"Card number cannot contain spaces");
            }
//            else if (cardnum.length()<=18) {
//            JOptionPane.showMessageDialog(null,"Card number must be minimum 18 digits.");
//            }
            else if (pinnum.matches(".*[a-zA-Z!@#$%^&*].*")) {
            JOptionPane.showMessageDialog(null,"PIN cannot contain alphabets or special symbols.");
            }
            else if (pinnum.matches("^\\s+|\\s+$|.*\\s+.*")) {
            JOptionPane.showMessageDialog(null,"Pin cannot contain spaces");
            }
           else{
           String query="select * from login where cardnumber = '"+cardnum+"' and pin = '"+pinnum+"'";
           try{
               ResultSet rs=c.s.executeQuery(query);
               if(rs.next()){
                   setVisible(false);
                   new Transactions(pinnum,cardnum).setVisible(true);
               }else{
                   JOptionPane.showMessageDialog(null,"Incorrect Card Number or PIN");
               }
           }catch(Exception e){
               System.out.println(e);
           }
           }
        }
    }
    
    public static void main(String args[]){
        new Login();
    }
    
}