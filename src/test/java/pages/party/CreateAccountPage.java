package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class CreateAccountPage extends BasePage {
    WebDriverWait wait = null;
    By firstNameErrorMessage = By.xpath("//*[@data-aid='CREATE_ACCOUNT_NAME_FIRST_ERR']");
    By lastNameErrorMessage = By.xpath("//*[@data-aid='CREATE_ACCOUNT_NAME_LAST_ERR']");
    By emailErrorMessage = By.xpath("//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']");
    By instructionHeader = By.xpath("//h4");
    By instructionDescription = By.xpath("//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']");

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        navigateContactUsPage();
    }

    public void navigateContactUsPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

    public boolean isFirstNameDisplayed(){
        return driver.findElement(firstNameErrorMessage).isDisplayed();
    }

    public String getFirstNameErrorMessage(){
        return driver.findElement(firstNameErrorMessage).getText();
    }

    public boolean isLastNameDisplayed(){
        return driver.findElement(lastNameErrorMessage).isDisplayed();
    }

    public String getLastNameErrorMessage(){
        return driver.findElement(lastNameErrorMessage).getText();
    }

    public boolean isEmailDisplayed(){
        return driver.findElement(emailErrorMessage).isDisplayed();
    }
    public String getEmailErrorMessage(){
        return driver.findElement(emailErrorMessage).getText();
    }

    public boolean isInstructionHeaderDisplayed(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(instructionHeader)).isDisplayed();
    }
    public String getInstructionHeaderErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(instructionHeader)).getText();
    }
    public boolean isInstructionDescriptionDisplayed(){
        return driver.findElement(instructionDescription).isDisplayed();
    }
    public String getInstructionDescriptionErrorMessage(){
        return driver.findElement(instructionDescription).getText();
    }
}
