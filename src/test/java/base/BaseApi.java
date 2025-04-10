package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import config.ApiConfig;

public class BaseApi {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ApiConfig.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
