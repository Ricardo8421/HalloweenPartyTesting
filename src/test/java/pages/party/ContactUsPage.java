package pages.party;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class ContactUsPage extends BasePage {
    WebDriverWait wait = null;
    private final String CONTACT_US_FORM_XPATH = "//*[@data-aid='CONTACT_FORM_CONTAINER_REND']";
    private final String EMAIL_MESSAGE_ERROR_XPATH = "//*[@data-aid='CONTACT_EMAIL_ERR_REND']";
    private final String FIRST_NAME_XPATH = "//input[@data-aid='First Name']";
    private final String LAST_NAME_XPATH = "//input[@data-aid='Last Name']";
    private final String EMAIL_XPATH = "//input[@data-aid='CONTACT_FORM_EMAIL']";
    private final String PHONE_XPATH = "//input[@data-aid='By entering a Phone Number you agree to our SMS Terms of Service']";
    private final String MESSAGE_XPATH = "//textarea[@data-aid='CONTACT_FORM_MESSAGE']";
    private final String SUCCESSFUL_MESSAGE_CSS = ".c2-5d";
    private final String SUCCESSFUL_TEXT_CSS = "div.c2-1:nth-child(2) > p:nth-child(1)";

    public ContactUsPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        navigateContactUsPage();
    }

    public void navigateContactUsPage() {
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(1));
        WebElement contactUsForm = driver.findElement(By.xpath
                (CONTACT_US_FORM_XPATH));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", contactUsForm);
        wait.until(ExpectedConditions.visibilityOf(contactUsForm));
    }

    public boolean isErrorElementEmail(){
        WebElement emailMessageError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(EMAIL_MESSAGE_ERROR_XPATH)));
        return emailMessageError.isDisplayed();
    }

    public String getErrorMessageEmail(){
        WebElement emailMessageError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(EMAIL_MESSAGE_ERROR_XPATH )));
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

        try{
            fillSingleElement(FIRST_NAME_XPATH, firstNameUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: First name input stale, retrying to fill...");
            fillSingleElement(FIRST_NAME_XPATH, firstNameUser);
        }
        try{
            fillSingleElement(LAST_NAME_XPATH, lastNameUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Last name input stale, retrying to fill...");
            fillSingleElement(LAST_NAME_XPATH, lastNameUser);
        }
        try{
            fillSingleElement(EMAIL_XPATH, emailUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Email input stale, retrying to fill...");
            fillSingleElement(EMAIL_XPATH, emailUser);
        }
        try{
            fillSingleElement(PHONE_XPATH, phoneUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Phone input stale, retrying to fill...");
            fillSingleElement(PHONE_XPATH, phoneUser);
        }
        try{
            fillSingleElement(MESSAGE_XPATH, messageUser);
        }catch(StaleElementReferenceException e){
            System.out.println("ContactUsTest: Message input stale, retrying to fill...");
            fillSingleElement(MESSAGE_XPATH, messageUser);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        try{
            js.executeAsyncScript("document.querySelector('[data-aid=\"CONTACT_SUBMIT_BUTTON_REND\"]').click();");
        }catch(ScriptTimeoutException e){
            System.out.println("Script timeout");
        }
    }

    public boolean isSuccessElementEmail(){
        WebElement successElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SUCCESSFUL_MESSAGE_CSS)));
        return successElement.isDisplayed();
    }

    public String getSuccessMessage(){
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(SUCCESSFUL_TEXT_CSS)));
        return successMessage.getText();
    }

}
