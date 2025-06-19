import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
  private static final String FILENAME = "todo.txt";
  private static final Map<String, String> COMMANDS = Map.of(
    "list", "lists the available tasks",
    "add", "add <task>, adds the task to the todo list",
    "complete", "complete <task>, complets the first task with name in the todo list",
    "help", "lists the available commands"
  );

  public static void main(String[] args) {
    // Objects
    TaskList taskList = new TaskList(FILENAME);

    // Commands
    String command = (args.length > 0 && COMMANDS.containsKey(args[0].toLowerCase())) ? args[0].toLowerCase() : "help";

    switch (command) {
      case "list" -> {
        List<Task> tasks = taskList.getTasks();
        System.out.println("Tasks (" + tasks.size() + "):");
        for (Task task : tasks) {
          System.out.println("- " + task.getName());
        }
      }

      case "add" -> {
        if (args.length < 2 || args[1] == null || args[1].trim().isEmpty()) {
          System.out.println("Cannot add task as it was not provided in command, expected: add <task>");
          break;
        }

        taskList.addTask(new Task(args[1].trim()));
      }

      case "complete" -> {
        if (args.length < 2 || args[1] == null || args[1].trim().isEmpty()) {
          System.out.println("Cannot complete task as it was not provided in command, expected: complete <task>");
          break;
        }

        List<Task> tasks = taskList.getTasks();
        String name = args[1].trim();
        boolean completed = false;
        for (int i = 0; i < tasks.size(); i++) {
          if (tasks.get(i).getName().equalsIgnoreCase(name)) {
            taskList.completeTask(i);
            completed = true;
            break;
          }
        }

        if (!completed) {
          System.out.println("Cannot complete task as did not match available tasks, expected: complete <task>");
          break;
        }
      }

      default -> {
        System.out.println("help - list the available commands:");
        for (Entry<String, String> availableCommand : COMMANDS.entrySet()) {
          System.out.println("- " + availableCommand.getKey() + ": " + availableCommand.getValue());
        }
      }
    }
  }
}