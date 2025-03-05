<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invoice</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa; /* Màu nền nhẹ */
            }
            .invoice-container {
                background-color: #ffffff; /* Nền trắng cho hóa đơn */
                border-radius: 10px; /* Bo tròn các góc */
                padding: 20px; /* Khoảng cách bên trong */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Đổ bóng */
            }
            .invoice-header {
                text-align: center; /* Căn giữa tiêu đề */
                margin-bottom: 20px; /* Khoảng cách dưới tiêu đề */
            }
            .invoice-item {
                border-bottom: 1px solid #dee2e6; /* Đường viền dưới cho từng mục */
                padding: 10px 0; /* Khoảng cách trên và dưới cho từng mục */
            }
            .total {
                font-weight: bold; /* Đậm cho tổng số tiền */
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <div class="invoice-container">
                <div class="invoice-header">
                    <h2>Thank you for your purchase</h2>
                    <ul class="list-unstyled">
                        <c:if test="${not empty user}">
                            <li class="text-black">${user.userName}</li> <!-- Tên người dùng -->
                            </c:if>
                            <c:if test="${not empty currentDate}">
                            <li class="text-black"><fmt:formatDate value="${currentDate}" pattern="MMMM-dd-yyyy"/></li> <!-- Ngày -->
                            </c:if>
                    </ul>
                </div>
                <hr>
                <c:if test="${not empty cart.listItems}">
                    <div class="invoice-items">
                        <c:forEach var="item" items="${cart.listItems}">
                            <div class="invoice-item row">
                                <div class="col-xl-10">
                                    <p>${item.product.productName} - ${item.quantity} x <fmt:formatNumber value="${item.product.unitPrice}" pattern="#,###"/> VND</p>
                                </div>
                                <div class="col-xl-2">
                                    <p class="float-end"><fmt:formatNumber value="${item.product.unitPrice * item.quantity}" pattern="#,###"/> VND</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${empty cart.listItems}">
                    <p class="text-black">Your cart is empty.</p>
                </c:if>
                <hr style="border: 2px solid black;">
                <div class="row text-black">
                    <div class="col-xl-12">
                        <c:if test="${not empty totalAmount}">
                            <p class="float-end total">Total: <fmt:formatNumber value="${totalAmount}" pattern="#,###"/> VND</p>
                        </c:if>
                    </div>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary btn-lg">Back to Home</a>
        </div>
    </body>
</html>
