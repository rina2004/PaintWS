<%-- 
    Document   : ManagerProduct
    Created on : Oct 26, 2024, 10:58:44 AM
    Author     : lytu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            img {
                width: 200px;
                height: 120px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Manage <b>Product</b></h2>
                        </div>
                        <div class="col-sm-6">
                            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons"></i> <span>Add New Product</span></a>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty sessionScope.message}">
                    <div class="alert-info">
                        ${sessionScope.message}
                        <% session.removeAttribute("message"); %> 
                    </div>
                </c:if>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th><span class="custom-checkbox">
                                    <input type="checkbox" id="selectAll">
                                    <label for="selectAll"></label>
                                </span></th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listP}" var="o">
                            <tr>
                                <td>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="checkbox1" name="options[]" value="1">
                                        <label for="checkbox1"></label>
                                    </span>
                                </td>
                                <td>${o.productID}</td>
                                <td>${o.productName}</td>
                                <td><img src="${o.image}"></td>
                                <td><fmt:formatNumber value="${o.unitPrice}" pattern="#,###" /> VND</td>
                                <td>
                                    <a href="loadProduct?pid=${o.productID}" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit"></i></a>
                                    <a href="delete?pid=${o.productID}" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Back to home</a>
        </div>

        <!-- Add Modal HTML -->
        <div id="addEmployeeModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="add" method="post">
                        <div class="modal-header">                        
                            <h4 class="modal-title">Add Product</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        </div>
                        <div class="modal-body">                    
                            <div class="form-group">
                                <label>Name</label>
                                <input name="name" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Image</label>
                                <input name="image" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Unit Price (Enter number only)</label>
                                <input name="price" type="number" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Units In Stock</label>
                                <input name="unitsInStock" type="number" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Quantity Sold</label>
                                <input name="quantitySold" type="number" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Volume (Enter number only)</label>
                                <input name="volume" type="number" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Color</label>
                                <input name="color" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>Supplier</label>
                                <select name="supplierID" class="form-select" aria-label="Select Supplier" required>
                                    <c:forEach items="${listS}" var="s">
                                        <option value="${s.id}">${s.companyName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Description</label>
                                <textarea name="description" class="form-control" required></textarea>
                            </div>
                            <div class="form-group">
                                <label>Category</label>
                                <select name="category" class="form-select" aria-label="Default select example">
                                    <c:forEach items="${listC}" var="o">
                                        <option value="${o.id}">${o.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Status (Trạng thái)</label>
                                <select name="status" class="form-select" required>
                                    <option value="1">Còn hàng</option>
                                    <option value="0">Hết hàng</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                            <input type="submit" class="btn btn-success" value="Add">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/manager.js" type="text/javascript"></script>
    </body>
</html>