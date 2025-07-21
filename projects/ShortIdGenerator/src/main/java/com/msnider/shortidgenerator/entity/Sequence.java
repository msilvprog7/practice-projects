package com.msnider.shortidgenerator.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
public class Sequence {
  public static final String SEQUENCE_ID = "sequence";

  @Id
  private String id = SEQUENCE_ID;
  private long currentSequence;
  private long lastUpdated;

  public Sequence(String id, long currentSequence, long lastUpdated) {
    this.id = id;
    this.currentSequence = currentSequence;
    this.lastUpdated = lastUpdated;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getCurrentSequence() {
    return this.currentSequence;
  }

  public void setCurrentSequence(long currentSequence) {
    this.currentSequence = currentSequence;
  }

  public long getLastUpdated() {
    return this.lastUpdated;
  }

  public void setLastUpdated(long lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
}
