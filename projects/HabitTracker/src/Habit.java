import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Habit {
  private final DateTimeFormatter formatter;
  private final String name;
  private final SortedSet<LocalDateTime> tracker;

  public Habit(DateTimeFormatter formatter, String name) {
    this.formatter = formatter;
    this.name = name;
    this.tracker = new TreeSet<>();
  }

  public String getName() {
    return this.name;
  }

  public Iterator<LocalDateTime> getIterator() {
    return this.tracker.iterator();
  }

  public void track(LocalDateTime dt) {
    this.tracker.add(dt);
  }

  @Override
  public String toString() {
    return this.name + ", Count: " + this.tracker.size() + ", Most Recent: " + this.tracker.last().format(this.formatter);
  }
}