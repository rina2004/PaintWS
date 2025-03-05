package com.paintws.chartdao.test.core;

import dal.ChartDAO;
import model.*;
import java.sql.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author A A
 */
public class ChartDAOTest {
    private static ChartDAO chartDAO;

    @Before
    public void setUp() throws SQLException, Exception {
        chartDAO = new ChartDAO();
    }
    
    @After
    public void tearDown() {
        chartDAO = null;
    }
    // ''-----------------------getBestSellingProducts()-----------------------------------''
    @Test
    public void testGetBestSellingProducts_ReturnsNonEmptyList() {
        // Skip if integration tests cannot be run
        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
    }
    
    @Test
    public void testGetBestSellingProducts_ContainsValidData() {
        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
        if (result.isEmpty()) return;
        // Check that each item has non-null and valid data
        for (BestSellingProduct product : result) {
            assertNotNull("Product name should not be null", product.getProductName());
            assertFalse("Product name should not be empty", product.getProductName().isEmpty());
            assertTrue("Quantity sold should be non-negative", product.getTotalQuantitySold() >= 0);
        }
    }
    
    //Ordering Test
    @Test
    public void testGetBestSellingProducts_OrderedByQuantityDesc() {
        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
        if (result.size() < 2) return;

        // Check descending order
        for (int i = 0; i < result.size() - 1; i++) {
            int currentQuantity = result.get(i).getTotalQuantitySold();
            int nextQuantity = result.get(i + 1).getTotalQuantitySold();
            assertTrue("Products should be ordered by quantity sold in descending order",
                currentQuantity >= nextQuantity);
        }
    }
    
    //Expected Data Test
    @Test
    public void testGetBestSellingProducts_MatchesExpectedData() {
        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
        boolean foundExpectedProduct = false;

        for (BestSellingProduct product : result) {
            if (product.getProductName().contains("Sơn")) {
                foundExpectedProduct = true;
                break;
            }
        }
        assertTrue("Result should contain at least one expected product", foundExpectedProduct);
    }
    
    //Error Handling Test
    @Test
    public void testGetBestSellingProducts_HandlesEmptyDatabase() {
        List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
        assertNotNull("Method should return a list (possibly empty) even with no data", result);
    }
    
    // ''-----------------------getTopSellingProducts()-----------------------------------''
    
    @Test
    public void testGetTopSellingProducts_ReturnsCorrectNumberOfProducts() {
        int limit = 5; // Test with 5 top products
        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);

        assertNotNull("Result should not be null", result);
        // The result size should be less than or equal to the limit
        assertTrue("Result size should not exceed the requested limit", 
                result.size() <= limit);
    }
    
    @Test
    public void testGetTopSellingProducts_ReturnsOrderedList() {
        int limit = 10;
        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);
        if (result.size() < 2) return;
        for (int i = 0; i < result.size() - 1; i++) {
            int currentQuantity = result.get(i).getTotalQuantitySold();
            int nextQuantity = result.get(i + 1).getTotalQuantitySold();

            assertTrue("Products should be ordered by quantity sold in descending order", currentQuantity >= nextQuantity);
        }
    }
    
    @Test
    public void testGetTopSellingProducts_WithZeroLimit() {
        int limit = 0;
        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);

        assertNotNull("Result should not be null even with zero limit", result);
        assertTrue("Result should be empty with zero limit", result.isEmpty());
    }
    
    @Test
    public void testGetTopSellingProducts_WithNegativeLimit() {
        int limit = -5;
        List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);

        // This might throw an exception or return an empty list depending on implementation
        // We expect either a handled exception or an empty list
        assertNotNull("Result should not be null even with negative limit", result);
    }
    
    @Test
    public void testGetTopSellingProducts_CompareWithFullList() {
        int limit = 3;
        List<BestSellingProduct> topProducts = chartDAO.getTopSellingProducts(limit);
        List<BestSellingProduct> allProducts = chartDAO.getBestSellingProducts();

        if (allProducts.isEmpty()) return;

        // Check if top products match the first n products of full list
        int actualLimit = Math.min(limit, allProducts.size());
        for (int i = 0; i < actualLimit; i++) {
            if (i < topProducts.size()) {
                assertEquals("Top product should match corresponding product in full list",
                        allProducts.get(i).getProductName(), 
                        topProducts.get(i).getProductName());
                assertEquals("Quantity sold should match",
                        allProducts.get(i).getTotalQuantitySold(), 
                        topProducts.get(i).getTotalQuantitySold());
            }
        }
    }
    
    // ''-----------------------getSalesByCategory()--------------------------------------''
    
    @Test
    public void testGetSalesByCategory_ReturnsNonEmptyList() {
        List<CategorySales> result = chartDAO.getSalesByCategory();

        assertNotNull("Result should not be null", result);
        assertFalse("Result should not be empty", result.isEmpty());
    }
    
    @Test
    public void testGetSalesByCategory_ContainsValidData() {
        List<CategorySales> result = chartDAO.getSalesByCategory();
        if (result.isEmpty()) return;

        // Check that each item has non-null and valid data
        for (CategorySales category : result) {
            assertNotNull("Category name should not be null", category.getCategoryName());
            assertFalse("Category name should not be empty", category.getCategoryName().isEmpty());
            assertTrue("Total quantity sold should be non-negative", category.getTotalSold() >= 0);
        }
    }
    
    @Test
    public void testGetSalesByCategory_OrderedByTotalSoldDesc() {
        List<CategorySales> result = chartDAO.getSalesByCategory();
        if (result.size() < 2) return;

        // Check descending order
        for (int i = 0; i < result.size() - 1; i++) {
            int currentTotal = result.get(i).getTotalSold();
            int nextTotal = result.get(i + 1).getTotalSold();
            assertTrue("Categories should be ordered by total sold in descending order", currentTotal >= nextTotal);
        }
    }
    
    @Test
    public void testGetSalesByCategory_IncludesExpectedCategories() {
        List<CategorySales> result = chartDAO.getSalesByCategory();
        Set<String> foundCategoryNames = new HashSet<>();

        for (CategorySales category : result) {
            foundCategoryNames.add(category.getCategoryName());
        }

        // Check for some expected categories based on the DB dump
        boolean hasExpectedCategories = foundCategoryNames.contains("Sơn Nội Thất") || 
                                      foundCategoryNames.contains("Sơn Ngoại Thất") ||
                                      foundCategoryNames.contains("Sơn Lót") ||
                                      foundCategoryNames.contains("Sơn Chống Thấm");
        assertTrue("Result should contain at least one of the expected categories", hasExpectedCategories);
    }
    
    @Test
    public void testGetSalesByCategory_IncludesCategoriesWithZeroSales() {
        List<CategorySales> result = chartDAO.getSalesByCategory();

        // The SQL uses a LEFT JOIN and COALESCE, which should include categories 
        // even if they have no associated products or sales
        // We can't definitively test for zero sales without knowing the database state,
        // but we can check that all expected categories are included
        boolean allCategories = result.size() >= 4; // Based on the SQL dump with 4 categories

        // This is a weak assertion since we don't know the exact DB state
        assertTrue("Result should include all categories, even those with zero sales", allCategories);
    }
    
    // ''-----------------------compareMonthlyRevenue()-----------------------------------''
    
    @Test
    public void testCompareMonthlyRevenue_ReturnsValidData() {
        int month1 = 1; // January
        int month2 = 2; // February

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(month1, month2);

        assertNotNull("Result should not be null", result);
        assertTrue("Result should contain month1 key", result.containsKey("month1"));
        assertTrue("Result should contain month2 key", result.containsKey("month2"));
        assertTrue("Result should contain revenue1 key", result.containsKey("revenue1"));
        assertTrue("Result should contain revenue2 key", result.containsKey("revenue2"));
        assertTrue("Result should contain percentageChange key", result.containsKey("percentageChange"));

        assertEquals("month1 should match input value", month1, result.get("month1"));
        assertEquals("month2 should match input value", month2, result.get("month2"));
    }
    
    @Test
    public void testCompareMonthlyRevenue_DataTypesAreCorrect() {
        Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);
        assertTrue("month1 should be an Integer", result.get("month1") instanceof Integer);
        assertTrue("month2 should be an Integer", result.get("month2") instanceof Integer);
        assertTrue("revenue1 should be a Number", result.get("revenue1") instanceof Number);
        assertTrue("revenue2 should be a Number", result.get("revenue2") instanceof Number);
        assertTrue("percentageChange should be a Number", result.get("percentageChange") instanceof Number);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_ThrowsExceptionForInvalidMonths() {
        // Month values should be between 1 and 12
        chartDAO.compareMonthlyRevenue(0, 13);
    }
    
    @Test
    public void testCompareMonthlyRevenue_CalculatesPercentageChangeCorrectly() {
        int month1 = 1;
        int month2 = 2;
        Map<String, Object> result = chartDAO.compareMonthlyRevenue(month1, month2);

        double revenue1 = ((Number)result.get("revenue1")).doubleValue();
        double revenue2 = ((Number)result.get("revenue2")).doubleValue();
        double percentageChange = ((Number)result.get("percentageChange")).doubleValue();

        // Skip test if revenue1 is zero (would cause division by zero)
        if (revenue1 == 0) return;

        // Calculate expected percentage change
        double expectedChange = ((revenue2 - revenue1) / revenue1) * 100;

        // Compare with a small delta to account for floating-point precision
        assertEquals("Percentage change should be calculated correctly", 
                expectedChange, percentageChange, 0.001);
    }
    
    @Test
    public void testCompareMonthlyRevenue_HandlesSameMonth() {
        int month = 1;

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(month, month);

        // If comparing the same month, the revenues should be equal
        // and percentage change should be 0
        double revenue1 = ((Number)result.get("revenue1")).doubleValue();
        double revenue2 = ((Number)result.get("revenue2")).doubleValue();
        double percentageChange = ((Number)result.get("percentageChange")).doubleValue();

        assertEquals("When comparing the same month, both revenues should be equal", 
                revenue1, revenue2, 0.001);
        assertEquals("When comparing the same month, percentage change should be 0", 
                0.0, percentageChange, 0.001);
    }    
    
    @Test
    public void testCompareMonthlyRevenue_HandlesZeroRevenue() {
        // Choose months that might not have orders in the test database
        // For integration tests, we can't be sure, but we can handle the possibility
        int month1 = 11;  // November (assuming test data might not have November orders)
        int month2 = 12;  // December (assuming test data might not have December orders)

        Map<String, Object> result = chartDAO.compareMonthlyRevenue(month1, month2);

        double revenue1 = ((Number)result.get("revenue1")).doubleValue();
        double percentageChange = ((Number)result.get("percentageChange")).doubleValue();

        if (revenue1 == 0) {
            // If revenue1 is 0, percentage change should be 0 to avoid division by zero
            assertEquals("When revenue1 is 0, percentage change should be 0", 
                    0.0, percentageChange, 0.001);
        }
    }
}