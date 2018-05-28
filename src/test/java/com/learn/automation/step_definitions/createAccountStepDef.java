package com.learn.automation.step_definitions;

import com.learn.automation.BaseStepDef;
import com.learn.automation.pages.DynamicsLeadsPage;
import com.learn.automation.utils.*;
import com.learn.automation.utils.JsonData.DataSetup;
import com.learn.automation.utils.JsonData.ExcelToJSON;
import com.learn.automation.utils.JsonData.JSON;
import com.learn.automation.utils.JsonData.JSONToExcel;
import com.learn.automation.utils.common.RandomGenerator;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class createAccountStepDef extends BaseStepDef {


    private GetdatafromExcel readExcel = new GetdatafromExcel();
    DynamicsLoginPageUtil dynamicsLoginPageUtil = new DynamicsLoginPageUtil();
    DynamicsHomePageUtil dynamicsHomePageUtil = new DynamicsHomePageUtil();
    DynamicsAccountsPageUtil dynamicsAccountsPageUtil = new DynamicsAccountsPageUtil();
    DynamicsContactsPageUtil dynamicsContactsPageUtil = new DynamicsContactsPageUtil();
    DynamicsLeadsPageUtil dynamicsLeadsPageUtil = new DynamicsLeadsPageUtil();
    public List<HashMap<String, String>> datamap;
    Scenario scenario;
    public String ScenarioName = "TC_01_Create Account";

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
        //JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        String URL = JSON.readDataFromJSON("LoginInfo", "URL", "SL_NO", "1");
        String UserName = JSON.readDataFromJSON("LoginInfo", "Username", "Name", "Gururaj");
        String PassWord = JSON.readDataFromJSON("LoginInfo", "Password", "Name", "Gururaj");
        System.out.println("URL from JSON-" + URL);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().get(URL);
        dynamicsLoginPageUtil.Login(UserName, PassWord);
        // String strSheetName, String strValue, String strColumnNameTobeUpdated, String strKeyColumnName, String strKeyColumnValue
        JSON.writeDataToJSON("LoginInfo", "ProdSupport", "Profiles", "Name", "Gururaj");
    }


    @And("Navigates to Sales Module$")
    public void NavigatesToSalesModule() throws Throwable {
        //dynamicsHomePageUtil.ClickSalesTile();
        dynamicsHomePageUtil.NavigateToSalesMOduleByURL();
    }

    @And("Selects Account Under Customers$")
    public void SelectsAccountUnderCustomers() throws Throwable {
        getDriver().navigate().refresh();
        dynamicsHomePageUtil.ClickAccountsObject();
    }

    @When("An Account is created by the user$")
    public void AnAccountIsCreatedByTheUser() throws Throwable {
        System.out.println("Starting GenerateRandomFirstName part2 ");
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        Date date = new Date();
        String strLastName = "Accnt " + dateFormat.format(date);
        strLastName = strLastName.replace(" ", "");
        strLastName = strLastName.replace("/", "");
        strLastName = strLastName.replace(":", "");
        System.out.println(strLastName);
        Thread.sleep(5000);
        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        String AccountName = JSON.readDataFromJSON("CreateAccount", "AccountName", "ScenarioName", ScenarioName);
        String Phone = JSON.readDataFromJSON("CreateAccount", "Phone", "ScenarioName", ScenarioName);
        JSON.writeDataToJSON("CreateAccount", AccountName + strLastName, "FinalAccountName", "ScenarioName", ScenarioName);
        dynamicsAccountsPageUtil.ClickNewButtonAndTypeName(AccountName + strLastName);
        Thread.sleep(3000);
        dynamicsAccountsPageUtil.EnterPhone(Phone);
        Thread.sleep(3000);
        dynamicsAccountsPageUtil.SelectRelationShipType("Customer");
    }

    @Then("Verify valid account information is captured in webpage$")
    public void VerifyValidAccountInformationIsCapturedInWebpage() throws Throwable {
        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        String AccountNameF = JSON.readDataFromJSON("CreateAccount", "FinalAccountName", "ScenarioName", ScenarioName);
        if (dynamicsAccountsPageUtil.VerifyValidInfoIsPresent(AccountNameF)) {
            JSON.writeDataToJSON("CreateAccount", "PASS", "Status", "ScenarioName", ScenarioName);
        } else {
            JSON.writeDataToJSON("CreateAccount", "FAIL", "Status", "ScenarioName", ScenarioName);
        }

    }

    @Given("User creates a Quick contact$")
    public void UserCreatesAQuickContact() throws Exception {
        System.out.println("S2 Given  - UserCreatesAQuickContact");
        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        dynamicsAccountsPageUtil.ClickNewContactButton();
        String fname = "Auto";
        String lName = RandomGenerator.CurrentDateTimeStamp("Test");
        String fcontactName = fname + " " + lName;
        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        JSON.writeDataToJSON("CreateAccount", fcontactName, "ContactName", "ScenarioName", ScenarioName);
        dynamicsContactsPageUtil.CreateContact(fname, lName);
    }


    @And("Creates a Lead with mandatory information$")
    public void CreateLead() throws Exception {
        Thread.sleep(9000);
        dynamicsHomePageUtil.ClickLeadsObject();
        dynamicsAccountsPageUtil.ClickNewButton();
        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        String topicName = RandomGenerator.CurrentDateTimeStamp("Auto Policy ");
        JSON.writeDataToJSON("CreateAccount", topicName, "LeadTopicName", "ScenarioName", ScenarioName);
        dynamicsLeadsPageUtil.EnterTopicName(topicName);
        String AccountNameLINK = JSON.readDataFromJSON("CreateAccount", "FinalAccountName", "ScenarioName", ScenarioName);
        String ContactNameLINK = JSON.readDataFromJSON("CreateAccount", "ContactName", "ScenarioName", ScenarioName);
        String AccountNameL = JSON.readDataFromJSON("CreateAccount", "LeadFirstName", "ScenarioName", ScenarioName);
        dynamicsLeadsPageUtil.EnterName(AccountNameL, RandomGenerator.CurrentDateTimeStamp("LCont"));
        dynamicsLeadsPageUtil.LinkExistingContactName(ContactNameLINK, AccountNameLINK);
        dynamicsLeadsPageUtil.SaveButton();
    }

    @When("Lead is associated to Existing Account and Contact$")
    public void AssociateLead() throws Exception {

        JSON.createJSONObj(DataSetup.SheetForAccountCreation);
        String AccountNameLINK = JSON.readDataFromJSON("CreateAccount", "FinalAccountName", "ScenarioName", ScenarioName);
        String ContactNameLINK = JSON.readDataFromJSON("CreateAccount", "ContactName", "ScenarioName", ScenarioName);
       // String AccountNameL = JSON.readDataFromJSON("CreateAccount", "LeadFirstName", "ScenarioName", ScenarioName);
        dynamicsLeadsPageUtil.LinkExistingContactName(ContactNameLINK, AccountNameLINK);
        dynamicsLeadsPageUtil.SaveButton();
    }


}
