/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comment;

import account.AccountDAO;
import chapter.ChapterDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class CommentDAO {

        public boolean delete(String novelID) throws SQLException {
                Connection con = null;
                PreparedStatement stm = null;
                try {
                        //1. Connect DB
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                //2. Create SQL String
                                String sql = "DELETE FROM Comment "
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

        public boolean add(String username, String context, String chapterID, String novelID) throws SQLException {
                Connection con = null;
                PreparedStatement ps = null;
                LinkedList<CommentDTO> lst = this.getAll();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String commentID = "CM1";
                Date date = new Date();
                String commentDate = format.format(date);
                for (CommentDTO comment : lst) {
                        if (comment.getCommentID().equalsIgnoreCase(commentID)) {
                                commentID = "CM" + (Integer.parseInt(commentID.substring(2)) + 1);
                        }
                }
                String sql = "INSERT INTO Comment(commentID, novelID, chapterID, username, context, commentDate)"
                        + "VALUES(?, ?, ?, ?, ?, ?)";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, commentID);
                                ps.setString(2, novelID);
                                ps.setString(3, chapterID);
                                ps.setString(4, username);
                                ps.setString(5, context);
                                ps.setString(6, commentDate);
                                ps.executeUpdate();
                                return true;
                        }
                } finally {
                        try {
                                if (ps != null) {
                                        ps.close();
                                }
                                if (con != null) {
                                        con.close();
                                }
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }
                }

                return false;
        }

        public LinkedList<CommentDTO> getAll() throws SQLException {
                LinkedList<CommentDTO> lst = new LinkedList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String sql = "SELECT * FROM Comment";
                ChapterDAO cDAO = new ChapterDAO();
                AccountDAO aDAO = new AccountDAO();
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String commentID = rs.getString("commentID");
                                        String commentNovelID = rs.getString("novelID");
                                        String commentChapterID = rs.getString("chapterID");
                                        String username = rs.getString("username");
                                        String context = rs.getString("context");
                                        Date commentDate = rs.getDate("commentDate");

                                        CommentDTO com = new CommentDTO(commentID, cDAO.getChapterByChapterIDNovelID(commentNovelID, commentChapterID), aDAO.getAccountByUsername(username), context, (java.sql.Date) commentDate);
                                        lst.add(com);
                                }
                        }
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
                return lst;
        }

        public LinkedList<CommentDTO> searchCmtByChapter(String nid, String cid) {
                LinkedList<CommentDTO> cmtList = new LinkedList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String sql = "SELECT * FROM Comment WHERE novelID=? AND chapterID=?";
                AccountDAO aDAO = new AccountDAO();
                ChapterDAO cDAO = new ChapterDAO();
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, nid);
                                ps.setString(2, cid);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String commentID = rs.getString("commentID");
                                        String commentNovelID = rs.getString("novelID");
                                        String commentChapterID = rs.getString("chapterID");
                                        String username = rs.getString("username");
                                        String context = rs.getString("context");
                                        Date commentDate = rs.getDate("commentDate");

                                        CommentDTO cmt = new CommentDTO(commentID, cDAO.getChapterByChapterIDNovelID(commentNovelID, commentChapterID), aDAO.getAccountByUsername(username), context, (java.sql.Date) commentDate);
                                        cmtList.add(cmt);
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
                return cmtList;
        }
}
