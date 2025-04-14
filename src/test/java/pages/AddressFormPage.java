package pages;

import base.BasePage;
import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Random;

public class AddressFormPage extends BasePage {
    public AddressFormPage(WebDriver driver) {
        super(driver);
    }

    private static final By FULL_NAME_FIELD = By.cssSelector("input#address-ui-widgets-enterAddressFullName");
    private static final By PHONE_NUMBER_FIELD = By.id("address-ui-widgets-enterAddressPhoneNumber");

    private static final By STREET_NAME_FIELD = By.id("address-ui-widgets-enterAddressLine1");

    private static final By BUILDING_NO_FIELD = By.id("address-ui-widgets-enter-building-name-or-number");

    private static final By CITY_FIELD = By.xpath("//div//input[@id='address-ui-widgets-enterAddressCity']");
    private static final By CITY_OPTION = By.xpath("//li[contains(text(),'15th of May City')]");

    private static final By DISTRICT_FIELD = By.xpath("//input[@id='address-ui-widgets-enterAddressDistrictOrCounty']");
    private static final By DISTRICT_OPTION = By.xpath("//li[text()='15 May City']");

    private static final By OPTION_DROPDOWN = By.xpath("//ul[@id='address-ui-widgets-autocompleteResultsContainer']");

    private static final By GOVERNORATE_FIELD = By.xpath("//input[@id='address-ui-widgets-enterAddressStateOrRegion']");

    private static final By USE_THIS_ADDRESS_BUTTON = By.xpath("//input[@type='submit' and @data-csa-c-slot-id='address-ui-widgets-continue-address-btn-bottom' and @data-testid='bottom-continue-button']");


    private void enterFullName() {
        if (isDisplayed(FULL_NAME_FIELD)) {
            type(FULL_NAME_FIELD, ConfigReader.getFullName());
        }
    }

    private void enterPhoneNumber() {
        type(PHONE_NUMBER_FIELD, ConfigReader.getPhoneNumber());
    }

    private void enterStreetName() {
        type(STREET_NAME_FIELD, ConfigReader.getStreetName());
    }

    private void enterBuildingNo() {
        type(BUILDING_NO_FIELD, ConfigReader.getBuildingNo());
    }

    private void enterCity() throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(CITY_FIELD));
        try{
            for (char c : ConfigReader.getCity().toCharArray()) {
                element.sendKeys(Character.toString(c));
                Thread.sleep(200); // simulate typing
            }
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        }catch (ElementNotVisibleException e){
            System.out.println(e + "City Field Not Displayed");
        }
    }

    private void enterDistrict() {
        try{
            type(DISTRICT_FIELD,ConfigReader.getDistrict());
        }catch (ElementNotVisibleException e){
            System.out.println(e + "District Field Not Displayed");
        }
    }

    private void enterGovernorate() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(GOVERNORATE_FIELD));
        try{
            type(GOVERNORATE_FIELD,ConfigReader.getGovernorate());
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        }catch (ElementNotVisibleException e){
            System.out.println(e + "District Field Not Displayed");
        }
    }


    private void useThisAddress() {
        scrollTo(USE_THIS_ADDRESS_BUTTON);
        click(USE_THIS_ADDRESS_BUTTON);
    }

    public void addFullAddress() throws InterruptedException {
        enterFullName();
        enterPhoneNumber();
        enterStreetName();
        enterBuildingNo();
        enterCity();
        enterDistrict();
        enterGovernorate();
        useThisAddress();
    }
}
