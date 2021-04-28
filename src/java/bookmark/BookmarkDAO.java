/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark;

import account.AccountDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class BookmarkDAO {

        //get all bookmark of a user
        public ArrayList<String> getBookmarkIDList(AccountDTO user) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                ArrayList<String> idList = new ArrayList<>();

                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement("SELECT * FROM Bookmark WHERE username=?");
                                ps.setString(1, user.getUserName());
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String novelID = rs.getString("novelID");
                                        idList.add(novelID);
                                }
                                return idList;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        try {
                                if (rs != null) {
                                        rs.close();
                                }
                                if (ps != null) {
                                        ps.close();
                                }
                                if (con != null) {
                                        con.close();
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return null;
        }

        //add/delete bookmark
        public boolean bookmarkHandler(String username, String novelID) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement("SELECT * FROM Bookmark WHERE novelID = ? AND username = ?");
                                ps.setString(1, novelID);
                                ps.setString(2, username);
                                rs = ps.executeQuery();
                                // if bookmark exist, delete it
                                if (rs.next()) {
                                        ps = con.prepareStatement("DELETE FROM Bookmark WHERE username=? AND novelID=?");
                                        ps.setString(1, username);
                                        ps.setString(2, novelID);
                                        ps.executeUpdate();
                                } else { //else create and add a new boomark
                                        ps = con.prepareStatement("INSERT INTO Bookmark(username, novelID) VALUEs(?, ?)");
                                        ps.setString(1, username);
                                        ps.setString(2, novelID);
                                        ps.executeUpdate();
                                }
                                return true;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return false;
        }

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
                        if (con != null) {
                                ps = con.prepareStatement("SELECT * FROM Bookmark WHERE novelID = ? AND username = ?");
                                ps.setString(1, novelID);
                                ps.setString(2, username);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                        return true;
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        try {
                                if (rs != null) {
                                        rs.close();
                                }
                                if (ps != null) {
                                        ps.close();
                                }
                                if (con != null) {
                                        con.close();
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
                return false;
        }
}
