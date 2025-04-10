package testdata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ApiTestData {

    private static final String BASE_JSON_PATH = "src/test/resources/";

    /**
     * Generic method to load JSON response for any test case
     * @param testCaseFolder e.g. "UC01_createUser", "UC02_updateUser"
     * @param fileName e.g. "response.json", "request.json"
     */
    public static String loadJsonForTestCase(String testCaseFolder, String fileName) {
        try {
            File jsonFile = new File(BASE_JSON_PATH + testCaseFolder + "/" + fileName);
            return Files.readString(jsonFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON for test case: "
                    + testCaseFolder + "/" + fileName, e);
        }
    }
}
