package runnable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import support_classes.ActivityDescription;
import support_classes.ObjectMapperClass;

public class SaveDescription implements Runnable {
  ActivityDescription[] activityDescription;
  String output_path;

  public SaveDescription(Object o, String output_path) {
    this.activityDescription = (ActivityDescription[]) o;
    this.output_path = output_path;

  }

  @Override
  public void run() {

    try {
      checkDirectory();
      BufferedWriter writer;
      for (int i = 0; i < activityDescription.length; i++) {
        String id = activityDescription[i].getIdActivity();
        File file = new File(output_path + id + ".json");
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(ObjectMapperClass.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(activityDescription[i]));
        writer.flush();
        writer.close();
      }
    } catch(JsonProcessingException e) {
      System.out.println("Error while parsing to string an activity \n" + e.getMessage());
      return;
    } 
    catch (IOException e) {
        System.out.println("Error while trying to reach the result path! \n" + e.getMessage());
        return;
    }
    

  }

  private void checkDirectory() throws IOException {
    File file = new File(output_path);
    file.mkdirs();
  }
}