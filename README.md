# Practice Projects

Hello! This is my collection of practice projects I've been developing
to practice software engineering skills. It's based on ideas from:

<https://github.com/florinpop17/app-ideas/blob/master/README.md>

I tried to spend only a couple of hours on each with the personal
goal to practice more.

I'm also trying to reinforce concepts. These will have small
demonstrations with similar goal to practice and reinforce
the ideas.

## Setup

Each project has a separate installation.

React sites follow this setup to run on `https://localhost:3000`:

```cmd
npm install
npm start
```

C# projects follow this setup to run console applications:

```cmd
dotnet build
dotnet run
```

Java projects follow this setup to run console applications:

```cmd
java src/Main.java
```

Java projects with Maven and Spring Boot will run command line
tool and service like this:

```cmd
// 1. Run command line tool
java ./src/main/java/com/msnider/habittracker/Main.java

// 2. Run spring boot service
mvn spring-boot:run
```

## Projects

| Project            | Description                                                                                                                                                                                             |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| book-finder        | Searches Google Books to return the top 10 results. Requires `REACT_APP_GOOGLE_BOOKS_API_KEY` in `.env.local`, create keys at [Google Cloud Console](https://console.cloud.google.com/apis/credentials) |
| calculator         | Basic calculator with limited functionality                                                                                                                                                             |
| cli-todo-list      | Simple todo list in java to get reacquainted with the programming language                                                                                                                              |
| drawing            | Simple drawing pad that supports multiple colors and brush sizes                                                                                                                                        |
| emoji              | Translator of text to emojis with a finite set of supported words                                                                                                                                       |
| first-db-app       | Site that uses IndexedDB to load and query an in-browser database                                                                                                                                       |
| flash-cards        | Flash card site with questions made up from my currently studied topics                                                                                                                                 |
| game-poll          | Work-in-progress app for creating polls with LocalStorage; I'm planning to add an API and database                                                                                                      |
| habit-tracker      | Track your habits and get a summary of your progress. CLI and Spring Boot REST service in java.                                                                                                         |
| k8s-spring-boot    | Following a tutorial to learn how to setup spring boot with k8s                                                                                                                                         |
| notes              | Note site with limited functionality, storing data in browser's LocalStorage                                                                                                                            |
| otp-locker         | Design problem turned into a Redis integration                                                                                                                                                          |
| quiz               | Fun, easy math quiz for practice                                                                                                                                                                        |
| short-id-generator | Design practice problem turned into a MongoDB database                                                                                                                                                  |

## Concepts

| Concept        | Description |
| -------------- | ----------- |
| multithreading | threadpool  |

## Tutorials

| Tutorial | Description                    |
| -------- | ------------------------------ |
| go       | Introduction to go programming |
