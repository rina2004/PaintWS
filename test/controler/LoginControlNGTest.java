/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package controler;

import java.time.Duration;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class LoginControlNGTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    public void setup() {
        driver.set(new ChromeDriver()); // ✅ Mỗi test có WebDriver riêng biệt
        getDriver().manage().window().maximize();
        getDriver().get("http://localhost:9999/PaintWS/Login.jsp");
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean expectedSuccess) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputEmail")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));

            emailField.clear();
            emailField.sendKeys(username);
            passwordField.clear();
            passwordField.sendKeys(password);

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
            loginButton.click();

            if (expectedSuccess) {
                boolean loggedIn = wait.until(ExpectedConditions.urlContains("home"));
                System.out.println("Test đăng nhập đúng: " + (loggedIn ? "THÀNH CÔNG" : "THẤT BẠI"));
            } else {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorMessage")));
                System.out.println("Test đăng nhập sai (" + username + " / " + password + "): " + (errorMessage.isDisplayed() ? "BÁO LỖI OK" : "KHÔNG CÓ LỖI??"));
            }
        } catch (TimeoutException e) {
            System.out.println("Timeout khi tìm phần tử: " + e.getMessage());
        }
    }

    @DataProvider(name = "loginData", parallel = true)
    public Object[][] loginData() {
        return new Object[][]{
            {"john_doe", "password123", true},
            {"john", "password123", false},
            {"john_doe", "pass", false},
            {"", "password123", false},
            {"john_doe", "", false},
        };
    }

    @AfterMethod
    public void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); 
        }
    }

    public WebDriver getDriver() {
        return driver.get(); 
    }
}
