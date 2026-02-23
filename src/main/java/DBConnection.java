import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection() {
   Dotenv dotenv = Dotenv.load();


        try {
                final String jdbcURl = dotenv.get("DB_URL");
        final String user = dotenv.get("DB_USER");
   final String password = dotenv.get("DB_PASSWORD");
            return DriverManager.getConnection(jdbcURl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Teste " + e);
        }
    }


}
