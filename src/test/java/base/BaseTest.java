package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import config.ConfigReader;
import utils.DriverFactory;
import utils.ExtentManager;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    protected ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    protected static ExtentReports extent;

    @BeforeClass
    public void setup() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setupTest(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);
    }

    @BeforeMethod
    public void setUp() {
        DriverFactory.createDriver();
        WebDriver driver = DriverFactory.getDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getImplicitlyWait()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitlyWait()));

        try {
            driver.get(ConfigReader.getBaseUrl());

            String pageSource = driver.getPageSource().toLowerCase();
            if (pageSource.contains("this site can’t be reached") ||
                    pageSource.contains("err_connection") ||
                    pageSource.contains("problem loading page") ||
                    pageSource.trim().isEmpty()) {

                System.out.println("🔁 Detected broken page. Refreshing...");
                getTest().log(Status.INFO, "Detected broken page. Refreshing...");
                driver.navigate().refresh();
            }

        } catch (TimeoutException te) {
            System.out.println("❌ Page took too long to load. Quitting browser.");
            getTest().log(Status.FAIL, "Page took too long to load. Quitting browser.");
            driver.quit();

            throw new RuntimeException("Page load timeout. Connection issue.");
        } catch (Exception e) {
            System.out.println("❌ Other issue during setup: " + e.getMessage());
            getTest().log(Status.FAIL, "Other issue during setup: "+ e.getMessage());
            driver.quit();
            throw new RuntimeException("Critical error loading page.");
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            getTest().log(Status.SKIP, "Test Skipped");
        }
        DriverFactory.quitDriver();
    }

    @AfterClass
    public void flushExtent() {
        if (extent != null) {
            extent.flush();
        }
        openReport();
    }

    public ExtentTest getTest() {
        return extentTest.get();
    }


    public WebDriver getBrowser() {
        return DriverFactory.getDriver();
    }

    private void openReport() {
        try {
            File htmlFile = new File("test-output/extent-report.html");
            if (htmlFile.exists()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            }
        } catch (Exception e) {
            System.out.println("Unable to open report automatically: " + e.getMessage());
        }
    }

    public boolean isBrandRegistryPage() {
        WebDriver driver = getBrowser();
        return driver.getCurrentUrl().contains(ConfigReader.getBrandRegisterUrl());

    }

    public void handleBrandRegistryPage() {
        WebDriver driver = getBrowser();
        if (isBrandRegistryPage()) {
            getTest().log(Status.INFO, "Brand Registry page detected. Navigating back.");
            driver.navigate().back();
        }
    }

}