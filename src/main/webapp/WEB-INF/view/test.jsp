<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">

    <title>Payments</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">

    <img src="https://personal-money-management.utah.edu/_images/money_icon.png" width="50" height="50" alt="logo">

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-targer="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a href="#" class="nav-link">Home</a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link" >Info</a>
            </li>
            <li class="nav-item">
                <a href="#" class="nav-link">Contacts</a>
            </li>
        </ul>
        <form method="get" class="form-inline my-2 my-lg-0">
            <input type="text" class="form-control mr-sm-2" placeholder="login" name="login" aria-label="Sing in" required>
            <input type="password" class="form-control mr-sm-2" placeholder="password" name="password" aria-label="Sing in" required>
            <button class="btn btn-outline-success my-2 my-sm-p">Sing in</button>
            <button class="btn btn-outline-success my-2 my-sm-p" data-toggle="modal"
                    data-target="#exampleModal" >Sing up</button>

        </form>
    </div>
</nav>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModal" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Registration</h5>
                <button class="close" type="button" data-dissmiss="modal"
                        aria-lable="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div  class="container-fluid">
                    <form action="/registration">
                        <div class="form-froup">
                            <label for="exampleInputEmail"></label>
                            <input type="email" class="form-control" id="exampleInputEmail"
                                   aria-describdby="emailHelp"
                                   placeholder="Email" name="email" required>
                            <small id="emailHelp" class="form-text text muted">
                                Enter your email
                            </small>
                        </div>
                        <div class="form-froup">
                            <label for="exampleInputPass"></label>
                            <input type="password" class="form-control" id="exampleInputPass"
                                   placeholder="Password" name="password" required>
                            <small id="passHelp" class="form-text text muted">
                                Enter your password
                            </small>
                        </div>
                        <div class="form-froup">
                            <label for="exampleInputName"></label>
                            <input type="password" class="form-control" id="exampleInputName"
                                   placeholder="Name" name="name" required>
                            <small id="nameHelp" class="form-text text muted">
                                Enter your name
                            </small>
                        </div>
                        <div class="form-froup">
                        <label for="exampleInputLastName"></label>
                        <input type="password" class="form-control" id="exampleInputLastName"
                               placeholder="Last name" name="lastName" required>
                        <small id="lastnameHelp" class="form-text text muted">
                            Enter your last name
                        </small>
                    </div>
                        <div class="form-froup">
                            <label for="exampleInputPhone"></label>
                            <input type="password" class="form-control" id="exampleInputPhone"
                                   placeholder="Phone" name="phone" required>
                            <small id="phoneHelp" class="form-text text muted">
                                Enter your phone
                            </small>
                        </div>
                        <button type="submit" class="btn btn-primary">Register</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<h1>Hello, world!</h1>


</body>
</html>