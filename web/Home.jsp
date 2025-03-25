<%-- 
    Document   : Home.jsp
    Created on : Oct 19, 2024, 9:21:59 PM
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
        <!-- Thêm CSS của Bootstrap -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

        <!-- Thêm jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

        <!-- Thêm Popper.js (cần thiết cho Bootstrap tooltips và popovers) -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>

        <!-- Thêm JavaScript của Bootstrap -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
                            <div class="d-flex justify-content-end">
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

                                    <a href="Cart.jsp" class="me-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-shopping-cart"></i> My cart
                                    </a>
                                </c:if>

                                <!-- Chỉ hiển thị cho admin (roleID == 1) -->
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
                                    <a href="loadCategory" class="ms-3 btn btn-outline-secondary"> 
                                        <i class="fas fa-user-cog"></i> Manage Category
                                    </a>
                                </c:if>
                            </div>
                        </div>

                        <!-- Center elements -->

                        <!-- Right elements -->
                        <div class="col-lg-5 col-md-12 col-12">
                            <form action="search" method="get"> <!-- Ensure this matches your servlet mapping -->
                                <div class="input-group me-auto"> <!-- Thêm lớp me-auto để đẩy thanh tìm kiếm sang trái -->
                                    <input type="search" id="form1" name="txt" class="form-control" placeholder="Search products..." aria-label="Search" />
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search"></i> 
                                    </button>
                                </div>
                            </form>
                        </div>
                        <!-- Right elements -->                    </div>
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

            <!-- Jumbotron với hình ảnh nền -->
            <div class="jumbotron-bg-image text-white py-5" style="background-image: url('https://png.pngtree.com/thumb_back/fw800/background/20240806/pngtree-young-family-doing-renovation-at-home-painting-walls-image_15999128.jpg'); background-size: cover; background-position: center;">
                <div class="container text-center py-5" style="background-color: rgba(0, 0, 0, 0.6); border-radius: 10px;">
                    <h1>Featured Products</h1>

                    <!-- Carousel -->
                    <div id="productCarousel" class="carousel slide" data-ride="carousel">
                        <div class="carousel-inner">
                            <!-- Best Selling Product -->
                            <div class="carousel-item active">
                                <h2>Best Selling Product: ${p.productName}</h2>
                                <p>
                                    Volume: ${p.volume} L <br />
                                    Price: <fmt:formatNumber value="${p.unitPrice}" pattern="#,###" /> VND <br />
                                    Color: ${p.color}
                                </p>
                                <img src="${p.image}" alt="${p.productName}" style="max-width: 300px; max-height: 300px;" class="img-fluid rounded mb-3">
                                <!-- Nút Learn more -->
                                <div class="d-flex justify-content-center mt-3">
                                    <a href="detail?pid=${p.productID}" class="btn btn-outline-light">Learn more</a>
                                </div>
                            </div>

                            <!-- Newest Product -->
                            <div class="carousel-item">
                                <h2>Newest Product: ${newProduct.productName}</h2>
                                <p>
                                    Volume: ${newProduct.volume} L <br />
                                    Price: <fmt:formatNumber value="${newProduct.unitPrice}" pattern="#,###" /> VND <br />
                                    Color: ${newProduct.color}
                                </p>
                                <img src="${newProduct.image}" alt="${newProduct.productName}" style="max-width: 300px; max-height: 300px;" class="img-fluid rounded mb-3">
                                <!-- Nút Learn more -->
                                <div class="d-flex justify-content-center mt-3">
                                    <a href="detail?pid=${newProduct.productID}" class="btn btn-outline-light">Learn more</a>
                                </div>
                            </div>

                            <!-- Cheapest Product -->
                            <div class="carousel-item">
                                <h2>Cheapest Product: ${cheap.productName}</h2>
                                <p>
                                    Volume: ${cheap.volume} L <br />
                                    Price: <fmt:formatNumber value="${cheap.unitPrice}" pattern="#,###" /> VND <br />
                                    Color: ${cheap.color}
                                </p>
                                <img src="${cheap.image}" alt="${cheap.productName}" style="max-width: 300px; max-height: 300px;" class="img-fluid rounded mb-3">
                                <!-- Nút Learn more -->
                                <div class="d-flex justify-content-center mt-3">
                                    <a href="detail?pid=${cheap.productID}" class="btn btn-outline-light">Learn more</a>
                                </div>
                            </div>
                        </div>

                        <!-- Các nút điều khiển carousel -->
                        <a class="carousel-control-prev" href="#productCarousel" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#productCarousel" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
            <!-- Jumbotron -->

        </header>


        <!-- Products -->
        <section>
            <div class="container my-5">
                <header class="mb-4 text-center">
                    <h3 class="new-products-title">New Products</h3>
                </header>

                <div class="row">
                    <!-- Start iterating through the product list -->
                    <c:forEach var="product" items="${listP}">
                        <div class="col-lg-3 col-md-6 col-sm-6 d-flex">
                            <div class="card w-100 my-2 shadow-2-strong text-center">
                                <!-- Display product image -->
                                <img src="${product.image}" class="card-img-top" style="aspect-ratio: 1 / 1" alt="Product Image" />
                                <div class="card-body d-flex flex-column align-items-center">
                                    <!-- Display product name -->
                                    <h5 class="card-title">
                                        <a href="detail?pid=${product.productID}" class="text-dark">${product.productName}</a>
                                    </h5>
                                    <!-- Display product unit price with formatted number -->
                                    <p class="card-text price-text">
                                        <fmt:formatNumber value="${product.unitPrice}" pattern="#,###" /> VND
                                    </p>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- End of loops -->
                </div>
            </div>
        </section>
        <!-- Products -->


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
