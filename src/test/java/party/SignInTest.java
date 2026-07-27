package party;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignInTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void redirectToSignInPage(ITestContext context){
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement sessionIcon = driver.findElement(By.id("4"));
        sessionIcon.click();

        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'membership-sign-in')]")));
        createAccountLink.click();
    }

    public void sendSignInFormWithValues(String email, String password){
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));

        emailInput.clear();
        passwordInput.clear();

        if(email != null){
            emailInput.sendKeys(email);
        }
        if(password != null){
            passwordInput.sendKeys(password);
        }

        WebElement signInButton = driver.findElement(By.xpath("//*[@data-aid='MEMBERSHIP_SSO_SUBMIT']"));
        signInButton.click();
    }

    @Test
    public void testEmptyEmail(){
        final String expectedErrorMessage = "Enter a valid email address.";

        sendSignInFormWithValues(null, "patataypatat0");

        WebElement emailErrorMessage = driver.findElement(By.xpath("//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']"));
        Assert.assertTrue(emailErrorMessage.isDisplayed());
        Assert.assertEquals(emailErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testIncorrectEmail(){
        final String expectedErrorMessage = "Enter a valid email address.";

        sendSignInFormWithValues("Alcachofas y más S.A. de C.V.", "patataypatat0");

        WebElement emailErrorMessage = driver.findElement(By.xpath("//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']"));
        Assert.assertTrue(emailErrorMessage.isDisplayed());
        Assert.assertEquals(emailErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testEmptyPassword(){
        final String expectedErrorMessage = "Passwords can’t be nothing.";

        sendSignInFormWithValues("r15mez888@gmail.com", null);

        WebElement passwordErrorMessage = driver.findElement(By.xpath("//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed());
        Assert.assertEquals(passwordErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testIncorrectCredentials(){
        final String expectedErrorMessage = "The password/email address combo is incorrect.";

        sendSignInFormWithValues("lecuhas@trenes.com", "patataypatat0");

        WebElement signInErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']")));
        Assert.assertTrue(signInErrorMessage.isDisplayed());
        Assert.assertEquals(signInErrorMessage.getText(), expectedErrorMessage);
    }

    @Test
    public void testSignIn(){
        final String email = "r15mez888@gmail.com";

        sendSignInFormWithValues(email, "patataypatat0");

        WebElement signedInEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-aid='ACCOUNT_DETAILS_EMAIL_REND']//p")));

        Assert.assertTrue(signedInEmail.isDisplayed());
        Assert.assertEquals(signedInEmail.getText(), email);
    }
}
