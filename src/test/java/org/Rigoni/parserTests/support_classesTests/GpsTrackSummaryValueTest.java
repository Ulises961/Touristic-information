/**
 * class aim to verify that hasGpsTrack == false iff no gps track is available
 */
package org.Rigoni.parserTests.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GpsTrackSummaryValueTest {

  static String emptyResponse = "src\\test\\java\\org\\Rigoni\\samples_responses\\EmptyInputs.json";
  static String nullResponse = "src\\test\\java\\org\\Rigoni\\samples_responses\\NullInputs.json";
  static String defaultResponse = "src/test/java/org/Rigoni/samples_responses/DefaultInputs.json";
  
  /**
   * Support method used for loading files content
   * 
   * @param filePath 
   * @return return String content
   * @throws IOException 
   */
  
  private static String loadFileContent(String filePath) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
    String fileContent = "";
    String newLine;

    while ((newLine = reader.readLine()) != null)
      fileContent += newLine;
    reader.close();

    return fileContent;

  }

  @DisplayName("False if values null")
  @Test
  void checkFalseOnNull() throws NoLanguageAvailable, IOException {

    String input = loadFileContent(nullResponse);
    
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
    String inputEmpty = loadFileContent(emptyResponse);
    

    /*
     * generated with "empty" content:
     * objects -> {}
     * arrays -> [] 
     */
    Activity shouldGpsFalse = ObjectMapperClass.mapper.readValue(inputEmpty, Activity.class);
    
    ActivityDescription activityDescription = shouldGpsFalse.getActivityDescription();

    boolean hasGpsTrack = activityDescription.isHasGpsTrack();
    
    //since all values representing gps tracks has no content, value should be false (no kind of tracking)
    assertFalse(hasGpsTrack);
  }

  @DisplayName("True if any kind of gps is provided")
  @Test
  void checkTrueIfGpsProvided() throws NoLanguageAvailable, IOException {
    String inputWithGpsTracks = loadFileContent(defaultResponse);
    

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