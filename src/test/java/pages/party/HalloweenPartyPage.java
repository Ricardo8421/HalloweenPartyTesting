package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.common.BasePage;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class HalloweenPartyPage extends BasePage {
    WebDriverWait wait = null;
    final String SPAN_GUEST_XPATH = "/html/body/div[2]/div/div/div[2]/div/div/section/div/h1/span";
    final String MORE_DROPDOWN_XPATH = "//*[@id='2']";
    final String GUEST_FRAME_XPATH = "//*[@id='iframe-06']";
    final String DROPDOWN_ELEMENT_XPATH = "//*[@id='guests']";
    final String IMAGE_XPATH = "//img[@data-aid='ABOUT_IMAGE_RENDERED0']";
    final String ERROR_MESSAGE_ELEMENT_XPATH = "//h1[@data-aid='ABOUT_SECTION_TITLE_RENDERED']//span";
    final String SUBTITLE_XPATH = "//h4[@data-aid='ABOUT_HEADLINE_RENDERED0']";
    final String FIRST_PARAGRAPH_XPATH = "//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[1]";
    final String SECOND_PARAGRAPH_XPATH = "//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[2]";
    final String THIRD_PARAGRAPH_XPATH = "//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[3]";
    final String NUMBER_GUEST_CSS_SELECTOR = "body > form:nth-child(1) > label:nth-child(1)";
    final String HALLOWEEN_LINK_CSS_SELECTOR = "li.nav-item:nth-child(4) > a:nth-child(1)";
    final String HALLOWEEN_LINK_DROP_CSS_SELECTOR = "li.visible:nth-child(4) > a:nth-child(1)";

    By spanGuest =  By.xpath(SPAN_GUEST_XPATH);
    By numberGuest = By.cssSelector(NUMBER_GUEST_CSS_SELECTOR);

    private WebElement halloweenLink = null;
    private WebElement moreDropdown = null;
    private WebElement actionPartyButton = null;
    private WebElement spanGuestElement = null;
    private WebElement numberGuestElement = null;
    private WebElement dropDownElement = null;
    private WebElement image = null;
    private WebElement errorMessageElement = null;
    private WebElement subtitleElement = null;
    private WebElement p = null;
    private WebElement halloweenLinkDrop = null;

    public HalloweenPartyPage(WebDriver driver) {
        super(driver);
        navigateToHalloweenPartyPage();
    }

    public void navigateToHalloweenPartyPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://candymapper.com/");

        halloweenLink = driver.findElement(By.cssSelector(HALLOWEEN_LINK_CSS_SELECTOR));

        if (halloweenLink.isDisplayed()) {
            halloweenLink.click();
        }else{
            moreDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(MORE_DROPDOWN_XPATH)));
            moreDropdown.click();
            halloweenLinkDrop = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(HALLOWEEN_LINK_DROP_CSS_SELECTOR)));
            halloweenLinkDrop.click();
        }
    }

    public void clickPartyButton(String action) {
        final String ACTION_PARTY_BUTTON = "//a[contains(text(), \""+action+"\")]";

        actionPartyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ACTION_PARTY_BUTTON)));
        actionPartyButton.click();
    }

    public boolean isGuestSpanDisplayed(){
        driver.switchTo().parentFrame();
        spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(spanGuest));
        return spanGuestElement.isDisplayed();
    }
    public String getGuestSpanName(){
        spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(spanGuest));
        return spanGuestElement.getText();
    }

    public void guestFrameDisplayed(){
        try{
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(GUEST_FRAME_XPATH)));
        }catch(NoSuchElementException e){
            System.out.println(driver.getPageSource());
            System.err.println(e.getStackTrace());
        }
    }

    public boolean isNumberGuestDisplayed(){
        driver.switchTo().frame("iframe-06");
        numberGuestElement=  wait.until(ExpectedConditions.visibilityOfElementLocated(numberGuest));
        return numberGuestElement.isDisplayed();
    }

    public String getNumberGuestText(){
        numberGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(numberGuest));
        return numberGuestElement.getText().trim();
    }

    public void verifyDropDown(){
        dropDownElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DROPDOWN_ELEMENT_XPATH)));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        options.forEach(option -> {
            dropDown.selectByValue(option.getText().trim());
        });
    }

    public boolean isImageDisplayed (){
        image = driver.findElement(By.xpath(IMAGE_XPATH));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", image);
        return image.isDisplayed();
    }

    public boolean isErrorMessageDisplayed(){
        errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_ELEMENT_XPATH)));
        return errorMessageElement.isDisplayed();
    }

    public String getErrorMessage(){
        errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_ELEMENT_XPATH)));
        return errorMessageElement.getText();
    }

    public boolean isSubTitleErrorDisplayed(){
        subtitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUBTITLE_XPATH)));
        return subtitleElement.isDisplayed();
    }

    public String getSubTitleErrorText(){
        subtitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUBTITLE_XPATH)));
        return subtitleElement.getText();
    }

    public boolean isFirstParagraphDisplayed(){
        p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_PARAGRAPH_XPATH)));
        return p.isDisplayed();
    }

    public String getFirstParagraphText(){
        p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_PARAGRAPH_XPATH)));
        return p.getText();
    }

    public boolean isSecondParagraphDisplayed(){
        p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SECOND_PARAGRAPH_XPATH)));
        return p.isDisplayed();
    }

    public String getSecondParagraphText(){
        p =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SECOND_PARAGRAPH_XPATH)));
        return p.getText();
    }

    public boolean isThirdParagraphDisplayed(){
        p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(THIRD_PARAGRAPH_XPATH)));
        return p.isDisplayed();
    }

    public String getThirdParagraphText(){
        p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(THIRD_PARAGRAPH_XPATH)));
        return p.getText();
    }

}
