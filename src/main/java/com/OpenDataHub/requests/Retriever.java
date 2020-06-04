package com.OpenDataHub.requests;


import java.io.*;
import java.net.*;

import javax.print.attribute.standard.PagesPerMinute;


public class Retriever {

    private int activitytype;
    private int pageNumber;
    private int pageSize;
    private String requestFormat;
    private String url;
    // private int 
    
    public Retriever() {        
        //set by default this value
        this.activitytype = 1023;  
        this.pageNumber = 1;
        this.pageSize = 0; //by default set it to 0
        this.requestFormat = "%s?pagenumber=%d&pagesize=%d&activitytype=%d";
        this.url = "http://tourism.opendatahub.bz.it/api/Activity";
    }


    private String setQueryParamenters() { 
        String request = "";

        request= String.format(this.requestFormat, this.url, this.pageNumber, this.pageSize, this.activitytype);
      
        return request;

    }
    
    /**
     *  Creates a client, invokes setQueryParameters to build the URI
     * and finally writes the result to a Json.json file on the pwd
     * 
     *  @makeRequest
     */
    public String makeRequest() throws IOException, InterruptedException {
        
        //pass as parameters and more easily change them, defined when calling the function
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
     * @return int return the activitytype
     */
    public int getActivitytype() {
        return activitytype;
    }

    /**
     * @param activitytype the activitytype to set
     */
    public void setActivitytype(int activitytype) {
        this.activitytype = activitytype;
    }

    /**
     * @return int return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return int return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return String return the requestFormat
     */
    public String getRequestFormat() {
        return requestFormat;
    }

    /**
     * @param requestFormat the requestFormat to set
     */
    public void setRequestFormat(String requestFormat) {
        this.requestFormat = requestFormat;
    }

    /**
     * @return String return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}