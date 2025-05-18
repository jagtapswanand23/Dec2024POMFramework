package com.qa.opencart.pages;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.qa.opencart.constants.AppConstants.*;

public class AccountsPage {
    private final By headers = By.xpath("//div[@id='content']/h2");
    private final By search = By.name("search");
    private final By searchIcon = By.cssSelector("div#search button");
    private WebDriver driver;
    private ElementUtil eleUtil;

    private static final Logger log = LogManager.getLogger(AccountsPage.class);


    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    @Step("getting account page title")
    public String getAccPageTitle() {
        String title = eleUtil.waitForTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
        log.info("Home page title " + title);
        return title;
    }

    @Step("getting account page url")
    public String getAccPageURL() {
        String url = eleUtil.waitForURLContains(HOME_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
        log.info("HOME page url " + url);
        return url;
    }

    @Step("getting account page headers")
    public List<String> getAccPageHeaders() {
        List<WebElement> headerList = eleUtil.getElements(headers);
        List<String> headerValList = new ArrayList<String>();
        for (WebElement e : headerList) {
            String text = e.getText();
            headerValList.add(text);
        }
        return headerValList;
    }

    @Step("perform search :{0}")
    public SearchResultsPage doSearch(String searchKey) {
//        System.out.println("Search key is: "+searchKey);
        log.info("Search key is: "+searchKey);
        eleUtil.doSendKeys(search, searchKey);
        eleUtil.doClick(searchIcon);
        return new SearchResultsPage(driver);

    }


}
