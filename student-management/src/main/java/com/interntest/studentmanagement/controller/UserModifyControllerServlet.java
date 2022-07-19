package com.interntest.studentmanagement.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interntest.studentmanagement.dao.UserDAO;
import com.interntest.studentmanagement.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserModifyControllerServlet", value = "/modify")
public class UserModifyControllerServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } finally {
            reader.close();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(String.valueOf(sb));

        String id = node.get("id").asText();
        System.out.println(id);
        String username = node.get("username").asText();
        System.out.println(username);
        String password = node.get("password").asText();
        System.out.println(password);
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String email = node.get("email").asText();
        String phone = node.get("phone").asText();
        String role = node.get("role").asText();
        System.out.println(id + username + password + firstName + lastName + email + phone + role);

        User toModify = new User(Integer.valueOf(id), username, password, role, firstName, lastName, email, phone);
//        System.out.println(toModify.toString());

        try {
            UserDAO.modifyUser(toModify);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
