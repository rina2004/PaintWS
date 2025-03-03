/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import dal.ProductDAO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anhbu
 */
public class ProductJUnitTest {

    private ProductDAO dao;

    @Before
    public void setUp() {
        dao = new ProductDAO();
    }

//    //Test productExists
//    @Test
//    public void testProductExists_ProductExists() {
//        String existingProductName = "Sơn ngoại thất đặc biệt";
//        boolean result = dao.productExists(existingProductName);
//        assertTrue("Product must exsist !", result);
//    }
//
//    @Test
//    public void testProductExists_ProductDoesNotExist() {
//        String nonExistingProductName = "NonExistentPaint";
//        boolean result = dao.productExists(nonExistingProductName);
//        assertFalse("Product does not exsist !", result);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testProductExists_DatabaseError() {
//        // Giả lập lỗi bằng cách đóng kết nối
//        dao.closeConnection();
//        dao.productExists("AnyProduct"); // Nên ném RuntimeException
//    }
//
//    //Test deleteProduct
//    @Test
//    public void testDeleteProduct_Success() {
//        String existingProductId = "2"; // Đảm bảo có sản phẩm này trong DB
//        int rowsAffected = dao.deleteProduct(existingProductId);
//        assertEquals("Sản phẩm phải bị xóa thành công", 1, rowsAffected);
//    }
//
//    @Test
//    public void testDeleteProduct_NotFound() {
//        String nonExistingProductId = "999999"; // ID không tồn tại
//        int rowsAffected = dao.deleteProduct(nonExistingProductId);
//        assertEquals("Không có sản phẩm nào bị xóa", 0, rowsAffected);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testDeleteProduct_DatabaseError() {
//        dao.closeConnection(); // Đóng kết nối để giả lập lỗi
//        dao.deleteProduct("1"); // Phải ném RuntimeException
//    }
//    // Test insertProduct
//    @Test
//    public void testInsertProduct_Success() {
//        String name = "Sơn mới thử nghiệm";
//        String image = "son_moi.jpg";
//        String price = "500000";
//        String stock = "100";
//        String sold = "0";
//        String volume = "5";
//        String color = "Xanh";
//        String supplier = "1";
//        String description = "Sơn chất lượng cao";
//        String categoryId = "2";
//        String discontinued = "false";
//        String status = "1";
//
//        dao.insertProduct(name, image, price, stock, sold, volume, color, supplier, description, categoryId, discontinued, status);
//
//        // Kiểm tra xem sản phẩm đã được chèn hay chưa
//        assertTrue("Sản phẩm phải được chèn thành công", dao.productExists(name));
//    }
//
//// Test insertProduct với sản phẩm bị trùng
//    @Test
//    public void testInsertProduct_Duplicate() {
//        String name = "Sơn ngoại thất đặc biệt"; // Tên sản phẩm đã tồn tại
//        String image = "son_cu.jpg";
//        String price = "600000";
//        String stock = "50";
//        String sold = "10";
//        String volume = "10";
//        String color = "Đỏ";
//        String supplier = "2";
//        String description = "Sơn đã tồn tại";
//        String categoryId = "3";
//        String discontinued = "false";
//        String status = "1";
//
//        dao.insertProduct(name, image, price, stock, sold, volume, color, supplier, description, categoryId, discontinued, status);
//
//        // Kiểm tra sản phẩm không được thêm lần nữa
//        boolean exists = dao.productExists(name);
//        assertTrue("Sản phẩm trùng không nên được chèn vào", exists);
//    }
//
//// Test khi xảy ra lỗi DB
//    @Test(expected = RuntimeException.class)
//    public void testInsertProduct_DatabaseError() {
//        dao.closeConnection(); // Đóng kết nối để giả lập lỗi DB
//        dao.insertProduct("Test", "test.jpg", "10000", "5", "0", "1", "Black", "1", "Test product", "1", "false", "1");
//    }
    
    
    // Test updateProductStatus: cập nhật trạng thái thành công
    @Test
    public void testUpdateProductStatus_Success() {
        String productName = "Sơn chống Thấm Pha Xi Măng";
        boolean newStatus = false;

        dao.updateProductStatus(productName, newStatus);
        Boolean status = dao.getProductStatus(productName);

        assertNotNull("Product must exist!", status);
        assertEquals("Status must be updated!", newStatus, status);
    }

    // Test updateProductStatus: sản phẩm không tồn tại
    @Test
    public void testUpdateProductStatus_ProductDoesNotExist() {
        String nonExistingProduct = "NonExistentPaint";
        boolean newStatus = true;

        dao.updateProductStatus(nonExistingProduct, newStatus);
        Boolean status = dao.getProductStatus(nonExistingProduct);

        assertNull("Product does not exist!", status);
    }

    

}
