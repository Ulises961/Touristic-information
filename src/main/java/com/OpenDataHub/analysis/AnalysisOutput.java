/**
 * Contains all the inforamtion caming from the {@link AnalysisDataStorage} class
 */
package com.OpenDataHub.analysis;

import java.util.Map;

public class AnalysisOutput {
  
  private Map<String,Integer> odhTagAndOccurrence;
  
  public AnalysisOutput(Map<String,Integer> odhTagAndOccurrence) {
    this.odhTagAndOccurrence = odhTagAndOccurrence;
  }
}