/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class BookmarkDAO {

    public boolean delete(String novelID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM Bookmark "
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
    
    public boolean isBookmarked(String username, String novelID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.makeConnection();
            if(con != null){
                ps = con.prepareStatement("SELECT * FROM Bookmark WHERE novelID = ? AND username = ?");
                ps.setString(1, novelID);
                ps.setString(2, username);
                rs = ps.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
