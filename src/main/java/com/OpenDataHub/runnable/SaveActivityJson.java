/**
 * Thread for save the activity descriptions into their correspondent files
 */
package com.OpenDataHub.runnable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.OpenDataHub.fileio.JsonFile;
import com.OpenDataHub.parser.support_classes.ActivityDescription;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveActivityJson implements Runnable {

  private static Logger logger = LogManager.getLogger();
  private static String targetPath = "results/";
  private static String fileType = ".json"; 

  private List<ActivityDescription> toBeSaved;
  

  public SaveActivityJson(List<ActivityDescription> toBeSaved) {
    this.toBeSaved = toBeSaved;
  }

  @Override
  public void run() {
    
    //map all the elemetns of the list into their correspondet {@link JsonFile} objects
    List<JsonFile> jsonFileList = toBeSaved.stream()
    .map(activityDescription -> { //map every activityDescription into corrispondent file
        return new JsonFile(activityDescription);
    }).collect(Collectors.toList());


    //make result directory if not present
    
    //save the elements
    jsonFileList.stream().parallel().forEach((jsonFile) -> {
      try {
        jsonFile.Save(targetPath);
        
      } catch (IOException e) {
        logger.error("Cannot save activity: " + jsonFile.getFileName());
      }
    });
  }

}