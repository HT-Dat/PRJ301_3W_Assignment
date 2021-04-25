/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter;

import java.sql.Date;
import novel.NovelDTO;

/**
 *
 * @author Gray
 */
public class ChapterDTO {

    private String chapterID;
    private NovelDTO novel;
    private String chapterName;
    private String fileURL;
    private Date uploadDate;

        public ChapterDTO() {
        }

        public ChapterDTO(String chapterID, NovelDTO novel, String chapterName, String fileURL, Date uploadDate) {
                this.chapterID = chapterID;
                this.novel = novel;
                this.chapterName = chapterName;
                this.fileURL = fileURL;
                this.uploadDate = uploadDate;
        }

        @Override
        public String toString() {
                return "ChapterDTO{" + "chapterID=" + getChapterID() + ", novel=" + getNovel() + ", chapterName=" + getChapterName() + ", fileURL=" + getFileURL() + ", uploadDate=" + getUploadDate() + '}';
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

        /**
         * @return the novel
         */
        public NovelDTO getNovel() {
                return novel;
        }

        /**
         * @param novel the novel to set
         */
        public void setNovel(NovelDTO novel) {
                this.novel = novel;
        }

        /**
         * @return the chapterName
         */
        public String getChapterName() {
                return chapterName;
        }

        /**
         * @param chapterName the chapterName to set
         */
        public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
        }

        /**
         * @return the fileURL
         */
        public String getFileURL() {
                return fileURL;
        }

        /**
         * @param fileURL the fileURL to set
         */
        public void setFileURL(String fileURL) {
                this.fileURL = fileURL;
        }

        /**
         * @return the uploadDate
         */
        public Date getUploadDate() {
                return uploadDate;
        }

        /**
         * @param uploadDate the uploadDate to set
         */
        public void setUploadDate(Date uploadDate) {
                this.uploadDate = uploadDate;
        }

    
    
}
