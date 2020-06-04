package com.OpenDataHub.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.FutureTask;

public class Requests {
  public static void main(String[] args) throws IOException, InterruptedException {
   
    RequestSetter requester = new RequestSetter(); 
    
    LinkedList<FutureTask<StringBuilder>> list = requester.startThreads();

    System.out.println("list size "+list.size());
 
  }
}