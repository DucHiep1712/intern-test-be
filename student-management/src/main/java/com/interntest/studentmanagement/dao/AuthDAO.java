package com.interntest.studentmanagement.dao;

import javax.sql.DataSource;
import java.sql.*;

public class AuthDAO {

    public static boolean login(String username, String password) throws SQLException {

        Connection myConn = null;
        PreparedStatement myPs = null;
        ResultSet myRs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "root";
            String pass = "Motconvit1";
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web-student-tracker", user, pass);

            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

            // query with parameters
            myPs = myConn.prepareStatement(sql);
            myPs.setString(1, username);
            myPs.setString(2, password);

            myRs = myPs.executeQuery();

            return myRs.next();
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
