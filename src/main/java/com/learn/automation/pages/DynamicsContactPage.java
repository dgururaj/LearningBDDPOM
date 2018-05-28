package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DynamicsContactPage extends AbstractPage {


    public WebElement QuickCreateContactText() {
        return waitForElementVisible(By.xpath("//div[text()='Quick Create: Contact']"));
    }

    public WebElement Frame1() {
        return waitForElementVisible(By.xpath("//iframe[@id='NavBarGloablQuickCreate']"));
    }

    public WebElement Frame2() {
        return waitForElementVisible(By.xpath("//iframe[@id='contentIFrame1']"));
    }


    public WebElement Frame3() {
        return waitForElementVisible(By.xpath("//iframe[@id='contentIFrame0']"));
    }


    public WebElement Frame4() {
        return waitForElementVisible(By.xpath("//iframe[@id='customScriptsFrame']"));
    }

    public WebElement FirstName() {
        return waitForElementVisible(By.xpath("//div[@id='firstname']"));
    }


    public WebElement LastName() {
        return waitForElementVisible(By.xpath("//div[@id='lastname']"));
    }


    public WebElement SaveButton() {
        return waitForElementVisible(By.xpath("//button[text()='Save']"));
    }


}
