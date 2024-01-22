package pages;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTest {


    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {

        System.setProperty("webdriver.chrome.driver", "chrome.exe");


        driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testAccommodationSearchWithDifferentValidInputs() {
        driver.get("http://localhost:4200/search");

        SearchPage searchPage = new SearchPage(driver);

        searchPage.enterCheckInDate("2024-03-01");
        searchPage.enterNumberOfGuests(2);
        searchPage.selectAccommodationType("Apartment");
        searchPage.clickSearchButton();
        int expectedSearchResultsCount1 = 3;
        int actualSearchResultsCount1 = searchPage.getSearchResultsCount();
        assertEquals(expectedSearchResultsCount1, actualSearchResultsCount1, "Test 1 failed.");

        searchPage.enterCheckInDate("2024-04-01");
        searchPage.enterNumberOfGuests(4);
        searchPage.selectAccommodationType("Villa");
        searchPage.clickSearchButton();
        int expectedSearchResultsCount2 = 2;
        int actualSearchResultsCount2 = searchPage.getSearchResultsCount();
        assertEquals(expectedSearchResultsCount2, actualSearchResultsCount2, "Test 2 failed.");


        searchPage.enterCheckInDate("2024-05-01");
        searchPage.enterNumberOfGuests(3);
        searchPage.selectAccommodationType("Hotel");
        searchPage.selectPriceRange("price100-200");
        searchPage.clickSearchButton();
        int expectedSearchResultsCount3 = 4;
        int actualSearchResultsCount3 = searchPage.getSearchResultsCount();
        assertEquals(expectedSearchResultsCount3, actualSearchResultsCount3, "Test 3 failed.");

        driver.quit();
    }
}
