package com.learn.automation.pages;

import com.usmanhussain.habanero.framework.AbstractPage;
import net.serenitybdd.core.annotations.findby.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class sampleTestPage extends AbstractPage {

    public WebElement searchBox() {
        return waitForUnstableElement(By.id("sb_form_q"));
    }
    public WebElement clickSearch() {
        return waitForElementPresent(By.id("sb_form_go"));
    }
    public WebElement searchBarOption(String searchResultTerm) {
        return waitForElementPresent(By.xpath("//input[@id='sb_form_q'][@value='" + searchResultTerm + "']"));
    }
}
