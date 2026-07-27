package party;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HalloweenPartyTest {
    WebDriver driver = null;

    @BeforeTest
    public void setupTest(){
        System.out.println("Configurando prueba...");

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private");

        driver = ThreadGuard.protect(WebDriverManager.firefoxdriver().capabilities(options).create());

    }

    @Test
    public void testPopUpClose(){
        driver.get("https://candymapper.com/");

        WebElement closePopUp = driver.findElement(By.id("popup-widget5912-close-icon"));

        closePopUp.click();

        Assert.assertFalse(driver.findElement(By.id("popup-widget5912")).isDisplayed());
    }

    @Test(dependsOnMethods = {"testPopUpClose"})
    public void testCreateAccount(){
        WebElement joinUsButton = driver.findElement(By.id("bs-2"));

        joinUsButton.click();

        WebElement createAccountLink = driver.findElement(By.linkText("Create account."));

        createAccountLink.click();

        WebElement firstNameInput = driver.findElement(By.name("nameFirst"));
        WebElement lastNameInput = driver.findElement(By.name("nameLast"));
        WebElement emailInput = driver.findElement(By.name("email"));

        firstNameInput.clear();
        firstNameInput.sendKeys("Juanito");
        lastNameInput.clear();
        lastNameInput.sendKeys("Alcachofa");
        emailInput.clear();
        emailInput.sendKeys("algo@dominio.com");

        WebElement createButton = driver.findElement(By.className("x-el-button"));
        createButton.click();

        Assert.assertTrue(driver.findElement(By.className("widget-membership-create-account")).isDisplayed());
    }

    @AfterTest
    public void endTest(){
        driver.close();
        driver.quit();
        System.out.println("Terminando prueba...");
    }
    
}
