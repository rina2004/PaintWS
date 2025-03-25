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
import org.testng.annotations.Test;

public class ProductDAOTestFail {

    @Test
    public void testDuplicateProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Test ABC", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Duplicate product name should not be allowed");
    }
    @Test
    public void testEmptyProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Product name should not be empty");
    }
    @Test
    public void testNoContainSonProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Ema Test Nhes", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Product name should contain 'Son'");
    }
    @Test
    public void testShortProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn fa", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Product name length should not less than 10");
    }
    @Test
    public void testInvalidProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn a31@432", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Product name length should not invalid");
    }
    @Test
    public void testTooLongProductName() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Test Nhesfdsafasdfqwereeewwwwwwwwwwwwwwwwwwwwwwwwwwwwwasdffffffffffqqqlewrmlqwkermlkqweflkqjlkfqwejlkfjqwlekfjqowiejfoiwqejiofqwiofqwejfiowqejifdsaf", "https://example.com/image.jpg", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Product name length should not more than 100");
    }
    @Test
    public void testEmptyImage() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Image should not be empty");
    }
    @Test
    public void testInvalidImage() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "asfd", "50000", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Image should not be invalid");
    }
    @Test
    public void testEmptyPrice() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Price should not be empty");
    }
    @Test
    public void testNegativePrice() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "-1", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Price should not be negative");
    }
    @Test
    public void testExceededPrice() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "100000001", "10", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Price should not be more than 100000000");
    }
    @Test
    public void testEmptyStock() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Stock should not be empty");
    }
    @Test
    public void testNegativeStock() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "-1", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Stock should not be negative");
    }
    @Test
    public void testExceededStock() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "1001", "0", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Stock should not be more than 1000");
    }
    @Test
    public void testEmptySold() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Sold should not be empty");
    }
    @Test
    public void testNegativeSold() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "-1", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Sold should not be negative");
    }
    @Test
    public void testExceededSold() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "1001", "2", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Sold should not be more than 1000");
    }
    @Test
    public void testEmptyVolume() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Volume should not be empty");
    }
    @Test
    public void testNegativeVolume() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "-1", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Volume should not be negative");
    }
    @Test
    public void testExceededVolume() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "101", "Red", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Volume should not be more than 100");
    }
    @Test
    public void testEmptyColor() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "2", "", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Color should not be empty");
    }
    @Test
    public void testExceededColor() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "2", "Redddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Color length should not be more than 50");
    }
    @Test
    public void testInvalidColor() {
        ProductDAO pd = new ProductDAO();
        boolean inserted = pd.insertProduct("Sơn Siêu Cấp Vip", "https://example.com/image.jpg", "50000", "10", "0", "2", "Supae@%kfdao", "1", "Test product", "1", "1");
        Assert.assertNotEquals(inserted, true, "Image should not contain special characters ");
    }
}
