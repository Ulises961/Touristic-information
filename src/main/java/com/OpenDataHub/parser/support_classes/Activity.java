package com.OpenDataHub.parser.support_classes;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Activity {
  
  @JsonSetter("Id")
  private String id;
 
  @JsonSetter("ODHTags")
  private List<ODHTag> types;

  @JsonSetter("Detail")
  private NameAndDescription nameAndDescription;

  @JsonSetter("HasLanguage")
  private HasLanguage language; 
 
  @JsonSetter("LocationInfo")
  private LocationInfo locationInfo;
  
  @JsonSetter("GpsInfo")
  private GpsInfo gpsInfo;

  @JsonSetter("GpsTrack")
  private GpsTrack gpsTrack;

  @JsonSetter("GpsPoints")
  private GpsPoints gpsPoints;

  public String toString() {
    return this.id + "\n" + this.types + "\n"  + nameAndDescription + "\n" + locationInfo;
  }

  //contains a method for instantiating inside itself a new ActivityDescription class, this will returned when description of the class
  public ActivityDescription getActivityDescription() throws NoLanguageAvailable {
     nameAndDescription.setVariables(language.getUtilizedLanguage());
     locationInfo.setVariables(language.getUtilizedLanguage());
    
    return new ActivityDescription(this.id, getOdhList(), nameAndDescription.getActivityName(), nameAndDescription.getActivityDescription(), hasGpsTrack(), locationInfo.getName());
  }

  private List<String> getOdhList() {
    List<String> list = new LinkedList<>();
    for (ODHTag odhTag : types) {
      list.add(odhTag.getId());
    }

    return list;
  }

  private boolean hasGpsTrack() {
    if(gpsInfo.isNull())
      if(gpsTrack.isEmpty())
        if(gpsPoints.isNull())
          return false;
    
    return true;
  }

  public String getId() {
    return this.id;
  }
}