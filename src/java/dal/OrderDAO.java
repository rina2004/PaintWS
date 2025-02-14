/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import model.Cart;
import model.Item;
import model.User;

/**
 *
 * @author anhbu
 */
public class OrderDAO extends DBContext {

    public void addOrder(User cus, Cart cart) {
        ProductDAO pd = new ProductDAO();
        LocalDate curDate = java.time.LocalDate.now();
        String date = curDate.toString();

        try {
            // Thêm đơn hàng vào bảng Orders
            String sql1 = "INSERT INTO [dbo].[Orders] ([Date], [UserID], [TotalMoney], [status]) VALUES (?,?,?,?)";
            PreparedStatement st1 = connection.prepareStatement(sql1);
            st1.setString(1, date);
            st1.setInt(2, cus.getUserID());
            st1.setDouble(3, cart.getTotalPrice());
            st1.setInt(4, 0);
            st1.executeUpdate();

            // Lấy OrderID của đơn hàng mới tạo
            String sql2 = "SELECT TOP 1 [OrderID] FROM [dbo].[Orders] ORDER BY [OrderID] DESC";
            PreparedStatement st2 = connection.prepareStatement(sql2);
            ResultSet rs = st2.executeQuery();

            if (rs.next()) {
                int oID = rs.getInt(1);

                // ✅ Tạo PreparedStatement 1 lần duy nhất
                String sql3 = "INSERT INTO [dbo].[OrderDetails] ([OrderID], [ProductID], [Quantity], [UnitPrice]) VALUES (?,?,?,?)";
                PreparedStatement st3 = connection.prepareStatement(sql3);

                for (Item item : cart.getListItems()) {
                    st3.setInt(1, oID);
                    st3.setInt(2, item.getProduct().getProductID());
                    st3.setDouble(3, item.getQuantity());
                    st3.setDouble(4, item.getProduct().getUnitPrice());
                    st3.addBatch();  

                    // Cập nhật số lượng sản phẩm
                    pd.updateValueProduct(item.getProduct(), item.getQuantity());
                }
                st3.executeBatch(); 
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
