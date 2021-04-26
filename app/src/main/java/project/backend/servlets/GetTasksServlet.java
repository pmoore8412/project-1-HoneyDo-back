package project.backend.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.json.JSONObject;

import project.backend.connections.DatabaseConnections;
import project.backend.objects.Tasks;

@WebServlet(name = "getTasks", urlPatterns = "/getTasks")
public class GetTasksServlet extends HttpServlet {

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

        try {

            Connection connection = DatabaseConnections.initDatabase();
            String sql = "SELECT * FROM tasks WHERE asigned_to = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);

            ResultSet rst = st.executeQuery();

            Tasks tasks = null;
            ArrayList<Tasks> listTasks = new ArrayList<>();

            while (rst.next()) {
                tasks = new Tasks();
            
                tasks.setTaskID(rst.getInt("task_id"));
                tasks.setTaskTitle(rst.getString("task_name"));
                tasks.setDoDate(rst.getString("task_due_date"));
                tasks.setTaskDiscription(rst.getString("task_discription"));
                tasks.setAssinedTo(rst.getString("asigned_to"));

                listTasks.add(tasks);
            }

            Gson gson = new Gson();

            if(listTasks != null) {

                String jsonString = gson.toJson(listTasks);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter send = response.getWriter();
                send.print(jsonString);
                send.flush();

            }

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
