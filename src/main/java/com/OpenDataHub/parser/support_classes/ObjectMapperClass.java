/**
 * Class for configure the ObjectMapper object.
 * Could be implemented using "Singleton" design pattern
 */
package com.OpenDataHub.parser.support_classes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperClass {
  
  public static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  
}