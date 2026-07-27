package party;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TwoFactorAuthentiationTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void redirectTo2FAPage(ITestContext context){
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        
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

    public void assertMessageWithString(String message){
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));

        WebElement emailErrorMessage = driver.findElement(By.xpath("//*[@id='message']"));
        Assert.assertTrue(emailErrorMessage.isDisplayed());
        Assert.assertEquals(emailErrorMessage.getText(), message);

        driver.switchTo().parentFrame();
    }

    @Test
    public void testEmptyEmail(){
        final String expectedErrorMessage = "Please enter a valid email address";

        sendCode(null);

        assertMessageWithString(expectedErrorMessage);
    }

    @Test
    public void testIncorrectEmail(){
        final String expectedErrorMessage = "Please enter a valid email address";

        sendCode("Alcachofas y más S.A. de C.V.");

        assertMessageWithString(expectedErrorMessage);
    }

    @Test
    public void testEmptyCode(){
        final String expectedErrorMessage = "Invalid code. Please try again.";

        sendCode("alcachofas@comida.com");
        verifyCode(null);

        assertMessageWithString(expectedErrorMessage);
    }

    @Test
    public void testIncorrectCode(){
        final String expectedErrorMessage = "Invalid code. Please try again.";
        String testCode = "000000";

        sendCode("alcachofas@comida.com");
        String code = findCode();
        if(code.equals(testCode)){
            testCode = "000001";
        }
        verifyCode(testCode);

        assertMessageWithString(expectedErrorMessage);
    }

    @Test
    public void test2FA(){
        final String expectedVerificationMessage = "Verification successful!";

        sendCode("alcachofas@comida.com");
        String code = findCode();
        verifyCode(code);

        assertMessageWithString(expectedVerificationMessage);
    }
}
