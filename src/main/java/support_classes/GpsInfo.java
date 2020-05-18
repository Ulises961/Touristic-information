package support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsInfo {
  
  private List<JsonNode> gpsInfo;

  @JsonCreator
  public GpsInfo(List<JsonNode> gpsInfo) {
    this.gpsInfo = gpsInfo;
  }

  //method for checking if it is empty (?)
  // return true if the list is empty -> null in the JsonNode
  public boolean isNull() {
    return (this.gpsInfo == null);
  }
}