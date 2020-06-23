package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class LocationInfo {
  
  /**
   * Coming from the api response
   */
  private JsonNode regionInfoObject;

  @JsonCreator
  public LocationInfo(Map<String,JsonNode> map) {
    this.regionInfoObject = map.get("RegionInfo");
  }

  /**
   * @param language 
   * @return String return the name
   */
  public String getName(String language) {

    String regionName = null;
    //use try-catch block because field received from the Api could be null
    if(isNotResponseNull()) {
      try {
        JsonNode jsonName = regionInfoObject.get("Name").get(language);
        regionName =  jsonName.asText(); 
      } catch (NullPointerException e) { //if not possible to read any value from the JsonNode
      }
    }
    
    return regionName;
  }
  
  public String getId(String language) {
    String regionId = null;

    if(isNotResponseNull()) {
      try {
        JsonNode jsonId = regionInfoObject.get("Id");
        regionId = jsonId.asText(); 
      } catch (NullPointerException e) { //if not possible to read any value from the JsonNode
      }
    }

    return regionId;
  } 

  private boolean isNotResponseNull() {
    return this.regionInfoObject != null;
  }
}