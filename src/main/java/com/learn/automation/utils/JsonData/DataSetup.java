package com.learn.automation.utils.JsonData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;



public class DataSetup {
    //##### PROJECT DIRECTORY #####
    public static String pathOfProjectHomeDirectory;

    //# BROWSERS

    //##### EXCEL WORKBOOKS & WORKSHEETS (JSON FILES) #####


    public static String SheetForAccountCreation;
    static{
        try {
            initializeDataSetup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeDataSetup() throws Exception{
        String strPropertyFileName = System.getProperty("user.dir") + File.separator + "Config.properties";

        if ( false != PropertyFile.LoadPropertyFile(strPropertyFileName)){
            pathOfProjectHomeDirectory = PropertyFile.getKeyValue("ProjectDir");
            if (pathOfProjectHomeDirectory.equals("")){
                pathOfProjectHomeDirectory = System.getProperty("user.dir");
            }
            String strTestDataFolder = pathOfProjectHomeDirectory + File.separator + "TestDataStore" + File.separator;
            SheetForAccountCreation = strTestDataFolder+ PropertyFile.getKeyValue("SheetForAccountCreation");
            System.out.println("SheetForAccountCreation"+SheetForAccountCreation);
            System.out.println("strTestDataFolder"+strTestDataFolder);

        }
    }
}




