package com.interntest.studentmanagement.controller;

import com.interntest.studentmanagement.dao.UserDAO;
import com.interntest.studentmanagement.entity.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserControllerServlet", value = "/user")
public class UserControllerServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get users from db
        try {
            List<User> users = UserDAO.getUsers();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("users", users);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonObject);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
