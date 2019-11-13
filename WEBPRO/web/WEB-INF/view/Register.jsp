<%-- 
    Document   : Register
    Created on : Nov 13, 2019, 9:23:05 AM
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
        <h1>Register</h1>
        ${message}<br>
        <form action="Register" method="post">
            Username:<input type="text" name="username"><br>
            Password:<input type="password"name="password"><br>
            Confirmed password:<input type="password" name="conpass"><br>
            Full Name: <input type="text" name="fullname"><br>
            E-mail:<input type="email" name="email"><br>
            Type:<select name="type">
                <option value="Teacher">Teacher</option>
                <option value="Student">Student</option>
               
            </select><br>
            <input type="submit" value="Register">

        </form>
    </body>
</html>
