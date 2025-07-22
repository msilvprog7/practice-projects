package com.msnider.otplocker.dto;

public class DropOffPackage {
  private String id;
  private double width;
  private double height;

  public DropOffPackage(String id, double width, double height) {
    this.id = id;
    this.width = width;
    this.height = height;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getWidth() {
    return this.width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return this.height;
  }

  public void setHeight(double height) {
    this.height = height;
  }
}
