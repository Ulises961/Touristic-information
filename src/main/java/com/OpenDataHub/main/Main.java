/**
 * Main class, containing the main method for running the application.
 * 
 * @author Rigoni Riccardo
 */
package com.OpenDataHub.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.OpenDataHub.analysis.AnalysisDataStorage;
// import com.OpenDataHub.analysis.AnalysisOutput;
import com.OpenDataHub.analysis.AnalysisSupportMethods;
import com.OpenDataHub.analysis.ComputeAnalysis;
// import com.OpenDataHub.analysis.RegionWithLessActivities;
// import com.OpenDataHub.analysis.RegionWithMostActivities;
import com.OpenDataHub.fileio.FileProcessor;
import com.OpenDataHub.fileio.JsonFile;
import com.OpenDataHub.parser.Parser;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.OpenDataHub.requests.RequestSetter;
import com.OpenDataHub.requests.SharedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.OpenDataHub.runnable.SaveActivityJson;
import com.fasterxml.jackson.core.JsonProcessingException;

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

    String url = "http://tourism.opendatahub.bz.it/api/Activity";

    int activitiesPerPage = 10;
    int activityType = 1023;
    Integer seed = 1; 

    //number read from the requests.txt input file
    String fileInputPath = "src\\main\\resources\\requests.txt";

    int requestedActivities = new FileProcessor(fileInputPath).getIntegerFromFile();

    //check if the correct input
    if(requestedActivities <= 0) {
      logger.fatal(String.format("Error while reading from the requests.txt input file (%d)", requestedActivities));
      return;
    }

    //set parameters and makes the requests
    List<FutureTask<StringBuilder>> list;
    try {

      logger.info("Start requests to the Api");
      RequestSetter r = new RequestSetter(url, activitiesPerPage, activityType, seed, requestedActivities);
      list = r.startThreads();
    } 
    catch (Exception e) {
      return;
    }
    
    //sharedList class will manage to retrieve resposnes while available from the apis
    SharedList.addResponsesList(list);
    int counter = 0;
    
    logger.info("Retrieve string responses from FutureTasks");
    try {
      //test with no connection

      String nextResponse = SharedList.getNewElement();
      AnalysisDataStorage.initializeData();
    
      //-1 -> no more elements to retrieve
    while(nextResponse != "-1") {
        // generate ActivityDescriptions list from the api response

        List<ActivityDescription> toBeSavedAndAnalized = Parser.getActivityDescriptionList(nextResponse);

        logger.info("Compute analysis for a new response (" + (++counter) + ")");
        toBeSavedAndAnalized.stream().forEach((activity) -> AnalysisDataStorage.addElement(activity));

        logger.info("Save activity descriptions");
        //save files
        Thread saveDescriptions = new Thread(new SaveActivityJson(toBeSavedAndAnalized));
        saveDescriptions.start();

        logger.info("Retrieve new response from FutureTasks");
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

    //here generate a new FinalAnalysisClass
    try {
      ComputeAnalysis.start(AnalysisDataStorage.getList());  
    } catch (Exception e) {
      e.printStackTrace();;
    }
     
    logger.info("Execution terminated, bye bye!");
    
  }
}