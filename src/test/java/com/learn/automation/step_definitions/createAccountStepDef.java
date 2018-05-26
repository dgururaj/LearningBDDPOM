package com.learn.automation.step_definitions;

import com.learn.automation.BaseStepDef;
import com.learn.automation.pages.DynamicsHomePage;
import com.learn.automation.utils.DynamicsAccountsPageUtil;
import com.learn.automation.utils.DynamicsHomePageUtil;
import com.learn.automation.utils.GetdatafromExcel;
import com.learn.automation.utils.DynamicsLoginPageUtil;
import com.learn.automation.utils.JsonData.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class createAccountStepDef extends BaseStepDef {


    private GetdatafromExcel readExcel = new GetdatafromExcel();
    DynamicsLoginPageUtil dynamicsLoginPageUtil = new DynamicsLoginPageUtil();
    DynamicsHomePageUtil dynamicsHomePageUtil = new DynamicsHomePageUtil();

    DynamicsAccountsPageUtil dynamicsAccountsPageUtil = new DynamicsAccountsPageUtil();
    public List<HashMap<String, String>> datamap;
    Scenario scenario;

    @Before
    public void before() throws IOException {
        try {

            System.out.println("@BeforeClass-createAccountStepDef");
            //Check if it is Batch Execution or StandAlone
            String strTestDataFolderPath = DataSetup.pathOfProjectHomeDirectory + File.separator + "TestDataStore";
            //String strTestDataFolderPath = "F:\\MyAutomation-master\\TestDataStore";
            System.out.println("strTestDataFolderPath=" + strTestDataFolderPath);
            ExcelToJSON.convertALLExcelFilesinFolderToJSON(strTestDataFolderPath, 0);
            System.out.println("@ Before Successful - Custom");
        } catch (Exception e) {
            System.out.println("Error in @Before in Step Def");
            e.printStackTrace();
        }

    }

    @After
    public void After() throws IOException {
        try {
            System.out.println("@After-createAccountStepDef");
            //Check if it is Batch Execution or StandAlone
            String strTestDataFolderPath = DataSetup.pathOfProjectHomeDirectory + File.separator + "TestDataStore";
            //String strTestDataFolderPath = "F:\\MyAutomation-master\\TestDataStore";
            System.out.println("strTestDataFolderPath=" + strTestDataFolderPath);
            JSONToExcel.convertALLJSONFilesinFolderToExcel(strTestDataFolderPath, 0);
            System.out.println("@ After Successful - Custom");
        } catch (Exception e) {
            System.out.println("Error in @After in Step Def");
            e.printStackTrace();
        }

    }

    @Given("^User Logs to Dynamics 365$")
    public void UserLogstoDynamics365() throws Throwable {
        JSON.createJSONObj("F:\\MyAutomation-master\\TestDataStore\\Global.txt");
        String URL = JSON.readDataFromJSON("LoginInfo", "URL", "SL_NO", "1");
        String UserName = JSON.readDataFromJSON("LoginInfo", "Username", "Name", "Gururaj");
        String PassWord = JSON.readDataFromJSON("LoginInfo", "Password", "Name", "Gururaj");
        System.out.println("URL from JSON-" + URL);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().get(URL);
        dynamicsLoginPageUtil.Login(UserName,PassWord);
        // String strSheetName, String strValue, String strColumnNameTobeUpdated, String strKeyColumnName, String strKeyColumnValue
        JSON.writeDataToJSON("LoginInfo", "ProdSupport", "Profiles", "Name", "Gururaj");
    }



    @And("Navigates to Sales Module$")
    public void NavigatesToSalesModule() throws Throwable {
        dynamicsHomePageUtil.ClickSalesTile();
    }
    @And("Selects Account Under Customers$")
    public void SelectsAccountUnderCustomers() throws Throwable {
        getDriver().navigate().refresh();
        dynamicsHomePageUtil.ClickAccountsObject();
    }
    @When("An Account is created by the user$")
    public void AnAccountIsCreatedByTheUser() throws Throwable {
        dynamicsAccountsPageUtil.ClickNewButton();



    }

}
