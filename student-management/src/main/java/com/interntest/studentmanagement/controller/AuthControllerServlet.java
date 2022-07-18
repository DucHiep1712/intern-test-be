package com.interntest.studentmanagement.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interntest.studentmanagement.dao.AuthDAO;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } finally {
                reader.close();
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(String.valueOf(sb));

            String username = node.get("username").asText();
            String password = node.get("password").asText();

            boolean isValid = AuthDAO.login(username, password);

            JSONObject jsonObject = new JSONObject();

            if (isValid) {
                jsonObject.put("status", "success");
                jsonObject.put("username", username);
            } else {
                jsonObject.put("status", "failed");
            }

            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
//            System.out.println(username + password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
