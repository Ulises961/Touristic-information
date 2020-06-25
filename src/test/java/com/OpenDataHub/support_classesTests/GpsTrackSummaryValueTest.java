/**
 * class aim to verify that hasGpsTrack == false iff no gps track is available
 */
package com.OpenDataHub.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.OpenDataHub.helper.TFileLoader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GpsTrackSummaryValueTest {

  @DisplayName("False if values null")
  @Test
  void checkFalseOnNull() throws NoLanguageAvailable, IOException {

    String input = TFileLoader.LoadFile(TFileLoader.LoadableFiles.NULL_RESPONSE_SAMPLE);
    
    //activity generated with gpsValues == null
    Activity shouldGpsFalse = ObjectMapperClass.mapper.readValue(input, Activity.class);

    ActivityDescription activityDescription = shouldGpsFalse.getActivityDescription();
    boolean hasGpsTrack = activityDescription.isHasGpsTrack();
    
    //since all values representing gps tracks are null, value should be false (no kind of tracking)
    assertFalse(hasGpsTrack);
    
  }

  @DisplayName("False if values empty")
  @Test
  void checkFalseOnEmpty() throws NoLanguageAvailable, IOException {
    String input = TFileLoader.LoadFile(TFileLoader.LoadableFiles.EMPTY_RESPONSE_SAMPLE);

    /*
     * generated with "empty" content:
     * objects -> {}
     * arrays -> [] 
     */
    Activity shouldGpsFalse = ObjectMapperClass.mapper.readValue(input, Activity.class);
    
    ActivityDescription activityDescription = shouldGpsFalse.getActivityDescription();

    boolean hasGpsTrack = activityDescription.isHasGpsTrack();
    
    //since all values representing gps tracks has no content, value should be false (no kind of tracking)
    assertFalse(hasGpsTrack);
  }

  @DisplayName("True if any kind of gps is provided")
  @Test
  void checkTrueIfGpsProvided() throws NoLanguageAvailable, IOException {
    String inputWithGpsTracks = TFileLoader.LoadFile(TFileLoader.LoadableFiles.DEFAULT_RESPONSE_SAMPLE);
    

    /*
     * generated with "empty" content:
     * objects -> {}
     * arrays -> [] 
     */
    Activity shouldGpsTrue = ObjectMapperClass.mapper.readValue(inputWithGpsTracks, Activity.class);
    
    ActivityDescription activityDescription = shouldGpsTrue.getActivityDescription();

    boolean hasGpsTrack = activityDescription.isHasGpsTrack();
    
    //since all values representing gps tracks has no content, value should be false (no kind of tracking)
    assertTrue(hasGpsTrack);
  }
}