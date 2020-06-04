package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class LocationInfo {
  
  private JsonNode regionInfo;

  private String id;
  private String name;

  @JsonCreator
  public LocationInfo(Map<String,JsonNode> locationInfo) {
    this.regionInfo = locationInfo.get("RegionInfo");
  }

  public void setVariables(String language) {
    this.id = regionInfo.get("Id").asText();
    this.name = regionInfo.get("Name").get(language).asText();
  }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }
  
    @Override
    public String toString() {
      return "Region Id: " + this.id + "\nRegion Name: " + this.name;
    }
}