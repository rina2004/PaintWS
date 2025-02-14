<%-- 
    Document   : Edit
    Created on : Oct 26, 2024, 10:46:16 PM
    Author     : anhbu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <title>Bootstrap CRUD Data Table for Database with Modal Form</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="css/manager.css" rel="stylesheet" type="text/css"/>
        <style>
            img{
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
                            <h2>Edit <b>Product</b></h2>
                        </div>
                        <div class="col-sm-6">
                        </div>
                    </div>
                </div>
            </div>

           
            <div id="editEmployeeModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form action="edit" method="post">
                            <div class="modal-header">						
                                <h4 class="modal-title">Edit Product</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Product ID</label>
                                    <input value="${detail.productID}" name="id" type="text" class="form-control" readonly required>
                                </div>
                                <div class="form-group">
                                    <label>Name</label>
                                    <input value="${detail.productName}" name="name" type="text" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Image</label>
                                    <input value="${detail.image}" name="image" type="text" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Unit Price</label>
                                    <input value="${detail.unitPrice}" name="price" type="text" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Units In Stock</label>
                                    <input value="${detail.unitsInStock}" name="unitsInStock" type="number" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Quantity Sold</label>
                                    <input value="${detail.quantitySold}" name="quantitySold" type="number" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Volume</label>
                                    <input value="${detail.volume}" name="volume" type="text" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Color</label>
                                    <input value="${detail.color}" name="color" type="text" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label>Supplier</label>
                                    <select name="supplierID" class="form-select" aria-label="Select Supplier" required>
                                        <c:forEach items="${listS}" var="s">
                                            <option value="${s.id}" 
                                                    <c:if test="${s.id == detail.supplier.id}">selected</c:if>
                                                    >${s.companyName}</option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <div class="form-group">
                                    <label>Description</label>
                                    <textarea name="description" class="form-control" required>${detail.description}</textarea>
                                </div>

                                <div class="form-group">
                                    <label>Category</label>
                                    <select name="category" class="form-select" aria-label="Default select example" required>
                                        <c:forEach items="${listC}" var="o">
                                            <option value="${o.id}" 
                                                    <c:if test="${o.id == detail.category.id}">selected</c:if>
                                                    >${o.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Thêm thuộc tính Discontinued và Status -->
                                <div class="form-group">
                                    <label>Discontinued (Ngừng bán)</label>
                                    <input name="discontinued" type="checkbox" class="form-check-input" <c:if test="${detail.discontinued}">checked</c:if>>
                                    </div>
                                    <div class="form-group">
                                        <label>Status (Trạng thái)</label>
                                        <select name="status" class="form-select" required>
                                            <option value="1" <c:if test="${detail.status}">selected</c:if>>Còn hàng</option>
                                        <option value="0" <c:if test="${not detail.status}">selected</c:if>>Hết hàng</option>
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
