package com.OpenDataHub.json;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

import support_classes.NameAndDescription;
import support_classes.NoLanguageAvailable;
import support_classes.ActivityDescription;
import support_classes.GpsInfo;
import support_classes.GpsPoints;
import support_classes.GpsTrack;
import support_classes.HasLanguage;
import support_classes.LocationInfo;
import support_classes.ODHTag;

public class Activity {
  
  @JsonSetter("Id")
  private String id;
 
  @JsonSetter("ODHTags")
  private List<ODHTag> types;

  @JsonSetter("Detail")
  private NameAndDescription nameAndDescription;

  @JsonSetter("HasLanguage")
  private HasLanguage language; //inside contains field "language" that has the preferred...  this could thorow an exception 
 
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