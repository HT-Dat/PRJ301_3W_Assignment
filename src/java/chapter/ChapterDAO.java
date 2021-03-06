/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import novel.NovelDAO;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class ChapterDAO {

        public boolean delete(String novelID) throws SQLException {
                Connection con = null;
                PreparedStatement stm = null;
                try {
                        //1. Connect DB
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                //2. Create SQL String
                                String sql = "DELETE FROM Chapter "
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

        public boolean delete(ChapterDTO chap) throws SQLException {
                Connection con = null;
                PreparedStatement ps = null;
                String sql = "DELETE FROM Comment WHERE chapterID=? AND novelID=?\n"
                        + "DELETE FROM Chapter WHERE chapterID=? AND novelID=?";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, chap.getChapterID());
                                ps.setString(2, chap.getNovel().getNovelID());
                                ps.setString(3, chap.getChapterID());
                                ps.setString(4, chap.getNovel().getNovelID());
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

        public LinkedList<ChapterDTO> getChapters(String novelID) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                LinkedList<ChapterDTO> lst = new LinkedList<>();
                NovelDAO nDAO = new NovelDAO();
                //get all chapter belong to a specific novel and order them by date
                String sql = "SELECT * FROM Chapter WHERE novelID=? ORDER BY CONVERT(DATE, uploadDate) ASC";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, novelID);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String chapterID = rs.getString("chapterID");
                                        String chapterName = rs.getString("chapterName");
                                        String chapterURL = rs.getString("fileURL");
                                        String nid = rs.getString("novelID");
                                        Date uploadDate = rs.getDate("uploadDate");
                                        ChapterDTO chapter = new ChapterDTO(chapterID, nDAO.get(nid), chapterName, chapterURL, uploadDate);
                                        lst.add(chapter);
                                }
                                return lst;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public ChapterDTO getChapterByChapterIDNovelID(String novelID, String chapterID) throws SQLException {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                NovelDAO nDAO = new NovelDAO();
                String sql = "SELECT * FROM Chapter WHERE chapterID=? AND novelID=?";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, chapterID);
                                ps.setString(2, novelID);

                                rs = ps.executeQuery();
                                if (rs.next()) {
                                        String cID = rs.getString("chapterID");
                                        String nID = rs.getString("novelID");
                                        String chapterName = rs.getString("chapterName");
                                        String fileURL = rs.getString("fileURL");
                                        Date uploadDate = rs.getDate("uploadDate");

                                        ChapterDTO chap = new ChapterDTO(cID, nDAO.get(nID), chapterName, fileURL, uploadDate);
                                        return chap;
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
                        }
                }
                return null;
        }

        public boolean add(ChapterDTO chap) throws SQLException {
                Connection con = null;
                PreparedStatement ps = null;
                String sql = "insert into Chapter(chapterID, novelID, chapterName, fileURL, uploadDate)"
                        + "values(?, ?, ?, ?, ?)";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, chap.getChapterID());
                                ps.setString(2, chap.getNovel().getNovelID());
                                ps.setString(3, chap.getChapterName());
                                ps.setString(4, chap.getFileURL());
                                ps.setDate(5, chap.getUploadDate());
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

        public int searchChapterInList(LinkedList<ChapterDTO> list, String novelID, String chapterID) {
                for (ChapterDTO chapter : list) {
                        if (novelID.equalsIgnoreCase(chapter.getNovel().getNovelID()) && chapterID.equalsIgnoreCase(chapter.getChapterID())) {
                                return list.indexOf(chapter);
                        }
                }
                return -1;
        }

}
