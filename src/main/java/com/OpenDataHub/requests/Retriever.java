package com.OpenDataHub.requests;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Retriever Class this class is in charge of making the requests to the API 
 * @author Ulises Sosa
 * 
*/

public class Retriever implements Callable<StringBuilder> {
    


    private static final Logger logger = LogManager.getLogger();
    private  String url;
    
    /**Contructor 
     * @param url to which the requests will be made */
    public Retriever(String url) {

        this.url = url;       

    }
    
    public Retriever() {
    }
    

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     *  Creates a client, invokes setQueryParameters to build the URI
     * Asynchrounous requests, not threadsafe
     * @throws ExecutionException
     * 
     * @return StringBuilder
     * @throws IOException thrown if the request is not successful
     * @throws InterruptedException thrown if request is interrupted
     * @throws ExecutionException thrown if Excecution if the task was aborted due to an exception from the Server
     */
    public StringBuilder makeRequest() throws IOException, InterruptedException, ExecutionException {

            URI uri;
            StringBuilder responseBody = new StringBuilder();
            uri = URI.create(this.url);
    
            CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    
            try{
                httpclient.start();
                HttpGet request = new HttpGet(uri);
             
                Future<HttpResponse> future = httpclient.execute(request, null);
                
                HttpResponse response= future.get();
             
                responseBody.append(new BasicResponseHandler().handleResponse(response));

                
                return responseBody;
               
    
            } finally {
                
                httpclient.close();
                    
              
            
         
            }
           
        }
       
        /**call 
         * @return Stringbuilder
         */
    public StringBuilder call()  {
        StringBuilder bodyResponse = new StringBuilder();
        
        try {
        
        bodyResponse = makeRequest();

        } catch ( IOException e) {
            System.out.println("Error while retrieving the data.");
            logger.error("Error while retrieving data", e.getCause());
            e.getCause();

        } catch (InterruptedException | ExecutionException e) {
            e.getLocalizedMessage();
        }
        
        
        return bodyResponse;
    }
    /** @return String */
	@Override
	public String toString() {
		return "Retriever [url=" + url + "]";
	}
}