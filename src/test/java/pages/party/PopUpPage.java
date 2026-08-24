package pages.party;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.common.BasePage;

import java.time.Duration;

public class PopUpPage extends BasePage {
    public PopUpPage(WebDriver driver) {
        super(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(By.id("popup-widget5912")));
    }

    public void clickCloseButton() {
        driver.findElement(By.id("popup-widget5912-close-icon")).click();
    }

    public boolean isPopUpPresent() {
        return driver.findElement(By.id("popup-widget5912")).isDisplayed();
    }

}
