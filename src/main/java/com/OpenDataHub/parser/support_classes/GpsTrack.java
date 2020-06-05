package com.OpenDataHub.parser.support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsTrack {
  private List<JsonNode> gpsTrack;
  

  @JsonCreator
  public GpsTrack(List<JsonNode> gpsTrack) {
    this.gpsTrack = gpsTrack;
  }

  /**
   * 
   * @return return boolean value, true if the {@link #gpsTrack} field is null or empty
   */
  public boolean isNullOrEmpty() {
    return this.gpsTrack == null || this.gpsTrack.isEmpty();
  }
}