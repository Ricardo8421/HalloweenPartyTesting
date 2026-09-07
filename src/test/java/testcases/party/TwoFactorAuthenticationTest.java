package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.TwoFactorAuthenticationPage;
import testcases.common.BaseTest;

public class TwoFactorAuthenticationTest extends BaseTest {
    final String EMAIL_ERROR_MESSAGE = "Please enter a valid email address";
    final String CODE_ERROR_MESSAGE = "Invalid code. Please try again.";
    final String SUCCESS_MESSAGE = "Verification successful!";
    TwoFactorAuthenticationPage twoFactorAuthenticationPage;

    @Test (priority = 1, groups = {"regression"})
    public void testEmptyEmail(){
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        twoFactorAuthenticationPage.sendCode(null);
        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testIncorrectEmail(){
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        twoFactorAuthenticationPage.sendCode("Alcachofas y más S.A. de C.V.");
        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testEmptyCode(){
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        twoFactorAuthenticationPage.sendCode("alcachofas@comida.com");
        twoFactorAuthenticationPage.verifyCode(null);
        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), CODE_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testIncorrectCode(){
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        twoFactorAuthenticationPage.getErrorMessageCode();
        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), CODE_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void test2FA(){
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        twoFactorAuthenticationPage.sendCode("alcachofas@comida.com");
        String code = twoFactorAuthenticationPage.findCode();
        twoFactorAuthenticationPage.verifyCode(code);
        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), SUCCESS_MESSAGE);
    }
}
