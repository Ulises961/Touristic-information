package com.OpenDataHub.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.OpenDataHub.parser.support_classes.ActivityDescription;

public class AnalysisSupportMethods {

  public static List<String> extractODHTags(List<ActivityDescription> inputList)  {
    List<String> tagList = new LinkedList<>();
      for (ActivityDescription activityDescription : inputList) {
        tagList.addAll(activityDescription.getTypes());
      } 
    return tagList;
  }

  public static List<String> extractRegionIds(List<ActivityDescription> inputList) {
    List<String> locationInfoList = new LinkedList<>();

    for (ActivityDescription activityDescription : inputList) {
      locationInfoList.add(activityDescription.getRegionId());
    }
    return locationInfoList;
  }

  public static <K,V> Map<K,List<V>> cleanNullValues(Map<K,List<V>> inputMap) { 
    inputMap.entrySet().stream().forEach((entry) -> entry.getValue().remove(null));

    return inputMap;
  }
}