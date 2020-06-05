package com.OpenDataHub.parser.support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;

public class GpsInfo {
  
  private List<JsonNode> gpsInfo;

  @JsonCreator
  public GpsInfo(List<JsonNode> gpsInfo) {
    this.gpsInfo = gpsInfo;
  }

  /**
   * 
   * @return return boolean value, true if the {@link #gpsInfo} field is null or empty
   */
  public boolean isNullOrEmpty() {
    return (this.gpsInfo == null || this.gpsInfo.isEmpty());
  }
}