/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private CategoryDAO mockCategoryDAO;

    @Mock
    private SupplierDAO mockSupplierDAO;

    @InjectMocks
    private ProductDAO productDAO;

    @Before
    public void setup() throws Exception {
        productDAO = new ProductDAO();
        productDAO.connection = mockConnection;
        productDAO.cd = mockCategoryDAO;
        productDAO.sd = mockSupplierDAO;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    public void testGetAllProducts_WithData() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("ProductID")).thenReturn(1, 2);
        when(mockResultSet.getString("ProductName")).thenReturn("Paint A", "Paint B");
        when(mockResultSet.getDouble("Volume")).thenReturn(1.5, 2.0);
        when(mockResultSet.getString("Color")).thenReturn("Red", "Blue");
        when(mockResultSet.getDouble("UnitPrice")).thenReturn(10.5, 12.0);
        when(mockResultSet.getInt("UnitsInStock")).thenReturn(100, 50);
        when(mockResultSet.getInt("QuantitySold")).thenReturn(30, 20);
        when(mockResultSet.getBoolean("Discontinued")).thenReturn(false, true);
        when(mockResultSet.getString("Image")).thenReturn("imageA.jpg", "imageB.jpg");
        when(mockResultSet.getString("Description")).thenReturn("Good quality", "Premium quality");
        when(mockResultSet.getDouble("Discount")).thenReturn(0.1, 0.2);
        when(mockResultSet.getBoolean("Status")).thenReturn(true, false);
        lenient().when(mockResultSet.getInt("CATEGORY_ID")).thenReturn(1, 2);
        lenient().when(mockResultSet.getInt("SUPPLIER_ID")).thenReturn(1, 2);

        List<Product> products = productDAO.getAll();

        assertEquals(2, products.size());

        // Kiểm tra dữ liệu sản phẩm thứ nhất
        Product product1 = products.get(0);
        assertEquals(1, product1.getProductID());
        assertEquals("Paint A", product1.getProductName());
        assertEquals(1.5, product1.getVolume(), 0.001);
        assertEquals("Red", product1.getColor());
        assertEquals(10.5, product1.getUnitPrice(), 0.001);
        assertEquals(100, product1.getUnitsInStock());
        assertEquals(30, product1.getQuantitySold());
        assertFalse(product1.isDiscontinued());
        assertEquals("imageA.jpg", product1.getImage());
        assertEquals("Good quality", product1.getDescription());
        assertEquals(0.1, product1.getDiscount(), 0.001);
        assertTrue(product1.isStatus());

        // Kiểm tra dữ liệu sản phẩm thứ hai
        Product product2 = products.get(1);
        assertEquals(2, product2.getProductID());
        assertEquals("Paint B", product2.getProductName());
        assertEquals(2.0, product2.getVolume(), 0.001);
        assertEquals("Blue", product2.getColor());
        assertEquals(12.0, product2.getUnitPrice(), 0.001);
        assertEquals(50, product2.getUnitsInStock());
        assertEquals(20, product2.getQuantitySold());
        assertTrue(product2.isDiscontinued());
        assertEquals("imageB.jpg", product2.getImage());
        assertEquals("Premium quality", product2.getDescription());
        assertEquals(0.2, product2.getDiscount(), 0.001);
        assertFalse(product2.isStatus());
    }

    @Test
    public void testGetAllProducts_NoData() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Không có dữ liệu

        List<Product> products = productDAO.getAll();
        assertEquals(0, products.size()); // Kiểm tra danh sách rỗng
    }

    // Test delete product
    @Test
    public void testDeleteProduct_Success() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Cập nhật thành công

        int result = productDAO.deleteProduct("P001");
        assertEquals(1, result);
    }

    // Test khi không có sản phẩm nào được cập nhật (ProductID không tồn tại)
    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Không có dòng nào bị ảnh hưởng

        int result = productDAO.deleteProduct("P999");
        assertEquals(0, result);
    }

// Test khi truyền vào chuỗi rỗng
    @Test
    public void testDeleteProduct_EmptyString() {
        int result = productDAO.deleteProduct(""); // Không gọi DB vì `pid` rỗng
        assertEquals(0, result);
    }

// Test khi truyền vào null
    @Test
    public void testDeleteProduct_Null() {
        int result = productDAO.deleteProduct(null); // Không gọi DB vì `pid` null
        assertEquals(0, result);
    }

    //Test update product status
    @Test
    public void testUpdateProductStatus_Success_True() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.updateProductStatus("Paint A", true);

        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setString(2, "Paint A");
        verify(mockPreparedStatement).executeUpdate();

    }

    @Test
    public void testUpdateProductStatus_Success_False() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.updateProductStatus("Paint A", false);

        verify(mockPreparedStatement).setBoolean(1, false);
        verify(mockPreparedStatement).setString(2, "Paint A");
        verify(mockPreparedStatement).executeUpdate();

    }

    @Test
    public void testUpdateProductStatus_NotFound() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Không có dòng nào bị cập nhật

        productDAO.updateProductStatus("Paint B", true);

        verify(mockPreparedStatement).setBoolean(1, true);
        verify(mockPreparedStatement).setString(2, "Paint B");
        verify(mockPreparedStatement).executeUpdate();

    }

    @Test
    public void testUpdateProductStatus_EmptyName() throws SQLException {
        productDAO.updateProductStatus("", true);
        verify(mockPreparedStatement, never()).executeUpdate(); // Không gọi SQL
    }

    @Test
    public void testUpdateProductStatus_NullName() throws SQLException {
        productDAO.updateProductStatus(null, false);
        verify(mockPreparedStatement, never()).executeUpdate(); // Không gọi SQL
    }

    //Test insert product
    @Test
    public void testInsertProduct_ValidData() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();

    }

    @Test
    public void testInsertProduct_AlreadyExists() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Sản phẩm đã tồn tại
        when(mockResultSet.getInt(1)).thenReturn(1); // COUNT(*) trả về số lượng sản phẩm

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        // Đảm bảo rằng không gọi `executeUpdate()`
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyName() throws Exception {
        productDAO.insertProduct("", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyImage() throws Exception {
        productDAO.insertProduct("Paint A", "", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyPrice() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyStock() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptySold() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "1000", "100", "", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyVolume() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "1000", "100", "20", "", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyColor() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptySupplier() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyDecription() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyCategoryID() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyDiscontinued() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "1", "", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_AllEmptyInputs() throws Exception {
        productDAO.insertProduct("", "", "", "", "", "", "", "", "", "", "", "");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_EmptyStatus() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "1", "false", "");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullName() throws Exception {
        productDAO.insertProduct(null, "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullImage() throws Exception {
        productDAO.insertProduct("Paint A", null, "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullPrice() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", null, "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullStock() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", null, "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullSold() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "1000", "100", null, "1.5", "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullVolume() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "1000", "100", "20", null, "Red", "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullColor() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", null, "1", "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullSupplier() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", null, "Good quality", "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullDecription() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", null, "2", "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullCategoryID() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", null, "false", "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullDiscontinued() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "1", null, "1");
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NullStatus() throws Exception {
        productDAO.insertProduct("PaintA", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "1", "false", null);
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_AllNullInputs() throws Exception {
        productDAO.insertProduct(null, null, null, null, null, null, null, null, null, null, null, null);
        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NegativePrice() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "-1", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NegativeStock() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "-1", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NegativeSold() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "-1", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_NegativeVolume() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "-1", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_ZeroPrice() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "0", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_ZeroStock() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "0", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_ZeroSold() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "0", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_ZeroVolume() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "0", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_MaxStockValue() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", String.valueOf(Short.MAX_VALUE), "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_StockExceedsMaxValue() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", String.valueOf((long) Short.MAX_VALUE + 1), "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }

    @Test
    public void testInsertProduct_MaxSoldValue() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", String.valueOf(Integer.MAX_VALUE), "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_SoldExceedsMaxValue() throws Exception {
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", String.valueOf((long) Integer.MAX_VALUE + 1), "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement, never()).executeUpdate();
    }
}
