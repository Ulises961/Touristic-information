/**
 * Contains name field and description field
 */
package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class NameAndDescription {
  
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
   * @return String return the ActivityName
   */
  public String getActivityName(String language) {
    return detailObject.get(language).get("Title").asText();
  }

  /**
   * @return String return the ActivityDescription
   */
  public String getActivityDescription(String language) {
    String description = detailObject.get(language).get("BaseText").asText();
    return HtmlTags.cleanTags(description);
  }
}