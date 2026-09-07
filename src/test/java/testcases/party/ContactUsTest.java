package testcases.party;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.ContactUsPage;
import testcases.common.BaseTest;

public class ContactUsTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ContactUsTest.class);

    ContactUsPage contactUsPage;
    final String EMAIL_MESSAGE = "Please enter a valid email address.";
    final String SUCCESS_TEXT = "Thank you for your inquiry! We will get back to you within 48 Years.";

    @Test (priority = 1, groups = {"regression"})
    public void testSendEmptyEmail () {
        log.info("Starting test: testSendEmptyEmail");
        try {
            contactUsPage = new ContactUsPage(getDriver());
            log.warn("Executing negative test scenario: Sending empty email");
            log.debug("Test Data -> First Name: 'test', Last Name: 'test', Email: ' ', Phone: 'test', Message: 'test'");
            contactUsPage.fillInformationContactUs("test","test"," ","test","test");

            log.info("Verifying error element is displayed for empty email");
            Assert.assertTrue(contactUsPage.isErrorElementEmail());
            Assert.assertEquals(contactUsPage.getErrorMessageEmail(), EMAIL_MESSAGE);
            log.info("Test testSendEmptyEmail completed successfully");
        } catch (AssertionError e) {
            log.error("Assertion failed in testSendEmptyEmail", e);
            throw e;
        }
    }

    @Test (priority = 2, groups = {"regression"})
    public void testWrongFormatEmail () {
        log.info("Starting test: testWrongFormatEmail");
        try {
            contactUsPage = new ContactUsPage(getDriver());
            log.warn("Executing negative test scenario: Sending wrong format email");
            log.debug("Test Data -> Email: 'emailtestemail.com'");
            contactUsPage.fillInformationContactUs("Juanito","Alcachofa",
                    "emailtestemail.com","00000","test");

            log.info("Verifying error element is displayed for wrong email format");
            Assert.assertTrue(contactUsPage.isErrorElementEmail());
            Assert.assertEquals(contactUsPage.getErrorMessageEmail(), EMAIL_MESSAGE);
            log.info("Test testWrongFormatEmail completed successfully");
        } catch (AssertionError e) {
            log.error("Assertion failed in testWrongFormatEmail", e);
            throw e;
        }
    }

    @Test (priority = 3, groups = {"regression"})
    public void testSendCorrectInformation (){
        log.info("Starting test: testSendCorrectInformation");
        try {
            contactUsPage = new ContactUsPage(getDriver());
            log.debug("Test Data -> Valid inputs provided for all fields");
            contactUsPage.fillInformationContactUs("Juanito", "Alcachofa",
                    "email@testemail.com", "00000","test");

            log.info("Verifying successful submission message is displayed");
            Assert.assertTrue(contactUsPage.isSuccessElementEmail());
            Assert.assertEquals(contactUsPage.getSuccessMessage(), SUCCESS_TEXT);
            log.info("Test testSendCorrectInformation completed successfully");
        } catch (AssertionError e) {
            log.error("Assertion failed in testSendCorrectInformation", e);
            throw e;
        }
    }
}