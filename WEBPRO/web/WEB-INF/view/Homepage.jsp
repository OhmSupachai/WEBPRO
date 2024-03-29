<%-- 
    Document   : Homepage
    Created on : Nov 16, 2019, 5:08:08 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>Homepage</title>
    </head>
    <body>
         <form action="Register" method="post">
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            <link href="https://fonts.googleapis.com/css?family=Lobster&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">
            <link href="css/home.css" rel="stylesheet">      
      
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
        <%-- close header --%>
        
        <hr>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12" align="center"> 
                    <center> <h4 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">Welcome : ${user.getUserFullname()}</h4> </center>
                    
                </div>
                    <br>
                <div class="col-md-4">
                </div>
                <div class="col-md-4" align="left">
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light static-top navbar-dark bg-dark">
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="navbar-toggler-icon" ></span>
                        </button> <h4 style="color: azure;font-family: 'Montserrat', sans-serif; font-weight: bold;" align="center">Quiz list : </h4>
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            
                            <form class="form-inline">
                                <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4>
                                
                            </form>
                            <ul class="navbar-nav ml-md-auto">
                                <li class="nav-item active">
                                    <a class="nav-link" href="#">Shortcut <span class="sr-only">(current)</span></a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown">Menu</a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                                        <a class="dropdown-item" href="Editpass">View Account</a> <a class="dropdown-item" href="Logout">Logout</a>
                                        
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-striped table-dark" >
                        <thead>
                            <tr>
                                <th>No.</th>  
                                <th>Title</th>  
                                <th>Do test</th>
                                <th></th>                            
                            </tr>
                        </thead>
                        <tbody>
                           <c:forEach items="${quiz}" var="i">
                            <tr class="table-active">
                                <td>${i.getQuizId()}</td>
                                <td>${i.getQuizName()}</td>                                
                                                            
                                <td><a href="Quiz?id=${i.getQuizId()}">Do Test</a></td>                                                          
                            </tr>
                           </c:forEach>
                            
                        </tbody>
                    </table>
                </div>
            </div>
            <br><br><br>
        </div>
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
    </body>
</html>
