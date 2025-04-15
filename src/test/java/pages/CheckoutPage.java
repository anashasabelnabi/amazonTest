package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    private static final By ADD_ADDRESS_BUTTON = By.cssSelector("a.a-button-text");

    private static final By CHANGE_ADDRESS_LINK = By.xpath("//a[normalize-space(text())='Change']");

    private static final By ADD_NEW_ADDRESS_LINK = By.xpath("//a[normalize-space(text())='Add a new delivery address']");

    private static final By CASH_PAYMENT_METHOD = By.xpath("//input[contains(@value, 'paymentMethod=COD')]");

    private static final By USE_THIS_PAYMENT_METHOD = By.xpath("//input[@data-testid='bottom-continue-button']");

    private static final By SHIPPING_PRICE_TEXT =By.xpath("//li[.//span[contains(text(), 'Shipping & handling:')]]//span[contains(@class, 'aok-nowrap')]");
    private static final By COD_PRICE_TEXT = By.xpath("//li[.//span[contains(text(), 'Cash on Delivery Fee')]]//span[contains(@class, 'aok-nowrap')]");
    private static final By FREE_DILVERY_PRICE_TEXT = By.xpath("//div[contains(@class, 'a-row') and contains(@class, 'a-color-success')]//div[contains(@class, 'a-column') and contains(@class, 'a-span4')]");
    private static final By ORDER_TOTAL_PRICE_TEXT = By.xpath("//li[.//span[contains(text(), 'Order total')]]//div[contains(@class, 'order-summary-line-definition')]");


    private void navigateToAddAddressForm() {
        click(ADD_ADDRESS_BUTTON);
    }

    private void changeAddress() {
        click(CHANGE_ADDRESS_LINK);
        if (isDisplayed(ADD_NEW_ADDRESS_LINK)) {
            click(ADD_NEW_ADDRESS_LINK);
        } else {
            System.out.println("New Address Link Not Displayed");
        }
    }

    public void userHaveAddress() {
        if (isDisplayed(ADD_ADDRESS_BUTTON)) {
            navigateToAddAddressForm();
        } else {
            changeAddress();
        }
    }

    public void handlePaymentMethod() {
        if (isDisplayed(CASH_PAYMENT_METHOD)) {
            WebElement element = driver.findElement(CASH_PAYMENT_METHOD);
            if (element.isEnabled()) {
                click(CASH_PAYMENT_METHOD);
            } else {
                throw new SkipException("Cash option is disabled, skipping test.");
            }
        } else {
            System.out.println("Payment Method Not Displayed");
        }
    }

    public void useThisPaymentMethod(){
        click(USE_THIS_PAYMENT_METHOD);
    }

    public void verifyTotalAmount(double subtotal) {
        // Extract shipping, fees, discounts, and displayed total from the page
        String shippingText = extractValueFromElement(SHIPPING_PRICE_TEXT);
        String codFeeText = extractValueFromElement(COD_PRICE_TEXT);
        String discountText = extractValueFromElement(FREE_DILVERY_PRICE_TEXT);
        String displayedTotalText = extractValueFromElement(ORDER_TOTAL_PRICE_TEXT);

        // Parse values to doubles
        double shipping = parsePrice(shippingText);
        double codFee = parsePrice(codFeeText);
        double discount = parsePrice(discountText);
        double displayedTotal = parsePrice(displayedTotalText);

        // Calculate the expected total
        double calculatedTotal = subtotal + shipping + codFee - discount;

        // Assert the calculated total matches the displayed total
        Assert.assertEquals(calculatedTotal, displayedTotal, 0.01, "The calculated total does not match the displayed total.");
    }

    private String extractValueFromElement(By locator) {
        String text = driver.findElement(locator).getText().replace("EGP", "").trim();
        return text.isEmpty() ? "0.00" : text;
    }

    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replaceAll("[^\\d.]", "").trim());
    }
}
