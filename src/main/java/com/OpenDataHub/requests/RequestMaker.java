package com.OpenDataHub.requests;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**Request Setter Class
 * this class prepares the query strings and invokes the methods to make the requests to the API 
 * @author Ulises Sosa 
 */
public class RequestMaker {
  
    private static final Logger logger = LogManager.getLogger();

   /** Starts the request threads using a new Retriever object per request
    * and place each thread into a list that contains all the requests made.
    The method will retrieve a number of activities which is >= then the numeber inserted from the user (a multiple of {@link RequestUtil.ELEMENT_PER_PAGE})
    * @return LinkedList<FutureTask<StringBuilder>>()
    */
    public static LinkedList<FutureTask<StringBuilder>> startThreadsMakingRequests()  {

        LinkedList<FutureTask<StringBuilder>> multithreadTasks = new LinkedList<>();
        ExecutorService executor = Executors.newWorkStealingPool(50);

        // <= in the for-loop conditions because TOTAL_PAGES starts from 1
        //continue to increment PAGE_NUMBER for retrieving new set of activities from Api
        for (; RequestUtil.PAGE_NUMBER <= RequestUtil.TOTAL_PAGES; RequestUtil.PAGE_NUMBER++) {
            
            String query = RequestUtil.setDefaultQuery();

            //instantiate a new task for retrieve new request
            Callable<StringBuilder> task = new Retriever(query);

            multithreadTasks.add(new FutureTask<StringBuilder>(task));
            //execute the request
            executor.execute(multithreadTasks.getLast());
        }
        executor.shutdown();
        return multithreadTasks;
    }

}
