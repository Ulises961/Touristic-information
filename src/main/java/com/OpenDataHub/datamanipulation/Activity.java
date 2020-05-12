package com.OpenDataHub.datamanipulation;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

public class Activity {
  //most important
  private String id;
  private String name;
  private String description;
  private List<String> types;
  private boolean hasGPSTrack;
  private String region;
  
  @JsonIgnoreProperties("regionId")
  private String regionId;
 
  //constructor
  public Activity(JsonNode node) {
    
    this.id = node.get("Id").asText();
    this.types = ODHTags(node);
    this.region = retrieveRegionName(node);
    this.regionId = retrieveRegionId(node);
    
    String[] nameDescription = retrieveNameAndDescription(node); //in order to not access two times the language field
    this.name = nameDescription[0];
    this.description = nameDescription[1];
    
    this.hasGPSTrack = retrieveBooleanGPSTrack(node);

  }

  private List<String> ODHTags(JsonNode node) {
    LinkedList<String> types = new LinkedList<>();

    try {
      for (int i = 0; ; i++)
        types.add(node.get("ODHTags").get(i).get("Id").asText());
    } catch (Exception e) {
      //TODO: handle exception

    }
    return types;
  }

  //return a String array containing the id and the 
  private String retrieveRegionName(JsonNode node) {
    String name = null;

    //search for the region... the gives preference for the language en, it, de!
    JsonNode regionInfo = node.get("LocationInfo").get("RegionInfo").get("Name");

    //try to get the name in english
    try {
      String enRegion = regionInfo.get("en").asText();
      name = enRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      String itRegion = regionInfo.get("it").asText();
      name = itRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    try {
      String deRegion = regionInfo.get("de").asText();
      name = deRegion;
    } catch (Exception e) {
      //TODO: handle exception
    }
    
    return name;
  }

  private String retrieveRegionId(JsonNode node) {
    String id = null;
    try {
      id = node.get("LocationInfo").get("RegionInfo").get("Id").asText();
    } catch (Exception e) {
      //TODO: handle exception
    }
    return id;
  }

  private String[] retrieveNameAndDescription(JsonNode node) {
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

  private boolean retrieveBooleanGPSTrack(JsonNode node) {
    boolean hasGps = true;
    if(node.get("GpsInfo").isNull())
      if(node.get("GpsTrack").isEmpty())
        //if(node.get("GpsPoints").isNull()) 
        hasGps = false;
    return hasGps;
  }

  //getters
  public boolean hasGpsTrack() {
    return this.hasGPSTrack;
  }
 
  public String getId() {
    return this.id;
  }

  public String getRegionId() {
    return this.regionId;
  }

  public List<String> getTypes() {
    return this.types;
  }
}