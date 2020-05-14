package com.OpenDataHub.main;

import java.util.ArrayList;

import com.OpenDataHub.datamanipulation.Activity;
import com.OpenDataHub.datamanipulation.Methods;
import com.OpenDataHub.json.GenerateAnalysisJson;
import com.OpenDataHub.requests.Retriever;
import com.fasterxml.jackson.databind.JsonNode;

public class Main {
  public static void main(String[] args) {
    System.out.println("From my branch");

    Retriever retriever = new Retriever();
    String requestString = retriever.checkAndRequest();

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