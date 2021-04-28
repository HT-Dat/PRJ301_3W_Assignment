<%-- 
    Document   : AddNovelForm
    Created on : Apr 22, 2021, 3:03:44 PM
    Author     : Gray
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page buffer="none" %>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <link rel="stylesheet" type="text/css" href="styles/index.css"> 
                <link rel="stylesheet" type="text/css" href="styles/addnovelform.css"> 
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
                <title>Add Novel Form</title>
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
                <form action="NovelServlet" enctype="multipart/form-data" method="POST" id='form'>
                        <input type="hidden" value="AddNovelDB" name="action">
                        <fieldset>
                                <legend><h1>Add a novel</h1></legend>
                                <c:if test="${duplicateError != null}">
                                        <p style="color: red">${duplicateError}</p>
                                        <button><a href="ChapterServlet?a=addchapform&nid=${duplicatedNovel.novelID}">Add a new Chapter</a></button>
                                </c:if>
                                <div>
                                        <label for="coverURL"><h1>Novel name</h1></label>
                                        <input type="text" name="novelName" id='novelName' value="${novelName}"/> <label style="visibility: hidden; color: red" id='msg'>Novel name can't be empty</label>
                                </div>
                                <div>
                                        <label for="coverURL"><h1>Cover</h1></label>
                                        <input type="file" name="coverURL" id="coverURL"/>
                                </div>
                                <h1>Tags</h1>
                                <c:if test="${TAGERROR != null}"><div style="color:red">${TAGERROR}</div></c:if>
                                        <div class="tagTable">
                                        <c:forEach items="${applicationScope.tagList}" var="tag">
                                                <span class="tag-item">
                                                        <label for="${tag.tagID}" class="tagname">${tag.tagName}</label>
                                                        <input type="checkbox" class="tagItem" style="border: 1px solid black; visibility: hidden" name="tag" value="${tag.tagID}" id="${tag.tagID}"/>
                                                </span>
                                        </c:forEach>
                                </div>
                                <div class="footer">
                                        <input id="add" type="submit" value="Add"/> 
                                        <button id="cancel"><a href="NovelServlet" style="text-decoration: none;">Cancel</a></button>
                                </div>
                        </fieldset>
                </form>
                <script defer>
                        const form = document.getElementById("form");
                        const novelName = document.getElementById("novelName");
                        const msg = document.getElementById("msg");
                        const tagName = document.querySelectorAll(".tagname");
                        tagName.forEach(button => {
                                button.addEventListener('click', () => {
                                        button.parentNode.classList.toggle("selected");
                                });
                        });
                        form.addEventListener('submit', event => {
                                if (novelName.value === "") {
                                        event.preventDefault();
                                        msg.style.visibility = "visible";
                                }
                        });
                </script>                 
        </body>
</html>
