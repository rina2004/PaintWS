<%-- 
    Document   : EditAccount
    Created on : Oct 30, 2024
    Author     : anhbu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Account</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Edit <b>Account</b></h2>
                        </div>
                    </div>
                </div>
            </div>

           

            <div id="editAccountModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="editUser" method="post">
                            <div class="modal-header">						
                                <h4 class="modal-title">Edit Account</h4>
                            </div>
                            <div class="modal-body">
                                <!-- ID tài khoản, không thể chỉnh sửa -->
                                <div class="form-group">
                                    <label>User ID</label>
                                    <input value="${account.userID}" name="id" type="text" class="form-control" readonly required>
                                </div>
                                <!-- Tên tài khoản -->
                                <div class="form-group">
                                    <label>Username</label>
                                    <input value="${account.userName}" name="username" type="text" class="form-control" readonly required>
                                </div>
                                <!-- Mật khẩu -->
                                <div class="form-group">
                                    <label>Password</label>
                                    <input value="${account.password}" name="password" type="password" class="form-control" required>
                                </div>
                                <!-- Email -->
                                <div class="form-group">
                                    <label>Email</label>
                                    <input value="${account.email}" name="email" type="email" class="form-control" required>
                                </div>
                                <!-- Số điện thoại -->
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input value="${account.phone}" name="phone" type="text" class="form-control" required>
                                </div>
                                <!-- Địa chỉ -->
                                <div class="form-group">
                                    <label>Address</label>
                                    <input value="${account.address}" name="address" type="text" class="form-control" required>
                                </div>
                                <!-- Vai trò -->
                                <div class="form-group">
                                    <label>Role</label>
                                    <select name="roleID" class="form-select" aria-label="Select Role" required>
                                        <option value="1" <c:if test="${account.roleID == 1}">selected</c:if>>Admin</option>
                                        <option value="2" <c:if test="${account.roleID == 2}">selected</c:if>>User</option>
                                    </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="submit" class="btn btn-success" value="Edit">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="js/manager.js" type="text/javascript"></script>
    </body>
</html>
