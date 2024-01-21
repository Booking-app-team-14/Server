package student2_e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccommodationDetailsPage {

    private WebDriver driver;

    @FindBy(css = "#check-in")
    private WebElement checkInInput;

    @FindBy(css = "#check-out")
    private WebElement checkOutInput;

    @FindBy(css = "#guests")
    private WebElement guestsInput;

    @FindBy(css = ".reserve-button")
    private WebElement reserveButton;

    @FindBy(css = ".profile-button")
    private WebElement profileButton;

    public AccommodationDetailsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setCheckIn(String checkIn){
        checkInInput.clear();
        checkInInput.sendKeys(checkIn);
    }

    public void setCheckOut(String checkOut){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#check-out"))).isDisplayed();
        checkOutInput.clear();
        checkOutInput.sendKeys(checkOut);
    }

    public void setGuests(String guests){
        guestsInput.clear();
        guestsInput.sendKeys(guests);
    }

    public void clickOnReserveButton(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(reserveButton)).click();
    }

    public void clickOnAlertOK(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void clickOnProfileButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(profileButton)).click();
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(reserveButton)).isDisplayed();
    }

}
