package com.msnider.habittracker;

import java.time.LocalDateTime;
import java.util.Arrays;

public class HabitEntry {
  private final String name;
  private final LocalDateTime dateTime;

  public HabitEntry(String name, LocalDateTime dateTime) {
    this.name = name;
    this.dateTime = dateTime;
  }

  public static HabitEntry fromString(String line) {
    String[] tokens = line.trim().split(" ");
    String name = tokens[0];
    String dtStr = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
    LocalDateTime dt = LocalDateTime.parse(dtStr, HabitList.formatter);
    return new HabitEntry(name, dt);
  }

  public String getName() {
    return this.name;
  }

  public LocalDateTime getDateTime() {
    return this.dateTime;
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getDateTime().format(HabitList.formatter);
  }
}
