package pages.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class SignInPage extends BasePage {
    private static final Logger log = LogManager.getLogger(SignInPage.class);

    final String SIGN_IN_LINK_XPATH = "//*[contains(@id, 'membership-sign-in')]";
    final String SIGN_IN_BUTTON_XPATH = "//*[@data-aid='MEMBERSHIP_SSO_SUBMIT']";
    final String ERROR_MESSAGE_XPATH = "//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']";
    final String SIGNED_IN_EMAIL_XPATH = "//*[@data-aid='ACCOUNT_DETAILS_EMAIL_REND']//p";
    final String SESSION_ICON = "4";
    final String EMAIL = "email";
    final String PASSWORD = "password";

    @FindBy(xpath = SIGN_IN_LINK_XPATH)
    private WebElement signInLink;
    @FindBy(id = SESSION_ICON)
    private WebElement sessionIcon;
    @FindBy(name = EMAIL)
    private WebElement emailInput;
    @FindBy(name = PASSWORD)
    private WebElement passwordInput;
    @FindBy(xpath = SIGN_IN_BUTTON_XPATH)
    private WebElement signInButton;
    @FindBy(xpath = ERROR_MESSAGE_XPATH)
    private WebElement errorMessage;
    @FindBy(xpath = SIGNED_IN_EMAIL_XPATH)
    private WebElement signedInEmail;

    public SignInPage(WebDriver driver) {
        super(driver);
        log.info("Initializing SignInPage");
        navigateToSignIn();
    }

    public void navigateToSignIn() {
        log.info("Navigating to Sign In page");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

        log.debug("Clicking session icon");
        sessionIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        log.debug("Clicking Sign In link");
        signInLink.click();
    }

    public void sendSignInFormWithValues(String email, String password) {
        log.info("Attempting login with email: '{}'", email);
        emailInput.clear();
        passwordInput.clear();

        if (email != null) {
            log.debug("Entering email value into input box");
            emailInput.sendKeys(email);
        } else {
            log.warn("Email parameter passed is null");
        }

        if (password != null) {
            log.debug("Entering password value (masked for security)");
            passwordInput.sendKeys(password);
        } else {
            log.warn("Password parameter passed is null");
        }

        log.info("Clicking Sign In submit button");
        signInButton.click();
    }

    public boolean isDisplayedErrorMessage() {
        log.info("Checking if Sign In error message is displayed");
        boolean isDisplayed = errorMessage.isDisplayed();
        log.debug("Error message display status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getErrorMessage() {
        log.info("Retrieving Sign In error message text");
        String text = errorMessage.getText();
        log.debug("Error message text: '{}'", text);
        return text;
    }

    public boolean isDisplayedSuccessMessage() {
        log.info("Verifying successful Sign In email banner visibility");
        boolean isDisplayed = wait.until(ExpectedConditions.visibilityOf(signedInEmail)).isDisplayed();
        log.debug("Success message banner visibility status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getSuccessMessage() {
        log.info("Fetching signed-in user email text");
        String text = wait.until(ExpectedConditions.visibilityOf(signedInEmail)).getText();
        log.debug("Signed-in user email text: '{}'", text);
        return text;
    }
}