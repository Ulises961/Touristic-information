/**
 * Support class for storing the charachteristics of a single activity provided by the Api.
 * The class aims to store in itself all the data needed for computing the final and clean class
 * {@link #ActivityDescription}. 
 * 
 * {@link #getActivityDescription} retrieve the corrispondent ActivityDescription object
 * for such values stored in that moment.
 * 
 * @author Rigoni Riccardo
 */
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
  private HasLanguage availableLanguages; 
 
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

  /**
   * Returns an {@link #ActivityDescription} object containing the values that describe clearly the Activity
   * @return  return {@link #ActivityDescription}
   * @throws NoLanguageAvailable if the languages in which the description is written does not find any corrispondance in {@link #HasLanguage} preferenceLanguages list.
   */
  public ActivityDescription getActivityDescription() throws NoLanguageAvailable {
    String languageToUse = availableLanguages.getLanguageToUse();
    
    //collect parameters for instantiating the new ActivityDescription object
    List<String> odhTags = getOdhList();
    String activityName = nameAndDescription.getActivityName(languageToUse);
    String activityDescription = nameAndDescription.getActivityName(languageToUse);
    boolean hasGpsTrack = hasGpsTrack();
    String locationName = "";
    String locationId = "";
    try {
      locationName = locationInfo.getName(languageToUse);
      locationId = locationInfo.getId(languageToUse);
  
    } catch (Exception e) {
      e.printStackTrace();;  
    }
    
    return new ActivityDescription(this.id, odhTags, activityName, activityDescription, hasGpsTrack, locationName, locationId);
  }

  /**
   * 
   * @return List<String> containing all OdhTags for that activity 
   */
  private List<String> getOdhList() {
    List<String> odhTags = new LinkedList<>();
    
    //if "null" not contains any value
    if(types != null)
      for (ODHTag odhTag : types) {
        odhTags.add(odhTag.getOdhId());
    }

    return odhTags;
  }

  /**
   * Go throw all the different gps sources and if at least one of them is True, return True
   * 
   * (Checks and manage null inputs)
   * @return boolean value wheter or not the activity has gpsTrack
   */
  private boolean hasGpsTrack() {
    /**
     * if "null" -> null in the json node representing the response
     * else, check if content different from "empty" 
     */
    if(gpsInfo == null || gpsInfo.isEmpty())
      if(gpsTrack == null || gpsTrack.isEmpty())
        if(gpsPoints == null || gpsPoints.isEmpty())
          return false;
    
    return true;
  }

  /**
   * Getter for {@link #id}
   * @return String Activity Id
   */
  public String getId() {
    return this.id;
  }

  

}