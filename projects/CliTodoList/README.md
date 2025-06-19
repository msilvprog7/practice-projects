# CLI Todo List

I'm getting reacquainted for interviews so I'd like
to make a simple todo list command line interface.

## Run

```cmd
java src/Main.java [command] [options]
```

| Command           | Options                     | Description                                     |
| ----------------- | --------------------------- | ----------------------------------------------- |
| `list`            |                             | Lists the tasks todo                            |
| `add <task>`      | required `task` to add      | Add the task                                    |
| `complete <task>` | required `task` to complete | Completes the task and removes it from the list |
| `help`            |                             | Lists the available commands and options        |
