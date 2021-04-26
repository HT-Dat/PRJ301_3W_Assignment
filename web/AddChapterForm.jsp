<%-- 
    Document   : ChapterForm
    Created on : Apr 22, 2021, 3:01:26 PM
    Author     : Gray
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Chapter</title>
        <link rel="stylesheet" type="text/css" href="styles/chap_form.css"> 
    </head>
    <body>
        <form action="ChapterServlet" method="POST" id='form'>
            <input type="hidden" name="action" value="add"/>
            <input type="hidden" name="nid" value="${requestScope.novelObj.novelID}"/>
            <h1 class="title">Add a new Chapter</h1>
            <h2>Novel Name: ${requestScope.novelObj.novelName}</h2>
            <br>
            <label class="chapName">Chapter Name</label>
            <input type="text" name="chapname" id="chapname"/>
            <p id="chapNameMsg" style="visibility: hidden; color: red">Chapter name can't be empty</p>
            <div>
                <label style='font-weight: bold; font-size: 150%'>Content</label> <br>
                <textarea name='content' id='content''></textarea>
                <p id='contentMsg' style='visibility: hidden; color: red'>Content can't be empty</p>
            </div>
            <div class="footer">
                <input id="add" type='submit' value='Add'/>
                <button id="cancel"><a href='NovelServlet' style='text-decoration: none; color:#000000'>Cancel</a></button>
            </div>
        </form>
        <script defer>
            const form = document.getElementById("form");
            const chapName = document.getElementById("chapname");
            const content = document.getElementById("content");
            const chapNameMsg = document.getElementById("chapNameMsg");
            const contentMsg = document.getElementById("contentMsg");
            form.addEventListener("submit", event => {
                if (chapName.value === "") {
                    event.preventDefault();
                    chapNameMsg.style.visibility = "visible";
                }
                if (content.value === "") {
                    event.preventDefault();
                    contentMsg.style.visibility = "visible";
                }
            });
        </script>
    </body>
</html>
