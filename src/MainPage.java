import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SwingMainPage implements ActionListener {
    JButton addExpense,viewExpense,summarizeExpense;
    SwingMainPage(){

        //Main Frame setup
        JFrame frame = new JFrame("Expense Tracker");
        frame.setLayout(new BorderLayout(10,10));
        JLabel label = new JLabel("Expense Tracker");
        label.setFont(new Font("MV Boli",Font.PLAIN,30));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);

        //dividing the mainframe to fit in all the components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,1,2,2));
        centerPanel.setBackground(Color.black);
        JPanel pan1 = new JPanel();
        pan1.setBackground(Color.black);
        JPanel pan2 = new JPanel();
        pan2.setBackground(Color.black);
        JPanel pan3 = new JPanel();
        pan3.setBackground(Color.black);
        addExpense = new JButton("Add Expense");
        viewExpense = new JButton("View Expense");
        summarizeExpense = new JButton("Summarize Expense");

        addExpense.setPreferredSize(new Dimension(300,60));
        viewExpense.setPreferredSize(new Dimension(300,60));
        summarizeExpense.setPreferredSize(new Dimension(300,60));

        //adding actionlistener to simulate button clicks
        addExpense.addActionListener(this);
        viewExpense.addActionListener(this);
        summarizeExpense.addActionListener(this);

        Border bgwh = BorderFactory.createLineBorder(Color.white);

        pan1.add(addExpense);
        pan2.add(viewExpense);
        pan3.add(summarizeExpense);

        centerPanel.add(pan1);
        centerPanel.add(pan2);
        centerPanel.add(pan3);

        frame.add(label,BorderLayout.NORTH);
        frame.add(centerPanel,BorderLayout.CENTER);

        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println(s);

        //checking on which button has been clicked
        if(s.charAt(0)=='V'){

            new ViewPage();
        }
        else if(s.charAt(0)=='A'){
            new AddExpense();
        }
        else{
            new SummarizeExpense();
        }

    }
}

public class MainPage {
    public static void main(String[] args) {

        com.formdev.flatlaf.FlatDarkLaf.install();
        new SwingMainPage();
    }
}
