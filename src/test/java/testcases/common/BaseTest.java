package testcases.common;

import driver.DriverFactory;
import driver.DriverManager;
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
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeSuite (alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("firefox") String xmlBrowser) {
        ConfigReader.initializeConfig();

        String browser = System.getProperty("browser", xmlBrowser).toLowerCase();

        WebDriver driver = DriverFactory.createInstance(browser);

        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("base.url"));

        DriverManager.setDriver(driver);
    }

    public static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @AfterSuite (alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }


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
                FileUtils.copyFile(imgFile, new File("reports/screenshots" + screenshotFileName));
                log.info("The screenshot saved with the name " + screenshotFileName);
            }catch(IOException ex){
                log.error("An error has occurred while saving a screenshot:" + ex.getMessage());
            }
        }
    }
}
