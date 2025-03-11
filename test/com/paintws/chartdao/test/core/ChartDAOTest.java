/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paintws.chartdao.test.core;

import context.DBContext;
import dal.ChartDAO;
import model.*;
import java.sql.*;
import java.util.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author A A
 */
@RunWith(MockitoJUnitRunner.class)
public class ChartDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    // Help mock create real ver. of chartdao & auto inject mocks into it, implement true logic
    @InjectMocks
    private ChartDAO chartDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup mock connection for unit tests
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        chartDAO = new ChartDAO();

        // Use reflection to set protected connection field
        setConnectionField(chartDAO, mockConnection);
    }

    // Helper method to use reflection to set the protected connection field
    private void setConnectionField(DBContext dao, Connection conn) throws Exception {
        java.lang.reflect.Field connectionField = DBContext.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        connectionField.set(dao, conn);
    }

    // -------------------- getBestSellingProducts Tests --------------------
    @Test
    public void testGetBestSellingProducts_WithMockData() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux", "Sơn Jotun");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100, 50);

        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();

        assertEquals(2, result.size());
        assertEquals("Sơn Dulux", result.get(0).getProductName());
        assertEquals(100, result.get(0).getTotalQuantitySold());
        assertEquals("Sơn Jotun", result.get(1).getProductName());
        assertEquals(50, result.get(1).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    public void testGetBestSellingProducts_EmptyResultSet() throws SQLException {
        // Setup mock data for empty result set
        when(mockResultSet.next()).thenReturn(false);

        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();

        assertTrue("Result should be empty", result.isEmpty());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    // -------------------- getTopSellingProducts Tests --------------------
    @Test
    public void testGetTopSellingProducts_WithMockData() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux", "Sơn Jotun");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100, 50);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(5);

        assertEquals(2, result.size());
        assertEquals("Sơn Dulux", result.get(0).getProductName());
        assertEquals(100, result.get(0).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 5);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetTopSellingProducts_EmptyResultSet() throws SQLException {
        // Setup mock data for empty result set
        when(mockResultSet.next()).thenReturn(false);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(5);

        assertTrue("Result should be empty", result.isEmpty());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 5);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetTopSellingProducts_LargeLimit() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux", "Sơn Jotun", "Sơn Mykolor");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100, 50, 30);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(100);

        assertEquals(3, result.size());
        assertEquals("Sơn Dulux", result.get(0).getProductName());
        assertEquals(100, result.get(0).getTotalQuantitySold());
        assertEquals("Sơn Jotun", result.get(1).getProductName());
        assertEquals(50, result.get(1).getTotalQuantitySold());
        assertEquals("Sơn Mykolor", result.get(2).getProductName());
        assertEquals(30, result.get(2).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 100);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetTopSellingProducts_ZeroLimit() throws SQLException {
        // Test with zero limit
        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(0);

        assertTrue(result.isEmpty());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 0);
        verify(mockPreparedStatement).executeQuery();
    }

    // -------------------- getSalesByCategory Tests --------------------
    @Test
    public void testGetSalesByCategory_WithMockData() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("CategoryName")).thenReturn("Sơn Nội Thất", "Sơn Ngoại Thất");
        when(mockResultSet.getInt("TotalSold")).thenReturn(150, 100);

        List<CategorySales> result = chartDAO.getSalesByCategory();

        assertEquals(2, result.size());
        assertEquals("Sơn Nội Thất", result.get(0).getCategoryName());
        assertEquals(150, result.get(0).getTotalSold());
        assertEquals("Sơn Ngoại Thất", result.get(1).getCategoryName());
        assertEquals(100, result.get(1).getTotalSold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    public void testGetSalesByCategory_EmptyResultSet() throws SQLException {
        // Setup mock data for empty result set
        when(mockResultSet.next()).thenReturn(false);

        List<CategorySales> result = chartDAO.getSalesByCategory();

        assertTrue("Result should be empty", result.isEmpty());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    @Test
    public void testGetSalesByCategory_WithZeroSales() throws SQLException {
        // Setup mock data with zero sales
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CategoryName")).thenReturn("Sơn Trang Trí");
        when(mockResultSet.getInt("TotalSold")).thenReturn(0);

        List<CategorySales> result = chartDAO.getSalesByCategory();

        assertEquals(1, result.size());
        assertEquals("Sơn Trang Trí", result.get(0).getCategoryName());
        assertEquals(0, result.get(0).getTotalSold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetSalesByCategory_NullCategoryName() throws SQLException {
        // Setup mock data with null category name
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("CategoryName")).thenReturn(null);
        when(mockResultSet.getInt("TotalSold")).thenReturn(100);

        List<CategorySales> result = chartDAO.getSalesByCategory();

        assertEquals(1, result.size());
        assertNull(result.get(0).getCategoryName());
        assertEquals(100, result.get(0).getTotalSold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
    }
    
    // -------------------- updatePaint Tests --------------------
    @Test
    public void testUpdatePaint_Success() throws SQLException {
        // Create mock objects
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(1);

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(2);

        // Create test paint object
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Updated Paint Name");
        when(paint.getSupplier()).thenReturn(supplier);
        when(paint.getCategory()).thenReturn(category);
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getColor()).thenReturn("Blue");
        when(paint.getUnitPrice()).thenReturn(1500.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(50);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn("image-url.jpg");
        when(paint.getDescription()).thenReturn("Updated description");
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 1 (1 row affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = chartDAO.updatePaint(paint);

        assertTrue("Update should be successful", result);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "Updated Paint Name");
        verify(mockPreparedStatement).setInt(2, 1); // supplier ID
        verify(mockPreparedStatement).setInt(3, 2); // category ID
        verify(mockPreparedStatement).setDouble(4, 5.0); // volume
        verify(mockPreparedStatement).setString(5, "Blue"); // color
        verify(mockPreparedStatement).setDouble(6, 1500.0); // unit price
        verify(mockPreparedStatement).setInt(7, 100); // units in stock
        verify(mockPreparedStatement).setInt(8, 50); // quantity sold
        verify(mockPreparedStatement).setBoolean(9, false); // discontinued
        verify(mockPreparedStatement).setString(10, "image-url.jpg"); // image
        verify(mockPreparedStatement).setString(11, "Updated description"); // description
        verify(mockPreparedStatement).setBoolean(12, true); // status
        verify(mockPreparedStatement).setInt(13, 1); // product ID
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePaint_Failure() throws SQLException {
        // Create mock objects
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(1);

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(2);

        // Create test paint object
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Updated Paint Name");
        when(paint.getSupplier()).thenReturn(supplier);
        when(paint.getCategory()).thenReturn(category);
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getColor()).thenReturn("Blue");
        when(paint.getUnitPrice()).thenReturn(1500.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(50);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn("image-url.jpg");
        when(paint.getDescription()).thenReturn("Updated description");
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 0 (no rows affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        boolean result = chartDAO.updatePaint(paint);

        assertFalse("Update should fail when no rows affected", result);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePaint_WithNullValues() throws SQLException {
        // Create mock objects with some null values
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(1);

        Category category = mock(Category.class);
        when(category.getId()).thenReturn(2);

        // Create test paint object with some null values
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getSupplier()).thenReturn(supplier);
        when(paint.getCategory()).thenReturn(category);
        when(paint.getVolume()).thenReturn(0.0);
        when(paint.getColor()).thenReturn(null); // Null color
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(50);
        when(paint.getQuantitySold()).thenReturn(25);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn(null); // Null image
        when(paint.getDescription()).thenReturn(null); // Null description
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 1 (1 row affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = chartDAO.updatePaint(paint);

        assertTrue("Update should be successful even with null values", result);

        // Verify interactions - check that null values are properly handled
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setDouble(eq(4), eq(0.0d)); // Null volume
        verify(mockPreparedStatement).setString(eq(5), isNull()); // Null color
        verify(mockPreparedStatement).setString(eq(10), isNull()); // Null image
        verify(mockPreparedStatement).setString(eq(11), isNull()); // Null description
        verify(mockPreparedStatement).executeUpdate();
    }
    
    // -------------------- compareMonthlyRevenue Tests --------------------
    @Test
    public void testCompareMonthlyRevenue_WithMockData() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 2);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0, 15000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);

        // Verify results
        assertEquals(1, result.get("month1"));
        assertEquals(2, result.get("month2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(15000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(50.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCompareMonthlyRevenue_OneMonthMissing() throws SQLException {
        // Setup mock data with only one month having data
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);

        // Verify results
        assertEquals(1, result.get("month1"));
        assertEquals(2, result.get("month2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(-100.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCompareMonthlyRevenue_NoData() throws SQLException {
        // Setup mock data with no data
        when(mockResultSet.next()).thenReturn(false);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);

        // Verify results
        assertEquals(1, result.get("month1"));
        assertEquals(2, result.get("month2"));
        assertEquals(0.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCompareMonthlyRevenue_ZeroFirstMonth() throws SQLException {
        // Setup mock data with zero revenue in first month
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 2);
        when(mockResultSet.getDouble("Revenue")).thenReturn(0.0, 15000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);

        // Verify results
        assertEquals(1, result.get("month1"));
        assertEquals(2, result.get("month2"));
        assertEquals(0.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(15000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001); // No division by zero

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2);
        verify(mockPreparedStatement).executeQuery();
    }

    // Detailed test for compareMonthlyRevenue
    @Test
    public void testCompareMonthlyRevenue_DecreaseInRevenue() throws SQLException {
        // Setup mock data for decreasing revenue
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(4, 5);
        when(mockResultSet.getDouble("Revenue")).thenReturn(20000.0, 10000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(4, 5);

        // Verify results - should show negative percentage change
        assertEquals(4, result.get("month1"));
        assertEquals(5, result.get("month2"));
        assertEquals(20000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(10000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(-50.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 4);
        verify(mockPreparedStatement).setInt(2, 5);
        verify(mockPreparedStatement).executeQuery();
    }

    // Invalid input test for compareMonthlyRevenue
    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_InvalidMonth() throws SQLException {
        // Should throw IllegalArgumentException for invalid month values
        chartDAO.compareMonthlyRevenue(0, 13);
    }

    // Equal month test for compareMonthlyRevenue
    @Test
    public void testCompareMonthlyRevenue_SameMonth() throws SQLException {
        // Setup mock data for same month
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("Month")).thenReturn(6);
        when(mockResultSet.getDouble("Revenue")).thenReturn(15000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(6, 6);

        // Verify results - should handle comparing same month
        assertEquals(6, result.get("month1"));
        assertEquals(6, result.get("month2"));
        assertEquals(15000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        // Percentage change should reflect comparing month to itself
        assertEquals(-100.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 6);
        verify(mockPreparedStatement).setInt(2, 6);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCompareMonthlyRevenue_NonSequentialMonths() throws SQLException {
        // Setup mock data for non-sequential months (e.g., January vs July)
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 7);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0, 30000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 7);

        // Verify results for comparing non-sequential months
        assertEquals(1, result.get("month1"));
        assertEquals(7, result.get("month2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(30000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(200.0, ((Number) result.get("percentageChange")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 7);
        verify(mockPreparedStatement).executeQuery();
    }
}
