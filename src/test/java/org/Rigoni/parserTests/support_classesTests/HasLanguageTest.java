/**
 * Test class for {@link #GpsInfo} class.
 */
package org.Rigoni.parserTests.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;

import com.OpenDataHub.parser.support_classes.HasLanguage;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HasLanguage Test")
public class HasLanguageTest {
  
  @DisplayName("Check matches correctly")
  @Test
  void checkMatch() throws NoLanguageAvailable {
    List<String> availableLanguagesInResponse = new LinkedList<>();
    availableLanguagesInResponse.add("ru");
    availableLanguagesInResponse.add("fin");
    availableLanguagesInResponse.add("de"); //should match "de"

    HasLanguage testInstance = new HasLanguage(availableLanguagesInResponse);
    assertEquals("de", testInstance.getLanguageToUse());
  }


  @DisplayName("Check Exception thrown when no matches (default order)")
  @Test
  void checkExceptionThrownDefault() {
    List<String> availableLanguagesInResponse = new LinkedList<>();
    availableLanguagesInResponse.add("ru");
    availableLanguagesInResponse.add("fin");
    availableLanguagesInResponse.add("bos");
    
    assertThrows(NoLanguageAvailable.class, () -> new HasLanguage(availableLanguagesInResponse));
  }

  @DisplayName("Check Exception thrown when no matches (another preference order)")
  @Test
  void checkExceptionThrownCustom() {
    List<String> availableLanguagesInResponse = new LinkedList<>();
    availableLanguagesInResponse.add("en"); //match when instantiating the class in the default order ("en", "it", "de")
    availableLanguagesInResponse.add("fin");
    availableLanguagesInResponse.add("bos");

    List<String> newPreferenceOrder = new LinkedList<>();
    newPreferenceOrder.add("svi");
    newPreferenceOrder.add("slo");
    newPreferenceOrder.add("ucr");

    assertThrows(NoLanguageAvailable.class, () -> {
      HasLanguage testInstance = new HasLanguage(availableLanguagesInResponse);
      testInstance.setNewPreferenceOrder(newPreferenceOrder); //here should throw exception... no matches
    }
    );
  }
}