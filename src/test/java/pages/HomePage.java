package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final By SIGN_IN_LINK = By.cssSelector("a.nav-a[data-nav-ref='nav_ya_signin']");
    private static final By WELCOME_USER = By.id("nav-link-accountList-nav-line-1");
    private static final By All_MENU = By.id("nav-hamburger-menu");

    private static final By SEE_MORE_BUTTON = By.cssSelector("a.hmenu-item.hmenu-compressed-btn[aria-label='See All Categories']");
    private static final By VIDEO_GAMES_LINK = By.cssSelector("a.hmenu-item[role='button'][data-menu-id='16']");

    private static final By ALL_VIDEO_GAMES = By.cssSelector("a.hmenu-item[href*='node=18022560031']");

    private static final By TITLE = By.xpath("//b[contains(text(), 'Video Games')]");

    private static final By GOT_TO_BASKET_BUTTON = By.xpath("//a[contains(text(),'Go to basket')]");

    public void navigateToSignIn() {
        if (isDisplayed(SIGN_IN_LINK)) {
            click(SIGN_IN_LINK);
        }
    }

    public String getUserName() {
        return getText(WELCOME_USER);
    }

    public String getTitle() {
        return getText(TITLE);
    }

    public void openFullMenu() {
        click(All_MENU);
        if (isDisplayed(SEE_MORE_BUTTON)) {
            click(SEE_MORE_BUTTON);
        }
    }

    public void selectVideoGames() {
            scrollTo(VIDEO_GAMES_LINK);
            click(VIDEO_GAMES_LINK);
    }

    public void selectAllVideoGames() {
        if (isDisplayed(ALL_VIDEO_GAMES)) {
            clickByJs(ALL_VIDEO_GAMES);
        }
    }

    public void goToBasket(){
        click(GOT_TO_BASKET_BUTTON);
    }
}
