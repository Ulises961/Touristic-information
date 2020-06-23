/**
 * Contains name field and description field
 */
package com.OpenDataHub.parser.support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class NameAndDescription {

  //used when calling the toString method
  private String lastUtilizedLanguage;
  
  private Map<String,JsonNode> detailObject;

  /**
   * Constructor
   * @param detailObject containing the description identified with "detail" tag from the response
   */
  @JsonCreator
  public NameAndDescription(Map<String,JsonNode> detailObject) { 
    this.detailObject = detailObject;
    this.lastUtilizedLanguage = "en"; //by default at english
  }

  /**
   * @return String return the ActivityName
   */
  public String getActivityName(String language) {
    this.lastUtilizedLanguage = language;
    return detailObject.get(language).get("Title").asText();
  }

  /**
   * @return String return the ActivityDescription
   */
  public String getActivityDescription(String language) {
    this.lastUtilizedLanguage = language;
    String description = detailObject.get(language).get("BaseText").asText();
    return HtmlTags.cleanTags(description);
  }

  /**
   * if the last language used, return the description, otherwise ask for selecting a language
   */
  @Override
  public String toString() {
    if(this.lastUtilizedLanguage != null)
      return "Name: " + getActivityName("en") + "\nDecription: " + HtmlTags.cleanTags(getActivityDescription(this.lastUtilizedLanguage));
    else
      return "Please before specify a language to use!";
  }

}