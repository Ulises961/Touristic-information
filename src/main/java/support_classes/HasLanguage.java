package support_classes;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Initialized only the list in the generic Activity -> when define the reduced 
 */
public class HasLanguage {
  @JsonSetter("HasLanguage")
  private List<String> languages;

  //fields for generalizing the preference order
  private List<String> preferences;

  private String language;

  //based on the preference
  // @JsonCreator 
  public HasLanguage() {
    setDefaultPreferenceOrder();
    setLanguage();
  }

  public String toString() {
    return language.toString();
  }

  public String getLanguage() {
    return this.language;
  }

  private void setDefaultPreferenceOrder() {
    this.preferences = new LinkedList<String>(); //modifying this, modify preference order
    this.preferences.add("en");
    this.preferences.add("it");
    this.preferences.add("de");
    setLanguage();
  }

  public void setNewPreferenceOrder(List<String> newOrder) {
    this.preferences.clear();

    for (String string : newOrder) {
      this.preferences.add(string);
    }

    setLanguage();
  }

   void setLanguage() {
    for (String string : languages) {
      if(languages.contains(string))
        this.language = string;
        return;
    }

    //if no languages chosen
    this.language = null;
    // throw new MyPersonalException -> no language found!
  }
}