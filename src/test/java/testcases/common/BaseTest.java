package testcases.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.party.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite (alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("firefox") String xmlBrowser) {
        ConfigReader.initializeConfig();

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
        localDriver.get(ConfigReader.getProperty("base.url"));
        this.driver.set(localDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    @AfterSuite (alwaysRun = true)
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    //TODO: Test failure for screenshot
    @AfterMethod(alwaysRun = true)
    public void checkFailure(ITestResult result) throws InterruptedException {
        // DEBUG
        int waitMiliseconds = Integer.parseInt(ConfigReader.getProperty("explicit.wait"));
        Thread.sleep(waitMiliseconds);

        if(result.getStatus() == ITestResult.FAILURE){
            File imgFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
            // Get all info for screenshot name
            String testName = result.getMethod().getMethodName();
            LocalDateTime failureTime = LocalDateTime.now();
            DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");
            String failureTimeString = failureTime.format(timeFormater);
            String screenshotFileName = testName + "_" + failureTimeString + ".png";

            try{
                FileUtils.copyFile(imgFile, new File("test/reports/screenshots" + screenshotFileName));
                log.info("The screenshot saved with the name " + screenshotFileName);
            }catch(IOException ex){
                log.error("An error has occurred while saving a screenshot:" + ex.getMessage());
            }
        }
    }
}
