/**
 * Contains all the inforamtion caming from the {@link AnalysisDataStorage} class
 */
package com.OpenDataHub.analysis;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisOutput {
  @JsonProperty("activitiesTypes")
  private Map<String, Integer> odhTagAndOccurrence;
  @JsonProperty("trackedActivityIds")
  private List<String> trackedActivitiesId;
  @JsonProperty("regionsWithMostActivities")
  private RegionWithMostActivities regionWithMostActivities;
  @JsonProperty("regionsWithLeastActivities")
  private RegionWithLessActivities regionWithLessActivities;

  /**
   * 
   * @param odhTagAndOccurrence
   * @param trackedActivitiesId
   * @param regionWithMostActivities
   * @param regionWithLessActivities
   */
  public AnalysisOutput (Map<String, Integer> odhTagAndOccurrence,
    List<String> trackedActivitiesId,
    RegionWithMostActivities regionWithMostActivities,
    RegionWithLessActivities regionWithLessActivities) {
    this.odhTagAndOccurrence = odhTagAndOccurrence;
    this.trackedActivitiesId = trackedActivitiesId;
    this.regionWithMostActivities = regionWithMostActivities;
    this.regionWithLessActivities = regionWithLessActivities;
  }

  /**
   * @return Map<String,Integer> return the odhTagAndOccurrence
   */
  @JsonProperty("activitiesTypes")
  public Map<String, Integer> getOdhTagAndOccurrence() {
    return odhTagAndOccurrence;
  }

  /**
   * @return List<String> return the trackedActivitiesId
   */
  @JsonProperty("trackedActivityIds")
  public List<String> getTrackedActivitiesId() {
    return trackedActivitiesId;
  }

  /**
   * @return Map<Integer,List<String>> return the regionWithMostActivities
   */
  public RegionWithMostActivities getRegionWithMostActivities() {
    return regionWithMostActivities;
  }

  /**
   * @return Map<Integer,List<String>> return the regionWithLessActivities
   */
  public RegionWithLessActivities getRegionWithLessActivities() {
    return regionWithLessActivities;
  }

 
}