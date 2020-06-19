package com.OpenDataHub.requests;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Requests {
  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    logger.info("Starting app");

    String url = "http://tourism.opendatahub.bz.it/api/Activity";

    int activitiesPerPage = 10;
    int activityType = 1023;
    Integer seed = null;
    int requestedActivities = 1;

    RequestSetter r = new RequestSetter(url, activitiesPerPage, activityType, seed, requestedActivities);
    List<FutureTask<StringBuilder>> list = r.startThreads();
    for (FutureTask<StringBuilder> element : list)
     element.get();
    

    System.out.println("error after the get");

  }
  
}