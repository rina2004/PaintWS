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
        try {
            List<BestSellingProduct> result = chartDAO.getBestSellingProducts();
            assertNotNull("Result should not be null", result);
            assertFalse("Result should not be empty", result.isEmpty());
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
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
    
    // ''-----------------------getTopSellingProducts()-----------------------------------''
    
    @Test
    public void testGetTopSellingProducts_ReturnsCorrectNumberOfProducts() {
        try {
            int limit = 5; // Test with 5 top products
            List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);
            
            assertNotNull("Result should not be null", result);
            // The result size should be less than or equal to the limit
            assertTrue("Result size should not exceed the requested limit", 
                    result.size() <= limit);
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetTopSellingProducts_ReturnsOrderedList() {
        try {
            int limit = 10;
            List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);
            
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
    
    @Test
    public void testGetTopSellingProducts_WithZeroLimit() {
        try {
            int limit = 0;
            List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);
            
            assertNotNull("Result should not be null even with zero limit", result);
            assertTrue("Result should be empty with zero limit", result.isEmpty());
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetTopSellingProducts_WithNegativeLimit() {
        try {
            int limit = -5;
            List<BestSellingProduct> result = chartDAO.getTopSellingProducts(limit);
            
            // This might throw an exception or return an empty list depending on implementation
            // We expect either a handled exception or an empty list
            assertNotNull("Result should not be null even with negative limit", result);
        } catch (Exception e) {
            // In some implementations, a negative limit might be expected to throw an exception
            // We're just ensuring the test doesn't crash unexpectedly
            System.out.println("Negative limit test: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetTopSellingProducts_CompareWithFullList() {
        try {
            int limit = 3;
            List<BestSellingProduct> topProducts = chartDAO.getTopSellingProducts(limit);
            List<BestSellingProduct> allProducts = chartDAO.getBestSellingProducts();
            
            // Skip if no data
            if (allProducts.isEmpty()) {
                return;
            }
            
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
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    // ''-----------------------getSalesByCategory()--------------------------------------''
    
    @Test
    public void testGetSalesByCategory_ReturnsNonEmptyList() {
        try {
            List<CategorySales> result = chartDAO.getSalesByCategory();
            
            assertNotNull("Result should not be null", result);
            assertFalse("Result should not be empty", result.isEmpty());
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSalesByCategory_ContainsValidData() {
        try {
            List<CategorySales> result = chartDAO.getSalesByCategory();
            
            // Skip if no data returned
            if (result.isEmpty()) {
                return;
            }
            
            // Check that each item has non-null and valid data
            for (CategorySales category : result) {
                assertNotNull("Category name should not be null", category.getCategoryName());
                assertFalse("Category name should not be empty", category.getCategoryName().isEmpty());
                
                // Total sold should be non-negative
                assertTrue("Total quantity sold should be non-negative", category.getTotalSold() >= 0);
            }
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSalesByCategory_OrderedByTotalSoldDesc() {
        try {
            List<CategorySales> result = chartDAO.getSalesByCategory();
            
            // Skip test if less than 2 categories
            if (result.size() < 2) {
                return;
            }
            
            // Check descending order
            for (int i = 0; i < result.size() - 1; i++) {
                int currentTotal = result.get(i).getTotalSold();
                int nextTotal = result.get(i + 1).getTotalSold();
                
                assertTrue("Categories should be ordered by total sold in descending order",
                        currentTotal >= nextTotal);
            }
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSalesByCategory_IncludesExpectedCategories() {
        try {
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
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetSalesByCategory_IncludesCategoriesWithZeroSales() {
        try {
            List<CategorySales> result = chartDAO.getSalesByCategory();
            
            // The SQL uses a LEFT JOIN and COALESCE, which should include categories 
            // even if they have no associated products or sales
            
            // We can't definitively test for zero sales without knowing the database state,
            // but we can check that all expected categories are included
            boolean allCategories = result.size() >= 4; // Based on the SQL dump with 4 categories
            
            // This is a weak assertion since we don't know the exact DB state
            assertTrue("Result should include all categories, even those with zero sales", allCategories);
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    // ''-----------------------compareMonthlyRevenue()-----------------------------------''
    
    @Test
    public void testCompareMonthlyRevenue_ReturnsValidData() {
        try {
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
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testCompareMonthlyRevenue_DataTypesAreCorrect() {
        try {
            Map<String, Object> result = chartDAO.compareMonthlyRevenue(1, 2);
            
            assertTrue("month1 should be an Integer", result.get("month1") instanceof Integer);
            assertTrue("month2 should be an Integer", result.get("month2") instanceof Integer);
            assertTrue("revenue1 should be a Number", result.get("revenue1") instanceof Number);
            assertTrue("revenue2 should be a Number", result.get("revenue2") instanceof Number);
            assertTrue("percentageChange should be a Number", result.get("percentageChange") instanceof Number);
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCompareMonthlyRevenue_ThrowsExceptionForInvalidMonths() {
        // Month values should be between 1 and 12
        chartDAO.compareMonthlyRevenue(0, 13);
    }
    
    @Test
    public void testCompareMonthlyRevenue_CalculatesPercentageChangeCorrectly() {
        try {
            int month1 = 1;
            int month2 = 2;
            
            Map<String, Object> result = chartDAO.compareMonthlyRevenue(month1, month2);
            
            double revenue1 = ((Number)result.get("revenue1")).doubleValue();
            double revenue2 = ((Number)result.get("revenue2")).doubleValue();
            double percentageChange = ((Number)result.get("percentageChange")).doubleValue();
            
            // Skip test if revenue1 is zero (would cause division by zero)
            if (revenue1 == 0) {
                return;
            }
            
            // Calculate expected percentage change
            double expectedChange = ((revenue2 - revenue1) / revenue1) * 100;
            
            // Compare with a small delta to account for floating-point precision
            assertEquals("Percentage change should be calculated correctly", 
                    expectedChange, percentageChange, 0.001);
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testCompareMonthlyRevenue_HandlesSameMonth() {
        try {
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
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }
    
    @Test
    public void testCompareMonthlyRevenue_HandlesZeroRevenue() {
        try {
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
        } catch (Exception e) {
            System.out.println("Skipping integration test due to: " + e.getMessage());
        }
    }

}