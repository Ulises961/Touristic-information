/**
 * Main class, containing the main method for running the application.
 * 
 * @author Rigoni Riccardo
 */
package com.OpenDataHub.main;

import java.util.List;
import java.util.concurrent.FutureTask;

import com.OpenDataHub.requests.ActivityDescriptionsManager;
import com.OpenDataHub.requests.RequestMaker;
import com.OpenDataHub.requests.RequestUtil;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  /**
   * Main method is divided into ? major parts.
   * 1) reading from input file how many Activitys should be retrieved from the Api
   * 2) make requests for getting the resposne
   * 3) parse the response
   * 4) compute the analysis
   * 5) store Activity's description into .json files
   * @param args
   */
  public static void main(String[] args) {

    Logger logger = LogManager.getRootLogger();

    logger.info("Setting parameters for making requests to the Api");

    RequestUtil.loadMissingParameters();
    if(!RequestUtil.areParametersValid())
      return;
    
    List<FutureTask<StringBuilder>> tasksWaitingForResponses = RequestMaker.startThreadsMakingRequests();
    
    ActivityDescriptionsManager.addResponsesList(tasksWaitingForResponses);
    ActivityDescriptionsManager.processResponses();
    
    logger.info("Execution terminated, bye bye!");
    
  }
}