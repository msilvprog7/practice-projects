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
