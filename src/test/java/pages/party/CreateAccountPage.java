package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class CreateAccountPage extends BasePage {
    WebDriverWait wait = null;
    final String FIRST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_FIRST_ERR']";
    final String LAST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_LAST_ERR']";
    final String EMAIL_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']";
    final String INSTRUCTIONS_HEADER_XPATH = "//h4";
    final String INSTRUCTIONS_DESCRIPTION_XPATH = "//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']";
    final String CREATE_ACCOUNT_LINK_XPATH = "//*[contains(@id, 'membership-create-account')]";

    By firstNameErrorMessage = By.xpath(FIRST_NAME_ERROR_MESSAGE_XPATH);
    By lastNameErrorMessage = By.xpath(LAST_NAME_ERROR_MESSAGE_XPATH);
    By emailErrorMessage = By.xpath(EMAIL_ERROR_MESSAGE_XPATH);
    By instructionHeader = By.xpath(INSTRUCTIONS_HEADER_XPATH);
    By instructionDescription = By.xpath(INSTRUCTIONS_DESCRIPTION_XPATH);

    @FindBy(id = "4")
    private WebElement sessionIcon;
    @FindBy (xpath = CREATE_ACCOUNT_LINK_XPATH)
    private WebElement createAccountLink;
    @FindBy (name = "nameFirst")
    private WebElement firstNameInput;
    @FindBy (name = "nameLast")
    private WebElement lastNameInput;
    @FindBy (name = "emial")
    private WebElement emailInput;
    @FindBy (name = "phone")
    private WebElement phoneInput;
    private WebElement createButton = null;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        navigateContactUsPage();
    }

    public void navigateContactUsPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        sessionIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(createAccountLink));
        createAccountLink.click();
    }

    public void sendCreateAccountFormWithValues(String firstName, String lastName, String email, String phone){
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

        createButton = driver.findElement(By.className("x-el-button"));
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
