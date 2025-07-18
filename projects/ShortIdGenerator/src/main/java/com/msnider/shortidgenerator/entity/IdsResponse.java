package com.msnider.shortidgenerator.entity;

import java.util.List;

public class IdsResponse {
  private List<Unavailable> unavailable;
  private List<Available> available;

  public IdsResponse(List<Unavailable> unavailable, List<Available> available) {
    this.unavailable = unavailable;
    this.available = available;
  }

  public List<Unavailable> getUnavailable() {
    return this.unavailable;
  }

  public List<Available> getAvailable() {
    return this.available;
  }
}
