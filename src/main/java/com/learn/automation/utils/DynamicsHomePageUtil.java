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
        getDriver.navigate().refresh();
        //Thread.sleep(15000);
        boolean dtrue = dynamicsHomePage.SalesTile().isDisplayed();
        System.out.println("dtrue-" + dtrue);
        //commonUtil.ClickByAction(dynamicsHomePage.SalesTile());
        commonUtil.clickbyJS(dynamicsHomePage.SalesTile());
        //dynamicsHomePage.SalesTile().click();
        System.out.println("SalesTile is clicked");
    }

    public void ClickAccountsObject() throws Exception {
        getDriver.navigate().refresh();
        waitForPageLoad();
        commonUtil.clickbyJS(dynamicsHomePage.NavigationArrow2());
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        waitForPageLoad();
        dynamicsHomePage.AccountsObject().click();

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
