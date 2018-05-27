package com.learn.automation.utils;

import com.google.api.services.gmail.model.Thread;
import com.learn.automation.pages.DynamicsAccountsPage;
import com.learn.automation.pages.DynamicsHomePage;
import com.learn.automation.utils.common.CommonUtil;
import com.sebuilder.interpreter.steptype.SwitchToDefaultContent;
import com.sebuilder.interpreter.steptype.SwitchToFrame;
import com.usmanhussain.habanero.framework.AbstractPage;

import java.util.Set;

public class DynamicsAccountsPageUtil extends AbstractPage {
    DynamicsAccountsPage dynamicsAccountsPage = new DynamicsAccountsPage();
    CommonUtil commonUtil = new CommonUtil();

    public void ClickNewButtonAndTypeName(String AccountName) throws Exception {
        //commonUtil.WaitforCRMLoadingIcontoDisappear();
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        commonUtil.HighLight(dynamicsAccountsPage.NewButton());
        dynamicsAccountsPage.NewButton().click();
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        switchToFrameById(dynamicsAccountsPage.FrameSecond());
        commonUtil.HighLight(dynamicsAccountsPage.AccountName());
        commonUtil.TypeByAction(dynamicsAccountsPage.AccountName(), AccountName);
    }

    public void EnterPhone(String Phone) throws Exception {
        commonUtil.HighLight(dynamicsAccountsPage.Phone());
        commonUtil.TypeByAction(dynamicsAccountsPage.Phone(), Phone);
    }
    public void SelectRelationShipType(String VisibleText) throws Exception {
        commonUtil.HighLight(dynamicsAccountsPage.RelationShipType());
        commonUtil.ClickByAction(dynamicsAccountsPage.RelationShipType());
        selectDrop(dynamicsAccountsPage.RelationShipTypeDD(),VisibleText);
        switchToDefault();
        commonUtil.HighLight(dynamicsAccountsPage.SaveButton());
        dynamicsAccountsPage.SaveButton().click();
    }
    public boolean VerifyValidInfoIsPresent(String TxtToVerify) throws Exception {
        boolean b=false;
        switchToFrameById(dynamicsAccountsPage.FrameSecond());
        waitForPageLoad();
        waitForPageLoad();
        commonUtil.HighLight(dynamicsAccountsPage.AccountText());
        if(dynamicsAccountsPage.AccountText().getText().equals(TxtToVerify)){
            b=true;
            System.out.println(b); }
        System.out.println(b);
        return b;
    }
    public void ClickNewContactButton() throws Exception {

        commonUtil.HighLight(dynamicsAccountsPage.NewContactButton());
        commonUtil.ClickByAction(dynamicsAccountsPage.NewContactButton());
        Set<String> all = getDriver.getWindowHandles();
        System.out.println(all.size());
        for (String string : all) {
            System.out.println(all);
        }

    }



}
