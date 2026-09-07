package pages.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.common.BasePage;

import java.time.Duration;

public class ContactUsPage extends BasePage {
    private static final Logger log = LogManager.getLogger(ContactUsPage.class);

    private final String CONTACT_US_FORM_XPATH = "//*[@data-aid='CONTACT_FORM_CONTAINER_REND']";
    private final String EMAIL_MESSAGE_ERROR_XPATH = "//*[@data-aid='CONTACT_EMAIL_ERR_REND']";
    private final String FIRST_NAME_XPATH = "//input[@data-aid='First Name']";
    private final String LAST_NAME_XPATH = "//input[@data-aid='Last Name']";
    private final String EMAIL_XPATH = "//input[@data-aid='CONTACT_FORM_EMAIL']";
    private final String PHONE_XPATH = "//input[@data-aid='By entering a Phone Number you agree to our SMS Terms of Service']";
    private final String MESSAGE_XPATH = "//textarea[@data-aid='CONTACT_FORM_MESSAGE']";
    private final String SUCCESSFUL_MESSAGE_CSS = ".c2-5d";
    private final String SUCCESSFUL_TEXT_CSS = "div.c2-1:nth-child(2) > p:nth-child(1)";

    @FindBy(xpath = CONTACT_US_FORM_XPATH)
    private WebElement contactUsForm;
    @FindBy(xpath = EMAIL_MESSAGE_ERROR_XPATH)
    private WebElement emailMessageError;
    private WebElement input = null;
    @FindBy(css = SUCCESSFUL_MESSAGE_CSS)
    private WebElement successElement;
    @FindBy(css = SUCCESSFUL_TEXT_CSS)
    private WebElement successMessageElement;

    public ContactUsPage(WebDriver driver) {
        super(driver);
        log.info("Initializing ContactUsPage");
        navigateContactUsPage();
    }

    public void navigateContactUsPage() {
        log.info("Navigating and scrolling to Contact Us Form");
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(1));
        contactUsForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CONTACT_US_FORM_XPATH)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", contactUsForm);
        log.debug("Successfully scrolled to Contact Us form container");
    }

    public boolean isErrorElementEmail() {
        log.info("Checking if email error element is displayed");
        wait.until(ExpectedConditions.visibilityOf(emailMessageError));
        boolean isDisplayed = emailMessageError.isDisplayed();
        log.debug("Email error element visibility status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getErrorMessageEmail() {
        log.info("Fetching email error message text");
        wait.until(ExpectedConditions.visibilityOf(emailMessageError));
        String errorText = emailMessageError.getText();
        log.debug("Retrieved email error message: '{}'", errorText);
        return errorText;
    }

    public void fillSingleElement(String xpath, String value) {
        log.debug("Attempting to fill element at xpath '{}' with value '{}'", xpath, value);
        input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        input.clear();
        if (value != null) {
            input.sendKeys(value);
        }
    }

    public void fillInformationContactUs(String firstNameUser, String lastNameUser, String emailUser, String phoneUser, String messageUser) {
        log.info("Filling out Contact Us form for user: {} {}", firstNameUser, lastNameUser);

        try {
            fillSingleElement(FIRST_NAME_XPATH, firstNameUser);
        } catch (StaleElementReferenceException e) {
            log.warn("First name input became stale. Retrying fill operation...", e);
            fillSingleElement(FIRST_NAME_XPATH, firstNameUser);
        }

        try {
            fillSingleElement(LAST_NAME_XPATH, lastNameUser);
        } catch (StaleElementReferenceException e) {
            log.warn("Last name input became stale. Retrying fill operation...", e);
            fillSingleElement(LAST_NAME_XPATH, lastNameUser);
        }

        try {
            fillSingleElement(EMAIL_XPATH, emailUser);
        } catch (StaleElementReferenceException e) {
            log.warn("Email input became stale. Retrying fill operation...", e);
            fillSingleElement(EMAIL_XPATH, emailUser);
        }

        try {
            fillSingleElement(PHONE_XPATH, phoneUser);
        } catch (StaleElementReferenceException e) {
            log.warn("Phone input became stale. Retrying fill operation...", e);
            fillSingleElement(PHONE_XPATH, phoneUser);
        }

        try {
            fillSingleElement(MESSAGE_XPATH, messageUser);
        } catch (StaleElementReferenceException e) {
            log.warn("Message input became stale. Retrying fill operation...", e);
            fillSingleElement(MESSAGE_XPATH, messageUser);
        }

        log.info("Submitting Contact Us form via JavaScript click");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeAsyncScript("document.querySelector('[data-aid=\"CONTACT_SUBMIT_BUTTON_REND\"]').click();");
            log.debug("Submit button clicked via JavaScript execution");
        } catch (ScriptTimeoutException e) {
            log.error("Script timeout occurred while attempting to submit Contact Us form", e);
        }
    }

    public boolean isSuccessElementEmail() {
        log.info("Verifying if submission success element is displayed");
        wait.until(ExpectedConditions.visibilityOf(successElement));
        boolean isDisplayed = successElement.isDisplayed();
        log.debug("Success element visibility status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getSuccessMessage() {
        log.info("Retrieving submission success message text");
        wait.until(ExpectedConditions.elementToBeClickable(successMessageElement));
        String successText = successMessageElement.getText();
        log.debug("Retrieved success message text: '{}'", successText);
        return successText;
    }
}