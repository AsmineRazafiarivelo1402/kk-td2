import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection() {
        try {
            String jdbcURl = "jdbc:postgresql://localhost:5432/election"; //
            String user = "postgres"; //mini_dish_db_manager
            String password = "Asmine1402"; //123456
            return DriverManager.getConnection(jdbcURl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Teste " + e);
        }
    }


}
