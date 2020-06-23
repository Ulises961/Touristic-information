/**
 * Class for storing all the relevant data of the Activities
 */
package com.OpenDataHub.analysis;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.ODHTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnalysisDataStorage {
  private static Logger logger = LogManager.getLogger();

  private static List<ODHTag> odhTagsAndOccurrences;
  private static List<String> trackedActivities;
  private static Map<String,Integer> regionsIdsAndOccurrences;
  
/**
 * Method for update occurrences od the ODHTags
 * @param newTags input list containing new tags
 */
  public static void updateODHTagsOccurrences(List<String> newTags) {
    synchronized(odhTagsAndOccurrences) {

      newTags.stream().forEach((odhTagId) -> {
        boolean foundMatches = false;

        //goes through all the objects in the list. If one has the same id, increment the occurrence of object in the list
        for (ODHTag odhTagFromList : odhTagsAndOccurrences) {

          if(odhTagId.contentEquals(odhTagFromList.getOdhId())) {
            odhTagFromList.incrementOccurrences();
            foundMatches = true;
          }
        }

        if(!foundMatches) {
          //since first time occurs, set default value at one

          final ODHTag newTag = new ODHTag();
          newTag.setOdhId(odhTagId);
          newTag.initOccurrence();
          odhTagsAndOccurrences.add(newTag);
        }
      });
    }
  }

  public static Map<String,Integer> collectTagsWithOccurrence() {
    Function<ODHTag,Integer> extractValue = (tag) -> tag.getOccurrences();
    Function<ODHTag,String> extractKey = (tag) -> tag.getOdhId();
    return odhTagsAndOccurrences.stream().collect(Collectors.toMap(extractKey, extractValue));
  }

  /**
   * 
   * @param inputList
   */
  public static void updateTrackedActivities(List<ActivityDescription> inputList) {
    for (ActivityDescription activityDescription : inputList) {
      synchronized(trackedActivities) {
        if(activityDescription.isHasGpsTrack() == true)
          trackedActivities.add(activityDescription.getIdActivity());
      }
    }
  }

  public static List<String> getTrackedActivities() {
    return trackedActivities;
  }

  public static void updateRegionIds(List<String> inputList) {
    synchronized(regionsIdsAndOccurrences) {

      inputList.stream().forEach((regionId) -> {
        //check if the Id is already in the map (increment associated value)
        if(regionsIdsAndOccurrences.containsKey(regionId)) {
          Integer previousValue =  regionsIdsAndOccurrences.get(regionId);
          regionsIdsAndOccurrences.replace(regionId, previousValue, ++previousValue);
        }
        //if not, add a new Entry with initial value of 1
        else {
          regionsIdsAndOccurrences.put(regionId, 1);
        }
      });
    }
  }

  public static RegionWithMostActivities getRegionWithMostActivities() {
    logger.debug(regionsIdsAndOccurrences);

    Optional<Entry<String,Integer>> optionalMaxOccerrenceRegion = regionsIdsAndOccurrences.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
     
    try {
      Integer maxOccurrence = optionalMaxOccerrenceRegion.get().getValue();
      
      logger.debug("Found maximum: " + maxOccurrence);

      //collect together all the ids with same highest occurrence
      List<String> regionWithMaxOccurrences = regionsIdsAndOccurrences.entrySet().stream()
          .filter(entryRegion -> entryRegion.getValue().equals(maxOccurrence))
          .map(entry -> entry.getKey())
          .collect(Collectors.toList());

     Map<Integer,List<String>> result = new LinkedHashMap<>();
     result.put(maxOccurrence, regionWithMaxOccurrences);

     result = cleanNullValues(result);
     
     return new RegionWithMostActivities(result);
    }
     catch (NoSuchElementException e) {
      logger.error(e.getMessage());
      return null;
      }
    }

  public static RegionWithLessActivities getRegionWithLessActivities() {
    Optional<Entry<String,Integer>> optionalMaxOccerrenceRegion = regionsIdsAndOccurrences.entrySet().stream().min(Comparator.comparing(Map.Entry::getValue));
     
    try {
      Integer minOccurrence = optionalMaxOccerrenceRegion.get().getValue();
      
      logger.debug("Found minimum: " + minOccurrence);

      //collect together all the ids with same highest occurrence
      List<String> regionWithLessOccurrences = regionsIdsAndOccurrences.entrySet().stream()
          .filter(entryRegion -> entryRegion.getValue().equals(minOccurrence))
          .map(entry -> entry.getKey())
          .collect(Collectors.toList());

     Map<Integer,List<String>> result = new LinkedHashMap<>();
     result.put(minOccurrence, regionWithLessOccurrences);

     result = cleanNullValues(result);

     return new RegionWithLessActivities(result);
    }
     catch (NoSuchElementException e) {
      logger.error(e.getMessage());
      return null;
      }
    }
  
  private static <K,V> Map<K,List<V>> cleanNullValues(Map<K,List<V>> inputMap) { 
    inputMap.entrySet().stream().forEach((entry) -> entry.getValue().remove(null));

    return inputMap;
  }

  /**
   * initialize all the data stored before in the class
   */
  public static void initializeData() {
    odhTagsAndOccurrences = new LinkedList<>();
    trackedActivities = new LinkedList<>();
    regionsIdsAndOccurrences = new LinkedHashMap<>();
  }
}