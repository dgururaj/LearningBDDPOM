package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsAccountsPage;
import com.learn.automation.pages.DynamicsHomePage;
import com.learn.automation.utils.common.CommonUtil;
import com.usmanhussain.habanero.framework.AbstractPage;

public class DynamicsAccountsPageUtil extends AbstractPage {
    DynamicsAccountsPage dynamicsAccountsPage = new DynamicsAccountsPage();
    CommonUtil commonUtil = new CommonUtil();

    public void ClickNewButton() {
        //commonUtil.WaitforCRMLoadingIcontoDisappear();
        commonUtil.WaitforCRMLoadingIcontoDisappear();
        dynamicsAccountsPage.NewButton().click(); }


}
