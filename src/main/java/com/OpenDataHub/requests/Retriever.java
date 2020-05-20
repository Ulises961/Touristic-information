package com.OpenDataHub.requests;


import java.io.*;
import java.net.*;


public class Retriever {


  private  String url;
  private  int pageSize;
  private  int pageNumber;
  private String requestString;
  private int activitytype;
    
    public Retriever() {
        this.url= "http://tourism.opendatahub.bz.it/api/Activity";
        this.pageSize = 0;
        this.activitytype = 1023;
        this.pageNumber = 1;
        this.requestString = "%s?pagenumber=%d&pagesize=%d&activitytype=%d";

    }

    /** 
    @param sets the query string to use in makeRequest(); 
     * @throws IOException
     * @throws NumberFormatException
    */
    private String setQueryParamenters() throws NumberFormatException, IOException {


        String request = "";
       
        

        request= String.format(this.requestString, this.url, this.pageNumber, this.pageSize, this.activitytype);
      
        return request;

    }
    
    /**
     *  Creates a client, invokes setQueryParameters to build the URI
     * and finally writes the result to a Json.json file on the pwd
     * 
     *  @makeRequest
     */
    public String makeRequest() throws IOException, InterruptedException, NumberFormatException {
        
        URL url = new URL(setQueryParamenters());
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        int status = connection.getResponseCode();
   
    
        String line;
        BufferedReader reader;
        String requestBody = "";
      
        if (status > 299)
        
        reader =new BufferedReader(new InputStreamReader(connection.getErrorStream()));
    else {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       
        while ((line = reader.readLine()) != null) {
            requestBody += line;
            
        }
 
        
       
    }

        return requestBody;
    }
    
/**
 * @param activitytype the activitytype to set
 */
public void setActivitytype(int activitytype) {
    this.activitytype = activitytype;
}
/**
 * @param pageNumber the pageNumber to set
 */
public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
}
/**
 * @param pageSize the pageSize to set
 */
public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
}
/**
 * @param requestString the requestString to set
 */
public void setRequestString(String requestString) {
    this.requestString = requestString;
}
/**
 * @param url the url to set
 */
public void setUrl(String url) {
    this.url = url;
}

/**
 * @return the activitytype
 */
public int getActivitytype() {
    return activitytype;
}
/**
 * @return the pageNumber
 */
public int getPageNumber() {
    return pageNumber;
}
/**
 * @return the pageSize
 */
public int getPageSize() {
    return pageSize;
}
/**
 * @return the url
 */
public String getUrl() {
    return url;
}
/**
 * @return the requestString
 */
public String getRequestString() {
    return requestString;
}
    
    
}