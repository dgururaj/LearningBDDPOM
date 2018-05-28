package com.learn.automation.utils;

import com.learn.automation.pages.DynamicsAccountsPage;
import com.learn.automation.pages.DynamicsLeadsPage;
import com.learn.automation.utils.common.CommonUtil;
import com.usmanhussain.habanero.framework.AbstractPage;

import java.util.Set;

public class DynamicsLeadsPageUtil extends AbstractPage {
    DynamicsAccountsPage dynamicsAccountsPage = new DynamicsAccountsPage();
    DynamicsLeadsPage dynamicsLeadsPage = new DynamicsLeadsPage();
    CommonUtil commonUtil = new CommonUtil();

    public void EnterTopicName(String topicName) throws Exception {
        switchToFrameById(dynamicsLeadsPage.FrameSecond());
        commonUtil.HighLight(dynamicsLeadsPage.TopicName());
        commonUtil.TypeByAction(dynamicsLeadsPage.TopicName(), topicName);
    }

    public void EnterName(String FirstName, String LastName) throws Exception {
        commonUtil.HighLight(dynamicsLeadsPage.Name());
        commonUtil.ClickByAction(dynamicsLeadsPage.Name());
        commonUtil.HighLight(dynamicsLeadsPage.FirstName());
        commonUtil.TypeByAction(dynamicsLeadsPage.FirstName(), FirstName);
        commonUtil.HighLight(dynamicsLeadsPage.LastName());
        commonUtil.TypeByAction(dynamicsLeadsPage.LastName(), LastName);
    }

    public void LinkExistingContactName(String Contact,String Account) throws Exception {
        commonUtil.HighLight(dynamicsLeadsPage.ExistingContactClick());
        commonUtil.TypeByAction(dynamicsLeadsPage.ExistingContactClick(), Contact);
        commonUtil.HighLight(dynamicsLeadsPage.ExistingAccountClick());
        commonUtil.TypeByAction(dynamicsLeadsPage.ExistingAccountClick(), Account);
    }

    public void SaveButton(){
        switchToDefault();
        commonUtil.ClickByAction(dynamicsLeadsPage.SaveButton());
    }


}
