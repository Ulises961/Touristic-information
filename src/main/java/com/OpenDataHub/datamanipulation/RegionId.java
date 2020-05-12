package com.OpenDataHub.datamanipulation;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/*
* REGIONID store the number of occurrences that a particular id String has to have
* used for storing all the ids that has max and min occurrence
*/

public class RegionId {
  private int numberOfActivitiese;
  private List<String> regionIds;

  public RegionId(Map<String,Integer> regionOccurrences, int flag) {
    /* 
    * flag variable means if we want to compute the minimum value occurences or the maximum one 
    */

    switch(flag) {
      case 0:
        int minValue = computeMinValue(regionOccurrences);
        this.regionIds = generateListIds(regionOccurrences.entrySet(), minValue);
        this.numberOfActivitiese = minValue;
        break;
      case 1:
        int maxValue = computeMaxValue(regionOccurrences);
        this.regionIds = generateListIds(regionOccurrences.entrySet(), maxValue);
        this.numberOfActivitiese = maxValue;
        break;
      default: 
        return;
    }
  }

  private int computeMinValue(Map<String,Integer> occurrences) {
    int minValue = Integer.MAX_VALUE;
    Collection<Integer> values = occurrences.values();
    for (Integer integer : values) {
      if (integer < minValue)
      minValue = integer; 
    }
    return minValue;
  }

  private int computeMaxValue(Map<String,Integer> occurrences) {
    int maxValue = -1;
    Collection<Integer> values = occurrences.values();
    for (Integer integer : values) {
      if(integer > maxValue)
        maxValue = integer;
    }
    return maxValue;
  }

  private List<String> generateListIds(Set<Entry<String,Integer>> entries, int parameter) {
    //Store in a Map all the values 
    List<String> idsSameOccurence = new LinkedList<String>();

    for (Entry<String,Integer> entry : entries) {
      if(entry.getValue() == parameter)
        idsSameOccurence.add(entry.getKey());
    }
    return idsSameOccurence;
  }

  
}