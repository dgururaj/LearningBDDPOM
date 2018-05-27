package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsLoginPage;
import com.learn.automation.utils.common.CommonUtil;
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
    CommonUtil commonUtil = new CommonUtil();

    public void Login(String userName, String Password) throws Exception {
        commonUtil.HighLight(dynamicsLoginPage.SignInTextBox());
        dynamicsLoginPage.SignInTextBox().sendKeys(userName);
        dynamicsLoginPage.NextButton().click();
        commonUtil.HighLight(dynamicsLoginPage.NextButton());
        Thread.sleep(5000);
        commonUtil.HighLight(dynamicsLoginPage.PassWordTextBox());
        dynamicsLoginPage.PassWordTextBox().sendKeys(Password);
        commonUtil.HighLight(dynamicsLoginPage.NextButton());
        dynamicsLoginPage.NextButton().click();
        commonUtil.HighLight(dynamicsLoginPage.NextButton());
        dynamicsLoginPage.NextButton().click();
        System.out.println(userName + " Login is Successful");

    }


}
