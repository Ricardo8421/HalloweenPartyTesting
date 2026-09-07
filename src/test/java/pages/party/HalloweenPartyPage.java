package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.common.BasePage;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class HalloweenPartyPage extends BasePage {
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


    @FindBy (css = HALLOWEEN_LINK_CSS_SELECTOR)
    private WebElement halloweenLink;
    @FindBy (xpath = MORE_DROPDOWN_XPATH)
    private WebElement moreDropdown;
    @FindBy (xpath = SPAN_GUEST_XPATH)
    private WebElement spanGuestElement;
    @FindBy (css = NUMBER_GUEST_CSS_SELECTOR)
    private WebElement numberGuestElement;
    @FindBy (xpath = DROPDOWN_ELEMENT_XPATH)
    private WebElement dropDownElement;
    @FindBy (xpath = IMAGE_XPATH)
    private WebElement image;
    @FindBy (xpath = ERROR_MESSAGE_ELEMENT_XPATH)
    private WebElement errorMessageElement;
    @FindBy (xpath = SUBTITLE_XPATH)
    private WebElement subtitleElement;
    @FindBy (css = HALLOWEEN_LINK_DROP_CSS_SELECTOR)
    private WebElement halloweenLinkDrop;
    @FindBy ( xpath = FIRST_PARAGRAPH_XPATH)
    private WebElement firstParagraph;
    @FindBy (xpath = SECOND_PARAGRAPH_XPATH)
    private WebElement secondParagraph;
    @FindBy (xpath = THIRD_PARAGRAPH_XPATH)
    private WebElement thirdParagraph;

    private WebElement actionPartyButton = null;


    public HalloweenPartyPage(WebDriver driver) {
        super(driver);
        navigateToHalloweenPartyPage();
    }

    public void navigateToHalloweenPartyPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://candymapper.com/");

        if (halloweenLink.isDisplayed()) {
            halloweenLink.click();
        }else{
            wait.until(ExpectedConditions.elementToBeClickable(moreDropdown));
            moreDropdown.click();
            wait.until(ExpectedConditions.visibilityOf(halloweenLinkDrop));
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
        wait.until(ExpectedConditions.visibilityOf(spanGuestElement));
        return spanGuestElement.isDisplayed();
    }
    public String getGuestSpanName(){
        wait.until(ExpectedConditions.visibilityOf(spanGuestElement));
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
        wait.until(ExpectedConditions.visibilityOf(numberGuestElement));
        return numberGuestElement.isDisplayed();
    }

    public String getNumberGuestText(){
        wait.until(ExpectedConditions.visibilityOf(numberGuestElement));
        return numberGuestElement.getText().trim();
    }

    public void verifyDropDown(){
        wait.until(ExpectedConditions.visibilityOf(dropDownElement));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        options.forEach(option -> {
            dropDown.selectByValue(option.getText().trim());
        });
    }

    public boolean isImageDisplayed (){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", image);
        return image.isDisplayed();
    }

    public boolean isErrorMessageDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        return errorMessageElement.isDisplayed();
    }

    public String getErrorMessage(){
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        return errorMessageElement.getText();
    }

    public boolean isSubTitleErrorDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(subtitleElement));
        return subtitleElement.isDisplayed();
    }

    public String getSubTitleErrorText(){
        wait.until(ExpectedConditions.visibilityOf(subtitleElement));
        return subtitleElement.getText();
    }

    public boolean isFirstParagraphDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(firstParagraph));
        return firstParagraph.isDisplayed();
    }

    public String getFirstParagraphText(){
        wait.until(ExpectedConditions.visibilityOf(firstParagraph));
        return firstParagraph.getText();
    }

    public boolean isSecondParagraphDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(secondParagraph));
        return secondParagraph.isDisplayed();
    }

    public String getSecondParagraphText(){
        wait.until(ExpectedConditions.visibilityOf(secondParagraph));
        return secondParagraph.getText();
    }

    public boolean isThirdParagraphDisplayed(){
        wait.until(ExpectedConditions.visibilityOf(thirdParagraph));
        return thirdParagraph.isDisplayed();
    }

    public String getThirdParagraphText(){
        wait.until(ExpectedConditions.visibilityOf(thirdParagraph));
        return thirdParagraph.getText();
    }

}
