package project.backend.validations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import project.backend.connections.DatabaseConnections;
import project.backend.objects.Users;

public class FetchUser {

    public Users getUser(String email) throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseConnections.initDatabase();
        String sql = "SELECT * FROM users WHERE user_email = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, email);

        ResultSet rst = st.executeQuery();

        Users user = null;

        if (rst.next()) {
            user = new Users();
            
            user.setFirstName(rst.getString("first_name"));
            user.setLastName(rst.getString("last_name"));  
            user.setEmail(email);
            user.setClassType(rst.getString("user_class"));
        }

        connection.close();

        return user;
    }
    
}
