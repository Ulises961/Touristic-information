/**
 * Class containing methods for compute the analysis on the ActivityDescriptions list retrieved from the api
 */
package com.OpenDataHub.analysis;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.OpenDataHub.fileio.JsonFile;
import com.OpenDataHub.fileio.schema.JsonSchemaValidator;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.networknt.schema.ValidationMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputeAnalysis {
  
  /**
   * 
   * @param inputList list containing the {@link ActivityDescription} objects on which make the analysis 
   * @return return Optional<true> if all works fine, otherwise Optional<false>
   * @throws Exception
   */
  public static Optional<Boolean> start(List<ActivityDescription> inputList) {
    getLogger().info("Start computing analysis");
    
    //skip if input == null -> stream() fails on it
      if(inputList == null || inputList.isEmpty())  {
        getLogger().error("Input list empty or null");;
        return Optional.of(false);
      }
      else{
      Map<String,Long> odhTagAndOccurrence = inputList.stream()
                .flatMap((activityDescription) -> activityDescription.getTypes().stream())
                .collect(Collectors.groupingBy((typeId) -> typeId, Collectors.counting()));
          
      List<String> trackedActivitiesId = inputList.stream()
                .filter(activityDescription -> activityDescription.isHasGpsTrack())
                .map(activityWithGps -> activityWithGps.getIdActivity())
                .collect(Collectors.toList());  
      
      //map storing regions id and their occurrences
      Map<String,Long> occurrencesRegions = inputList.stream()
        .filter(entryRegion -> entryRegion.getRegionId() != null) //filter null values -> .map() method fails on them
        .map(activityDescription -> activityDescription.getRegionId())
        .collect(Collectors.groupingBy((regionId) -> regionId, Collectors.counting()));
      
      Long maxOccurrence = occurrencesRegions.entrySet().stream()
        .map(touple -> touple.getValue())
        .max(Long::compareTo).get();

      Long minOccurrence = occurrencesRegions.entrySet().stream()
        .map(touple -> touple.getValue())
        .min(Long::compareTo).orElse((long) -1);

      //extract all regions with highest activities
      List<String> regionsMaxList = occurrencesRegions.entrySet().stream()
        .filter(entryRegion -> entryRegion.getValue() == maxOccurrence)
        .map(entryRegion -> entryRegion.getKey())
        .collect(Collectors.toList());

      //extract all regions with lowest occurrence
      List<String> regionsMinList = occurrencesRegions.entrySet().stream()
        .filter(entryRegion -> entryRegion.getValue() == minOccurrence)
        .map(entryRegion -> entryRegion.getKey())
        .collect(Collectors.toList());
      
      
      //store regionsId data 
      RegionWithActivities regionWithMaxActivities = new RegionWithActivities(maxOccurrence, regionsMaxList);
      RegionWithActivities regionWithLessActivities = new RegionWithActivities(minOccurrence, regionsMinList);
      
      getLogger().info("Computed analysis");

      //instantiate object (contains all the results)
      AnalysisResult analysisResult = new AnalysisResult(odhTagAndOccurrence, trackedActivitiesId, regionWithMaxActivities, regionWithLessActivities);

      String analysisResultString;
      String folder = "src/main/results";
      
      try {
        analysisResultString = ObjectMapperClass.mapper.writeValueAsString(analysisResult).toString();
        Set<ValidationMessage> validationErrors = JsonSchemaValidator.ValidateFromString(analysisResultString, AnalysisResult.class);
        
        if(validationErrors.size() == 0) { //schema validated -> save file
          JsonFile analysisFile = new JsonFile(analysisResult);
          analysisFile.Save(folder);
        }
        else 
          //log the error and do not save the file
          getLogger().error("Errors while validating analysis file\n" + validationErrors.toString());
      } 
      catch (Exception e) {
        getLogger().error("Error while saving the analysis file");  
        return Optional.of(false);
      }
      
      
      getLogger().info("Save analysis file");

      return Optional.of(true);
    }
  }

  private static Logger getLogger() {
    return LogManager.getLogger(ComputeAnalysis.class);
  }

  public void setFilePath(String newFilePath) {}
}