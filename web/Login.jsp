<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <section class="vh-100">
            <div class="container py-5 h-100">
                <div class="row d-flex align-items-center justify-content-center h-100">
                    <div class="col-md-8 col-lg-7 col-xl-6">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg" class="img-fluid" alt="Login Image">
                    </div>

                    <%
                        Cookie[] cookies = request.getCookies();
                        String savedUser = "";
                        String savedPass = "";
                        boolean rememberMe = false;

                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if ("username".equals(cookie.getName())) {
                                    savedUser = cookie.getValue();
                                }
                                if ("password".equals(cookie.getName())) {
                                    savedPass = cookie.getValue();
                                    rememberMe = true;
                                }
                            }
                        }
                    %>

                    <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                        <form class="form-signin" action="login" method="post">
                            <h1 class="h3 mb-3 font-weight-normal text-center">Sign in</h1>

                            <!-- Hiển thị lỗi nếu có -->
                            <c:if test="${not empty mess}">
                                <p class="text-danger text-center">${mess}</p>
                            </c:if>

                            <div class="form-group">
                                <label for="inputEmail">Username</label>
                                <input name="user" type="text" id="inputEmail" class="form-control" placeholder="Username" required 
                                       value="<%= savedUser %>">
                            </div>

                            <div class="form-group">
                                <label for="inputPassword">Password</label>
                                <input name="pass" type="password" id="inputPassword" class="form-control" placeholder="Password" required 
                                       value="<%= rememberMe ? savedPass : "" %>">
                            </div>

                            <div class="form-group form-check">
                                <input name="remember" value="1" type="checkbox" class="form-check-input" id="rememberMe" <%= rememberMe ? "checked" : "" %>>
                                <label class="form-check-label" for="rememberMe">Remember me</label>
                            </div>

                            <button class="btn btn-success btn-block" type="submit">
                                <i class="fas fa-sign-in-alt"></i> Sign in
                            </button>

                            <hr>

                            <a href="Signup.jsp" class="btn btn-primary btn-block">
                                <i class="fas fa-user-plus"></i> Sign up New Account
                            </a>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
