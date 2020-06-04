package com.OpenDataHub.requests;


import java.io.*;
import java.net.*;
import java.net.http.*;
import java.util.concurrent.Callable;

public class Retriever implements  Callable<StringBuilder> {

    private String url;
    private int pageSize;
    private int pageNumber;
    private String requestString;
    private int activitytype;
    private Integer seed;

    public Retriever() {
        this.url = "http://tourism.opendatahub.bz.it/api/Activity";
        this.pageSize = 10;
        this.activitytype = 1023;
        this.pageNumber = 1;
        this.seed = null;
        this.requestString = "%s?pagenumber=%d&pagesize=%d&activitytype=%d&seed=%d";

    }

    /** 
    * sets the query string to use in makeRequest(); 
    * @throws IOException
    * @throws NumberFormatException
    */
    private String setQueryParamenters() throws NumberFormatException, IOException {

        String request = "";

        request = String.format(this.requestString, this.url, this.pageNumber, this.pageSize, this.activitytype,
                this.seed);

        return request;

    }

    /**
     * Creates a client, invokes setQueryParameters to build the URI
     * sends Asynchronous requests
     * @see FutureTask, 
     * @link RequestSetter
     * @return StringBuilder
     *  @throws IOException, InterruptedException, NumberFormatException
     */
    public StringBuilder makeRequest() throws IOException, InterruptedException, NumberFormatException {

        URI url;
        StringBuilder responseBody = new StringBuilder();
        url = URI.create(setQueryParamenters());

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(url).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            //   System.out.println("Status: " + response.statusCode());

            responseBody.append(response.body());

            return responseBody;
        }).join();

        return responseBody;

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

    /** 
     * @return StringBuilder
     * method invoked when threads are executed
     */
    public StringBuilder call() {
        StringBuilder bodyResponse = new StringBuilder();

        try {

            bodyResponse = makeRequest();

        } catch (NumberFormatException | IOException | InterruptedException e) {
          
            e.printStackTrace();
        }

        return bodyResponse;
    }
}