<%-- 
    Document   : ReadChapter
    Created on : Apr 22, 2021, 3:02:06 PM
    Author     : Gray
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${currNovel.novelName} - ${currChap.chapterName}</title>
        <link rel="stylesheet" type="text/css" href="styles/index.css"> 
        <link rel="stylesheet" type="text/css" href="styles/chapter.css"> 
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
                    <c:out value="${tagList}" />
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
                        <button><a href="LoginServlet" class="login-btn">Login</a></button>
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
                    <input type="text" placeholder="Search.." id="search-bar" name="nameSearch">
                    <button id="search-btn" type="submit"><i class="fa fa-search"></i></button>
                </form>
            </div>
        </div>
        <div style="text-align: center">
            <h1 style="font-size: 120%">Novel: ${currNovel.novelName}</h1>
            <h2>Chapter: ${currChap.chapterName}</h2>
        </div>
        <div style="width: 75%; margin: 0 auto; text-align: justify">
            <c:forEach items="${chapLines}" var="line">
                ${line} <br>
            </c:forEach>
        </div>
        <div class="navigator">
            <c:if test="${prevChap != null}">
                <a class="fw" href="NovelServlet?a=read&n=${prevChap.novel.novelID}&c=${prevChap.chapterID}" class="changeChapter">Previous Chapter</a>
            </c:if>
            <c:if test="${nextChap != null}">
                <a class="fw" href="NovelServlet?a=read&n=${nextChap.novel.novelID}&c=${nextChap.chapterID}" class="changeChapter">Next Chapter</a>
            </c:if>
            <a class="goback" href="NovelServlet?a=novel_info&n=${currNovel.novelID}">Back to chapters list</a>
        </div>

        <div class="chapterList">

        </div>
        <div id="commentSection">
            <h3>Comments</h3>
            <c:if test="${sessionScope.user != null}">
                <div>
                    <form action="CommentServlet" method="POST" id="form">
                        Comment <textarea name="context" placeholder="Maximum 200 characters" id="cmtInput"></textarea><span style="visibility:hidden; color:red" id="cmtMsg">Maximum: 200 characters</span> <br>
                        <input type="hidden" name="chapterID" value="${currChap.chapterID}"/>
                        <input type="hidden" name="novelID" value="${currNovel.novelID}"/>
                        <input type="submit" value="Submit"/>
                    </form>
                </div>
            </c:if>
            <c:choose>
                <c:when test="${comments.size() eq 0}">
                    <p>No comments</p>
                </c:when>
                <c:otherwise>
                    <table class="commentTable">
                        <thead>
                            <tr>
                                <th class="col1">User</th>
                                <th class="col2">Comment</th>
                                <th class="col3">Upload date</th>
                        <c:if test="${sessionScope.user.isAdmin == true}"><th>Action</th></c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${comments}" var="comment">
                            <tr>
                                <td class="col1">${comment.user.username}</td>
                                <td class="col2">${comment.context}</td>
                                <td class="col3">${comment.commentDate}</td>
                            <c:if test="${sessionScope.user.isAdmin == true || sessionScope.user.username.equals(comment.user.username)}">
                                <td><a href="CommentServlet?a=delete&cmid=${comment.commentID}&nid=${currNovel.novelID}&cid=${currChap.chapterID}">delete</a></td>
                            </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <script defer>
            const input = document.getElementById("cmtInput");
            const form = document.getElementById("form");
            form.addEventListener('click', event => {
                if (input.value.length < 1)
                    event.preventDefault();
                else if (input.value.length > 200) {
                    event.preventDefault();
                    document.getElementById("cmtMsg").style.visibility = "visible";
                }
            });
        </script>
    </body>
</html>
