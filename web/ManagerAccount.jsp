<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage User Accounts</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            img {
                width: 50px; /* Kích thước hình ảnh có thể điều chỉnh theo nhu cầu */
                height: 50px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Manage <b>Users</b></h2>
                        </div>
                        <div class="col-sm-6">
                            <a href="#addUserModal" class="btn btn-success" data-toggle="modal">
                                <i class="material-icons">&#xE147;</i> <span>Add New User</span>
                            </a>
                        </div>
                    </div>
                </div>
                <% 
                    String error = (String) session.getAttribute("errorU"); 
                    String success = (String) session.getAttribute("successU");
                %>
                
                <% if (success != null) { %>
                <div class="alert alert-success"><%= success %></div>
                <% session.removeAttribute("successU"); %>
                <% } %>
                
                <% if (error != null) { %>
                <div class="alert alert-danger"><%= error %></div>
                <% session.removeAttribute("errorU"); %>
                <% } %>

                
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${listU}" var="user">
                            <tr>
                                <td>${user.userID}</td>
                                <td>${user.userName}</td>
                                <td>${user.password}</td>
                                <td>${user.address}</td>
                                <td>${user.phone}</td>
                                <td>${user.email}</td>
                                <td>${user.roleID}</td>
                                <td>
                                    <a href="loadacc?uid=${user.userID}" class="edit" data-toggle="modal"><i class="material-icons" title="Edit">&#xE254;</i></a>
                                    <a href="deleteacc?uid=${user.userID}" class="delete" data-toggle="modal"><i class="material-icons" title="Delete">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Back to home</a>
        </div>

        <!-- Add User Modal HTML -->
        <div id="addUserModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="addUser" method="post">
                        <div class="modal-header">
                            <h4 class="modal-title">Add User</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Username</label>
                                <input name="username" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input name="password" type="password" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Address</label>
                                <input name="address" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Phone</label>
                                <input name="phone" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input name="email" type="email" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Role</label>
                                <select name="roleID" class="form-control" required>
                                    <option value="1">Admin</option>
                                    <option value="2">User</option>
                                    <!-- Thêm các vai trò khác nếu có -->
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-success" value="Add" id="addButton">
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <script src="js/manager.js" type="text/javascript"></script>

    </body>
</html>
