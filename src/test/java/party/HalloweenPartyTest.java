package party;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class HalloweenPartyTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void setUp(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get("https://candymapper.com/");

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(":2.container"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=':2.noAutoPopup']"))).click();
        driver.switchTo().parentFrame();

        WebElement halloweenLink = driver.findElement(By.cssSelector("li.nav-item:nth-child(4) > a:nth-child(1)"));

        if (halloweenLink.isDisplayed()) {
            halloweenLink.click();
        }else{
            WebElement moreDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='2']")));
            moreDropdown.click();
            WebElement halloweenLinkDrop = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("li.visible:nth-child(4) > a:nth-child(1)")));
            halloweenLinkDrop.click();
        }
    }

    public void clickPartyButton(String action) {
        WebElement actionPartyButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), '"+action+"')]")));
        actionPartyButton.click();
    }

    public void verifyGuest (){
        final String BRINGING_GUEST = "Are you bringing any guests?";
        final String NUMBER_GUESTS = "Heck yeah, I'm bringing my friends! There is safety in numbers: ";

        WebElement spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div/div/div[2]/div/div/section/div/h1/span")));
        Assert.assertEquals(spanGuestElement.getText(), BRINGING_GUEST);

        System.out.println(driver.getPageSource());

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));

        WebElement numberElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > form:nth-child(1) > label:nth-child(1)")));
        Assert.assertEquals(numberElement.getText().trim(), NUMBER_GUESTS.trim());

        WebElement dropDownElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='guests']")));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        options.forEach(option -> {
            dropDown.selectByValue(option.getText().trim());
        });
    }

    @Test
    public void testHostZombieParty() {
        clickPartyButton("I Am Hosting A Party");
        clickPartyButton("Zombies");
        verifyGuest();

    }

    @Test
    public void testHostGhostParty() {
        clickPartyButton("I Am Hosting A Party");
        clickPartyButton("Ghosts");
        verifyGuest();
    }
}
