package project.backend.validations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import project.backend.connections.DatabaseConnections;
import project.backend.objects.Users;

public class UserAuthentication {
    
    public Users checkAutherization(String email, String password) throws SQLException, ClassNotFoundException {

        Connection connection = DatabaseConnections.initDatabase();
        String sql = "SELECT * FROM users WHERE user_email = ? and user_password = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, email);
        st.setString(2, password);
 
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
