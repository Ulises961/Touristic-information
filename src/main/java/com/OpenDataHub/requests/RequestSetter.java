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
public class RequestSetter {

    private String url;
    private int elementsPerPage;
    private int elementsInLastPage;
    private int activityType;
    private Integer seed;
    private int totalPages;
    private int requestedActivities;
    private int pageNumber;
  
    private static final Logger logger = LogManager.getLogger();

/** Constructor
 * @param url, URL address of the API
 * @param elementsPerPage, how many activities per page will be requested by default 10
 * @param activityType, default 1023
 * @param seed, random sorter of elements, null = disabled
 * @param requestedActivities, how many elements need to be fetched from the API
 */
    public RequestSetter(String url, int elementsPerPage, int activityType,
            Integer seed, int requestedActivities) {

        this.url = url;
        this.elementsPerPage = elementsPerPage;
        this.activityType = activityType;
        this.seed = seed;
        this.requestedActivities = requestedActivities;
     
    }
        
/** setQuery()
 * 
 * This method prepares the String to be used when settig the request
 *  @return String
 */
    public String setQuery() {

        String requestString = "%s?pagenumber=%d&pagesize=%d&activitytype=%d&seed=%d";
        String request = "";

        request= String.format(requestString, this.url, this.pageNumber, this.elementsPerPage, this.activityType, this.seed);
        
      
        
        return request;
    }
 
    /** calculateElementsOfLastPage()
     * The remaining activities that do not complete a whole page, this method fits the last request
     * to the precise number of remaining activities 
     * @return int
     */
    public int calculateElementsOfLastPage() {

        this.elementsInLastPage = this.requestedActivities % this.elementsPerPage;
        if (elementsInLastPage == 0)
            elementsInLastPage = elementsPerPage;

        return elementsInLastPage;
    }
    
    /** calculateTotalPages()
     * pages are rounded up and so that the remaining elements are fit into the last page using @link calculateElementsOfLastPage
     * @return int
     */

    public int calculateTotalPages() {

        this.totalPages = 1;

        if (requestedActivities > elementsPerPage)
            this.totalPages = (int) Math.ceil(this.requestedActivities / (double) this.elementsPerPage);
            
        return totalPages;
        
    }
    
    /**
     * gerElementsPerPage()
     * @return int */

	public int getElementsPerPage() {
		return elementsPerPage;
	}

    /** setPageSize() 
     * sets the number of activities per page to a arbitrary number
     * @param elementsPerPage
    */
	public void setPageSize(int elementsPerPage) {
		this.elementsPerPage = elementsPerPage;
	}

        /** @return int */

	public int getPageNumber() {
		return pageNumber;
	}

    /**setPageNumber
     * @param pageNumber
     * 
     */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


   /** Starts the request threads using a new Retriever object per request
    * and place each thread into a list that contains all the requests made.

    * @return LinkedList<FutureTask<StringBuilder>>()
    */
    public LinkedList<FutureTask<StringBuilder>> startThreads()  {

        LinkedList<FutureTask<StringBuilder>> multithreadTasks = new LinkedList<>();

        ExecutorService executor = Executors.newWorkStealingPool(25);

        calculateTotalPages();
     

        calculateElementsOfLastPage();

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            
            boolean lastPageIsNotEqualToTheFirstOne = currentPage > 1 && currentPage == totalPages;

            setPageNumber(currentPage);
            
            if (lastPageIsNotEqualToTheFirstOne)
                setPageSize(elementsInLastPage);
            
            String url = setQuery();
           
            Callable<StringBuilder> task = new Retriever(url);

            logger.debug(task.toString());

            multithreadTasks.add(new FutureTask<StringBuilder>(task));

            logger.debug("Executing thread "+currentPage);
            
            executor.execute(multithreadTasks.getLast());

        }

            executor.shutdown();

       
         
        return multithreadTasks;
    }
    /** @return String */

	@Override
	public String toString() {
		return "RequestSetter [activityType=" + activityType + ", elementsInLastPage=" + elementsInLastPage + ", pageNumber=" + pageNumber
				+ ", elementsPerPage=" + elementsPerPage + ", requestedActivities=" + requestedActivities + ", seed=" + seed
				+ ", totalPages=" + totalPages + ", url=" + url + "]";
	}


}
