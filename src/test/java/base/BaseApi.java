package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import config.ApiConfig;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;
import utils.ExtentManager;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class BaseApi {

    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    protected static ExtentReports extent;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ApiConfig.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setupTest(Method method) {
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            getTest().log(Status.SKIP, "Test Skipped");
        }
    }

    @AfterClass
    public void flushExtent() {
        if (extent != null) {
            extent.flush();
        }
        openReport();
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

    protected Response performSafeRequest(RequestSpecification requestSpec, String method, String endpoint, String testCaseName) {
        try {
            getTest().log(Status.INFO, "🔄 Performing " + method.toUpperCase() + " request for test case: " + testCaseName);
            return switch (method.toLowerCase()) {
                case "get" -> requestSpec.get(endpoint);
                case "post" -> requestSpec.post(endpoint);
                case "patch" -> requestSpec.patch(endpoint);
                case "put" -> requestSpec.put(endpoint);
                case "delete" -> requestSpec.delete(endpoint);
                default -> throw new IllegalArgumentException("❌ Unsupported HTTP method: " + method);
            };
        } catch (Exception e) {
            getTest().log(Status.FAIL, "❌ Request failed in test case " + testCaseName + ": " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception in request: " + e.getMessage());
            return null;
        }
    }
}
