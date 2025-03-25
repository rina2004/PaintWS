/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package func;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 *
 * @author Rinaaaa
 */
public class RegisterAutomationTest {

    private final String baseUrl = "http://localhost:9999/PaintWS/register";
    private WebDriver edgeDriver;
    private WebDriverWait wait;
    
    /*------------------------------------------------------------------------*/
    /*--------------------------------Initiate--------------------------------*/
    /*------------------------------------------------------------------------*/

    @BeforeClass
    public void Setup() {
        String driverPath = "C:\\edgedriver_win64\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", driverPath);
        edgeDriver = new EdgeDriver();
        edgeDriver.manage().window().maximize();
        wait = new WebDriverWait(edgeDriver, Duration.ofSeconds(20));
    }

    @AfterClass
    public void CleanUp() throws InterruptedException {
        edgeDriver.quit();
    }
    
    /*------------------------------------------------------------------------*/
    /*----------------------------Check validation----------------------------*/
    /*------------------------------------------------------------------------*/

    @Test(priority = 1, description = "Empty username")
    public void testCase1() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys("");
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());
        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Username is required."));
    }

    @Test(priority = 2, description = "Username too short")
    public void testCase2() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys("john");
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Username must be at least 5 characters long."));
    }

    @Test(priority = 3, description = "Username with special characters")
    public void testCase3() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys("john@doe");
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Username can only contain letters, numbers, and underscores."));
    }

    @Test(priority = 4, description = "Username already exists")
    public void testCase4() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys("john_doe");
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Username is already taken."));
    }

    @Test(priority = 5, description = "Empty password & confirm password")
    public void testCase5() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-password")));
        Assert.assertTrue(message1.getText().equals("Password is required."));
        
        WebElement message2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-comfirmpassword")));
        Assert.assertTrue(message2.getText().equals("Confirm password is required."));
    }

    @Test(priority = 6, description = "Empty password but have confirm password")
    public void testCase6() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-password")));
        Assert.assertTrue(message1.getText().equals("Password is required."));
        
        WebElement message2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-comfirmpassword")));
        Assert.assertTrue(message2.getText().equals("Confirm password do not match."));
    }
    
    @Test(priority = 7, description = "Have password but empty confirm password")
    public void testCase7() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-comfirmpassword")));
        Assert.assertTrue(message.getText().equals("Confirm password is required."));
    }
    
    @Test(priority = 8, description = "Have password & confirm password but confirm password do not match")
    public void testCase8() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-comfirmpassword")));
        Assert.assertTrue(message.getText().equals("Confirm password do not match."));
    }
    
    @Test(priority = 9, description = "Password too short")
    public void testCase9() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Pass1!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Pass1!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-password")));
        Assert.assertTrue(message.getText().equals("Password must be at least 8 characters long."));
    }
    
    @Test(priority = 10, description = "Password without number")
    public void testCase10() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-password")));
        Assert.assertTrue(message.getText().equals("Password must contain at least one number."));
    }
    
    @Test(priority = 11, description = "Password without letter")
    public void testCase11() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("12345678");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("12345678");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-password")));
        Assert.assertTrue(message.getText().equals("Password must contain at least one letter."));
    }
    
    @Test(priority = 12, description = "Empty full name")
    public void testCase12() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys("");
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Full name is required."));
    }
    
    @Test(priority = 13, description = "Full name with number")
    public void testCase13() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys("John 23");
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Full name should only contain letters."));
    }
    
    @Test(priority = 14, description = "Full name with special characters")
    public void testCase14() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys("John -Doe");
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Full name should only contain letters."));
    }
    
    @Test(priority = 15, description = "Full name too short")
    public void testCase15() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys("John");
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Please enter your complete full name."));
    }
    
    @Test(priority = 16, description = "Empty email adrress")
    public void testCase16() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys("");

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Email address is required."));
    }
    
    @Test(priority = 17, description = "Invalid email address format")
    public void testCase17() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys("john.example.com");

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Please enter a valid email address."));
    }
    
    @Test(priority = 18, description = "Email address already registered")
    public void testCase18() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys("john@example.com");

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Email address is already registered."));
    }
    
    @Test(priority = 19, description = "Empty phone number")
    public void testCase19() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys("");

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Phone number is required."));
    }
    
    @Test(priority = 20, description = "Invalid phone number format")
    public void testCase20() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys("123abc124");

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Please enter a valid phone number for Vietnam."));
    }
    
    @Test(priority = 21, description = "Phone number already registered")
    public void testCase21() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys("123456789");

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("Phone number is already registered."));
    }
    
    @Test(priority = 22, description = "Terms of service not checked")
    public void testCase22() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123!");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");

        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();
        
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-message")));
        Assert.assertTrue(message.getText().equals("You must agree to the terms of service to create an account."));
    }
    
    /*------------------------------------------------------------------------*/
    /*------------------------------Check Business----------------------------*/
    /*------------------------------------------------------------------------*/
    
    @Test(priority = 23, description = "Successful registration")
    public void testCase23() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");
        
        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();
        acceptMarketing.click();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("alert alert-success")));
        Assert.assertTrue(message.getText().equals("Registration successful! You can now login."));
    }

    @Test(priority = 24, description = "Country change updates phone prefix")
    public void testCase24() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("France");

        WebElement phonePrefix = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone-prefix")));
        Assert.assertTrue(phonePrefix.getText().equals("+33"));
    }
    
    @Test(priority = 25, description = "Marketing email checkbox optional")
    public void testCase25() throws InterruptedException {
        edgeDriver.get(baseUrl);
        wait.until(ExpectedConditions.urlContains(baseUrl));

        edgeDriver.findElement(By.name("username")).sendKeys(TestDataGenerator.genRandomUsername());
        edgeDriver.findElement(By.name("password")).sendKeys("Password123");
        edgeDriver.findElement(By.name("confirmPassword")).sendKeys("Password123");
        edgeDriver.findElement(By.name("fullname")).sendKeys(TestDataGenerator.genRandomFullname());
        edgeDriver.findElement(By.name("email")).sendKeys(TestDataGenerator.genRandomEmail());

        WebElement dropdownElement = edgeDriver.findElement(By.name("country"));
        new Actions(edgeDriver).scrollToElement(dropdownElement).perform();
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("Vietnam");
        
        edgeDriver.findElement(By.name("phoneNumber")).sendKeys(TestDataGenerator.genRandomPhoneNumber());

        WebElement acceptTerms = edgeDriver.findElement(By.name("acceptTerms"));
        new Actions(edgeDriver).scrollToElement(acceptTerms).perform();
        acceptTerms.click();

        WebElement acceptMarketing = edgeDriver.findElement(By.name("acceptMarketing"));
        new Actions(edgeDriver).scrollToElement(acceptMarketing).perform();

        WebElement regBtn = edgeDriver.findElement(By.cssSelector("button[type='submit']"));
        new Actions(edgeDriver).scrollToElement(regBtn).perform();
        regBtn.click();

        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("alert alert-success")));
        Assert.assertTrue(message.getText().equals("Registration successful! You can now login."));
    }
}