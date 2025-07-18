package com.msnider.shortidgenerator.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "generated")
public class Generated {
  @Id
  private long latestSequence;
  private long generatedAt;

  public Generated() {}

  public Generated(long latestSequence, long generatedAt) {
    this.latestSequence = latestSequence;
    this.generatedAt = generatedAt;
  }

  public long getLatestSequence() {
    return this.latestSequence;
  }

  public void setLatestSequence(long latestSequence) {
    this.latestSequence = latestSequence;
  }

  public long getGeneratedAt() {
    return this.generatedAt;
  }

  public void setGeneratedAt(long generatedAt) {
    this.generatedAt = generatedAt;
  }
}
