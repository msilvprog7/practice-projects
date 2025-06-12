namespace ThreadPool;

public class MyThreadPool : IDisposable
{
  private readonly Queue<Func<Task>> tasks;
  private readonly Lock myLock;
  private readonly Random random;
  private readonly List<Thread> threads;
  private bool shutdown;
  private bool disposed;

  public MyThreadPool(int threads, Random random)
  {
    Console.WriteLine($"MyThreadPool, starting {threads} threads");
    this.tasks = new();
    this.myLock = new();
    this.random = random;
    this.threads = [.. Enumerable.Range(0, threads).Select(id => {
      // Each thread will have an id and a random delay
      var delay = TimeSpan.FromSeconds(random.Next(threads));
      var thread = new Thread(async () => await this.Work(id, delay));
      thread.Start();
      return thread;
    })];
    this.shutdown = false;
  }

  public void Dispose()
  {
    this.Dispose(true);
    GC.SuppressFinalize(this);
  }

  public void AddTask(Func<Task> task)
  {
    // ensure only one adding to the queue
    lock (this.myLock)
    {
      Console.WriteLine("Adding task");
      this.tasks.Enqueue(task);
    }
  }

  public void Shutdown()
  {
    Console.WriteLine("MyThreadPool, shutting down");
    this.shutdown = true;

    foreach (var thread in threads)
    {
      thread.Join();
    }

    Console.WriteLine("MyThreadPool, shutdown complete");
  }

  protected virtual void Dispose(bool disposing)
  {
    if (this.disposed)
    {
      return;
    }

    if (disposing)
    {
      this.Shutdown();
    }

    this.disposed = true;
  }

  private async Task Work(int id, TimeSpan delay)
  {
    Console.WriteLine($"Thread {id}, starting");
    while (!this.shutdown)
    {
      // Safely dequeue messages
      Func<Task>? task = null;
      lock (this.myLock)
      {
        if (this.tasks.Count > 0)
        {
          task = this.tasks.Dequeue();
        }
      }

      // Wait if no tasks
      if (task == null)
      {
        await Task.Delay(TimeSpan.FromSeconds(1));
        continue;
      }

      // Run the task, then delay
      try
      {
        Console.WriteLine($"Thread {id}, running task");
        await task();
        await Task.Delay(delay);
      }
      catch (Exception e)
      {
        Console.WriteLine($"Thread {id}, failed with exception {e}");
      }
    }
  }
}
