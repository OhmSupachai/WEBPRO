<%-- 
    Document   : ViewAccount
    Created on : Nov 26, 2019, 9:59:27 PM
    Author     : ohmsu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">      
        <title>Account</title>
    </head>
    <body>
        <form action="Editpass" method="post" >
        <div style="background-color: darkorange ">

            <div class="p-0 mb-0 jumbotron jumbotron-fluid d-flex justify-content-center" style="background-color: darkorange">

            </div>

            <h1 style="color:white;font-family: 'Lobster', cursive;font-weight: bold;" align="center">QuitQuiz</h1>
            <h2 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">Let's edit your account</h2>
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
                <div class="container-fluid">
                    <br>
                    <div class="row">
                        
                        <div class="col-md-12" align="center">
                            <h3 style="color: #5d647f;font-family: 'Lobster', cursive;font-weight: bold;" align="center">View Account</h3>
                            <p>*Please keep your data as your top secret*</p>
                        </div>
                    </div>
                     <div class="container">
                        <center> <img src="user.png" class="img-fluid" alt="logo" width="20%" > </center>
                       
                        
                    </div>
                    <br>
                    <table align="center">
                        <tr>
                            <td>Username :</td> <td>${user.getUsername()}</td> 
                        </tr>
                        <tr>    
                        <td>Email :</td> <td>${user.getUserEmail()}</td>
                        </tr>
                        <tr>
                        <td>Full Name :</td><td>${user.getUserFullname()}</td>
                        </tr>
                        <tr>
                            <td><hr></td>
                        </tr>
                        <tr>
                            <td><h5 style="color: #5d647f;font-family: 'Lobster', cursive;font-weight: bold;" align="center">Change your password here</h5></td>
                        </tr>
                        <tr>
                            <td><hr></td>
                        </tr>
                        <tr>
                            <td>Old Password :</td> <td><input type="password" name="oldpassword" class="form-control"  placeholder="oldpassword" align="center"></td>
                        </tr>
                        <tr>
                            <td>New Password :</td> <td><input type="password" name="newpassword" class="form-control"  placeholder="newpassword" align="center"></td>
                        </tr>
                        <tr>
                            <td>Confirm New Password :</td> <td><input type="password" name="confirmnewpassword" class="form-control"  placeholder="confirm newpassword" align="center"></td>
                        </tr>
                            
                    </table>
                        <center> ${message}</center>
                    <br>
                    <div class="row">
                        <div class="col-md-12" align="center">
                            <button input type="submit" class="btn btn-success"  value="Confirm Register Data" name="home">Confirm Register Data</button>
                            <button input type="submit" class="btn btn-secondary" value="Return to Home" name="home">Return to Home</button>
                            
                        </div>
                    </div>
                    <br>
                </div>
            </div>
        </div>
        </form>>
</body>
</html>

