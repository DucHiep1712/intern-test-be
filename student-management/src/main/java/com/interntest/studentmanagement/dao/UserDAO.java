package com.interntest.studentmanagement.dao;

import com.interntest.studentmanagement.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String user = "root";
    private static final String pass = "Motconvit1";

    public static List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // initialize connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker", user, pass);

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
                System.out.println(tempUser.toString());

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

    public static void modifyUser(User myUser) throws ClassNotFoundException, SQLException {

        Connection myConn = null;
        PreparedStatement myPs = null;

        try {
            // initialize connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker", user, pass);

            // query to execute
            String sql = "UPDATE user\n" +
                    "SET username = ?, password = ?, first_name = ?, last_name = ?, email = ?, phone = ?, role = ?\n" +
                    "WHERE id = ?";

            myPs = myConn.prepareStatement(sql);
            myPs.setString(1, myUser.getUsername());
            myPs.setString(2, myUser.getPassword());
            myPs.setString(3, myUser.getFirst_name());
            myPs.setString(4, myUser.getLast_name());
            myPs.setString(5, myUser.getEmail());
            myPs.setString(6, myUser.getPhone());
            myPs.setString(7, myUser.getRole());
            myPs.setString(8, String.valueOf(myUser.getId()));

            // execute query
            myPs.executeQuery(sql);
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
