package com.OpenDataHub.analysis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionWithLessActivities {
  @JsonProperty("numberOfActivities")
  private int occurrence;
  @JsonProperty("regionIds")
  private List<String> ids;

  public RegionWithLessActivities(Map<Integer, List<String>> inputMap) {
    Entry<Integer, List<String>> entry = inputMap.entrySet().iterator().next();
      Integer minValue = entry.getKey();
      List<String> list = entry.getValue();

      this.occurrence = minValue;
      this.ids = list;
    }

  /**
   * 
   * @return
   */
  @JsonProperty("numberOfActivities")
  public int getOccurrences() {
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

  @Override
  public String toString() {
    return String.format("LESS, %d\n%s", this.occurrence, this.ids);
  }
}