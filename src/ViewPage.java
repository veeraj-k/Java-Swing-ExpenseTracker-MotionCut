import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class ViewPage{
    JFrame f2;
    ViewPage() {
        try {
            f2 = new JFrame("View Page");
            f2.getContentPane().setBackground(Color.BLACK);
            f2.setVisible(true);
            f2.setSize(900, 500);

            //sql connection using jdbc
            Connection conn = MyConnection.connect();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery("SELECT * FROM expenses ORDER BY date DESC;");

            ArrayList<String[]> dt = new ArrayList<String[]>();

            //retriving data from mysql resultset
            while (rst.next()) {
                String[] inner = new String[5];
                inner[0] = rst.getString(1);
                inner[1] = rst.getString(2);
                inner[2] = rst.getString(3);
                inner[3] = rst.getString(4);
                inner[4] = rst.getString(5);
                dt.add(inner);
            }
            String[][] data = new String[dt.size()][];

            dt.toArray(data);



            //if empty set
            if(dt.size()==0){
                this.showWarn();
            }
            else{
                String [] col = new String[]{"Item","Category", "Description", "Cost","Date"};

                JTable jt = new JTable(data, col) {
                    public boolean editCellAt(int row, int column, java.util.EventObject e) {
                        return false;
                    }
                };
                jt.setFont(new Font("MV Boli",Font.PLAIN,15));
                jt.setForeground(Color.white);
                jt.setBackground(new Color(0,0,0));
                jt.getTableHeader().setBackground(new Color(38, 37, 37));
                jt.getTableHeader().setForeground(Color.white);
                jt.getTableHeader().setFont(new Font("MV Boli",Font.PLAIN,15));
                JScrollPane js = new JScrollPane(jt);

                f2.add(js);
                f2.pack();
            }


        }
        catch (Exception e) {
            this.showWarn();
        }
    }

     void showWarn(){
        JLabel msg = new JLabel("No data Found!!");
        msg.setBackground(Color.BLACK);
        msg.setForeground(Color.RED);
        msg.setFont(new Font("MV Boli",Font.PLAIN,20));
        msg.setHorizontalAlignment(JLabel.CENTER);
        f2.add(msg);
    }
}

