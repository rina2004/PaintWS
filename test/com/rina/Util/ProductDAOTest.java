/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.rina.Util;

import dal.ProductDAO;
import dal.CategoryDAO;
import dal.SupplierDAO;
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

        assertNotNull(products);
        assertEquals(2, products.size());

        assertEquals("Paint A", products.get(0).getProductName());
        assertEquals("Paint B", products.get(1).getProductName());

    }

    @Test
    public void testGetAllProducts_NoData() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Không có dữ liệu

        List<Product> products = productDAO.getAll();

        assertNotNull(products);
        assertEquals(0, products.size()); // Kiểm tra danh sách rỗng
    }

    //Test delete product
    @Test
    public void testDeleteProduct_Success() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Xóa thành công

        int result = productDAO.deleteProduct("P001");
        assertEquals(1, result);
    }

    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Không có sản phẩm nào bị xóa

        int result = productDAO.deleteProduct("P999");
        assertEquals(0, result);
    }

    @Test
    public void testDeleteProduct_EmptyString() {
        int result = productDAO.deleteProduct(""); // Không gọi DB vì `pid` rỗng
        assertEquals(0, result);
    }

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
    
    

    @Test
    public void testInsertProduct_ValidData() throws Exception {
        // Tạo mock ResultSet
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Sản phẩm chưa tồn tại

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Gọi phương thức thực của `productExists()` mà không cần `when().thenCallRealMethod()`
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertProduct_AlreadyExists() throws Exception {
        // Mock ResultSet
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Sản phẩm đã tồn tại
        when(mockResultSet.getInt(1)).thenReturn(1); // COUNT(*) trả về số lượng sản phẩm

        // Gọi phương thức thực của `productExists()` mà không cần `when().thenCallRealMethod()`
        productDAO.insertProduct("Paint A", "image.jpg", "10.5", "100", "20", "1.5", "Red", "1", "Good quality", "2", "false", "1");

        // Đảm bảo rằng không gọi `executeUpdate()`
        verify(mockPreparedStatement, never()).executeUpdate();
    }

}
