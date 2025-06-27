package com.msnider.habittracker;

import java.util.Collection;

public class HabitSummaries {
  private final Collection<HabitSummary> summaries;

  public HabitSummaries(Collection<HabitSummary> summaries) {
    this.summaries = summaries;
  }

  public Collection<HabitSummary> getSummaries() {
    return this.summaries;
  }
}
