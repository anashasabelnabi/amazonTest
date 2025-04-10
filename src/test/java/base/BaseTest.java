package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import config.ConfigReader;
import utils.DriverFactory;

import java.time.Duration;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        initializeDriver();
        configureDriver();
        navigateToBaseUrl();
    }

    private void initializeDriver() {
         DriverFactory.createDriver();
    }

    private void configureDriver() {
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitlyWait()));
    }

    private void navigateToBaseUrl() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getBaseUrl());
    }

    public WebDriver getBrowser (){
        return DriverFactory.getDriver();
    }


    @AfterMethod
    public void tearDown() {
            DriverFactory.quitDriver();
    }

}
