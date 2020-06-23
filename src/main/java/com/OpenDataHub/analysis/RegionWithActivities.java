package com.OpenDataHub.analysis;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionWithActivities {
  @JsonProperty("numberOfActivities")
  private Long occurrence;
  @JsonProperty("regionIds")
  private List<String> ids;

  public RegionWithActivities(Long occurrences, List<String> ids) {
      this.occurrence = occurrences;
      this.ids = ids;
    }

  /**
   * 
   * @return
   */
  @JsonProperty("numberOfActivities")
  public Long getOccurrences() {
    return this.occurrence;
  }

  /**
   * 
   * @return
   */
  @JsonProperty("regionIds")
  public List<String> getIdList() {
    return this.ids;
  }

}