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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">  
        <title> Scores </title>

    </head>
    <body>
        <div style="background-color: darkorange ">

            <div class="p-0 mb-0 jumbotron jumbotron-fluid d-flex justify-content-center" style="background-color: darkorange">

            </div>

            <h1 style="color:white;font-family: 'Lobster', cursive;font-weight: bold;" align="center">QuitQuiz</h1>
            <h2 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">Let's do the quiz</h2>
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
        <br>
    <center>   <div class="card text-white bg-warning mb-3 " style="max-width: 18rem;">
            <div class="card-header"> <h1>Your score</h1></div>
            <div class="card-body">
                
                <h4 class="card-title">${score}</h4>
                <br>

                <form action="Login">
                     <center> <button input type="submit" class="btn btn-secondary"">Homepage</button> </center>
                </form>

            </div>
        </div> </center>



</body>
</html>



