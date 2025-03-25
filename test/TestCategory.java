/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class TestCategory {

    private WebDriver driver;
    
    
    //private static final String BASE_URL = "http://localhost:8080/PaintWS/loadCategory";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Cập nhật đường dẫn phù hợp
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testAddCategorySuccess() {
        driver.get("http://localhost:8080/PaintWS/loadCategory");

        // Điền thông tin danh mục
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement descriptionField = driver.findElement(By.name("describe"));
        WebElement submitButton = driver.findElement(By.id("submitCategory"));

        nameField.sendKeys("Sơn Nội Thất 2.0");
        descriptionField.sendKeys("Loại sơn chuyên dùng cho nội thất");
        submitButton.click();

        // Kiểm tra thông báo thành công
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        assertTrue(successMessage.isDisplayed());
        assertEquals("Thêm danh mục thành công!", successMessage.getText());
    }

    @Test
    public void testAddCategoryEmptyName() {
        driver.get("http://localhost:8080/PaintWS/loadCategory");

        WebElement nameField = driver.findElement(By.name("name"));
        WebElement descriptionField = driver.findElement(By.name("describe"));
        WebElement submitButton = driver.findElement(By.id("submitCategory"));

        nameField.sendKeys(""); // Trường name rỗng
        descriptionField.sendKeys("Mô tả danh mục");
        submitButton.click();

        // Kiểm tra thông báo lỗi
        WebElement errorMessage = driver.findElement(By.id("errorMessage"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Tên danh mục không được để trống", errorMessage.getText());
    }

    @Test
    public void testAddCategoryLongName() {
        driver.get("http://localhost:8080/PaintWS/loadCategory");

        WebElement nameField = driver.findElement(By.name("name"));
        WebElement descriptionField = driver.findElement(By.name("describe"));
        WebElement submitButton = driver.findElement(By.id("submitCategory"));

        nameField.sendKeys("Tên danh mục này rất dài và vượt quá 50 ký tự của hệ thống giới hạn");
        descriptionField.sendKeys("Mô tả danh mục");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.id("errorMessage"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Tên danh mục không được vượt quá 50 ký tự", errorMessage.getText());
    }

    @Test
    public void testAddCategoryDuplicateName() {
        driver.get("http://localhost:8080/PaintWS/loadCategory");

        WebElement nameField = driver.findElement(By.name("name"));
        WebElement descriptionField = driver.findElement(By.name("describe"));
        WebElement submitButton = driver.findElement(By.id("submitCategory"));

        nameField.sendKeys("Sơn Ngoại Thất"); // Giả sử danh mục này đã tồn tại
        descriptionField.sendKeys("Mô tả danh mục");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.id("errorMessage"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Tên danh mục không được trùng", errorMessage.getText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
