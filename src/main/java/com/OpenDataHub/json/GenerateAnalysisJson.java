package com.OpenDataHub.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.OpenDataHub.datamanipulation.Activity;
import com.OpenDataHub.datamanipulation.RegionId;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateAnalysisJson {
  ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

   // generate the Analysis.json file taking as input the list of activities
   public void generateAnalysisJson(String pathname, ArrayList<Activity> list) throws IOException {
    File file = new File(pathname + "analysis.json");
    file.createNewFile();

    Map<String,Integer> occurrences = counterTypes(list);
    List<String> trackedActivitiesIds = trackedActivityIds(list);
    
    Map<String,Integer> regionOccurrences = computeRegionOccurrences(list);
    RegionId regionsWithMostActivities = new RegionId(regionOccurrences, 1); // 1 -> the one with highest occurrence
    RegionId regionsWithLessActivities = new RegionId(regionOccurrences, 0); // 0 -> the one with the minimum one

    Analysis analysis = new Analysis(occurrences, trackedActivitiesIds, regionsWithMostActivities, regionsWithLessActivities);

    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(analysis));

    writer.close();
  }
  private Map<String, Integer> counterTypes(List<Activity> list) {
    
    Map<String, Integer> occurences = new HashMap<String,Integer>();

    //for every Activity in the list, for every activity type in the sublist
    for(int i = 0; i < list.size(); i++) {
      List<String> types = list.get(i).getTypes();

      for(int y = 0; y < types.size(); y++) {

        String name = types.get(y).toLowerCase();
          if(occurences.containsKey(name)) //if the String already in the map -> increment associated value
            occurences.replace(name,  occurences.get(name).intValue() + 1);
          else //if not in the list
            occurences.put(name, 1);
          
      }
    }
    return occurences;
  }

  private List<String> trackedActivityIds(List<Activity> list) {
    List<String> trackedActivities = new LinkedList<String>();
    for(int i = 0; i < list.size(); i++) {
        if(list.get(i).hasGpsTrack())
          trackedActivities.add(list.get(i).getId());
    }
    return trackedActivities;
  }
  
  private Map<String,Integer> computeRegionOccurrences(ArrayList<Activity> list) {
    Map<String,Integer> occurrences = new HashMap<String,Integer>();

    for(int i = 0; i < list.size(); i++) {
      String id = list.get(i).getRegionId();
      //if in the map..
      if(occurrences.containsKey(id))
        occurrences.replace(id, occurrences.get(id).intValue() + 1);
      else 
      occurrences.put(id, 1);  
    }
    //remove null id... regions that does not have it (or more then one!)
    occurrences.remove("null");
    // Set<String> ids = new LinkedHashSet<>();
    // for (int i = 0; i < list.size(); i++)
    //   ids.add(list.get(i).getRegionId());
    // System.out.println(ids);
    return occurrences;
  }
}