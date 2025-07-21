package com.msnider.shortidgenerator.entity;

public class IdRequest {
  private String id;
  private String text;

  public IdRequest(String id, String text) {
    this.id = id;
    this.text = text;
  }

  public String getId() {
    return this.id;
  }

  public String getText() {
    return this.text;
  }
}
