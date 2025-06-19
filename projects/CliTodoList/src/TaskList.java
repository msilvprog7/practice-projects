import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
  private final String filename;
  private final List<Task> tasks;

  public TaskList(String filename) {
    this.filename = filename;
    this.tasks = new ArrayList<>();
    this.readTasks();
  }

  public List<Task> getTasks() {
    return this.tasks;
  }

  public void addTask(Task task) {
    this.tasks.add(task);
    this.writeTasks();
  }

  public void completeTask(int index) {
    this.tasks.remove(index);
    this.writeTasks();
  }

  private void readTasks() {
    try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
      reader.lines().forEach(line -> this.addTask(new Task(line)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeTasks() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
      for (Task task : tasks) {
        writer.write(task.getName());
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}