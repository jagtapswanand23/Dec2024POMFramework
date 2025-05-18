package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

import io.qameta.allure.Step;

public class DriverFactory {

    WebDriver driver;
    Properties prop;  //its coming from JAVA to read the properties from file
    OptionsManager optionsManager;

    /**
     * Its coming from JAVA
     * Create local copy of dirver
     */
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    public static String highlight;

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    //warn, info,error,fatal

    /**
     * getDriver: get the local thread copy of the driver
     */

    public static WebDriver getDriver() {

        return tlDriver.get();
    }

    @Step("init the driver with properties:{0}")
    public WebDriver initDriver(Properties prop) {

        log.info("properties: "+prop);
        // passing prop it will help us to get all values from properties file
        String browserName = prop.getProperty("browser");
//        System.out.println("browser name : " + browserName);
        log.info("browser name : " + browserName);
        optionsManager = new OptionsManager(prop);

        /**
         * This method is used to init the browser on the basis of given browser name
         * @param browserName
         */
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
//                driver = new ChromeDriver(optionsManager.getChromeOptions());
                break;
            case "firefox":
                tlDriver.set(new FirefoxDriver(optionsManager.getFirfoxOptions()));
//                driver = new FirefoxDriver(optionsManager.getFirfoxOptions());
                break;
            case "edge":
                tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
//                driver = new EdgeDriver(optionsManager.getEdgeOptions());
                break;
            default:
//                System.out.println("Please pass the valid browser name... " + browserName);
                log.error("Please pass the valid browser name... " + browserName);
                throw new BrowserException("==Invalid Browser==");
        }
        getDriver().get(prop.getProperty("url"));  //login page url
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        return getDriver();
    }

    /**
     * this is used to init the config properties
     *
     * @return
     */
    public Properties initProp() {

        String envName = System.getProperty("env"); //used to fetch the value of env variable , its given by mvn
        FileInputStream ip = null;
        prop = new Properties();

        try {
            if (envName == null) {
//                System.out.println("Your env is null. hence running the test on QA env..");
                log.warn("Your env is null. hence running the test on QA env..");
                ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
            } else {

//                System.out.println("Running tests on env: " + envName);
                log.info("Running tests on env: " + envName);
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
                        break;
                    default:
                        log.error("--invalid env name--- "+envName);
                        throw new FileNotFoundException("===INVALID ENV NAME=== " + envName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;  // return the prop
    }

        /**
         * takescreenshot
         */

        public static File getScreenshotFile() {
            File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
            return srcFile;
        }

//        public static byte[] getScreenshotByte () {
//            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir
//
//        }
//
//        public static String getScreenshotBase64 () {
//            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir
//
//        }

}
