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
    By spanGuest =  By.xpath("/html/body/div[2]/div/div/div[2]/div/div/section/div/h1/span");
    By numberGuest = By.cssSelector("body > form:nth-child(1) > label:nth-child(1)");

    public HalloweenPartyPage(WebDriver driver) {
        super(driver);
        navigateToHalloweenPartyPage();
    }

    public void navigateToHalloweenPartyPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public boolean isGuestSpanDisplayed(){
        driver.switchTo().parentFrame();
        WebElement spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(spanGuest));
        return spanGuestElement.isDisplayed();
    }
    public String getGuestSpanName(){
        WebElement spanGuestElement = wait.until(ExpectedConditions.visibilityOfElementLocated(spanGuest));
        return spanGuestElement.getText();
    }

    public void guestFrameDisplayed(){
        try{
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//*[@id='iframe-06']")));
        }catch(NoSuchElementException e){
            System.out.println(driver.getPageSource());
            System.err.println(e.getStackTrace());
        }
    }

    public boolean isNumberGuestDisplayed(){
        driver.switchTo().frame("iframe-06");
        WebElement numberElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(numberGuest));
        return numberElement.isDisplayed();
    }

    public String getNumberGuestText(){
        WebElement numberGuestText = wait.until(ExpectedConditions.visibilityOfElementLocated(numberGuest));
        return numberGuestText.getText().trim();
    }

    public void verifyDropDown(){
        WebElement dropDownElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='guests']")));
        Select dropDown = new Select(dropDownElement);
        List<WebElement> options = dropDown.getOptions();
        options.forEach(option -> {
            dropDown.selectByValue(option.getText().trim());
        });
    }

    public boolean isImageDisplayed (){
        WebElement image = driver.findElement(By.xpath("//img[@data-aid='ABOUT_IMAGE_RENDERED0']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", image);
        return image.isDisplayed();
    }

    public boolean isErrorMessageDisplayed(){
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@data-aid='ABOUT_SECTION_TITLE_RENDERED']//span")));
        return errorMessageElement.isDisplayed();
    }

    public String getErrorMessage(){
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@data-aid='ABOUT_SECTION_TITLE_RENDERED']//span")));
        return errorMessageElement.getText();
    }

    public boolean isSubTitleErrorDisplayed(){
        WebElement subtitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@data-aid='ABOUT_HEADLINE_RENDERED0']")));
        return subtitle.isDisplayed();
    }

    public String getSubTitleErrorText(){
        WebElement subtitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[@data-aid='ABOUT_HEADLINE_RENDERED0']")));
        return subtitle.getText();
    }

    public boolean isFirstParagraphDisplayed(){
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[1]")));
        return p.isDisplayed();
    }

    public String getFirstParagraphText(){
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[1]")));
        return p.getText();
    }

    public boolean isSecondParagraphDisplayed(){
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[2]")));
        return p.isDisplayed();
    }

    public String getSecondParagraphText(){
        WebElement p =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[2]")));
        return p.getText();
    }

    public boolean isThirdParagraphDisplayed(){
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[3]")));
        return p.isDisplayed();
    }

    public String getThirdParagraphText(){
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-aid='ABOUT_DESCRIPTION_RENDERED0']/p[3]")));
        return p.getText();
    }


}
