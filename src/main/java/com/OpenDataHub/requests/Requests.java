package com.OpenDataHub.requests;

import java.io.IOException;

public class Requests {
  public static void main(String[] args) throws IOException, InterruptedException {
    
    Retriever retrieve = new Retriever();
    retrieve.checkAndRequest();
  }
}