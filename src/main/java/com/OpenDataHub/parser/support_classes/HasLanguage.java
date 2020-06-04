package com.OpenDataHub.parser.support_classes;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Initialized only the list in the generic Activity -> when define the reduced 
 */
public class HasLanguage {
 
  private List<String> availableaLanguages;

  //fields for generalizing the preference order
  private List<String> preferenceOrder;

  private String utilizedLanguage;

  //based on the preference
  @JsonCreator 
  public HasLanguage(List<String> hasLanguage) throws NoLanguageAvailable {
    this.availableaLanguages = hasLanguage;
    setDefaultPreferenceOrder();
  }

  // public String toString() {
  //   return language.toString();
  // }

  public String getUtilizedLanguage() {
    return this.utilizedLanguage;
  }

  private void setDefaultPreferenceOrder() throws NoLanguageAvailable {
    this.preferenceOrder = new LinkedList<String>(); //modifying this, modify preference order
    this.preferenceOrder.add("en");
    this.preferenceOrder.add("it");
    this.preferenceOrder.add("de");
    setLanguage();
  }

  public void setNewPreferenceOrder(List<String> newOrder) throws NoLanguageAvailable {
    this.preferenceOrder.clear();

    for (String string : newOrder) {
      this.preferenceOrder.add(string);
    }

    setLanguage();
  }

   void setLanguage() throws NoLanguageAvailable {
    for (String string : preferenceOrder) {
      if(availableaLanguages.contains(string))
        this.utilizedLanguage = string;
        return;
    }

    String message = "No languages available for this set of preferences. \nPreferences: " + preferenceOrder.toString() + "\nAvailable: " + availableaLanguages;
    throw new NoLanguageAvailable(message);
  }
}