/**
 * check that the filtering of duplicate activities works fine
 */
package com.OpenDataHub.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import com.OpenDataHub.helper.TFileLoader;
import com.OpenDataHub.parser.support_classes.Activity;
import com.OpenDataHub.parser.support_classes.ActivityDescription;
import com.OpenDataHub.parser.support_classes.NoLanguageAvailable;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.OpenDataHub.requests.ActivityDescriptionsManager;
import com.OpenDataHub.requests.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Filtering duplicate activities Test")
public class DuplicateActivitiesTest {

  @DisplayName("Passing two times the same activity, only one stored")
  @Test
  void with_same_activity_one_keeped_other_discarded()
      throws NoLanguageAvailable, JsonMappingException, JsonProcessingException {
    String input = TFileLoader.LoadFile(TFileLoader.LoadableFiles.EMPTY_RESPONSE_SAMPLE);
    
    Activity activity1 = ObjectMapperClass.mapper.readValue(input, Activity.class);
    ActivityDescription activityDescription1 = activity1.getActivityDescription();

    Activity activity2 = ObjectMapperClass.mapper.readValue(input, Activity.class);
    ActivityDescription activityDescription2 = activity2.getActivityDescription();

    List<ActivityDescription> listWithEquivalentElements = new LinkedList<>();
    listWithEquivalentElements.add(activityDescription1);
    listWithEquivalentElements.add(activityDescription2);

    listWithEquivalentElements = ActivityDescriptionsManager.filterDuplicateActivities(listWithEquivalentElements);
    assertEquals(1, listWithEquivalentElements.size(), "The second element has been discarded");

  }
}