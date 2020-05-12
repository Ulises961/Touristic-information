package com.OpenDataHub.datamanipulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Methods {
  /* fields in the classes has private visibility*/
  ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
 
  //method for retrieving the list of all the items in the json file input... 
  public JsonNode readFile(String pathName, String field) throws JsonProcessingException, IOException {
    File file = new File(pathName);

    JsonNode node = mapper.readTree(file).get(field);
    return node;
  }

  // retrieve data from a JsonNode
  // gets as input a JsonArray containing the description of all the activities retrieved by the OpenDataHub API
  // in a for loop (infinite cycle) continue to read ind instantiate Activity classes and add them to the list
  public ArrayList<Activity> getList(JsonNode node) throws JsonProcessingException, IOException {
    ArrayList<Activity> list = new ArrayList<Activity>();

    try {
      for(int i = 0;; i++) { //infinite loop until throws an exception NullPointer Exception... no more elements 
        list.add(new Activity(node.get(i)));
        System.out.println(i);
      }    
    } catch (Exception e) {
      ;
    }
    return list;
  }

  //for every activity in the list, serialize the object into a JsonFile
  public void generateActivityJson(String pathname, ArrayList<Activity> list) throws IOException{

    for(int i = 0; i < list.size(); i++) {
      String fileName = list.get(i).getId();
      File newFile = new File(pathname + fileName + ".json");
      newFile.createNewFile();

      BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
      String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list.get(i));
      writer.write(output); //take the activity object and write it as JsonFile -> expoilt to String method
      writer.flush();
      writer.close();
    }

  }

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
    
    System.out.println(list.toString());
    System.out.println(occurrences.toString());
    // Set<String> ids = new LinkedHashSet<>();
    // for (int i = 0; i < list.size(); i++)
    //   ids.add(list.get(i).getRegionId());
    // System.out.println(ids);
    return occurrences;
  }
}