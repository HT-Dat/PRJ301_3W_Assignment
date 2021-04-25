<%-- 
    Document   : register_form
    Created on : 04-Mar-2021, 17:28:14
    Aulabelor     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
        <link rel="stylesheet" href="styles/register.css"/>
    </head>
    <body>
        <div class="modal">
            <form action="RegisterServlet" method="POST" enctype="multipart/form-data" id="form" class="modal-content animate">
                <input type="hidden" name="action" value="register"/>
                <h1 class="modal-title">Register</h1>
                <div>
                    <label>Username</label>
                    <input type="text" name="username" value="${username}" id="username" />
                    <c:if test="${duplicatedUser != null}">
                        <span id="dupUser" style="color:red">This username already exists</span>
                    </c:if>
                </div>
                <div id="usernameMsg" style="visibility: hidden" class="error">
                    Username can only be of length 4 to 20 characters
                </div>
                <label>Full name</label>
                <input type="text" name="name" value="${name}" id="name" />
                <div id="nameMsg" style="visibility:hidden" class="error">
                    Full name can not be empty
                </div>
                <label>Email</label>
                <input type="text" name="email" value="${email}" id="email" />
                <c:if test="${duplicatedEmail != null}">
                    <div id="duplicatedEmailMsg" class="error">This email address has already been used</div>
                </c:if>
                <div id="emailMsg" style="visibility:hidden" class="error">
                    Invalid Email
                </div>
                <label>Password</label>
                <input type="password" name="password" id="password" />
                <div id="passMsg" style="visibility:hidden" class="error">
                    Password can only be of length 5 to 32 characters
                </div>
                <label>Confirm password</label>
                <input type="password" name="conpass" id="confpassword" />
                <div id="confpassMsg" style="visibility:hidden" class="error">
                    Confirm password did not match
                </div>
                <label><b>Avatar</b></label>
                <input style="display: block" type="file" name="avatar" value="${avatar}" id="avatar" />
                <div class="footer">
                    <button type="submit">Register</button>
                    <a class="goback" style="display: block;" href="NovelServlet"><b>&laquo; Click here to go
                            back</b></a>
                </div>
            </form>
        </div>
        <script src="js/script.js" defer></script>
    </body>
</html>
