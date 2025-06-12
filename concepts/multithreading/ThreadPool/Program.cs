using ThreadPool;

var threadCount = 3;
var taskCount = 15;
var random = new Random();
using var threadPool = new MyThreadPool(threadCount, random); // Dispose will shutdown thread pool
var tscs = new List<Task>(taskCount); // Track the completion of tasks separate 

for (var i = 0; i < taskCount; i++)
{
  var taskId = i;
  var tsc = new TaskCompletionSource();
  var task = new Func<Task>(async () =>
  {
    var delay = TimeSpan.FromSeconds(1 + random.Next(taskCount));
    Console.WriteLine($"Task {taskId}, executing that takes {delay}");
    await Task.Delay(delay);

    // Complete the task
    tsc.SetResult();
  });
  tscs.Add(tsc.Task);
  threadPool.AddTask(task);
}

await Task.WhenAll(tscs);
