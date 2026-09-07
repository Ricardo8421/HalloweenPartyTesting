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
    final String FIRST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_FIRST_ERR']";
    final String LAST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_LAST_ERR']";
    final String EMAIL_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']";
    final String INSTRUCTIONS_HEADER_XPATH = "//h4";
    final String INSTRUCTIONS_DESCRIPTION_XPATH = "//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']";
    final String CREATE_ACCOUNT_LINK_XPATH = "//*[contains(@id, 'membership-create-account')]";
    final String SESSION_ICON = "4";
    final String NAME_FIRST =  "nameFirst";
    final String LAST_NAME = "nameLast";
    final String EMAIL = "email";
    final String PHONE = "phone";
    final String CREATE_BUTTON = "x-el-button";


    @FindBy (xpath = FIRST_NAME_ERROR_MESSAGE_XPATH)
    private WebElement firstNameErrorMessage;
    @FindBy (xpath = LAST_NAME_ERROR_MESSAGE_XPATH)
    private WebElement lastNameErrorMessage;
    @FindBy (xpath = EMAIL_ERROR_MESSAGE_XPATH)
    private WebElement emailErrorMessage;
    @FindBy (xpath = INSTRUCTIONS_HEADER_XPATH)
    private WebElement instructionHeader;
    @FindBy (xpath = INSTRUCTIONS_DESCRIPTION_XPATH)
    private WebElement instructionDescription;
    @FindBy (xpath = CREATE_ACCOUNT_LINK_XPATH)
    private WebElement createAccountLink;
    @FindBy(id = SESSION_ICON)
    private WebElement sessionIcon;
    @FindBy (name = NAME_FIRST)
    private WebElement firstNameInput;
    @FindBy (name = LAST_NAME)
    private WebElement lastNameInput;
    @FindBy (name = EMAIL)
    private WebElement emailInput;
    @FindBy (name = PHONE)
    private WebElement phoneInput;
    @FindBy (className = CREATE_BUTTON)
    private WebElement createButton;

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

        createButton.click();
    }

    public boolean isFirstNameDisplayed(){
        return firstNameErrorMessage.isDisplayed();
    }

    public String getFirstNameErrorMessage(){
        return firstNameErrorMessage.getText();
    }

    public boolean isLastNameDisplayed(){
        return lastNameErrorMessage.isDisplayed();
    }

    public String getLastNameErrorMessage(){
        return lastNameErrorMessage.getText();
    }

    public boolean isEmailDisplayed(){
        return emailErrorMessage.isDisplayed();
    }
    public String getEmailErrorMessage(){
        return emailErrorMessage.getText();
    }

    public boolean isInstructionHeaderDisplayed(){
        return wait.until(ExpectedConditions.visibilityOf(instructionHeader)).isDisplayed();
    }
    public String getInstructionHeaderErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOf(instructionHeader)).getText();
    }
    public boolean isInstructionDescriptionDisplayed(){
        return instructionDescription.isDisplayed();
    }
    public String getInstructionDescriptionErrorMessage(){
        return instructionDescription.getText();
    }
}
