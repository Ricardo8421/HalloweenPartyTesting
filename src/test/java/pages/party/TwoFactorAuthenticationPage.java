package pages.party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.common.BasePage;

import java.time.Duration;

public class TwoFactorAuthenticationPage extends BasePage {
    WebDriverWait wait = null;
    final String NO_AUTO_POPUP_BUTTON_XPATH = "//*[@id=':2.noAutoPopup']";
    final String MORE_DROPDOWN_XPATH = "//*[@id='2']";
    final String EXPECTED_ELEMENT_XPATH = "/html/body/div[2]/div/div/div[1]/div/div/section/div[3]/div[2]/div/nav/div[2]/div/div[2]/div[1]/nav/ul/li[15]/ul/li[14]/a";
    final String CONTENT_XPATH = "//*[@id='iframe-06']";
    final String EMAIL_INPUT_XPATH = "//*[@id='email']";
    final String SEND_CODE_BUTTON_XPATH = "//div[@class='email-section']//button";
    final String CODE_INPUT_XPATH = "//*[@id='code']";
    final String VERIFY_CODE_BUTTON_XPATH = "//div[@id='verificationSection']//button";
    final String MESSAGE_XPATH = "//*[@id='message']";

    private WebElement moreDropdown = null;
    private WebElement expectedElement = null;
    private WebElement emailInput = null;
    private WebElement sendCodeButton = null;
    private WebElement codeInput = null;
    private WebElement verifyCodeButton = null;
    private WebElement codeMessage = null;
    private WebElement emailErrorMessage = null;

    public TwoFactorAuthenticationPage(WebDriver driver) {
        super(driver);
        navigateTo2FAPage();
    }

    public void navigateTo2FAPage(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(":2.container"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NO_AUTO_POPUP_BUTTON_XPATH))).click();
        driver.switchTo().parentFrame();


        moreDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(MORE_DROPDOWN_XPATH)));
        moreDropdown.click();

        expectedElement = driver.findElement(By.xpath(EXPECTED_ELEMENT_XPATH));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", expectedElement);

        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(EXPECTED_ELEMENT_XPATH))).click();
        }catch(ElementClickInterceptedException e){
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(EXPECTED_ELEMENT_XPATH))).click();
        }
    }

    public void sendCode(String email){
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));

        emailInput = driver.findElement(By.xpath(EMAIL_INPUT_XPATH));

        emailInput.clear();

        if(email != null){
            emailInput.sendKeys(email);
        }

        sendCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEND_CODE_BUTTON_XPATH)));
        sendCodeButton.click();

        driver.switchTo().parentFrame();
    }

    public void verifyCode(String code){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));

        codeInput = driver.findElement(By.xpath(CODE_INPUT_XPATH));

        codeInput.clear();

        if(code != null){
            codeInput.sendKeys(code);
        }

        verifyCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(VERIFY_CODE_BUTTON_XPATH)));
        verifyCodeButton.click();

        driver.switchTo().parentFrame();
    }

    public String findCode(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));

        codeMessage = driver.findElement(By.xpath(MESSAGE_XPATH));

        String code = codeMessage.getText().substring(23, 29);

        driver.switchTo().parentFrame();

        return code;
    }

    public void getErrorMessageCode(){
        String testCode = "000000";
        sendCode("alcachofas@comida.com");
        String code = findCode();
        if(code.equals(testCode)){
            testCode = "000001";
        }
        verifyCode(testCode);
    }

    public boolean isMessageDisplayed(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        emailErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MESSAGE_XPATH)));
        boolean messageDisplayed = emailErrorMessage.isDisplayed();
        driver.switchTo().parentFrame();
        return messageDisplayed;
    }

    public String getMessage(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(CONTENT_XPATH)));
        emailErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MESSAGE_XPATH)));
        String  message = emailErrorMessage.getText();
        driver.switchTo().parentFrame();
        return message;
    }
}
