/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
/**
 *
 * @author DUCDA
 */
package controler;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class AddUserControlNGTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Khởi tạo WebDriver và thiết lập thời gian chờ
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        // Điều hướng đến trang đăng nhập
        driver.get("http://localhost:9999/PaintWS/Login.jsp");

        // Đăng nhập với tài khoản admin
        driver.findElement(By.id("inputEmail")).sendKeys("john_doe");
        driver.findElement(By.id("inputPassword")).sendKeys("password123");
        driver.findElement(By.id("button")).click();

        // Điều hướng đến trang quản lý tài khoản
        driver.get("http://localhost:9999/PaintWS/manageracc");

        
    }

    @Test
    public void AddUser_1() throws InterruptedException {
        // Mở pop-up thêm người dùng
        driver.findElement(By.cssSelector("a[href='#addUserModal']")).click();
        Thread.sleep(1000);

        // Nhập thông tin với username đã tồn tại
        driver.findElement(By.name("username")).sendKeys("jane_smith");
        driver.findElement(By.name("password")).sendKeys("P@ssw0rd");
        driver.findElement(By.name("address")).sendKeys("testAddress");
        driver.findElement(By.name("phone")).sendKeys("0123456789");
        driver.findElement(By.name("email")).sendKeys("test@example.com");

        // Chọn vai trò
        driver.findElement(By.name("roleID")).sendKeys("2");

        // Gửi form
        driver.findElement(By.id("addButton")).click();

        // Kiểm tra thông báo lỗi
        Thread.sleep(1000);

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(text(), 'Không thể thể thêm người dùng này vì đã trùng username hoặc email hoặc phone.')]"));
        assertNotNull(errorMessage, "Thông báo lỗi không được hiển thị!");
        assertTrue(errorMessage.getText().contains("Không thể thể thêm người dùng này vì đã trùng username hoặc email hoặc phone."), "Thông báo lỗi không đúng!");
    }

    @Test
    public void AddUser_2() throws InterruptedException {
        

        // Mở pop-up thêm người dùng
        driver.findElement(By.cssSelector("a[href='#addUserModal']")).click();
        Thread.sleep(1000);

        // Nhập thông tin với username chứa ký tự đặc biệt
        driver.findElement(By.name("username")).sendKeys("user@name!");
        driver.findElement(By.name("password")).sendKeys("P@ssw0rd");
        driver.findElement(By.name("address")).sendKeys("testAddress");
        driver.findElement(By.name("phone")).sendKeys("0123456789");
        driver.findElement(By.name("email")).sendKeys("test@example.com");

        // Chọn vai trò
        driver.findElement(By.name("roleID")).sendKeys("2");

        // Gửi form
        driver.findElement(By.id("addButton")).click();

        // Kiểm tra thông báo lỗi
        Thread.sleep(1000);

        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(text(), 'Không thể thể thêm người dùng này vì đã trùng username hoặc email hoặc phone.')]"));
        assertNotNull(errorMessage, "Thông báo lỗi không được hiển thị!");
        assertTrue(errorMessage.getText().contains("Không thể thể thêm người dùng này vì đã trùng username hoặc email hoặc phone."), "Thông báo lỗi không đúng!");
    }

    @Test
    public void AddUser_3() throws InterruptedException {
        // Mở pop-up thêm người dùng
        driver.findElement(By.cssSelector("a[href='#addUserModal']")).click();
        Thread.sleep(1000);
    
        driver.findElement(By.name("password")).sendKeys("P@ssw0rd");
        driver.findElement(By.name("address")).sendKeys("789 Street");
        driver.findElement(By.name("phone")).sendKeys("0987654323");
        driver.findElement(By.name("email")).sendKeys("user03@gmail.com");

        // Gửi form
        driver.findElement(By.id("addButton")).click();

        // Kiểm tra thông báo lỗi
        Thread.sleep(1000);
    
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(text(), 'Username is required.')]"));
        assertNotNull(errorMessage, "Thông báo lỗi không được hiển thị!");
        assertTrue(errorMessage.getText().contains("Username is required."), "Thông báo lỗi không đúng!");
    }

    @AfterClass
    public void tearDown() {
        // Đóng trình duyệt
        if (driver != null) {
            driver.quit();
        }
    }
}
