<%-- 
    Document   : Test
    Created on : Nov 24, 2019, 5:56:26 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>        
        <h1 style="color:navy;font-family:courier;font-weight: bold;" align="center">QuitQuiz</h1>
        <h2 style="color:darkblue;font-family:courier;font-weight: bold;" align="center">The Online Exam</h2>
        <hr>
        <h1 style="color:red;font-family:courier;font-weight: bold;" align="center">TestTitle</h1>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4" align="center">
                    <c:forEach items="${q}" var="i" varStatus="vs">
                        ${vs.count}${i.getQuestionName()}<br>
                        <form action="Quiz">
                            <c:forEach items="${i.getChoiceCollection()}" var="choice" varStatus="cvs">
                                <input type="radio" name="correct_${choice.getChoiceId()}" value="${choice.getChoiceId()}">(${cvs.count})${choice.getChoiceName()}<br> 
                            </c:forEach>
                        </form> 
                    </c:forEach>
                </div>
                <div class="col-md-4">
                </div>
            </div>
        </div>
        <form action="Quiz">
            <input type="submit" >
        </form> 
    </body>
</html>
