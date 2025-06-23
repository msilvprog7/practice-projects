import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HabitList {
  private final DateTimeFormatter formatter;
  private final Map<String, Habit> habits;

  public HabitList(DateTimeFormatter formatter) {
    this.formatter = formatter;
    this.habits = new HashMap<>();
  }

  public void readHabits(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      reader.lines().forEach(line -> {
        this.addHabit(this.parseEntry(line));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeHabits(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Habit habit : this.habits.values()) {
          String name = habit.getName();
          Iterator<LocalDateTime> it = habit.getIterator();
          while (it.hasNext()) {
            writer.write(this.formatLine(new AbstractMap.SimpleEntry<>(name, it.next())));
            writer.newLine();
          }
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addHabit(Entry<String, LocalDateTime> entry) {
    String name = entry.getKey();
    LocalDateTime dt = entry.getValue();
    Habit habit = this.habits.getOrDefault(name, new Habit(this.formatter, name));
    habit.track(dt);
    this.habits.put(name, habit);
  }

  public Collection<Habit> getHabits() {
    return this.habits.values();
  }

  public Entry<String, LocalDateTime> parseEntry(String line) {
    String[] tokens = line.trim().split(" ");
    String name = tokens[0];
    String dtStr = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
    LocalDateTime dt = LocalDateTime.parse(dtStr, this.formatter);
    return new AbstractMap.SimpleEntry<>(name, dt);
  }

  public String formatLine(Entry<String, LocalDateTime> entry) {
    return entry.getKey() + " " + entry.getValue().format(this.formatter);
  }
}