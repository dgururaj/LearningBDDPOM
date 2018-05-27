package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsHomePage;
import com.learn.automation.pages.DynamicsLoginPage;
import com.learn.automation.utils.common.CommonUtil;
import com.usmanhussain.habanero.framework.AbstractPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DynamicsHomePageUtil extends AbstractPage {
    DynamicsHomePage dynamicsHomePage = new DynamicsHomePage();
    CommonUtil commonUtil = new CommonUtil();


    public void ClickSalesTile() throws Exception {

        String URLbyGET = getDriver.getCurrentUrl();
        for (int i = 0; i < 4; i++) {
            System.out.println("Browser Refresh:: " + URLbyGET + " " + i);
            commonUtil.BrowserRefreshByCurrentURL(URLbyGET);
            Thread.sleep(2000);
        }
        Thread.sleep(5000);
        boolean dtrue = dynamicsHomePage.SalesTile().isDisplayed();
        System.out.println("dtrue-" + dtrue);
        //commonUtil.ClickByAction(dynamicsHomePage.SalesTile());
        commonUtil.clickbyJS(dynamicsHomePage.SalesTile());
        //dynamicsHomePage.SalesTile().click();
        System.out.println("SalesTile is clicked");
    }

    public void NavigateToSalesMOduleByURL() {
        commonUtil.BrowserRefreshByCurrentURL("https://dgr.crm.dynamics.com/main.aspx?appid=8f216f5a-6d5b-e811-a979-000d3a37d203#220175621");

    }

    public void ClickAccountsObject() throws Exception {
        //getDriver.navigate().refresh();
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        waitForPageLoad();
        commonUtil.HighLight(dynamicsHomePage.NavigationArrow2());
        commonUtil.clickbyJS(dynamicsHomePage.NavigationArrow2());
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        waitForPageLoad();
        ;
        commonUtil.HighLight(dynamicsHomePage.AccountsObject());
        dynamicsHomePage.AccountsObject().click();
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        /*boolean btrue = dynamicsHomePage.NavigationArrow().isDisplayed();

        System.out.println("btrue-ClickAccountsObject" + btrue);

        if (btrue) {
            for (int i = 0; i < 3; i++) {
                commonUtil.clickbyJS(dynamicsHomePage.NavigationArrow2());
            }
            //dynamicsHomePage.NavigationArrow().click();
            // waitForPageLoad();
            dynamicsHomePage.AccountsObject().click();*/
    }


}
