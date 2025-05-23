package com.qa.opencart.base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utils.LogUtil;
import jdk.jfr.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.Properties;

//@Listeners(ChainTestListener.class)
public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected Properties prop;
    //it wil work as within class and out side of package also
    protected LoginPage loginPage;
    protected AccountsPage accPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;
    WebDriver driver;
    DriverFactory df;

    @Description(" init the driver and properties")// coming from allure report
    @Parameters({"browser"})  // it means TestNG thread try to run your test it will come here
    @BeforeTest
    public void setup(String browserName) {
        df = new DriverFactory();
        prop = df.initProp();

        // browserNAme is passed from .xml file
        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }
        // this method return the driver so it called from driver factory
        driver = df.initDriver(prop); // open login page
        //it will get same driver from above variable
        loginPage = new LoginPage(driver);
    }

//    @BeforeMethod
//    public void beforeMethod(ITestNGMethod result) {
//        LogUtil.info("-------------starting test case==== " + result.getMethodName());
//    }

    @AfterMethod //will be running after each @test method
    public void attachScreenshot(ITestResult result) {
        if (!result.isSuccess()) {//only for failure test cases -- true
            log.info("---screenshot is taken---");
            ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
        }

        //ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");

        LogUtil.info("-------------ending test case==== " + result.getMethod().getMethodName());
    }

    @Description("closing the browser") //coming from allure
    @AfterTest
    public void tearDown() {
        driver.quit();
        log.info("---Closing the browser---");
    }
}
