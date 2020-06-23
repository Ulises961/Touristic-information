/**
 * Support class for storing odhTag id and it's occurences for computing the analysis 
 */
package com.OpenDataHub.parser.support_classes;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ODHTag {
  
  @JsonSetter("Id")
  private String ohdId;

  public String getOdhId() {
    return this.ohdId;
  }

  public void setOdhId(String newId) {
    this.ohdId = newId;
  }

}