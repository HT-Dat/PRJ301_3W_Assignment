/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gray
 */
public class ConnectDB {

        public static Connection makeConnetion() {
                try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url = "jdbc:sqlserver://localhost:1433;databaseName=NorthwindCopyDB";
                        Connection con = DriverManager.getConnection(url, "Dung", "123456");
                        if (con != null) {
                                return con;
                        }
                } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }
}
