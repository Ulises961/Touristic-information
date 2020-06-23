/**
 * Main class, containing the main method for running the application.
 * 
 * @author Rigoni Riccardo
 */
package com.OpenDataHub.main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.OpenDataHub.analysis.AnalysisDataStorage;
import com.OpenDataHub.analysis.AnalysisOutput;
import com.OpenDataHub.analysis.AnalysisSupportMethods;
import com.OpenDataHub.analysis.RegionWithLessActivities;
import com.OpenDataHub.analysis.RegionWithMostActivities;
import com.OpenDataHub.fileio.FileProcessor;
import com.OpenDataHub.fileio.JsonFile;
import com.OpenDataHub.parser.Parser;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.OpenDataHub.requests.RequestSetter;
import com.OpenDataHub.requests.SharedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.OpenDataHub.runnable.SaveActivityJson;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

  /**
   * Main method is divided into ? major parts.
   * 1) reading from input file how many Activitys should be retrieved from the Api
   * 2) make requests for getting the resposne
   * 3) parse the response
   * 4) compute the analysis
   * 5) store Activity's description into .json files
   * @param args
   */
  public static void main(String[] args) {

    Logger logger = LogManager.getRootLogger();

    String url = "http://tourism.opendatahub.bz.it/api/Activity";

    int activitiesPerPage = 10;
    int activityType = 1023;
    Integer seed = 1; 

    //number read from the requests.txt input file
    String fileInputPath = "src\\main\\resources\\requests.txt";
    int requestedActivities = new FileProcessor(fileInputPath).getIntegerFromFile();

    //check if the correct input
    if(requestedActivities <= 0) {
      logger.fatal(String.format("Error while reading from the requests.txt input file (%d)", requestedActivities));
      return;
    }

    //set parameters and makes the requests
    List<FutureTask<StringBuilder>> list;
    try {
      RequestSetter r = new RequestSetter(url, activitiesPerPage, activityType, seed, requestedActivities);
      list = r.startThreads();
    } catch (Exception e) {
      return;
    }
    
    //sharedList class will manage to retrieve resposnes while available from the apis
    SharedList.addResponsesList(list);
    
    try {
      //test with no connection

      String nextResponse = SharedList.getNewElement();
      AnalysisDataStorage.initializeData();
    
      //-1 -> no more elements to retrieve
    while(nextResponse != "-1") {
        // generate ActivityDescriptions list from the api response
        logger.debug("Iterate through a new response");

        List<ActivityDescription> toBeSavedAndAnalized = Parser.getActivityDescriptionList(nextResponse);

       
        //update analysis odhTags
        List<String> odhTagsExtracted = AnalysisSupportMethods.extractODHTags(toBeSavedAndAnalized); 
        AnalysisDataStorage.updateODHTagsOccurrences(odhTagsExtracted);

        //update analysis activity per region
        List<String> regionIds = AnalysisSupportMethods.extractRegionIds(toBeSavedAndAnalized);
        AnalysisDataStorage.updateRegionIds(regionIds);
        
        //update tracked activities
        AnalysisDataStorage.updateTrackedActivities(toBeSavedAndAnalized);

        //save files
        Thread saveDescriptions = new Thread(new SaveActivityJson(toBeSavedAndAnalized));
        saveDescriptions.start();

        //new available response
        nextResponse = SharedList.getNewElement();  
      }
    } 
    catch (ExecutionException e) {
      logger.fatal("Problems while retrieving responde from the future task");
      return;
    }
    catch (InterruptedException e) {
      logger.fatal(e.getMessage());
      return;
    }

    //here generate a new FinalAnalysisClass
    Map<String,Integer> odhTagAndOccurrence = AnalysisDataStorage.collectTagsWithOccurrence();
    List<String> trackedActivitiesId = AnalysisDataStorage.getTrackedActivities();
    RegionWithMostActivities regionWithMostActivities = AnalysisDataStorage.getRegionWithMostActivities();
    RegionWithLessActivities regionWithLessActivities = AnalysisDataStorage.getRegionWithLessActivities();

    logger.debug(odhTagAndOccurrence);
    logger.debug(trackedActivitiesId + "\t" + trackedActivitiesId.size());
    logger.debug(regionWithLessActivities);
    logger.debug(regionWithMostActivities);
    
    AnalysisOutput analysisOutput = new AnalysisOutput(odhTagAndOccurrence, trackedActivitiesId, regionWithMostActivities, regionWithLessActivities);

    String fileName = "src\\main\\results\\" + "analysis" + ".json";
    String fileContent;
    JsonFile analysisFile;

    try {
      fileContent = ObjectMapperClass.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(analysisOutput);  
      analysisFile = new JsonFile(fileName, fileContent);

      analysisFile.Save();
    } 
    catch (JsonProcessingException e) {
      logger.error("Cannot be able to retrieve analysis file content from the class");
      return;
    } 
    catch(IOException e) {
      logger.error("Cannot be able to save the analysis.json");
      return;
    }
    
    logger.info("Execution terminated, bye bye!");
    


    
//     String jsonInput;
//     String file_path = "./src/main/resources/requests.txt";
//     String output_description_path = "./src/main/results/";
//     ActivityDescription[] activityDescriptionList;
//     // cleaner main file... only type of exceptions, when generated helpful to read
//     // the message
//     System.out.println("Hi Ulises now I'll use your classes <3");
//     try {
      
//       int pageSize = Loaderpt2.retrieveInput(file_path); // could throw FileFormat o FileNotfound Ecxeption ()

//       Retriever retriever = new Retriever(); // with no parameters, default values
//       retriever.setPageSize(pageSize);

//       jsonInput = retriever.makeRequest();
//     } catch (NumberFormatException e) {
//       System.out.println(
//           "Error while reading the requests.txt file, check if only a positive integer number has been inserted");
//       System.out.println(e.getMessage());
//       return;
//     } catch (FileNotFoundException e) {
//       System.out.println("Not requests.txt file in the resources folder. Please create it before executing");
//       System.out.println(e.getMessage());
//       return;
//     } catch (IOException e) {
//       System.out.println("Error while retrieving json file object from the OpeDataHub Api");
//       System.out.println(e.getMessage());
//       return;
//     }
//     /**
//      * if request code < 299 -> input ErrorWxception 
//      */
//     long time = System.currentTimeMillis();

//      System.out.println("start parsing");
//     try {
//       JsonNode rawInput = ObjectMapperClass.mapper.readTree(jsonInput);
//       JsonNode activityArray = rawInput.get("Items");

//       Activity[] activityList = ObjectMapperClass.mapper.readValue(activityArray.toString(), Activity[].class);
//       activityDescriptionList = new ActivityDescription[activityList.length]; 

//       int i = 0;
//       for (Activity activity : activityList) {
//         // save the activity description into a file
//         ActivityDescription activityDescription;
//         try {
//           activityDescription = activity.getActivityDescription();
//           activityDescriptionList[i] = activityDescription;
//           i++;
//         } catch (NoLanguageAvailable e) {
//           System.out.println("For the activity (id) " + activity.getId());
//           e.getMessage();
//           return;
//         }
//       }
//     } catch (JsonMappingException e) {
//       System.out.println("Error from ObjectMapper");
//       System.out.println(e.getMessage());
//       return;
//     }
//     catch(JsonProcessingException e) {
//       System.out.println("Error from ObjectMapper");
//       System.out.println(e.getMessage());
//       return;
//     }
    
//     /**
//      * from there... one thread for writing the files and another for computing the Analysis
//      */
    
//     Thread saveDescription = new Thread(new SaveDescription(activityDescriptionList, output_description_path));
//     saveDescription.start();
//     Thread computeAnalysis = new Thread();

//     System.out.println(System.currentTimeMillis() - time);
//     //     Activity activity = mapper.readValue(activity1.toString(), Activity.class);
//     //    //from this JsonNode needs to create and instantiate the Activity class
//     //    System.out.println("\n\n\n" + activity);
    
//     //  Methods method = new Methods(); //class for support methods

//     //  JsonNode node = method.readFile("input.json", "Items");
//     //  ArrayList<Activity> list = method.getList(node); //retrieve the list of all the Activities
    
     
//   //   String outputPath = "src\\main\\results\\";

//   //  method.generateActivityJson(outputPath,list);
    
//   //   //create the Json file containing the output of the analysis
//   //   String analysisPath = "src\\main\\results\\";
//   //   GenerateAnalysisJson analysisJson = new GenerateAnalysisJson();
//   //   analysisJson.generateAnalysisJson(analysisPath, list);
//   }

//   // private static String readInput() throws IOException {
//   //   BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/input.json")));
//   //   String input = "";
//   //   try {
//   //     String nextLine = "";
//   //     while((nextLine = reader.readLine())!= null) {
           
//   //       input = input + nextLine;
//   //       }
//   //     } catch (Exception e) {
//   //       //TODO: handle exception
//   //       System.out.println("Erro");
//   //     }
    
//   //   reader.close();
    
//   //   System.out.println("Done reading the input");

//   //   return input;
  }
}