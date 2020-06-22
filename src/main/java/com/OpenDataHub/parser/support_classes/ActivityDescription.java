/**
 * Class containing the fields necessary for describing activity 
 * 
 * @author Rigoni Riccardo 
 */
package com.OpenDataHub.parser.support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityDescription 
//implements FileWriteble
{
  private String idActivity;
  private List<String> types;
  private String name;
  private String description;
  private boolean hasGpsTrack;
  private String region;
  private String regionId;

  /**
   * Constructor, accept as parameters all the values needed to fill instance variables
   * @param idActivity
   * @param types
   * @param name
   * @param description
   * @param hasGpsTrack
   * @param region
   * @param regionId
   */
  public ActivityDescription(
    @JsonProperty("id")
    String idActivity,
    @JsonProperty("types")
    List<String> types,
    @JsonProperty("name")
    String name,
    @JsonProperty("description")
    String description,
    @JsonProperty("hasGpsTrack")
    boolean hasGpsTrack,
    @JsonProperty("region")
    String region,
    String regionId
  ) {
    this.idActivity = idActivity;
    this.types = types;
    this.name= name;
    this.description= description;
    this.hasGpsTrack = hasGpsTrack;
    this.region = region;
    this.regionId = regionId;
  }

    /**
     * @return String return the idActivity
     */
    @JsonProperty("id")
    public String getIdActivity() {
        return idActivity;
    }

    /**
     * @return List<String> return the types
     */
    @JsonProperty("types")
    public List<String> getTypes() {
        return types;
    }

    /**
     * @return String return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @return String return the description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @return boolean return the hasGpsTrack
     */
    @JsonProperty("hasGpsTrack")
    public boolean isHasGpsTrack() {
        return hasGpsTrack;
    }

    /**
     * @return String return the region
     */
    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    /**
     * @return String return the regionId
     */
    public String getRegionId() {
        return regionId;
    }
}