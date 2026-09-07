package pages.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class TwoFactorAuthenticationPage extends BasePage {
    private static final Logger log = LogManager.getLogger(TwoFactorAuthenticationPage.class);

    final String NO_AUTO_POPUP_BUTTON_XPATH = "//*[@id=':2.noAutoPopup']";
    final String MORE_DROPDOWN_XPATH = "//*[@id='2']";
    final String EXPECTED_ELEMENT_XPATH = "/html/body/div[2]/div/div/div[1]/div/div/section/div[3]/div[2]/div/nav/div[2]/div/div[2]/div[1]/nav/ul/li[15]/ul/li[14]/a";
    final String CONTENT_XPATH = "//*[@id='iframe-06']";
    final String EMAIL_INPUT_XPATH = "//*[@id='email']";
    final String SEND_CODE_BUTTON_XPATH = "//div[@class='email-section']//button";
    final String CODE_INPUT_XPATH = "//*[@id='code']";
    final String VERIFY_CODE_BUTTON_XPATH = "//div[@id='verificationSection']//button";
    final String MESSAGE_XPATH = "//*[@id='message']";

    @FindBy(xpath = MORE_DROPDOWN_XPATH)
    private WebElement moreDropdown;
    @FindBy(xpath = EXPECTED_ELEMENT_XPATH)
    private WebElement expectedElement;
    @FindBy(xpath = EMAIL_INPUT_XPATH)
    private WebElement emailInput;
    @FindBy(xpath = SEND_CODE_BUTTON_XPATH)
    private WebElement sendCodeButton;
    @FindBy(xpath = CODE_INPUT_XPATH)
    private WebElement codeInput;
    @FindBy(xpath = VERIFY_CODE_BUTTON_XPATH)
    private WebElement verifyCodeButton;
    @FindBy(xpath = MESSAGE_XPATH)
    private WebElement codeMessage;
    @FindBy(xpath = MESSAGE_XPATH)
    private WebElement emailErrorMessage;

    public TwoFactorAuthenticationPage(WebDriver driver) {
        super(driver);
        log.info("Initializing TwoFactorAuthenticationPage");
        navigateTo2FAPage();
    }

    public void navigateTo2FAPage() {
        log.info("Navigating to 2FA Page");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

        log.debug("Switching to container iframe ':2.container'");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(":2.container"));

        log.debug("Dismissing auto popup");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NO_AUTO_POPUP_BUTTON_XPATH))).click();
        driver.switchTo().parentFrame();

        log.debug("Opening 'More' navigation dropdown menu");
        wait.until(ExpectedConditions.elementToBeClickable(moreDropdown)).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", expectedElement);

        try {
            log.debug("Attempting to click 2FA link item");
            wait.until(ExpectedConditions.elementToBeClickable(expectedElement)).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted on 2FA link element. Switching to default content to retry click.", e);
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(expectedElement)).click();
        }
    }

    public void sendCode(String email) {
        log.info("Requesting 2FA verification code for email: '{}'", email);
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        emailInput.clear();

        if (email != null) {
            log.debug("Entering email into 2FA input box");
            emailInput.sendKeys(email);
        } else {
            log.warn("Email parameter passed to sendCode is null");
        }

        log.debug("Clicking Send Code button");
        wait.until(ExpectedConditions.elementToBeClickable(sendCodeButton)).click();
        driver.switchTo().parentFrame();
    }

    public void verifyCode(String code) {
        log.info("Submitting verification code: '{}'", code);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        codeInput.clear();

        if (code != null) {
            log.debug("Entering code into verification input box");
            codeInput.sendKeys(code);
        } else {
            log.warn("Code parameter passed to verifyCode is null");
        }

        log.debug("Clicking Verify Code button");
        wait.until(ExpectedConditions.elementToBeClickable(verifyCodeButton)).click();
        driver.switchTo().parentFrame();
    }

    public String findCode() {
        log.info("Extracting generated 2FA verification code from message");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        String code = codeMessage.getText().substring(23, 29);
        log.debug("Extracted verification code: '{}'", code);
        driver.switchTo().parentFrame();
        return code;
    }

    public void getErrorMessageCode() {
        log.info("Executing invalid code scenario flow");
        String testCode = "000000";
        sendCode("alcachofas@comida.com");
        String code = findCode();
        if (code.equals(testCode)) {
            log.warn("Extracted code matched fallback test code. Changing test code to avoid false positive.");
            testCode = "000001";
        }
        verifyCode(testCode);
    }

    public boolean isMessageDisplayed() {
        log.info("Checking display status of 2FA email/code message");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        boolean messageDisplayed = wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).isDisplayed();
        log.debug("Message visibility status: {}", messageDisplayed);
        driver.switchTo().parentFrame();
        return messageDisplayed;
    }

    public String getMessage() {
        log.info("Retrieving text content of 2FA message");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        String message = wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).getText();
        log.debug("Retrieved 2FA message: '{}'", message);
        driver.switchTo().parentFrame();
        return message;
    }
}