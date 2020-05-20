package com.OpenDataHub.requests;

import java.io.*;

/**
 * This class interacts with the input *.txt file reading validating and returning the input,
 * it is a support class with static methods.
 */

public class Loader {
    

   
    /**
     * retrieveInput reads the input using a BufferedReader, and returns it as a String
     * @return
     * @throws IOException
     */
    public static int retrieveInput() throws IOException, NumberFormatException {

        String rawInput = "";
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/requests.txt"));

        rawInput = reader.readLine();
        int output = validateInput(rawInput);
        
        
        reader.close();
        
        return output;
    }
    
    /**
     * 
     * @return true if the String returned by retrieveInput() can be parsed correctly
     * assigning the value parsed to the global variable input. If there is an error parsing it 
     * prints on the console an error message
     */
    public static int validateInput(String rawInput) throws NumberFormatException {
        
       int  parsed = Integer.parseInt(rawInput);
        if (parsed < 0) {
            throw new NumberFormatException("Error validating input, number smaller than 0");
      
        }
        return parsed;
    }

  
}