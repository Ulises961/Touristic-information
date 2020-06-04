package com.OpenDataHub.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor implements IntRetriever{


    String filePath;
    public FileProcessor(String path)
    {
        filePath = path;
    }


    @Override
    public Integer getIntegerFromFile() {
        return parse(readFirstLine());
    }

    private String readFirstLine() {

        FileReader fReader;
        try {
            fReader = new FileReader(this.filePath);
        }
        catch (IOException e)
        {
            return "-1";
        }
        BufferedReader bReader = new BufferedReader(fReader);
        String line;
        try {
            line = bReader.readLine();
            bReader.close();
        }
        catch (IOException e) {
            line = "-1";
        }
        return line;
    }

    private Integer parse(String num)
    {
        int number;
        try {
            number = Integer.parseInt(num);
        }
        catch (NumberFormatException e)
        {
            number = -1;
        }
        return number;

    }

}
