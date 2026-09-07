package testcases.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.CreateAccountPage;
import testcases.common.BaseTest;

public class CreateAccountTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(CreateAccountTest.class);

    final String FIRSTNAME_ERROR_MESSAGE = "Enter your first name.";
    final String LASTNAME_ERROR_MESSAGE = "Enter your last name.";
    final String EMAIL_ERROR_MESSAGE = "Enter a valid email address.";
    final String INSTRUCTION_HEADER_MESSAGE = "Check your email";
    final String INSTRUCTION_DESCRIPTION_MESSAGE = "You're almost there! We sent an email to alcachofas@comida.com with a link to activate your account. Please check your email and click the activation link.";

    CreateAccountPage createAccountPage;

    @Test (groups = {"smoke"})
    public void testEmptyFirstName(){
        log.info("Starting test: testEmptyFirstName");
        createAccountPage = new CreateAccountPage(getDriver());
        log.warn("Triggering validation: Passing null for first name");
        createAccountPage.sendCreateAccountFormWithValues(null, "Alcachofa", "alcachofas@comida.com", null);

        log.info("Asserting first name error message");
        Assert.assertTrue(createAccountPage.isFirstNameDisplayed());
        Assert.assertEquals(createAccountPage.getFirstNameErrorMessage(), FIRSTNAME_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testEmptyLastName(){
        log.info("Starting test: testEmptyLastName");
        createAccountPage = new CreateAccountPage(getDriver());
        log.warn("Triggering validation: Passing null for last name");
        createAccountPage.sendCreateAccountFormWithValues("Juanito", null, "alcachofas@comida.com", null);

        log.info("Asserting last name error message");
        Assert.assertTrue(createAccountPage.isLastNameDisplayed());
        Assert.assertEquals(createAccountPage.getLastNameErrorMessage(), LASTNAME_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testEmptyEmail(){
        log.info("Starting test: testEmptyEmail");
        createAccountPage = new CreateAccountPage(getDriver());
        log.warn("Triggering validation: Passing null for email");
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", null, null);

        log.info("Asserting email error message");
        Assert.assertTrue(createAccountPage.isEmailDisplayed());
        Assert.assertEquals(createAccountPage.getEmailErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testIncorrectEmail(){
        log.info("Starting test: testIncorrectEmail");
        createAccountPage = new CreateAccountPage(getDriver());
        log.debug("Injecting malformed email string");
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "Alcachofas y más S.A. de C.V.", null);

        Assert.assertTrue(createAccountPage.isEmailDisplayed());
        Assert.assertEquals(createAccountPage.getEmailErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test (groups = {"smoke"})
    public void testCreateAccountWithoutPhone(){
        log.info("Starting test: testCreateAccountWithoutPhone");
        try {
            createAccountPage = new CreateAccountPage(getDriver());
            log.debug("Submitting valid account data without phone number");
            createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", null);

            log.info("Asserting instruction header and description");
            Assert.assertTrue(createAccountPage.isInstructionHeaderDisplayed());
            Assert.assertTrue(createAccountPage.isInstructionDescriptionDisplayed());
            Assert.assertEquals(createAccountPage.getInstructionHeaderErrorMessage(), INSTRUCTION_HEADER_MESSAGE);
            Assert.assertEquals(createAccountPage.getInstructionDescriptionErrorMessage(), INSTRUCTION_DESCRIPTION_MESSAGE);
        } catch (Exception e) {
            log.error("Unexpected error occurred during testCreateAccountWithoutPhone", e);
            throw e;
        }
    }

    @Test (groups = {"smoke"})
    public void testCreateAccountWithPhone(){
        log.info("Starting test: testCreateAccountWithPhone");
        createAccountPage = new CreateAccountPage(getDriver());
        log.debug("Submitting valid account data including phone number");
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", "5512345678");

        Assert.assertTrue(createAccountPage.isInstructionHeaderDisplayed());
        Assert.assertTrue(createAccountPage.isInstructionDescriptionDisplayed());
        Assert.assertEquals(createAccountPage.getInstructionHeaderErrorMessage(), INSTRUCTION_HEADER_MESSAGE);
        Assert.assertEquals(createAccountPage.getInstructionDescriptionErrorMessage(), INSTRUCTION_DESCRIPTION_MESSAGE);
    }
}