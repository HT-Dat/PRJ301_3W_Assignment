/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

/**
 *
 * @author Gray
 */
public class AccountDTO {

        private String userName;
        private String email;
        private String password;
        private String name;
        private boolean isAdmin;
        private String avatarURL;

        public AccountDTO() {
        }

        public AccountDTO(String userName, String email, String password, String name, boolean isAdmin, String avatarURL) {
                this.userName = userName;
                this.email = email;
                this.password = password;
                this.name = name;
                this.isAdmin = isAdmin;
                this.avatarURL = avatarURL;
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
         * @return the email
         */
        public String getEmail() {
                return email;
        }

        /**
         * @param email the email to set
         */
        public void setEmail(String email) {
                this.email = email;
        }

        /**
         * @return the password
         */
        public String getPassword() {
                return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
                this.password = password;
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
         * @return the isAdmin
         */
        public boolean isIsAdmin() {
                return isAdmin;
        }

        /**
         * @param isAdmin the isAdmin to set
         */
        public void setIsAdmin(boolean isAdmin) {
                this.isAdmin = isAdmin;
        }

        /**
         * @return the avatarURL
         */
        public String getAvatarURL() {
                return avatarURL;
        }

        /**
         * @param avatarURL the avatarURL to set
         */
        public void setAvatarURL(String avatarURL) {
                this.avatarURL = avatarURL;
        }

        @Override
        public String toString() {
                return "AccountDTO{" + "userName=" + userName + ", email=" + email + ", password=" + password + ", name=" + name + ", isAdmin=" + isAdmin + ", avatarURL=" + avatarURL + '}';
        }
 
}
