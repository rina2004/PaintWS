<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Paint Shop</title>
        <!-- Consolidated Bootstrap and Font Awesome -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="css/style.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>
        <header>
            <div class="p-3 text-center bg-white border-bottom">
                <div class="container">
                    <div class="row gy-3 align-items-center">
                        <!-- Logo -->
                        <div class="col-lg-2 col-sm-4 col-4">
                            <img src="https://images.vexels.com/content/136540/preview/paint-shop-logo-c3433e.png" height="100" alt="Logo" />
                        </div>

                        <!-- User Actions -->
                        <div class="order-lg-last col-lg-5 col-sm-8 col-8">
                            <div class="d-flex justify-content-end">
                                <c:choose>
                                    <c:when test="${sessionScope.acc == null}">
                                        <a href="Login.jsp" class="me-3 btn btn-outline-secondary"> 
                                            <i class="fas fa-user-alt"></i> Sign in
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="me-3 align-self-center">Hello, ${sessionScope.acc.userName}!</span>
                                        <a href="logout" class="me-3 btn btn-outline-secondary"> 
                                            <i class="fas fa-user-alt"></i> Log out
                                        </a>
                                        <a href="Cart.jsp" class="me-3 btn btn-outline-secondary"> 
                                            <i class="fas fa-shopping-cart"></i> My cart
                                        </a>
                                        
                                        <c:if test="${sessionScope.acc.roleID == 1}">
                                            <div class="dropdown">
                                                <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="adminDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="fas fa-user-cog"></i> Admin
                                                </button>
                                                <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                                                    <li><a class="dropdown-item" href="manageracc">Manage Account</a></li>
                                                    <li><a class="dropdown-item" href="manager">Manage Product</a></li>
                                                    <li><a class="dropdown-item" href="Chart">Best-Selling Chart</a></li>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- Search -->
                        <div class="col-lg-5 col-md-12 col-12">
                            <form action="search" method="get">
                                <div class="input-group">
                                    <input type="search" name="txt" class="form-control" placeholder="Search products..." />
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search"></i> 
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Navigation -->
            <nav class="navbar navbar-expand-lg navbar-light bg-white">
                <div class="container">
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                        <i class="fas fa-bars"></i>
                    </button>
                    <div class="collapse navbar-collapse" id="mainNavbar">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link text-dark" href="${pageContext.request.contextPath}/home">Home</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-dark" href="#" data-bs-toggle="dropdown">
                                    Categories
                                </a>
                                <ul class="dropdown-menu">
                                    <c:forEach var="category" items="${listC}">
                                        <li><a class="dropdown-item" href="category?id=${category.id}">${category.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-dark" href="#" data-bs-toggle="dropdown">
                                    Others
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="#">Action</a></li>
                                    <li><a class="dropdown-item" href="#">Another action</a></li>
                                    <li><hr class="dropdown-divider" /></li>
                                    <li><a class="dropdown-item" href="#">Something else</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- Featured Products Carousel -->
            <div class="jumbotron-bg-image text-white py-5" style="background-image: url('https://png.pngtree.com/thumb_back/fw800/background/20240806/pngtree-young-family-doing-renovation-at-home-painting-walls-image_15999128.jpg'); background-size: cover;">
                <div class="container text-center py-5" style="background-color: rgba(0, 0, 0, 0.6); border-radius: 10px;">
                    <h1>Featured Products</h1>
                    <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner">
                            <c:forEach var="featuredProduct" items="${[p, newProduct, cheap]}" varStatus="status">
                                <div class="carousel-item ${status.first ? 'active' : ''}">
                                    <h2>${status.first ? 'Best Selling' : status.index == 1 ? 'Newest' : 'Cheapest'} Product: ${featuredProduct.productName}</h2>
                                    <p>
                                        Volume: ${featuredProduct.volume} L <br />
                                        Price: <fmt:formatNumber value="${featuredProduct.unitPrice}" pattern="#,###" /> VND <br />
                                        Color: ${featuredProduct.color}
                                    </p>
                                    <img src="${featuredProduct.image}" alt="${featuredProduct.productName}" class="img-fluid rounded mb-3" style="max-width: 300px; max-height: 300px;">
                                    <div class="d-flex justify-content-center mt-3">
                                        <a href="detail?pid=${featuredProduct.productID}" class="btn btn-outline-light">Learn more</a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
            </div>
        </header>

        <!-- Products Section -->
        <section class="container my-5">
            <header class="mb-4 text-center">
                <h3 class="new-products-title">New Products</h3>
            </header>
            <div class="row">
                <c:forEach var="product" items="${listP}">
                    <div class="col-lg-3 col-md-6 col-sm-6 d-flex">
                        <div class="card w-100 my-2 shadow-2-strong text-center">
                            <img src="${product.image}" class="card-img-top" style="aspect-ratio: 1 / 1" alt="Product Image" />
                            <div class="card-body d-flex flex-column align-items-center">
                                <h5 class="card-title">
                                    <a href="detail?pid=${product.productID}" class="text-dark">${product.productName}</a>
                                </h5>
                                <p class="card-text price-text">
                                    <fmt:formatNumber value="${product.unitPrice}" pattern="#,###" /> VND
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Footer -->
        <footer class="text-center text-muted mt-3" style="background-color: #333; color: white;">
            <section class="py-5" style="background: linear-gradient(135deg, #ff7e5f, #feb47b); color: white;">
                <div class="container text-center">
                    <h6 class="text-uppercase fw-bold mb-3">Join Our Newsletter</h6>
                    <p class="mb-4">Stay updated with the latest paint trends and exclusive offers</p>
                    <div class="input-group mb-3 justify-content-center" style="max-width: 500px;">
                        <input type="email" class="form-control border-0 p-3" style="border-radius: 30px 0 0 30px;" placeholder="Enter your email" />
                        <button class="btn p-3" style="background-color: #ff7e5f; color: white; border-radius: 0 30px 30px 0;" type="button">
                            Subscribe
                        </button>
                    </div>
                </div>
            </section>
        </footer>
    </body>
</html>