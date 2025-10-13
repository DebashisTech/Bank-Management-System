package bank.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PinChange extends JFrame implements ActionListener {
    JTextField balanceTF,oldpin,newpin,confpin;
    JButton back,submit;
    JLabel balance;
    String pinnum,cardnum;
    int amt;
   
    
   PinChange(String pinnum,String cardnum){
       this.pinnum=pinnum;
       this.cardnum=cardnum;
       setLayout(null);
       ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
       Image i2=i1.getImage().getScaledInstance(750,750,Image.SCALE_DEFAULT);
       ImageIcon i3=new ImageIcon(i2);
       JLabel image=new JLabel(i3);
       image.setBounds(0,0,750,750);
       add(image);
       
       oldpin= new JTextField();
       oldpin.setBounds(170,270,100,30);
       oldpin.setFont(new Font("Raleway",Font.BOLD,12));
       oldpin.setText("OLD PIN");
       oldpin.addFocusListener(new FocusListener(){
           public void focusGained(FocusEvent event){
               if(oldpin.getText().equals("OLD PIN")){
                   oldpin.setText("");
               }
           }
           
           public void focusLost(FocusEvent event){
                if(oldpin.getText().equals("")){
                    oldpin.setText("OLD PIN");
                }
           }
       });
       image.add(oldpin);
       
        newpin= new JTextField("Old PIN");
       newpin.setBounds(170,320,100,30);
       newpin.setFont(new Font("Raleway",Font.BOLD,12));
       newpin.setText("NEW PIN");
       newpin.addFocusListener(new FocusListener(){
           public void focusGained(FocusEvent event){
               if(newpin.getText().equals("NEW PIN")){
                   newpin.setText("");
               }
           }
           
           public void focusLost(FocusEvent event){
                if(newpin.getText().equals("")){
                    newpin.setText("NEW PIN");
                }
           }
       });
       image.add(newpin);
       
        confpin= new JTextField();
       confpin.setBounds(170,370,100,30);
       confpin.setFont(new Font("Raleway",Font.BOLD,12));
       confpin.setText("CONFIRM PIN");
       confpin.addFocusListener(new FocusListener(){
           public void focusGained(FocusEvent event){
               if(confpin.getText().equals("CONFIRM PIN")){
                   confpin.setText("");
               }
           }
           
           public void focusLost(FocusEvent event){
                if(confpin.getText().equals("")){
                    confpin.setText("CONFIRM PIN");
                }
           }
       });
       image.add(confpin);
        
        
         submit=new JButton("Submit");
        submit.setBounds(300,404,130,26);
        submit.setBackground(Color.white);
        submit.addActionListener(this);
        image.add(submit);
        
        
        
     
        
        back=new JButton("Back");
        back.setBounds(300,431,130,26);
        back.setBackground(Color.white);
        back.addActionListener(this);
        image.add(back);
        

        
       
       setSize(750,750);
       setLocation(300,0);
       setVisible(true);
   
   
   }
    
    public void actionPerformed(ActionEvent ae){
        String piin;
        int count=0;
        if(ae.getSource()==back){
            setVisible(false);
            new Transactions(pinnum,cardnum).setVisible(true);
        }else if(ae.getSource()==submit){
            if(newpin.getText().equals("")){
                JOptionPane.showMessageDialog(null,"NEW PIN can't be EMPTY");
            }
            else if(confpin.getText().equals(""))
                JOptionPane.showMessageDialog(null,"ENTER NEW PIN AGAIN ");
            else if(!(newpin.getText().equals(confpin.getText())))
                JOptionPane.showMessageDialog(null,"New PIN and Confirm PIN don't match");
            else{
                piin=newpin.getText();
                if (piin.matches(".*[a-zA-Z].*")) {
                    JOptionPane.showMessageDialog(null,"PIN number cannot contain alphabets");
                }else{
                try{
                Conn c=new Conn();
                String query1="update accounts set pin = '"+piin+"' where cardnumber = '"+cardnum+"'";
                int rep=c.s.executeUpdate(query1);
                if(rep>0)
                    count+=1;
                String query2="update login set pin = '"+piin+"' where cardnumber = '"+cardnum+"'";
                rep=c.s.executeUpdate(query2);
                if(rep>0)
                    count+=1;
                 String query3="update signupthree set pinno = '"+piin+"' where cardnumber = '"+cardnum+"'";
                rep=c.s.executeUpdate(query3);
                setVisible(false);
                if(rep>0)
                    count+=1;
                
                }catch(Exception e){
                    System.out.println(e);
                }
                }
                if(count==3){
                    JOptionPane.showMessageDialog(null,"PIN NUMBER has been updated");
                    new Transactions(piin,cardnum);
                }
            }   
            
        }
    }
    
    public static void main(String args[]){
        new PinChange("","");
    }
    
}
