<%-- 
    Document   : Homepage
    Created on : Apr 22, 2021, 3:03:31 PM
    Author     : Gray
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Homepage</title>
                <link rel="stylesheet" type="text/css" href="styles/homepage.css"> 
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
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
                                        <c:forEach begin="0" end="4">
                                                <div class=column>
                                                        <c:forEach items="${applicationScope.tagList}" var="tag" begin="${begin}" end="${end}">
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
                                        <div class="login">
                                                <button class="login-btn"><a href="LoginServlet">Login</a></button>
                                        </div>
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
                                        <input type="text" placeholder="Search..." id="search-bar" name="nameSearch">
                                        <button id="search-btn" type="submit"><i class="fa fa-search"></i></button>
                                </form>
                        </div>
                </div>
                <c:set var="user" value="${sessionScope.user}"/>
                
                <%--Properly headings for each action--%>
                <div class="n-container">
                        <c:if test="${tag != null}">
                                <h1 style="margin-left: 2%;">Novels with tag: ${tag.tagName}</h1>
                        </c:if>
                        <c:if test="${BookmarkFlag != null}">
                                <h1 style="color:red; font-size: 150%; text-align: center">${BookmarkFlag}</h1>
                        </c:if>
                        <c:if test="${addFlag != null}">
                                <h1 style="color:red; font-size: 150%; text-align: center">Your Novels</h1>
                        </c:if>
                        <c:if test="${not empty novelList}">
                                <ul class="n-list">
                                        <c:forEach items="${novelList}" var="novel">
                                                <li class="n-listitem">
                                                        <a href="NovelServlet?action=NovelInfo&nid=${novel.novelID}"><img class="cover" src="${pageContext.request.contextPath}/images/covers/${novel.coverURL}"/></a>
                                                        <a class="n-title" href="NovelServlet?action=NovelInfo&n=${novel.novelID}">${novel.novelName}</a>
                                                        <p>${novel.author.getUserName()}</p>
                                                        <c:if test="${addFlag != null}">
                                                                <a href="ChapterServlet?action=AddChapterForm&nid=${novel.novelID}">Add new chapter</a>
                                                        </c:if>
                                                        <c:if test="${user.isAdmin == true || user.userName.equals(novel.author.userName)}">
                                                                <p><a href="NovelServlet?action=DeleteNovel&nid=${novel.novelID}" onclick="return confirm('This action will remove all chapters in this novel.\nAre you sure ?')" class="del">Delete</a></p>
                                                                <c:if test="${user.userName.equals(novel.author.userName)}">
                                                                        <p><a href="NovelServlet?action=UpdateNovelForm&nid=${novel.novelID}" class="del">Update</a></p>
                                                                </c:if>
                                                        </c:if>
                                                </li>
                                        </c:forEach>
                                </ul>
                        </c:if>
                        <%--If your novel list is emty, ask if you want to add a novel or return to homepage --%>
                        <c:if test="${NoNovelError != null}">
                                <h2 style="color: red">Sorry, you don't have any novel yet!</h2>
                                <c:if test="${flag != null}">
                                        <h1 style="text-align: center"><a href="NovelServlet?a=n_form" style="font-size: 250%; border: dashed 7px; line-height: 5; padding: 10%;">Add a novel</a></h1>
                                        <h1><a class="home" href="NovelServlet">Return to homepage</a></h1>
                                </c:if>
                        </c:if>
                        <%--If your bookmark is empty--%>
                        <c:if test="${EMPTYBOOKMARK != null}">
                                <h1 style="color:red">${EMPTYBOOKMARK}</h1>
                                <h1><a class="home" href="NovelServlet">Return to homepage</a></h1>
                        </c:if>
                </div>
                <%--Actions only availabe if you are an user/admin--%>
                <c:choose>
                        <c:when test="${user != null}">
                                <div class="side-box">
                                        <a href="NovelServlet?action=AddNovelForm">Add a novel</a><br>
                                        <a href="NovelServlet?action=YourNovelList&u=${user.userName}">Your novels</a><br>
                                        <a href="BookmarkServlet?action=BookmarkList">Bookmark</a><br>
                                </div>
                        </c:when>
                        <c:otherwise>
                                <div class="side-box">
                                        <a href="LoginServlet">Add a novel</a><br>
                                        <a href="LoginServlet">Your novels</a><br>
                                        <a href="LoginServlet">Bookmark</a><br>
                                </div>
                        </c:otherwise> 
                </c:choose>
        </body>
</html>
