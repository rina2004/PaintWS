<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống kê sản phẩm bán chạy</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
</head>
<body>
    <canvas id="bestSellingChart"></canvas>
    
    <script type="text/javascript">
        // Lấy ngữ cảnh vẽ biểu đồ
        var ctx = document.getElementById("bestSellingChart").getContext("2d");

        // Mảng chứa tên sản phẩm
        var productNames = [
            <c:forEach var="product" items="${bestSellers}" varStatus="status">
                "${product.productName}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        // Mảng chứa số lượng bán của từng sản phẩm
        var quantities = [
            <c:forEach var="product" items="${bestSellers}" varStatus="status">
                ${product.totalQuantitySold}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
        ];

        // Khởi tạo biểu đồ với dữ liệu sản phẩm bán chạy
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: productNames,
                datasets: [{
                    label: 'Số lượng bán',
                    data: quantities,
                    backgroundColor: 'rgba(0, 156, 255, 0.5)',
                    borderColor: 'rgba(0, 156, 255, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    xAxes: [{
                        ticks: {
                            autoSkip: false,      // Hiển thị tất cả các nhãn
                            maxRotation: 0,       // Đặt văn bản nằm ngang
                            minRotation: 0,
                            callback: function(value) {
                                // Tách nhãn dài thành nhiều dòng
                                var maxLineLength = 20;
                                if (value.length > maxLineLength) {
                                    var words = value.split(' ');
                                    var lines = [];
                                    var currentLine = words[0];
                                    for (var i = 1; i < words.length; i++) {
                                        if (currentLine.length + words[i].length + 1 <= maxLineLength) {
                                            currentLine += ' ' + words[i];
                                        } else {
                                            lines.push(currentLine);
                                            currentLine = words[i];
                                        }
                                    }
                                    lines.push(currentLine);
                                    return lines;
                                } else {
                                    return value;
                                }
                            }
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                },
                layout: {
                    padding: {
                        left: 10,
                        right: 10,
                        top: 10,
                        bottom: 40   // Tạo thêm khoảng trống bên dưới nếu cần
                    }
                },
                legend: {
                    display: false   // Tắt hiển thị phần chú thích
                }
            }
        });
    </script>
    
</body>
</html>
