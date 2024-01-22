package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SearchPage {
    private final WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterCheckInDate(String date) {
        WebElement checkInDateInput = driver.findElement(By.id("checkInDate"));
        checkInDateInput.sendKeys(date);
    }

    public void enterCheckOutDate(String date) {
        WebElement checkOutDateInput = driver.findElement(By.id("checkOutDate"));
        checkOutDateInput.sendKeys(date);
    }

    public void enterNumberOfGuests(int numberOfGuests) {
        WebElement numberOfGuestsInput = driver.findElement(By.id("numberOfGuests"));
        numberOfGuestsInput.sendKeys(String.valueOf(numberOfGuests));
    }

    public void selectAccommodationType(String type) {
        WebElement accommodationTypeSelect = driver.findElement(By.id("accommodationType"));
        Select select = new Select(accommodationTypeSelect);
        select.selectByVisibleText(type);
    }

    public void enterSearchTerm(String searchTerm) {
        WebElement searchInput = driver.findElement(By.cssSelector(".input-field"));
        searchInput.sendKeys(searchTerm);
    }

    public void selectPriceRange(String priceRange) {
        WebElement priceCheckbox = driver.findElement(By.id(priceRange));
        priceCheckbox.click();
    }

    public void selectMinRating(int rating) {
        WebElement minRatingSelect = driver.findElement(By.className("select-field"));
        Select select = new Select(minRatingSelect);
        select.selectByValue(String.valueOf(rating));
    }

    public void selectAmenities(String... amenities) {
        for (String amenity : amenities) {
            WebElement amenityCheckbox = driver.findElement(By.id(amenity));
            amenityCheckbox.click();
        }
    }

    public void clickSearchButton() {
        WebElement searchButton = driver.findElement(By.cssSelector(".search-button"));
        searchButton.click();
    }

    public int getSearchResultsCount() {

        return 0;
    }
}
