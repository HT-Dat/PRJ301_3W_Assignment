/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comment;

import java.sql.Date;

/**
 *
 * @author Gray
 */
public class CommentDTO {
        private String commentID;
        private String userName;
        private String novelID;
        private String context;
        private Date commentDate;
        private String chapterID;

        public CommentDTO() {
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

        /**
         * @return the chapterID
         */
        public String getChapterID() {
                return chapterID;
        }

        /**
         * @param chapterID the chapterID to set
         */
        public void setChapterID(String chapterID) {
                this.chapterID = chapterID;
        }
        
}
