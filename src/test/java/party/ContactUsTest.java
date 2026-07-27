package party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ContactUsTest {
    WebDriver driver = null;
    WebDriverWait wait = null;
    @BeforeMethod
    public void  beforeTest(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @Test (dependsOnMethods = {"testPopUpClose"})
    public void testNavigatesToContactUs(ITestContext context) {

        WebElement contactUsForm = driver.findElement(By.xpath
                ("//*[@data-aid='CONTACT_FORM_CONTAINER_REND']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", contactUsForm);
        wait.until(ExpectedConditions.visibilityOf(contactUsForm));
    }


    public void errorEmailMessage(){
        final String EMAIL_MESSAGE = "Please enter a valid email address.";

        WebElement emailMessageError = driver.findElement(By.xpath("//*[@data-aid='CONTACT_EMAIL_ERR_REND']"));

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
    public void testSendEmptyForm () {
        fillInformationContactUs(" "," "," "," "," ");
        WebElement logInButton = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@data-aid='CONTACT_SUBMIT_BUTTON_REND']")));
        logInButton.click();
        errorEmailMessage();
    }

    @Test (priority = 2)
    public void testWrongFormatEmail () {
        fillInformationContactUs("Juanito","Alcachofa",
                "emailtestemail.com","00000","test");
        WebElement logInButton = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@data-aid='CONTACT_SUBMIT_BUTTON_REND']")));
        logInButton.click();
        errorEmailMessage();
    }

    @Test (priority = 3)
    public void testSendCorrectInformation (){
        final String SUCCESS_TEXT = "Thank you for your inquiry! We will get back to you within 48 Years.";

        fillInformationContactUs("Juanito", "Alcachofa",
                "email@testemail.com", "00000","test");

        WebElement logInButton = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@data-aid='CONTACT_SUBMIT_BUTTON_REND']")));
        logInButton.click();
        WebElement successSend = wait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath("/html/body/div[2]/div/div/div[6]/div/div/section/div/div/div[1]/span/div/div/div/div/div/div[1]")));

        WebElement submitText = driver.findElement(By.xpath("//*[@data-aid='CONTACT_FORM_SUBMIT_SUCCESS_MESSAGE'] "));

        Assert.assertEquals(submitText.getText(),SUCCESS_TEXT);
        Assert.assertTrue(successSend.isDisplayed());
    }

}
