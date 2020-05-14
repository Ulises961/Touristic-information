package com.OpenDataHub.requests;


import java.io.*;
import java.net.*;


public class Retriever {
    
    public Retriever() {

    }

    /** 
    @param sets the query string to use in makeRequest(); 
    */
    private String setQueryParamenters() {


        String request = "";
       
        String url= "http://tourism.opendatahub.bz.it/api/Activity";
        
        int pageSize = Loader.getInput();
   

        int activitype = 1023;

        int pageNum = 1;

        request= String.format("%s?pagenumber=%d&pagesize=%d&activitytype=%d", url, pageNum, pageSize, activitype);
      
        return request;

    }
    
    /**
     *  Creates a client, invokes setQueryParameters to build the URI
     * and finally writes the result to a Json.json file on the pwd
     * 
     *  @makeRequest
     */
    public String makeRequest() throws IOException, InterruptedException {

        URL url = new URL(setQueryParamenters());
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        int status = connection.getResponseCode();
        System.out.println("Status: " + status);
    
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
    System.out.println(requestBody);
        return requestBody;
    }
    

           /**
          * This method invokes valideteInput() for validation of the input in the txt file. 
          * If the input loaded into the txt file 
          * is correct then the method invokes the makeRequest() method 
          * @produceJson() 
          */
           public String checkAndRequest() {
               String output = "";
        if (Loader.validateInput()) {
            try {
              
                 output = makeRequest();
                
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		return output;

    }
    
    
}