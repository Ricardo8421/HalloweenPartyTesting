package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.CreateAccountPage;
import testcases.common.BaseTest;

public class CreateAccountTest extends BaseTest {
    final String FIRSTNAME_ERROR_MESSAGE = "Enter your first name.";
    final String LASTNAME_ERROR_MESSAGE = "Enter your last name.";
    final String EMAIL_ERROR_MESSAGE = "Enter a valid email address.";
    final String INSTRUCTION_HEADER_MESSAGE = "Check your email";
    final String INSTRUCTION_DESCRIPTION_MESSAGE = "You're almost there! We sent an email to alcachofas@comida.com with a link to activate your account. Please check your email and click the activation link.";

    CreateAccountPage createAccountPage;
    @Test
    public void testEmptyFirstName(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues(null, "Alcachofa", "alcachofas@comida.com", null);
        Assert.assertTrue(createAccountPage.isFirstNameDisplayed());
        Assert.assertEquals(createAccountPage.getFirstNameErrorMessage(), FIRSTNAME_ERROR_MESSAGE);
    }

    @Test
    public void testEmptyLastName(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues("Juanito", null, "alcachofas@comida.com", null);
        Assert.assertTrue(createAccountPage.isLastNameDisplayed());
        Assert.assertEquals(createAccountPage.getLastNameErrorMessage(), LASTNAME_ERROR_MESSAGE);
    }

    @Test
    public void testEmptyEmail(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", null, null);
        Assert.assertTrue(createAccountPage.isEmailDisplayed());
        Assert.assertEquals(createAccountPage.getEmailErrorMessage(), EMAIL_ERROR_MESSAGE);
    }
    
    @Test
    public void testIncorrectEmail(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "Alcachofas y más S.A. de C.V.", null);
        Assert.assertTrue(createAccountPage.isEmailDisplayed());
        Assert.assertEquals(createAccountPage.getEmailErrorMessage(), EMAIL_ERROR_MESSAGE);
    }

    @Test
    public void testCreateAccountWithoutPhone(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", null);
        Assert.assertTrue(createAccountPage.isInstructionHeaderDisplayed());
        Assert.assertTrue(createAccountPage.isInstructionDescriptionDisplayed());
        Assert.assertEquals(createAccountPage.getInstructionHeaderErrorMessage(), INSTRUCTION_HEADER_MESSAGE);
        Assert.assertEquals(createAccountPage.getInstructionDescriptionErrorMessage(), INSTRUCTION_DESCRIPTION_MESSAGE);
    }

    @Test
    public void testCreateAccountWithPhone(){
        createAccountPage = new CreateAccountPage(getDriver());
        createAccountPage.sendCreateAccountFormWithValues("Juanito", "Alcachofa", "alcachofas@comida.com", "5512345678");
        Assert.assertTrue(createAccountPage.isInstructionHeaderDisplayed());
        Assert.assertTrue(createAccountPage.isInstructionDescriptionDisplayed());
        Assert.assertEquals(createAccountPage.getInstructionHeaderErrorMessage(), INSTRUCTION_HEADER_MESSAGE);
        Assert.assertEquals(createAccountPage.getInstructionDescriptionErrorMessage(), INSTRUCTION_DESCRIPTION_MESSAGE);
    }
}
