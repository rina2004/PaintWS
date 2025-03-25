/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */

package controler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class CategoryControlNGTest  {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Điều hướng đến trang đăng nhập
        driver.get("http://localhost:9999/PaintWS/Login.jsp");

        // Đăng nhập với tài khoản admin
        driver.findElement(By.id("inputEmail")).sendKeys("john_doe");
        driver.findElement(By.id("inputPassword")).sendKeys("password123");
        driver.findElement(By.className("btn-success")).click();

        // Chờ load trang sau đăng nhập
        wait.until(ExpectedConditions.urlContains("PaintWS"));
    }

    @Test
    public void testCreateCategorySuccess() {
        driver.get("http://localhost:9999/PaintWS/loadCategory");
        boolean result = createCategory("Sơn nội thất 4.0", "Sơn nội thất loại mới nhất");
        Assert.assertTrue(result, "Category added successfully.");
    }

    @Test
    public void testCreateCategoryWithEmptyName() {
        driver.get("http://localhost:9999/PaintWS/loadCategory");
        boolean result = createCategory("", "Sơn nội thất");
        Assert.assertFalse(result, "Category name cannot be empty.");
    }

    @Test
    public void testCreateCategoryWithNoDescription() {
        driver.get("http://localhost:9999/PaintWS/loadCategory");
        boolean result = createCategory("Sơn B", "");
        Assert.assertTrue(result, "Category added successfully.");
    }

    @Test
    public void testCreateCategoryWithLongName() {
        driver.get("http://localhost:9999/PaintWS/loadCategory");
        boolean result = createCategory("X8mNp2QzVrLb6YgJwT0KCfHdZoMAqPuREtW5s94BnD1xLcvF7Gh3ySV", "Sơn mới");
        Assert.assertFalse(result, "Category names cannot exceed 50 characters.");
    }

    @Test
    public void testCreateCategoryWithMaxLengthName() {
        driver.get("http://localhost:9999/PaintWS/loadCategory");
        boolean result = createCategory("x7GmZ2pQdL9vJrBtK5WYfXNH8CqMlVAoE3sT6Z1cRDubP1", "Sơn mới");
        Assert.assertTrue(result, "Category added successfully.");
    }

    private boolean createCategory(String name, String description) {
        try {
            // Mở modal "Add Category"
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='#addCategoryModal']"))).click();

            // Nhập dữ liệu
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).clear();
            driver.findElement(By.name("name")).sendKeys(name);
            driver.findElement(By.name("describe")).clear();
            driver.findElement(By.name("describe")).sendKeys(description);

            // Nhấn nút "Add"
            driver.findElement(By.id("AddCategory")).click();

            // Kiểm tra có hiển thị thông báo lỗi không
            try {
                WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
                System.out.println("Display error: " + alert.getText());
                return false; // Có lỗi -> test fail
            } catch (Exception e) {
                System.out.println("No errors - category added successfully!");
                return true; // Không có lỗi -> test pass
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
