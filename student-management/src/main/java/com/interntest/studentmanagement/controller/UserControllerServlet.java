package com.interntest.studentmanagement.controller;

import com.interntest.studentmanagement.entity.User;
import com.interntest.studentmanagement.dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserControllerServlet", value = "/user")
public class UserControllerServlet extends HttpServlet {

//    private UserDAO userDAO;
//    private DataSource dataSource;
//
//    @Override
//    public void init() throws ServletException {
//
//        super.init();
//
//        try {
//            userDAO = new UserDAO(dataSource);
//        }
//        catch (Exception e) {
//            throw new ServletException(e);
//        }
//    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        // list the users
//        listUser(request, response) {
//
//        }
//    }

//    private void listUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // get users from db util
//        List<User> users = userDAO.getUsers();
//
//        // add users to the request
//        request.setAttribute("USER_LIST", users);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
