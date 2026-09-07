package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;
import pages.party.config.ConfigReader;

import java.time.Duration;

public class PopUpPage extends BasePage {

  final String CLOSE_POPUP = "popup-widget5912-close-icon";
  final String POPUP_ELEMENT = "popup-widget5912";

  @FindBy (id = CLOSE_POPUP)
  private WebElement closeButton;
  @FindBy (id = POPUP_ELEMENT)
  private WebElement popUpElement;

    public PopUpPage(WebDriver driver) {
        super(driver);
        int waitSeconds = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                .until(d -> d.findElement(By.id(POPUP_ELEMENT)));
    }

    public void clickCloseButton() {
        closeButton.click();
    }

    public boolean isPopUpPresent() {
        return popUpElement.isDisplayed();
    }

}
