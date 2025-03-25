/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
/**
 *
 * @author A A
 */
package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class InsertProductUITest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
    @Test
    public void testInsertProductSuccess() throws InterruptedException {
        driver.get("http://localhost:20020/PaintWS/home");
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in")));
        signInBtn.click();

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("user")));
        usernameField.clear();
        usernameField.sendKeys("john_doe");

        WebElement passwordField = driver.findElement(By.name("pass"));
        passwordField.clear();
        passwordField.sendKeys("password123");

        // sign in
        signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        signInBtn.click();
        wait.until(ExpectedConditions.urlContains("/home"));

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();

        wait.until(ExpectedConditions.urlContains("/manager"));
        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();

        // fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        driver.findElement(By.name("price")).sendKeys("50000");
        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        driver.findElement(By.name("quantitySold")).sendKeys("0");
        driver.findElement(By.name("volume")).sendKeys("2");
        driver.findElement(By.name("color")).sendKeys("Red");
        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        driver.findElement(By.name("description")).sendKeys("Test product");
        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        addButton.click();

        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Add successfully!"));
    }
    @Test(dependsOnMethods = "testInsertProductSuccess")
    public void testInsertProduct_DuplicateName() throws InterruptedException {
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");
        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();

        wait.until(ExpectedConditions.urlContains("/manager"));
        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        driver.findElement(By.name("price")).sendKeys("50000");
        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        driver.findElement(By.name("quantitySold")).sendKeys("0");
        driver.findElement(By.name("volume")).sendKeys("2");
        driver.findElement(By.name("color")).sendKeys("Red");
        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        driver.findElement(By.name("description")).sendKeys("Test product");
        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        addButton.click();

        boolean isAlertPresent = !driver.findElements(By.className("alert-info")).isEmpty();
        Assert.assertFalse(isAlertPresent, "No success message should appear when inserting a duplicate product.");
    }
    @Test(dependsOnMethods = "testInsertProduct_DuplicateName")
    public void testInsertProduct_EmptyName() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Product name cannot be empty!"));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyName")
    public void testInsertProduct_NoSonName() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Ema Test Nhes");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Product name must contain the word 'Sơn'!"));
    }
    @Test(dependsOnMethods = "testInsertProduct_NoSonName")
    public void testInsertProduct_ShortName() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn fa");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Product name must be at least 10 characters long!"));
    }
    @Test(dependsOnMethods = "testInsertProduct_ShortName")
    public void testInsertProduct_InvalidName() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn a31@432");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Product name can only contain letters."));
    }
    @Test(dependsOnMethods = "testInsertProduct_ShortName")
    public void testInsertProduct_ExceededName() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test Nhesfdsafasdfqwereeewwwwwwwwwwwwwwwwwwwwwwwwwwwwwasdffffffffffqqqlewrmlqwkermlkqweflkqjlkfqwejlkfjqwlekfjqowiejfoiwqejiofqwiofqwejfiowqejifdsaf");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Product name cannot exceed 100 characters!"));
    }
    @Test(dependsOnMethods = "testInsertProduct_ExceededName")
    public void testInsertProduct_EmptyImage() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Image link cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyImage")
    public void testInsertProduct_InvalidImage() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("a");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Image link must be a valid URL."));
    }
    @Test(dependsOnMethods = "testInsertProduct_InvalidImage")
    public void testInsertProduct_EmptyPrice() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Unit price cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyPrice")
    public void testInsertProduct_NegativePrice() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("-1");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Unit price must be greater than zero."));
    }
    @Test(dependsOnMethods = "testInsertProduct_NegativePrice")
    public void testInsertProduct_ExceededPrice() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("100000001");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Unit Price cannot exceed 100,000,000."));
    }
    @Test(dependsOnMethods = "testInsertProduct_ExceededPrice")
    public void testInsertProduct_EmptyStock() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Units in stock cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyStock")
    public void testInsertProduct_NegativeStock() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("-1");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Units in stock cannot be negative."));
    }
    @Test(dependsOnMethods = "testInsertProduct_NegativeStock")
    public void testInsertProduct_ExceededStock() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("1001");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Units In Stock cannot exceed 1,000."));
    }
    @Test(dependsOnMethods = "testInsertProduct_ExceededStock")
    public void testInsertProduct_EmptySold() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Quantity sold cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptySold")
    public void testInsertProduct_NegativeSold() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("-1");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Quantity sold cannot be negative."));
    }
    @Test(dependsOnMethods = "testInsertProduct_NegativeSold")
    public void testInsertProduct_ExceededSold() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("1001");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Quantity Sold cannot exceed 1,000."));
    }
    @Test(dependsOnMethods = "testInsertProduct_ExceededSold")
    public void testInsertProduct_EmptyVolume() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Volume cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyVolume")
    public void testInsertProduct_NegativeVolume() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("-1");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Volume must be greater than zero."));
    }
    @Test(dependsOnMethods = "testInsertProduct_NegativeVolume")
    public void testInsertProduct_ExceededVolume() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("101");
        

        driver.findElement(By.name("color")).sendKeys("Red");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Volume cannot exceed 100."));
    }
    @Test(dependsOnMethods = "testInsertProduct_ExceededVolume")
    public void testInsertProduct_EmptyColor() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Color cannot be empty."));
    }
    @Test(dependsOnMethods = "testInsertProduct_EmptyColor")
    public void testInsertProduct_InvalidColor() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Supae@%kfdao");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Color can only contain letters."));
    }
    @Test(dependsOnMethods = "testInsertProduct_InvalidColor")
    public void testInsertProduct_ExceededColor() throws InterruptedException {
        //1
        Thread.sleep(2000);
        driver.get("http://localhost:20020/PaintWS/home");

        WebElement adminButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("adminDropdown")));
        adminButton.click();
        

        WebElement manageProduct = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Manage Product")));
        manageProduct.click();
        

        wait.until(ExpectedConditions.urlContains("/manager"));
        

        WebElement addNewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-toggle='modal']")));
        addNewBtn.click();
        

        // Fill
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Sơn Test UI");
        

        driver.findElement(By.name("image")).sendKeys("https://example.com/image.jpg");
        

        driver.findElement(By.name("price")).sendKeys("50000");
        

        driver.findElement(By.name("unitsInStock")).sendKeys("10");
        

        driver.findElement(By.name("quantitySold")).sendKeys("0");
        

        driver.findElement(By.name("volume")).sendKeys("2");
        

        driver.findElement(By.name("color")).sendKeys("Redddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        

        Select supplierDropdown = new Select(driver.findElement(By.name("supplierID")));
        supplierDropdown.selectByIndex(1);
        

        driver.findElement(By.name("description")).sendKeys("Test product");
        

        Select categoryDropdown = new Select(driver.findElement(By.name("category")));
        categoryDropdown.selectByIndex(1);
        

        Select statusDropdown = new Select(driver.findElement(By.name("status")));
        statusDropdown.selectByValue("1");
        

        // add
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));
        
        addButton.click();
        
        // wait
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assert.assertTrue(message.getText().contains("Color cannot exceed 50 characters."));
    }
}
