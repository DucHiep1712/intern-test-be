package com.interntest.studentmanagement.dao;

import com.interntest.studentmanagement.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String user = "b70e6c554b9c90";
    private static final String pass = "0ba8f3c0";

    public static List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // initialize connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net/heroku_d270d79324c27ba", user, pass);

            // query to execute
            String sql = "SELECT * FROM user ORDER BY last_name";

            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String role = myRs.getString("role");
                String username = myRs.getString("username");
                String password = myRs.getString("password");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");
                String phone = myRs.getString("phone");

                // create new user object
                User tempUser = new User(id, username, password, role, firstName, lastName, email, phone);

                // add to user list
                users.add(tempUser);
            }

            return users;
        }
        finally {

            // close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public static void modifyUser(User myUser, String modifyType) throws ClassNotFoundException, SQLException {
        System.out.println(modifyType);

        Connection myConn = null;
        PreparedStatement myPs = null;

        try {
            // initialize connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker", user, pass);

            // query to execute
                System.out.println(modifyType.equals("student") ? "Dung" : "Sai");

                String sql = (modifyType.equals("student") ? ("UPDATE user SET username = "
                    + "'" + myUser.getUsername() + "'" + ", password = "
                    + "'" + myUser.getPassword() + "'" + ", first_name = "
                    + "'" + myUser.getFirst_name() + "'" + ", last_name = "
                    + "'" + myUser.getLast_name() + "'" + ", email = "
                    + "'" + myUser.getEmail() + "'" + ", phone = "
                    + "'" + myUser.getPhone() + "'" + ", role = "
                    + "'" + myUser.getRole() + "'" + " WHERE id = "
                    + String.valueOf(myUser.getId())) :
                    ("UPDATE user SET first_name = "
                    + "'" + myUser.getFirst_name() + "'" + ", last_name = "
                    + "'" + myUser.getLast_name() + "'" + ", email = "
                    + "'" + myUser.getEmail() + "'" + ", phone = "
                    + "'" + myUser.getPhone() + "'" + ", role = "
                    + "'" + myUser.getRole() + "'" + " WHERE id = "
                    + String.valueOf(myUser.getId())));

                myPs = myConn.prepareStatement(sql);

                // execute query
                myPs.executeUpdate(sql);
                System.out.println(sql);
        }
        finally {

            // close JDBC objects
            close(myConn, myPs);
        }
    }

    private static void close(Connection myConn, PreparedStatement myPs) {

        try {

            if (myPs != null) {
                myPs.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
        catch(Exception exc) {
            exc.printStackTrace();
        }
    }
}
