package com.learn.automation.utils.JsonData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileOperations {
    //public static File file;

    public static void createFile(String strFileName) throws IOException {
        try {
            //System.out.println("Log File: " +strFileName);
            File file = new File(strFileName);
            if (file.exists()) {
                file.delete();
            }

            // creates the file
            file.createNewFile();
            System.out.println("Log File: " + strFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in creating Log File");
        }
    }

    public static void writeToLog(String strFileName, String strContentToFile, boolean... bNewLine) throws IOException {
        try {
            File file = new File(strFileName);

            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file, true); //  (file, "true");

            // Writes the content to the file
            writer.append(strContentToFile);

            //If optional parameter not included, then by default new line is added.
            //If optional parameter included, then finding out if newline optional parameter argument is true or false
            boolean bAddNewLine = true;
            if (bNewLine.length > 0) {
                bAddNewLine = bNewLine[0];
            }

            if (bAddNewLine == true) {
                writer.append(System.lineSeparator());
            } else {
                //Don't Add a new line
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in writing to Log File");
        }
    }

    public static ArrayList<String> readFromLog(String strFileName) {
        try {
            FileReader reader = new FileReader(strFileName);
            BufferedReader br = new BufferedReader(reader);
            ArrayList<String> alLogList = new ArrayList<String>();

            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
                alLogList.add(s);
            }

            reader.close();
            return alLogList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in reading form Log File");
            return null;
        }
    }
}

