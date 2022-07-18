package com.interntest.studentmanagement.dao;

import com.interntest.studentmanagement.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // initialize connection
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web-student-tracker");

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
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");
                String phone = myRs.getString("phone");

                // create new user object
                User tempUser = new User(id, role, firstName, lastName, email, phone);

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
