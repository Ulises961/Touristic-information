package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class LocationInfo implements Comparable{
  

  private JsonNode regionInfoObject;

  private String id;
  private String name;

  private int occurrence;

  @JsonCreator
  public LocationInfo(Map<String,JsonNode> map) {
    this.regionInfoObject = map.get("RegionInfo");
  }

  /**
   * 
   * @param language String language tag in which goes through the response
   */
  public void setVariables(String language) {
    this.id = regionInfoObject.get("Id").asText();
    this.name = regionInfoObject.get("Name").get(language).asText();
  }

    /**
     * @param language 
     * @return String return the name
     */
    public String getName(String language) {
        return regionInfoObject.get("Name").get(language).asText();
    }
    
    public String getId(String language) {
      return regionInfoObject.get("Id").asText();
    } 

    public String getId() {
      return this.id;
    }

    public void setId(String newId) {
      this.id = newId;
    } 

    @Override
    public String toString() {
      return "Region Id: " + this.id + "\nRegion Name: " + this.name;
    }

    public void initializeOccurrence() {
      this.occurrence = 1;
    }

    public void incrementOccurrence() {
      this.occurrence++;
    }

    public int getOccurrences() {
      return this.occurrence;
    }

    @Override
    public int compareTo(Object o) {
      LocationInfo obj = (LocationInfo) o;
      return this.occurrence - obj.occurrence;
    }
}