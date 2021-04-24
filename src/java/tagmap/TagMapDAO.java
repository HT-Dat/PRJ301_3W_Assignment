/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;

/**
 *
 * @author hotie
 */
public class TagMapDAO {

    public boolean delete(String novelID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM TagMap "
                        + "WHERE novelID = ?";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, novelID);
                //4. Execute query
                int row = stm.executeUpdate();
                //5. Process result
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public List<String> getNovelID(String tagID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<>();
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT * "
                        + "FROM TagMap "
                        + "WHERE tagID = ?";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, tagID);
                //4. Execute query
                rs = stm.executeQuery(sql);
                //5. Process result
                while (rs.next()) {
                    String novelID = rs.getString("novelID");
                    list.add(novelID);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public boolean add(String novelID, String tagID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO TagMap "
                        + "VALUES(?, ?)";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, novelID);
                stm.setString(2, tagID);
                //4. Execute query
                int row = stm.executeUpdate();
                //5. Process result
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
