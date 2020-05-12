package com.OpenDataHub.datamanipulation;

import java.util.List;
import java.util.Map;

/*  ANALYSIS 
*   when called "toPrettyString", generate the output as in the slides
*/
public class Analysis {
  private Map<String,Integer> activitiesTypes;
  private List<String> trackedActivityIds;
  private RegionId regionsWithMostActivities; 
  private RegionId regionsWithLessActivities;

  public Analysis(Map<String,Integer> activitiesTypes, List<String> trackedActivityIds, RegionId regionsWithMostActivities, RegionId regionsWithLessActivities) {
    this.activitiesTypes = activitiesTypes;
    this.trackedActivityIds = trackedActivityIds;
    this.regionsWithMostActivities = regionsWithMostActivities;
    this.regionsWithLessActivities = regionsWithLessActivities;
  }

}