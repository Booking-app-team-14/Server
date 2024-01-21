package student2_e2e.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import student2_e2e.helper.Helper;
import student2_e2e.pages.AccommodationDetailsPage;
import student2_e2e.pages.HomePage;
import student2_e2e.pages.LoginPage;
import student2_e2e.pages.ProfilePage;

public class ViewAndCancellationOfReservationsTest extends TestBase {

    static final String GUEST_USERNAME  = "guest1@guest.com";
    static final String OWNER_USERNAME  = "owner1@owner.com";
    static final String PASSWORD = "12345678";

    @Test
    public void test_reserve_accommodation_and_cancel_one_request_by_guest_then_approve_request_by_owner() {

        HomePage homePage = new HomePage(driver);
        homePage.clickOnLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened());

        loginPage.setUsername(GUEST_USERNAME);
        loginPage.setPassword(PASSWORD);
        loginPage.clickOnLoginButton();
        loginPage.clickOnAlertOK();

        Assert.assertTrue(homePage.isPageOpened());

        homePage.clickOnAccommodationDetailsButton();

        AccommodationDetailsPage accommodationDetailsPage = new AccommodationDetailsPage(driver);
        Assert.assertTrue(accommodationDetailsPage.isPageOpened());

        accommodationDetailsPage.setCheckIn("02102024");
        accommodationDetailsPage.setCheckOut("02132024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02062024");
        accommodationDetailsPage.setCheckOut("02072024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02082024");
        accommodationDetailsPage.setCheckOut("02102024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02102024");
        accommodationDetailsPage.setCheckOut("02112024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02112024");
        accommodationDetailsPage.setCheckOut("02122024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02122024");
        accommodationDetailsPage.setCheckOut("02132024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02132024");
        accommodationDetailsPage.setCheckOut("02152024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.setCheckIn("02142024");
        accommodationDetailsPage.setCheckOut("02152024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        // this reservation request will be cancelled by the user himself
        accommodationDetailsPage.setCheckIn("02162024");
        accommodationDetailsPage.setCheckOut("02182024");
        accommodationDetailsPage.setGuests("2");
        accommodationDetailsPage.clickOnReserveButton();
        accommodationDetailsPage.clickOnAlertOK();

        accommodationDetailsPage.clickOnProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isPageOpened());

        profilePage.clickOnGuestReservationsButton();
        Assert.assertTrue(profilePage.areReservationsDisplayed());

        profilePage.scrollToTheMiddleOfThePage();
        Helper.takeScreenshoot(driver, "reservation_requests_sent");

        WebElement card = profilePage.getReservationRequestCard("Feb 16, 2024", "Feb 18, 2024");
        Assert.assertNotNull(card);
        profilePage.clickOnCancelButtonInReservationRequestCard(card);

        profilePage.clickOnAlertOK(); // confirm yes
        profilePage.clickOnAlertOK(); // alert ok

        Assert.assertFalse(profilePage.isReservationRequestCard("Feb 16, 2024", "Feb 18, 2024"));

        Helper.takeScreenshoot(driver, "reservation_request_cancelled");

        profilePage.clickOnProfileButton();
        Assert.assertTrue(profilePage.isPageOpened());

        profilePage.clickOnSignOutButton();
        Assert.assertTrue(homePage.isPageOpened());

        Assert.assertTrue(homePage.isPageOpened());

        homePage.clickOnLoginButton();

        Assert.assertTrue(loginPage.isPageOpened());

        loginPage.setUsername(OWNER_USERNAME);
        loginPage.setPassword(PASSWORD);
        loginPage.clickOnLoginButton();
        loginPage.clickOnAlertOK();

        Assert.assertTrue(homePage.isPageOpened());

        homePage.clickOnProfileButton();

        Assert.assertTrue(profilePage.isPageOpened());

        profilePage.clickOnOwnerCheckReservationsButton();
        Assert.assertTrue(profilePage.areReservationsDisplayed());

        WebElement approveCard = profilePage.getReservationRequestCard("Feb 10, 2024", "Feb 13, 2024");
        Assert.assertNotNull(card);
        profilePage.clickOnApproveButtonInReservationRequestCard(approveCard);

        profilePage.clickOnAlertOK();

        profilePage.clickOnApprovedRadioButton();

        WebElement acceptedCard = profilePage.getReservationRequestCard("Feb 10, 2024", "Feb 13, 2024");
        Assert.assertNotNull(acceptedCard);

        profilePage.scrollToBottomALittle();
        Helper.takeScreenshoot(driver, "reservation_request_accepted");

        profilePage.scrollToTopOfThePage();
        profilePage.clickOnDeclinedRadioButton();

        profilePage.scrollToBottomALittle();
        Helper.takeScreenshoot(driver, "reservation_requests_declined");

        // check declined reservation requests
        WebElement cardDeclined1 = profilePage.getReservationRequestCard("Feb 8, 2024", "Feb 10, 2024");
        Assert.assertNotNull(cardDeclined1);

        WebElement cardDeclined2 = profilePage.getReservationRequestCard("Feb 10, 2024", "Feb 11, 2024");
        Assert.assertNotNull(cardDeclined2);

        WebElement cardDeclined3 = profilePage.getReservationRequestCard("Feb 11, 2024", "Feb 12, 2024");
        Assert.assertNotNull(cardDeclined3);

        WebElement cardDeclined4 = profilePage.getReservationRequestCard("Feb 12, 2024", "Feb 13, 2024");
        Assert.assertNotNull(cardDeclined4);

        WebElement cardDeclined5 = profilePage.getReservationRequestCard("Feb 13, 2024", "Feb 15, 2024");
        Assert.assertNotNull(cardDeclined5);

        profilePage.scrollToTopOfThePage();

        // check reservation requests that are still pending
        profilePage.clickOnOnWaitRadioButton();

        profilePage.scrollToTheMiddleOfThePage();
        Helper.takeScreenshoot(driver, "reservation_requests_still_pending");

        WebElement cardPending1 = profilePage.getReservationRequestCard("Feb 6, 2024", "Feb 7, 2024");
        Assert.assertNotNull(cardPending1);

        WebElement cardPending2 = profilePage.getReservationRequestCard("Feb 14, 2024", "Feb 15, 2024");
        Assert.assertNotNull(cardPending2);

    }

}
