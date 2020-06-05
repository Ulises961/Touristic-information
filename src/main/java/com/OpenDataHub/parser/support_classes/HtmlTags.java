/**
 * Support class for removing html tags from the Api response
 * 
 * Method from: https://stackoverflow.com/questions/11229831/regular-expression-to-remove-html-tags-from-a-string
 */
package com.OpenDataHub.parser.support_classes;


public class HtmlTags {
  
  private static String html_regex = "<[^>]*>";

  public static String cleanTags(String value) {
        return value.replaceAll(html_regex, "");
  }
}