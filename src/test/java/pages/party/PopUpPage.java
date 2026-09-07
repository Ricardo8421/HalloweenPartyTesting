package pages.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class PopUpPage extends BasePage {
    private static final Logger log = LogManager.getLogger(PopUpPage.class);

    final String CLOSE_POPUP = "popup-widget5912-close-icon";
    final String POPUP_ELEMENT = "popup-widget5912";

    @FindBy(id = CLOSE_POPUP)
    private WebElement closeButton;
    @FindBy(id = POPUP_ELEMENT)
    private WebElement popUpElement;

    public PopUpPage(WebDriver driver) {
        super(driver);
        log.info("Initializing PopUpPage and waiting for popup element presence");
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        log.debug("Waiting for popup element '{}' with timeout of {} seconds", POPUP_ELEMENT, waitSeconds);
        new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                .until(d -> d.findElement(By.id(POPUP_ELEMENT)));
        log.info("PopUp element located successfully");
    }

    public void clickCloseButton() {
        log.info("Clicking popup close button");
        closeButton.click();
        log.debug("Popup close button clicked");
    }

    public boolean isPopUpPresent() {
        log.info("Verifying if popup element is currently displayed");
        boolean isDisplayed = popUpElement.isDisplayed();
        log.debug("Popup display status: {}", isDisplayed);
        return isDisplayed;
    }
}