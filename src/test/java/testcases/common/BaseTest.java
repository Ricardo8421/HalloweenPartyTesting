package testcases.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite
    @Parameters({"browser"})
    public void setUp(@Optional("firefox") String xmlBrowser) {
        String browser = System.getProperty("browser", xmlBrowser).toLowerCase();
        WebDriver localDriver;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                localDriver = new ChromeDriver(chromeOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("-inprivate");
                localDriver = new EdgeDriver(edgeOptions);
                break;

            case "firefox":
            default:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                localDriver = new FirefoxDriver(firefoxOptions);
                break;
        }

        localDriver.manage().window().maximize();
        localDriver.get("https://candymapper.com/");
        this.driver.set(localDriver);
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

    @AfterMethod
    public void delay() throws InterruptedException {
        Thread.sleep(500);
    }

}
