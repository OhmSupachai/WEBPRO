<%-- 
    Document   : Scores
    Created on : Nov 24, 2019, 1:05:30 PM
    Author     : ohmsu
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
        <table align="center">
            <tr>
                <td align="center"><h1>Test Result</h1></td>
            </tr>
            <tr>
                <td align="center"><h2>User : ${user.getUserFullname()}</h2></td>
            </tr>
            <tr>
                <td align="center"><h2>Score : ${score} / 10<h2></td>
            </tr>
            <tr>
                <td align="center">
                    <form action="Login">
                        <input type="submit" value="Return to Homepage">
                    </form>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <h3 style="color:green;font-family:courier;font-weight: bold;">***Thanks for participating our quiz!***</h3>
                </td>
            </tr>
        </table>
    </body>
</html>
