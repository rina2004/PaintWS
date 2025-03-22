<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sales Analytics Dashboard</title>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f7fb;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }
            .header {
                background-color: #2a3f54;
                color: white;
                padding: 15px 20px;
                border-radius: 5px;
                margin-bottom: 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .header h1 {
                margin: 0;
            }
            .chart-container {
                background-color: white;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                padding: 20px;
                margin-bottom: 20px;
            }
            .chart-row {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
            }
            .chart-box {
                flex: 0 0 48%;
                margin-bottom: 20px;
            }
            .chart-header {
                border-bottom: 1px solid #eee;
                padding-bottom: 10px;
                margin-bottom: 15px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .chart-title {
                font-size: 18px;
                font-weight: 600;
                color: #2a3f54;
                margin: 0;
            }
            .chart-controls {
                display: flex;
                align-items: center;
            }
            select {
                padding: 5px 10px;
                border-radius: 4px;
                border: 1px solid #ddd;
                margin-left: 10px;
            }
            .stats-row {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .stat-box {
                flex: 0 0 23%;
                background-color: white;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                padding: 15px;
                text-align: center;
            }
            .stat-value {
                font-size: 24px;
                font-weight: bold;
                color: #2a3f54;
                margin: 10px 0;
            }
            .stat-label {
                color: #73879c;
                font-size: 14px;
            }
            .comparison-box {
                background-color: white;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                padding: 20px;
                margin-bottom: 20px;
            }
            .comparison-controls {
                display: flex;
                justify-content: center;
                margin-bottom: 20px;
            }
            .comparison-result {
                display: flex;
                justify-content: space-around;
                align-items: center;
                padding: 15px 0;
            }
            .revenue-box {
                text-align: center;
                flex: 0 0 30%;
            }
            .revenue-value {
                font-size: 24px;
                font-weight: bold;
                margin: 10px 0;
            }
            .revenue-month {
                font-size: 16px;
                color: #73879c;
            }
            .percentage-change {
                font-size: 20px;
                font-weight: bold;
            }
            .positive-change {
                color: #26B99A;
            }
            .negative-change {
                color: #E74C3C;
            }
            @media (max-width: 768px) {
                .chart-box, .stat-box {
                    flex: 0 0 100%;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1>Sales Analytics Dashboard</h1>
                <div class="date">
                    <script>
                        document.write(new Date().toLocaleDateString('vi-VN', {
                        weekday: 'long',
                                year: 'numeric',
                                month: 'long',
                                day: 'numeric'
                        }));
                    </script>
                </div>
            </div>

            <!-- Statistics Summary -->
            <div class="stats-row">
                <div class="stat-box">
                    <div class="stat-label">Tổng sản phẩm</div>
                    <div class="stat-value" id="totalProducts">-</div>
                    <div class="stat-label">Đã bán</div>
                </div>
                <div class="stat-box">
                    <div class="stat-label">Top danh mục</div>
                    <div class="stat-value" id="topCategory">-</div>
                    <div class="stat-label">Bán chạy nhất</div>
                </div>
                <div class="stat-box">
                    <div class="stat-label">Sản phẩm bán chạy</div>
                    <div class="stat-value" id="bestProduct">-</div>
                    <div class="stat-label">Đơn vị</div>
                </div>
                <div class="stat-box">
                    <div class="stat-label">Doanh thu tháng này</div>
                    <div class="stat-value" id="currentMonthRevenue">-</div>
                    <div class="stat-label">VNĐ</div>
                </div>
            </div>

            <!-- Top Selling Products Chart -->
            <div class="chart-container">
                <div class="chart-header">
                    <h2 class="chart-title">Sản phẩm bán chạy nhất</h2>
                    <div class="chart-controls">
                        <label for="topProductsLimit">Hiển thị:</label>
                        <select id="topProductsLimit" onchange="updateTopProductsChart()">
                            <option value="5">5 sản phẩm</option>
                            <option value="10" selected>10 sản phẩm</option>
                            <option value="15">15 sản phẩm</option>
                            <option value="20">20 sản phẩm</option>
                        </select>
                    </div>
                </div>
                <canvas id="bestSellingChart"></canvas>
            </div>

            <div class="chart-row">
                <!-- Category Sales Chart -->
                <div class="chart-box">
                    <div class="chart-header">
                        <h2 class="chart-title">Doanh số theo danh mục</h2>
                    </div>
                    <canvas id="categorySalesChart"></canvas>
                </div>

                <!-- Monthly Sales Chart -->
                <div hidden class="chart-box">
                    <div class="chart-header">
                        <h2 class="chart-title">Doanh thu theo tháng</h2>
                    </div>
                    <canvas id="monthlySalesChart"></canvas>
                </div>
            </div>

            <!-- Monthly Comparison -->
            <div class="comparison-box">
                <div class="chart-header">
                    <h2 class="chart-title">So sánh doanh thu tháng</h2>
                </div>
                <div class="comparison-controls">
                    <label for="month1">Tháng:</label>
                    <select id="month1" onchange="updateMonthlyComparison()">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                    &nbsp;&nbsp;
                    <label for="month2">Tháng:</label>
                    <select id="month2" onchange="updateMonthlyComparison()">
                        <option value="1">1</option>
                        <option value="2" selected>2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                </div>
                <div class="comparison-result">
                    <div class="revenue-box">
                        <div class="revenue-month" id="month1Name">Tháng 1</div>
                        <div class="revenue-value" id="month1Revenue">-</div>
                    </div>
                    <div class="percentage-change" id="percentageChange">-</div>
                    <div class="revenue-box">
                        <div class="revenue-month" id="month2Name">Tháng 2</div>
                        <div class="revenue-value" id="month2Revenue">-</div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            // Lưu trữ biểu đồ để cập nhật
            var bestSellingChart;
            var categorySalesChart;
            var monthlySalesChart;
            // Mảng chứa tên các tháng
            const MONTHS = ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                    "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"];
            // Lưu trữ dữ liệu từ server
            var bestSellersData = {
            productNames: [
            <c:forEach var="product" items="${bestSellers}" varStatus="status">
            "${product.productName}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ],
                    quantities: [
            <c:forEach var="product" items="${bestSellers}" varStatus="status">
                ${product.totalQuantitySold}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                    ]
            };
            var categorySalesData = {
            categoryNames: [
            <c:forEach var="category" items="${categorySales}" varStatus="status">
            "${category.categoryName}"<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ],
                    quantities: [
            <c:forEach var="category" items="${categorySales}" varStatus="status">
                ${category.totalSold}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                    ]
            };
            var monthlySalesData = {
            months: [
            <c:forEach var="monthly" items="${monthlySales}" varStatus="status">
                ${monthly.month}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
            ],
                    revenues: [
            <c:forEach var="monthly" items="${monthlySales}" varStatus="status">
                ${monthly.revenue}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
                    ]
            };
            // Khởi tạo trang
            window.onload = function() {
            initBestSellingChart();
            initCategorySalesChart();
            initMonthlySalesChart();
            updateMonthlyComparison();
            updateStatistics();
            };
            // Cập nhật thống kê tổng quan
            function updateStatistics() {
            if (bestSellersData.productNames.length > 0) {
            document.getElementById('bestProduct').textContent = bestSellersData.quantities[0] || 0;
            document.getElementById('totalProducts').textContent = bestSellersData.quantities.reduce((a, b) => a + b, 0);
            }

            if (categorySalesData.categoryNames.length > 0) {
            let maxIndex = categorySalesData.quantities.indexOf(Math.max(...categorySalesData.quantities));
            document.getElementById('topCategory').textContent = categorySalesData.categoryNames[maxIndex] || '-';
            }

            if (monthlySalesData.months.length > 0) {
            let currentMonth = new Date().getMonth() + 1;
            let revenueIndex = monthlySalesData.months.indexOf(currentMonth);
            if (revenueIndex !== - 1) {
            document.getElementById('currentMonthRevenue').textContent =
                    formatCurrency(monthlySalesData.revenues[revenueIndex]);
            }
            }
            }

            // Khởi tạo biểu đồ sản phẩm bán chạy
            function initBestSellingChart() {
            var ctx = document.getElementById("bestSellingChart").getContext("2d");
            bestSellingChart = new Chart(ctx, {
            type: 'bar',
                    data: {
                    labels: bestSellersData.productNames.slice(0, 10),
                            datasets: [{
                            label: 'Số lượng bán',
                                    data: bestSellersData.quantities.slice(0, 10),
                                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                            }]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                            xAxes: [{
                            ticks: {
                            autoSkip: false,
                                    maxRotation: 45,
                                    callback: function(value) {
                                    return truncateLabel(value, 20);
                                    }
                            }
                            }],
                                    yAxes: [{
                                    ticks: {
                                    beginAtZero: true
                                    }
                                    }]
                            },
                            tooltips: {
                            callbacks: {
                            title: function(tooltipItem, data) {
                            return data.labels[tooltipItem[0].index];
                            }
                            }
                            },
                            legend: {
                            display: false
                            }
                    }
            });
            }

            // Cập nhật biểu đồ sản phẩm bán chạy
            function updateTopProductsChart() {
            var limit = parseInt(document.getElementById('topProductsLimit').value);
            bestSellingChart.data.labels = bestSellersData.productNames.slice(0, limit);
            bestSellingChart.data.datasets[0].data = bestSellersData.quantities.slice(0, limit);
            bestSellingChart.update();
            }

            // Khởi tạo biểu đồ doanh số theo danh mục
            function initCategorySalesChart() {
            var ctx = document.getElementById("categorySalesChart").getContext("2d");
            // Tạo mảng màu ngẫu nhiên
            var colors = generateColors(categorySalesData.categoryNames.length);
            categorySalesChart = new Chart(ctx, {
            type: 'pie',
                    data: {
                    labels: categorySalesData.categoryNames,
                            datasets: [{
                            data: categorySalesData.quantities,
                                    backgroundColor: colors.background,
                                    borderColor: colors.border,
                                    borderWidth: 1
                            }]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            legend: {
                            position: 'right',
                                    labels: {
                                    boxWidth: 15,
                                            padding: 10
                                    }
                            },
                            tooltips: {
                            callbacks: {
                            label: function(tooltipItem, data) {
                            var dataset = data.datasets[tooltipItem.datasetIndex];
                            var total = dataset.data.reduce(function(previousValue, currentValue) {
                            return previousValue + currentValue;
                            });
                            var currentValue = dataset.data[tooltipItem.index];
                            var percentage = Math.round((currentValue / total) * 100);
                            return data.labels[tooltipItem.index] + ': ' + currentValue + ' (' + percentage + '%)';
                            }
                            }
                            }
                    }
            });
            }

            // Khởi tạo biểu đồ doanh thu theo tháng
            function initMonthlySalesChart() {
            var ctx = document.getElementById("monthlySalesChart").getContext("2d");
            // Tạo mảng nhãn tháng từ dữ liệu
            var monthLabels = monthlySalesData.months.map(month => MONTHS[month - 1]);
            monthlySalesChart = new Chart(ctx, {
            type: 'line',
                    data: {
                    labels: monthLabels,
                            datasets: [{
                            label: 'Doanh thu',
                                    data: monthlySalesData.revenues,
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                    borderWidth: 2,
                                    fill: true,
                                    tension: 0.4
                            }]
                    },
                    options: {
                    responsive: true,
                            maintainAspectRatio: false,
                            scales: {
                            yAxes: [{
                            ticks: {
                            beginAtZero: true,
                                    callback: function(value) {
                                    return formatCurrency(value, true);
                                    }
                            }
                            }]
                            },
                            tooltips: {
                            callbacks: {
                            label: function(tooltipItem, data) {
                            return 'Doanh thu: ' + formatCurrency(tooltipItem.yLabel);
                            }
                            }
                            }
                    }
            });
            }

            // Cập nhật so sánh doanh thu tháng
            function updateMonthlyComparison() {
            var month1 = parseInt(document.getElementById('month1').value);
            var month2 = parseInt(document.getElementById('month2').value);
            // Giả lập dữ liệu so sánh từ server - thực tế sẽ gọi AJAX
            var comparisonData = {
            month1: month1,
                    month2: month2,
                    revenue1: getMonthRevenue(month1),
                    revenue2: getMonthRevenue(month2),
                    percentageChange: 0
            };
            if (comparisonData.revenue1 > 0) {
            comparisonData.percentageChange = ((comparisonData.revenue2 - comparisonData.revenue1) / comparisonData.revenue1) * 100;
            }

            // Cập nhật giao diện
            document.getElementById('month1Name').textContent = MONTHS[month1 - 1];
            document.getElementById('month2Name').textContent = MONTHS[month2 - 1];
            document.getElementById('month1Revenue').textContent = formatCurrency(comparisonData.revenue1);
            document.getElementById('month2Revenue').textContent = formatCurrency(comparisonData.revenue2);
            var percentageElement = document.getElementById('percentageChange');
            var percentageValue = comparisonData.percentageChange.toFixed(2) + '%';
            if (comparisonData.percentageChange > 0) {
            percentageElement.innerHTML = '<i class="arrow-up"></i> +' + percentageValue;
            percentageElement.className = 'percentage-change positive-change';
            } else if (comparisonData.percentageChange < 0) {
            percentageElement.innerHTML = '<i class="arrow-down"></i> ' + percentageValue;
            percentageElement.className = 'percentage-change negative-change';
            } else {
            percentageElement.textContent = '0%';
            percentageElement.className = 'percentage-change';
            }
            }

            // Hàm lấy doanh thu của tháng từ dữ liệu có sẵn
            function getMonthRevenue(month) {
            var index = monthlySalesData.months.indexOf(month);
            if (index !== - 1) {
            return monthlySalesData.revenues[index];
            }
            // Trả về giá trị ngẫu nhiên nếu không có dữ liệu
            return Math.round(Math.random() * 1000000);
            }

            // Tạo mảng màu ngẫu nhiên
            function generateColors(count) {
            var baseColors = [
            [54, 162, 235], // Xanh dương
            [255, 99, 132], // Đỏ nhạt
            [255, 206, 86], // Vàng
            [75, 192, 192], // Xanh lá
            [153, 102, 255], // Tím
            [255, 159, 64], // Cam
            [199, 199, 199], // Xám
            [83, 123, 196], // Xanh dương đậm
            [255, 133, 27], // Cam đậm
            [46, 204, 113]     // Xanh lá đậm
            ];
            var background = [];
            var border = [];
            for (var i = 0; i < count; i++) {
            var color = baseColors[i % baseColors.length];
            background.push('rgba(' + color[0] + ',' + color[1] + ',' + color[2] + ',0.6)');
            border.push('rgba(' + color[0] + ',' + color[1] + ',' + color[2] + ',1)');
            }

            return {
            background: background,
                    border: border
            };
            }

            // Hàm rút gọn nhãn quá dài
            function truncateLabel(label, maxLength) {
            if (label.length > maxLength) {
            return label.substring(0, maxLength) + '...';
            }
            return label;
            }

            // Định dạng tiền tệ
            function formatCurrency(value, compact = false) {
            if (compact && value >= 1000000) {
            return (value / 1000000).toFixed(1) + 'M';
            } else if (compact && value >= 1000) {
            return (value / 1000).toFixed(1) + 'K';
            }
            return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
                    currency: 'VND',
                    maximumFractionDigits: 0
            }).format(value);
            }
            function loadTopProducts(limit) {
            fetch('Chart?action=topProducts&limit=' + limit)
                    .then(response => response.json())
                    .then(data => {
                    // Update the chart with new data
                    bestSellingChart.data.labels = data.map(product => product.productName);
                    bestSellingChart.data.datasets[0].data = data.map(product => product.totalQuantitySold);
                    bestSellingChart.update();
                    })
                    .catch(error => console.error('Error loading top products:', error));
            }
        </script>
    </body>
</html>