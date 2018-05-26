package com.learn.automation.utils.JsonData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
    private static FileInputStream fStream;
    private static FileOutputStream fOutStream;
    private static Properties propertyFile;
    private static String strFilePath;

    public static boolean LoadPropertyFile(String strPropertyFileName) throws IOException {
        propertyFile = new Properties();
        strFilePath = strPropertyFileName;

        boolean bStatus = false;
        try {
            fStream = new FileInputStream(strPropertyFileName);
            propertyFile.load(fStream);
            bStatus = true;
        } catch (Exception e) {
            bStatus = false;
            e.printStackTrace();
            System.out.println("Property File not present in folder " + strPropertyFileName);
        }
        return bStatus;
    }

    public static String getKeyValue(String strKey) throws Exception {
        String strValue = null;
        try {
            strValue = propertyFile.getProperty(strKey);
        } catch (Exception e) {
            e.printStackTrace();
            strValue = null;
            System.out.println("Error in retrieving value from Property File");
        }
        return strValue;
    }

    public static void setKeyValue(String strKey, String strValue) throws Exception {
        try {
            //PropertiesConfiguration config = new PropertiesConfiguration(strFilePath);

            fOutStream = new FileOutputStream(strFilePath);
            propertyFile.setProperty(strKey, strValue);
            propertyFile.store(fOutStream, null);
            fOutStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in seting value to Property File");
        }
    }
}
