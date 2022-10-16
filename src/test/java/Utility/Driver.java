package Utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver;

    private static Actions actions;

    private static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void quitDriver() {
        driver.quit();
    }

    public static WebDriverWait getWait() {
        if (wait == null)
            wait = new WebDriverWait(getDriver() , 15);
        return wait;
    }

    public static Actions getActions() {
        if (actions == null)
            actions = new Actions(driver);
        return actions;
    }

}