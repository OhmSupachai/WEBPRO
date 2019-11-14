<%-- 
    Document   : Homepage
    Created on : Nov 12, 2019, 5:38:37 PM
    Author     : ohmsu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Home Page</h1>
        <h2>${user.getUserFullname()} ${user.getUserType()}</h2>
        <ol>
        <c:forEach items="${quiz}" var="i">
            <li><a href="Quiz">${i.getQuizName()}</a><br></li>
        </c:forEach>
            </ol>
            
        
    </body>
</html>
