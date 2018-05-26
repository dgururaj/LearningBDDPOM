package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class DynamicsHomePage extends AbstractPage {

    public WebElement TextMessage() {
        return findElement(By.xpath("//h2[text()='The home for all your business apps']"));
    }

    public WebElement SalesTile()

    {
        return waitForElementVisible(By.xpath("//bdi[text()='Sales']"));
        //return findElement(By.xpath("//bdi[text()='Sales']"));
        //   return waitForExpectedElement(By.xpath("//a[@title='Go to Sales Area']/following::span"),30);
    }

    public WebElement NavigationArrow() {
        return findElement(By.xpath("//a[@title='Go to Sales Area']/following::span"));
    }

    public WebElement NavigationArrow2() {
        return waitForExpectedElement(By.xpath("//a[@title='Go to Sales Area']/following::span"), 30);
    }

    //
    public WebElement AccountsObject() {
        return findElement(By.xpath("//span[text()='Accounts']"));
    }


}
