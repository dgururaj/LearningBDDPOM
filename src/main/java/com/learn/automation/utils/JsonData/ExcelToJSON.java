package com.learn.automation.utils.JsonData;


import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;

public class ExcelToJSON {
    public static void convertALLExcelFilesinFolderToJSON(String dirPath, int level) throws IOException {
        try {
            File dir = new File(dirPath);
            File[] firstLevelFiles = dir.listFiles();
            if (firstLevelFiles != null && firstLevelFiles.length > 0) {
                for (File aFile : firstLevelFiles) {
                    if (aFile.isDirectory()) {
                        //System.out.println("[" + aFile.getName() + "]");
                        convertALLExcelFilesinFolderToJSON(aFile.getAbsolutePath(), level + 1);
                    } else if (FilenameUtils.getExtension(aFile.getName()).equals("xls") || FilenameUtils.getExtension(aFile.getName()).equals("xlsx")) {
                        convertExcelToJSON(aFile.getPath());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in converting Excel to JSON");
        }
    }

    public static void convertExcelToJSON(String strExcelTestDataPath) throws IOException {
        try {
            System.out.println("Converting Excel File: " + strExcelTestDataPath);

            ExcelPOI.openExcelConnection(strExcelTestDataPath);
            String strJSONTestDataPath = strExcelTestDataPath.replace(".xls", ".txt");
            strJSONTestDataPath = strJSONTestDataPath.replace(".txtx", ".txt");

            Map<String, ArrayList<JSONObject>> mExcelTestData = new HashMap<>();
            ArrayList<String> alSheetNames = ExcelPOI.GetSheetsfromWorkbook();

            if (alSheetNames.size() > 0) {
                System.out.print("Sheet: ");
                for (String strSheetName : alSheetNames) {
                    System.out.print(strSheetName + " ");

                    ArrayList<String> alColNames = ExcelPOI.GetAllColumnsfromRow(strSheetName, 0);
                    /*System.out.print("Column Names for the sheet: ");
					for ( String strColName : alColNames )
				    {
						System.out.print(strColName + " ");
				    }
					System.out.println("");*/

                    int iRowCount = ExcelPOI.GetRowCount(strSheetName);

                    ArrayList<JSONObject> alJSONObj = new ArrayList<>();
                    for (int iRowIndex = 1; iRowIndex <= iRowCount; iRowIndex++) {
                        JSONObject jsonObjRow = new JSONObject();
                        ArrayList<String> alRowData = ExcelPOI.GetAllColumnsfromRow(strSheetName, iRowIndex);

                        if (alRowData.size() > 0) {
                            for (int iColIndex = 0; iColIndex < alColNames.size(); iColIndex++) {
                                String strCellValue = "";
                                try {
                                    strCellValue = alRowData.get(iColIndex);
                                } catch (IndexOutOfBoundsException i) { // No data in the Row cell for the specified Column in Sheet
                                    strCellValue = "";
                                }

                                //Add each cell data for a Row into the JSON object (ROW)
                                jsonObjRow.put(alColNames.get(iColIndex), strCellValue);
                            }

                            //Add the JSON object row to the JSON Array (SHEET)
                            alJSONObj.add(jsonObjRow);
                        }
                    }

                    // if sheet has no rows, no JSON array of object will be created, hence the column names will not be known since columns are the keys
                    if (iRowCount == 0) {
                        JSONObject jsonObjRow = new JSONObject();
                        for (int iColIndex = 0; iColIndex < alColNames.size(); iColIndex++) {
                            jsonObjRow.put(alColNames.get(iColIndex), "Blank Row");
                        }
                        alJSONObj.add(jsonObjRow);
                    }

                    mExcelTestData.put(strSheetName, alJSONObj);
                }
                System.out.println("");

                JSONObject jsonObj = new JSONObject();
                jsonObj.putAll(mExcelTestData);

                FileWriter file = new FileWriter(strJSONTestDataPath);
                file.write(jsonObj.toJSONString());
                file.flush();
                file.close();
                //System.out.println(jsonObj.toString());

                ExcelPOI.closeExcelConnection();

                System.out.println("Excel to JSON conversion complete");
                //JOptionPane.showMessageDialog(null,"Excel to JSON conversion complete","Excel to JSON Conversion",JOptionPane.OK_OPTION);
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null,"Error in converting Excel to JSON","Excel to JSON Conversion Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Error in converting Excel to JSON");
            e.printStackTrace();
            ExcelPOI.closeExcelConnection();
        }
    }
}

