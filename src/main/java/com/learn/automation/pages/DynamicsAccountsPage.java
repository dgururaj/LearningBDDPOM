package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DynamicsAccountsPage extends AbstractPage {


    public WebElement NewButton() {
        return waitForElementVisible(By.xpath("//span[text()=' New ']"));
    }





}
