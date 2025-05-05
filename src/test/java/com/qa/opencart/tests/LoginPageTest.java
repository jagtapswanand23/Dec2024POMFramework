package com.qa.opencart.tests;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.qa.opencart.constants.AppConstants.*;

@Feature("Feature 100: Open cart - Login feature")
@Epic("Epic 001: design pages for open cart application")
@Story("US 101: implement login page for open card application")
public class LoginPageTest extends BaseTest {

    @Description("checking open cart login page url..")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test(description = "checking login title")
    public void loginPageTitleTest() {
        String actTitle = loginPage.getLoginPageTitle();
        ChainTestListener.log("checking login page title: " + actTitle);
        Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
    }

    @Description("checking open cart login page title..")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test(description = "checking login page url")
    public void loginPageURLTest() {
        String actURL = loginPage.getLoginPageURL();
        Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
    }

    @Description("checking open cart login page forgot password link..")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Swanand J")
    @Test(description = "forgotPwdLinkExistTest")
    public void forgotPwdLinkExistTest() {

        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Description("check user login with valid user credentials..")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Swanand J")
    @Test(priority = Short.MAX_VALUE, description = "login with valid credentails")
    public void doLoginTest() {
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
    }

    @Test(enabled = false, description = "Work in Progress -- forgot pwd check")
    public void forgotPwd() {

        System.out.println("forgot pwd");
    }
}
