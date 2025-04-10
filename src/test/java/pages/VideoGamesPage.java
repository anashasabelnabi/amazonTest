package pages;

import base.BasePage;
import net.bytebuddy.pool.TypePool;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class VideoGamesPage extends BasePage {

    public VideoGamesPage(WebDriver driver) {
        super(driver);
    }

    private static final By FREE_SHIPPING_FILTER = By.xpath("//a[@class='a-link-normal' and .//span[text()='Free Shipping']]");
    private static final By NEW_CONDITION_FILTER = By.xpath("//a[@class='a-link-normal s-navigation-item' and .//span[text()='New']]");
    private static final By SORT_DROPDOWN = By.xpath("//select[@class='a-native-dropdown a-declarative' and @role='combobox']");
    private static final By HIGH_TO_LOW_OPTION = By.xpath("//option[@value='price-desc-rank']");

    private static final By PRODUCT_PRICES = By.cssSelector(".a-price-whole");
    private static final By PRODUCT_CARDS = By.cssSelector(".s-result-item");
    private static final By ADD_TO_CART_BUTTONS = By.name("submit.addToCart");
    private static final By CART_COUNT = By.id("nav-cart-count");

    private static final By OUTSIDE = By.tagName("body");

    private static final By NEXT_PAGE_BUTTON = By.cssSelector(".s-pagination-item.s-pagination-next");

    private static final double MAX_PRICE_EGP = 15000.0;


    public void selectFreeShipping() {
        if (isDisplayed(FREE_SHIPPING_FILTER)) {
            click(FREE_SHIPPING_FILTER);
        } else {
            System.out.println("free shipping not presented");
        }
    }

    public void selectNewConditions() {
        if (isPresence(NEW_CONDITION_FILTER)) {
            scrollTo(NEW_CONDITION_FILTER);
            clickByJs(NEW_CONDITION_FILTER);
        } else {
            System.out.println("new condition not presented");
        }
    }

    public void sortHighToLow() {

        if (isDisplayed(SORT_DROPDOWN)) {
            clickByJs(SORT_DROPDOWN);
            if (isDisplayed(HIGH_TO_LOW_OPTION)) {
                click(HIGH_TO_LOW_OPTION);
                wait.until(ExpectedConditions.urlContains("s=price-desc-rank"));
                try {
                    click(OUTSIDE); // This may close the dropdown if clicking outside closes it
                } catch (Exception e) {
                    System.out.println("Failed to close the dropdown after clicking the option.");
                }
            } else {
                System.out.println("High to low not presented");
            }
        } else {
            System.out.println("sort dropdown not presented");
        }
    }

    public void addProductsBelowMaxPriceToCart() {
        // Initial page load
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_CARDS));

        boolean hasNextPage = true;

        while (hasNextPage) {
            List<WebElement> productCards = driver.findElements(PRODUCT_CARDS);

            for (WebElement card : productCards) {
                try {
                    String priceText = (String) ((JavascriptExecutor) driver).executeScript(
                            "return arguments[0].querySelector('.a-price-whole')?.textContent || ''", card).toString().replace(",", "").replace("EGP", "").trim();
                    if (priceText.isEmpty()) continue;
                    double price = Double.parseDouble(priceText);

                    if (price < MAX_PRICE_EGP) {
                        WebElement addToCartBtn = (WebElement) ((JavascriptExecutor) driver).executeScript(
                                "return arguments[0].querySelector('[name*=\"submit.addToCart\"]')", card);
                        if (addToCartBtn != null && addToCartBtn.isEnabled()) {
                            addToCartBtn.click();
                            System.out.println("Added product with price " + price + " EGP to cart.");
                        } else {
                            System.out.println("Add to Cart button not clickable for product with price " + price + " EGP.");
                        }
                    }
                } catch (NoSuchElementException | NumberFormatException e) {
                    continue;
                }
            }
            hasNextPage = moveToNextPageIfNoProducts();
        }
    }

    public boolean moveToNextPageIfNoProducts() {
        List<WebElement> productCards = driver.findElements(PRODUCT_CARDS);
        boolean productFound = false;

        for (WebElement card : productCards) {
            try {
                String priceText = (String) ((JavascriptExecutor) driver).executeScript(
                        "return arguments[0].querySelector('.a-price-whole')?.textContent || ''", card).toString().replace(",", "").replace("EGP", "").trim();
                if (priceText.isEmpty()) continue;

                double price = Double.parseDouble(priceText);

                if (price < MAX_PRICE_EGP) {
                    productFound = true;
                    break;
                }
            } catch (NoSuchElementException | NumberFormatException e) {
                continue;
            }
        }

        if (!productFound) {
            try {
                WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(NEXT_PAGE_BUTTON));
                scrollTo(NEXT_PAGE_BUTTON);
                click(NEXT_PAGE_BUTTON);
                System.out.println("➡ No products below " + MAX_PRICE_EGP + " EGP, moving to next page.");
                wait.until(ExpectedConditions.urlContains("page="));
                return true;
            } catch (NoSuchElementException e) {
                System.out.println("Couldn't find next page button.");
                return false;
            } catch (TimeoutException e) {
                System.out.println("Timeout while waiting for the next page.");
                return false;
            }
        }

        return false;
    }

}
