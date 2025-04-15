package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in configuration file");
        }
        return value;
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getBaseUrl() {
        return getProperty("baseURL");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public static int getImplicitlyWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    public static String getEmail() {
        return getProperty("email");
    }

    public static String getPassword() {
        return getProperty("password");
    }

    public static String getName() {
        return getProperty("welcome_message");
    }

    public static String getFullName() {
        return getProperty("full_name");
    }

    public static String getPhoneNumber() {
        return getProperty("phone_number");
    }

    public static String getStreetName() {
        return getProperty("street_name");
    }

    public static String getBuildingNo() {
        return getProperty("building_no");
    }

    public static String getCity() {
        return getProperty("city");
    }

    public static String getDistrict() {
        return getProperty("district");
    }

    public static String getGovernorate() {
        return getProperty("governorate");
    }

    public static String getMaxPrice() {
        return getProperty("max_price");
    }

    public static String getBrandRegisterUrl() {
        return getProperty("brandRegistryUrl");
    }

    public static String getExtentReport() {
        return getProperty("extent_report_path");
    }

    public static String getEnvironment() {
        return getProperty("env");
    }
    public static String getTesterName() {
        return getProperty("tester");
    }


}
