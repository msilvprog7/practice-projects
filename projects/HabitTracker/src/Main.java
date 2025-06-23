import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
  private static final String filename = "habits.txt";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean exited = false;
    HabitList habitList = new HabitList(formatter);
    habitList.readHabits(filename);

    System.out.print("Hi! ");

    while (!exited) {
      System.out.println("What would you like to do next?");
      System.out.println("- list");
      System.out.println("- add <habit> <datetime>");
      System.out.println("- summary");
      System.out.println("- exit");
      System.out.println();

      String input = scanner.nextLine().trim();
      String[] tokens = input.split(" ");
      System.out.println();
      
      switch (tokens[0].toLowerCase()) {
        case "list" -> {
          Collection<Habit> habits = habitList.getHabits();
          int size = habits.size();
          System.out.println("You have " + size + " habit" + (size != 1 ? "s" : "") + ".");
          for (Habit habit : habits) {
            System.out.println("- " + habit);
          }
          System.out.println();
        }
        case "add" -> {
          try {
            String line = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
            habitList.addHabit(habitList.parseEntry(line));
          } catch (DateTimeParseException dtpe) {
            System.out.println(dtpe);
            System.out.println("Sorry, add needs a <habit> and <datetime>.");
            System.out.println();
          }

          habitList.writeHabits(filename);
        }
        case "summary" -> {
          Collection<Habit> habits = habitList.getHabits();
          int size = habits.size();
          System.out.println("You have " + size + " habit" + (size != 1 ? "s" : "") + ".");
          for (Habit habit : habits) {
            int count = 0;
            Iterator<LocalDateTime> it = habit.getIterator();
            LocalDateTime dt = LocalDateTime.now();

            while (it.hasNext()) {
              LocalDateTime dt2 = it.next();
              if (dt.getYear() == dt2.getYear() && dt.getMonth() == dt2.getMonth()) {
                count++;
              }
            }

            System.out.println("- " + habit.getName() + ", Count this month: " + count);
          }
          System.out.println();
        }
        case "exit" -> {
          exited = true;
        }
        default -> {
          System.out.println("Sorry, I didn't understand your command.");
          System.out.println();
        }
      }
    }

    System.out.println("Thanks, please come again soon!");
  }
}