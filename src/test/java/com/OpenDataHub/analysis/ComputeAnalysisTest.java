/**
 * Class for verify correct behaviour of the analysis procedure under different kinds of inputs
 */
package com.OpenDataHub.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.OpenDataHub.helper.FileLoader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ComputeAnalysis test")
public class ComputeAnalysisTest {

  @DisplayName("Check return false when null list as input")
  @Test
  void start_method_return_false_NULL_List_Test() throws IOException {
    List<ActivityDescription> inputList = null;

    Optional<Boolean> isDoneCorrectly = ComputeAnalysis.start(inputList);

    assertEquals(false, isDoneCorrectly.get());
  }

  @DisplayName("Check return false when empty list as input ")
  @Test
  void start_method_return_false_EMPTY_List_Test() throws IOException {
    List<ActivityDescription> inputList = new LinkedList<>();

    Optional<Boolean> isDoneCorrectly = ComputeAnalysis.start(inputList);

    assertEquals(false, isDoneCorrectly.get());

  }

  @DisplayName("Check return true when list not empty or not null")
  @Test
  void start_method_return_true_with_elements_Test() throws IOException, NoLanguageAvailable {
    List<ActivityDescription> inputList = new LinkedList<>();

    String defaultActivityAsString = FileLoader.LoadFile(FileLoader.LoadableFiles.DEFAULT_RESPONSE_SAMPLE);
    Activity defaultActivityObject = ObjectMapperClass.mapper.readValue(defaultActivityAsString, Activity.class);
    ActivityDescription defaultActivityDescription = defaultActivityObject.getActivityDescription();

    inputList.add(defaultActivityDescription);

    ComputeAnalysis.start(inputList);


    byte[] sampleAnalysis = FileLoader.LoadFile(FileLoader.LoadableFiles.ANALYSIS_FROM_DEFAULT_SAMPLE).getBytes();
    byte[] genertedAnalysis = FileLoader.LoadFile(FileLoader.LoadableFiles.ANALYSIS_FILE_PATH).getBytes();

    System.out.println(sampleAnalysis.length);
    System.out.println(genertedAnalysis.length);

    boolean areEquals = true;
    for(int i = 0; i < sampleAnalysis.length; i++) {
      if(sampleAnalysis[i] != genertedAnalysis[i])
        areEquals = false;
    }

    assertEquals(areEquals, true);
  }
}