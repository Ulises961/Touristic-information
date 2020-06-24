/**
 * The class contains a list of FutureTasks for storing the responses retrieved from the Api
 * 
 * @author Sosa Ulises
 */
package com.OpenDataHub.requests;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.OpenDataHub.analysis.ComputeAnalysis;
import com.OpenDataHub.parser.Parser;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.runnable.SaveActivityJson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActivityDescriptionsManager {

  private static List<FutureTask<StringBuilder>> responsesList = new LinkedList<>();
  private static List<ActivityDescription> allActivitiesGenerated = new LinkedList<>();
  private static boolean parseLastElement = false;
  private static boolean finished = false;

  private static Logger logger = LogManager.getLogger();
  
  /***
   * Add a new list and set the number of iterations
   * @param newResponsesList new list for getting "done" responses
   */
  public static void addResponsesList(List<FutureTask<StringBuilder>> newResponsesList) {
    responsesList = newResponsesList;
 
  }

  /**
   * retrieve a String from the FutureTasks list. The method iterate continuosly through the list, until it 
   * founds a "done" object.
   * From it extract the content and return it as String
   * 
   * @return return String response; "-1" if no more elements in the list to wait for 
   * @throws InterruptedException
   * @throws ExecutionException
   */
public static String getNewElement() throws InterruptedException, ExecutionException {
  String newElement = "";

    //iterate from inital element of the list
    int index = 0;
    
    while (!finished) {

      //list empty -> no more elements
      if (responsesList.size() == 0) {
        finished = true;
        break;
      }
      //size == 1 -> only one element remaining -> last page
      //should return a special value, 
      else if(responsesList.size() == 1) {
        parseLastElement = true;
      }
      
      boolean responseIsReady = responsesList.get(index).isDone();
      if (responseIsReady) {
    
        FutureTask<StringBuilder> response = responsesList.remove(index);

        newElement = response.get().toString();
        
        return newElement;
      }
      else
        index = (index + 1) % (responsesList.size());
      } 
      //if responsesList is empty -> no need to wait more for new elements to be done
      return "-1";
    }
  

  /**
   * Method for parse responses, launch thread for saving the ActivityDescrptions and lanch the Analysis on data retrieved.
   * A list of ActivitiyDescriptions is generated at every iteration for every successfull response from Api and superfluous elements from the last response are discarded.
   */
  public static void processResponses() {
    try {
      String nextResponse = getNewElement();
    
      //-1 -> no more elements to retrieve
    while(nextResponse != "-1") {
        // generate ActivityDescriptions list from the api response

        List<ActivityDescription> toBeSavedAndAnalized = Parser.getActivityDescriptionList(nextResponse);

        //discard superfluous elements
        if(parseLastElement)
          toBeSavedAndAnalized = toBeSavedAndAnalized.subList(0, RequestUtil.ELEMENT_IN_LAST_PAGE);
        
        toBeSavedAndAnalized.stream().forEach((newActivity) -> allActivitiesGenerated.add(newActivity));


        //save files
        Thread saveDescriptions = new Thread(new SaveActivityJson(toBeSavedAndAnalized));
        saveDescriptions.start();

        nextResponse = getNewElement();  
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

    //compute analysis on all the ActivityDescriptions generated
    Optional<Boolean> doneCorrectly = ComputeAnalysis.start(allActivitiesGenerated);  
    
    if(doneCorrectly.orElse(false))
      logger.info("Analysis computed without any problem"); 
    else 
      logger.error("Problems while making the analysi over tha dataset");
    
  }
}