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
    //name, description and locationInfo parameters may have differente names depending on the language to use
    nameAndDescription.setVariables(languageToUse);
    locationInfo.setVariables(languageToUse);
    
    return new ActivityDescription(this.id, getOdhList(), nameAndDescription.getActivityName(), nameAndDescription.getActivityDescription(), hasGpsTrack(), locationInfo.getName());
  }

  /**
   * 
   * @return List<String> containing all OdhTags for that activity 
   */
  private List<String> getOdhList() {
    List<String> odhTags = new LinkedList<>();
    for (ODHTag odhTag : types) {
      odhTags.add(odhTag.getId());
    }

    return odhTags;
  }

  /**
   * Go throw all the different gps sources and if at least one of them is True, return True
   * @return boolean value wheter or not the activity has gpsTrack
   */
  private boolean hasGpsTrack() {
    if(gpsInfo.isNull())
      if(gpsTrack.isEmpty())
        if(gpsPoints.isNull())
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