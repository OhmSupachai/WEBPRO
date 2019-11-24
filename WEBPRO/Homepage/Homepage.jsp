<%-- 
    Document   : Homepage
    Created on : Nov 16, 2019, 5:08:08 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <title>JSP Page</title>
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
                <div class="col-md-4" align="Right">
                    <h3 style="color:blue;font-family:courier;font-weight: bold;" align="center">Welcome : ${user}</h3>
                </div>
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
                            <span class="navbar-toggler-icon"></span>
                        </button> <h4 style="color:whitesmoke;font-family:courier;font-weight: bold;">Search test title : </h4>
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            
                            <form class="form-inline">
                                <h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4>
                                <input class="form-control mr-sm-2" type="text" /> 
                                <button class="btn btn-primary my-2 my-sm-0" type="submit">
                                    Search
                                </button>
                            </form>
                            <ul class="navbar-nav ml-md-auto">
                                <li class="nav-item active">
                                    <a class="nav-link" href="#">Shortcut <span class="sr-only">(current)</span></a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown">Menu</a>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                                        <a class="dropdown-item" href="AccountPage">View Account</a> <a class="dropdown-item" href="Logout">Logout</a>
                                        <div class="dropdown-divider">
                                        </div> <a class="dropdown-item" href="#">Separated link</a>
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
                    <table class="table">
                        <thead>
                            <tr>
                                <th>No.</th>  
                                <th>Title</th>  
                                <th>Creator</th>
                                <th></th>                            
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Science</td>                                
                                <td>John</td>                              
                                <td><a href="ScienceTest">Do Test</a></td>                                                          
                            </tr>
                            <tr class="table-active">
                                <td>2</td>                                                           
                                <td>Math</td>                            
                                <td>Henrik</td>                                                            
                                <td><a href="MathTest">Do Test</a></td>                                                          
                            </tr>
                            <tr class="table-success">
                                <td>3</td>                                                         
                                <td>English</td>                                                          
                                <td>George</td>                                                           
                                <td><a href="EnglishTest">Do Test</a></td>                                                          
                            </tr>
                            <tr class="table-warning">
                                <td>4</td>                                                          
                                <td>Social</td>                                                           
                                <td>Victor</td>                                                          
                                <td><a href="SocialTest">Do Test</a></td>                                                        
                            </tr>
                            <tr class="table-danger">
                                <td>5</td>                                                          
                                <td>Thai</td>                                                          
                                <td>Somchai</td>                                                         
                                <td><a href="ThaiTest">Do Test</a> </td>                                                           
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
