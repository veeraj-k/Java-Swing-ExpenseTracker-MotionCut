import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
    private static Connection conn = null;
     private static Connection startConnection(){
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:./expense.db");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection connect() {
        if (conn == null) {
            startConnection();
        }
        return conn;
    }


}
