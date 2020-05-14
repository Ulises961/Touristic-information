package com.OpenDataHub.datamanipulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
}