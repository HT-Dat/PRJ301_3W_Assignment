/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.ConnectDB;

/**
 *
 * @author Gray
 */
public class TagDAO {

        //  get tag name based on tagID
        public TagDTO getTag(String tagID) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String sql = "SELECT * FROM Tag WHERE tagID=?";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, tagID);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String tagid = rs.getString("tagID");
                                        String tagName = rs.getString("tagName");
                                        TagDTO tag = new TagDTO(tagid, tagName);
                                        return tag;
                                }
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        //  get tag list of a novel
        public ArrayList<TagDTO> getTagList(String novelID) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                ArrayList<TagDTO> tagList = new ArrayList<>();
                String sql = "SELECT * FROM TagMap WHERE novelID=?";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                ps.setString(1, novelID);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String tagID = rs.getString("tagID");
                                        TagDTO tag = getTag(tagID);
                                        tagList.add(tag);
                                }
                                return tagList;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public ArrayList<TagDTO> getAllTags() {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                ArrayList<TagDTO> tagList = new ArrayList<>();
                String sql = "SELECT * FROM Tag";
                try {
                        con = ConnectDB.makeConnection();
                        if (con != null) {
                                ps = con.prepareStatement(sql);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                        String tagID = rs.getString("tagID");
                                        TagDTO tag = getTag(tagID);
                                        tagList.add(tag);
                                }
                                return tagList;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }
}
