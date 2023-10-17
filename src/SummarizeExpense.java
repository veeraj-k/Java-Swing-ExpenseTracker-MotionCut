import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class SummarizeExpense implements ActionListener {
    JTextArea yearBox;
    JComboBox monthBox;
    Connection con;

    JPanel selectionPanel;
    JFrame summPage;
    JButton summarize;
    Statement st;
    JTable jt;
    SummarizeExpense(){

        //summarize page setup
        summPage = new JFrame("Summarize Expense");

        JLabel summLabel =new JLabel("Summarize expense");

        summLabel.setBackground(Color.BLACK);
        summLabel.setForeground(Color.WHITE);
        summLabel.setHorizontalAlignment(JLabel.CENTER);
        summLabel.setFont(new Font("MV Boli",Font.PLAIN,30));

        summPage.getContentPane().setBackground(Color.BLACK);
        summPage.add(summLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        selectionPanel =new JPanel();


        selectionPanel.setLayout(new GridLayout(1,4));

        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setForeground(Color.white);
        monthLabel.setHorizontalAlignment(JLabel.CENTER);

        //items for months selection box
        String[] months=
                {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December",
                        "Whole Year"

                };

        monthBox = new JComboBox(months);
        yearBox = new JTextArea();

        JLabel yearLabel = new JLabel("Year");
        yearLabel.setForeground(Color.white);
        yearLabel.setHorizontalAlignment(JLabel.CENTER);

        summarize = new JButton("Summarize");
        summarize.addActionListener(this);

        selectionPanel.add(monthLabel);
        selectionPanel.add(monthBox);

        selectionPanel.add(yearLabel);
        selectionPanel.add(yearBox);

        selectionPanel.add(summarize);

        selectionPanel.setBackground(Color.BLACK);

        centerPanel.setLayout(new BorderLayout());


        centerPanel.add(selectionPanel,BorderLayout.NORTH);
        summPage.add(centerPanel,BorderLayout.CENTER);

        summPage.setVisible(true);
        summPage.setSize(900,600);
        summPage.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        jt = new JTable();
        jt.setBackground(Color.BLACK);
        jt.setForeground(Color.WHITE);
        jt.setFont(new Font("Time New Roman",Font.PLAIN,16));
        jt.getTableHeader().setFont(new Font("Time New Roman",Font.PLAIN,18));
        jt.getTableHeader().setBackground(Color.gray);
        jt.getTableHeader().setForeground(Color.white);

        centerPanel.add(new JScrollPane(jt),BorderLayout.CENTER);


//        Sql Connection
        try{
            con =MyConnection.connect();
            st = con.createStatement();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String month= (String) monthBox.getSelectedItem();
            String query,iner="";


            switch (month) {
                case "January":
                    iner = "01";
                    break;
                case "February":
                    iner = "02";
                    break;
                case "March":
                    iner = "03";
                    break;
                case "April":
                    iner = "04";
                    break;
                case "May":
                    iner = "05";
                    break;
                case "June":
                    iner = "06";
                    break;
                case "July":
                    iner = "07";
                    break;
                case "August":
                    iner = "08";
                    break;
                case "September":
                    iner = "09";
                    break;
                case "October":
                    iner = "10";
                    break;
                case "November":
                    iner = "11";
                    break;
                case "December":
                    iner = "12";
                    break;
            }

            //condition to check for whole year or single month
            if(month.charAt(0)=='W'){
                query=String.format("SELECT category,SUM(cost) FROM expenses WHERE strftime('%%Y', date)='%1$s' GROUP BY category;", yearBox.getText());
            }
            else{
                query= String.format("SELECT category,SUM(cost) FROM expenses WHERE strftime('%%m', date)='%1$s' AND strftime('%%Y', date)='%2$s' GROUP BY category;", iner, yearBox.getText());
            }

            ResultSet rst = st.executeQuery(query);

            //retriving data from sql
            String[] cols={"Category" , "Cost"};
            DefaultTableModel dtm = new DefaultTableModel(cols,0);
            boolean isEmpty=true;
            int total=0;
            while (rst.next()){
                isEmpty=false;
                Object[] row = new Object[cols.length];
                for(int i=1;i<=cols.length;i++){
                    row[i-1]=rst.getString(i);
                }
                total+=Integer.parseInt(rst.getString(2));
                dtm.addRow(row);
            }
            //adding inot the Jtable
            if(isEmpty){
                dtm.setRowCount(0);
                JOptionPane.showMessageDialog(null,"No data retrieved!!","Warning",JOptionPane.PLAIN_MESSAGE);
                jt.setModel(dtm);
            }
            else {
                Object[] totRow= {"Total Expense:","₹ "+Integer.toString(total)};
                dtm.addRow(totRow);
                jt.setModel(dtm);
            }
        }
        catch (Exception et){
            et.printStackTrace();
        }
    }
}
