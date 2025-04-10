package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private static final By EMAIL_FIELD = By.id("ap_email_login");
    private static final By CONTINUE_BUTTON = By.cssSelector("input.a-button-input[type='submit'][aria-labelledby='continue-announce']");
    private static final By PASSWORD_FIELD = By.id("ap_password");
    private static final By SIGN_IN_BUTTON = By.id("signInSubmit");
    private static final By ERROR_MESSAGE = By.cssSelector(".a-alert-heading");


    // Page Actions
    public void enterEmail(String email) {
        isDisplayed(EMAIL_FIELD);
        type(EMAIL_FIELD, email);
    }

    public void clickContinue() {
        click(CONTINUE_BUTTON);
    }

    public void enterPassword(String password) {
        isDisplayed(PASSWORD_FIELD);
        type(PASSWORD_FIELD, password);
    }

    public void clickSignIn() {
        click(SIGN_IN_BUTTON);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(ERROR_MESSAGE);
    }

    public String getErrorMessageText() {
        return getText(ERROR_MESSAGE);
    }

    public void login(String email, String password) {
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSignIn();
    }
}

