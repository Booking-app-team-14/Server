package student2_e2e.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    @FindBy(css = "#non-primary-btn")
    private WebElement loginButton;

    @FindBy(css = ".card:nth-child(1) .button-details")
    private WebElement accommodationDetailsButton;

    @FindBy(css = "#testimonials-header")
    private WebElement testimonialsHeader;

    @FindBy(css = ".profile-button")
    private WebElement profileButton;

    public HomePage(WebDriver driver){
        this.driver = driver;
        String PAGE_URL = "http://localhost:4200";
        driver.get(PAGE_URL);

        PageFactory.initElements(driver, this);
    }

    public void clickOnLoginButton(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickOnAccommodationDetailsButton(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(accommodationDetailsButton)).click();
    }

    public void clickOnProfileButton(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(profileButton)).click();
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOf(testimonialsHeader)).isDisplayed();
    }

}