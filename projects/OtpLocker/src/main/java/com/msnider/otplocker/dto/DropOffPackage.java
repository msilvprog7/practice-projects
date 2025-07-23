package com.msnider.otplocker.dto;

import com.msnider.otplocker.model.Dimensions;
import com.msnider.otplocker.model.Package;

public class DropOffPackage {
  private String id;
  private double width;
  private double height;
  private double depth;

  public DropOffPackage() {
  }
  
  public DropOffPackage(String id, double width, double height, double depth) {
    this.id = id;
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  public DropOffPackage(Package dropOffPackage) {
    this.id = dropOffPackage.getId();

    Dimensions dimensions = dropOffPackage.getDimensions();
    this.width = dimensions.getWidth();
    this.height = dimensions.getHeight();
    this.depth = dimensions.getDepth();
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

    public double getDepth() {
    return depth;
  }

  public void setDepth(double depth) {
    this.depth = depth;
  }
}
