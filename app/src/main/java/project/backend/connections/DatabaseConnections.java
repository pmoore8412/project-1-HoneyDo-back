package project.backend.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnections {
    
    public static Connection initDatabase() throws SQLException, ClassNotFoundException {

        String dbURL = "jdbc:postgresql://localhost:8080/";
        String dbName = "honeydotoo";
        String dbUserName = "honeydotoo";
        String dbPassword = "D3@dp00!";

        Connection connection = DriverManager.getConnection(dbURL + dbName, dbUserName, dbPassword);

        return connection;

    }
}
