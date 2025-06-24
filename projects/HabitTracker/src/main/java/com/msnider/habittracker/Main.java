package com.msnider.habittracker;

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
    HabitList HabitList = new HabitList(formatter);
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
            HabitList.addHabit(HabitList.parseEntry(line));
          } catch (DateTimeParseException dtpe) {
            System.out.println(dtpe);
            System.out.println("Sorry, add needs a <Habit> and <datetime>.");
            System.out.println();
          }

          HabitList.writeHabits(filename);
        }
        case "summary" -> {
          Collection<Habit> Habits = HabitList.getHabits();
          int size = Habits.size();
          System.out.println("You have " + size + " Habit" + (size != 1 ? "s" : "") + ".");
          for (Habit Habit : Habits) {
            int count = 0;
            Iterator<LocalDateTime> it = Habit.iterator();
            LocalDateTime dt = LocalDateTime.now();

            while (it.hasNext()) {
              LocalDateTime dt2 = it.next();
              if (dt.getYear() == dt2.getYear() && dt.getMonth() == dt2.getMonth()) {
                count++;
              }
            }

            System.out.println("- " + Habit.getName() + ", Count this month: " + count);
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