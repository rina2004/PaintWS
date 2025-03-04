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
 * @author lytu
 */
public class ChartDAO extends DBContext{
    //Retrieves the best selling products ordered by quantity sold
    public List<BestSellingProduct> getBestSellingProducts() {
        List<BestSellingProduct> productSales = new ArrayList<>();
        String sql = """
           SELECT 
                p.ProductName AS ProductTitle,
                p.QuantitySold AS TotalQuantitySold
            FROM Paints p
            ORDER BY p.QuantitySold DESC;
        """;

        try (
            PreparedStatement st = connection.prepareStatement(sql); 
            ResultSet rs = st.executeQuery()) {
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
    
    //Retrieves the top N best selling products
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
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, limit);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String productName = rs.getString("ProductTitle");
                    int totalQuantitySold = rs.getInt("TotalQuantitySold");
                    productSales.add(new BestSellingProduct(productName, totalQuantitySold));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return productSales;
    }
    
    //Retrieves sales data grouped by categories
    public List<CategorySales> getSalesByCategory() {
        List<CategorySales> categorySales = new ArrayList<>();
        String sql = """
            SELECT 
                c.CategoryName,
                COALESCE(SUM(p.QuantitySold), 0) AS TotalSold
            FROM Categories c
            LEFT JOIN Paints p ON c.CategoryID = p.CategoryID
            GROUP BY c.CategoryName
            ORDER BY TotalSold DESC;
        """;
        try (
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                String categoryName = rs.getString("CategoryName");
                int totalSold = rs.getInt("TotalSold");
                categorySales.add(new CategorySales(categoryName, totalSold));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return categorySales;
    }
    
//    //Retrieves monthly sales data for the current year
//    public List<MonthlySales> getMonthlySales() {
//        List<MonthlySales> monthlySales = new ArrayList<>();
//        String sql = """
//            SELECT 
//                MONTH(o.Date) AS Month,
//                SUM(od.Quantity * od.UnitPrice * (1 - COALESCE(od.Discount, 0))) AS Revenue
//            FROM Orders o
//            JOIN OrderDetails od ON o.OrderID = od.OrderID
//            WHERE YEAR(o.Date) = YEAR(GETDATE())
//            GROUP BY MONTH(o.Date)
//            ORDER BY Month;
//        """;
//        try (
//            PreparedStatement st = connection.prepareStatement(sql);
//            ResultSet rs = st.executeQuery()) {
//            while (rs.next()) {
//                int month = rs.getInt("Month");
//                double revenue = rs.getDouble("Revenue");
//                monthlySales.add(new MonthlySales(month, revenue));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return monthlySales;
//    }
    
    //Calculates revenue comparison between two months
    public Map<String, Object> compareMonthlyRevenue(int month1, int month2) {
        Map<String, Object> result = new HashMap<>();
        
        if (month1 < 1 || month1 > 12 || month2 < 1 || month2 > 12) {
            throw new IllegalArgumentException("Month values must be between 1 and 12");
        }
        String sql = """
            SELECT 
                MONTH(o.Date) AS Month,
                SUM(od.Quantity * od.UnitPrice * (1 - COALESCE(od.Discount, 0))) AS Revenue
            FROM Orders o
            JOIN OrderDetails od ON o.OrderID = od.OrderID
            WHERE MONTH(o.Date) IN (?, ?) AND YEAR(o.Date) = YEAR(GETDATE())
            GROUP BY MONTH(o.Date);
        """;
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, month1);
            st.setInt(2, month2);
            
            double revenue1 = 0;
            double revenue2 = 0;
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("Month");
                    double revenue = rs.getDouble("Revenue");
                    
                    if (month == month1) {
                        revenue1 = revenue;
                    } else if (month == month2) {
                        revenue2 = revenue;
                    }
                }
            }
            double percentageChange = 0;
            if (revenue1 > 0) {
                percentageChange = ((revenue2 - revenue1) / revenue1) * 100;
            }
            result.put("month1", month1);
            result.put("month2", month2);
            result.put("revenue1", revenue1);
            result.put("revenue2", revenue2);
            result.put("percentageChange", percentageChange);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
}
