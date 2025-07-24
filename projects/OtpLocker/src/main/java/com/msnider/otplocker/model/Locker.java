package com.msnider.otplocker.model;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Locker {
  private String id;
  private Dimensions dimensions;
  private Package dropOffPackage;

  public Locker() {
  }

  public Locker(String id, Dimensions dimensions) {
    this.id = id;
    this.dimensions = dimensions;
    this.dropOffPackage = null;
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

  public Package getDropOffPackage() {
    return this.dropOffPackage;
  }

  public void setDropOffPackage(Package dropOffPackage) {
    this.dropOffPackage = dropOffPackage;
  }

  public boolean dropOffPackage(Package dropOffPackage) {
    if (this.dropOffPackage != null || !this.dimensions.canFit(dropOffPackage.getDimensions())) {
        return false;
    }

    this.dropOffPackage = dropOffPackage;
    return true;
  }

  public Optional<Package> pickUpPackage() {
    if (this.dropOffPackage == null) {
      return Optional.empty();
    }
    
    Package pickUpPackage = this.dropOffPackage;
    this.dropOffPackage = null;
    return Optional.ofNullable(pickUpPackage);
  }
}
