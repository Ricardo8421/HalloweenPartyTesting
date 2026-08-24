package testcases.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite
    public void setUp() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private");
        WebDriver driver = WebDriverManager.firefoxdriver().create();
        driver.get("https://candymapper.com/");
        this.driver.set(driver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    @AfterSuite
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
