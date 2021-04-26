package project.backend.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.json.JSONObject;

import project.backend.objects.Users;
import project.backend.validations.FetchUser;

@WebServlet(name = "getUser", urlPatterns = "/getUser")
public class GetUserServlet extends HttpServlet {

    @Override
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

        FetchUser getUser = new FetchUser();

        try {

            Users user = getUser.getUser(email);
            Gson gson = new Gson();

            if(user != null) {

                String jsonString = gson.toJson(user);
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
