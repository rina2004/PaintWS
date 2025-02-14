/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BestSellingProduct;

/**
 *
 * @author anhbu
 */
public class ChartDAO extends DBContext{
    public List<BestSellingProduct> getBestSellingProducts() {
        List<BestSellingProduct> productSales = new ArrayList<>();
        String sql = """
           SELECT 
                p.ProductName AS ProductTitle,
                p.QuantitySold AS TotalQuantitySold
            FROM Paints p
            ORDER BY p.QuantitySold DESC;
        """;

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
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

    public static void main(String[] args) {
        ChartDAO dao = new ChartDAO();
        List<BestSellingProduct> list = dao.getBestSellingProducts();
        for (BestSellingProduct bestSellingProduct : list) {
            System.out.println(bestSellingProduct.toString());
        }
    }
}
