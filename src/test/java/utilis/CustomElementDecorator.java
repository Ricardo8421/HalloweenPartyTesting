package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.util.List;

public class CustomElementDecorator implements WebElement {
    private static final Logger log = LogManager.getLogger(CustomElementDecorator.class);
    private final WebElement element;
    private final String elementName;

    public CustomElementDecorator(WebElement element, String elementName) {
        this.element = element;
        this.elementName = elementName;
    }

    public CustomElementDecorator(WebElement element) {
        this(element, "Unnamed Element");
    }


    @Override
    public void click() {
        log.info("Clicking on element: {}", elementName);
        element.click();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        log.info("Sending keys '{}' to element: {}", (Object) keysToSend, elementName);
        element.sendKeys(keysToSend);
    }

    @Override
    public boolean isDisplayed() {
        boolean displayed = element.isDisplayed();
        log.info("Checking visibility of element '{}': {}", elementName, displayed);
        return displayed;
    }

    @Override
    public String getText() {
        String text = element.getText();
        log.info("Retrieved text '{}' from element: {}", text, elementName);
        return text;
    }

    @Override
    public void clear() {
        log.info("Clearing element input field: {}", elementName);
        element.clear();
    }

    //methods of the webElement interface

    @Override
    public void submit() {
        element.submit();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override
    public Point getLocation() {
        return element.getLocation();
    }

    @Override
    public Dimension getSize() {
        return element.getSize();
    }

    @Override
    public Rectangle getRect() {
        return element.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return element.getScreenshotAs(target);
    }
}