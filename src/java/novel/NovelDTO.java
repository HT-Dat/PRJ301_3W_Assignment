/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package novel;

/**
 *
 * @author Gray
 */
public class NovelDTO {
        private String novelID;
        private String name;
        private String author;
        private String coverURL;

        public NovelDTO() {
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
         * @return the name
         */
        public String getName() {
                return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
                this.name = name;
        }

        /**
         * @return the author
         */
        public String getAuthor() {
                return author;
        }

        /**
         * @param author the author to set
         */
        public void setAuthor(String author) {
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
