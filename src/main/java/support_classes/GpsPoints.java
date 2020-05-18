package support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsPoints {
  private List<JsonNode> gpsPoints;

  @JsonCreator
  public GpsPoints(List<JsonNode> gpsPoints) {
    this.gpsPoints = gpsPoints;
  }

  //check if the list is null
  public boolean isNull() {
    return (gpsPoints == null);
  }

}