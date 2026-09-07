package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.ContactUsPage;
import testcases.common.BaseTest;

public class ContactUsTest extends BaseTest {
    ContactUsPage contactUsPage;
    final String EMAIL_MESSAGE = "Please enter a valid email address.";
    final String SUCCESS_TEXT = "Thank you for your inquiry! We will get back to you within 48 Years.";

    @Test (priority = 1, groups = {"regression"})
    public void testSendEmptyEmail () {
        contactUsPage = new ContactUsPage(getDriver());
        contactUsPage.fillInformationContactUs("test","test"," ","test","test");
        Assert.assertTrue(contactUsPage.isErrorElementEmail());
        Assert.assertEquals(contactUsPage.getErrorMessageEmail(), EMAIL_MESSAGE);

    }

    @Test (priority = 2, groups = {"regression"})
    public void testWrongFormatEmail () {
        contactUsPage = new ContactUsPage(getDriver());
        contactUsPage.fillInformationContactUs("Juanito","Alcachofa",
                "emailtestemail.com","00000","test");
        Assert.assertTrue(contactUsPage.isErrorElementEmail());
        Assert.assertEquals(contactUsPage.getErrorMessageEmail(), EMAIL_MESSAGE);
    }

    @Test (priority = 3, groups = {"regression"})
    public void testSendCorrectInformation (){
        contactUsPage = new ContactUsPage(getDriver());
        contactUsPage.fillInformationContactUs("Juanito", "Alcachofa",
                "email@testemail.com", "00000","test");
    
        Assert.assertTrue(contactUsPage.isSuccessElementEmail());
        Assert.assertEquals(contactUsPage.getSuccessMessage(), SUCCESS_TEXT);
    }

}
