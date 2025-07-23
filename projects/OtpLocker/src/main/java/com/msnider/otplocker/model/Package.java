package com.msnider.otplocker.model;

import com.msnider.otplocker.dto.DropOffPackage;

public class Package {
  private String id;
  private Dimensions dimensions;

  public Package(String id, Dimensions dimensions) {
    this.id = id;
    this.dimensions = dimensions;
  }

  public Package(DropOffPackage dropOffPackage) {
    this.id = dropOffPackage.getId();
    this.dimensions = new Dimensions(
      dropOffPackage.getWidth(),
      dropOffPackage.getHeight(),
      dropOffPackage.getDepth()
    );
  }

  public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
  }

  public Dimensions getDimensions() {
      return this.dimensions;
  }

  public void setDimensions(Dimensions dimensions) {
      this.dimensions = dimensions;
  }
}
