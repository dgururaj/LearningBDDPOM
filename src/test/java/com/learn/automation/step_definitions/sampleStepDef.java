package com.learn.automation.step_definitions;

import com.learn.automation.BaseStepDef;
import com.learn.automation.utils.DataHelper;
import com.learn.automation.utils.GetdatafromExcel;
import com.learn.automation.utils.common.CommonUtil;
import com.learn.automation.utils.sampleTestPageUtil;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.List;

import static com.usmanhussain.habanero.framework.AbstractPage.getDriver;

public class sampleStepDef extends BaseStepDef {

    private sampleTestPageUtil SampleTestPageUtil = new sampleTestPageUtil();
    private GetdatafromExcel readExcel = new GetdatafromExcel();
    CommonUtil commonUtil = new CommonUtil();
    public List<HashMap<String,String>> datamap;

    Scenario scenario;
    @Before
    public void before(Scenario scenario) {
       this.scenario = scenario;
        datamap = DataHelper.data(System.getProperty("user.dir")+"/testdata.xlsx","DataSheet1");

    }

    @Given("^I have navigated to a website$")
    public void iHaveNavigatedToAWebsite() throws Throwable {
        getDriver().get("http://www.bing.com");
        String URLbyGET = getDriver.getCurrentUrl();
        for (int i = 0; i < 4; i++) {
            System.out.println("Browser Refresh:: "+URLbyGET+ " "  + i);
            commonUtil.BrowserRefreshByCurrentURL(URLbyGET);
            Thread.sleep(2000);
        }
    }

    @When("^I search for the following '(.+)'$")
    public void iSearchForTheFollowing(String searchTerm) throws Throwable {
        SampleTestPageUtil.enterTextAndClickSearch(searchTerm);
    }

    @Then("^The correct (.+) appears on the screen$")
    public void theCorrectResultsAppearOnTheScreen(String searchResultTerm) throws Throwable {
        SampleTestPageUtil.verifyTextInSearchBox(searchResultTerm);
    }

    @Then("^The correct \"([^\"]*)\" appears on the screen from excel$")
    public void theCorrectAppearsOnTheScreenFromExcel(String arg0) throws Throwable {
        SampleTestPageUtil.verifyTextInSearchBox(readExcel.get(scenario.getName(), arg0));
    }

    @When("^I search for the following \"([^\"]*)\" from excel$")
    public void iSearchForTheFollowingFromExcel(String arg0) throws Throwable {
        SampleTestPageUtil.enterTextAndClickSearch(readExcel.get(scenario.getName(), arg0));
    }

    @When("^I search for the following (.+) with excelScenarioOutline$")
    public void iSearchForTheFollowingSearchTermWithExcelScenarioOutline(String arg0) throws Throwable {
        int index = Integer.parseInt(arg0)-1;
//        System.out.println("Printing current data set...");
//        for(HashMap h:datamap)
//        {
//            System.out.println(h.keySet());
//            System.out.println(h.values());
//        }
        SampleTestPageUtil.enterTextAndClickSearch(datamap.get(index).get("searchTerm"));
    }

    @Then("^The correct (.+) appears on the screen with excelScenarioOutline$")
    public void theCorrectSearchTermAppearsOnTheScreenWithExcelScenarioOutline(String arg0) throws Throwable {
        int index = Integer.parseInt(arg0)-1;
//        System.out.println("Printing current data set...");
//        for(HashMap h:datamap)
//        {
//            System.out.println(h.keySet());
//            System.out.println(h.values());
//        }
        SampleTestPageUtil.verifyTextInSearchBox(datamap.get(index).get("searchTerm"));
    }
}
