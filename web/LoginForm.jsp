<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="styles/login.css"> 
                <title>Login </title>
        </head>
        <body>
                
                <form action="LoginServlet?action=login" method="POST">
                        <h4 style="color : red">${noti}</h4>
                        UserName
                        <input type="text" name="username" />
                        Password
                        <input type="password" name="password" />
                        <input type="submit" name="login"  />
                </form>
                <div id="register">
                        Don't have account?
                        <a href="RegisterServlet">Sign Up</a>
                </div>
        </body>
</html>