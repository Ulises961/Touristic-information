package com.OpenDataHub.parser.support_classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsPoints {
  private JsonNode gpsPoints;

  @JsonCreator
  public GpsPoints(JsonNode gpsPoints) {
    this.gpsPoints = gpsPoints;
  }

 /**
   * 
   * @return return boolean value, true if the {@link #gpsPoints} size is equal 0 
   * Means that no gpsPoints are provided for that Activity
   */
  public boolean isEmpty() {
    return gpsPoints.size() == 0;
  }


}