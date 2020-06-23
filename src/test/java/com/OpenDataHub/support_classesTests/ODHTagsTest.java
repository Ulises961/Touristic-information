/**
 * Test class for {@link #ODHTag} class.
 */
package com.OpenDataHub.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


@DisplayName("ODHTags Test")
public class ODHTagsTest {

  static String emptyResponse = "src/test/java/com/OpenDataHub/samples_responses/EmptyInputs.json";
  static String nullResponse = "src/test/java/com/OpenDataHub/samples_responses/NullInputs.json";
  static String defaultResponse = "src/test/java/com/OpenDataHub/samples_responses/DefaultInputs.json";

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
  
  @DisplayName("Check that not fail when list is empty")
  @Test
  void checkEmptyODHTagsList() throws IOException, NoLanguageAvailable {
    String input = loadFileContent(emptyResponse);
    
    //activity generated with odhTags == [] (empty list)
    Activity shouldNotHaveTypes = ObjectMapperClass.mapper.readValue(input, Activity.class);

    ActivityDescription activityDescription = shouldNotHaveTypes.getActivityDescription();
    boolean hasNotOdhTags = activityDescription.getTypes().isEmpty();

    //since input list empty, no tags should be read
    assertTrue(hasNotOdhTags);
  }

  @DisplayName("Check that not fail when list is null")
  @Test
  void checkNullODHTagsList() throws IOException, NoLanguageAvailable {
    String input = loadFileContent(nullResponse);
    
    //activity generated with odhTags == null
    Activity shouldNotHaveTypes = ObjectMapperClass.mapper.readValue(input, Activity.class);

    ActivityDescription activityDescription = shouldNotHaveTypes.getActivityDescription();
    boolean hasNotOdhTags = activityDescription.getTypes().isEmpty();
    
    //since input list null, no tags should be read
    assertTrue(hasNotOdhTags);
  }

  @DisplayName("Check retrieve correct ODHTags")
  @Test
  void checkCorrectlyRetrieving() throws IOException, NoLanguageAvailable {
    String input = loadFileContent(defaultResponse);
    
    //activity generated with odhTags == null
    Activity shouldNotHaveTypes = ObjectMapperClass.mapper.readValue(input, Activity.class);

    ActivityDescription activityDescription = shouldNotHaveTypes.getActivityDescription();
    int odhTagsListSize = activityDescription.getTypes().size();
    
    //check that correctly read the tags
    assertEquals(2, odhTagsListSize);
  }
}