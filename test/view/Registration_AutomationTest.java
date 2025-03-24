/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert.*;
import org.testng.annotations.*;

/**
 *
 * @author Rinaaaa
 */
public class Registration_AutomationTest {
    
    EdgeDriver edgeDriver;
    
    @BeforeMethod
    public void Setup() {
        WebDriverManager.edgedriver().setup();
        edgeDriver = new EdgeDriver();
    }
    
    @Test
    public void Run() {
        System.out.println("Hello TestNG");
        edgeDriver.get("https://google.com.vn/");
        
        sleep(10000);
    }
    
    @AfterMethod
    public void CleanUp() {
        System.out.println("After method");
        edgeDriver.quit();
    }
    
    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
