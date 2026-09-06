package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class SignInPage extends BasePage {
    WebDriverWait wait = null;
    final String CREATE_ACCOUNT_LINK_XPATH = "//*[contains(@id, 'membership-sign-in')]";
    final String SIGN_IN_BUTTON_XPATH = "//*[@data-aid='MEMBERSHIP_SSO_SUBMIT']";
    final String ERROR_MESSAGE_XPATH = "//*[@data-aid='MEMBERSHIP_SSO_ERR_REND']";
    final String SIGNED_IN_EMAIL_XPATH = "//*[@data-aid='ACCOUNT_DETAILS_EMAIL_REND']//p";

    private WebElement sessionIcon = null;
    private WebElement createAccountLink = null;
    private WebElement emailInput = null;
    private WebElement passwordInput = null;
    private WebElement signInButton = null;
    private WebElement errorMessage = null;
    private WebElement signedInEmail = null;

    public SignInPage(WebDriver driver) {
        super(driver);
        navigateToSignIn();
    }

    public void navigateToSignIn(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        sessionIcon = driver.findElement(By.id("4"));
        sessionIcon.click();

        createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CREATE_ACCOUNT_LINK_XPATH)));
        createAccountLink.click();
    }

    public void sendSignInFormWithValues(String email, String password){
        emailInput = driver.findElement(By.name("email"));
        passwordInput = driver.findElement(By.name("password"));

        emailInput.clear();
        passwordInput.clear();

        if(email != null){
            emailInput.sendKeys(email);
        }
        if(password != null){
            passwordInput.sendKeys(password);
        }

        signInButton = driver.findElement(By.xpath(SIGN_IN_BUTTON_XPATH));
        signInButton.click();
    }

    public boolean isDisplayedErrorMessage(){
        errorMessage = driver.findElement(By.xpath(ERROR_MESSAGE_XPATH));
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage(){
        errorMessage = driver.findElement(By.xpath(ERROR_MESSAGE_XPATH));
        return errorMessage.getText();
    }

    public boolean isDisplayedSuccessMessage(){
        signedInEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SIGNED_IN_EMAIL_XPATH)));
        return signedInEmail.isDisplayed();
    }

    public String getSuccessMessage(){
        signedInEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SIGNED_IN_EMAIL_XPATH)));
        return signedInEmail.getText();
    }
}
