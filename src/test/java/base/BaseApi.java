package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import config.ApiConfig;
import org.testng.annotations.BeforeMethod;
import utils.ExtentManager;

import java.lang.reflect.Method;

public class BaseApi {

    protected ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
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

    public ExtentTest getTest() {
        return extentTest.get();
    }
}
