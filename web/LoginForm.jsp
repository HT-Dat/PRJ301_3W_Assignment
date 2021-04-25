<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/login.css"> 
        <title>Login </title>
    </head>
    <body>
        <form action="LoginServlet?action=login" method="POST">
            UserName
            <input type="text" name="username" />
            Password
            <input type="password" name="password" />
            <input type="submit" name="login"  />
        </form>
    </body>
</html>