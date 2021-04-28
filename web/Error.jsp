<%-- 
    Document   : Error
    Created on : Apr 22, 2021, 3:03:09 PM
    Author     : Gray
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="2; url=NovelServlet"/>
        <title>Error Page</title>
    </head>
    <body>
        <h1>${noti}</h1>
        <c:if test="${noti == null}">
                <h1>Error, something went wrong, try again later</h1>
        </c:if>
    </body>
</html>
