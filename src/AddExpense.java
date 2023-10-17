import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AddExpense implements ActionListener {

    JTextArea itemName,costName,descName;
    JLabel mes;
    JComboBox categoryName;
    JPanel mainPanel;
    AddExpense(){
        //setup of add expense frame
            JFrame addPage = new JFrame("Add Expense");

            JLabel addLabel =new JLabel("Add an expense");
            addLabel.setBackground(Color.BLACK);
            addLabel.setForeground(Color.WHITE);
            addLabel.setHorizontalAlignment(JLabel.CENTER);
            addLabel.setFont(new Font("MV Boli",Font.PLAIN,30));

            addPage.getContentPane().setBackground(Color.BLACK);

            addPage.add(addLabel, BorderLayout.NORTH);

             mainPanel = new JPanel();

            mainPanel.setLayout(new GridLayout(5,2));

            JLabel itemLabel = new JLabel("Item Name:");
             itemName = new JTextArea();

            JLabel categoryLabel = new JLabel("Item Name:");
            //items for the catgory dropdown
            String[] expenseCategories = {
                    "Housing",
                    "Transportation",
                    "Food",
                    "Entertainment",
                    "Health",
                    "Personal Care",
                    "Debt/Loans",
                    "Insurance",
                    "Utilities",
                    "Education",
                    "Miscellaneous"
            };
             categoryName = new JComboBox(expenseCategories);

            JLabel descLabel = new JLabel("Description:");
             descName = new JTextArea();

            JLabel costLabel = new JLabel("Cost:");
             costName = new  JTextArea();

            JButton submit = new JButton("Add Expense");
            submit.addActionListener(this);

            mes = new JLabel();

            //adding the catgories to the mainpanel
            mainPanel.add(itemLabel);
            mainPanel.add(itemName);
            mainPanel.add(categoryLabel);
            mainPanel.add(categoryName);
            mainPanel.add(descLabel);
            mainPanel.add(descName);
            mainPanel.add(costLabel);
            mainPanel.add(costName);
            mainPanel.add(submit);

            mes.setVisible(false);
            mainPanel.add(mes);

            addPage.add(mainPanel,BorderLayout.CENTER);

            addPage.setVisible(true);
            addPage.setSize(600,400);
            addPage.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


    }

    void showWarn(){
        //show waring message if add is not succesfull

        mes.setText("Failed to add into expense list!");
        mes.setFont(new Font("Calibri",Font.BOLD,16));
        mes.setForeground(Color.RED);
        mes.setVisible(true);
    }
    void showSuccess(){
        //method to show success message
        mes.setText("Succesfully added into expenses list!");
        mes.setFont(new Font("Calibri",Font.BOLD,16));
        mes.setForeground(Color.GREEN);
        mes.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            //mysql connection using jdbc

            Connection conn = MyConnection.connect();
            Statement st = conn.createStatement();

            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            st.executeUpdate("CREATE TABLE IF NOT EXISTS expenses( Item varchar(100) NOT NULL,Category varchar(50) NOT NULL, Description varchar(255) NOT NULL, Cost int NOT NULL,Date date NOT NULL)");


            System.out.println("-------"+itemName.getText().length()+"----------");
            if(itemName.getText().length() ==0 || descName.getText().length()==0 || costName.getText().length()==0){
                showWarn();
            }
            else{
                String query= String.format("INSERT INTO expenses VALUES(\"%1$s\" ,\"%2$s\" , \"%3$s\", \"%4$s\", \"%5$s\");",itemName.getText(),categoryName.getSelectedItem(),descName.getText(),costName.getText(),formattedDate);

                st.executeUpdate(query);

                showSuccess();

            }

        }

        catch (Exception et){
            showWarn();
        }
    }
}
