package com.qa.opencart.factory;

import com.qa.opencart.exceptions.BrowserException;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    /**
     * Its coming from JAVA
     * Create local copy of dirver
     */
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    public static String highlight;
    WebDriver driver;
    Properties prop;  //its coming from JAVA to read the properties from file
    OptionsManager optionsManager;
    //warn, info,error,fatal

    /**
     * getDriver: get the local thread copy of the driver
     */

    public static WebDriver getDriver() {

        return tlDriver.get();
    }

    /**
     * takescreenshot
     */

    public static File getScreenshotFile() {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
        return srcFile;
    }

    @Step("init the driver with properties:{0}")
    public WebDriver initDriver(Properties prop) {

        log.info("properties: " + prop);
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

                if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                    initRemoteDriver("chrome");
                } else {
                    //run it on local machine
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
//                driver = new ChromeDriver(optionsManager.getChromeOptions());
                }
                break;
            case "firefox":
                if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                    initRemoteDriver("firefox");
                } else {
                    //run it on local machine
                    tlDriver.set(new FirefoxDriver(optionsManager.getFirfoxOptions()));
//                driver = new ChromeDriver(optionsManager.getFirfoxOptions());
                }
                break;
            case "edge":
                if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                    initRemoteDriver("edge");
                } else {
                    //run it on local machine
                    tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
//                driver = new ChromeDriver(optionsManager.getEdgeOptions());
                }
                break;
            case "safari":
                tlDriver.set(new SafariDriver());
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

    // run it on remote grid
    private void initRemoteDriver(String browserName) {

        switch (browserName) {

            case "chrome":
                try {
                    // run on remote or selenium grid server / aws / machine
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "firefox":
                try {
                    // run on remote or selenium grid server / aws / machine
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirfoxOptions()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "edge":
                try {
                    // run on remote or selenium grid server / aws / machine
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("this browser is not supported on selenium grid server : " + browserName);
                throw new BrowserException("INVALID BROWSER==");
        }
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
                        log.error("--invalid env name--- " + envName);
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
