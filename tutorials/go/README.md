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

Artifacts are files or directories created when the
pipeline runs. Pipelines can depend on the artifacts
of other pipelines. Pipelines can reference artifacts
from ancestor pipelines which supports the concept
of only building once to avoid issues re-building
in various places and environments in the pipeline.

Custom tabs are used to display output files as
their own tab in the job run. For instance, the
tutorial has an html artifact that can be displayed
as a custom tab. Updating it can then be displayed
on previous job runs.

Value stream map (VSM) is an end-to-end deployment
pipeline that offers abstraction, build once,
parallelization, and traceability. Fan-out describes
when there are multiple pipelines that depend on
a pipeline (1 to N) like a material. Fan-in describes
when a pipeline depends on multiple pipelines (N to 1)
where its important that the versions are consistent
to trigger the dependent pipeline. It's important
that GoCD offers correctness and quicker feedback
during a deployment.

There are also more advanced features like
source control for pipelines (json or yaml),
running on a kubernetes cluster, and elastic
agents.

<https://docs.gocd.org/current/gocd_on_kubernetes/introduction.html>

GoCD server and agents can run on a kubernetes cluster as well.
The tutorial goes through running minikube to run the server
and agent.

```cmd
minikube start

helm repo add gocd https://gocd.github.io/helm-chart
helm repo update

helm install gocd gocd/gocd --namespace gocd --create-namespace

kubectl get svc --all-namespaces
minikube service gocd-server -n gocd
```

Once you setup your Docker Hub account, you can setup an
Artifact Store on the GoCD server. For the next step, I had
to setup a service account and create a secret token:

```cmd
kubectl create serviceaccount my-api-user
kubectl create clusterrolebinding my-api-user-binding \
  --clusterrole=cluster-admin \
  --serviceaccount=default:my-api-user
```

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-api-user-token
  annotations:
    kubernetes.io/service-account.name: my-api-user
type: kubernetes.io/service-account-token
```

```cmd
kubectl apply -f token-secret.yaml
kubectl get secret my-api-user-token -o jsonpath="{.data.token}" | base64 --decode
```

Then I could apply it in the secrets-for-gocd.yaml.
If you're using a personal account, use the same
base64 encoding of your username as the DOCKERHUB_ORG.
The rest of the steps ensured I had an Elastic Profile
with access to the local k8s cluster and used the
configuration for the bulletin board project from git.
I did need to add a rule to allow pipeline group sample.

I am still debugging the agents building the steps.
It seems like there's an issue with java version
for the build steps:

```text
jvm 1    | Caused by: java.lang.UnsupportedClassVersionError: com/thoughtworks/go/agent/launcher/AgentLauncherImpl has been compiled by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 59.0
jvm 1    | 	at java.base/java.lang.ClassLoader.defineClass1(Native Method)
jvm 1    | 	at java.base/java.lang.ClassLoader.defineClass(Unknown Source)
jvm 1    | 	at java.base/java.security.SecureClassLoader.defineClass(Unknown Source)
jvm 1    | 	at java.base/java.net.URLClassLoader.defineClass(Unknown Source)
jvm 1    | 	at java.base/java.net.URLClassLoader$1.run(Unknown Source)
jvm 1    | 	at java.base/java.net.URLClassLoader$1.run(Unknown Source)
jvm 1    | 	at java.base/java.security.AccessController.doPrivileged(Unknown Source)
jvm 1    | 	at java.base/java.net.URLClassLoader.findClass(Unknown Source)
jvm 1    | 	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
jvm 1    | 	at java.base/java.lang.ClassLoader.loadClass(Unknown Source)
jvm 1    | 	at com.thoughtworks.go.agent.bootstrapper.DefaultAgentLauncherCreatorImpl.loadClass(DefaultAgentLauncherCreatorImpl.java:68)
jvm 1    | 	at com.thoughtworks.go.agent.bootstrapper.DefaultAgentLauncherCreatorImpl.createLauncher(DefaultAgentLauncherCreatorImpl.java:58)
jvm 1    | 	at com.thoughtworks.go.agent.bootstrapper.AgentBootstrapper.go(AgentBootstrapper.java:73)
jvm 1    | 	at com.thoughtworks.go.agent.bootstrapper.AgentBootstrapper.lambda$main$0(AgentBootstrapper.java:57)
jvm 1    | 	at com.thoughtworks.go.logging.LogConfigurator.runWithLogger(LogConfigurator.java:53)
jvm 1    | 	at com.thoughtworks.go.agent.bootstrapper.AgentBootstrapper.main(AgentBootstrapper.java:57)
```
