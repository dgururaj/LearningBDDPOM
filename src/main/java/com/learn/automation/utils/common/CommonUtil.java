package com.learn.automation.utils.common;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil extends AbstractPage {

    public static String regexOpertion(String regexData, String value) {
        Pattern p = Pattern.compile(regexData);
        Matcher m = p.matcher(value);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public void setValue(List<List<String>> data, String fieldName, WebElement property) throws NoSuchElementException {
        String strValue1 = datapicker(data, fieldName);
        if (!strValue1.equalsIgnoreCase("")) {
            property.sendKeys(strValue1);
        }
    }

    public void setValue(List<String> columnvalue, List<String> columnname, String fieldName, WebElement property) throws Throwable {
        String strValue1 = datapicker(columnvalue, columnname, fieldName);
        if (!strValue1.equalsIgnoreCase("")) {
            property.sendKeys(strValue1);
        }
    }

    public String datapicker(List<List<String>> data, String fieldName) {
        boolean flag = false;
        String strValue = null;
        for (int i = 0; i < data.size(); i++) {
            for (int k = 0; k < data.get(0).size(); k++) {
                String searchKey = data.get(0).get(k).toString();
                if (searchKey.equalsIgnoreCase(fieldName)) {
                    strValue = data.get(1).get(k).toString();
                    flag = true;
                    break;
                }
                if (flag == true) {
                    break;
                }
            }
        }
        return strValue;
    }

    public String datapicker(List<String> columnvalue, List<String> columnname, String fieldName) {
        boolean flag = false;
        String strValue = null;
        for (int k = 0; k < columnname.size(); k++) {
            String searchKey = columnname.get(k).toString();
            if (searchKey.equalsIgnoreCase(fieldName)) {
                for (int i = k; i < columnvalue.size(); i++) {
                    strValue = columnvalue.get(i).toString();
                    flag = true;
                    break;
                }
                if (flag == true) {
                    break;
                }
            }
        }
        return strValue;
    }

    JavascriptExecutor Javascript;

    public void switchFrame(String frameName) throws Throwable {
        WebDriverWait wait = new WebDriverWait(getDriver, 20);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    public void switchFrameIfAvailable(String frameName) throws Throwable {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver, 20);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectElement(List<List<String>> data, String fieldName, WebElement property) throws Throwable {
        String title = datapicker(data, fieldName);
        if (!title.equalsIgnoreCase("")) {
            new Select(property).selectByVisibleText(title);
        }
    }

    public void selectElement(List<String> columnvalue, List<String> columnname, String fieldName, WebElement property) throws Throwable {
        String title = datapicker(columnvalue, columnname, fieldName);
        if (!title.equalsIgnoreCase("")) {
            new Select(property).selectByVisibleText(title);
        }
    }



    public void clickbyJS(WebElement strProperty) {
        JavascriptExecutor executor = getDriver;
        executor.executeScript("arguments[0].click();", strProperty);
    }

    public void tabKeypress() throws NoSuchElementException {
        Actions action = new Actions(getDriver);
        action.sendKeys(Keys.TAB).build().perform();
    }

    public void setValue(String data, WebElement property) throws Throwable {
        if (!data.equalsIgnoreCase("")) {
            property.sendKeys(data);
        }
    }

    public List<String> getIntegrationProperties(String integrationName) {
        Properties prop = new Properties();
        InputStream input = null;
        List<String> lstPerilQues = new ArrayList<String>();

        try {

            input = new FileInputStream("src/test/resources/Integration.properties");
            prop.load(input);
            String integrationFieldHeader = prop.getProperty(integrationName);
            String[] arrintegrationFieldHeader = integrationFieldHeader.split("#");
            lstPerilQues = Arrays.asList(arrintegrationFieldHeader);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return lstPerilQues;
    }


    public void ClickByAction(WebElement webElement) {
        Actions actions = new Actions(getDriver);
        actions.moveToElement(webElement).click().build().perform();
    }

    public void WaitforCRMLoadingIcontoDisappear() {

        try {

            System.out.println("WaitforCRMLoadingIcontoDisappear Starting...");
            while (getDriver.findElement(By.xpath("//img[@id='loading']")).isDisplayed()) {
                System.out.println("Loading Icon is displayed....");
                Thread.sleep(3000);
            }
            System.out.println("Xpath is not present so breaking while...");
        } catch (Exception e) {
            System.out.println("Inside WaitTillLoadingIconDisappear2 catch...");

        }

    }


}