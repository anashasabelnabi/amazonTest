package helper;

import base.BaseApi;
import com.aventstack.extentreports.Status;

import static base.BaseApi.getTest;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.fail;


public class JsonAssertUtil {

    public static void assertJsonEquals(String actual, String expected, String testCaseName) {
        try {
            getTest().log(Status.INFO, "🔍 Comparing JSON for test case: " + testCaseName);
            assertThatJson(actual).isEqualTo(expected);
            getTest().log(Status.PASS, "✅ JSON response matches expected for " + testCaseName);
        } catch (AssertionError e) {
            getTest().log(Status.FAIL,
                    "❌ JSON mismatch in test case: " + testCaseName + "<br><b>Error:</b> "
                            + e.getMessage().replace("\n", "<br>"));
            fail("❌ JSON comparison failed for " + testCaseName + ": " + e.getMessage());
        }
    }
}
