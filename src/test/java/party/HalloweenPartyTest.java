package party;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HalloweenPartyTest {
    WebDriver driver = null;

    @BeforeTest
    public void setupTest(ITestContext context){
        System.out.println("Configurando prueba...");

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private");

        driver = ThreadGuard.protect(WebDriverManager.firefoxdriver().capabilities(options).create());

        context.setAttribute("driver", driver);
    }

    @Test
    public void testPopUpClose(){
        driver.get("https://candymapper.com/");

        WebElement closePopUp = driver.findElement(By.id("popup-widget5912-close-icon"));

        closePopUp.click();

        Assert.assertFalse(driver.findElement(By.id("popup-widget5912")).isDisplayed());
    }

    @AfterTest
    public void endTest(ITestContext context){
        driver = (WebDriver) context.getAttribute("driver");
        try{
            driver.close();
            driver.quit();
        }catch(NoSuchSessionException e){
            System.out.println("Ninguna sesión que cerrar");
        }
        System.out.println("Terminando prueba...");
    }
    
}
