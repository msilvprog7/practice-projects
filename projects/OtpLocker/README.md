# OTP Locker

Based on a design problem, the idea is to explore integrating with Redis cache.

## Concept

My concept is that the locker should have limited space for packages and
generate a unique OTP for each package. This can be represented through apis:

- Service
  - POST /api/lockers/v1/dropoff
    - Package can be stored
      - `{ "package": { "width": 1.5, "height": 0.5, ", "units": "feet" } }`
      - 201 Created `{ "otp": "123456" }`
    - Package cannot be stored
      - 409 Conflict `{ "message": "there is not enough capacity for your package" }`
  - POST /api/lockers/v1/pickup
    - OTP is correct
      - `{ "otp": "123456" }`
      - `{ "package": { ... } }`
    - OTP is incorrect
      - 401 Unauthorized `{ "message": "there is no package found for your package" }`
- Storage (Redis)
  - Key: OTP like `123456`
  - Value: Locker Id
  - Eviction policy: None, unless caching to permanent storage

## Setup

| Dependencies | Link                                                    | Description                                                                           |
| ------------ | ------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| Java         | <https://www.oracle.com/java/technologies/downloads/>   | Install latest Java and setup JAVA_HOME, VS code extensions, etc.                     |
| Gradle       | <https://gradle.org/install/>                           | Use `./gradlew` in the project and setup any environment specific `gradle.properties` |
| Redis        | <https://redis.io/learn/howtos/quick-start#setup-redis> | Install redis stack server and tools like Redis Insight                               |
| Postman      | <https://www.postman.com/downloads/>                    | Install postman to send API requests to run against the service                       |

## Development

| Step        | Commands                                            | Notes                                                |
| ----------- | --------------------------------------------------- | ---------------------------------------------------- | --- | --- |
| Build       | `./gradlew build`                                   | Compile the code, gradlew also works in this project |
| Test        | `./gradlew test`                                    | Run the tests, VS code extensions can be helpful too |
| Run         | `./gradlew bootRun`                                 | Run the service on `localhost:8080`                  |
| Redis       | `redis-stack-server`                                | Run redis on `localhost:6379`                        |
| Postman     | `Otp Locker API.postman_collection.json`            | API requests to import and run against the service   |
| API DropOff | `POST http://localhost:8080/api/lockers/v1/dropoff` | See postman scripts for example request              |
| API PickUp  | `POST http://localhost:8080/api/lockers/v1/pickup`  | See postman scripts for example request              |     |     |
