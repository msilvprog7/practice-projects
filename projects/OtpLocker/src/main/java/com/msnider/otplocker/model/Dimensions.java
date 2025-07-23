package com.msnider.otplocker.model;

public class Dimensions {
  private double width;
  private double height;
  private double depth;

  public Dimensions(double width, double height, double depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
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

  public boolean canFit(Dimensions smaller) {
    return this.canFit(smaller.width, smaller.height, smaller.depth) ||
           this.canFit(smaller.width, smaller.depth, smaller.height) ||
           this.canFit(smaller.height, smaller.width, smaller.depth) ||
           this.canFit(smaller.height, smaller.depth, smaller.width) ||
           this.canFit(smaller.depth, smaller.width, smaller.height) ||
           this.canFit(smaller.depth, smaller.height, smaller.width);
  }

  private boolean canFit(double dimension1, double dimension2, double dimension3) {
    return this.width >= dimension1 && this.height >= dimension2 && this.depth >= dimension3;
  }
}
