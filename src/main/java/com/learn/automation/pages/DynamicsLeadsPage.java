package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DynamicsLeadsPage extends AbstractPage {


    public WebElement NewButton() {
        return waitForElementVisible(By.xpath("//span[text()=' New ']"));
    }

    public WebElement FrameFirst() {
        return waitForElementVisible(By.xpath("//iframe[contains(@id,'contentIFrame')]"));
    }

    public WebElement FrameSecond() {
        return waitForElementVisible(By.xpath("(//iframe[contains(@id,'contentIFrame')])[2]"));
    }

    public WebElement TopicName() {
       // return waitForElementVisible(By.xpath("//div[@id='name']"));
        return  waitForExpectedElement(By.xpath("//div[@id='subject']"),120);
    }

    // telephone1
    public WebElement Name()
    {
        return waitForElementVisible(By.xpath("//div[@id='fullname']"));
    }

    // customertypecode
    public WebElement FirstName() {
        return waitForElementVisible(By.xpath("//div[@id='fullname_compositionLinkControl_firstname']"));
    }

    public WebElement LastName() {

        return waitForElementVisible(By.xpath("//div[@id='fullname_compositionLinkControl_lastname']"));
    }
    public WebElement SaveButton() {
        return waitForElementVisible(By.xpath("//span[contains(text(),'Save')]"));
    }

    public WebElement ExistingContactClick() {

        return waitForExpectedElement(By.xpath("//span[text()='click to enter']"),10);
        //return findElement(By.xpath("//span[text()='click to enter']"));
    }
    public WebElement ExistingAccountClick() {
        return findElement(By.xpath("(//span[text()='click to enter'])[2]"));
    }



}
