<%-- 
    Document   : UpdateNovelForm
    Created on : Apr 22, 2021, 3:04:42 PM
    Author     : Gray
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Update novel</title>
        </head>
        <body>
                <div class="navbar">
                        <img src="images/logo.svg" alt="Logo-unclickable" id="logo">
                        <a class="home" href="NovelServlet">Home</a>
                        <div class="dropdown">
                                <a class="drop-btn" href="#">Category <i class="fa fa-caret-down"></i></a>
                                <c:set var="begin" value="0"/>
                                <c:set var="end" value="5"/>
                                <ul class="dropdown-content">
                                        <c:out value="${tagList}" />
                                        <c:forEach begin="0" end="4">
                                                <div class=column>
                                                        <c:forEach items="${applicationScope.tagListObj}" var="tag" begin="${begin}" end="${end}">
                                                                <li><a class="tag" href="NovelServlet?a=searchtag&id=${tag.tagID}"><c:out value="${tag.getTagName()}"/></a></li>
                                                        </c:forEach>
                                                </div>
                                                <c:set var="begin" value="${begin+7}"/>
                                                <c:set var="end" value="${end+7}"/>
                                        </c:forEach>
                                </ul>
                        </div>
                        <c:choose>
                                <c:when test="${sessionScope.user == null}">
                                        <button class="login"><a href="LoginServlet" class="login-btn"><span>Login</span></a></button>
                                </c:when>
                                <c:otherwise>
                                        <div class="user-mng">
                                                <img src="${pageContext.request.contextPath}/images/avatars/${sessionScope.user.avatarURL}" alt="user" id="avatar">
                                                <ul class="user-dropdown">
                                                        <li><a id="Manage" href="ManageAccountServlet">Manage Account</a></li>
                                                        <li><a id="Logout" href="LoginServlet?action=logout" onclick="return confirm('Do you want to logout ?')">Logout</a></li>
                                                </ul>
                                        </div>
                                </c:otherwise>
                        </c:choose>
                        <div class="search-container">
                                <form action="NovelServlet" method="post">
                                        <input type="hidden" name="a" value="searchname"/>
                                        <input type="text" placeholder="Search.." id="search-bar" name="nameSearch">
                                        <button id="search-btn" type="submit"><i class="fa fa-search"></i></button>
                                </form>
                        </div>
                </div>
        <c:set var="user" value="${sessionScope.user}"/>
        <form action="NovelServlet" method="POST" enctype="multipart/form-data" id="form">
                <input type="hidden" name="action" value="UpdateNovelDB"/>
                <input type="hidden" name="nid" value="${n.novelID}" id="novelID" class="input" readonly/>
                <fieldset>
                        <legend><h1>Update Novel</h1></legend>
                        <div>
                                <label>Novel Name</label><br>
                                <input type="text" name="novelName" value="${nName}" id="novelName" class="input"/>
                                <c:if test="${ERROR != null}">
                                        <p style="color:red">${ERROR}</p>
                                </c:if>
                        </div>
                        <div>
                                <label>Cover</label><br>
                                <img src="${pageContext.request.contextPath}/images/covers/${n.coverURL}" id="avatar"/><br>
                                <input type="file" name="coverURL"/>
                        </div>
                        <div>
                                <input type="submit" value="Update" id="submitBtn"/>
                                <button type="button" id="cancel"><a href="NovelServlet">Cancel</a></button>
                        </div>
                </fieldset>
        </form>
</body>
</html>
