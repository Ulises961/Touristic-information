package com.OpenDataHub.samples_responses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * support class, provide different sample responses
 */
public class SampleResponses {

  public static String EMPTY_RESPONSE_SAMPLE = "src/test/java/com/OpenDataHub/samples_responses/EmptyInputs.json";
  public static String NULL_RESPONSE_SAMPLE = "src/test/java/com/OpenDataHub/samples_responses/NullInputs.json";
  public static String DEFAULT_RESPONSE_SAMPLE = "src/test/java/com/OpenDataHub/samples_responses/DefaultInputs.json";
  public static String ANALYSIS_FROM_DEFAULT_SAMPLE = "src/test/java/com/OpenDataHub/samples_responses/AnalysisFromDefaultInput.json";

  public static String loadSampleResponse(String response) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(new File(response)));
    String fileContent = "";
    String newLine;

    while ((newLine = reader.readLine()) != null)
      fileContent += newLine;
    reader.close();

    return fileContent;
  }
}