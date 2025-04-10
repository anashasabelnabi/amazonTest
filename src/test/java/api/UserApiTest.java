package api;

import base.BaseApi;
import org.testng.annotations.Test;
import testdata.ApiTestData;
import config.ApiConfig;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;



public class UserApiTest extends BaseApi {


    @Test
    public void UC01_createUser_Happy() {
         given()
                .header("Content-Type", "application/json")
                .body(ApiTestData.loadJsonForTestCase("UC01_createUser_Happy", "createUserRequest.json"))
                .when()
                .post(ApiConfig.USERS_ENDPOINT)
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void UC02_updateUser_Happy() {
        String expectedResponse = ApiTestData.loadJsonForTestCase("UC02_updateUser_Happy", "createUserResponse.json");
        String actualResponse =
                given()
                .header("Content-Type", "application/json")
                .body(ApiTestData.loadJsonForTestCase("UC02_updateUser_Happy", "createUserRequest.json"))
                .when()
                .patch(ApiConfig.ENDPOINT)
                .then()
                .statusCode(200)
                .extract().asString();

        assertThatJson(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void UC03_getUser_Happy() {
        String expectedResponse = ApiTestData.loadJsonForTestCase("UC03_getUser_Happy", "createUserResponse.json");
        String actualResponse =
                given()
                        .header("Content-Type", "application/json")
                        .when()
                        .get(ApiConfig.ENDPOINT)
                        .then()
                        .statusCode(200)
                        .extract().asString();

        assertThatJson(actualResponse).isEqualTo(expectedResponse);
    }
}
