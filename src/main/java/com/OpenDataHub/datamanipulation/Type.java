package com.OpenDataHub.datamanipulation;

public class Type {
  private String id;
  private int counter;

  //constructor
  public Type(String string) {
    this.id = string;
    counter = 1;
  }

  //get an ID, check if content equal to what has been passed as parameter
  //return true -> added ; false -> not equal
  public boolean add(String id) {
    if(this.id.contentEquals(id)) {
      this.counter++;
      return true;
    }
    else 
    return false;
  }
}