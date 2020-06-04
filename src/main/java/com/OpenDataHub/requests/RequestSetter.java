package com.OpenDataHub.requests;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/** RequestSetter class, this class acts as the interface between the main and the threads through which the requests will be made */
public class RequestSetter{


    private int lastPage;
    private int pages;
    private int pageSize;
	private int requestedActivities;


    /**@Constructor 
     * default is set with the default size of page from the API, this default is (presumably) optimized for speed of transaction
     */
   
    public RequestSetter() {

        // order of the calls in the instantiation is important
        
        this.pageSize = 10;

        setRequestedActivities();
        setPages();
        setLastPage();

    }
     /**@Constructor */

    public RequestSetter(int pageSize) {
     
        this.pageSize = pageSize;
        
        setRequestedActivities();
        setPages();
        setLastPage();

    }

    /**Retrieves a validated number of activities to be processed */
    public void setRequestedActivities() {

        try {

            this.requestedActivities = Loader.retrieveInput();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**@return int
     * Number of activities to be processed
     */
    
    public int getRequestedActivities() {
		return requestedActivities;
	}

    /**@return void
     * sets how many activities are to be requested in the last page,  by default the number of activities is 10
     * used when the number of activities requested is not round
     */
    public void setLastPage() {
      
            this.lastPage = this.requestedActivities % this.pageSize;
        
    }

	public int getLastPage() {
		return lastPage;
	}

	public int getPages() {
        return pages;
	}

    public void setPages() {
        
        pages = 1;

        if (requestedActivities > 10)
            pages = (int) Math.ceil(this.requestedActivities / (double)this.pageSize);
        
	}
    /** return number of activities per page */
	public int getPageSize() {
		return pageSize;
	}
    /** setter for arbitrary number of activities per page */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


    /**@return LinkedList <FutureTask<StringBuilder>>
     * Threads are instantiated and request placed. Requests are asynchronous. 
     * @see FutureTask
     */
    public LinkedList<FutureTask<StringBuilder>> startThreads() {

        LinkedList<FutureTask<StringBuilder>> multithreadTasks = new LinkedList<>();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        System.out.println("pages to be retrieved... " + pages);

        for (int i = 1; i <= pages; i++) {
            
            Retriever thread = new Retriever();
            thread.setPageNumber(i);

            if (i == pages)
                thread.setPageSize(lastPage);

            multithreadTasks.add(new FutureTask<StringBuilder>(thread));

            executor.execute(multithreadTasks.getLast());
            System.out.println("Starting the thread " + i);
        }
         
        executor.shutdown();

        try {
			executor.awaitTermination(-1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        return multithreadTasks;
    }


    /** Overriden toString method */
	@Override
	public String toString() {
		return "RequestSetter [lastPage=" + lastPage + ", pageSize=" + pageSize + ", pages=" + pages
				+ ", requestedActivities=" + requestedActivities + "]";
	}

}