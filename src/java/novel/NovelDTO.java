/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package novel;

import account.AccountDTO;

/**
 *
 * @author Gray
 */
public class NovelDTO {
        private String novelID;
        private String novelName;
        private AccountDTO author;
        private String coverURL;

        public NovelDTO() {
        }

        public NovelDTO(String novelID, String novelName, AccountDTO author, String coverURL) {
                this.novelID = novelID;
                this.novelName = novelName;
                this.author = author;
                this.coverURL = coverURL;
        }
        
        

        @Override
        public String toString() {
                return "NovelDTO{" + "novelID=" + getNovelID() + ", novelName=" + getNovelName() + ", author=" + getAuthor() + ", coverURL=" + getCoverURL() + '}';
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
         * @return the novelName
         */
        public String getNovelName() {
                return novelName;
        }

        /**
         * @param novelName the novelName to set
         */
        public void setNovelName(String novelName) {
                this.novelName = novelName;
        }

        /**
         * @return the author
         */
        public AccountDTO getAuthor() {
                return author;
        }

        /**
         * @param author the author to set
         */
        public void setAuthor(AccountDTO author) {
                this.author = author;
        }

        /**
         * @return the coverURL
         */
        public String getCoverURL() {
                return coverURL;
        }

        /**
         * @param coverURL the coverURL to set
         */
        public void setCoverURL(String coverURL) {
                this.coverURL = coverURL;
        }
        
}
