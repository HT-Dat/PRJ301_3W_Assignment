/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tag;

/**
 *
 * @author Gray
 */
public class TagDTO {
        private String tagID;
        private String tagName;

        public TagDTO() {
        }

        public TagDTO(String tagID, String tagName) {
                this.tagID = tagID;
                this.tagName = tagName;
        }

        /**
         * @return the tagID
         */
        public String getTagID() {
                return tagID;
        }

        /**
         * @param tagID the tagID to set
         */
        public void setTagID(String tagID) {
                this.tagID = tagID;
        }

        /**
         * @return the tagName
         */
        public String getTagName() {
                return tagName;
        }

        /**
         * @param tagName the tagName to set
         */
        public void setTagName(String tagName) {
                this.tagName = tagName;
        }

}
