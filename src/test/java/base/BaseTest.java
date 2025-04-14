package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
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
        initializeDriver();
        configureDriver();
        navigateToBaseUrl();
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
}