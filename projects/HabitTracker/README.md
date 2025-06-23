# Habit Tracker

The goal of this project is to make a simple CLI application
and then turn it into a REST service with Spring Boot.

## CLI application

```cmd
java ./src/Main.java
```

Then the application will ask you for your next command.

```cmd
Hi! What would you like to do next?
- list
- add
- summary
- exit
```

Each will include prompts to help you add habits and
start summarizing your progress.

The habits can be stored to file as well.

## REST service

The idea is to then keep the habits in-memory and later
a database, but first to replace CLI commands with
Spring Boot REST-ful endpoints.

```text
GET /habits
POST /habits
GET /habits/summary
```
