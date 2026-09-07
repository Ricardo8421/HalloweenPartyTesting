package testcases.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.PopUpPage;
import testcases.common.BaseTest;

public class PopUpTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(PopUpTest.class);

    PopUpPage popUpPage;

    @Test (groups = {"smoke", "regression"})
    public void testPopUpClose(){
        log.info("Starting test: testPopUpClose");
        try {
            popUpPage = new PopUpPage(getDriver());
            log.debug("Invoking clickCloseButton on PopUpPage");
            popUpPage.clickCloseButton();

            log.info("Asserting that popup is no longer present");
            Assert.assertFalse(popUpPage.isPopUpPresent());
        } catch (AssertionError e) {
            log.error("Popup was still present after clicking close", e);
            throw e;
        }
    }
}