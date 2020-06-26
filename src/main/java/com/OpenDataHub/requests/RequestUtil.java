package com.OpenDataHub.requests;

import java.util.HashSet;
import java.util.Set;

import com.OpenDataHub.fileio.FileProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestUtil {
  
  public static String URL = "http://tourism.opendatahub.bz.it/api/Activity";
  public static int ELEMENT_PER_PAGE = 100;
  public static int ELEMENT_IN_LAST_PAGE = -1;
  public static int ACTIVITY_TYPE = 1023;
  public static Integer SEED = null;
  public static int TOTAL_PAGES = -1;
  public static int ACTIVITIES_TO_BE_REQUESTED = -1;
  public static int PAGE_NUMBER = 1;

  public static String TOTAL_ACTIVITIES_FILE_PATH = "src/main/resources/requests.txt";
  public static String REQUEST_FORMAT = "%s?pagenumber=%d&pagesize=%d&activitytype=%d&seed=%d";

  public static Set<String> ID_ACTIVITIES_ALREADY_RECEIVED = new HashSet<>(); 
  public static Set<String> ID_ACTIVITIES_DUPLICATED = new HashSet<>();

  public static  void loadMissingParameters() {
    loadActivitiesNumberFromFile();
    computeTotalPages();
    computeElementInLastPage();
  }

  private static void loadActivitiesNumberFromFile() {
    ACTIVITIES_TO_BE_REQUESTED = new FileProcessor(RequestUtil.TOTAL_ACTIVITIES_FILE_PATH).getIntegerFromFile();
  }
  
  private static void computeTotalPages() {

    if (ACTIVITIES_TO_BE_REQUESTED > ELEMENT_PER_PAGE)
        TOTAL_PAGES = (int) Math.ceil(ACTIVITIES_TO_BE_REQUESTED / (double) ELEMENT_PER_PAGE);
    
    else
      TOTAL_PAGES = 1;
  }

  private static void computeElementInLastPage() {
    ELEMENT_IN_LAST_PAGE = ACTIVITIES_TO_BE_REQUESTED % ELEMENT_PER_PAGE;
      if (ELEMENT_IN_LAST_PAGE == 0)
        ELEMENT_IN_LAST_PAGE = ELEMENT_PER_PAGE;
        
  }
  
  public static boolean areParametersValid() {
    //total number of activities cannot be <= 0
    boolean correctParameter = true;

    if(ACTIVITIES_TO_BE_REQUESTED <= 0) {
      getLogger().fatal("Number of inserted activities not valide (only positive integer values");;
      correctParameter = false;
    }
    
    return correctParameter;
  }

  /** setQuery()
     * 
     * This method prepares the String to be used when settig the request
     *  @return String
     */
  public static String setDefaultQuery() {
      return String.format(REQUEST_FORMAT, URL, PAGE_NUMBER, ELEMENT_PER_PAGE, ACTIVITY_TYPE, SEED);
  }

  /**
   * @param newActivityNumber 
   */
  public static void setActivitiesToRetrieve(int newActivityNumber) {
    ACTIVITIES_TO_BE_REQUESTED = newActivityNumber;
    computeTotalPages();
    computeElementInLastPage();
  }

  /**
   * method that verify if an activity was previously downloaded
   * 
   * @param newId
   * @return return true if the activity was retrieved for the first time, false if it is a duplicate 
   */
  public static boolean isNewActivity(String newId) {
    if(ID_ACTIVITIES_ALREADY_RECEIVED.contains(newId)) {
      ID_ACTIVITIES_DUPLICATED.add(newId);
      return false;
    }
    else {
      ID_ACTIVITIES_ALREADY_RECEIVED.add(newId);
      return true;
    }
  }

  private static Logger getLogger() {
    return LogManager.getLogger();
    
  }
}