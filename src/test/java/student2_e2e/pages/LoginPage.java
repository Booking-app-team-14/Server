package student2_e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    @FindBy(css = "#username-input")
    WebElement usernameInput;

    @FindBy(css = "#password-input")
    WebElement passwordInput;

    @FindBy(css = "#login-button")
    WebElement loginButton;

    @FindBy(css = "#login-header")
    WebElement loginHeader;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String username){
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void setPassword(String password){
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickOnLoginButton(){
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickOnAlertOK(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.alertIsPresent()).accept();
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(loginHeader)).isDisplayed();
    }

}
