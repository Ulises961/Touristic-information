package com.OpenDataHub.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * support class, provide different sample responses
 */
public class FileLoader {

  public enum LoadableFiles
  {
    EMPTY_RESPONSE_SAMPLE("src/test/java/com/OpenDataHub/samples_responses/EmptyInputs.json"),
    DEFAULT_RESPONSE_SAMPLE("src/test/java/com/OpenDataHub/samples_responses/DefaultInputs.json"),
    ANALYSIS_FROM_DEFAULT_SAMPLE("src/test/java/com/OpenDataHub/samples_responses/AnalysisFromDefaultInput.json"),
    NULL_RESPONSE_SAMPLE("src/test/java/com/OpenDataHub/samples_responses/NullInputs.json"),
    ANALYSIS_FILE_PATH("src/main/results/analysis.json"),
    SAMPLE_SCHEMA ("src/test/java/com/OpenDataHub/iotests/sampleschema_of_activity_description.json"),
    GENERATED_TEST_SCHEMA("src/test/java/com/OpenDataHub/iotests/generatedschema_of_activity_description.json");


    private String file;
    LoadableFiles(String file)
    {
      this.file = file;
    }


    @Override
    public String toString() {
      return this.file;
    }
  }



  public static String LoadFile(LoadableFiles response) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(new File(response.toString())));
    String fileContent = "";
    String newLine;

    while ((newLine = reader.readLine()) != null)
      fileContent += newLine;
    reader.close();

    return fileContent;
  }
}