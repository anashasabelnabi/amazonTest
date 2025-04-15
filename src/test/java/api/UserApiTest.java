package api;

import base.BaseApi;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import config.ApiConfig;
import helper.ApiTestData;

import static helper.JsonAssertUtil.assertJsonEquals;
import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.fail;


public class UserApiTest extends BaseApi {


    @Test
    public void UC01_createUser_Happy() {

        getTest().log(Status.INFO, "Create A New User");
        String testCase = "UC01_createUser_Happy";
        try {
            Response response = performSafeRequest(
                    given()
                            .header("Content-Type", "application/json")
                            .body(ApiTestData.loadJsonForTestCase(testCase, "createUserRequest.json")),
                    "post",
                    ApiConfig.USERS_ENDPOINT,
                    testCase
            );

            response.then().statusCode(201)
                    .body("name", equalTo("morpheus"))
                    .body("job", equalTo("leader"))
                    .body("id", notNullValue())
                    .body("createdAt", notNullValue());

            getTest().log(Status.PASS, "✅ User created successfully");

        } catch (Exception e) {
            getTest().log(Status.FAIL, "❌ Unexpected error in " + testCase + ": " + e.getMessage());
            fail();
        }
    }

    @Test
    public void UC02_updateUser_Happy() {
        getTest().log(Status.INFO, "Update User");
        String testCase = "UC02_updateUser_Happy";
        try {
            String expectedResponse = ApiTestData.loadJsonForTestCase(testCase, "createUserResponse.json");

            Response response = performSafeRequest(
                    given()
                            .header("Content-Type", "application/json")
                            .body(ApiTestData.loadJsonForTestCase(testCase, "createUserRequest.json")),
                    "patch",
                    ApiConfig.ENDPOINT,
                    testCase
            );
            assertJsonEquals(response.getBody().asString(), expectedResponse, testCase);
            getTest().log(Status.PASS, "✅ User updated and validated");

        } catch (Exception e) {
            getTest().log(Status.FAIL, "❌ Error in " + testCase + ": " + e.getMessage());
            fail();
        }
    }

    @Test
    public void UC03_getUser_Happy() {
        getTest().log(Status.INFO, "Get User");
        String testCase = "UC03_getUser_Happy";
        try {
            String expectedResponse = ApiTestData.loadJsonForTestCase(testCase, "createUserResponse.json");

            Response response = performSafeRequest(
                    given().header("Content-Type", "application/json"),
                    "get",
                    ApiConfig.ENDPOINT,
                    testCase
            );

            assertJsonEquals(response.getBody().asString(), expectedResponse, testCase);
            getTest().log(Status.PASS, "✅ User fetched and response matched");

        } catch (Exception e) {
            getTest().log(Status.FAIL, "❌ Error in " + testCase + ": " + e.getMessage());
            fail();
        }
    }
}
