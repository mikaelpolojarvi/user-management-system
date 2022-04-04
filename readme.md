#User management system for fun

## Introduction
This module serves as API for user resource management. SCIM protocol defines the endpoints
for the user management, but the user resource defined in here is not as rich as defined in SCIM.
The module is missing also many other features introduced in SCIM, but it has the most basic ones.

More info about SCIM:
- https://datatracker.ietf.org/doc/html/rfc7643
- https://datatracker.ietf.org/doc/html/rfc7644
- https://datatracker.ietf.org/doc/html/rfc7642

This module starts a server which listens to four endpoints. Users can be created, updated and fetched from database. 
Bearer authentication is used to authenticate the requests.

## Requirements
- Redis server running on default port (6379)

## How to run locally
- Clone this repository
- Start your Redis server on default port ```sudo service redis-server start```
- Configure your bearer value for authentication in Main.kt
- Start the module from Main.kt
- Note that this module does not save any information after server shutdown if it isn't otherwise defined
      
## Endpoints

- POST /Users
  - Create user from provided JSON
  - Example request body:
  ```
  {
    "firstName":"John",
    "lastName":"Doe",
    "emailAddress":"john.doe@example.com",
    "title":"Software Developer"
  }
  ```
  - Example response from server: 201 Created
  ```
  {
    "firstName": "John",
    "lastName": "Doe",
    "emailAddress": "john.doe@example.com",
    "title": "Software Developer",
    "uuid": "72010590-fc2a-4c2a-a7ce-76a904b9f6e4"
  }
  ```
- GET /Users
  - Get all users in the database
- PUT /User/{uuid}
  - Update user
  - Use same body as in user creation
  - All attributes but uuid can be updated
  
## Improvement ideas
- More endpoints and features from SCIM protocol
  - User groups, extensions etc.
- Redis improvements:
  - Automatic saving to disk
  - Prefixes to keys
