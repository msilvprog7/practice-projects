# Practice Projects

Hello! This is my collection of practice projects I've been developing
to practice software engineering skills. It's based on ideas from:

<https://github.com/florinpop17/app-ideas/blob/master/README.md>

I tried to spend only a couple of hours on each with the personal
goal to practice more.

## Setup

Each project has a separate installation.

React sites follow this setup to run on `https://localhost:3000`:

```cmd
npm install
npm start
```

## Projects

| Project      | Description                                                                                                                                                                                             |
| ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| book-finder  | Searches Google Books to return the top 10 results. Requires `REACT_APP_GOOGLE_BOOKS_API_KEY` in `.env.local`, create keys at [Google Cloud Console](https://console.cloud.google.com/apis/credentials) |
| calculator   | Basic calculator with limited functionality                                                                                                                                                             |
| drawing      | Simple drawing pad that supports multiple colors and brush sizes                                                                                                                                        |
| emoji        | Translator of text to emojis with a finite set of supported words                                                                                                                                       |
| first-db-app | Site that uses IndexedDB to load and query an in-browser database                                                                                                                                       |
| flash-cards  | Flash card site with questions made up from my currently studied topics                                                                                                                                 |
| game-poll    | Work-in-progress app for creating polls with LocalStorage; I'm planning to add an API and database                                                                                                      |
| notes        | Note site with limited functionality, storing data in browser's LocalStorage                                                                                                                            |
| quiz         | Fun, easy math quiz for practice                                                                                                                                                                        |
