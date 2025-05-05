package com.qa.opencart.base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.tests.ProductInfoPageTest;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.Properties;

//@Listeners(ChainTestListener.class)
public class BaseTest {

    WebDriver driver;

    DriverFactory df;
    protected Properties prop;

    //it wil work as within class and out side of package also
    protected LoginPage loginPage;
    protected AccountsPage accPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;

    @Description(" init the driver and properties")// coming from allure report
    @Parameters({"browser"})  // it means TestNG thread try to run your test it will come here
    @BeforeTest
    public void setup(String browserName) {
        df = new DriverFactory();
        prop = df.initProp();

        // browserNAme is passed from .xml file
        if(browserName!=null){
            prop.setProperty("browser",browserName);
        }
        // this method return the driver so it called from driver factory
        driver = df.initDriver(prop); // open login page
        //it will get same driver from above variable
        loginPage = new LoginPage(driver);
    }


    @AfterMethod //will be running after each @test method
    public void attachScreenshot(ITestResult result) {
        if(!result.isSuccess()) {//only for failure test cases -- true
//            log.info("---screenshot is taken---");
            ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
        }

        //ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");


    }

    @Description("closing the browser") //coming from allure
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
