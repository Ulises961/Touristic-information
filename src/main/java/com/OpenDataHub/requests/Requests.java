package com.OpenDataHub.requests;

import java.io.IOException;

public class Requests {
  
  // this gives an example of how the class shold be used

  public static void main(String[] args) throws IOException, InterruptedException, NumberFormatException {
    
    //get the value from the file 
    String file_path = "./src/main/resources/requests.txt";
    int pageSize = Loaderpt2.retrieveInput(file_path); //could throw FileFormat o FileNotfound Ecxeption () 

    Retriever retriever = new Retriever(); //with no parameters, default values 
    retriever.setPageSize(pageSize);

    String jsonInput = retriever.makeRequest();

  }
}