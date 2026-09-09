package pages.party;

import models.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.party.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class CreateAccountPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CreateAccountPage.class);

    final String FIRST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_FIRST_ERR']";
    final String LAST_NAME_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_NAME_LAST_ERR']";
    final String EMAIL_ERROR_MESSAGE_XPATH = "//*[@data-aid='CREATE_ACCOUNT_EMAIL_ERR']";
    final String INSTRUCTIONS_HEADER_XPATH = "//h4";
    final String INSTRUCTIONS_DESCRIPTION_XPATH = "//*[@data-aid='CREATE_ACCOUNT_DESCRIPTION_REND']";
    final String CREATE_ACCOUNT_LINK_XPATH = "//*[contains(@id, 'membership-create-account')]";
    final String SESSION_ICON = "4";
    final String NAME_FIRST = "nameFirst";
    final String LAST_NAME = "nameLast";
    final String EMAIL = "email";
    final String PHONE = "phone";
    final String CREATE_BUTTON = "x-el-button";

    @FindBy(xpath = FIRST_NAME_ERROR_MESSAGE_XPATH)
    private WebElement firstNameErrorMessage;
    @FindBy(xpath = LAST_NAME_ERROR_MESSAGE_XPATH)
    private WebElement lastNameErrorMessage;
    @FindBy(xpath = EMAIL_ERROR_MESSAGE_XPATH)
    private WebElement emailErrorMessage;
    @FindBy(xpath = INSTRUCTIONS_HEADER_XPATH)
    private WebElement instructionHeader;
    @FindBy(xpath = INSTRUCTIONS_DESCRIPTION_XPATH)
    private WebElement instructionDescription;
    @FindBy(xpath = CREATE_ACCOUNT_LINK_XPATH)
    private WebElement createAccountLink;
    @FindBy(id = SESSION_ICON)
    private WebElement sessionIcon;
    @FindBy(name = NAME_FIRST)
    private WebElement firstNameInput;
    @FindBy(name = LAST_NAME)
    private WebElement lastNameInput;
    @FindBy(name = EMAIL)
    private WebElement emailInput;
    @FindBy(name = PHONE)
    private WebElement phoneInput;
    @FindBy(className = CREATE_BUTTON)
    private WebElement createButton;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        log.info("Initializing CreateAccountPage");
        navigateContactUsPage();
    }

    public void sendCreateAccountFormWithValues(UserAccount userAccount) {
        sendCreateAccountFormWithValues(
                userAccount.getFirstName(),
                userAccount.getLastName(),
                userAccount.getEmail(),
                userAccount.getPhone()
        );
    }

    public void navigateContactUsPage() {
        log.info("Navigating to Create Account section");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        log.debug("Setting explicit wait timeout to {} seconds", waitSeconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

        log.debug("Clicking session icon");
        sessionIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(createAccountLink));
        log.debug("Clicking Create Account link");
        createAccountLink.click();
    }

    public void sendCreateAccountFormWithValues(String firstName, String lastName, String email, String phone) {
        log.info("Submitting Create Account form with parameters -> FirstName: '{}', LastName: '{}', Email: '{}', Phone: '{}'",
                firstName, lastName, email, phone);

        firstNameInput.clear();
        lastNameInput.clear();
        emailInput.clear();
        phoneInput.clear();

        if (firstName != null) {
            log.debug("Entering First Name: {}", firstName);
            firstNameInput.sendKeys(firstName);
        } else {
            log.warn("First Name parameter is null. Leaving field blank.");
        }

        if (lastName != null) {
            log.debug("Entering Last Name: {}", lastName);
            lastNameInput.sendKeys(lastName);
        } else {
            log.warn("Last Name parameter is null. Leaving field blank.");
        }

        if (email != null) {
            log.debug("Entering Email: {}", email);
            emailInput.sendKeys(email);
        } else {
            log.warn("Email parameter is null. Leaving field blank.");
        }

        if (phone != null) {
            log.debug("Entering Phone: {}", phone);
            phoneInput.sendKeys(phone);
        } else {
            log.warn("Phone parameter is null. Leaving field blank.");
        }

        log.info("Clicking Create Account button");
        createButton.click();
    }

    public boolean isFirstNameDisplayed() {
        log.info("Checking First Name error message visibility");
        boolean isDisplayed = firstNameErrorMessage.isDisplayed();
        log.debug("First Name error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getFirstNameErrorMessage() {
        log.info("Fetching First Name error message text");
        String text = firstNameErrorMessage.getText();
        log.debug("First Name error message: '{}'", text);
        return text;
    }

    public boolean isLastNameDisplayed() {
        log.info("Checking Last Name error message visibility");
        boolean isDisplayed = lastNameErrorMessage.isDisplayed();
        log.debug("Last Name error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getLastNameErrorMessage() {
        log.info("Fetching Last Name error message text");
        String text = lastNameErrorMessage.getText();
        log.debug("Last Name error message: '{}'", text);
        return text;
    }

    public boolean isEmailDisplayed() {
        log.info("Checking Email error message visibility");
        boolean isDisplayed = emailErrorMessage.isDisplayed();
        log.debug("Email error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getEmailErrorMessage() {
        log.info("Fetching Email error message text");
        String text = emailErrorMessage.getText();
        log.debug("Email error message: '{}'", text);
        return text;
    }

    public boolean isInstructionHeaderDisplayed() {
        log.info("Verifying instruction header display");
        boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(instructionHeader)).isDisplayed();
        log.debug("Instruction header visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getInstructionHeaderErrorMessage() {
        log.info("Retrieving instruction header text");
        String text = wait.until(ExpectedConditions.visibilityOf(instructionHeader)).getText();
        log.debug("Instruction header text: '{}'", text);
        return text;
    }

    public boolean isInstructionDescriptionDisplayed() {
        log.info("Checking instruction description display");
        boolean isDisplayed = instructionDescription.isDisplayed();
        log.debug("Instruction description visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getInstructionDescriptionErrorMessage() {
        log.info("Retrieving instruction description text");
        String text = instructionDescription.getText();
        log.debug("Instruction description text: '{}'", text);
        return text;
    }
}