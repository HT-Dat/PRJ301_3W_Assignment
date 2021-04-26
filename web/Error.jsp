<%-- 
    Document   : Error
    Created on : Apr 22, 2021, 3:03:09 PM
    Author     : Gray
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="2; url=NovelServlet"/>
        <title>Error Page</title>
    </head>
    <body>
        <h1>ERROR</h1>
        <h2>${ACCOUNTNOTFOUNDERROR}</h2>
        <h2>${DUPLICATEDEMAILERROR}</h2>
        <h2>${UPDATEFAILDERROR}</h2>
        <h2>${TAGNOTFOUNDERROR}</h2>
        <h2>${NOVELNOTFOUND}</h2>
        <h2>${CHAPTERNOTFOUND}</h2>
    </body>
</html>
