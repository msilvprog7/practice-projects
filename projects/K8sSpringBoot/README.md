# K8s Spring Boot

I'd like to learn how to setup a local k8s cluster with a
spring boot service so I'm going to follow this tutorial:

<https://spring.io/guides/gs/spring-boot-kubernetes>

## Setup

Windows setup with PowerShell, Choco:

| Dependency | Description                       | Link                                                                                                | Steps                          | Verify                     |
| ---------- | --------------------------------- | --------------------------------------------------------------------------------------------------- | ------------------------------ | -------------------------- |
| WSL        | Linux-like command line           | <https://learn.microsoft.com/en-us/windows/wsl/install>                                             | `wsl --install`                | `wsl --version`            |
| kubectl    | Kubernetes command line           | <https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/#install-nonstandard-package-tools> | `choco install kubernetes-cli` | `kubectl version --client` |
| kind       | Create a local Kubernetes cluster | <https://kind.sigs.k8s.io/docs/user/quick-start/>                                                   | `choco install kind`           | `kind --version`           |
| Maven      | Build and run spring boot service | <https://maven.apache.org/install.html>                                                             | `choco install maven`          | `maven --version`          |

## Run

Here are steps to run each component:

| Component      | Description                                               | Commands              |
| -------------- | --------------------------------------------------------- | --------------------- |
| localhost:8080 | Run the spring boot service on localhost to debug the API | `mvn spring-boot:run` |
