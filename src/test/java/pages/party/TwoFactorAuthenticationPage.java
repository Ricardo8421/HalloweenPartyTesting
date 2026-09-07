package pages.party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class TwoFactorAuthenticationPage extends BasePage {
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
        navigateTo2FAPage();
    }

    public void navigateTo2FAPage() {
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(":2.container"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NO_AUTO_POPUP_BUTTON_XPATH))).click();
        driver.switchTo().parentFrame();

        wait.until(ExpectedConditions.elementToBeClickable(moreDropdown)).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", expectedElement);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(expectedElement)).click();
        } catch (ElementClickInterceptedException e) {
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(expectedElement)).click();
        }
    }

    public void sendCode(String email) {
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        emailInput.clear();

        if (email != null) {
            emailInput.sendKeys(email);
        }
        wait.until(ExpectedConditions.elementToBeClickable(sendCodeButton)).click();
        driver.switchTo().parentFrame();
    }

    public void verifyCode(String code) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        codeInput.clear();

        if (code != null) {
            codeInput.sendKeys(code);
        }

        wait.until(ExpectedConditions.elementToBeClickable(verifyCodeButton)).click();
        driver.switchTo().parentFrame();
    }

    public String findCode() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        String code = codeMessage.getText().substring(23, 29);
        driver.switchTo().parentFrame();
        return code;
    }

    public void getErrorMessageCode() {
        String testCode = "000000";
        sendCode("alcachofas@comida.com");
        String code = findCode();
        if (code.equals(testCode)) {
            testCode = "000001";
        }
        verifyCode(testCode);
    }

    public boolean isMessageDisplayed() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        boolean messageDisplayed = wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).isDisplayed();
        driver.switchTo().parentFrame();
        return messageDisplayed;
    }

    public String getMessage() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        String message = wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).getText();
        driver.switchTo().parentFrame();
        return message;
    }
}