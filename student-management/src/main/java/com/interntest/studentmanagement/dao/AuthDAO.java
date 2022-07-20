package com.interntest.studentmanagement.dao;

import com.interntest.studentmanagement.entity.User;

import javax.sql.DataSource;
import java.sql.*;

public class AuthDAO {

    public static User login(String username, String password) throws SQLException {
        Connection myConn = null;
        PreparedStatement myPs = null;
        ResultSet myRs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "root";
            String pass = "Motconvit1";
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker", user, pass);
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

            // query with parameters
            myPs = myConn.prepareStatement(sql);
            myPs.setString(1, username);
            myPs.setString(2, password);

            myRs = myPs.executeQuery();

            if (myRs.next()) {
                User loginUser = new User(Integer.valueOf(myRs.getString(1)), myRs.getString(8), myRs.getString(4), myRs.getString(5), myRs.getString(6), myRs.getString(7));

                return loginUser;
            } else {
                return null;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            close(myConn, myPs, myRs);
        }
    }

    private static void close(Connection myConn, PreparedStatement myPs, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }

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
}
