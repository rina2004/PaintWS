package dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import context.DBContext;
import dal.CategoryDAO;
import dal.SupplierDAO;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import model.Supplier;

/**
 *
 * @author anhbu
 */
public class ProductDAO extends DBContext {
    
    private final CategoryDAO cd = new CategoryDAO(); 
    private final SupplierDAO sd = new SupplierDAO(); 
    private static final String CATEGORY_ID = "CategoryID";
    private static final String SUPPLIER_ID = "SupplierID";
   
    
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Paints]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();

        // Kiểm tra một sản phẩm với ID cụ thể
        Product product = dao.getProductByID("1"); // Thay "1" bằng ID sản phẩm bạn muốn kiểm tra

        if (product != null) {
            System.out.println("Sản phẩm tìm thấy: " + product);
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID đã cho.");
        }
    }

    public Product getBestSellingProduct() {
        String sql = "SELECT TOP 1 * FROM [dbo].[Paints] ORDER BY QuantitySold DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Product getCheapestProduct() {
        String sql = "SELECT TOP 1 * FROM [dbo].[Paints] ORDER BY UnitPrice ASC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Product getNewestProduct() {
        String sql = "SELECT TOP 1 * FROM [dbo].[Paints] ORDER BY ProductID DESC"; // Giả sử bạn có trường ReleaseDate trong bảng
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Product> getProductByCategoryID(String cid) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Paints] WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, cid); // Gán giá trị 'id' cho tham số CategoryID trong câu truy vấn
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));

                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Product getProductByID(String pid) {
        String sql = "SELECT * FROM [dbo].[Paints] WHERE ProductID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid); // Gán giá trị 'id' cho tham số CategoryID trong câu truy vấn
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));

                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                return p;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public List<Product> searchProductByName(String name) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Paints] WHERE ProductName LIKE N'%' + ? + '%'"; // Fixed the LIKE clause
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name); // This stays the same
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = cd.getCategoryById(rs.getInt(CATEGORY_ID));
                Supplier s = sd.getSupplierById(rs.getInt(SUPPLIER_ID));

                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Volume"),
                        rs.getString("Color"),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("UnitsInStock"),
                        rs.getInt("QuantitySold"),
                        rs.getBoolean("Discontinued"),
                        rs.getString("Image"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getBoolean("Status"),
                        c, s
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public int deleteProduct(String pid) {
        String sql = "DELETE FROM [dbo].[Paints] WHERE ProductID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, pid); // Gán giá trị 'pid' cho tham số ProductID trong câu truy vấn
            return st.executeUpdate(); // Trả về số dòng bị ảnh hưởng
        } catch (SQLException e) {
            System.out.println("Error during deletion: " + e.getMessage());
        }
        return 0; // Trả về 0 nếu không xóa được sản phẩm nào
    }

    public void insertProduct(String name, String image, String price, String stock,
            String sold, String volume, String color, String supplier,
            String description, String category, String discontinued, String status) {

        // Kiểm tra xem sản phẩm đã tồn tại
        if (productExists(name)) {
            return;
        }

        String sql = "INSERT INTO Paints (ProductName, SupplierID, CategoryID, Volume, Color, UnitPrice, UnitsInStock, QuantitySold, Discontinued, Image, Description, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Thiết lập các giá trị vào câu lệnh SQL
            st.setString(1, name);
            st.setInt(2, Integer.parseInt(supplier));  // SupplierID
            st.setInt(3, Integer.parseInt(category));  // CategoryID
            st.setDouble(4, Double.parseDouble(volume));  // Volume
            st.setString(5, color);
            st.setBigDecimal(6, new BigDecimal(price));  // UnitPrice
            st.setInt(7, Integer.parseInt(stock));  // UnitsInStock
            st.setInt(8, Integer.parseInt(sold));  // QuantitySold
            st.setBoolean(9, Boolean.parseBoolean(discontinued));  // Discontinued
            st.setString(10, image);
            st.setString(11, description);
            int statusValue = Integer.parseInt(status); // Nếu giá trị là 1 thì còn hàng, 0 thì hết hàng
            st.setBoolean(12, statusValue == 1); // Cần xác định cách lưu vào DB

            // Thực hiện câu lệnh SQL và trả về số dòng bị ảnh hưởng
            st.executeUpdate();

        } catch (SQLException e) {
        }
    }

// Phương thức kiểm tra xem sản phẩm đã tồn tại hay chưa
    private boolean productExists(String name) {
        String sql = "SELECT COUNT(*) FROM Paints WHERE ProductName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có sản phẩm, trả về true
            }
        } catch (SQLException e) {
        }
        return false; // Nếu không có sản phẩm, trả về false
    }

    public int editProduct(String name, String image, String price, String stock,
            String sold, String volume, String color, String supplier, String description,
            String category, String discontinued, String status, String pid) {

        // Sử dụng cú pháp SET để cập nhật các cột
        String sql = "UPDATE Paints SET ProductName = ?, SupplierID = ?, CategoryID = ?, Volume = ?, Color = ?, "
                + "UnitPrice = ?, UnitsInStock = ?, QuantitySold = ?, Discontinued = ?, Image = ?, "
                + "Description = ?, Status = ? WHERE ProductID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);

            // Thiết lập các giá trị vào câu lệnh SQL
            st.setString(1, name);
            st.setInt(2, Integer.parseInt(supplier));  // SupplierID
            st.setInt(3, Integer.parseInt(category));  // CategoryID
            st.setDouble(4, Double.parseDouble(volume));  // Volume
            st.setString(5, color);
            st.setBigDecimal(6, new BigDecimal(price));  // UnitPrice
            st.setInt(7, Integer.parseInt(stock));  // UnitsInStock
            st.setInt(8, Integer.parseInt(sold));  // QuantitySold
            st.setBoolean(9, Boolean.parseBoolean(discontinued));  // Discontinued
            st.setString(10, image);
            st.setString(11, description);

            // Cập nhật cột Status
            int statusValue = Integer.parseInt(status); // Giá trị 1 là còn hàng, 0 là hết hàng
            st.setBoolean(12, statusValue == 1);

            // Cập nhật ProductID
            st.setString(13, pid);

            // Thực hiện câu lệnh SQL và trả về số dòng bị ảnh hưởng
            return st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }

        return 0; // Trả về 0 nếu không cập nhật được sản phẩm nào
    }

    public void updateValueProduct(Product product, int value) {
        try {
            // Cập nhật số lượng hàng tồn kho và số lượng đã bán
            String sql = "UPDATE [dbo].[Paints] SET [UnitsInStock] = (UnitsInStock - ?), [QuantitySold] = (QuantitySold + ?) WHERE [ProductName] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, value);
            st.setInt(2, value);
            st.setString(3, product.getProductName());
            int rowsAffected = st.executeUpdate();

            // Kiểm tra số lượng hàng tồn kho sau khi cập nhật
            if (rowsAffected > 0) {
                // Lấy trạng thái hàng tồn kho hiện tại
                String checkStockSql = "SELECT UnitsInStock FROM [dbo].[Paints] WHERE [ProductName] = ?";
                PreparedStatement checkSt = connection.prepareStatement(checkStockSql);
                checkSt.setString(1, product.getProductName());
                ResultSet rs = checkSt.executeQuery();

                if (rs.next()) {
                    int unitsInStock = rs.getInt("UnitsInStock");

                    // Cập nhật trạng thái sản phẩm nếu hàng tồn kho bằng 0
                    boolean status = unitsInStock > 0; // Trạng thái còn hàng hay hết hàng
                    updateProductStatus(product.getProductName(), status); // Cập nhật trạng thái
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateProductStatus(String productName, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Paints] SET [Status] = ? WHERE [ProductName] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBoolean(1, status);
            st.setString(2, productName);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
