package com.msnider.habittracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Main {
  private static final String filename = "habits.txt";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean exited = false;
    HabitList HabitList = new HabitList();
    HabitList.readHabits(filename);

    System.out.print("Hi! ");

    while (!exited) {
      System.out.println("What would you like to do next?");
      System.out.println("- list");
      System.out.println("- add <Habit> <datetime>");
      System.out.println("- summary");
      System.out.println("- exit");
      System.out.println();

      String input = scanner.nextLine().trim();
      String[] tokens = input.split(" ");
      System.out.println();
      
      switch (tokens[0].toLowerCase()) {
        case "list" -> {
          Collection<Habit> Habits = HabitList.getHabits();
          int size = Habits.size();
          System.out.println("You have " + size + " Habit" + (size != 1 ? "s" : "") + ".");
          for (Habit Habit : Habits) {
            System.out.println("- " + Habit);
          }
          System.out.println();
        }
        case "add" -> {
          try {
            String line = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
            HabitList.addHabit(HabitEntry.fromString(line));
          } catch (DateTimeParseException dtpe) {
            System.out.println(dtpe);
            System.out.println("Sorry, add needs a <Habit> and <datetime>.");
            System.out.println();
          }

          HabitList.writeHabits(filename);
        }
        case "summary" -> {
          Collection<Habit> habits = HabitList.getHabits();
          int size = habits.size();
          System.out.println("You have " + size + " Habit" + (size != 1 ? "s" : "") + ".");
          for (Habit habit : habits) {
            int count = 0;
            LocalDateTime dt = LocalDateTime.now();

            for (HabitEntry entry : habit) {
              LocalDateTime dt2 = entry.getDateTime();
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