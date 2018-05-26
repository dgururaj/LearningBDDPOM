package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DynamicsLoginPage extends AbstractPage {

    public WebElement SignInTextBox() {
        return findElement(By.name("loginfmt"));
    }
    public WebElement NextButton()
    {
        return findElement(By.xpath("//input[@type='submit']"));
    }
    public WebElement PassWordTextBox()
    {
        return findElement(By.name("passwd"));
    }




}
