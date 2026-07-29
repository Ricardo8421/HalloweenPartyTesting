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

public class CreateAccountTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void redirectToCreateAccountPage(ITestContext context){
        driver = (WebDriver) context.getAttribute("driver");

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement sessionIcon = driver.findElement(By.id("4"));
        sessionIcon.click();

        WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'membership-create-account')]")));
        createAccountLink.click();
    }

    public void sendCreateAccountFormWithValues(String firstName, String lastName, String email, String phone){
        WebElement firstNameInput = driver.findElement(By.name("nameFirst"));
        WebElement lastNameInput = driver.findElement(By.name("nameLast"));
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement phoneInput = driver.findElement(By.name("phone"));

        firstNameInput.clear();
        lastNameInput.clear();
        emailInput.clear();
        phoneInput.clear();

        if(firstName != null){
            firstNameInput.sendKeys(firstName);
        }
        if(lastName != null){
            lastNameInput.sendKeys(lastName);
        }
        if(email != null){
            emailInput.sendKeys(email);
        }
        if(phone != null){
            phoneInput.sendKeys(phone);
        }
        
        WebElement createButton = driver.findElement(By.className("x-el-button"));
        createButton.click();
    }

    @Test
    public void testEmptyFirstName(){
        final String EXPECTED_ERROR_MESSAGE = "Enter your first name.";

        sendCreateAccountFormWithValues(null, "Alcachofa", "alcachofas@comida.com", null);

        WebElement firstNameErrorMessage = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_NAME_FIRST_ERR']"));
        Assert.assertTrue(firstNameErrorMessage.isDisplayed());
        Assert.assertEquals(firstNameErrorMessage.getText(), EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void testEmptyLastName(){
        final String EXPECTED_ERROR_MESSAGE = "Enter your last name.";
        sendCreateAccountFormWithValues("Juanito", null, "alcachofas@comida.com", null);

        WebElement lastNameErrorMessage = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_NAME_LAST_ERR']"));
        Assert.assertTrue(lastNameErrorMessage.isDisplayed());
        Assert.assertEquals(lastNameErrorMessage.getText(), EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void testEmptyEmail(){
        final String EXPECTED_ERROR_MESSAGE = "Enter a valid email address.";
        sendCreateAccountFormWithValues("Juanito", "Alcachofa", null, null);

        WebElement emailErrorMessage = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']"));
        Assert.assertTrue(emailErrorMessage.isDisplayed());
        Assert.assertEquals(emailErrorMessage.getText(), EXPECTED_ERROR_MESSAGE);
    }
    
    @Test
    public void testIncorrectEmail(){
        final String EXPECTED_ERROR_MESSAGE = "Enter a valid email address.";
        sendCreateAccountFormWithValues("Juanito", "Alcachofa", "Alcachofas y más S.A. de C.V.", null);

        WebElement emailErrorMessage = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']"));
        Assert.assertTrue(emailErrorMessage.isDisplayed());
        Assert.assertEquals(emailErrorMessage.getText(), EXPECTED_ERROR_MESSAGE);
    }

    @Test
    public void testCreateAccountWithoutPhone(){
        final String INSTRUCTION_HEADER_MESSAGE = "Check your email";
        final String INSTRUCTION_DESCRIPTION_MESSAGE = "You're almost there! We sent an email to alcachofas@comida.com with a link to activate your account. Please check your email and click the activation link.";

        sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", null);

        WebElement instructionHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4")));
        WebElement instructionDescription = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']"));

        Assert.assertTrue(instructionHeader.isDisplayed());
        Assert.assertTrue(instructionDescription.isDisplayed());
        Assert.assertEquals(instructionHeader.getText(), INSTRUCTION_HEADER_MESSAGE);
        Assert.assertEquals(instructionDescription.getText(), INSTRUCTION_DESCRIPTION_MESSAGE);
    }

    @Test
    public void testCreateAccountWithPhone(){
        final String INSTRUCTION_HEADER_MESSAGE = "Check your email";
        final String INSTRUCTION_DESCRIPTION_MESSAGE = "You're almost there! We sent an email to alcachofas@comida.com with a link to activate your account. Please check your email and click the activation link.";

        sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", "5512345678");

        WebElement instructionHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4")));
        WebElement instructionDescription = driver.findElement(By.xpath("//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']"));
        
        Assert.assertTrue(instructionHeader.isDisplayed());
        Assert.assertTrue(instructionDescription.isDisplayed());
        Assert.assertEquals(instructionHeader.getText(), INSTRUCTION_HEADER_MESSAGE);
        Assert.assertEquals(instructionDescription.getText(), INSTRUCTION_DESCRIPTION_MESSAGE);
    }
}
