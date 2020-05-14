package com.OpenDataHub.requests;


import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public void makeRequest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create(setQueryParamenters());

        HttpRequest request = HttpRequest.newBuilder(uri).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());

    }
    

           /**
          * This method invokes valideteInput() for validation of the input in the txt file. 
          * If the input loaded into the txt file 
          * is correct then the method invokes the makeRequest() method 
          * @produceJson() 
          */
    public void checkAndRequest() {
        if (Loader.validateInput()) {
            try {
              
                makeRequest();
           
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    
    
}