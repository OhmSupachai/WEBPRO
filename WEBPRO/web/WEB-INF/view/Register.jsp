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
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <title>Register</title>
        </head>
        <body>
            <form action="Register" method="post">
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            <link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
            <link href="css/regis.css" rel="stylesheet">      



            <div style="background-color: darkorange ">

                <div class="p-0 mb-0 jumbotron jumbotron-fluid d-flex justify-content-center" style="background-color: darkorange">

                </div>
                <h1 style="color:white;font-family: 'Lobster', cursive;font-weight: bold;" align="center">QuitQuiz</h1>
                <h2 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">The Online Exam</h2>
                <div class="text-center">
                    <div class="spinner-grow text-primary" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-secondary" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-success" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-danger" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-warning" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-info" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="spinner-grow text-light" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
                <hr>
            </div>

            <div class="row justify-content-center"style="height: 100%">
                <div class="card col-5" style="height: 100%">
                    <div class="row">
                        <div class="col-md-12" align="center">
                            <br><br>
                            <h4 style="font-weight: bold;">Register</h4>
                            <p style="color: #5d647f">*Please input your data to create your account*</p>
                        </div>
                    </div>
                    <table align="center">
                        <tbody><tr>
                                <td>Username </td> <td><input type="text" class="form-control" name="username" placeholder="username" align="center"></td>         
                            </tr>
                            <tr>
                                <td>Password </td> <td><input type="password" class="form-control" name="password" placeholder="password" align="center"></td>
                            </tr>
                            <tr>
                                <td>Confirm Password </td> <td><input type="password" class="form-control" name="conpass" placeholder="Confirm Password" align="center"></td>
                            </tr>    
                            <tr><td>Email </td> <td><input type="email" name="email" class="form-control"  placeholder="email" align="center"></td>
                            </tr>
                            <tr>
                                <td>FullName </td> <td><input type="text" name="fullname" class="form-control"  placeholder="firstname  lastname" align="center"></td>
                            </tr>

                        </tbody></table>
                    <br><br>
                    <div class="row">
                        <div class="col-md-12" align="center">
                            <input type="submit" class="btn btn-warning" value="Confirm Register Data" align="center">

                        </div>
                    </div>

                    <br> 
                    <div class="container">
                        <center>  <img src="logo.png" class="img-fluid" alt="logo" width="20%"> </center>
                    </div><br>
                </div>
            </div>
            </form>>
        </body>
    </html>


