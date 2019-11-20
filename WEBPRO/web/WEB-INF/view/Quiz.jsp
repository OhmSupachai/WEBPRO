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

        <c:forEach items="${q}" var="i" varStatus="vs">
            ${vs.count}${i.getQuestionName()}<br>
            <form action="Quiz">
                <c:forEach items="${i.getChoiceCollection()}" var="choice" varStatus="cvs">

                    <input type="radio" name="correct" value="choice.getCorrect()">(${cvs.count})${choice.getChoiceName()}<br> 

                </c:forEach>
            </form> 
        </c:forEach>
            <form action="Quiz">
                <input type="submit" >
            </form>



    </body>
</html>
