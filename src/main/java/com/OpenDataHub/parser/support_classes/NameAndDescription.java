package support_classes;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class NameAndDescription {
  private String ActivityName;
  private String ActivityDescription;

  
  private Map<String,JsonNode> detail;

  @JsonCreator
  public NameAndDescription(Map<String,JsonNode> detail) { 
    //initialize the map with as keys the languages abbreviations in which their are written
    this.detail = detail;
  }

  public void setVariables(String language) {
    String name = detail.get(language).get("Title").asText();
    this.ActivityName = name;
    String description = detail.get(language).get("BaseText").asText();
    this.ActivityDescription = HtmlTags.cleanTags(description);
  }

  
  // public String toString() {
  //   return this.detail.toString();
  // }

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