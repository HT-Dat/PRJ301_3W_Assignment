/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comment;

import account.AccountDTO;
import chapter.ChapterDTO;
import java.sql.Date;

/**
 *
 * @author Gray
 */
public class CommentDTO {
    private String commentID;
    private ChapterDTO chapter;
    private AccountDTO user;
    private String context;
    private Date commentDate;

    public CommentDTO(String commentID, ChapterDTO chapter, AccountDTO user, String context, Date commentDate) {
        this.commentID = commentID;
        this.chapter = chapter;
        this.user = user;
        this.context = context;
        this.commentDate = commentDate;
    }

    /**
     * @return the commentID
     */
    public String getCommentID() {
        return commentID;
    }

    /**
     * @param commentID the commentID to set
     */
    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    /**
     * @return the chapter
     */
    public ChapterDTO getChapter() {
        return chapter;
    }

    /**
     * @param chapter the chapter to set
     */
    public void setChapter(ChapterDTO chapter) {
        this.chapter = chapter;
    }

    /**
     * @return the user
     */
    public AccountDTO getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(AccountDTO user) {
        this.user = user;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the commentDate
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate the commentDate to set
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
    
}
