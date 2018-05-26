package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsLoginPage;
import com.saucelabs.ci.sauceconnect.AbstractSauceTunnelManager;
import com.usmanhussain.habanero.framework.AbstractPage;
import cucumber.api.Scenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DynamicsLoginPageUtil extends AbstractPage {
    DynamicsLoginPage dynamicsLoginPage = new DynamicsLoginPage();


    public void Login(String userName, String Password) throws IOException, InterruptedException {
        dynamicsLoginPage.SignInTextBox().sendKeys(userName);
        dynamicsLoginPage.NextButton().click();
        Thread.sleep(5000);
        dynamicsLoginPage.PassWordTextBox().sendKeys(Password);
        dynamicsLoginPage.NextButton().click();
        dynamicsLoginPage.NextButton().click();
        System.out.println(userName + " Login is Successful");

    }


}
