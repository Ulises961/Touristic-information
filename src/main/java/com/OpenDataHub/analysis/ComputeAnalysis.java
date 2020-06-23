package com.OpenDataHub.analysis;

import java.lang.StackWalker.Option;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.OpenDataHub.fileio.JsonFile;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;

public class ComputeAnalysis {
  
  public static Optional<Boolean> start(List<ActivityDescription> inputList) throws Exception{
    
    //count occurrences for every odhTag
    Map<String,Long> odhTagAndOccurrence = inputList.stream()
              .flatMap((activityDescription) -> activityDescription.getTypes().stream())
              .collect(Collectors.groupingBy((typeId) -> typeId, Collectors.counting()));
    
    List<String> trackedActivitiesId = inputList.stream()
              .filter(activityDescription -> activityDescription.isHasGpsTrack())
              .map(activityWithGps -> activityWithGps.getIdActivity())
              .collect(Collectors.toList());  
    
    //map storing regions id and their occurrences
    Map<String,Long> occurrencesRegions = inputList.stream()
      .filter(entryRegion -> entryRegion.getRegionId() != null)
      .map(activityDescription -> activityDescription.getRegionId())
      .collect(Collectors.groupingBy((regionId) -> regionId, Collectors.counting()));
    
    Long maxOccurrence = occurrencesRegions.entrySet().stream()
      .map(touple -> touple.getValue())
      .max(Long::compareTo).get();

    Long minOccurrence = occurrencesRegions.entrySet().stream()
      .map(touple -> touple.getValue())
      .min(Long::compareTo).get();


    List<String> regionsMaxList = occurrencesRegions.entrySet().stream()
      .filter(entryRegion -> entryRegion.getValue() == maxOccurrence)
      .map(entryRegion -> entryRegion.getKey())
      .collect(Collectors.toList());

    List<String> regionsMinList = occurrencesRegions.entrySet().stream()
      .filter(entryRegion -> entryRegion.getValue() == minOccurrence)
      .map(entryRegion -> entryRegion.getKey())
      .collect(Collectors.toList());
    
    RegionWithActivities regionWithMaxActivities = new RegionWithActivities(maxOccurrence, regionsMaxList);
    RegionWithActivities regionWithLessActivities = new RegionWithActivities(minOccurrence, regionsMinList);
        

    AnalysisResult analysisOutput = new AnalysisResult(odhTagAndOccurrence, trackedActivitiesId, regionWithMaxActivities, regionWithLessActivities);
    
    String fileName = "src\\main\\results\\" + "analysis" + ".json";

    String fileContent = ObjectMapperClass.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(analysisOutput);  
    JsonFile analysisFile = new JsonFile(fileName, fileContent);

    analysisFile.Save();
    
    return Optional.of(true);
  }

}