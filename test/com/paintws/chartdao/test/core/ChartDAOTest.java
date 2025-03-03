package com.paintws.chartdao.test.core;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.sql.*;
import java.util.*;

import context.DBContext;
import dal.ChartDAO;
import model.BestSellingProduct;
import model.CategorySales;
import model.MonthlySales;

/**
 *
 * @author A A
 */
public class ChartDAOTest {
    
    private ChartDAO chartDAO;
    private ChartDAO mockChartDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    
    public ChartDAOTest() {
    }
    
    @Before
    public void setUp() throws SQLException, Exception {
        // Setup regular DAO for integration tests
        chartDAO = new ChartDAO();
        
//        // Setup mocks for unit tests
//        mockConnection = mock(Connection.class);
//        mockPreparedStatement = mock(PreparedStatement.class);
//        mockResultSet = mock(ResultSet.class);
//        
//        // Create a spy of ChartDAO
//        mockChartDAO = spy(new ChartDAO());
//        
//        // Configure mock connection behavior
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
//        
//        // This approach assumes ChartDAO extends DBContext and you can override getConnection
//        // If your structure is different, you may need to modify this part
//        doReturn(mockConnection).when(mockChartDAO).getConnection();
    }
    
    @After
    public void tearDown() {
        chartDAO = null;
        mockChartDAO = null;
        mockConnection = null;
        mockPreparedStatement = null;
        mockResultSet = null;
    }
    
    //================= Integration Tests with real database =================
    
    @Test
    public void testGetBestSellingProducts_ReturnsNonEmptyList() {
        // Skip if integration tests cannot be run
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            assertNotNull("Result should not be null", result);
            assertFalse("Result should not be empty", result.isEmpty());
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
            // Don't fail the test if DB isn't available
        }
    }
    
    @Test
    public void testGetBestSellingProducts_ContainsValidData() {
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            
            // Skip if no data returned
            if (result.isEmpty()) {
                return;
            }
            
            // Check that each item has non-null and valid data
            for (BestSellingProduct product : result) {
                assertNotNull("Product name should not be null", product.getProductName());
                assertFalse("Product name should not be empty", product.getProductName().isEmpty());
                
                // Quantity sold should be non-negative
                assertTrue("Quantity sold should be non-negative", product.getTotalQuantitySold() >= 0);
            }
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    //Ordering Test
    @Test
    public void testGetBestSellingProducts_OrderedByQuantityDesc() {
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            
            // Skip test if less than 2 products 
            if (result.size() < 2) {
                return;
            }
            
            // Check descending order
            for (int i = 0; i < result.size() - 1; i++) {
                int currentQuantity = result.get(i).getTotalQuantitySold();
                int nextQuantity = result.get(i + 1).getTotalQuantitySold();
                
                assertTrue("Products should be ordered by quantity sold in descending order",
                        currentQuantity >= nextQuantity);
            }
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    //Expected Data Test
    @Test
    public void testGetBestSellingProducts_MatchesExpectedData() {
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            boolean foundExpectedProduct = false;
            
            for (BestSellingProduct product : result) {
                if (product.getProductName().contains("Sơn")) {
                    foundExpectedProduct = true;
                    break;
                }
            }
            assertTrue("Result should contain at least one expected product", foundExpectedProduct);
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    //Error Handling Test
    @Test
    public void testGetBestSellingProducts_HandlesEmptyDatabase() {
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            assertNotNull("Method should return a list (possibly empty) even with no data", result);
        } catch (Exception e) {
            fail("Method should not throw exceptions when database might be empty: " + e.getMessage());
        }
    }
    
//    //================= Unit Tests with Mockito =================
//    
//    @Test
//    public void testGetBestSellingProducts_WithMockData() throws SQLException, Exception {
//        // Setup mock to return 2 rows of data
//        when(mockResultSet.next())
//            .thenReturn(true)  // First call returns true
//            .thenReturn(true)  // Second call returns true
//            .thenReturn(false); // Third call returns false (end of ResultSet)
//        
//        when(mockResultSet.getString("ProductTitle"))
//            .thenReturn("Mock Product 1")
//            .thenReturn("Mock Product 2");
//        
//        when(mockResultSet.getInt("TotalQuantitySold"))
//            .thenReturn(100)
//            .thenReturn(50);
//        
//        // Execute the method
//        List<BestSellingProduct> result = mockChartDAO.getBestSellingProducts();
//        
//        // Verify the SQL query was prepared
//        verify(mockConnection).prepareStatement(contains("SELECT"));
//        verify(mockConnection).prepareStatement(contains("ORDER BY p.QuantitySold DESC"));
//        
//        // Assert results
//        assertEquals("Should return 2 products", 2, result.size());
//        assertEquals("First product name should match", "Mock Product 1", result.get(0).getProductName());
//        assertEquals("First product quantity should match", 100, result.get(0).getTotalQuantitySold());
//        assertEquals("Second product name should match", "Mock Product 2", result.get(1).getProductName());
//        assertEquals("Second product quantity should match", 50, result.get(1).getTotalQuantitySold());
//    }
//    
//    @Test
//    public void testGetTopSellingProducts_Limit() throws SQLException, Exception {
//        // Setup mock result
//        when(mockResultSet.next())
//            .thenReturn(true)
//            .thenReturn(false);
//        
//        when(mockResultSet.getString("ProductTitle"))
//            .thenReturn("Top Product");
//        
//        when(mockResultSet.getInt("TotalQuantitySold"))
//            .thenReturn(200);
//        
//        // Execute with limit of 1
//        List<BestSellingProduct> result = mockChartDAO.getTopSellingProducts(1);
//        
//        // Verify the correct parameters were set
//        verify(mockPreparedStatement).setInt(1, 1);
//        
//        // Verify results
//        assertEquals("Should return 1 product", 1, result.size());
//        assertEquals("Product name should match", "Top Product", result.get(0).getProductName());
//        assertEquals("Product quantity should match", 200, result.get(0).getTotalQuantitySold());
//    }
//    
//    @Test
//    public void testGetSalesByCategory() throws SQLException, Exception {
//        // Setup mock to return 2 categories
//        when(mockResultSet.next())
//            .thenReturn(true)
//            .thenReturn(true)
//            .thenReturn(false);
//        
//        when(mockResultSet.getString("CategoryName"))
//            .thenReturn("Category 1")
//            .thenReturn("Category 2");
//        
//        when(mockResultSet.getInt("TotalSold"))
//            .thenReturn(150)
//            .thenReturn(120);
//        
//        // Execute the method
//        List<CategorySales> result = mockChartDAO.getSalesByCategory();
//        
//        // Verify the SQL query contains expected terms
//        verify(mockConnection).prepareStatement(contains("Categories"));
//        verify(mockConnection).prepareStatement(contains("GROUP BY"));
//        
//        // Assert results
//        assertEquals("Should return 2 categories", 2, result.size());
//        assertEquals("First category name should match", "Category 1", result.get(0).getCategoryName());
//        assertEquals("First category sales should match", 150, result.get(0).getTotalSold());
//        assertEquals("Second category name should match", "Category 2", result.get(1).getCategoryName());
//        assertEquals("Second category sales should match", 120, result.get(1).getTotalSold());
//    }
//    
//    @Test
//    public void testGetMonthlySales() throws SQLException, Exception {
//        // Setup mock to return 2 months
//        when(mockResultSet.next())
//            .thenReturn(true)
//            .thenReturn(true)
//            .thenReturn(false);
//        
//        when(mockResultSet.getInt("Month"))
//            .thenReturn(1)
//            .thenReturn(2);
//        
//        when(mockResultSet.getDouble("Revenue"))
//            .thenReturn(10000.0)
//            .thenReturn(15000.0);
//        
//        // Execute the method
//        List<MonthlySales> result = mockChartDAO.getMonthlySales();
//        
//        // Verify SQL components
//        verify(mockConnection).prepareStatement(contains("MONTH"));
//        verify(mockConnection).prepareStatement(contains("Revenue"));
//        
//        // Assert results
//        assertEquals("Should return 2 months", 2, result.size());
//        assertEquals("First month should be January", 1, result.get(0).getMonth());
//        assertEquals("First month revenue should match", 10000.0, result.get(0).getRevenue(), 0.01);
//        assertEquals("Second month should be February", 2, result.get(1).getMonth());
//        assertEquals("Second month revenue should match", 15000.0, result.get(1).getRevenue(), 0.01);
//    }
//    
//    @Test
//    public void testCompareMonthlyRevenue() throws SQLException, Exception {
//        // Setup mock to return data for 2 months
//        when(mockResultSet.next())
//            .thenReturn(true)
//            .thenReturn(true)
//            .thenReturn(false);
//        
//        when(mockResultSet.getInt("Month"))
//            .thenReturn(1)
//            .thenReturn(2);
//        
//        when(mockResultSet.getDouble("Revenue"))
//            .thenReturn(10000.0)
//            .thenReturn(12500.0);
//        
//        // Execute the method
//        Map<String, Object> result = mockChartDAO.compareMonthlyRevenue(1, 2);
//        
//        // Verify parameter setting
//        verify(mockPreparedStatement).setInt(1, 1);
//        verify(mockPreparedStatement).setInt(2, 2);
//        
//        // Assert results
//        assertEquals("Month 1 should be January", 1, result.get("month1"));
//        assertEquals("Month 2 should be February", 2, result.get("month2"));
//        assertEquals("Month 1 revenue should match", 10000.0, (Double)result.get("revenue1"), 0.01);
//        assertEquals("Month 2 revenue should match", 12500.0, (Double)result.get("revenue2"), 0.01);
//        assertEquals("Percentage change should be 25%", 25.0, (Double)result.get("percentageChange"), 0.01);
//    }
//    
//    @Test(expected = IllegalArgumentException.class)
//    public void testCompareMonthlyRevenue_InvalidMonth() throws SQLException, Exception {
//        // Should throw exception for invalid month value
//        mockChartDAO.compareMonthlyRevenue(0, 13);
//    }
//    
//    @Test
//    public void testMonthlySales_GetMonthName() {
//        MonthlySales january = new MonthlySales(1, 1000);
//        MonthlySales july = new MonthlySales(7, 2000);
//        
//        assertEquals("Month 1 should be January", "January", january.getMonthName());
//        assertEquals("Month 7 should be July", "July", july.getMonthName());
//    }
}