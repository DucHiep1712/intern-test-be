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
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods","GET, HEAD, PUT, POST");

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

        String modifyType = node.get("modifyType").asText();
        System.out.println("Day " + modifyType);
        String id = node.get("id").asText();
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        String email = node.get("email").asText();
        String phone = node.get("phone").asText();
        String role = node.get("role").asText();

        User toModify = new User(Integer.valueOf(id), username, password, role, firstName, lastName, email, phone);

        try {
            UserDAO.modifyUser(toModify, modifyType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
