package pages.party;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class SignInPage extends BasePage {
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
        navigateToSignIn();
    }

    public void navigateToSignIn() {
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        sessionIcon.click();

        wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInLink.click();
    }

    public void sendSignInFormWithValues(String email, String password) {
        emailInput.clear();
        passwordInput.clear();

        if (email != null) {
            emailInput.sendKeys(email);
        }
        if (password != null) {
            passwordInput.sendKeys(password);
        }
        signInButton.click();
    }

    public boolean isDisplayedErrorMessage() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isDisplayedSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(signedInEmail)).isDisplayed();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(signedInEmail)).getText();
    }
}
