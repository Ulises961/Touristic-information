package support_classes;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ODHTag {
  private int occurrences;

  @JsonSetter("Id")
  private String id;

  //getter
  public String getId() {
    return this.id;
  }

  public int getOccurrences() {
    return this.occurrences;
  }


  public void initOccurrence() {
    this.occurrences = 1;
  }

  public void incrementOccurrences() {
    this.occurrences = this.occurrences + 1;
  }

  public String toString() {
    return this.id;
  }

}