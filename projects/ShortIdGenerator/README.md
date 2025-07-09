# Short Id Generator

My goal with this project is to take a simple procedure
I learned from a design practice problem. Specifically,
it's for generating tiny urls with an idea of using a
sequencer to reserve urls for later use, then tracking
them in a database.

## Concept

My concept is to just make a simple service for now
that can get all the data, post text to reserve an id,
and a background process for adding unavailable. In
a real system, the service would need to consider
distributing the workload like for generating ids:

- Service
  - Background Thread:
    - Reads Database for Available every 5 seconds
    - When there is less than 50 ids, creates new
      ones in the sequence, and writes to database
  - GET /ids/v1
    - `{ "reserved": [ ... ], "available": [ ... ]}`
  - POST /ids/v1
    - `{ "text": "My text" }`
    - `{ "id": "...", "created": "...", "text": "..." }`
- Database (MongoDB)
  - Available (Id, Created)
  - Reserved (Id, Created, Text)
