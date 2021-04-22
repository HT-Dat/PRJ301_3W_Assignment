/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark;

/**
 *
 * @author Gray
 */
public class BookmarkDTO {
    private String novelID;
    private String userName;

    public BookmarkDTO(String novelID, String userName) {
        this.novelID = novelID;
        this.userName = userName;
    }

    /**
     * @return the novelID
     */
    public String getNovelID() {
        return novelID;
    }

    /**
     * @param novelID the novelID to set
     */
    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
