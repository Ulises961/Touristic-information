/**
 * Contains name field and description field
 */
package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class NameAndDescription {
  private String ActivityName;
  private String ActivityDescription;

  
  private Map<String,JsonNode> detailObject;

  /**
   * Constructor
   * @param detailObject containing the description identified with "detail" tag from the response
   */
  @JsonCreator
  public NameAndDescription(Map<String,JsonNode> detailObject) { 
    this.detailObject = detailObject;
  }

  /**
   * Set name and description using the language parameter
   * @param language language tag used for going throw the Api response
   */
  public void setVariables(String language) {
    String name = detailObject.get(language).get("Title").asText();
    this.ActivityName = name;
    String description = detailObject.get(language).get("BaseText").asText();
    this.ActivityDescription = HtmlTags.cleanTags(description);
  }

    /**
     * @return String return the ActivityName
     */
    public String getActivityName() {
        return ActivityName;
    }

    /**
     * @return String return the ActivityDescription
     */
    public String getActivityDescription() {
        return ActivityDescription;
    }

    @Override
    public String toString() {
      return "Name: " + this.ActivityName + "\nDecription: " + this.ActivityDescription;
    }

}