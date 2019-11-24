<%-- 
    Document   : Register
    Created on : Nov 16, 2019, 4:31:41 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Login</title>
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <h1 style="color:navy;font-family:courier;font-weight: bold;" align="center">QuitQuiz</h1>
        <h2 style="color:darkblue;font-family:courier;font-weight: bold;" align="center">The Online Exam</h2>
        <hr>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12" align="center">
                    <h4 style="font-weight: bold;">Register</h4>
                    <p>*Please input your data to create your account*</p>
                </div>
            </div>
            <form action="Register" method="post">
            <table align="center">
                <tr>
                    <td>Username </td> <td><input type="text" name="username"></td>
                </tr>
                <tr>
                    <td>Password </td> <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td>Confirm Password </td> <td><input type="password" name="confirm password"></td>
                </tr>    
                <td>Email </td> <td><input type="email" name="email"></td>
                </tr>
                <tr>
                    <td>Full Name </td> <td><input type="text" name="firstname"></td>
                </tr>
                
            </table>
            <div class="row">
                <div class="col-md-12" align="center">
                    <input type="submit" value="Confirm Register Data">
                </div>
            
            </div>
            </form>
        </div>
    </div>
</body>
</html>
