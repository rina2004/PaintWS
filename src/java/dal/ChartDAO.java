/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.*;
import java.util.*;
import model.*;

/**
 *
 * @author A A
 */
public class ChartDAO extends DBContext {

    // Retrieves the best selling products ordered by quantity sold
    public List<BestSellingProduct> getBestSellingProducts() {
        List<BestSellingProduct> productSales = new ArrayList<>();
        String sql = """
            SELECT 
            p.ProductName AS ProductTitle,
            p.QuantitySold AS TotalQuantitySold
            FROM Paints p
            ORDER BY p.QuantitySold DESC;
            """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("ProductTitle");
                int totalQuantitySold = rs.getInt("TotalQuantitySold");
                productSales.add(new BestSellingProduct(productName, totalQuantitySold));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productSales;
    }

    // Retrieves the top N best selling products
    public List<BestSellingProduct> getTopSellingProducts(int limit) {
        List<BestSellingProduct> productSales = new ArrayList<>();
        String sql = """
            SELECT 
            p.ProductName AS ProductTitle,
            p.QuantitySold AS TotalQuantitySold
            FROM Paints p
            ORDER BY p.QuantitySold DESC
            OFFSET 0 ROWS
            FETCH NEXT ? ROWS ONLY;
            """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, limit);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("ProductTitle");
                int totalQuantitySold = rs.getInt("TotalQuantitySold");
                productSales.add(new BestSellingProduct(productName, totalQuantitySold));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productSales;
    }

    private Map<Integer, Integer> categorySalesMap = new HashMap<>();

    public int getTotalSoldForCategory(int categoryId) {
        return categorySalesMap.getOrDefault(categoryId, 0);
    }

    // Retrieves sales data grouped by categories
    public List<Category> getSalesByCategory() {
        List<Category> categorySales = new ArrayList<>();
        String sql = """
        SELECT 
        c.CategoryID,
        c.CategoryName,
        c.Description,
        COALESCE(SUM(p.QuantitySold), 0) AS TotalSold
        FROM Categories c
        LEFT JOIN Paints p ON c.CategoryID = p.CategoryID
        GROUP BY c.CategoryID, c.CategoryName, c.Description
        ORDER BY TotalSold DESC;
        """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("CategoryID"));
                category.setName(rs.getString("CategoryName"));
                category.setDescribe(rs.getString("Description"));

                int totalSold = rs.getInt("TotalSold");
                categorySalesMap.put(category.getId(), totalSold);
                categorySales.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return categorySales;
    }

    // Update existing product
    public int update(Product paint) {
        // Validation conditions
        if (paint == null 
            || paint.getProductName() == null
            || paint.getProductName().trim().isEmpty()
            || paint.getProductName().length() > 100  
            || paint.getVolume() < 0
            || paint.getUnitPrice() < 0
            || paint.getUnitsInStock() < 0
            || paint.getQuantitySold() < 0
            || (paint.getColor() != null && paint.getColor().length() > 50)) { 
        return 0;
    }
        String sql = """
            UPDATE Paints 
            SET ProductName = ?, Volume = ?, 
            Color = ?, UnitPrice = ?, UnitsInStock = ?, QuantitySold = ?,
            Discontinued = ?, Image = ?, Description = ?, Status = ?
            WHERE ProductID = ?
            """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, paint.getProductName());
            st.setDouble(2, paint.getVolume());
            st.setString(3, paint.getColor());
            st.setDouble(4, paint.getUnitPrice());
            st.setInt(5, paint.getUnitsInStock());
            st.setInt(6, paint.getQuantitySold());
            st.setBoolean(7, paint.isDiscontinued());
            st.setString(8, paint.getImage());
            st.setString(9, paint.getDescription());
            st.setBoolean(10, paint.isStatus());
            st.setInt(11, paint.getProductID());
            int affectedRows = st.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
    }

    //Calculates revenue comparison between two months
    public Map<String, Object> compareMonthlyRevenue(int month1, int year1, int month2, int year2) {
        // validate month
        if (month1 < 1 || month1 > 12 || month2 < 1 || month2 > 12) {
            throw new IllegalArgumentException("Month values must be between 1 and 12");
        }

        // validate year 
        if (year1 < 2000 || year2 < 2000) {
            throw new IllegalArgumentException("Year values must be 2000 or greater");
        }

        int currentYear = java.time.Year.now().getValue();
        if (year1 > currentYear || year2 > currentYear) {
            throw new IllegalArgumentException("Cannot compare revenue for future years");
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("period1", year1 + "-" + month1);
        result.put("period2", year2 + "-" + month2);
        result.put("revenue1", 0.0);
        result.put("revenue2", 0.0);
        result.put("differenceRatio", 0.0);

        String sql = """
            SELECT 
            MONTH(o.Date) AS Month,
            YEAR(o.Date) AS Year,
            SUM(od.Quantity * od.UnitPrice * (1 - COALESCE(od.Discount, 0))) AS Revenue
            FROM Orders o
            JOIN OrderDetails od ON o.OrderID = od.OrderID
            WHERE (MONTH(o.Date) = ? AND YEAR(o.Date) = ?) OR (MONTH(o.Date) = ? AND YEAR(o.Date) = ?)
            GROUP BY MONTH(o.Date), YEAR(o.Date)
            """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, month1);
            st.setInt(2, year1);
            st.setInt(3, month2);
            st.setInt(4, year2);

            double revenue1 = 0;
            double revenue2 = 0;
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("Month");
                    int year = rs.getInt("Year");
                    double revenue = rs.getDouble("Revenue");
                    if (month == month1 && year == year1) {
                        revenue1 = revenue;
                        if (month1 == month2 && year1 == year2) {
                            revenue2 = revenue;
                        }
                    } else if (month == month2 && year == year2) {
                        revenue2 = revenue;
                    }
                }
            }
            result.put("revenue1", revenue1);
            result.put("revenue2", revenue2);

            double differenceRatio = 0;
            if (revenue1 > 0) {
                differenceRatio = ((revenue2 - revenue1) / revenue1) * 100;
            }
            result.put("differenceRatio", differenceRatio);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    //Retrieves monthly sales data for the current year
    public List<MonthlySales> getMonthlySales() {
        List<MonthlySales> monthlySales = new ArrayList<>();
        String sql = """
            SELECT 
                MONTH(o.Date) AS Month,
                SUM(od.Quantity * od.UnitPrice * (1 - COALESCE(od.Discount, 0))) AS Revenue
            FROM Orders o
            JOIN OrderDetails od ON o.OrderID = od.OrderID
            WHERE YEAR(o.Date) = YEAR(GETDATE())
            GROUP BY MONTH(o.Date)
            ORDER BY Month;
        """;
        try (
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("Month");
                double revenue = rs.getDouble("Revenue");
                monthlySales.add(new MonthlySales(month, revenue));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return monthlySales;
    }
}
