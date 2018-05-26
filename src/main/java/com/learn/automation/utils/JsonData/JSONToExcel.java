package com.learn.automation.utils.JsonData;



        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;

        import javax.swing.JOptionPane;

        import org.apache.commons.io.FilenameUtils;
        import org.json.simple.JSONArray;
        import org.json.simple.JSONObject;
        import org.json.simple.parser.JSONParser;



public class JSONToExcel {
    public static void main(String args[]) throws IOException{
        try{/*
            System.out.println("Running convertProjJSONFilesToExcel after Batch Execution");
            String strGlobalDataSheet = DataSetup.pathOfProjectHomeDirectory + File.separator + DataSetup.Logininfo;
            convertJSONToExcel(strGlobalDataSheet);*/

            String strTestDataFolderPath = DataSetup.pathOfProjectHomeDirectory + File.separator + "TestDataStore";
            JSONToExcel.convertALLJSONFilesinFolderToExcel (strTestDataFolderPath, 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void convertALLJSONFilesinFolderToExcel (String dirPath, int level) throws IOException {
        try{
            File dir = new File(dirPath);
            File[] firstLevelFiles = dir.listFiles();
            if (firstLevelFiles != null && firstLevelFiles.length > 0) {
                for (File aFile : firstLevelFiles) {
                    if (aFile.isDirectory()) {
                        //System.out.println("[" + aFile.getName() + "]");
                        convertALLJSONFilesinFolderToExcel (aFile.getAbsolutePath(), level + 1);
                    } else if (FilenameUtils.getExtension(aFile.getName()).equals("txt")){
                        convertJSONToExcel (aFile.getPath());
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error in converting JSON to Excel");
        }
    }

    public static void convertJSONToExcel (String strjsonFilePath) throws IOException{
        try{
            System.out.println("Converting JSON File: " +strjsonFilePath);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(strjsonFilePath));
            JSONObject jsonObject = (JSONObject) obj;

            String strExcelTestDataPath = strjsonFilePath.replace(".txt", ".xls");
            boolean bExcelFileExists = false;

            // Create Excel File if it doesn't exist, else update the existing file
            File f = new File(strExcelTestDataPath);
            if(!f.exists()) {
                ExcelPOI.CreateNewExcelFile (strExcelTestDataPath);
            }
            else{
                bExcelFileExists = true;
                System.out.println("Excel file exists");
            }

            ExcelPOI.openExcelConnection(strExcelTestDataPath);

            for (Object key : jsonObject.keySet()){
                // Create Sheet
                String keySheetName = (String) key;
                ExcelPOI.AddNewSheet (keySheetName);
                System.out.println(".. Entering data in Sheet: " +keySheetName);

                JSONArray valTestDatainAllRowsinSheet = (JSONArray) jsonObject.get(keySheetName);
                int iRowIndexToWrite = 0;
                for (Object object : valTestDatainAllRowsinSheet) {
                    JSONObject valTestDatainSingleRowinSheet = (JSONObject) object;

                    // Add column headers to Sheet
                    if (iRowIndexToWrite == 0){
                        if (bExcelFileExists == false){
                            int iColIndexToWrite = 0;
                            for (Object keyColName : valTestDatainSingleRowinSheet.keySet()){
                                ExcelPOI.WriteDataToExcel (keySheetName, iColIndexToWrite, iRowIndexToWrite, keyColName.toString());
                                iColIndexToWrite ++;
                            }
                        }
                        iRowIndexToWrite ++;
                    }

                    //Retrieve the column index for the column headers (Optimizing write operations to excel, so that everytime during write operation the column index need not be figured out
                    ArrayList <Integer> alColIndex = new ArrayList <Integer> ();
                    for (Object keyColName : valTestDatainSingleRowinSheet.keySet()) {
                        alColIndex.add (ExcelPOI.GetColumnIndex(keySheetName, keyColName.toString()));
                    }

                    //Write to the excel file
                    int iColIndex = 0;
                    for (Object keyColName : valTestDatainSingleRowinSheet.keySet()){
                        String valCellData = (String) valTestDatainSingleRowinSheet.get(keyColName);
                        //System.out.println(keySheetName +"= "+ keyColName.toString() +":"+ valCellData);

                        //if value of cell in JSON file is Blank Row, that means there is no row in the excel and data needs to be input from 1st row
                        if (valCellData.equals("Blank Row")){
                            iRowIndexToWrite --;
                            break;
                        }
                        else{
                            if (!valCellData.equals(""))
                                ExcelPOI.WriteDataToExcel(keySheetName, alColIndex.get(iColIndex), iRowIndexToWrite, valCellData);
                        }

                        iColIndex ++;
                    }

                    iRowIndexToWrite ++;
                }
            }

            ExcelPOI.closeExcelConnection();

            System.out.println("JSON to Excel conversion complete");
            //JOptionPane.showMessageDialog(null,"JSON to Excel conversion complete","JSON to Excel Conversion",JOptionPane.OK_OPTION);
        }catch (Exception e){
            //JOptionPane.showMessageDialog(null,"Error in converting JSON to Excel","JSON to Excel Conversion Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Error in converting JSON to Excel");
            e.printStackTrace();
            ExcelPOI.closeExcelConnection();
        }
    }
}

