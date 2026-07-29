package party;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class HalloweenPartyTest {
    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeMethod
    public void setUp(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get("https://candymapper.com/");

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
                By.xpath("//a[contains(text(), \""+action+"\")]")));
        actionPartyButton.click();
    }

    public void verifyGuest (){
        final String BRINGING_GUEST = "Are you bringing any guests?";
        final String NUMBER_GUESTS = "Heck yeah, I'm bringing my friends! There is safety in numbers: ";

        WebElement spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div/div/div[2]/div/div/section/div/h1/span")));
        Assert.assertEquals(spanGuestElement.getText(), BRINGING_GUEST);

        try{
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));
        }catch(NoSuchElementException e){
            System.out.println(driver.getPageSource());
            System.err.println(e.getStackTrace());
        }

        WebElement numberElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > form:nth-child(1) > label:nth-child(1)")));
        Assert.assertEquals(numberElement.getText().trim(), NUMBER_GUESTS.trim());

        WebElement dropDownElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='guests']")));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        options.forEach(option -> {
            dropDown.selectByValue(option.getText().trim());
        });
    }

    public void assertText(String xpath, String expectedText){
        WebElement textElement = driver.findElement(By.xpath(xpath));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", textElement);
        Assert.assertTrue(textElement.isDisplayed());
        Assert.assertEquals(textElement.getText(), expectedText);
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

    @Test
    public void testAttendZombieton(){
        clickPartyButton("I Am Attending A Party");
        clickPartyButton("Zombieton");
        verifyGuest();
    }
    
    @Test
    public void testAttendGhostville(){
        clickPartyButton("I Am Attending A Party");
        clickPartyButton("Ghostville");
        verifyGuest();
    }

    @Test
    public void testAttendImScared(){
        clickPartyButton("I Am Attending A Party");
        clickPartyButton("I'm Scared, Let's Go Back!");

        final String TITLE_TEXT = "Error 404 Page Not Found";
        final String SUBTITLE_TEXT = "Whoopsies... How did we end up here?";
        final String DESCRIPTION_TEXT_1 = "You probably were trying to exit from the Halloween Party path";
        final String DESCRIPTION_TEXT_2 = "Thank you for finding this bug! ";
        final String DESCRIPTION_TEXT_3 = "The Jira ticket has been submitted via temporal vortex and fixed in CandyMapperR2.com";
        
        assertText("//h1[@data-aid='ABOUT_SECTION_TITLE_RENDERED']//span", TITLE_TEXT);

        WebElement image = driver.findElement(By.xpath("//img[@data-aid='ABOUT_IMAGE_RENDERED0']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", image);
        Assert.assertTrue(image.isDisplayed());

        assertText("//h4[@data-aid='ABOUT_HEADLINE_RENDERED0']", SUBTITLE_TEXT);
        assertText("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[1]", DESCRIPTION_TEXT_1);
        assertText("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[2]", DESCRIPTION_TEXT_2);
        assertText("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[3]", DESCRIPTION_TEXT_3);
    }
}
