package com.qa.opencart.utils;

import com.aventstack.chaintest.plugins.ChainTestListener;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ElementUtil {
    private WebDriver driver;
    private Actions act;

    public ElementUtil(WebDriver driver) {

        this.driver = driver;
        act = new Actions(driver);
    }


//    public static void bylocators(String value){
//
//        By firstName=By.id("input-firstname");
//        doElementGetText(firstName,"swanand");
//        By lastName=By.id("input-lastname");
//        By eMailaddress= By.xpath("//input[@id='input-email']");
//        By password=By.xpath("//input[@name='password']");
//        By passConfirm=By.cssSelector("#input-confirm");
//        By yesRadioBtn=By.xpath("//label[@class='radio-inline']/input[@value='1']");
//        By privacyPolicy=By.xpath("//input[@name='agree']");
//        By continueBtn=By.className("btn btn-primary");
//        By accountCreated=By.tagName("h1");
//    }

    private void nullCheck(String value) {
        if (value == null) {
            throw new RuntimeException("==Value can not be null===");
        }
    }
    @Step("Entering value:{1} into element:{0}") // value 1 is value from parameter and 0 is locator from parameter
    public void doSendKeys(By locator, String value) {
        nullCheck(value);
        getElement(locator).clear();
        getElement(locator).sendKeys(value);
    }


    @Step("finding the element using:{0}")
    public WebElement getElement(By locator) {
        ChainTestListener.log("locator: "+locator.toString());
        return driver.findElement(locator);
    }

    @Step("clicking on element using:{0}")
    public void doClick(By locator) {
        getElement(locator).click();
    }
    @Step("fetching the element text using:{0}")
    public String doElementGetText(By locator) {
        String eleText = getElement(locator).getText();
        System.out.println("element text: " + eleText);
        return eleText;
    }


    //DoM Attribute value
    public String getElementDomAttributeValue(By locator, String attrName) {
        nullCheck(attrName);
        return getElement(locator).getDomAttribute(attrName);
    }

    public String getElementDomPropertyValue(By locator, String propName) {
        nullCheck(propName);
        return getElement(locator).getDomAttribute(propName);
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return getElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("element is not present on the page using : " + locator);
            return false;
        }
    }

    //*****************FindElements methods *****************//

    public List<String> getElementTextList(By locator) {
        List<WebElement> eleList = getElements(locator);
        List<String> eleTextList = new ArrayList<String>();
        for (WebElement e : eleList) {
            String text = e.getText();
            if (text.length() != 0) {
                System.out.println(text);
                eleTextList.add(text);
            }
        }

        return eleTextList;
    }

    public int getElementsCount(By locator) {
        int eleCount = getElements(locator).size();
        System.out.println("element count ==>" + eleCount);
        return eleCount;

    }

    public boolean checkElementDisplayed(By locator) {
        if (getElements(locator).size() == 1) {
            System.out.println("element: " + locator + " is displayed on the page time ");
            return true;
        }
        return false;
    }

    public List<WebElement> getElements(By locator) {

        return driver.findElements(locator);
    }

    public boolean checkElementDisplayed(By locator, int expElementCount) {
        if (getElements(locator).size() == expElementCount) {
            System.out.println("element: " + locator + " is displayed on the page time " + expElementCount);
            return true;
        }
        return false;
    }

    //******************click element************************

    public void clickElement(By locator, String value) {
        List<WebElement> elist = getElements(locator);
        System.out.println(elist.size());
        for (WebElement e : elist) {
            String text = e.getText();
            if (text.contains(value)) {
                e.click();
                break;
            }
        }
    }


    // ******************Select MultiPle dropdown**************
    public boolean selectDropDownValue(By locator, String value) {
        Select select = new Select(getElement(locator));
        List<WebElement> optionList = select.getOptions();
        System.out.println(optionList.size());
        boolean flag = false;
        for (WebElement e : optionList) {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value)) {
                e.click();
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println(value + " is selected");
            return true;
        } else {
            System.out.println(value + " value is not selected");
            return false;
        }
    }


    //*********************ACTIONS UTILS***************************

    public void doMoveToElement(By locator) throws InterruptedException {
//        Actions act = new Actions(driver);
        act.moveToElement(getElement(locator)).build().perform();
        Thread.sleep(2000);

    }

    public void handleParentSubMenu(By parentMenu, By subMenu) throws InterruptedException {

        doMoveToElement(parentMenu);
        doClick(subMenu);
    }

    public void handle4levelMenu(By level1Menu, By level2Menu, By level3Menu, By level4Menu) throws InterruptedException {
        doClick(level1Menu);
        Thread.sleep(2000);
        doMoveToElement(level2Menu);
        Thread.sleep(2000);
        doMoveToElement(level3Menu);
        Thread.sleep(2000);
        doClick(level4Menu);
    }

    public void doActionsSendKeys(By locator, String value) {
//        Actions act=new Actions(driver);
        act.sendKeys(getElement(locator), value).build().perform();
    }

    public void doActionsClick(By locator) {
//        Actions act=new Actions(driver);
        act.click(getElement(locator)).build().perform();
    }

    public void doSendKeysWithPause(By locator, String value, long pauseTime) {
//        Actions act=new Actions(driver);
        char valu[] = value.toCharArray();
        for (char ch : valu) {
            act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
        }
    }


    //****************** Explicit Wait*****************

    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));//Duraiton.ofseconds is Selenium 4.X feature
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Step("waiting for an element using:{0} and timeout:{1}")
    public WebElement waitForElementVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));//Duraiton.ofseconds is Selenium 4.X feature
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void sendKeyWithWait(By locator, int timeOut, CharSequence... value) {
        waitForElementVisible(locator, timeOut).sendKeys(value);
    }

    public WebElement getElementWithWait(By lcoator, int timeOut) {
        return getElementWithWait(lcoator, timeOut);

    }


    /*********wait for Alert(JS POPUP)****************/
    public Alert waitForAlert(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAler(int timeOut) {
        waitForAlert(timeOut).accept();
    }


    public void dismissAler(int timeOut) {
        waitForAlert(timeOut).dismiss();
    }


    public String getTextAler(int timeOut) {
        return waitForAlert(timeOut).getText();
    }


    public void acceptAler(int timeOut, String value) {
        waitForAlert(timeOut).sendKeys(value);
    }

    /****** Wait for Title **************/

    public String waitForTitleContains(String fractionTitle, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

        try {
            wait.until(ExpectedConditions.titleContains(fractionTitle));
            return driver.getTitle();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String waitForTitleIs(String Title, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.titleIs(Title));
            return driver.getTitle();
        } catch (TimeoutException e) {
            return null;
        }
    }

    /********* wait Utils**********/

    public List<WebElement> waitForAllElementsPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitForAllElementsVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            return Collections.EMPTY_LIST;  // equivalent to 0 when size is 0
        }

    }

    /***url contains ***/

    public String waitForURLContains(String fractionURL, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlContains(fractionURL));
            return driver.getCurrentUrl();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String waitForURLIs(String url, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            wait.until(ExpectedConditions.urlToBe(url));
            return driver.getCurrentUrl();
        } catch (TimeoutException e) {
            return null;
        }
    }


    /*** Frame *****/
    public void waitForFrameAndSwitchToItUsingBy(By framellocator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(framellocator));
    }

    public void waitForFrameAndSwitchTo(String frameIDOrName, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
    }

    public void waitForFrameAndSwitchTo(int index, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    }

    public void waitForFrameAndSwitchTo(WebElement element, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can click it
     *
     * @param locator
     * @param timeOut
     */
    @Step("waiting for an element and clicking using:{0} and timeout:{1}")
    public void clickWhenReady(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**** wait fir windows****/
    public Boolean waitForWinow(int expectedWindowsCount, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        try {
            return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindowsCount));
        } catch (Exception e) {
            System.out.println("Expected no of windows are not correct");
            return false;
        }
    }

    /********* Fluent Wait****/
    public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).withMessage("==Element is not found");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).withMessage("==Element is not found");

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}