package com.msnider.habittracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HabitList {
  public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

  private final Map<String, Habit> habits;

  public HabitList() {
    this.habits = new HashMap<>();
  }

  public void readHabits(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      reader.lines().forEach(line -> {
        this.addHabit(HabitEntry.fromString(line));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeHabits(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Habit habit : this.habits.values()) {
          for (HabitEntry entry : habit) {
            writer.write(entry.toString());
            writer.newLine();
          }
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Habit addHabit(HabitEntry entry) {
    String name = entry.getName();
    Habit habit = this.habits.getOrDefault(name, new Habit(name));
    habit.track(entry);
    this.habits.put(name, habit);
    return habit;
  }

  public Collection<Habit> getHabits() {
    return this.habits.values();
  }
}