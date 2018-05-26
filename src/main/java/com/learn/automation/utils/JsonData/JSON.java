package com.learn.automation.utils.JsonData;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
    private static JSONObject jsonObject;
    private static String strjsonFilePathToWrite;

    public static JSONObject createJSONObj(String strjsonFilePath) {
        try {
            strjsonFilePathToWrite = strjsonFilePath;
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(strjsonFilePath));

            if (jsonObject != null)
                jsonObject = null;
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            System.out.println("Error in creating JSon object");
        }

        return jsonObject;
    }

    public static int getRowCountOfSheetJSON(String strSheetName) {
        int iRowCount = 0;

        try {
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);
            iRowCount = arTestDatainAllRowsinSheet.size();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting RowCount for sheet:" + strSheetName);
        }

        return iRowCount;
    }

    public static String readDataFromJSON(String strSheetName, String strColumnNameTobeRead, String strKeyColumnName, String strKeyColumnValue) {
        String strValue = "";

        try {
            boolean bKeyColValueFound = false;
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            for (Object object : arTestDatainAllRowsinSheet) {
                JSONObject arTestDatainSingleRowinSheet = (JSONObject) object;

                String strKeyColumnValueRetrieved = (String) arTestDatainSingleRowinSheet.get(strKeyColumnName);
                if (strKeyColumnValueRetrieved.equals(strKeyColumnValue)) {
                    strValue = (String) arTestDatainSingleRowinSheet.get(strColumnNameTobeRead);
                    bKeyColValueFound = true;
                    System.out.println("Value retrieved: " + strColumnNameTobeRead + " = " + strValue + " (when " + strKeyColumnName + " = " + strKeyColumnValue + ")");
                    break;
                }
            }

            if (bKeyColValueFound == false) {
                System.out.println("Key Col Value not Found: " + strKeyColumnValue);
            }

            return strValue;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Possible Error Cause: Key Column not Found:" + strKeyColumnName + " or Column to be Retrieved not Found:" + strColumnNameTobeRead);
            return null;
        }
    }

    public static String readDataFromJSON(String strSheetName, String strColumnNameTobeRead, int iRowIndex) {
        String strValue = "";

        try {
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            strValue = (String) ((JSONObject) arTestDatainAllRowsinSheet.get(iRowIndex)).get(strColumnNameTobeRead);
            System.out.println("Value retrieved: " + strColumnNameTobeRead + " = " + strValue + " for RowIndex = " + iRowIndex);

            return strValue;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Possible Error Cause: Column to be Retrieved not Found:" + strColumnNameTobeRead);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeDataToJSON(String strSheetName, String strValue, String strColumnNameTobeUpdated, String strKeyColumnName, String strKeyColumnValue) {
        try {
            boolean bKeyColValueFound = false;
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            for (Object object : arTestDatainAllRowsinSheet) {
                JSONObject arTestDatainSingleRowinSheet = (JSONObject) object;

                String strKeyColumnValueRetrieved = (String) arTestDatainSingleRowinSheet.get(strKeyColumnName);
                if (strKeyColumnValueRetrieved.equals(strKeyColumnValue)) {
                    arTestDatainSingleRowinSheet.put(strColumnNameTobeUpdated, strValue);

                    bKeyColValueFound = true;
                    System.out.println("Value updated: " + strColumnNameTobeUpdated + " = " + strValue + " (when " + strKeyColumnName + " = " + strKeyColumnValue + ")");
                    break;
                }
            }

            if (bKeyColValueFound == false) {
                System.out.println("Key Col Value not Found: " + strKeyColumnValue);
            }

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Possible Error Cause: Key Column not Found:" + strKeyColumnName + " or Column to be Updated not Found:" + strColumnNameTobeUpdated);
        }
    }

    @SuppressWarnings("unchecked")
    public static void writeDataToJSON(String strSheetName, String strValue, String strColumnNameTobeUpdated, int iRowIndex) {
        try {
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            ((JSONObject) arTestDatainAllRowsinSheet.get(iRowIndex)).put(strColumnNameTobeUpdated, strValue);
            System.out.println("Value updated: " + strColumnNameTobeUpdated + " = " + strValue + " for RowIndex = " + iRowIndex);

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Possible Error Cause: Column to be Updated not Found:" + strColumnNameTobeUpdated);
        }
    }

    @SuppressWarnings("unchecked")
    public static void insertNewRowToJSONSummaryTestLogs(String strSheetName, String strTestCaseName, String strDescription, String strStatus, String strStartofExecution, String strEndofExecution, String strDuration, String strBrowser, String strProfiles) {
        try {
            boolean bEmptyEXELogSheet = false;
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            for (Object object : arTestDatainAllRowsinSheet) {
                JSONObject arTestDatainSingleRowinSheet = (JSONObject) object;

                if (((String) arTestDatainSingleRowinSheet.get("TestCase")).equals("Blank Row")) {
                    bEmptyEXELogSheet = true;
                } else
                    break;
            }

            JSONObject jsonObjNewRow = new JSONObject();

            jsonObjNewRow.put("TestCase", strTestCaseName);
            jsonObjNewRow.put("Description", strDescription);
            jsonObjNewRow.put("Status", strStatus);
            jsonObjNewRow.put("StartofExecution", strStartofExecution);
            jsonObjNewRow.put("EndofExecution", strEndofExecution);
            jsonObjNewRow.put("Duration", strDuration);
            jsonObjNewRow.put("Browser", strBrowser);
            jsonObjNewRow.put("Profiles", strProfiles);

            // if Sheet is not empty then append the new row to the existing rows. If sheet is empty then overwrite the JSON entry 'Blank Row Blank Row...' with the new first row data
            if (bEmptyEXELogSheet)
                arTestDatainAllRowsinSheet.clear();

            arTestDatainAllRowsinSheet.add(jsonObjNewRow);

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in inserting new row to JSON in insertNewRowToJSONSummaryTestLogs module");
        }
    }

    @SuppressWarnings("unchecked")
    public static void insertNewRowToJSON(String strSheetName, ArrayList<String> strCellDatainRow) {
        try {
            JSONArray arTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(strSheetName);

            ArrayList<String> arColumnNames = new ArrayList<String>();
            for (Object object : arTestDatainAllRowsinSheet) {
                JSONObject arTestDatainSingleRowinSheet = (JSONObject) object;
                for (Object keyColName : arTestDatainSingleRowinSheet.keySet()) {
                    arColumnNames.add(keyColName.toString());
                }
                break;
            }

            JSONObject jsonObjNewRow = new JSONObject();
            int iCount = 0;
            for (String strColumnName : arColumnNames) {
                jsonObjNewRow.put(strColumnName, strCellDatainRow.get(iCount));
                iCount++;
            }

            arTestDatainAllRowsinSheet.add(jsonObjNewRow);

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in inserting new row to JSON in insertNewRowToJSONSummaryTestLogs module");
        }
    }

    public static void updateJSONCellWithApend(String strSheetName, String strNewValue, String strColumnNameTobeUpdated, String strKeyColumnName, String strKeyColumnValue) {
        try {
            String strData = readDataFromJSON(strSheetName, strColumnNameTobeUpdated, strKeyColumnName, strKeyColumnValue);

            if (!strData.equals("")) {
                strNewValue = strData + "," + strNewValue;
            }

            writeDataToJSON(strSheetName, strNewValue, strColumnNameTobeUpdated, strKeyColumnName, strKeyColumnValue);

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in appending to cell in JSON in UpdateJSONCellWithApend module");
        }
    }

    @SuppressWarnings("unchecked")
    public static void createJSONTestLogFile(String xl_tc_name, ArrayList<String> al_xl_details, ArrayList<String> al_xl_results, ArrayList<String> al_xl_screenshot) {
        try {
            String strSheetName = "Results";
            Map<String, ArrayList<JSONObject>> mTestLOGData = new HashMap<>();

            ArrayList<JSONObject> alJSONObj = new ArrayList<>();
            for (int iRowIndex = 0; iRowIndex < al_xl_details.size(); iRowIndex++) {
                JSONObject jsonObjRow = new JSONObject();

                jsonObjRow.put("StepNo", "Step-" + (iRowIndex + 1));
                jsonObjRow.put("TestCaseName", xl_tc_name);
                jsonObjRow.put("Details", al_xl_details.get(iRowIndex).toString());
                jsonObjRow.put("Status", al_xl_results.get(iRowIndex).toString());
                jsonObjRow.put("ScreenPrints", al_xl_screenshot.get(iRowIndex).toString());

                alJSONObj.add(jsonObjRow);
            }
            mTestLOGData.put(strSheetName, alJSONObj);

            JSONObject jsonObj = new JSONObject();
            jsonObj.putAll(mTestLOGData);

            FileWriter file = new FileWriter(strjsonFilePathToWrite);
            file.write(jsonObj.toJSONString());
            file.flush();
            file.close();
            //System.out.println(jsonObj.toString());

            System.out.println("JSON Test Log File generated");
        } catch (Exception e) {
            System.out.println("Error in creating JSON Test Log File");
            e.printStackTrace();
        }
    }
}

