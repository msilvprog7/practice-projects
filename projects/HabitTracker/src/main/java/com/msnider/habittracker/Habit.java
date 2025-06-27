package com.msnider.habittracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Habit implements Iterable<HabitEntry> {
  private final String name;
  private final SortedSet<HabitEntry> tracker;

  public Habit(String name) {
    this.name = name;
    this.tracker = new TreeSet<>((a, b) -> a.getDateTime().compareTo(b.getDateTime()));
  }

  public String getName() {
    return this.name;
  }

  public List<LocalDateTime> getDateTimes() {
    List<LocalDateTime> dates = new ArrayList<>();
    this.iterator().forEachRemaining(entry -> dates.add(entry.getDateTime()));
    return dates;
  }

  public void track(HabitEntry entry) {
    this.tracker.add(entry);
  }

  public LocalDateTime last() {
    return this.tracker.last().getDateTime();
  }

  public List<LocalDateTime> range(LocalDateTime start, LocalDateTime end) {
    HabitEntry startEntry = new HabitEntry(this.name, start);
    HabitEntry endEntry = new HabitEntry(this.name, end);
    return this.tracker.subSet(startEntry, endEntry)
      .stream()
      .map(HabitEntry::getDateTime)
      .collect(Collectors.toList());
  }
  
  @Override
  public Iterator<HabitEntry> iterator() {
    return this.tracker.iterator();
  }

  @Override
  public String toString() {
    return this.name + ", Count: " + this.tracker.size() + ", Most Recent: " + this.tracker.last().getDateTime().format(HabitList.formatter);
  }
}