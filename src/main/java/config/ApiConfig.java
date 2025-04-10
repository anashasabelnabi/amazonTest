package config;

public class ApiConfig {

    public static final String BASE_URL = ConfigReader.getProperty("api.base.url");
    public static final String USERS_ENDPOINT = ConfigReader.getProperty("api.users.endpoint");

    public static final String ENDPOINT = ConfigReader.getProperty("endpoint");

}
