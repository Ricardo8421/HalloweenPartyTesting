package pages.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class HalloweenPartyPage extends BasePage {
    private static final Logger log = LogManager.getLogger(HalloweenPartyPage.class);

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

    @FindBy(css = HALLOWEEN_LINK_CSS_SELECTOR)
    private WebElement halloweenLink;
    @FindBy(xpath = MORE_DROPDOWN_XPATH)
    private WebElement moreDropdown;
    @FindBy(xpath = SPAN_GUEST_XPATH)
    private WebElement spanGuestElement;
    @FindBy(css = NUMBER_GUEST_CSS_SELECTOR)
    private WebElement numberGuestElement;
    @FindBy(xpath = DROPDOWN_ELEMENT_XPATH)
    private WebElement dropDownElement;
    @FindBy(xpath = IMAGE_XPATH)
    private WebElement image;
    @FindBy(xpath = ERROR_MESSAGE_ELEMENT_XPATH)
    private WebElement errorMessageElement;
    @FindBy(xpath = SUBTITLE_XPATH)
    private WebElement subtitleElement;
    @FindBy(css = HALLOWEEN_LINK_DROP_CSS_SELECTOR)
    private WebElement halloweenLinkDrop;
    @FindBy(xpath = FIRST_PARAGRAPH_XPATH)
    private WebElement firstParagraph;
    @FindBy(xpath = SECOND_PARAGRAPH_XPATH)
    private WebElement secondParagraph;
    @FindBy(xpath = THIRD_PARAGRAPH_XPATH)
    private WebElement thirdParagraph;

    private WebElement actionPartyButton = null;

    public HalloweenPartyPage(WebDriver driver) {
        super(driver);
        log.info("Initializing HalloweenPartyPage");
        navigateToHalloweenPartyPage();
    }

    public void navigateToHalloweenPartyPage() {
        log.info("Navigating to Halloween Party Page");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
        driver.get("https://candymapper.com/");

        if (halloweenLink.isDisplayed()) {
            log.debug("Halloween link directly visible. Clicking link.");
            halloweenLink.click();
        } else {
            log.warn("Halloween link not directly visible. Expanding 'More' dropdown menu.");
            wait.until(ExpectedConditions.elementToBeClickable(moreDropdown));
            moreDropdown.click();
            wait.until(ExpectedConditions.visibilityOf(halloweenLinkDrop));
            halloweenLinkDrop.click();
        }
    }

    public void clickPartyButton(String action) {
        log.info("Clicking party button with action text: '{}'", action);
        final String ACTION_PARTY_BUTTON = "//a[contains(text(), \"" + action + "\")]";
        actionPartyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ACTION_PARTY_BUTTON)));
        actionPartyButton.click();
    }

    public boolean isGuestSpanDisplayed() {
        log.info("Checking guest span element display status after switching to parent frame");
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.visibilityOf(spanGuestElement));
        boolean isDisplayed = spanGuestElement.isDisplayed();
        log.debug("Guest span display status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getGuestSpanName() {
        log.info("Fetching guest span text");
        wait.until(ExpectedConditions.visibilityOf(spanGuestElement));
        String text = spanGuestElement.getText();
        log.debug("Retrieved guest span text: '{}'", text);
        return text;
    }

    public void guestFrameDisplayed() {
        log.info("Attempting to switch to guest iframe: '{}'", GUEST_FRAME_XPATH);
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(GUEST_FRAME_XPATH)));
            log.debug("Successfully switched to guest iframe");
        } catch (NoSuchElementException e) {
            log.error("Failed to locate guest iframe. Page source snippet dumped for debug.", e);
            log.debug("Page Source: {}", driver.getPageSource());
        }
    }

    public boolean isNumberGuestDisplayed() {
        log.info("Switching to 'iframe-06' to check guest number element display");
        driver.switchTo().frame("iframe-06");
        wait.until(ExpectedConditions.visibilityOf(numberGuestElement));
        boolean isDisplayed = numberGuestElement.isDisplayed();
        log.debug("Number guest display status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getNumberGuestText() {
        log.info("Retrieving guest number text");
        wait.until(ExpectedConditions.visibilityOf(numberGuestElement));
        String text = numberGuestElement.getText().trim();
        log.debug("Guest number text: '{}'", text);
        return text;
    }

    public void verifyDropDown() {
        log.info("Verifying dropdown options in Halloween Party Page");
        wait.until(ExpectedConditions.visibilityOf(dropDownElement));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        log.debug("Found {} options in guest dropdown", options.size());
        options.forEach(option -> {
            String val = option.getText().trim();
            log.debug("Selecting dropdown option: '{}'", val);
            dropDown.selectByValue(val);
        });
    }

    public boolean isImageDisplayed() {
        log.info("Scrolling into view and checking image display");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", image);
        boolean isDisplayed = image.isDisplayed();
        log.debug("Image display status: {}", isDisplayed);
        return isDisplayed;
    }

    public boolean isErrorMessageDisplayed() {
        log.info("Checking error message display status");
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        boolean isDisplayed = errorMessageElement.isDisplayed();
        log.debug("Error message display status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getErrorMessage() {
        log.info("Retrieving error message text");
        wait.until(ExpectedConditions.visibilityOf(errorMessageElement));
        String text = errorMessageElement.getText();
        log.debug("Error message text: '{}'", text);
        return text;
    }

    public boolean isSubTitleErrorDisplayed() {
        log.info("Checking subtitle error display status");
        wait.until(ExpectedConditions.visibilityOf(subtitleElement));
        boolean isDisplayed = subtitleElement.isDisplayed();
        log.debug("Subtitle error display status: {}", isDisplayed);
        return isDisplayed;
    }

    public String getSubTitleErrorText() {
        log.info("Retrieving subtitle error text");
        wait.until(ExpectedConditions.visibilityOf(subtitleElement));
        String text = subtitleElement.getText();
        log.debug("Subtitle error text: '{}'", text);
        return text;
    }

    public boolean isFirstParagraphDisplayed() {
        log.info("Verifying first paragraph display");
        wait.until(ExpectedConditions.visibilityOf(firstParagraph));
        return firstParagraph.isDisplayed();
    }

    public String getFirstParagraphText() {
        log.info("Fetching first paragraph text");
        wait.until(ExpectedConditions.visibilityOf(firstParagraph));
        return firstParagraph.getText();
    }

    public boolean isSecondParagraphDisplayed() {
        log.info("Verifying second paragraph display");
        wait.until(ExpectedConditions.visibilityOf(secondParagraph));
        return secondParagraph.isDisplayed();
    }

    public String getSecondParagraphText() {
        log.info("Fetching second paragraph text");
        wait.until(ExpectedConditions.visibilityOf(secondParagraph));
        return secondParagraph.getText();
    }

    public boolean isThirdParagraphDisplayed() {
        log.info("Verifying third paragraph display");
        wait.until(ExpectedConditions.visibilityOf(thirdParagraph));
        return thirdParagraph.isDisplayed();
    }

    public String getThirdParagraphText() {
        log.info("Fetching third paragraph text");
        wait.until(ExpectedConditions.visibilityOf(thirdParagraph));
        return thirdParagraph.getText();
    }
}