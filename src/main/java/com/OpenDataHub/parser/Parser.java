/**
 * Contains the methods for creating {@link #ActivityDescription} classes from the
 * responses of the API
 * 
 * @author Rigoni Riccardo
 */

package com.OpenDataHub.parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parser {
  private static Logger logger = LogManager.getLogger();
  /**
   * Retrieving the list of {@link ActivityDescription} generated from
   * the Api response.
   * Whenever a {@link JsonMappingException} or a {@link JsonMappingException} being thrown, 
   * the returned list is an empty list.
   * The method internally catch {@link NoLanguageAvailable} exception. In this case, does not add 
   * any Description for such a {@link Activity}
   * 
   * @param apiResponse String returned by the Api
   * @return return List<{@link #ActivityDescription}> generated from the Api
   *         esponse
   */
  public static List<ActivityDescription> getActivityDescriptionList(String apiResponse) {
    List<ActivityDescription> returnedList = new LinkedList<>();

    try {
      String itemsFieldContent = extractItemsField(apiResponse);

      Activity[] activityArray = generateActivityFromString(itemsFieldContent);
      
      Consumer<Activity> extractAndSaveDescription = (newActivity) -> {
        try {
          ActivityDescription newElement = newActivity.getActivityDescription();
          returnedList.add(newElement);
        } 
        catch(NoLanguageAvailable e){
          //if no Languages Available, not possible to instatiate the ActivityDescriptionObject -> not add it to the list
          return;
        }
      };
      Arrays.stream(activityArray).forEach(extractAndSaveDescription);
      return returnedList;
    } 
    catch (JsonProcessingException e) {
      //impossible to instatiate the list, return an empty one
      returnedList.clear();
      return returnedList;
    }
  }

  /**
   * 
   * @param itemsFieldValue String containing the array of activity descriptions
   * @return return {@link #Activity} array cointaining the classes generated from the input String
   * @throws JsonProcessingException
   * @throws JsonMappingException
   */
  private static Activity[] generateActivityFromString(String itemsFieldValue) 
      throws JsonMappingException, JsonProcessingException {
    
    try {
      Activity[] activityArray = ObjectMapperClass.mapper.readValue(itemsFieldValue, Activity[].class);
      return activityArray;
    } catch (Exception e) {
      logger.error("Exception thrown: " + e.getMessage());
      return new Activity[0];
    }
    
    
  }

  /**
   * From the whole response extract the content of field "Items"
   * @param apiResponse entire Api response String
   * @return return String containing value of the field "Items"
   * @throws JsonMappingException
   * @throws JsonProcessingException
   */
  private static String extractItemsField(String apiResponse) throws JsonMappingException, JsonProcessingException {
    //could be implemented using regex expression
    JsonNode rawInput = ObjectMapperClass.mapper.readTree(apiResponse);  
    JsonNode activityDescriptionArray = rawInput.get("Items");
    
    return activityDescriptionArray.toString();
  } 

}