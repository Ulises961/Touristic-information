/**
 * Contains all the inforamtion caming from the {@link AnalysisDataStorage} class
 */
package com.OpenDataHub.analysis;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisResult {
  @JsonProperty("activitiesTypes")
  private Map<String, Long> odhTagAndOccurrence;
  @JsonProperty("trackedActivityIds")
  private List<String> trackedActivitiesId;
  @JsonProperty("regionsWithMostActivities")
  private RegionWithActivities regionWithMostActivities;
  @JsonProperty("regionsWithLeastActivities")
  private RegionWithActivities regionWithLessActivities;

  /**
   * 
   * @param odhTagAndOccurrence
   * @param trackedActivitiesId
   * @param regionWithMostActivities
   * @param regionWithLessActivities
   */
  public AnalysisResult (Map<String, Long> odhTagAndOccurrence,
    List<String> trackedActivitiesId,
    RegionWithActivities regionWithMostActivities,
    RegionWithActivities regionWithLessActivities) {
    this.odhTagAndOccurrence = odhTagAndOccurrence;
    this.trackedActivitiesId = trackedActivitiesId;
    this.regionWithMostActivities = regionWithMostActivities;
    this.regionWithLessActivities = regionWithLessActivities;
  }

  /**
   * @return Map<String,Integer> return the odhTagAndOccurrence
   */
  @JsonProperty("activitiesTypes")
  public Map<String, Long> getOdhTagAndOccurrence() {
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
  public RegionWithActivities getRegionWithMostActivities() {
    return regionWithMostActivities;
  }

  /**
   * @return Map<Integer,List<String>> return the regionWithLessActivities
   */
  public RegionWithActivities getRegionWithLessActivities() {
    return regionWithLessActivities;
  }

 
}