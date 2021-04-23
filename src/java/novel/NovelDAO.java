/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package novel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class NovelDAO {

    List<NovelDTO> list;

    public void getAll() throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create sql string
                String sql = "SELECT * "
                        + "FROM Novel";
                //3. Create statement and assign values 
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String novelID = rs.getString("novelID");
                    String name = rs.getString("name");
                    String author = rs.getString("author");
                    String coverURL = rs.getString("coverURL");
                    NovelDTO dto = new NovelDTO(novelID, name, author, coverURL);
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add(dto);
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
    }

    public boolean update(NovelDTO dto) throws SQLException {
                Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE Novel "
                        + "SET name = ?, "
                        + "author = ?, "
                        + "coverURL = ? "
                        + "WHERE novelID = ?";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);     
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getAuthor());
                stm.setString(3, dto.getCoverURL());
                stm.setString(4, dto.getNovelID());
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

    public boolean delete(String novelID) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM Novel "
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
}
