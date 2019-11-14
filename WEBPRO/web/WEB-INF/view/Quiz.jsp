<%-- 
    Document   : Quiz
    Created on : Nov 14, 2019, 7:04:07 PM
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
        <h1>Hello World!</h1>
        <c:forEach items="${hm}" var="i">
            ${i}<br>
        </c:forEach>
    </body>
</html>
