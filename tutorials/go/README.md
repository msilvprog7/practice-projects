# Go tutorial

## Getting started

<https://go.dev/doc/tutorial/getting-started>

In the getting started tutorial, you learn how to setup
go and some fundamentals about go packages. The `go.mod`
file is first setup, and then tidied as dependencies
are added. It can help to redirect local packages
before each has been published. Each package can be
tested as well, then built and installed to the local
directory.

## Go tour

<https://go.dev/tour/list>

There are a variety of types that can be used and
inferred when declared and assigned. There are nice
short hands for naming multiple variable or parameters
of the same type or returning named variables from
short methods for readability. Flow control statements
are based on for loops and typically allow variable
declaration and assignment in an optional statement
for if and switch statements. Cases do not need to be
constants and can be calculated. This makes it a clean
way to write long if-else chains and have them organized
as cases in a switch statement. There is also support
for pointers, structs, arrays, and slices. Slices are
particularly interesting because they create views on
top of arrays and can be adjusted later or declared
and initialized with the underlying array too. There
are a lot of options for creating types like slices,
maps, and passing functions in methods.

Interfaces allow for implicit implementations of
types by describing which functions are required
for the type. Specifically, using receiver arguments
to describe functions for a specific receiver type.
These allow for interesting ways to provide more
nuanced types like stringers, errors, readers,
and images with specific implementations of
common functionality.

Generics are supported by providing `[T any]`
or supporting a constraint like `[T comparable]`.
This can be applied to functions or types.

Concurrency is well supported with the keyword `go`,
channels to message pass, and synchronization objects
like mutexes and wait groups.

## Gin web service framework

<https://go.dev/doc/tutorial/web-service-gin>

Gin is a web service framework that supports
routing for JSON endpoints. It makes it easy
to create endpoints to support RESTful APIs.

Curl commands useful to test:

```cmd
curl http://localhost:8080/albums

curl http://localhost:8080/albums/2

curl http://localhost:8080/albums \
    --include \
    --header "Content-Type: application/json" \
    --request "POST" \
    --data '{"id": "4","title": "The Modern Sound of Betty Carter","artist": "Betty Carter","price": 49.99}'
```

The website includes examples for more complex
behavior like how to setup middleware, customize
input, and deploy a gin web service.

## GoCD continuous deployment pipelines

<https://www.gocd.org/getting-started/part-1/>

Users interact with the Server to provide work to
the Agents. Pipelines represent workflows for
application tasks like test, build, and deploy
that can be modeled with stages, jobs, and tasks
by running commands. Materials are causes for
pipelines to trigger like source control repo
and commit to that repo. At least one Material
is needed for a Pipeline.

Server can be started with Docker on port 8153:

```cmd
docker run -d -p8153:8153 gocd/gocd-server:v25.3.0
```

Agent can be started with Docker referencing the Server:

```cmd
docker run -d -e GO_SERVER_URL=http://host.docker.internal:8153/go gocd/gocd-agent-alpine:v25.3.0
```

In the simplest form, a pipeline can be created with
one command, which requires one stage with one job
to run the command like `./build`. Tasks are commands
like `./build`, Jobs are sequences of tasks that stops
executing after the first task to fail, and Stages
are collections of jobs that can be independent
of one another and thus run non-sequentially in
parallel. Materials can include pipelines to make
pipelines dependent on one another for more complex
pipelines like fan-in and fan-out.
