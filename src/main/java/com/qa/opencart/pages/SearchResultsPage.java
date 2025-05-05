package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.qa.opencart.constants.AppConstants.*;

public class SearchResultsPage {
    private WebDriver driver;
    private ElementUtil eleUtil;

    private final By resultsProduct = By.cssSelector("div.product-thumb");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    @Step("getting the product count on result page")
    public int getResultsProductCount() {
        int searchCount = eleUtil.waitForAllElementsVisible(resultsProduct, MEDIUM_DEFAULT_TIMEOUT).size();
        System.out.println("total no of search products is= " + searchCount);
        return searchCount;
    }

    public ProductInfoPage selectProduct(String productName){
        System.out.println("product NAME: "+productName);
        eleUtil.doClick(By.linkText(productName));
        return new ProductInfoPage(driver);

    }
}

