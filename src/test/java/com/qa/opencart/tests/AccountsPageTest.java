package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.qa.opencart.constants.AppConstants.*;

@Feature("Feature 101: Open cart - Account page feature")
@Epic("Epic 002: design pages for open cart application")
@Story("US 102: implement Account page for open card application")
public class AccountsPageTest extends BaseTest {

    // BT --> BC will implement by TestNG
    // one hook condition or before  condition

    @BeforeClass
    public void accPageSetup() {
        //this gives account page ref , so that we can call ref in another functions
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Description("checking open cart account page title..")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test
    public void accPageTitleTest() {
        Assert.assertEquals(accPage.getAccPageTitle(), HOME_PAGE_TITLE);
    }

    @Description("checking open cart account page url..")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test
    public void accPageURLTest() {

        Assert.assertTrue(accPage.getAccPageURL().contains(HOME_PAGE_FRACTION_URL));
    }

    @Description("checking open cart account page header..")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test
    public void accPageHeadersTest(){
        List<String>accHeaderList=accPage.getAccPageHeaders();
        Assert.assertEquals(accHeaderList,expectedAccPageHeaderList);
    }
}
