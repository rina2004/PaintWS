package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FPT University - PRJ30X
 */
public class DBContext {

    public Connection connection;

    public DBContext() {
        try {
            String user = "rina";
            String pass = "26122004";
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=Paint_Store";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        DBContext db = new DBContext();
        Connection conn = db.connection;

        if (conn != null) {
            System.out.println("Kết nối database thành công!");
        } else {
            System.out.println("Kết nối database thất bại!");
        }
    }
}
