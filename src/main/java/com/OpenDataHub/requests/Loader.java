package com.OpenDataHub.requests;

import java.io.*;

/**
 * This class interacts with the input *.txt file reading validating and returning the input,
 * it is a support class with static methods.
 */public class Loader {
    

   
    /**
     * retrieveInput reads the input using a BufferedReader, and returns it as a String
     * @return
     * @throws IOException
     */
    public static int retrieveInput() throws IOException {

        String rawOutput = "";
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/requests.txt"));

        rawOutput = reader.readLine();
        int output = validateInput(rawOutput);
        
        
        reader.close();
        
        return output;
    }
     
    /**
     * 
     * @return boolean
     * True if the String returned by retrieveInput() can be parsed correctly
     * If there is an error parsing stops excecution.
     * @throws NumberFormatException
     */
    public static int validateInput(String rawOutput) throws NumberFormatException {
        
       int  parsed = Integer.parseInt(rawOutput);
        if (parsed < 0) {
            throw new NumberFormatException("Error validating input, number smaller than 0");
      
        }
        return parsed;
    }

  
}