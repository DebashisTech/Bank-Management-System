package bank.management.system;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MiniStatement extends JFrame implements ActionListener {
    String pinno, cardno;
    JButton back;
    JLabel mini, beneNameLabel;

    MiniStatement(String pinno, String cardno) {
        this.pinno = pinno;
        this.cardno = cardno;
        setLayout(null);

        Font f = new Font("Arial", Font.BOLD, 20);
        JLabel bank = new JLabel("INDIAN BANK");
        bank.setFont(f);
        bank.setBounds(150, 20, 200, 20);
        add(bank);
        
        Font fn = new Font("Times New Roman", Font.BOLD, 20);
        JLabel ministmt = new JLabel("Mini Statement");
        ministmt.setFont(fn);
        ministmt.setBounds(150, 50, 200, 20);
        add(ministmt);

        // Fetch beneficiary name
        String beneName = "";
        try {
            String query1 = "select formno from login where cardnumber = '" + cardno + "'";
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery(query1);
            if (rs.next()) {
                String formno = rs.getString("formno");
                String query2 = "select name from signup where formno = '" + formno + "'";
                ResultSet rs2 = conn.s.executeQuery(query2);
                if (rs2.next()) {
                    beneName = rs2.getString("name");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel beneNameLabel = new JLabel("Beneficiary Name: " + beneName);
        beneNameLabel.setBounds(20, 80, 300, 20);
        add(beneNameLabel);

        JLabel card = new JLabel("Card Number: " + cardno);
        card.setBounds(20, 100, 300, 20);
        add(card);

        // Font for last 5 transactions
        Font font = new Font("Arial", Font.BOLD, 14);

        JLabel date = new JLabel("<html><u>DATE</u></html>");
        date.setFont(font);
        date.setForeground(Color.BLUE);
        date.setBounds(60, 130, 40, 20);
        add(date);

        JLabel type = new JLabel("<html><u>TYPE</u></html>");
        type.setFont(font);
        type.setForeground(Color.BLUE);
        type.setBounds(165, 130, 40, 20);
        add(type);

        JLabel amount = new JLabel("<html><u>AMOUNT</u></html>");
        amount.setFont(font);
        amount.setForeground(Color.BLUE);
        amount.setBounds(250, 130, 80, 20);
        add(amount);

        mini = new JLabel("<html>");
        mini.setBounds(20, 140, 400, 200);

        try {
            String query3 = "select * from logs where cardnumber = '" + cardno + "' order by daate DESC limit 5";
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query3);
            while (rs.next()) {
                mini.setText(mini.getText() + rs.getString("daate") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
                            + rs.getString("transaction") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
                            + rs.getString("amount") + "<br><br><html>");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        mini.setText(mini.getText() + "</html>");
        add(mini);

        back = new JButton("Back");
        back.setBounds(200, 431, 130, 26);
        back.setBackground(Color.white);
        back.addActionListener(this);
        add(back);

        setSize(500, 500);
        setLocation(20, 20);
        getContentPane().setBackground(Color.white);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinno, cardno).setVisible(true);
        }
    }

    public static void main(String args[]) {
        //new MiniStatement("3458", "8477464230981842685");
        new MiniStatement("pinno","cardno"); 
        
    }
}
