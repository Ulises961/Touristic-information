/**
 * Support class for retrieving all the languages in which the ActivityDescription is provided and 
 * to find out which needs to be used from the User for interacting with the String response
 * 
 * @author Rigoni Riccardo
 */
package com.OpenDataHub.parser.support_classes;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class HasLanguage {
 
  private List<String> availableLanguagesFromResponse;

  /**
   * Generalize the preference order for the languages in which retrieve the informations
   * position 0 in the list, most important, and so on... 
   */
  private List<String> preferenceOrder;

  private String utilizedLanguage;

  /**
   * Constructor
   * @param availableLanguagesFromResponse List<String> containing the languages available in the response
   * @throws NoLanguageAvailable if no {@link #availableLanguagesFromResponse} matches any language in {@link #preferenceOrder} inserted by the user (or feault "en", "it", "de")
   */
  @JsonCreator 
  public HasLanguage(List<String> availableLanguagesFromResponse) throws NoLanguageAvailable {
    this.availableLanguagesFromResponse = availableLanguagesFromResponse;
    setDefaultPreferenceOrder();
  }

  /**
   * Getter for {@link #utilizedLanguage}
   * @return return String tag for the language to use
   */
  public String getLanguageToUse() {
    return this.utilizedLanguage;
  }

  /**
   * Default preference 1) en, 2) it, 3) de
   * 
   * @throws NoLanguageAvailable if no one mathces
   */
  private void setDefaultPreferenceOrder() throws NoLanguageAvailable {
    this.preferenceOrder = new LinkedList<String>(); //modifying this, modify preference order
    this.preferenceOrder.add("en");
    this.preferenceOrder.add("it");
    this.preferenceOrder.add("de");
    setLanguage();
  }

  /**
   * Accept an new order for languages and set the new language to use based on the new order inserted
   * 
   * @param newOrder List<String> containing the new order inserted by the user
   * @throws NoLanguageAvailable if no one mathces
   */
  public void setNewPreferenceOrder(List<String> newOrder) throws NoLanguageAvailable {
    this.preferenceOrder.clear();

    for (String string : newOrder) {
      this.preferenceOrder.add(string);
    }

    setLanguage();
  }

  /**
   * Goes throw every language from {@link #availableLanguagesFromResponse} and search the first matching one in {@link #preferenceOrder}
   * @throws NoLanguageAvailable if no one matches
   */
  private void setLanguage() throws NoLanguageAvailable { 
    for (String preference : preferenceOrder) 
      if(availableLanguagesFromResponse.contains(preference)) {  //search the first preference that matches in the availableLanguages
        this.utilizedLanguage = preference;
        return;
      }

    String message = "No languages available for this set of preferences. \nPreferences: " + preferenceOrder.toString() + "\nAvailable: " + availableLanguagesFromResponse;
    throw new NoLanguageAvailable(message);
  }
   
}