package com.interntest.studentmanagement.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.interntest.studentmanagement.dao.AuthDAO;
import com.interntest.studentmanagement.entity.User;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "AuthControllerServlet", value = "/login")
public class AuthControllerServlet extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, PUT, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Allow", "GET, HEAD, PUT, POST, OPTIONS");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

            String username = node.get("username").asText();
            String password = node.get("password").asText();

            User validUser = AuthDAO.login(username, password);

            JSONObject jsonObject = new JSONObject();

            if (validUser != null) {
                jsonObject.put("status", "success");
                jsonObject.put("id", validUser.getId());
                jsonObject.put("role", validUser.getRole());
                jsonObject.put("firstName", validUser.getFirst_name());
                jsonObject.put("lastName", validUser.getLast_name());
                jsonObject.put("email", validUser.getEmail());
                jsonObject.put("phone", validUser.getPhone());
            } else {
                jsonObject.put("status", "failed");
            }

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods","GET, HEAD, PUT, POST");
            out.print(jsonObject);
            out.flush();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
