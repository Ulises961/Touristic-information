/**
 * Test class for {@link #GpsInfo} class
 */
package org.Rigoni.parserTests.support_classesTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import com.OpenDataHub.parser.support_classes.GpsInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GpsInfo Test")
public class GpsInfoTest {

  /**
   * Check if fails on null input.
   * First is passed a null reference, then an empty List
   * 
   * @throws JsonProcessingException
   * @throws JsonMappingException
   */
  @DisplayName("Not fails on null or empty inputs")
  @Test
  void notFailsOnNullInput() throws JsonMappingException, JsonProcessingException {

    List<JsonNode> emptyNode = new LinkedList<>();
    List<JsonNode> nullNode = null;
    
    GpsInfo emptyInfo = new GpsInfo(emptyNode);
    GpsInfo nullInfo = new GpsInfo(nullNode);

    assertAll(
      () -> assertTrue(emptyInfo.isNullOrEmpty()),
      () -> assertTrue(nullInfo.isNullOrEmpty()));

    }


}