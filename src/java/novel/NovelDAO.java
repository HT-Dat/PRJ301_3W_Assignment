/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package novel;

import account.AccountDAO;
import account.AccountDTO;
import bookmark.BookmarkDAO;
import chapter.ChapterDAO;
import comment.CommentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tagmap.TagMapDAO;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class NovelDAO {

    public NovelDAO() {
    }

    public List<NovelDTO> getAll() throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<NovelDTO> list = new ArrayList<>();
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
                    System.out.println("ohoho");
                    String novelID = rs.getString("novelID");
                    String name = rs.getString("name");
                    String AuthorName = rs.getString("author");
                    AccountDAO accDAO = new AccountDAO();
                    AccountDTO author = accDAO.getAccountByUsername(AuthorName);
                    String coverURL = rs.getString("coverURL");
                    NovelDTO dto = new NovelDTO(novelID, name, author, coverURL);
                    list.add(dto);
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
        System.out.println("ahihi" + list);
        return list;
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
                stm.setString(1, dto.getNovelName());
                stm.setString(2, dto.getAuthor().getUserName());
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
        BookmarkDAO bookmarkDAO = new BookmarkDAO();
        bookmarkDAO.delete(novelID);

        CommentDAO commentDAO = new CommentDAO();
        commentDAO.delete(novelID);

        ChapterDAO chapterDAO = new ChapterDAO();
        chapterDAO.delete(novelID);

        TagMapDAO tagMapDAO = new TagMapDAO();
        tagMapDAO.delete(novelID);

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

    public NovelDTO get(String novelID) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create sql string
                String sql = "SELECT * "
                        + "FROM Novel "
                        + "WHERE novelID = ?";
                //3. Create statement and assign values 
                stm = con.prepareStatement(sql);
                stm.setString(1, novelID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String AuthorName = rs.getString("author");
                    AccountDAO dao = new AccountDAO();
                    AccountDTO author = dao.getAccountByUsername(AuthorName);
                    String coverURL = rs.getString("coverURL");
                    NovelDTO dto = new NovelDTO(novelID, name, author, coverURL);
                    return dto;
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
        return null;
    }

    public boolean add(NovelDTO dto) throws SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO Products "
                        + "VALUES(?, ?, ?, ?)";
                //3. Create statement and assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getNovelID());
                stm.setString(2, dto.getNovelName());
                stm.setString(3, dto.getAuthor().getUserName());
                stm.setString(4, dto.getCoverURL());
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

    public NovelDTO getByUsernameAuthor(String AuthorName) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        NovelDTO dto = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create sql string
                String sql = "SELECT * "
                        + "FROM Novel "
                        + "WHERE author = ?";
                stm.setString(1, AuthorName);
                //3. Create statement and assign values 
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    AccountDAO dao = new AccountDAO();
                    AccountDTO author = dao.getAccountByUsername(AuthorName);
                    String novelID = rs.getString("novelID");
                    String name = rs.getString("name");
                    String coverURL = rs.getString("coverURL");
                    dto = new NovelDTO(novelID, name, author, coverURL);
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
        return dto;
    }

    public List<NovelDTO> searchByName(String name) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<NovelDTO> list = new ArrayList<>();
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create sql string
                String sql = "SELECT * "
                        + "FROM Novel "
                        + "WHERE name = ?";
                //3. Create statement and assign values 
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    AccountDAO dao = new AccountDAO();
                    String novelID = rs.getString("novelID");
                    String AuthorName = rs.getString("author");
                    AccountDTO author = dao.getAccountByUsername(AuthorName);
                    String coverURL = rs.getString("coverURL");
                    NovelDTO dto = new NovelDTO(novelID, name, author, coverURL);
                    list.add(dto);
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

    public List<NovelDTO> searchByTag(String tag) throws SQLException, ClassNotFoundException {

        List<NovelDTO> list = new ArrayList<>();
        TagMapDAO tagMapDAO = new TagMapDAO();
        List<String> novelIDList = tagMapDAO.getNovelID(tag);
        for (String novelID : novelIDList) {
            list.add(get(novelID));
        }
        return list;
    }

    public NovelDTO getByNameAndUsername(String name, String username) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        NovelDTO dto = null;
        try {
            //1. Connect DB
            con = ConnectDB.makeConnection();
            if (con != null) {
                //2. Create sql string
                String sql = "SELECT * "
                        + "FROM Novel "
                        + "WHERE name = ? AND author = ?";
                stm.setString(1, name);
                stm.setString(2, username);
                //3. Create statement and assign values 
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    AccountDAO aDAO = new AccountDAO();
                    AccountDTO author = aDAO.getAccountByUsername(username);
                    String novelID = rs.getString("novelID");
                    String coverURL = rs.getString("coverURL");
                    dto = new NovelDTO(novelID, name, author, coverURL);
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
        return dto;
    }
}
