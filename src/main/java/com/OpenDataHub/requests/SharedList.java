/**
 * The class contains a list of FutureTasks for storing the responses retrieved from the Api
 * 
 * @author Sosa Ulises
 */
package com.OpenDataHub.requests;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedList {

  public static List<FutureTask<StringBuilder>> responsesList = new LinkedList<>();

  private static boolean finished = false;

  private static Logger logger = LogManager.getLogger();
  
  /***
   * Add a new list and set the number of iterations
   * @param newResponsesList new list for getting "done" responses
   */
  public static void addResponsesList(List<FutureTask<StringBuilder>> newResponsesList) {
    responsesList = newResponsesList;
 
  }

  /**
   * retrieve a String from the FutureTasks list. The method iterate continuosly through the list, until it 
   * founds a "done" object
   * 
   * @return return String response; "-1" if no more elements in the list to wait for 
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static String getNewElement() throws InterruptedException, ExecutionException {
    String newElement = "";

    //sinchronized for thread save access
    synchronized (responsesList) {
      //iterate from inital element of the list
      int index = 0;
      
      while (!finished) {
      
        boolean responseIsReady = responsesList.get(i).isDone();

        if (responsesList.size() == 0) 
          finished = true;
        
<<<<<<< HEAD
        else if (responseIsReady) {
      
          FutureTask<StringBuilder> response = responsesList.remove(index);

          newElement = response.get().toString();
         

        return newElement;
=======
        else if (responsesList.get(index).isDone()) {
          newElement = responsesList.remove(index).get().toString(); 
          return newElement;
>>>>>>> c355b85efca82342026fd61fe3af54605f54b72f
        } 
        else
          index = (index + 1) % (responsesList.size());
      
      }

      //if responsesList is empty -> no need to wait more for new elements to be done
      return "-1";
    }
  }
}