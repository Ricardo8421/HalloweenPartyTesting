package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    protected void click(WebElement element, String elementName) {
        new utils.CustomElementDecorator(element, elementName).click();
    }

    protected void sendKeys(WebElement element, String value, String elementName) {
        new utils.CustomElementDecorator(element, elementName).sendKeys(value);
    }

    protected boolean isDisplayed(WebElement element, String elementName) {
        return new utils.CustomElementDecorator(element, elementName).isDisplayed();
    }

    protected String getText(WebElement element, String elementName){
        return new utils.CustomElementDecorator(element, elementName).getText();
    }

    protected void clear(WebElement element, String elementName){
        new utils.CustomElementDecorator(element, elementName).clear();
    }
}
