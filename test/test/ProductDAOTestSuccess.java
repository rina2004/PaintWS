/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
/**
 *
 * @author A A
 */
package test;

import dal.ProductDAO;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProductDAOTestSuccess {
    private ProductDAO productDAO;

    @BeforeClass
    public void setup() {
        productDAO = new ProductDAO();
    }

    @Test
    public void testInsertProduct_Success() {
        boolean inserted = productDAO.insertProduct("Sơn Test ABC", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertTrue(inserted, "Product should be inserted successfully");
    }

}
