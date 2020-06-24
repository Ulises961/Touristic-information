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

    * @return LinkedList<FutureTask<StringBuilder>>()
    */
    public static LinkedList<FutureTask<StringBuilder>> startThreadsMakingRequests()  {

        LinkedList<FutureTask<StringBuilder>> multithreadTasks = new LinkedList<>();
        ExecutorService executor = Executors.newWorkStealingPool(25);

        for (; RequestUtil.PAGE_NUMBER < RequestUtil.TOTAL_PAGES; RequestUtil.PAGE_NUMBER++) {
            
            // boolean isLastPage = RequestUtil.PAGE_NUMBER > 0 && RequestUtil.PAGE_NUMBER == RequestUtil.TOTAL_PAGES;
            String query = "";

            boolean isLastPage = RequestUtil.PAGE_NUMBER == (RequestUtil.TOTAL_PAGES - 1);

            if(isLastPage) 
                query = RequestUtil.setLastPageQuery();
            else
                query = RequestUtil.setDefaultQuesry();
            
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
