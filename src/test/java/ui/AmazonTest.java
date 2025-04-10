package ui;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.VideoGamesPage;
import config.ConfigReader;

public class AmazonTest extends BaseTest {

    HomePage homePage;
    LoginPage loginPage;

    VideoGamesPage videoGamesPage;

    @Test
    public void placeOrder() {
        WebDriver driver = getBrowser();
        getTest().log(Status.INFO, "Navigating to Sign In page");
        homePage = new HomePage(driver);
        homePage.navigateToSignIn();

        getTest().log(Status.INFO, "Logging in");
        loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getEmail(), ConfigReader.getPassword());

        String actualName = homePage.getUserName();
        getTest().log(Status.INFO, "Validating user name: " + actualName);
        Assert.assertEquals(actualName, ConfigReader.getName());

        getTest().log(Status.INFO, "Navigating to Video Games section");
        homePage.openFullMenu();
        homePage.selectVideoGames();
        homePage.selectAllVideoGames();

        String title = homePage.getTitle();
        getTest().log(Status.INFO, "Page title: " + title);
        Assert.assertEquals(title, "Video Games");

        videoGamesPage = new VideoGamesPage(driver);
        getTest().log(Status.INFO, "Applying filters: Free Shipping + New Condition");
        videoGamesPage.selectFreeShipping();
        videoGamesPage.selectNewConditions();

        String url = driver.getCurrentUrl();
        getTest().log(Status.INFO, "Validating URL filters: " + url);
        Assert.assertTrue(url.contains("p_n_free_shipping_eligible"), "Missing free shipping in URL");
        Assert.assertTrue(url.contains("p_n_condition-type"), "Missing condition filter in URL");

        getTest().log(Status.INFO, "Sorting high to low by price");
        videoGamesPage.sortHighToLow();
        Assert.assertTrue(driver.getCurrentUrl().contains("s=price-desc-rank"), "Sort not applied properly");

        getTest().log(Status.INFO, "Adding products under max price to cart");
        videoGamesPage.addProductsBelowMaxPriceToCart();

        getTest().log(Status.INFO, "Moving to next page (if needed)");
        videoGamesPage.moveToNextPageIfNoProducts();

        getTest().log(Status.PASS, "Test completed successfully ✅");


    }
}
