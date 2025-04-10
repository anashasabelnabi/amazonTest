package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.ConfigReader;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getImplicitlyWait()));
    }

    // ========== Basic Actions ==========
    protected void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }catch (TimeoutException e) {
            throw new ElementNotInteractableException("Element not clickable: " + locator, e);
        } catch (NoSuchElementException e) {
            throw new ElementNotVisibleException("Element not found: " + locator, e);
        } catch (StaleElementReferenceException e) {
            // Retry once
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        }
    }

    protected void type(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
        }
        catch (TimeoutException e) {
            throw new ElementNotVisibleException("Element not visible for typing: " + locator, e);
        }
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isPresence(By locator){
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void scrollTo(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("scrollBy(0,3500)", element);
    }

    protected void clickByJs(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
