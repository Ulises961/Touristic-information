package support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsTrack {
  private List<JsonNode> gpsTrack;
  

  @JsonCreator
  public GpsTrack(List<JsonNode> gpsTrack) {
    this.gpsTrack = gpsTrack;
  }

  //check if the list is empty
  public boolean isEmpty() {
    return this.gpsTrack.isEmpty();
  }
}