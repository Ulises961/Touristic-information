/**
 * Test class for {@link #GpsPoints} class
 */
package org.Rigoni.parserTests.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.OpenDataHub.parser.support_classes.GpsPoints;
import com.OpenDataHub.parser.support_classes.ObjectMapperClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GpsPoints Test")
public class GpsPointsTest {

  /**
   * Check if fails on null input.
   * First is passed a null reference, then an empty List
   * 
   * @throws JsonProcessingException
   * @throws JsonMappingException
   */
  @DisplayName("Not fails on empty input")
  @Test
  void notFailsOnNullInput() throws JsonMappingException, JsonProcessingException {

    JsonNode sizeZeroNode = ObjectMapperClass.mapper.createObjectNode(); //instantiate an empty Node (size == 0)
    
    GpsPoints testClass = new GpsPoints(sizeZeroNode); 

    assertTrue(testClass.isEmpty());
  }


}