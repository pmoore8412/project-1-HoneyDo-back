package project.backend.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import project.backend.connections.DatabaseConnections;
import project.backend.objects.Users;

@WebServlet(name = "createUser", urlPatterns = "/createUser")
public class CreateAccountServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setAccessControlHeaders(response);

        Users user = new Users();

        String line = null;
        StringBuffer data = new StringBuffer();
        JSONObject jsonObject;

        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                data.append(line);
            }

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        

        try{
            jsonObject = new JSONObject(data.toString());

            user.setEmail(jsonObject.getString("email"));
            user.setFirstName(jsonObject.getString("firstName"));
            user.setLastName(jsonObject.getString("lastName"));
            user.setPassword(jsonObject.getString("password"));
            user.setClassType(jsonObject.getString("userClass"));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            Connection connection = DatabaseConnections.initDatabase();
            PreparedStatement statement = connection.prepareStatement("insert into users values(?, ?, ?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getClassType());
            statement.executeUpdate();

            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    //for Preflight
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
    }


}
