package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.SearchResultsPage;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @BeforeClass
    public void searchSetup() {
        accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Description("checking search filter test")
    @Severity(SeverityLevel.MINOR)
    @Owner("Swanand J")
    @Test
    public void searchTest() {
        searchResultsPage = accPage.doSearch("Macbook"); // calling from BaseTest class
        int accResutlsCount = searchResultsPage.getResultsProductCount();
        Assert.assertEquals(accResutlsCount, 3);
    }
}
