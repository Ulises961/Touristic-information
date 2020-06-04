package support_classes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsTrack {
  private List<JsonNode> gpsTrack;
  

  @JsonCreator
  public GpsTrack(List<JsonNode> gpsTrack) {
    if(gpsTrack != null)
      this.gpsTrack = gpsTrack;
    else 
      this.gpsTrack = new ArrayList<JsonNode>();
  }

  //check if the list is empty
  public boolean isEmpty() {
    return this.gpsTrack.isEmpty();
  }
}