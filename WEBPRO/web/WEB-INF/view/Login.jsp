<%-- 
    Document   : LoginNew
    Created on : Nov 16, 2019, 4:09:27 PM
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
       <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">




        <div class="container-fluid">




            <div style="background-color: darkorange ">

                <div class="p-0 mb-0 jumbotron jumbotron-fluid d-flex justify-content-center" style="background-color: darkorange">
                    <div class="container">
                        <center>  <img src="logo.png" class="img-fluid" alt="logo" width="20%" > </center>
                    </div>
                </div>
                <h1 style="color:white;font-family: 'Lobster', cursive;font-weight: bold;" align="center">QuitQuiz</h1>
                <h2 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">The Online Exam</h2>
                <hr>
            </div>


            <div class="row">
                <div class="col-md-12">
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                </div>
            </div>



            <div class="row" style="height: 60vh">
                <div class="col-md-6" align="center">
                    <div class="card" style="height: 100%">
                        <div class="p-5 card-body">
                            <form action="Login" method="post">
                                <br>
                                <h3>Login</h3>
                                 
                                <input type="text" class="form-control" name="user" placeholder="Username" align="center"><br>
                                <input type="password" class="form-control" name="pass" placeholder="Password" align="center" ><br><br><br>
                                <input type="submit" class="btn btn-primary" value="Login" align="center" >
                                <h4 style="color:red;font-family:courier;"></h4>

                            </form>
                        </div>
                    </div>
                </div>


                <div class="col-md-6" "="" align="center">
                    <div class="card" style="height: 100%">
                        <div class="p-5 card-body" style="
                             ">
                            <br>
                            <br><br>
                            <h5>Need an Account?</h5>
                            <a href="Register">Register</a>
                            <br><br><br><br>
                           
                            <h5>Continue as Guest</h5>
                            <a href="Homepage">Stay as Guest</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </body>
</html>