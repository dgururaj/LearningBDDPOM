package com.learn.automation.utils;

import com.learn.automation.pages.sampleTestPage;
import com.usmanhussain.habanero.framework.AbstractPage;

public class sampleTestPageUtil extends AbstractPage {

    private sampleTestPage SampleTestPage = new sampleTestPage();

    public void enterTextAndClickSearch(String search) {
        SampleTestPage.searchBox().sendKeys(search);
        SampleTestPage.clickSearch().click();
    }

    public void verifyTextInSearchBox(String searchResultTerm) {
        SampleTestPage.searchBarOption(searchResultTerm).isDisplayed();
    }
}