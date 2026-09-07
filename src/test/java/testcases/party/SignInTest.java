package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.SignInPage;
import testcases.common.BaseTest;

public class SignInTest extends BaseTest {
    final String EMAIL_ERROR_MESSAGE = "Enter a valid email address.";
    final String PASSWORD_ERROR_MESSAGE = "Passwords can’t be nothing.";
    final String PASS_EMAIL_ERROR_MESSAGE = "The password/email address combo is incorrect.";
    final String EMAIL = "r15mez888@gmail.com";
    SignInPage signInPage;

    @Test (groups = {"smoke"})
    public void testEmptyEmail(){
        signInPage = new SignInPage(getDriver());
        signInPage.sendSignInFormWithValues(null, "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testIncorrectEmail(){
        signInPage = new SignInPage(getDriver());
        signInPage.sendSignInFormWithValues("Alcachofas y más S.A. de C.V.", "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testEmptyPassword(){
        signInPage = new SignInPage(getDriver());
        signInPage.sendSignInFormWithValues("r15mez888@gmail.com", null);

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(),PASSWORD_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testIncorrectCredentials(){
        signInPage = new SignInPage(getDriver());
        signInPage.sendSignInFormWithValues("lecuhas@trenes.com", "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedErrorMessage());
        Assert.assertEquals(signInPage.getErrorMessage(), PASS_EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testSignIn(){
        signInPage = new SignInPage(getDriver());
        signInPage.sendSignInFormWithValues(EMAIL, "patataypatat0");

        Assert.assertTrue(signInPage.isDisplayedSuccessMessage());
        Assert.assertEquals(signInPage.getSuccessMessage(), EMAIL);
    }
}
