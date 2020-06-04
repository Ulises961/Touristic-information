package com.OpenDataHub.parser.support_classes;

/**
 * https://stackoverflow.com/questions/11229831/regular-expression-to-remove-html-tags-from-a-string
 */
public class HtmlTags {
  
  private static String html_regex = "<[^>]*>";

  public static String cleanTags(String value) {
        return value.replaceAll(html_regex, "");
  }
}