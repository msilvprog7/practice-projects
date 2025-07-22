package com.msnider.otplocker.model;

public class Locker {
  private String id;
  private double width;
  private double height;
  private double depth;

  public Locker(String id, double width, double height, double depth) {
    this.id = id;
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
  }

  public double getWidth() {
      return width;
  }

  public void setWidth(double width) {
      this.width = width;
  }

  public double getHeight() {
      return height;
  }

  public void setHeight(double height) {
      this.height = height;
  }

  public double getDepth() {
      return depth;
  }

  public void setDepth(double depth) {
      this.depth = depth;
  }
}
