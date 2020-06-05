/**
 * Support class for storing odhTag id and it's occurences for computing the analysis 
 */
package com.OpenDataHub.parser.support_classes;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ODHTag {
  private int occurrences;

  @JsonSetter("Id")
  private String ohdId;

  public String getOdhId() {
    return this.ohdId;
  }

  /**
   * 
   * @return return int, how many times the tag were present in the response
   */
  public int getOccurrences() {
    return this.occurrences;
  }

  /**
   * When the tag cames the first time, initialize the occurence to 1
   */
  public void initOccurrence() {
    this.occurrences = 1;
  }

  /**
   * Increment {@link #occurrences} every time the tag being seen anothe time 
   */
  public void incrementOccurrences() {
    this.occurrences = this.occurrences + 1;
  }

  public String toString() {
    return this.ohdId;
  }

}