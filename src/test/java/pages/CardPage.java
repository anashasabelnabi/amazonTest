package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CardPage extends BasePage {
    public CardPage(WebDriver driver) {
        super(driver);
    }
    private static final By PROCEED_TO_CHECKOUT = By.name("proceedToRetailCheckout");

    public void proceedToCheckout(){
        if (isDisplayed(PROCEED_TO_CHECKOUT)){
            click(PROCEED_TO_CHECKOUT);
        }
    }
}
