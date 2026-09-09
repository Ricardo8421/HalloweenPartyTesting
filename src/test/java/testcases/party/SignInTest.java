package testcases.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.SignInPage;
import testcases.common.BaseTest;

public class SignInTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(SignInTest.class);

    final String EMAIL_ERROR_MESSAGE = "Enter a valid email address.";
    final String PASSWORD_ERROR_MESSAGE = "Passwords can’t be nothing.";
    final String PASS_EMAIL_ERROR_MESSAGE = "The password/email address combo is incorrect.";
    final String EMAIL = "r15mez888@gmail.com";
    SignInPage signInPage;

    @Test (groups = {"regression"})
    public void testEmptyEmail(){
        log.info("Starting test: testEmptyEmail");
        signInPage = new SignInPage(getDriver());
        log.warn("Testing empty email validation");
        signInPage.sendSignInFormWithValues(null, "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"regression"})
    public void testIncorrectEmail(){
        log.info("Starting test: testIncorrectEmail");
        signInPage = new SignInPage(getDriver());
        log.debug("Testing invalid email format");
        signInPage.sendSignInFormWithValues("Alcachofas y más S.A. de C.V.", "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"regression"})
    public void testEmptyPassword(){
        log.info("Starting test: testEmptyPassword");
        signInPage = new SignInPage(getDriver());
        log.warn("Testing empty password validation");
        signInPage.sendSignInFormWithValues("r15mez888@gmail.com", null);

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(),PASSWORD_ERROR_MESSAGE);
    }

    @Test (groups = {"regression"})
    public void testIncorrectCredentials(){
        log.info("Starting test: testIncorrectCredentials");
        try {
            signInPage = new SignInPage(getDriver());
            log.debug("Testing login with unregistered credentials");
            signInPage.sendSignInFormWithValues("lecuhas@trenes.com", "patataypatat0");

            log.info("Asserting invalid credentials error message");
            Assert.assertTrue(signInPage.isDisplayedErrorMessage());
            Assert.assertEquals(signInPage.getErrorMessage(), PASS_EMAIL_ERROR_MESSAGE);
        } catch (AssertionError e) {
            log.error("Failed to validate incorrect credentials error message", e);
            throw e;
        }
    }

    @Test (groups = {"smoke"})
    public void testSignIn(){
        log.info("Starting test: testSignIn");
        signInPage = new SignInPage(getDriver());
        log.debug("Logging in with valid account: {}", EMAIL);
        signInPage.sendSignInFormWithValues(EMAIL, "patataypatat0");

        log.info("Asserting successful login message");
        Assert.assertTrue(signInPage.isDisplayedSuccessMessage());
        Assert.assertEquals(signInPage.getSuccessMessage(), EMAIL);
    }
}