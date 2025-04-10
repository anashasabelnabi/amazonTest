package ui;

import base.BaseTest;
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
        homePage = new HomePage(driver);
        homePage.navigateToSignIn();

        loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getEmail(), ConfigReader.getPassword());
        Assert.assertEquals(homePage.getUserName(), ConfigReader.getName());
        homePage.openFullMenu();
        homePage.selectVideoGames();
        homePage.selectAllVideoGames();
        Assert.assertEquals(homePage.getTitle(),"Video Games");

        videoGamesPage = new VideoGamesPage(driver);
        videoGamesPage.selectFreeShipping();
        videoGamesPage.selectNewConditions();

        Assert.assertTrue(driver.getCurrentUrl().contains("p_n_free_shipping_eligible"), "URL does not contain 'free_shipping'");
        Assert.assertTrue(driver.getCurrentUrl().contains("p_n_condition-type"), "URL does not contain 'new_condition'");

        videoGamesPage.sortHighToLow();
        Assert.assertTrue(driver.getCurrentUrl().contains("s=price-desc-rank"), "URL does not contain 'High To Low Sort'");

        videoGamesPage.addProductsBelowMaxPriceToCart();
        videoGamesPage.moveToNextPageIfNoProducts();
    }
}
