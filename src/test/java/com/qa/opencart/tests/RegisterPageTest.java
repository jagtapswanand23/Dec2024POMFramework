package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.constants.AppConstants;

public class RegisterPageTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(RegisterPageTest.class);

    @BeforeClass
    public void registerSetUp() {
        registerPage = loginPage.navigateToRegisterPage();
    }


    @DataProvider
    public Object[][] getUserRegTestData() {
        return new Object[][]{
                {"vishal", "mehta", "8888888888", "Test@123", "yes"},
                {"Swanand", "jagtap", "8888888888", "Test@123", "no"}
        };
    }

    @DataProvider
    public Object[][] getUserRegData(){
       Object regData[][]= ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
       return regData;
    }

    @Test(dataProvider = "getUserRegData")
    public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
        Assert.assertTrue(
                registerPage.
                        userRegisteration(firstName, lastName, telephone, password, subscribe));
    }
}
