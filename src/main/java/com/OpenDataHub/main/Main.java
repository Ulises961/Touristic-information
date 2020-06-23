/**
 * Main class, containing the main method for running the application.
 * 
 * @author Rigoni Riccardo
 */
package com.OpenDataHub.main;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.OpenDataHub.analysis.ComputeAnalysis;
import com.OpenDataHub.fileio.FileProcessor;
import com.OpenDataHub.parser.Parser;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.requests.RequestMaker;
import com.OpenDataHub.requests.RequestParameters;
import com.OpenDataHub.requests.RequestSetter;
import com.OpenDataHub.requests.RequestUtil;
import com.OpenDataHub.requests.SharedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.OpenDataHub.runnable.SaveActivityJson;

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
    
    List<FutureTask<StringBuilder>> list = RequestMaker.startThreadsMakingRequests();
    
    //sharedList class will manage to retrieve resposnes while available from the apis
    SharedList.addResponsesList(list);
    List<ActivityDescription> allActivitiesDescriptions = new LinkedList<>();
    
    logger.info("Retrieve string responses from FutureTasks");
    try {
      String nextResponse = SharedList.getNewElement();
    
      //-1 -> no more elements to retrieve
    while(nextResponse != "-1") {
        // generate ActivityDescriptions list from the api response

        List<ActivityDescription> toBeSavedAndAnalized = Parser.getActivityDescriptionList(nextResponse);

        toBeSavedAndAnalized.stream().forEach((newActivity) -> allActivitiesDescriptions.add(newActivity));

        //save files
        Thread saveDescriptions = new Thread(new SaveActivityJson(toBeSavedAndAnalized));
        saveDescriptions.start();

        nextResponse = SharedList.getNewElement();  
      }
    } 
    catch (ExecutionException e) {
      logger.fatal("Problems while retrieving responde from the future task");
      return;
    }
    catch (InterruptedException e) {
      logger.fatal(e.getMessage());
      return;
    }

    //compute analysis on data
    try {
      ComputeAnalysis.start(allActivitiesDescriptions);  
    } catch (Exception e) {
      logger.error(e.getStackTrace());;
    }
     
    logger.info("Execution terminated, bye bye!");
    
  }
}