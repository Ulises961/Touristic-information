package com.OpenDataHub.fileio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileContentRetriever {

    public static String ReadFileContents(String path)
    {
        Logger l = LogManager.getRootLogger();
        String fileContent = "";
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(new File(path)));
            String newLine;

            while ((newLine = reader.readLine()) != null)
                fileContent += newLine;
            reader.close();
        }catch (IOException e)
        {
            l.error("Error reading a file: " + e.toString());
        }
        return fileContent;
    }
}
