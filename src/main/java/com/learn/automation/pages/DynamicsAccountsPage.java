package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DynamicsAccountsPage extends AbstractPage {


    public WebElement NewButton() {
        return waitForElementVisible(By.xpath("//span[text()=' New ']"));
    }

    public WebElement FrameFirst() {
        return waitForElementVisible(By.xpath("//iframe[contains(@id,'contentIFrame')]"));
    }

    public WebElement FrameSecond() {
        return waitForElementVisible(By.xpath("(//iframe[contains(@id,'contentIFrame')])[2]"));
    }

    public WebElement AccountName() {
        return waitForElementVisible(By.xpath("//div[@id='name']"));
    }

    // telephone1
    public WebElement Phone() {
        return waitForElementVisible(By.xpath("//div[@id='telephone1']"));
    }

    // customertypecode
    public WebElement RelationShipType() {
        return waitForElementVisible(By.xpath("//div[@id='customertypecode']"));
    }

    public WebElement RelationShipTypeDD() {
        return waitForElementVisible(By.xpath("//select[@id='customertypecode_i']"));
    }
    public WebElement SaveButton() {
        return waitForElementVisible(By.xpath("//span[contains(text(),'Save')]"));
    }

    public WebElement AccountText() {
        return waitForElementVisible(By.xpath("//h1[@class='ms-crm-TextAutoEllipsis']"));
    }
    public WebElement NewContactButton()

    {
        return waitForElementVisible(By.xpath("//img[@id='Contacts_addImageButtonImage']"));
    }


}
