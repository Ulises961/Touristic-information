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

  // public getName(String language) {
  //   String name = ObjectMapperClass.mapper.get
  // }
}