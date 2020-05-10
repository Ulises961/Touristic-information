package com.OpenDataHub.datamanipulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataManipulation {
  public static void main(final String[] args) throws JsonParseException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    final ArrayList<Activity> list = new ArrayList();
    final TypeReference type = new TypeReference<ArrayList<Activity>>() {
    };

    File file = new File("input.json");
    final JsonNode node = mapper.readTree(file).get("Items");

   /* int elements = mapper.readTree(file).get("TotalResults").asInt();
    int pages = mapper.readTree(file).get("TotalPages").asInt();
    int availableElements = elements / pages;
    */

    try {
      for(int i = 0;; i++) { //infinite loop until throws an exception NullPointer Exception... no more elements 
        list.add(new Activity(node.get(i)));
        System.out.println(i);
      }    
    } catch (Exception e) {
      System.out.println("finisch parsing");
    }

    File output = new File("output.json");
    output.createNewFile();

    BufferedWriter writer = new BufferedWriter(new FileWriter(output));

    for(int i = 0; i < list.size(); i++){
      //if(list.get(i).getGps() == false)
        //System.out.println(list.get(i).getGps());
      // System.out.println(list.get(i));
      System.out.println("Writing Activity number " + i);
      writer.append("" + i); //writer the number... otherwise cast to the corresponding character
      writer.newLine();
      if(list.get(i).getGps().isNull())
      writer.write(list.get(i).getGps().toPrettyString());
      writer.newLine();
    }

    /*compute number of activities per type 
    * Mixing up the list of the names with the list of the values... 
    * In order to has them in order, mix the two lists, ...:<value> (using ':' as separator) 
    */
    List<String> perType = new LinkedList();
    List<Integer> counter = new LinkedList();

    //for every Activity in the list, for every activity type in the sublist
    for(int i = 0; i < list.size(); i++) {
      List<String> types = list.get(i).getTypes();
      for(int y = 0; y < types.size(); y++) {
          if(perType.contains(types.get(y))) { //if String already in the String, get index of his position... increment counter in the correspontet
            int position = perType.indexOf(types.get(y));
            Integer value = counter.remove(position);
            Integer newValue = value.intValue() + 1;
            counter.add(position, newValue);
          }
          else { //if not in the list
            perType.add(types.get(y));
            counter.add(1);
          }
      }
    }

    List<String> orderedName = new LinkedList();
    int size = perType.size();
    //mix up the two lists
    for(int i = 0; i < size; i++){
      String name = perType.remove(0); //remove and retrieve... every time get element at index 0, at the end the lists will be empty
      name = name + ":" + counter.remove(0);
      orderedName.add(name); //add the name again with, in addition, the namber of repetitions
    }

    long time = System.currentTimeMillis();
    //put the list in order
    orderedName.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println("Time: " + (System.currentTimeMillis() - time));

    //clean the counter list 
    //counter.clear();

    //for every element, split
    System.out.println("PerType size: " + perType.size() + "\tCounter size: " + counter.size() + "\tOrdered: " + orderedName.size());
    for(int i = 0; i < size; i++)
      System.out.println("Type: " + orderedName.get(i));
  }
}