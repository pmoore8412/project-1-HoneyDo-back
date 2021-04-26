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
import project.backend.objects.Tasks;

@WebServlet(name = "createTask", urlPatterns = "/createTask")
public class CreateTaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setAccessControlHeaders(response);

        Tasks task = new Tasks();

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

            task.setTaskID(jsonObject.getInt("taskID"));
            task.setDoDate(jsonObject.getString("doDate"));
            task.setTaskTitle(jsonObject.getString("taskTitle"));
            task.setTaskDiscription(jsonObject.getString("taskDiscription"));
            task.setAssinedTo(jsonObject.getString("assinedTo"));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            Connection connection = DatabaseConnections.initDatabase();
            PreparedStatement statement = connection.prepareStatement("insert into tasks values(?, ?, ?, ?, ?)");
            statement.setInt(1, task.getTaskID());
            statement.setString(2, task.getTaskTitle());
            statement.setString(3, task.getDoDate());
            statement.setString(4, task.getTaskDiscription());
            statement.setString(5, task.getAssinedTo());
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
