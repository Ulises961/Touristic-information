package com.OpenDataHub.requests;

import java.io.*;
import java.io.IOException;

/**
 * This class interacts with the input *.txt file reading validating and returning the input,
 * it is a support class with static methods.
 */

public class Loader {
    
	private  static int input;

   
    /**
     * retrieveInput reads the input using a BufferedReader, and returns it as a String
     * @return
     */
    public static String retrieveInput() {

        String rawInput = "";

        try (BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/requests.txt"))) {

            rawInput = reader.readLine();

        } catch (IOException e) {

            System.out.println("File not found");
        }
        return rawInput;
    }
    
    /**
     * 
     * @return true if the String returned by retrieveInput() can be parsed correctly
     * assigning the value parsed to the global variable input. If there is an error parsing it 
     * prints on the console an error message
     */
    public static boolean validateInput() {
        boolean isValid = false;
        
        if (parseIt() > 0) {

            isValid = true;
        }
        else
            System.out.println("Invalid input");
        return isValid;
    }

   

    private static int parseIt() {
        int parsed = 0;
        try {
            parsed = Integer.parseInt(retrieveInput());

        } catch (Exception e) {
            //TODO: handle exception
           
        }
        return parsed;
    }
    

     /**
     * getInput() 
     * @return the global value input set with validateInput()
     */
    public static int getInput() {

        return input;

    }
  
}