<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sign Up</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <section class="vh-100">
            <div class="container py-5 h-100">
                <div class="row d-flex align-items-center justify-content-center h-100">
                    <div class="col-md-8 col-lg-7 col-xl-6">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" class="img-fluid" alt="Sign up illustration">
                    </div>
                    <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                        <form class="form-signup" action="signup" method="post">
                            <h1 class="h3 mb-3 font-weight-normal text-center">Sign up</h1>

                            <!-- Display error message if any -->
                            <p class="text-danger text-center">${mess}</p>

                            <!-- User input fields -->
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input name="user" type="text" id="username" class="form-control" placeholder="Enter your username" required autofocus>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input name="email" type="email" id="email" class="form-control" placeholder="Enter your email" required>
                            </div>

                            <div class="form-group">
                                <label for="phone">Phone</label>
                                <input name="phone" type="text" id="phone" class="form-control" placeholder="Enter your phone number" required>
                            </div>

                            <div class="form-group">
                                <label for="address">Address</label>
                                <input name="address" type="text" id="address" class="form-control" placeholder="Enter your address" required>
                            </div>

                            <div class="form-group">
                                <label for="password">Password</label>
                                <input name="pass" type="password" id="password" class="form-control" placeholder="Enter your password" required>
                            </div>

                            <div class="form-group">
                                <label for="repass">Confirm Password</label>
                                <input name="repass" type="password" id="repass" class="form-control" placeholder="Confirm your password" required>
                            </div>

                            <!-- Sign up button -->
                            <button class="btn btn-secondary btn-block" type="submit">
                                <i class="fas fa-user-plus"></i> Sign Up
                            </button>

                            <!-- Back to login link -->
                            <a href="Login.jsp" class="btn btn-link d-block text-center mt-3">
                                <i class="fas fa-angle-left"></i> Back to Login
                            </a>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
