package support_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * FINAL CLASS CONTAINING ONLY THE VALUES THAT HAS TO BE PRINTED
 */

public class ActivityDescription 
// implements FileWriteble
{
  private String idActivity;
  private List<String> types;
  private String name;
  private String description;
  private boolean hasGpsTrack;
  private String region;

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
    String region
  ) {
    this.idActivity = idActivity;
    this.types = types;
    this.name= name;
    this.description= description;
    this.hasGpsTrack = hasGpsTrack;
    this.region = region;
  }

  //getter for the property 
  

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

}