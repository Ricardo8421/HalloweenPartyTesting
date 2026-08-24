package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.PopUpPage;
import testcases.common.BaseTest;

public class PopUpTest extends BaseTest {
    PopUpPage popUpPage;

    @Test
    public void testPopUpClose(){
        popUpPage = new PopUpPage(getDriver());
        popUpPage.clickCloseButton();
        Assert.assertFalse(popUpPage.isPopUpPresent());
    }
}
