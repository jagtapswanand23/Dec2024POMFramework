package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// import all static variables of this class
import static com.qa.opencart.constants.AppConstants.*;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil eleUtil;

    // private By locators
    private final By email = By.id("input-email");
    private final By password = By.id("input-password");
    private final By loginButton = By.xpath("//input[@value='Login']");
    private final By forgotPwdLink = By.linkText("Forgotten Password");
    private final By registerLink = By.linkText("Register");

    // public page constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);  // we have to create object of eleutil if not written then it wil give null pointer exception
    }

    //public page actions/methods
    @Step("getting login page title")
    public String getLoginPageTitle() {
        String title = eleUtil.waitForTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
        System.out.println("Login page title " + title);
        return title;
    }

    @Step("getting login page url")
    public String getLoginPageURL() {
        String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
        System.out.println("Login page url " + url);
        return url;
    }

    @Step("checking forgot password link exist")
    public boolean isForgotPwdLinkExist() {
        return eleUtil.isElementDisplayed(forgotPwdLink);
//        return driver.findElement(forgotPwdLink).isDisplayed();  // we use encapsulation private final variable accessing
    }

    @Step("login with valid username:{0} and password:{1}") //0 is username and 1 is password from parameters
    public AccountsPage doLogin(String username, String pwd) {
        System.out.println("User credentials: " + username + ":" + pwd);
        eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(loginButton);

//        String title = eleUtil.waitForTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
//        System.out.println("accounts page title: " + title);
//        return title;

        // landing on Accounts Page objects
        //after clicking on login button --> landing on Account page
        // responsible to return the AccountsPage class object, so its returning the next landing page
        return new AccountsPage(driver);
    }

    @Step("navigating to registration page")
    public RegisterPage navigateToRegisterPage(){
        eleUtil.clickWhenReady(registerLink,DEFAULT_TIMEOUT);
        return new RegisterPage(driver);
    }

}
