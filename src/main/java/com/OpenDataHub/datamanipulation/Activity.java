package com.OpenDataHub.datamanipulation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Activity {
  //most important
  private String id;
  private String description;
  private String name;
  private List<String> types;
  private boolean hasGPSTrack;
  private String region;
  
  //for tests
  private JsonNode GpsPoints;
  
  //constructor
  public Activity(JsonNode node) {
    
    this.id = node.get("Id").asText();
    this.types = ODHTags(node);
    this.region = getRegion(node);
    String[] nameDescription = getNameAndDescription(node); //in order to not access two times the language field
    this.name = nameDescription[0];
    this.description = nameDescription[1];
    this.hasGPSTrack = getBooleanGPSTrack(node);

    //tests
    this.GpsPoints = node.get("GpsPoints");
  }

  public String toString() {
    return this.id + "\t Types: " + types.size() + "\tRegion: " + region +"\tName: " + name + "\nDescription: " + description + "\nGpsTrack: " + hasGPSTrack;
  }

  private List<String> ODHTags(JsonNode node) {
    LinkedList<String> types = new LinkedList<>();

    try {
      for (int i = 0; ; i++)
        types.add(node.get("ODHTags").get(i).get("Id").asText());
    } catch (Exception e) {
      //TODO: handle exception

      //loop until IndexOutOfBound exception
    }
    return types;
  }

  private String getRegion(JsonNode node) {
    //search for the region... the gives preference for the language en, it, de!
    JsonNode regionInfo = node.get("LocationInfo").get("RegionInfo").get("Name");

    //try to get the name in english
    try {
      String enRegion = regionInfo.get("en").asText();
      return enRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      String itRegion = regionInfo.get("it").asText();
      return itRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      String deRegion = regionInfo.get("de").asText();
      return deRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    return null;
  }

  private String[] getNameAndDescription(JsonNode node) {
    String[] output = new String[2];
    try {
      JsonNode detail = node.get("Detail").get("en"); //in case not find the language throws exception and tryies with the next
      String title = detail.get("Title").asText();
      output[0] = title;
      String description = detail.get("BaseText").asText();
      output[1] = description;
      return output;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      JsonNode detail = node.get("Detail").get("it");
      String title = detail.get("Title").asText();
      output[0] = title;
      String description = detail.get("BaseText").asText();
      output[1] = description;
      return output;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      JsonNode detail = node.get("Detail").get("de");
      String title = detail.get("Title").asText();
      output[0] = title;
      String description = detail.get("BaseText").asText();
      output[1] = description;
      return output;
    } catch (Exception e) {
      //TODO: handle exception
    }
    return null;
  }

  private List<JsonNode> getGPSTrack(JsonNode node) {
    LinkedList<JsonNode> list = new LinkedList<JsonNode>();
    JsonNode gpsInfo = node.get("GpsPoints"); //array
    
    try {
      for(int i = 0; ; i++)
        list.add(gpsInfo.get(i));
    } catch (Exception e) {
      //TODO: handle exception
    }
    return list;
  }

  public JsonNode getGps() {
    return this.GpsPoints;
  }

  public List<String> getTypes() {
    return this.types;
  }

  private boolean getBooleanGPSTrack(JsonNode node) {
    boolean hasGps = true;
    if(node.get("GpsInfo").isNull())
      if(node.get("GpsTrack").isEmpty())
        //if(node.get("GpsPoints").isNull()) 
        hasGps = false;
    return hasGps;
  }
}