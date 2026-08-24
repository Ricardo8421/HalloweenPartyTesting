package pages.party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.common.BasePage;

import java.time.Duration;

public class TwoFactorAuthenticationPage extends BasePage {
    WebDriverWait wait = null;

    public TwoFactorAuthenticationPage(WebDriver driver) {
        super(driver);
        navigateTo2FAPage();
    }

    public void navigateTo2FAPage(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(":2.container"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=':2.noAutoPopup']"))).click();
        driver.switchTo().parentFrame();


        WebElement moreDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='2']")));
        moreDropdown.click();

        WebElement expectedElement = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div/div/section/div[3]/div[2]/div/nav/div[2]/div/div[2]/div[1]/nav/ul/li[15]/ul/li[14]/a"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", expectedElement);

        try{
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[1]/div/div/section/div[3]/div[2]/div/nav/div[2]/div/div[2]/div[1]/nav/ul/li[15]/ul/li[14]/a"))).click();
        }catch(ElementClickInterceptedException e){
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/div[1]/div/div/section/div[3]/div[2]/div/nav/div[2]/div/div[2]/div[1]/nav/ul/li[15]/ul/li[14]/a"))).click();
        }
    }

    public void sendCode(String email){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));

        WebElement emailInput = driver.findElement(By.xpath("//*[@id='email']"));

        emailInput.clear();

        if(email != null){
            emailInput.sendKeys(email);
        }

        WebElement sendCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='email-section']//button")));
        sendCodeButton.click();

        driver.switchTo().parentFrame();
    }

    public void verifyCode(String code){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));

        WebElement codeInput = driver.findElement(By.xpath("//*[@id='code']"));

        codeInput.clear();

        if(code != null){
            codeInput.sendKeys(code);
        }

        WebElement verifyCodeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='verificationSection']//button")));
        verifyCodeButton.click();

        driver.switchTo().parentFrame();
    }

    public String findCode(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));

        WebElement codeMessage = driver.findElement(By.xpath("//*[@id='message']"));

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
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));
        WebElement emailErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='message']")));
        boolean messageDisplayed = emailErrorMessage.isDisplayed();
        driver.switchTo().parentFrame();
        return messageDisplayed;
    }

    public String getMessage(){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));
        WebElement emailErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='message']")));
        String  message = emailErrorMessage.getText();
        driver.switchTo().parentFrame();
        return message;
    }
}
