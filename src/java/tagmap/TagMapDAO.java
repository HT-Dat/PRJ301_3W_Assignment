/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagmap;

import bookmark.BookmarkDAO;
import chapter.ChapterDAO;
import comment.CommentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.ConnectDB;

/**
 *
 * @author hotie
 */
public class TagMapDAO {
    public boolean delete(String novelID) throws SQLException{
        BookmarkDAO bookmarkDAO=new BookmarkDAO();
        bookmarkDAO.delete(novelID);
        
        CommentDAO commentDAO=new CommentDAO();
        commentDAO.delete(novelID);
        
        ChapterDAO chapterDAO=new ChapterDAO();
        chapterDAO.delete(novelID);
        
        TagMapDAO tagMapDAO=new TagMapDAO();
        tagMapDAO.delete(novelID);
       
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
}
