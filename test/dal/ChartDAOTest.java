/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import context.DBContext;
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
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100);

        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();

        assertEquals(1, result.size());
        assertEquals("Sơn Dulux", result.get(0).getProductName());
        assertEquals(100, result.get(0).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
    }

    @Test
    public void testGetBestSellingProducts_WithNullValues() throws SQLException {
        // Setup mock data with null values - just one row with null values
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn(null);
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(0);

        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getProductName());
        assertEquals(0, result.get(0).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
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
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(5);

        assertEquals(1, result.size());
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
    public void testGetTopSellingProducts_WithNullValues() throws SQLException {
        // Setup mock data with null values - just one row with null values
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn(null);
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(0);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getProductName());
        assertEquals(0, result.get(0).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next();
    }
    @Test
    public void testGetTopSellingProducts_LargeLimit() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("ProductTitle")).thenReturn("Sơn Dulux", "Sơn Mykolor");
        when(mockResultSet.getInt("TotalQuantitySold")).thenReturn(100, 30);

        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(100);

        assertEquals(2, result.size());
        assertEquals("Sơn Dulux", result.get(0).getProductName());
        assertEquals(100, result.get(0).getTotalQuantitySold());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 100);
        verify(mockPreparedStatement).executeQuery();
    }

    // -------------------- getSalesByCategory Tests --------------------
    @Test
    public void testGetSalesByCategory_WithMockData() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1);
        when(mockResultSet.getString("CategoryName")).thenReturn("Sơn Nội Thất");
        when(mockResultSet.getString("Description")).thenReturn("Mô tả sơn nội thất");
        when(mockResultSet.getInt("TotalSold")).thenReturn(150);

        List<Category> result = chartDAO.getSalesByCategory();

        assertEquals(1, result.size());
        assertEquals("Sơn Nội Thất", result.get(0).getName());
        assertEquals(1, result.get(0).getId());
        assertEquals("Mô tả sơn nội thất", result.get(0).getDescribe());
        assertEquals(150, chartDAO.getTotalSoldForCategory(1));

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next(); // Called twice (once true, once false)
        verify(mockResultSet, times(1)).getInt("CategoryID");
        verify(mockResultSet, times(1)).getString("CategoryName");
        verify(mockResultSet, times(1)).getString("Description");
        verify(mockResultSet, times(1)).getInt("TotalSold");
    }

    @Test
    public void testGetSalesByCategory_WithNullValues() throws SQLException {
        // Setup mock data with null values
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1);
        when(mockResultSet.getString("CategoryName")).thenReturn(null);
        when(mockResultSet.getString("Description")).thenReturn(null);
        when(mockResultSet.getInt("TotalSold")).thenReturn(0);

        List<Category> result = chartDAO.getSalesByCategory();

        assertEquals(1, result.size());
        assertNull(result.get(0).getName());
        assertNull(result.get(0).getDescribe());
        assertEquals(1, result.get(0).getId());

        // Check the sales data in the map
        assertEquals(0, chartDAO.getTotalSoldForCategory(1));

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next();  // Called twice (once true, once false)
    }

    @Test
    public void testGetSalesByCategory_EmptyResultSet() throws SQLException {
        // Setup mock data for empty result set
        when(mockResultSet.next()).thenReturn(false);

        List<Category> result = chartDAO.getSalesByCategory();
        assertTrue("Result should be empty", result.isEmpty());

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

    // -------------------- updatePaint Tests --------------------
    @Test
    public void testUpdatePaint_Success() throws SQLException {
        // Create test paint object
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getColor()).thenReturn("Blue");
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(100);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn("image-url.jpg");
        when(paint.getDescription()).thenReturn("Updated description");
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 1 (1 row affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 1 affected row", 1, affectedRows);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "Test Paint");
        verify(mockPreparedStatement).setDouble(2, 5.0); // volume
        verify(mockPreparedStatement).setString(3, "Blue"); // color
        verify(mockPreparedStatement).setDouble(4, 1000.0); // unit price
        verify(mockPreparedStatement).setInt(5, 100); // units in stock
        verify(mockPreparedStatement).setInt(6, 100); // quantity sold
        verify(mockPreparedStatement).setBoolean(7, false); // discontinued
        verify(mockPreparedStatement).setString(8, "image-url.jpg"); // image
        verify(mockPreparedStatement).setString(9, "Updated description"); // description
        verify(mockPreparedStatement).setBoolean(10, true); // status
        verify(mockPreparedStatement).setInt(11, 1); // product ID
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePaint_WithNullValues() throws SQLException {
        // Create test paint object with some null values
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(0.0);
        when(paint.getColor()).thenReturn(null); // Null color
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(100);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn(null); // Null image
        when(paint.getDescription()).thenReturn(null); // Null description
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 1 (1 row affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 1 affected row", 1, affectedRows);

        // Verify interactions - check that null values are properly handled
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setDouble(eq(2), eq(0.0d)); // Volume
        verify(mockPreparedStatement).setString(eq(3), isNull()); // Null color
        verify(mockPreparedStatement).setString(eq(8), isNull()); // Null image
        verify(mockPreparedStatement).setString(eq(9), isNull()); // Null description
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePaint_Failure() throws SQLException {
        // Create test paint object
        Product paint = mock(Product.class);
        when(paint.getProductID()).thenReturn(1);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getColor()).thenReturn("Blue");
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(100);
        when(paint.isDiscontinued()).thenReturn(false);
        when(paint.getImage()).thenReturn("image-url.jpg");
        when(paint.getDescription()).thenReturn("Updated description");
        when(paint.isStatus()).thenReturn(true);

        // Mock executeUpdate to return 0 (no rows affected)
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 affected rows", 0, affectedRows);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdatePaint_NullPaint() throws SQLException {
        // Test with null paint object
        int affectedRows = chartDAO.update(null);

        assertEquals("Should return 0 for null paint", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_NullProductName() throws SQLException {
        // Create test paint object with null product name
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn(null);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for null product name", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_EmptyProductName() throws SQLException {
        // Create test paint object with empty product name
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("");

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for empty product name", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_WhitespaceProductName() throws SQLException {
        // Create test paint object with whitespace-only product name
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("   ");

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for whitespace product name", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_NegativeVolume() throws SQLException {
        // Create test paint object with negative volume
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(-5.0);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for negative volume", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_NegativeUnitPrice() throws SQLException {
        // Create test paint object with negative unit price
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getUnitPrice()).thenReturn(-1000.0);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for negative unit price", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_NegativeUnitsInStock() throws SQLException {
        // Create test paint object with negative units in stock
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(-100);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for negative units in stock", 0, affectedRows);

        // Verify no interactions with prepared statement
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    @Test
    public void testUpdatePaint_NegativeQuantitySold() throws SQLException {
        // Create test paint object with negative quantity sold - use only what's needed
        Product paint = mock(Product.class);
        when(paint.getProductName()).thenReturn("Test Paint");
        when(paint.getVolume()).thenReturn(5.0);
        when(paint.getUnitPrice()).thenReturn(1000.0);
        when(paint.getUnitsInStock()).thenReturn(100);
        when(paint.getQuantitySold()).thenReturn(-100);

        int affectedRows = chartDAO.update(paint);

        assertEquals("Should return 0 for negative quantity sold", 0, affectedRows);

        // Verify interactions
        verify(mockConnection, never()).prepareStatement(anyString());
    }

    // -------------------- compareMonthlyRevenue Tests --------------------
    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_InvalidMonths() throws SQLException {
        // Should throw IllegalArgumentException for invalid months
        chartDAO.compareMonthlyRevenue(0, 2023, 1, 2023); // tháng < 1
        chartDAO.compareMonthlyRevenue(13, 2023, 1, 2023); // tháng > 12
        chartDAO.compareMonthlyRevenue(1, 2023, 0, 2023); // tháng < 1
        chartDAO.compareMonthlyRevenue(1, 2023, 13, 2023); // tháng > 12
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_InvalidYears() throws SQLException {
        // Should throw IllegalArgumentException for year < 2000
        chartDAO.compareMonthlyRevenue(1, 1999, 1, 2023);
        chartDAO.compareMonthlyRevenue(1, 2023, 1, 1999);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_FutureYears() throws SQLException {
        // Should throw IllegalArgumentException for future year
        int currentYear = java.time.Year.now().getValue();
        chartDAO.compareMonthlyRevenue(1, currentYear + 1, 1, 2023);
        chartDAO.compareMonthlyRevenue(1, 2023, 1, currentYear + 1);
    }

    @Test
    public void testCompareMonthlyRevenue_WithMockData() throws SQLException {
        // Setup mock data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 2);
        when(mockResultSet.getInt("Year")).thenReturn(2023, 2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0, 15000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2023, 2, 2023);

        // Verify results
        assertEquals("2023-1", result.get("period1"));
        assertEquals("2023-2", result.get("period2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(15000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(50.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2023);
        verify(mockPreparedStatement).setInt(3, 2);
        verify(mockPreparedStatement).setInt(4, 2023);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCompareMonthlyRevenue_MissingMonthData() throws SQLException {
        // Setup mock data with only one month having data
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1);
        when(mockResultSet.getInt("Year")).thenReturn(2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2023, 2, 2023);

        // Verify results
        assertEquals("2023-1", result.get("period1"));
        assertEquals("2023-2", result.get("period2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(-100.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);
    }

    @Test
    public void testCompareMonthlyRevenue_NoData() throws SQLException {
        // Setup mock data with no data
        when(mockResultSet.next()).thenReturn(false);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2023, 2, 2023);

        // Verify results
        assertEquals("2023-1", result.get("period1"));
        assertEquals("2023-2", result.get("period2"));
        assertEquals(0.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);
    }

    @Test
    public void testCompareMonthlyRevenue_ZeroFirstMonth() throws SQLException {
        // Setup mock data with zero revenue in first month
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 2);
        when(mockResultSet.getInt("Year")).thenReturn(2023, 2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(0.0, 15000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2023, 2, 2023);

        // Verify results - no division by zero
        assertEquals("2023-1", result.get("period1"));
        assertEquals("2023-2", result.get("period2"));
        assertEquals(0.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(15000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);
    }

    @Test
    public void testCompareMonthlyRevenue_DecreaseInRevenue() throws SQLException {
        // Setup mock data for decreasing revenue
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(4, 5);
        when(mockResultSet.getInt("Year")).thenReturn(2023, 2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(20000.0, 10000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(4, 2023, 5, 2023);

        // Verify results - should show negative percentage change
        assertEquals("2023-4", result.get("period1"));
        assertEquals("2023-5", result.get("period2"));
        assertEquals(20000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(10000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(-50.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);
    }

    @Test
    public void testCompareMonthlyRevenue_DifferentYears() throws SQLException {
        // Setup mock data for different years
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("Month")).thenReturn(12, 1);
        when(mockResultSet.getInt("Year")).thenReturn(2022, 2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(18000.0, 21600.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(12,     2022, 1, 2023);

        // Verify results - should handle year boundary comparison
        assertEquals("2022-12", result.get("period1"));
        assertEquals("2023-1", result.get("period2"));
        assertEquals(18000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(21600.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(20.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);
    }

    @Test
    public void testCompareMonthlyRevenue_SameMonthAndYear() throws SQLException {
        // Setup mock data for the same month and year
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("Month")).thenReturn(1, 1);
        when(mockResultSet.getInt("Year")).thenReturn(2023, 2023);
        when(mockResultSet.getDouble("Revenue")).thenReturn(10000.0, 10000.0);

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2023, 1, 2023);

        // Verify results - should show 0% difference
        assertEquals("2023-1", result.get("period1"));
        assertEquals("2023-1", result.get("period2"));
        assertEquals(10000.0, ((Number) result.get("revenue1")).doubleValue(), 0.001);
        assertEquals(10000.0, ((Number) result.get("revenue2")).doubleValue(), 0.001);
        assertEquals(0.0, ((Number) result.get("differenceRatio")).doubleValue(), 0.001);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 2023);
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).setInt(4, 2023);
        verify(mockPreparedStatement).executeQuery();
    }
}
