# K8s Spring Boot

I'd like to learn how to setup a local k8s cluster with a
spring boot service so I'm going to follow this tutorial:

<https://spring.io/guides/gs/spring-boot-kubernetes>

## Setup

Windows setup with PowerShell, Choco:

| Dependency  | Description                                                                              | Link                                                                                                | Steps                                    | Verify                          |
| ----------- | ---------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ---------------------------------------- | ------------------------------- |
| WSL         | Linux-like command line                                                                  | <https://learn.microsoft.com/en-us/windows/wsl/install>                                             | `wsl --install`                          | `wsl --version`                 |
| kubectl     | Kubernetes command line                                                                  | <https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/#install-nonstandard-package-tools> | `choco install kubernetes-cli`           | `kubectl version --client`      |
| kind        | Create a local Kubernetes cluster                                                        | <https://kind.sigs.k8s.io/docs/user/quick-start/>                                                   | `choco install kind`                     | `kind --version`                |
| Maven       | Build and run spring boot service                                                        | <https://maven.apache.org/install.html>                                                             | `choco install maven`                    | `maven --version`               |
| Spring Boot | Create a spring boot service with actuator (health) and webflux (reactive web framework) | <https://start.spring.io/>                                                                          | Build and extract files for this project | `pom.xml` includes dependencies |
| k9s         | View pods running                                                                        | <>                                                                                                  | `choco install k9s`                      | `k9s`                           |

## Run

Here are steps to run each component:

| Component                    | Description                                                       | Commands                                               |
| ---------------------------- | ----------------------------------------------------------------- | ------------------------------------------------------ |
| localhost:8080               | Run the spring boot service on localhost to debug the API         | `mvn spring-boot:run`                                  |
| jar                          | Build jar to target/                                              | `mvn install`                                          |
| docker image                 | Build docker image                                                | `mvn spring-boot:build-image`                          |
| localhost:8080               | Run docker image                                                  | `docker run -p 8080:8080 k8sspringboot:0.0.1-SNAPSHOT` |
| local kubernetes cluster     | Setup a local kubernetes cluster using kind                       | `kind create cluster`                                  |
| local kubernetes cluster     | Get cluster info of default kind cluster                          | `kubectl cluster-info --context kind-kind`             |
| local kubernetes cluster pod | Run deployment to run springboot/demo on pod with service on 8080 | `kubectl apply -f deployment.yaml`                     |
| local kubernetes cluster pod | Check deployed kubernetes resources                               | `kubectl get all`                                      |
| local kubernetes cluster pod | Port forward to access the pod's endpoint                         | `kubectl port-forward service/demo 8080:8080`          |
| local kubernetes cluster pod | Check running pods and logs                                       | `k9s`                                                  |

## Next Steps

- Build docker image to file
- Deploy to kind cluster from docker image file
- Build docker image to local registry
- Deploy to kind cluster from local registry to simulate a production setup
