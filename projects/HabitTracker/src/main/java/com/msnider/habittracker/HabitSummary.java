package com.msnider.habittracker;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class HabitSummary {
  private final Habit habit;

  public HabitSummary(Habit habit) {
    this.habit = habit;
  }

  public String getName() {
    return this.habit.getName();
  }

  public LocalDateTime getMostRecent() {
    return this.habit.last();
  }

  public int getMonthly() {
    LocalDateTime start = LocalDateTime.now()
      .withDayOfMonth(1)
      .withHour(0)
      .withMinute(0)
      .withSecond(0);
    LocalDateTime end = start.plusMonths(1);
    return this.habit.range(start, end).size();
  }

  public int getWeekly() {
    LocalDateTime dateTime = LocalDateTime.now();
    LocalDateTime start = dateTime
    .minusDays(dateTime.getDayOfWeek().getValue() % DayOfWeek.SUNDAY.getValue())
      .withHour(0)
      .withMinute(0)
      .withSecond(0);
    LocalDateTime end = start.plusWeeks(1);
    return this.habit.range(start, end).size();
  }

  public int getDaily() {
    LocalDateTime start = LocalDateTime.now()
      .withHour(0)
      .withMinute(0)
      .withSecond(0);
    LocalDateTime end = start.plusDays(1);
    return this.habit.range(start, end).size();
  }
}
