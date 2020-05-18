package com.OpenDataHub.requests;

import java.io.*;

/**
 * This class interacts with the input *.txt file reading validating and
 * returning the input, it is a support class with static methods.
 */

public class Loaderpt2 {

  /**
   * retrieveInput reads the input using a BufferedReader, and returns it as a
   * String
   * 
   * @return
   * @throws IOException
   */
  public static int retrieveInput(String file_path) throws IOException, NumberFormatException {
        String rawInput = "";
        //"./src/main/resources/requests.txt"
        
        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        rawInput = reader.readLine();
        //if the method works fine, parsed... otherwised throws and exception
        int input = validateInput(rawInput);
        reader.close();

        return input;
    }
    
    /**
     * 
     * @return true if the String returned by retrieveInput() can be parsed correctly
     * assigning the value parsed to the global variable input. If there is an error parsing it 
     * prints on the console an error message
     * @throws NumberFormatException
     */

     //method return an integer, otherwise return null value()
    public static int validateInput(String rawInput) throws NumberFormatException{
      int value = Integer.parseInt(rawInput);
      
      if(value <= 0)
        throw new NumberFormatException("Number smaller or equal 0");
      
        return value;
    }

}

/* when calling function parseInt -> if file not found throws an exeption... ok!
*  isValid even not used!
*  retrieved by Loader.getInput()... let it returning the value
*  before return it manage in validate input every possible exception
*  question ? is it better to have one method that manage all the problems, or many different?
*  different methods for handling different exceptions... file not found, NumberFormatException (value or null) 
*  unique clean method that works with this two exceptions, that's it
*/