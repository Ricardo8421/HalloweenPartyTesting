package party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

public class ContactUsTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void  beforeTest(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement contactUsForm = driver.findElement(By.xpath
                ("//*[@data-aid='CONTACT_FORM_CONTAINER_REND']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", contactUsForm);
        wait.until(ExpectedConditions.visibilityOf(contactUsForm));
    }

    public void errorEmailMessage(){
        final String EMAIL_MESSAGE = "Please enter a valid email address.";

        WebElement emailMessageError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@data-aid='CONTACT_EMAIL_ERR_REND']")));
        Assert.assertTrue(emailMessageError.isDisplayed());
        Assert.assertEquals(emailMessageError.getText(), EMAIL_MESSAGE);
    }

    public void fillInformationContactUs(String firstNameUser, String lastNameUser, String emailUser, String phoneUser, String messageUser){
        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-aid='First Name']")));
        firstName.clear();
        if (firstNameUser != null) {
            firstName.sendKeys(firstNameUser);
        }
        WebElement lastName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@data-aid='Last Name']")));
        lastName.clear();
        if (lastNameUser != null) {
            lastName.sendKeys(lastNameUser);
        }
        WebElement email = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//input[@data-aid='CONTACT_FORM_EMAIL']")));
        email.clear();
        if (emailUser != null) {
            email.sendKeys(emailUser);
        }
        WebElement phoneNumber = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//input[@data-aid='By entering a Phone Number you agree to our SMS Terms of Service']")));
        phoneNumber.clear();
        if (phoneUser != null) {
            phoneNumber.sendKeys(phoneUser);
        }
        WebElement message = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//*[@data-aid='CONTACT_FORM_MESSAGE']")));
        message.clear();
        if (messageUser != null) {
            message.sendKeys(messageUser);
        }

    }

    @Test (priority = 1)
    public void testSendEmptyEmail () {
        fillInformationContactUs("test","test"," ","test","test");
        WebElement logInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.x-el")));
        logInButton.sendKeys(Keys.ENTER);
        errorEmailMessage();
    }

    @Test (priority = 2)
    public void testWrongFormatEmail () {
        fillInformationContactUs("Juanito","Alcachofa",
                "emailtestemail.com","00000","test");
        WebElement logInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.x-el")));
        logInButton.sendKeys(Keys.ENTER);
        errorEmailMessage();
    }

    @Test (priority = 3)
    public void testSendCorrectInformation (){
        fillInformationContactUs("Juanito", "Alcachofa",
                "email@testemail.com", "00000","test");
        WebElement logInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.x-el")));
        logInButton.sendKeys(Keys.ENTER);
    }

    @Test (dependsOnMethods = {"testSendCorrectInformation"},priority = 4)
    public void testSuccessMessage (){
        final String SUCCESS_TEXT = "Thank you for your inquiry! We will get back to you within 48 Years.";
        WebElement successElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".c2-5d")));
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.c2-1:nth-child(2) > p:nth-child(1)")));

        Assert.assertTrue(successElement.isDisplayed());
        Assert.assertEquals(successMessage.getText(), SUCCESS_TEXT);
    }

}
