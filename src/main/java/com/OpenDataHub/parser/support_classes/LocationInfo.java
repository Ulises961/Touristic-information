package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class LocationInfo implements Comparable{
  
  //from input response could be null
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

      //use try-catch block because field received from the Api could be null
      if(this.regionInfoObject == null)
        return null;
      else {
        try {
          JsonNode jsonName = regionInfoObject.get("Name").get(language);
          return jsonName.asText(); 

        } catch (NullPointerException e) { //if not possible to read any value from the JsonNode
          return null;
        }
      }
    }
    
    public String getId(String language) {
      if(this.regionInfoObject == null) 
        return null;
      else {
        try {
          JsonNode jsonId = regionInfoObject.get("Id");
          return jsonId.asText(); 

        } catch (NullPointerException e) { //if not possible to read any value from the JsonNode
          return null;
        }
      }
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