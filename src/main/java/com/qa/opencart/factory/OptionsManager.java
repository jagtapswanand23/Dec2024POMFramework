package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Properties;

public class OptionsManager {

    private Properties prop;

    public OptionsManager(Properties prop){
        this.prop=prop;
    }

    public ChromeOptions getChromeOptions(){
        ChromeOptions co=new ChromeOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            co.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            co.addArguments("--incognito");
        }
        return co;
    }

    public FirefoxOptions getFirfoxOptions(){
        FirefoxOptions fo=new FirefoxOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            fo.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            fo.addArguments("--incognito");
        }
        return fo;
    }

    public EdgeOptions getEdgeOptions(){
        EdgeOptions eo=new EdgeOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))){
            eo.addArguments("--headless");
        }
        if(Boolean.parseBoolean(prop.getProperty("incognito"))){
            eo.addArguments("--inPrivate");
        }
        return eo;
    }
}
