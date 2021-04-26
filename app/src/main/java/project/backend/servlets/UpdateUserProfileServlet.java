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

@WebServlet(name = "updateUser", urlPatterns = "/updateUser")
public class UpdateUserProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setAccessControlHeaders(response);

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

        jsonObject = new JSONObject(data.toString());

        String email = jsonObject.getString("email");
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        String password = jsonObject.getString("password");

        try {

            Connection connection = DatabaseConnections.initDatabase();
            String sql = "update users set first_name = ?, last_name = ?, user_password = ? where user_email = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.executeUpdate();

        } catch (Exception exception) {
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
