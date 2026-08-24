package pages.party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class ContactUsPage extends BasePage {
    WebDriverWait wait = null;


    public ContactUsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        navigateContactUsPage();
    }

    public void navigateContactUsPage() {
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(1));
        WebElement contactUsForm = driver.findElement(By.xpath
                ("//*[@data-aid='CONTACT_FORM_CONTAINER_REND']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", contactUsForm);
       wait.until(ExpectedConditions.visibilityOf(contactUsForm));
    }

    public boolean isErrorElementEmail(){
        WebElement emailMessageError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@data-aid='CONTACT_EMAIL_ERR_REND']")));
       return emailMessageError.isDisplayed();
    }

    public String getErrorMessageEmail(){
        WebElement emailMessageError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@data-aid='CONTACT_EMAIL_ERR_REND']")));
        return emailMessageError.getText();
    }

    public void fillSingleElement(String xpath, String value){
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        input.clear();
        if(value != null){
            input.sendKeys(value);
        }
    }

    public void fillInformationContactUs(String firstNameUser, String lastNameUser, String emailUser, String phoneUser, String messageUser){
        String firstNameXpath = "//input[@data-aid='First Name']";
        String lastNameXpath = "//input[@data-aid='Last Name']";
        String emailXpath = "//input[@data-aid='CONTACT_FORM_EMAIL']";
        String phoneXpath = "//input[@data-aid='By entering a Phone Number you agree to our SMS Terms of Service']";
        String messageXpath = "//textarea[@data-aid='CONTACT_FORM_MESSAGE']";

        try{
            fillSingleElement(firstNameXpath, firstNameUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: First name input stale, retrying to fill...");
            fillSingleElement(firstNameXpath, firstNameUser);
        }
        try{
            fillSingleElement(lastNameXpath, lastNameUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Last name input stale, retrying to fill...");
            fillSingleElement(lastNameXpath, lastNameUser);
        }
        try{
            fillSingleElement(emailXpath, emailUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Email input stale, retrying to fill...");
            fillSingleElement(emailXpath, emailUser);
        }
        try{
            fillSingleElement(phoneXpath, phoneUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Phone input stale, retrying to fill...");
            fillSingleElement(phoneXpath, phoneUser);
        }
        try{
            fillSingleElement(messageXpath, messageUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Message input stale, retrying to fill...");
            fillSingleElement(messageXpath, messageUser);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try{
            js.executeAsyncScript("document.querySelector('[data-aid=\"CONTACT_SUBMIT_BUTTON_REND\"]').click();");
        }catch(ScriptTimeoutException e){
            System.out.println("Script timeout");
        }
    }

    public boolean isSuccessElementEmail(){
        WebElement successElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".c2-5d")));
        return successElement.isDisplayed();
    }

    public String getSuccessMessage(){
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.c2-1:nth-child(2) > p:nth-child(1)")));
        return successMessage.getText();
    }

}
