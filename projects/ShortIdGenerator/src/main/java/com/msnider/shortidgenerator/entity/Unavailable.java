package com.msnider.shortidgenerator.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "unavailable")
public class Unavailable {
  @Id 
  private String id;
  private String text;
  private long generatedAt;
  private long reservedAt;

  public Unavailable() {}

  public Unavailable(String id, String text, long generatedAt, long reservedAt) {
    this.id = id;
    this.text = text;
    this.generatedAt = generatedAt;
    this.reservedAt = reservedAt;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public long getGeneratedAt() {
    return this.generatedAt;
  }

  public void setGeneratedAt(long generatedAt) {
    this.generatedAt = generatedAt;
  }

  public long getReservedAt() {
    return this.reservedAt;
  }

  public void setReservedAt(long reservedAt) {
    this.reservedAt = reservedAt;
  }
}
