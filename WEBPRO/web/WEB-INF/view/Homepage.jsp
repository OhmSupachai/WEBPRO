<%-- 
    Document   : Homepage
    Created on : Nov 12, 2019, 5:38:37 PM
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
        <h1>Home Page</h1>
        ${user.getUserFullname()} ${user.getUserType()}
        
    </body>
</html>
