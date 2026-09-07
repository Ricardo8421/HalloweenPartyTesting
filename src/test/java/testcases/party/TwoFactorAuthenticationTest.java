package testcases.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.TwoFactorAuthenticationPage;
import testcases.common.BaseTest;

public class TwoFactorAuthenticationTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(TwoFactorAuthenticationTest.class);

    final String EMAIL_ERROR_MESSAGE = "Please enter a valid email address";
    final String CODE_ERROR_MESSAGE = "Invalid code. Please try again.";
    final String SUCCESS_MESSAGE = "Verification successful!";
    TwoFactorAuthenticationPage twoFactorAuthenticationPage;

    @Test (priority = 1, groups = {"regression"})
    public void testEmptyEmail(){
        log.info("Starting test: testEmptyEmail (2FA)");
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        log.warn("Sending null email to trigger validation");
        twoFactorAuthenticationPage.sendCode(null);

        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testIncorrectEmail(){
        log.info("Starting test: testIncorrectEmail (2FA)");
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        log.debug("Sending invalid email string to 2FA input");
        twoFactorAuthenticationPage.sendCode("Alcachofas y más S.A. de C.V.");

        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testEmptyCode(){
        log.info("Starting test: testEmptyCode (2FA)");
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        log.debug("Sending valid email, then testing empty code verification");
        twoFactorAuthenticationPage.sendCode("alcachofas@comida.com");
        log.warn("Verifying with null code");
        twoFactorAuthenticationPage.verifyCode(null);

        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), CODE_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void testIncorrectCode(){
        log.info("Starting test: testIncorrectCode (2FA)");
        twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
        log.debug("Executing incorrect code fallback scenario");
        twoFactorAuthenticationPage.getErrorMessageCode();

        Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
        Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), CODE_ERROR_MESSAGE);
    }

    @Test (priority = 1, groups = {"regression"})
    public void test2FA(){
        log.info("Starting test: test2FA (Happy Path)");
        try {
            twoFactorAuthenticationPage = new TwoFactorAuthenticationPage(getDriver());
            log.debug("Requesting code for valid email");
            twoFactorAuthenticationPage.sendCode("alcachofas@comida.com");

            String code = twoFactorAuthenticationPage.findCode();
            log.info("Submitting extracted code for verification");
            twoFactorAuthenticationPage.verifyCode(code);

            log.info("Asserting verification success message");
            Assert.assertTrue(twoFactorAuthenticationPage.isMessageDisplayed());
            Assert.assertEquals(twoFactorAuthenticationPage.getMessage(), SUCCESS_MESSAGE);
        } catch (AssertionError e) {
            log.error("2FA Verification assertion failed", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during 2FA test execution", e);
            throw e;
        }
    }
}