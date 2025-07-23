package com.msnider.otplocker.model;

import java.util.Optional;

public class Locker {
  private String id;
  private Dimensions dimensions;
  private Optional<Package> dropOffPackage;

  public Locker(String id, Dimensions dimensions) {
    this.id = id;
    this.dimensions = dimensions;
    this.dropOffPackage = Optional.empty();
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

  public boolean dropOffPackage(Package dropOffPackage) {
    if (!this.dropOffPackage.isEmpty() || !this.dimensions.canFit(dropOffPackage.getDimensions())) {
        return false;
    }

    this.dropOffPackage = Optional.ofNullable(dropOffPackage);
    return true;
  }

  public Optional<Package> pickUpPackage() {
    Optional<Package> pickUpPackage = this.dropOffPackage;
    this.dropOffPackage = Optional.empty();
    return pickUpPackage;
  }
}
