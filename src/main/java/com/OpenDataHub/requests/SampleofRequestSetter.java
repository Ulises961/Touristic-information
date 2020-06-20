package com.OpenDataHub.requests;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Sample class for requests
 * Seed for a random order sorting, with a seed 1, the least loss of data is achieved with 23 activities per page
 * The list returned can be accessed by the get() method of the Future class. This method is blocking so some
 * sort of non blocking implementation must be deviced in order to search in the list for responses ready.
 * The .isDone method lets one know if an element in the list is ready.
 */
public class Requests {
  private static final Logger logger = LogManager.getLogger();

 // public static void main(String[] args) throws InterruptedException, ExecutionException {
    
    public static void sampleUseOfRequestSetter() throws InterruptedException, ExecutionException { //comment this header and uncomment the upper line to test
    
    
    logger.info("Starting app");

    String url = "http://tourism.opendatahub.bz.it/api/Activity";

    int activitiesPerPage = 23;
    int activityType = 1023;
    Integer seed = 1; 
    int requestedActivities = 60;

    RequestSetter r = new RequestSetter(url, activitiesPerPage, activityType, seed, requestedActivities);
    List<FutureTask<StringBuilder>> list = r.startThreads();
    
    int i = 0;
   
    
    while (list.size() > 0) {
      boolean responseIsReady = list.get(i).isDone();
      
      if (responseIsReady) {
     
        
        FutureTask<StringBuilder> response = list.remove(i);

        String responseContent = response.get().toString();
       
        System.out.printf( "I am the response number %d being retrieved\n", i);
        logger.debug("element number" + i);
    
        i = 0; // the scanning of the list must start always from 0 to ensure an OutOfBounds error does not occur

      } else

        i = (i + 1) % (list.size());
 
    }

  }
  
}
