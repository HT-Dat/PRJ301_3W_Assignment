/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
// Table name: Account
public class AccountDAO {

    public AccountDTO getAccountByUsername(String uname) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Account WHERE username = ?");
                ps.setString(1, uname);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    boolean isAdmin = Boolean.parseBoolean(rs.getString("isAdmin"));
                    String avatarURL = rs.getString("avatarURL");
                    AccountDTO acc = new AccountDTO(username, email, password, name, isAdmin, avatarURL);
                    return acc;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    public AccountDTO getByEmail(String email) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectDB.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * "
                        + "FROM Account "
                        + "WHERE email = ?");
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    boolean isAdmin = Boolean.parseBoolean(rs.getString("isAdmin"));
                    String avatarURL = rs.getString("avatarURL");
                    AccountDTO acc = new AccountDTO(username, email, password, name, isAdmin, avatarURL);
                    return acc;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    public boolean checkLogin(String username, String password) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String url = "select * from Account where username=? and password=?";

        con = ConnectDB.makeConnection();
        try {
            if (con != null) {
                pstm = con.prepareStatement(url);
                pstm.setString(1, username);
                pstm.setString(2, password);
                rs = pstm.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean addAccount(AccountDTO a) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO Account(username, email, password, name, isAdmin,avatarURL)"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            con = ConnectDB.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, a.getUserName());
                ps.setString(2, a.getEmail());
                ps.setString(3, a.getPassword());
                ps.setString(4, a.getName());
                ps.setString(5, "false");
                ps.setString(6, a.getAvatarURL());
                ps.executeUpdate();
                return true;
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean update(AccountDTO dto) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE Account "
                        + "SET email = ?, "
                        + "password = ?, "
                        + "name = ?, "
                        + "isAdmin = ?, "
                        + "avatarURL = ? "
                        + "WHERE username = ?";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getEmail());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getName());
                stm.setString(4, Boolean.toString(dto.isIsAdmin()));
                stm.setString(5, dto.getAvatarURL());
                stm.setString(6, dto.getUserName());
                //4. Execute query
                int row = stm.executeUpdate();
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
