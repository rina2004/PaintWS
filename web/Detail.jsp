<%-- 
    Document   : Detail
    Created on : Oct 22, 2024, 5:29:05 PM
    Author     : anhbu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="css/style.css" rel="stylesheet" type="text/css"/>   
    </head>
    <body>
        <!--Main Navigation-->
        <header>
            <!-- Jumbotron -->
            <div class="p-3 text-center bg-white border-bottom">
                <div class="container">
                    <div class="row gy-3 align-items-center"> <!-- Added align-items-center for better vertical alignment -->
                        <!-- Left elements -->
                        <div class="col-lg-2 col-sm-4 col-4">
                            <a target="_blank" class="float-start">
                                <img src="https://images.vexels.com/content/136540/preview/paint-shop-logo-c3433e.png" height="100" />
                            </a>
                        </div>
                        <!-- Left elements -->

                        <!-- Center elements -->
                        <div class="order-lg-last col-lg-5 col-sm-8 col-8">
                            <div class="d-flex justify-content-end"> <!-- Changed to justify-content-end for better alignment -->
                                <c:if test="${sessionScope.acc == null}">
                                    <a href="Login.jsp" class="me-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-user-alt"></i> Sign in
                                    </a>
                                </c:if>

                                <c:if test="${sessionScope.acc != null}">
                                    <!-- Hiển thị lời chào và tên người dùng -->
                                    <span class="me-3 align-self-center">
                                        Hello, ${sessionScope.acc.userName}!
                                    </span>
                                    <a href="logout" class="me-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-user-alt"></i> Log out
                                    </a>

                                    <a href="Cart.jsp" class="btn btn-outline-secondary"> 
                                        <i class="fas fa-shopping-cart"></i> My cart
                                    </a>
                                </c:if>
                                <!-- Chỉ hiển thị Manage account cho admin (roleID == 1) -->
                                <c:if test="${sessionScope.acc.roleID == 1}">
                                    <a href="manageracc" class="ms-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-user-cog"></i> Manage Account
                                    </a>
                                    <a href="manager" class="ms-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-user-cog"></i> Manage Product
                                    </a>
                                    <!-- Nút hiển thị biểu đồ bán chạy -->
                                    <a href="Chart" class="ms-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-chart-bar"></i> Best-Selling Chart
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <!-- Center elements -->

                        <!-- Right elements -->
                        <div class="col-lg-5 col-md-12 col-12">
                            <form action="search" method="get"> <!-- Ensure this matches your servlet mapping -->
                                <div class="input-group">
                                    <input type="search" id="form1" name="txt" class="form-control" placeholder="Search products..." aria-label="Search" />
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search"></i> 
                                    </button>
                                </div>
                            </form>
                        </div>
                        <!-- Right elements -->
                    </div>
                </div>
            </div>

           
            <!-- Jumbotron -->
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg navbar-light bg-white">
                <!-- Container wrapper -->
                <div class="container justify-content-center justify-content-md-between">
                    <!-- Toggle button -->
                    <button
                        class="navbar-toggler border py-2 text-dark"
                        type="button"
                        data-mdb-toggle="collapse"
                        data-mdb-target="#navbarLeftAlignExample"
                        aria-controls="navbarLeftAlignExample"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                        >
                        <i class="fas fa-bars"></i>
                    </button>

                    <!-- Collapsible wrapper -->
                    <div class="collapse navbar-collapse" id="navbarLeftAlignExample">
                        <!-- Left links -->
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link text-dark" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-dark" href="#" id="categoriesDropdown" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                    Categories
                                </a>
                                <!-- Drop down menu -->
                                <ul class="dropdown-menu" aria-labelledby="categoriesDropdown">
                                    <!-- Lặp qua danh sách categories và hiển thị -->
                                    <c:forEach var="category" items="${listC}">
                                        <li><a class="dropdown-item category-item" href="category?id=${category.id}">${category.name}</a></li>
                                        </c:forEach>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-dark" >Coming soon</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-dark" >Coming soon</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-dark" >Coming soon</a>
                            </li>

                            <!-- Navbar dropdown -->
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdown" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
                                    Others
                                </a>
                                <!-- Dropdown menu -->
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li>
                                        <a class="dropdown-item" href="#">Action</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="#">Another action</a>
                                    </li>
                                    <li><hr class="dropdown-divider" /></li>
                                    <li>
                                        <a class="dropdown-item" href="#">Something else here</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <!-- Left links -->
                    </div>
                </div>
                <!-- Container wrapper -->
            </nav>
            <!-- Navbar -->

            <!--Main layout-->
            <main class="mt-5 pt-4">
                <div class="container mt-5">
                    <!--Grid row-->
                    <div class="row align-items-center"> <!-- Align items vertically center -->
                        <!--Grid column-->
                        <div class="col-md-6 mb-4">
                            <!-- Display product image dynamically -->
                            <img src="${detail.image}" class="img-fluid rounded shadow-sm" style="max-width: 75%;" alt="${detail.productName}" /> <!-- Reduce image size -->
                        </div>
                        <!--Grid column-->

                        <!--Grid column-->
                        <div class="col-md-6 mb-4">
                            <!--Content-->
                            <div class="p-4">
                                <!-- Display product price -->
                                <span class="h5">
                                    Price: <fmt:formatNumber value="${detail.unitPrice}" pattern="#,###" /> VND
                                </span>

                                <!-- Display product description -->
                                <strong>
                                    <p class="mt-3 mb-1" style="font-size: 24px; color: #007bff; font-weight: bold; text-transform: uppercase; border-bottom: 2px solid #007bff;">
                                        Description
                                    </p>
                                </strong>
                                <p class="text-muted" style="font-size: 18px; background-color: #f9f9f9; padding: 10px; border-radius: 5px;">
                                    <strong style="color: #333;">${detail.description}</strong>
                                </p>


                                <!-- Display additional product information -->
                                <div class="mt-3">
                                    <div class="product-info">
                                        <p><strong>Product Name:</strong> ${detail.productName}</p>
                                        <p><strong>Volume:</strong> ${detail.volume} L</p>
                                        <p><strong>Color:</strong> ${detail.color}</p>
                                        <p><strong>Units in Stock:</strong> ${detail.unitsInStock}</p>
                                        <p><strong>Status:</strong> ${detail.status ? 'Còn hàng' : 'Hết hàng'}</p>
                                    </div>
                                    <div class="supplier-info mt-3" style="padding: 10px; border: 1px solid #007bff; border-radius: 5px; background-color: #f9f9f9;">
                                        <p style="font-size: 20px; font-weight: bold; color: #007bff; text-transform: uppercase; border-bottom: 2px solid #007bff; margin-bottom: 10px;">
                                            Supplier
                                        </p>
                                        <ul style="list-style-type: none; padding: 0;">
                                            <li><strong>Company:</strong> ${detail.supplier.companyName}</li>
                                            <li><strong>Contact person:</strong> ${detail.supplier.contactName}</li>
                                            <li><strong>Country:</strong> ${detail.supplier.country}</li>
                                            <li><strong>Phone number:</strong> ${detail.supplier.phone}</li>
                                        </ul>
                                    </div>
                                </div>


                                <!-- Quantity input and Add to cart button -->
                                <c:if test="${sessionScope.acc != null}">
                                    <form action="buy" method="get" class="d-flex align-items-center mt-3" onsubmit="return validateForm(this)"> <!-- Align items center -->
                                        <!-- Hidden input for Product ID -->
                                        <input type="hidden" name="pid" value="${detail.productID}" />
                                        <!-- Quantity input -->
                                        <div class="form-outline me-2" style="width: 80px;">
                                            <input type="number" name="num" value="1" class="form-control" min="1" max="${detail.unitsInStock}" required />
                                        </div>
                                        <!-- Add to cart button -->
                                        <button class="btn btn-primary" type="submit">
                                            Add to cart
                                            <i class="fas fa-shopping-cart ms-1"></i>
                                        </button>
                                    </form>

                                    <script>
                                        function validateForm(form) {
                                            const quantityInput = form.num;
                                            const quantity = parseInt(quantityInput.value, 10);
                                            const maxStock = parseInt(quantityInput.max, 10);

                                            if (isNaN(quantity) || quantity < 1 || quantity > maxStock) {
                                                alert(`Please enter a valid quantity (1 - ${maxStock})`);
                                                return false; // Prevent form submission
                                            }
                                            return true; // Allow form submission
                                        }
                                    </script>
                                </c:if>

                                </div>
                                <!--Content-->
                            </div>
                            <!--Grid column-->
                        </div>
                        <!--Grid row-->

                        <hr />

                        <!-- Additional Information Section (optional) -->
                        <div class="row d-flex justify-content-center">
                            <div class="col-md-6 text-center">
                                <p class="mt-3 mb-1" style="font-size: 24px; color: #007bff; font-weight: bold; text-transform: uppercase; border-bottom: 2px solid #007bff;">
                                    Explore More Products
                                </p>
                            </div>
                        </div>
                        <!--Grid row-->

                        <!-- Similar products (optional) -->
                        <div class="row">
                            <div class="col-lg-4 col-md-12 mb-4">
                                <img src="http://surl.li/qyqgfo" class="img-fluid rounded shadow-sm" alt="" />
                            </div>
                            <div class="col-lg-4 col-md-6 mb-4">
                                <img src="http://surl.li/xoldhn" class="img-fluid rounded shadow-sm" alt="" />
                            </div>
                            <div class="col-lg-4 col-md-6 mb-4">
                                <img src="http://surl.li/zrtewk" class="img-fluid rounded shadow-sm" alt="" />
                            </div>
                        </div>
                        <!--Grid row-->
                        <p class="text-center mt-4" style="font-size: 20px; font-weight: bold; color: #007bff; background-color: #f0f8ff; padding: 15px; border-radius: 5px; border: 1px solid #007bff;">
                            Sơn không chỉ là màu sắc; nó là cảm xúc, là nghệ thuật, là cách chúng ta biến những không gian bình thường thành những tác phẩm đầy sức sống.
                        </p>

                    </div>
                </main>
                <!--Main layout-->




                <!-- Footer -->
                <footer class="text-center text-lg-start text-muted mt-3" style="background-color: #333; color: white;">
                    <!-- Section: Newsletter -->
                    <section class="py-5" style="background: linear-gradient(135deg, #ff7e5f, #feb47b); color: white;">
                        <div class="container text-center">
                            <h6 class="text-uppercase fw-bold mb-3" style="font-size: 1.5rem;">Join Our Newsletter</h6>
                            <p class="mb-4" style="font-size: 1.1rem;">Stay updated with the latest paint trends and exclusive offers</p>
                            <div class="input-group mb-3 justify-content-center" style="max-width: 500px;">
                                <input type="email" class="form-control border-0 p-3" style="border-radius: 30px 0 0 30px;" placeholder="Enter your email" aria-label="Email" aria-describedby="button-addon2" />
                                <button class="btn p-3" style="background-color: #ff7e5f; color: white; border-radius: 0 30px 30px 0;" type="button" id="button-addon2" data-mdb-ripple-color="light">
                                    Subscribe
                                </button>
                            </div>
                        </div>
                    </section>
                    <!-- Section: Newsletter -->
                </footer>
                <!-- Footer -->

        </body>
    </html>
