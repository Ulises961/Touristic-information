package com.OpenDataHub.json;

import java.io.IOException;
import java.util.ArrayList;

import com.OpenDataHub.datamanipulation.GenerateAnalysisJson;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;


public class DataManipulation {
  public static void main(final String[] args) throws JsonParseException, JsonMappingException, IOException {
    
    Methods method = new Methods(); //class for support methods

    JsonNode node = method.readFile("input.json", "Items");
    ArrayList<Activity> list = method.getList(node); //retrieve the list of all the Activities

    String outputPath = "src\\main\\results\\";

   method.generateActivityJson(outputPath,list);
    
    //create the Json file containing the output of the analysis
    String analysisPath = "src\\main\\results\\";
    GenerateAnalysisJson analysisJson = new GenerateAnalysisJson();
    analysisJson.generateAnalysisJson(analysisPath, list);
    
  }
}
