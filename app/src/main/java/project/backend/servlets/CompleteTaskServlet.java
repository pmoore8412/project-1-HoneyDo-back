package project.backend.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import project.backend.connections.DatabaseConnections;

@WebServlet(name = "completeTask", urlPatterns = "/completeTask")
public class CompleteTaskServlet extends HttpServlet {
    
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

        System.out.println(jsonObject);

        String assignedTo = null;
        int taskID = jsonObject.getInt("taskID");
        String taskTitle = null;
        String completedDate = jsonObject.getString("completedDate");

        try {

            Connection connection = DatabaseConnections.initDatabase();
            String sql = "select * from tasks where task_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, taskID);

            ResultSet rst = statement.executeQuery();

            while (rst.next()) {
                taskTitle = rst.getString("task_name");
                assignedTo = rst.getString("asigned_to");
            }

            statement.close();
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            Connection connection = DatabaseConnections.initDatabase();
            String sql = "insert into tasksComplete values(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, taskID);
            statement.setString(2, taskTitle);
            statement.setString(3, completedDate);
            statement.setString(4, assignedTo);
            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            Connection connection = DatabaseConnections.initDatabase();
            String sql = "delete from tasks where task_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, taskID);
            statement.executeUpdate();

            statement.close();
            connection.close();
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
