package com.msnider.shortidgenerator.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "available")
public class Available {
  @Id 
  private String id;
  private long generatedAt;

  public Available() {}

  public Available(String id, long generatedAt) {
    this.id = id;
    this.generatedAt = generatedAt;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getGeneratedAt() {
    return this.generatedAt;
  }

  public void setGeneratedAt(long generatedAt) {
    this.generatedAt = generatedAt;
  }
}
