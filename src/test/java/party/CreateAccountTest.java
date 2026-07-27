package party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class CreateAccountTest {
    WebDriver driver = null;

    @Test(dependsOnMethods = {"testPopUpClose"})
    public void testCreateAccount(ITestContext context){
        driver = (WebDriver) context.getAttribute("driver");

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
}
