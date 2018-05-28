package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsAccountsPage;
import com.learn.automation.pages.DynamicsContactPage;
import com.learn.automation.utils.common.CommonUtil;
import com.usmanhussain.habanero.framework.AbstractPage;

import java.util.Set;

public class DynamicsContactsPageUtil extends AbstractPage {
    DynamicsAccountsPage dynamicsAccountsPage = new DynamicsAccountsPage();
    DynamicsContactPage dynamicsContactPage = new DynamicsContactPage();
    CommonUtil commonUtil = new CommonUtil();

    public void CreateContact(String fName, String lName) throws Exception {
        Thread.sleep(20000);
        commonUtil.HighLight(dynamicsContactPage.QuickCreateContactText());
        switchToFrameById(dynamicsContactPage.Frame1());
        //switchToFrameById(dynamicsContactPage.Frame4());
        commonUtil.HighLight(dynamicsContactPage.FirstName());
        commonUtil.TypeByAction(dynamicsContactPage.FirstName(), fName);
        commonUtil.HighLight(dynamicsContactPage.LastName());
        commonUtil.TypeByAction(dynamicsContactPage.LastName(), lName);
        switchToDefault();
        commonUtil.HighLight(dynamicsContactPage.SaveButton());
        commonUtil.ClickByAction(dynamicsContactPage.SaveButton());



    }

}
