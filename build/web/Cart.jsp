<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <section class="h-100">
            <div class="container h-100 py-5">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-10">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h3 class="fw-normal mb-0">Shopping Cart</h3>
                        </div>

                        <c:if test="${cart != null && empty cart.listItems}">
                            <p class="text-center">Your cart is currently empty.</p>
                        </c:if>

                        <!-- Hiển thị thông báo lỗi nếu có -->
                        <c:if test="${not empty sessionScope.errorMessage}">
                            <div style="color: red;">
                                <strong>${sessionScope.errorMessage}</strong>
                            </div>
                            <!-- Xóa thông báo sau khi hiển thị -->
                            <c:remove var="errorMessage" scope="session"/>
                        </c:if>

                        <!-- Lặp qua từng sản phẩm trong giỏ hàng -->
                        <c:forEach var="item" items="${cart.listItems}">
                            <div class="card rounded-3 mb-4">
                                <div class="card-body p-4">
                                    <div class="row d-flex justify-content-between align-items-center">
                                        <div class="col-md-2 col-lg-2 col-xl-2">
                                            <img src="${item.product.image}" class="img-fluid rounded-3" alt="${item.product.productName}">
                                        </div>
                                        <div class="col-md-3 col-lg-3 col-xl-3">
                                            <p class="lead fw-normal mb-2">${item.product.productName}</p>
                                            <p>
                                                <span class="text-muted">Volume: </span>${item.product.volume}L 
                                                <span class="text-muted">Color: </span>${item.product.color}
                                            </p>
                                            <p><span class="text-muted">In Stock: </span>${item.product.unitsInStock}</p> <!-- Hiển thị số lượng tồn kho -->
                                        </div>
                                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex align-items-center">
                                            <!-- Đường dẫn cập nhật số lượng sản phẩm -->
                                            <a href="cart?pid=${item.product.productID}&num=-1" class="btn btn-link px-2">
                                                <i class="fas fa-minus"></i>
                                            </a>
                                            <span class="mx-2">${item.quantity}</span> <!-- Hiển thị số lượng sản phẩm -->
                                            <a href="cart?pid=${item.product.productID}&num=1" class="btn btn-link px-2" 
                                               onclick="return checkStock(${item.product.unitsInStock}, ${item.quantity + 1});">
                                                <i class="fas fa-plus"></i>
                                            </a>
                                        </div>
                                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                                            <p class="card-text price-text">
                                                <fmt:formatNumber value="${item.price}" pattern="#,###" /> VND
                                            </p>
                                        </div>
                                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                                            <!-- Đường dẫn xóa sản phẩm khỏi giỏ hàng -->
                                            <a href="cart?pid=${item.product.productID}&num=0" class="text-danger"><i class="fas fa-trash fa-lg"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <div class="card">
                            <div class="card-body d-flex justify-content-between align-items-center">
                                <form action="check" method="post">
                                    <button type="submit" class="btn btn-warning">
                                        Proceed to Pay
                                        <i class="fas fa-credit-card ms-1"></i>
                                    </button>
                                </form>
                                <!-- Nút Back to Home -->
                                <a href="home" class="btn btn-secondary btn-lg">Back to Home</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </section>

        <script>
            function checkStock(max, current) {
                if (current > max) {
                    alert("Bạn không thể tăng số lượng vượt quá số lượng có sẵn!");
                    return false; // Ngăn không cho thực hiện hành động
                }
                return true; // Cho phép thực hiện hành động
            }
        </script>
    </body>
</html>
