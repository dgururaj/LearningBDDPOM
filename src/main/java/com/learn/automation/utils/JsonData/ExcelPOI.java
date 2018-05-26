package com.learn.automation.utils.JsonData;


import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExcelPOI {
    //public static int iLogSheetRowCounter=1;
    private static String strTestDataFilePath;
    private static File file;
    private static Workbook wbTestDataExcelWB;
    private static FileInputStream inputStream;
    private static FileOutputStream outputStream;

    static {
        inputStream = null;
        outputStream = null;
    }

    public static void openExcelConnection(String strTestDataFile) throws IOException {
        try {
            strTestDataFilePath = strTestDataFile;

            //Create a object of File class to open xlsx file
            file = new File(strTestDataFilePath);

            //Create an object of FileInputStream class to read excel file
            inputStream = new FileInputStream(file);

            wbTestDataExcelWB = null;
            String strFileExtnsn = FilenameUtils.getExtension(strTestDataFilePath);
            //System.out.println("Excel File: " +ConfigFile.strTestDataFilePath + " Extnsn: " +strFileExtnsn);

            if (strFileExtnsn.equals("xlsx")) {
                wbTestDataExcelWB = new XSSFWorkbook(inputStream);
            } else if (strFileExtnsn.equals("xls")) {
                wbTestDataExcelWB = new HSSFWorkbook(inputStream);
            }
        } catch (Exception e) {
            System.out.println("Error in Opening POI Excel Connection");
            e.printStackTrace();
            closeExcelConnection();
        }
    }

    public static void closeExcelConnection() {
        try {
            //Close input stream
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }

            //close output stream
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in Closing POI Excel Connection");
            e.printStackTrace();
        }
    }

    public static void browseForExcelFile() {
        // Browse for the Excel file

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        fileChooser.setDialogTitle("Select the RTP sheet");
        int userSelection = fileChooser.showDialog(null, "Select Excel");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fExcelFile = fileChooser.getSelectedFile();
            strTestDataFilePath = fExcelFile.getAbsolutePath();
            System.out.println("Excel file: " + fExcelFile.getAbsolutePath());
        }
    }

    public static boolean CreateNewExcelFile(String strExcelWorkbookName) {
        Workbook wbTestDataExcelWB = null;

        try {
            String strFileExtnsn = FilenameUtils.getExtension(strExcelWorkbookName);
            //System.out.println("Excel File: " +ConfigFile.strTestDataFilePath + " Extnsn: " +strFileExtnsn);

            if (strFileExtnsn.equals("xlsx")) {
                wbTestDataExcelWB = new XSSFWorkbook();
            } else if (strFileExtnsn.equals("xls")) {
                wbTestDataExcelWB = new HSSFWorkbook();
            }

            FileOutputStream fileOut;
            fileOut = new FileOutputStream(strExcelWorkbookName);
            wbTestDataExcelWB.write(fileOut);
            fileOut.close();

            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error in creating new Excel");
            closeExcelConnection();
            return false;
        }
    }

    public static int AddNewSheet(String strSheetName) throws Exception {
        int iIndex = 0;
        try {
            //Read excel sheet by sheet name
            iIndex = wbTestDataExcelWB.getSheetIndex(strSheetName);
            if (iIndex == -1)
                wbTestDataExcelWB.createSheet(strSheetName);
            else
                System.out.print("Sheet exists");

            //Create an object of FileOutputStream class to create write data in excel file
            outputStream = new FileOutputStream(file);

            //write data in the excel file
            wbTestDataExcelWB.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in adding new Sheet to Excel");
            closeExcelConnection();
            //JOptionPane.showMessageDialog(null,"Please close the excel file first","File Open Error",JOptionPane.ERROR_MESSAGE);
        }
        return iIndex;
    }

    public static int GetRowCount(String strSheetName) {
        int iRowCount = 0;
        try {
            //Read sheet inside the workbook by its name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);
            iRowCount = shtTestDataSheet.getLastRowNum();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting Row Count");
            closeExcelConnection();
        }

        return iRowCount;
    }

    public static int GetColumnCountForaRow(String strSheetName, int iRowIndex) {
        int iColCount = 0;
        try {
            //Read sheet inside the workbook by its name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);
            Row row = shtTestDataSheet.getRow(iRowIndex);
            iColCount = row.getLastCellNum();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting Column Count for Row");
            closeExcelConnection();
        }

        return iColCount;
    }

    public static ArrayList<String> GetAllRowsfromColumn(String strSheetName, String strColNameToRead) {
        ArrayList<String> alAllRows = new ArrayList<String>();
        try {
            //Read sheet inside the workbook by its name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            int i, j;
            boolean bColNameFound = false;
            Row row = shtTestDataSheet.getRow(0);
            for (j = 0; j <= row.getLastCellNum(); j++) {
                if (row.getCell(j).getStringCellValue().equals(strColNameToRead)) {
                    bColNameFound = true;
                    break;
                }
            }

            if (bColNameFound) {
                //System.out.println(shtTestDataSheet.getLastRowNum());
                for (i = 1; i <= shtTestDataSheet.getLastRowNum(); i++) {
                    row = shtTestDataSheet.getRow(i);

                    try {
                        if (Cell.CELL_TYPE_STRING == row.getCell(j).getCellType()) {
                            String strCellValue = row.getCell(j).getStringCellValue().trim();
                            if (!strCellValue.equals("")) {
                                //System.out.println(row.getCell(j).getStringCellValue());
                                alAllRows.add(row.getCell(j).getStringCellValue());
                            }
                        }
                    } catch (Exception e2) {
                        System.out.println("In Catch for Row No: " + i + ".. Possible Reason: Value in Cell is Not CELL_TYPE_STRING");
                    }
                }
            } else {
                //System.out.println("Column Name not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting all rows from Column");
            closeExcelConnection();
        }

        return alAllRows;
    }

    public static ArrayList<String> GetAllColumnsfromRow(String strSheetName, int iRowIndex) {
        ArrayList<String> alAllColumns = new ArrayList<String>();
        try {
            //Read sheet inside the workbook by its name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            int iIndexofRowToRead;
            boolean bRowNameFound = false;

            //Find index of RowName
            Row row;
            int j;
            row = shtTestDataSheet.getRow(iRowIndex);

            int iColCnt = 0;
            try {
                iColCnt = shtTestDataSheet.getRow(0).getLastCellNum();
            } catch (Exception e) {
                iColCnt = 0;
            }

            for (j = 0; j < iColCnt; j++) {
                String strCellValueRetrieved = "";
                try {
                    if (Cell.CELL_TYPE_BLANK == row.getCell(j).getCellType())
                        strCellValueRetrieved = "";
                    else if (Cell.CELL_TYPE_NUMERIC == row.getCell(j).getCellType())
                        strCellValueRetrieved = Integer.toString((int) row.getCell(j).getNumericCellValue());
                    else if (Cell.CELL_TYPE_STRING == row.getCell(j).getCellType())
                        strCellValueRetrieved = row.getCell(j).getStringCellValue().trim();
                } catch (Exception e2) {
                    strCellValueRetrieved = "";
                }
                if (iRowIndex == 0) { //if it's col header thn don't take into account empty cells, otherwise include empty cells (for rows other thn col header since test data in rows can be blank data but col header shuldn't be blank)
                    if (!strCellValueRetrieved.equals(""))
                        alAllColumns.add(strCellValueRetrieved);
                } else {
                    alAllColumns.add(strCellValueRetrieved);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting all Columns from Row");
            closeExcelConnection();
            //JOptionPane.showMessageDialog(null,"Error in Excel operation","Excel Error",JOptionPane.ERROR_MESSAGE);
        }

        return alAllColumns;
    }

    public static ArrayList<String> GetSheetsfromWorkbook() {
        ArrayList<String> alSheetNames = new ArrayList<String>();
        try {
            for (int i = 0; i < wbTestDataExcelWB.getNumberOfSheets(); i++) {
                alSheetNames.add(wbTestDataExcelWB.getSheetAt(i).getSheetName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting Sheets from Workbook");
            closeExcelConnection();
        }

        return alSheetNames;
    }

    public static void WriteDataToExcel(String strSheetName, int iColIndexToWrite, int iRowIndexToWrite, String strValueToWrite) throws Exception {
        try {
            //Read excel sheet by sheet name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            Row row = shtTestDataSheet.getRow(iRowIndexToWrite);
            if (row == null)
                row = shtTestDataSheet.createRow(iRowIndexToWrite);

            Cell cell = row.createCell(iColIndexToWrite);

            if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(strValueToWrite);
            }

            //Create an object of FileOutputStream class to create write data in excel file
            outputStream = new FileOutputStream(file);

            //write data in the excel file
            wbTestDataExcelWB.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in writing to Excel");
            closeExcelConnection();
            //JOptionPane.showMessageDialog(null,"Please close the excel file first","File Open Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int GetColumnIndex(String strSheetName, String strColNameToSearch) throws Exception {
        int iColIndex = -1;
        try {
            //Read excel sheet by sheet name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            int i, j;
            boolean bColNameFound = false;

            Row row = shtTestDataSheet.getRow(0);
            for (j = 0; j <= row.getLastCellNum(); j++) {
                if (row.getCell(j).getStringCellValue().equals(strColNameToSearch)) {
                    bColNameFound = true;
                    break;
                }
            }

            if (!bColNameFound) {
                System.out.println("Column: " + strColNameToSearch + " not found");
            } else
                iColIndex = j;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getting Column Index");
            closeExcelConnection();
            //JOptionPane.showMessageDialog(null,"Please close the excel file first","File Open Error",JOptionPane.ERROR_MESSAGE);
        }

        return iColIndex;
    }

    public static void WriteDataToExcel(String strSheetName, String strColNameToWrite, String strRecToSearch, String strColToSearch, String strValueToWrite) throws Exception {
        try {
            //Read excel sheet by sheet name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            int iIndexofRowToWrite, iIndexofColToWrite, iIndexofColToSearch;
            boolean bRowNameFound = false, bColNameToWriteFound = false, bColNameToSearchFound = false;

            //Find index of RowName
            Row row;

            //Find index of ColumnName To Search
            row = shtTestDataSheet.getRow(0);
            for (iIndexofColToSearch = 0; iIndexofColToSearch <= row.getLastCellNum(); iIndexofColToSearch++) {
                if (row.getCell(iIndexofColToSearch).getStringCellValue().equals(strColToSearch)) {
                    bColNameToSearchFound = true;
                    break;
                }
            }

            //Find index of ColumnName To Write
            row = shtTestDataSheet.getRow(0);
            for (iIndexofColToWrite = 0; iIndexofColToWrite <= row.getLastCellNum(); iIndexofColToWrite++) {
                if (row.getCell(iIndexofColToWrite).getStringCellValue().equals(strColNameToWrite)) {
                    bColNameToWriteFound = true;
                    break;
                }
            }

            if (bColNameToSearchFound && bColNameToWriteFound) {
                for (iIndexofRowToWrite = 0; iIndexofRowToWrite <= shtTestDataSheet.getLastRowNum(); iIndexofRowToWrite++) {
                    row = shtTestDataSheet.getRow(iIndexofRowToWrite);
                    String strCellValue = row.getCell(iIndexofColToSearch).getStringCellValue();

                    //System.out.println( strCellValue + " = " + strRecordinColumnX);
                    if (strCellValue.equals(strRecToSearch)) {
                        bRowNameFound = true;
                        Cell cell = row.createCell(iIndexofColToWrite);
                        cell.setCellValue(strValueToWrite);
                    }
                }

                if (!bRowNameFound) {
                    System.out.println("Record not found in Column: " + strRecToSearch);
                    //JOptionPane.showMessageDialog(null,"Record not found in Column","Excel Error",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Column Name not found");
                //JOptionPane.showMessageDialog(null,"Column Name not found","Excel Error",JOptionPane.ERROR_MESSAGE);
            }

            //Create an object of FileOutputStream class to create write data in excel file
            outputStream = new FileOutputStream(file);

            //write data in the excel file
            wbTestDataExcelWB.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in writing to Excel");
            //JOptionPane.showMessageDialog(null,"Error in Excel operation","Excel Error",JOptionPane.ERROR_MESSAGE);
            closeExcelConnection();
        }
    }

    public static void WriteDataToExcel(String strSheetName, String strColNameToWrite, int iRowIndexToWrite, String strValueToWrite) throws Exception {
        try {
            //Read excel sheet by sheet name
            Sheet shtTestDataSheet = wbTestDataExcelWB.getSheet(strSheetName);

            int i, j;
            boolean bColNameFound = false;

            Row row = shtTestDataSheet.getRow(0);
            for (j = 0; j <= row.getLastCellNum(); j++) {
                if (row.getCell(j).getStringCellValue().equals(strColNameToWrite)) {
                    bColNameFound = true;
                    break;
                }
            }

            if (bColNameFound) {
                row = shtTestDataSheet.getRow(iRowIndexToWrite);
                //System.out.println("j: " +j);

                if (row == null)
                    row = shtTestDataSheet.createRow(iRowIndexToWrite);

                Cell cell = row.createCell(j);

                if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(strValueToWrite);
                }
            } else {
                System.out.println("Column Name not found");
                //JOptionPane.showMessageDialog(null,"Column Name not found","Excel Error",JOptionPane.ERROR_MESSAGE);
            }

            //Create an object of FileOutputStream class to create write data in excel file
            outputStream = new FileOutputStream(file);

            //write data in the excel file
            wbTestDataExcelWB.write(outputStream);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in writing to Excel");
            closeExcelConnection();
            //JOptionPane.showMessageDialog(null,"Please close the excel file first","File Open Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void DeleteRow(String strSheetName) throws Exception {
        try {
            Sheet sheet = wbTestDataExcelWB.getSheet(strSheetName);
            /*Iterator<Row> rowIte =  sheet.iterator();
	    	while(rowIte.hasNext()){
	    	    rowIte.next();
	    	    rowIte.remove();
	    	}*/

            for (int index = sheet.getLastRowNum(); index >= sheet.getFirstRowNum() + 1; index--) {
                sheet.removeRow(sheet.getRow(index));
            }

            //Create an object of FileOutputStream class to create write data in excel file
            outputStream = new FileOutputStream(file);

            //write data in the excel file
            wbTestDataExcelWB.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in deleting rows in Sheet");
            closeExcelConnection();
        }
    }
}


