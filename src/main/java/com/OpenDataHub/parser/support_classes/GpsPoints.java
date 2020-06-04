package com.OpenDataHub.parser.support_classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsPoints {
  private JsonNode gpsPoints;

  @JsonCreator
  public GpsPoints(JsonNode gpsPoints) {
    this.gpsPoints = gpsPoints;
  }

  //check if the list is null
  public boolean isNull() {
    return (gpsPoints == null);
  }

}