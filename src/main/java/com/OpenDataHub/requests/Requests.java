package com.OpenDataHub.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Requests {
  public static void main(String[] args)  {

    String url = "http://tourism.opendatahub.bz.it/api/Activity";

    int activitiesPerPage = 10;
    int activityType = 1023;
    Integer seed = null; 
    int requestedActivities = 1;

    RequestSetter r = new RequestSetter(url, activitiesPerPage, activityType, seed, requestedActivities);
    List<FutureTask<StringBuilder>> list = r.startThreads();
    for ( FutureTask<StringBuilder> element : list)
    System.out.println(element.toString());

 
  }
}