package com.OpenDataHub.main;

import java.io.IOException;
import java.util.ArrayList;

import com.OpenDataHub.json.Activity;
import com.OpenDataHub.json.Methods;
// import com.OpenDataHub.datamanipulation.GenerateAnalysisJson;
import com.OpenDataHub.requests.Loaderpt2;
import com.OpenDataHub.requests.Retriever;
import com.fasterxml.jackson.databind.JsonNode;

public class Main {
  public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
    //cleaner main file... only type of exceptions, when generated helpful to read the message
    try {
     String file_path = "./src/main/resources/requests.txt";
     int pageSize = Loaderpt2.retrieveInput(file_path); //could throw FileFormat o FileNotfound Ecxeption () 
 
     Retriever retriever = new Retriever(); //with no parameters, default values 
     retriever.setPageSize(pageSize);
 
     String jsonInput = retriever.makeRequest();
    } 
    catch(NumberFormatException e) {
      System.out.println("Error while reading the input.txt file, check if only a positive integer number has been inserted");
      System.out.println(e.getMessage());
    }
    
     Methods method = new Methods(); //class for support methods

     JsonNode node = method.readFile("input.json", "Items");
     ArrayList<Activity> list = method.getList(node); //retrieve the list of all the Activities
    
     
  //   String outputPath = "src\\main\\results\\";

  //  method.generateActivityJson(outputPath,list);
    
  //   //create the Json file containing the output of the analysis
  //   String analysisPath = "src\\main\\results\\";
  //   GenerateAnalysisJson analysisJson = new GenerateAnalysisJson();
  //   analysisJson.generateAnalysisJson(analysisPath, list);
  }
}