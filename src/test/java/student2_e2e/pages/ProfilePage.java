package student2_e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {

    private WebDriver driver;

    @FindBy(css = ".titles:nth-child(1)")
    private WebElement profileHeader;

    @FindBy(css = "#guest-reservations")
    private WebElement guestReservationsButton;

    @FindBy(css = "#reservation-requests-header")
    private WebElement reservationRequestsHeader;

    @FindBy(css = ".profile-button")
    private WebElement profileButton;

    @FindBy(css = "#sign-out-button")
    private WebElement signOutButton;

    @FindBy(css = "#check-reservations-button")
    private WebElement checkReservationsButton;

    @FindBy(css = "#approved-radio-button")
    private WebElement approvedRadioButton;

    @FindBy(css = "#on-wait-radio-button")
    private WebElement onWaitRadioButton;

    @FindBy(css = "#declined-radio-button")
    private WebElement declinedRadioButton;

    public ProfilePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnGuestReservationsButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(guestReservationsButton)).click();
    }

    public void clickOnOwnerCheckReservationsButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(checkReservationsButton)).click();
    }

    public WebElement getReservationRequestCard(String startDate, String endDate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".reservation-card"), 0));
        List<WebElement> reservationCards = driver.findElements(By.cssSelector(".reservation-card"));
        for (WebElement reservationCard : reservationCards) {
            if (reservationCard.findElement(By.cssSelector(".request-start-date")).getText().contains(startDate) &&
                reservationCard.findElement(By.cssSelector(".request-end-date")).getText().contains(endDate)) {
                    return reservationCard;
            }
        }
        return null;
    }

    public void clickOnCancelButtonInReservationRequestCard(WebElement reservationCard) {
        reservationCard.findElement(By.cssSelector(".cancel")).click();
    }

    public void clickOnApproveButtonInReservationRequestCard(WebElement reservationCard) {
        reservationCard.findElement(By.cssSelector(".approve")).click();
    }

    public void clickOnAlertOK() {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.alertIsPresent()).accept();
    }


    public boolean isReservationRequestCard(String startDate, String endDate) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cancelled-request-confirmation")));
        List<WebElement> reservationCards = driver.findElements(By.cssSelector(".reservation-card"));
        for (WebElement reservationCard : reservationCards) {
            if (reservationCard.findElement(By.cssSelector(".request-start-date")).getText().contains(startDate) &&
                    reservationCard.findElement(By.cssSelector(".request-end-date")).getText().contains(endDate)) {
                return true;
            }
        }
        return false;
    }

    public boolean areReservationsDisplayed() {
        return (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(reservationRequestsHeader)).isDisplayed();
    }

    public void clickOnProfileButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(profileButton)).click();
    }

    public void clickOnSignOutButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(signOutButton)).click();
    }

    public void clickOnApprovedRadioButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(approvedRadioButton)).click();
    }

    public void clickOnOnWaitRadioButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(onWaitRadioButton)).click();
    }

    public void clickOnDeclinedRadioButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(declinedRadioButton)).click();
    }

    public void scrollToTopOfThePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to the top of the page
        js.executeScript("window.scrollTo(0, 0)");

        // Wait until the scroll position reaches the top
        wait.until(webDriver -> {
            Number scrollY = (Number) js.executeScript("return window.scrollY;");
            System.out.println("Checking scroll position - Scroll Y: " + scrollY.doubleValue());
            return scrollY.doubleValue() == 0.0;
        });
    }

    public void scrollToBottomALittle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to the middle of the page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 4)");

        // Wait until the scroll position reaches the middle
        wait.until(webDriver -> {
            Number innerHeight = (Number) js.executeScript("return window.innerHeight;");
            Number scrollY = (Number) js.executeScript("return window.scrollY;");
            Number bodyScrollHeight = (Number) js.executeScript("return document.body.scrollHeight;");

            double innerHeightValue = innerHeight.doubleValue();
            double scrollYValue = scrollY.doubleValue();
            double bodyScrollHeightValue = bodyScrollHeight.doubleValue();

            System.out.println("Checking scroll position - Inner height: " + innerHeightValue + ", Scroll Y: " + scrollYValue + ", Body scroll height: " + bodyScrollHeightValue);

            double margin = 10.0; // Adjust as needed
            return (innerHeightValue + scrollYValue + margin) >= bodyScrollHeightValue / 4;
        });
    }

    public void scrollToTheMiddleOfThePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to the middle of the page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2)");

        // Wait until the scroll position reaches the middle
        wait.until(webDriver -> {
            Number innerHeight = (Number) js.executeScript("return window.innerHeight;");
            Number scrollY = (Number) js.executeScript("return window.scrollY;");
            Number bodyScrollHeight = (Number) js.executeScript("return document.body.scrollHeight;");

            double innerHeightValue = innerHeight.doubleValue();
            double scrollYValue = scrollY.doubleValue();
            double bodyScrollHeightValue = bodyScrollHeight.doubleValue();

            System.out.println("Checking scroll position - Inner height: " + innerHeightValue + ", Scroll Y: " + scrollYValue + ", Body scroll height: " + bodyScrollHeightValue);

            double margin = 10.0; // Adjust as needed
            return (innerHeightValue + scrollYValue + margin) >= bodyScrollHeightValue / 2;
        });
    }

    public boolean isPageOpened() {
        return (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(profileHeader)).isDisplayed();
    }

}
