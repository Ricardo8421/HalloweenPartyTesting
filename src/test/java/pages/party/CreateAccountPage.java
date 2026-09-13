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
        navigateCreateAccountPage();
    }

    public void sendCreateAccountFormWithValues(UserAccount userAccount) {
        sendCreateAccountFormWithValues(
                userAccount.getFirstName(),
                userAccount.getLastName(),
                userAccount.getEmail(),
                userAccount.getPhone()
        );
    }

    public void navigateCreateAccountPage() {
        log.info("Navigating to Create Account section");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        log.debug("Setting explicit wait timeout to {} seconds", waitSeconds);
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

        log.debug("Clicking session icon");
        click(sessionIcon, "Session Icon");

        wait.until(ExpectedConditions.elementToBeClickable(createAccountLink));
        log.debug("Clicking Create Account link");
        click(createAccountLink, "Create Account Link");
    }

    public void sendCreateAccountFormWithValues(String firstName, String lastName, String email, String phone) {
        log.info("Submitting Create Account form with parameters -> FirstName: '{}', LastName: '{}', Email: '{}', Phone: '{}'",
                firstName, lastName, email, phone);

        clear(firstNameInput, "First Name Input");
        clear(lastNameInput, "Last Name Input");
        clear(emailInput, "Email Input");
        clear(phoneInput, "Phone Input");

        if (firstName != null) {
            log.debug("Entering First Name: {}", firstName);
            sendKeys(firstNameInput, firstName, "First Name Input");
        } else {
            log.warn("First Name parameter is null. Leaving field blank.");
        }

        if (lastName != null) {
            log.debug("Entering Last Name: {}", lastName);
            sendKeys(lastNameInput, lastName, "Last Name Input");
        } else {
            log.warn("Last Name parameter is null. Leaving field blank.");
        }

        if (email != null) {
            log.debug("Entering Email: {}", email);
            sendKeys(emailInput, email, "Email Input");
        } else {
            log.warn("Email parameter is null. Leaving field blank.");
        }

        if (phone != null) {
            log.debug("Entering Phone: {}", phone);
            sendKeys(phoneInput, phone, "Phone Input");
        } else {
            log.warn("Phone parameter is null. Leaving field blank.");
        }

        log.info("Clicking Create Account button");
        click(createButton, "Create Button");
    }

    public boolean isFirstNameDisplayed() {
        log.info("Checking First Name error message visibility");
        boolean isDisplayed = isDisplayed(firstNameErrorMessage, "First Name Error Message");
        log.debug("First Name error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getFirstNameErrorMessage() {
        log.info("Fetching First Name error message text");
        String text = getText(firstNameErrorMessage, "First Name Error Message");
        log.debug("First Name error message: '{}'", text);
        return text;
    }

    public boolean isLastNameDisplayed() {
        log.info("Checking Last Name error message visibility");
        boolean isDisplayed = isDisplayed(lastNameErrorMessage, "Last Name Error Message");
        log.debug("Last Name error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getLastNameErrorMessage() {
        log.info("Fetching Last Name error message text");
        String text = getText(lastNameErrorMessage, "Last Name Error Message");
        log.debug("Last Name error message: '{}'", text);
        return text;
    }

    public boolean isEmailDisplayed() {
        log.info("Checking Email error message visibility");
        boolean isDisplayed = isDisplayed(emailErrorMessage, "Email Error Message");
        log.debug("Email error visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getEmailErrorMessage() {
        log.info("Fetching Email error message text");
        String text = getText(emailErrorMessage, "Email Error Message");
        log.debug("Email error message: '{}'", text);
        return text;
    }

    public boolean isInstructionHeaderDisplayed() {
        log.info("Verifying instruction header display");
        boolean isDisplayed = isDisplayed(wait.until(ExpectedConditions.visibilityOf(instructionHeader)), "Instruction Header");
        log.debug("Instruction header visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getInstructionHeaderErrorMessage() {
        log.info("Retrieving instruction header text");
        String text = getText(wait.until(ExpectedConditions.visibilityOf(instructionHeader)), "Instruction Header");
        log.debug("Instruction header text: '{}'", text);
        return text;
    }

    public boolean isInstructionDescriptionDisplayed() {
        log.info("Checking instruction description display");
        boolean isDisplayed = isDisplayed(instructionDescription, "Instruction Description");
        log.debug("Instruction description visibility: {}", isDisplayed);
        return isDisplayed;
    }

    public String getInstructionDescriptionErrorMessage() {
        log.info("Retrieving instruction description text");
        String text = getText(instructionDescription, "Instruction Description");
        log.debug("Instruction description text: '{}'", text);
        return text;
    }
}