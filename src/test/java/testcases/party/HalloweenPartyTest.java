package testcases.party;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.party.HalloweenPartyPage;
import testcases.common.BaseTest;

public class HalloweenPartyTest extends BaseTest {
    HalloweenPartyPage halloweenPartyPage;

    final String BRINGING_GUEST = "Are you bringing any guests?";
    final String NUMBER_GUESTS = "Heck yeah, I'm bringing my friends! There is safety in numbers: ";
    final String TITLE_TEXT = "Error 404 Page Not Found";
    final String SUBTITLE_TEXT = "Whoopsies... How did we end up here?";
    final String DESCRIPTION_TEXT_1 = "You probably were trying to exit from the Halloween Party path";
    final String DESCRIPTION_TEXT_2 = "Thank you for finding this bug! ";
    final String DESCRIPTION_TEXT_3 = "The Jira ticket has been submitted via temporal vortex and fixed in CandyMapperR2.com";


    @Test
    public void testHostZombieParty() {
        halloweenPartyPage = new HalloweenPartyPage(getDriver());
        halloweenPartyPage.clickPartyButton("I Am Hosting A Party");
        halloweenPartyPage.clickPartyButton("Zombies");
        halloweenPartyPage.guestFrameDisplayed();
        Assert.assertTrue(halloweenPartyPage.isGuestSpanDisplayed());
        Assert.assertEquals(halloweenPartyPage.getGuestSpanName(), BRINGING_GUEST);
        Assert.assertTrue(halloweenPartyPage.isNumberGuestDisplayed());
        Assert.assertEquals(halloweenPartyPage.getNumberGuestText(), NUMBER_GUESTS.trim());
        halloweenPartyPage.verifyDropDown();
    }

    @Test
    public void testHostGhostParty() {
        halloweenPartyPage = new HalloweenPartyPage(getDriver());
        halloweenPartyPage.clickPartyButton("I Am Hosting A Party");
        halloweenPartyPage.clickPartyButton("Ghosts");
        halloweenPartyPage.guestFrameDisplayed();
        Assert.assertTrue(halloweenPartyPage.isGuestSpanDisplayed());
        Assert.assertEquals(halloweenPartyPage.getGuestSpanName(), BRINGING_GUEST);
        Assert.assertTrue(halloweenPartyPage.isNumberGuestDisplayed());
        Assert.assertEquals(halloweenPartyPage.getNumberGuestText(), NUMBER_GUESTS.trim());
        halloweenPartyPage.verifyDropDown();
    }

    @Test
    public void testAttendZombieton(){
        halloweenPartyPage = new HalloweenPartyPage(getDriver());
        halloweenPartyPage.clickPartyButton("I Am Attending A Party");
        halloweenPartyPage.clickPartyButton("Zombieton");
        halloweenPartyPage.guestFrameDisplayed();
        Assert.assertTrue(halloweenPartyPage.isGuestSpanDisplayed());
        Assert.assertEquals(halloweenPartyPage.getGuestSpanName(), BRINGING_GUEST);
        Assert.assertTrue(halloweenPartyPage.isNumberGuestDisplayed());
        Assert.assertEquals(halloweenPartyPage.getNumberGuestText(), NUMBER_GUESTS.trim());
        halloweenPartyPage.verifyDropDown();
    }
    
    @Test
    public void testAttendGhostville(){
        halloweenPartyPage = new HalloweenPartyPage(getDriver());
        halloweenPartyPage.clickPartyButton("I Am Attending A Party");
        halloweenPartyPage.clickPartyButton("Ghostville");
        halloweenPartyPage.guestFrameDisplayed();
        Assert.assertTrue(halloweenPartyPage.isGuestSpanDisplayed());
        Assert.assertEquals(halloweenPartyPage.getGuestSpanName(), BRINGING_GUEST);
        Assert.assertTrue(halloweenPartyPage.isNumberGuestDisplayed());
        Assert.assertEquals(halloweenPartyPage.getNumberGuestText(), NUMBER_GUESTS.trim());
        halloweenPartyPage.verifyDropDown();
    }

    @Test
    public void testAttendImScared(){
        halloweenPartyPage = new HalloweenPartyPage(getDriver());
        halloweenPartyPage.clickPartyButton("I Am Attending A Party");
        halloweenPartyPage.clickPartyButton("I'm Scared, Let's Go Back!");
        Assert.assertTrue(halloweenPartyPage.isErrorMessageDisplayed());
        Assert.assertEquals(halloweenPartyPage.getErrorMessage(),TITLE_TEXT);
        Assert.assertTrue(halloweenPartyPage.isImageDisplayed());
        Assert.assertTrue(halloweenPartyPage.isSubTitleErrorDisplayed());
        Assert.assertEquals(halloweenPartyPage.getSubTitleErrorText(),SUBTITLE_TEXT);
        Assert.assertTrue(halloweenPartyPage.isFirstParagraphDisplayed());
        Assert.assertEquals(halloweenPartyPage.getFirstParagraphText(),DESCRIPTION_TEXT_1);
        Assert.assertTrue(halloweenPartyPage.isSecondParagraphDisplayed());
        Assert.assertEquals(halloweenPartyPage.getSecondParagraphText(),DESCRIPTION_TEXT_2);
        Assert.assertTrue(halloweenPartyPage.isThirdParagraphDisplayed());
        Assert.assertEquals(halloweenPartyPage.getThirdParagraphText(),DESCRIPTION_TEXT_3);
    }
}
