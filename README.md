# Spring Security API

This is a RESTful API built with Spring Boot and Spring Security. It provides user registration, login, and management functionalities.

## Features

- User registration: New users can register by providing a username and password.
- User login: Registered users can login by providing their username and password. Upon successful login, a JWT token is returned.
- User management: The API provides endpoints for retrieving a user by username, retrieving all users, deleting a user by ID, and updating a user by ID. These endpoints are only accessible to users with the `ROLE_ADMIN` role.
- Password change: Logged in users can change their password by providing their old password and the new password.
- Token renewal: Logged in users can renew their JWT token.

Sure, here's a more detailed description of the endpoints:


### Endpoints

1. **Register a New User**
   - *POST /users/register*
   - **Request Body:**
     ```json
     { 
     "username": "<Your-username>", 
     "password": "<Your-password>" 
     }
     ```
   - **Successful Response:**
      - HTTP 200 OK with a message indicating successful registration.
   - **Error Response:**
      - HTTP 400 Bad Request with a message indicating the username already exists.

2. **Authenticate a User and Return a JWT Token**
   - *POST /users/login*
   - **Request Body:**
     ```json
     { 
     "username": "<Your-username>", 
     "password": "<Your-password>" 
     }
     ```
   - **Successful Response:**
      - HTTP 200 OK with the JWT token in the response body.
   - **Error Response:**
      - HTTP 401 Unauthorized with a message indicating incorrect username or password.

3. **Retrieve a User by Username**
   - *GET /users/{name}*
   - **Path Variable:**
      - name - The username of the user.
   - **Successful Response:**
      - HTTP 200 OK with the user details in the response body.
   - **Error Response:**
      - HTTP 404 Not Found if the user is not found.

4. **Retrieve All Users**
   - *GET /users/all*
   - **Successful Response:**
      - HTTP 200 OK with a list of all users in the response body.
   - **Error Response:**
      - HTTP 404 Not Found if no users are found.

5. **Delete a User by ID**
   - *DELETE /users/{id}*
   - **Path Variable:**
      - id - The ID of the user.
   - **Successful Response:**
      - HTTP 200 OK if the user is successfully deleted.
   - **Error Response:**
      - HTTP 404 Not Found if the user is not found.

6. **Update a User by ID**
   - *PUT /users/{id}*
   - **Request Body:**
     ```json
     { 
     "username": "<new_username>", 
     "password": "<new_password>" 
     }
     ```
   - **Path Variable:**
      - id - The ID of the user.
   - **Successful Response:**
      - HTTP 200 OK with a message indicating successful update.
   - **Error Response:**
      - HTTP 404 Not Found if the user is not found.

7. **Change the Password of the Currently Logged-in User**
   - *PUT /users/changePass*
   - **Request Body:**
     ```json
     { 
     "oldPassword": "<old_password>", 
     "newPassword": "<new_password>", 
     "confirmPassword": "<confirm_password>" 
     }
     ```
   - **Successful Response:**
      - HTTP 200 OK with a message indicating successful password change.
   - **Error Response:**
      - HTTP 400 Bad Request with an error message if the password change fails.

8. **Renew the JWT Token of the Currently Logged-in User**
   - *POST /users/renewToken*
   - **Successful Response:**
      - HTTP 200 OK with the new JWT token in the response body.
   - **Error Response:**
      - HTTP 401 Unauthorized with a message indicating token renewal failure.



## Diagram

![Diagram](Diagram%2FSamer.png)


## Setup

To run this project, you need to have Java and Maven installed on your machine. You can then clone the repository and run the application with the following commands:



```bash
git clone https://github.com/Samer-Ismael/WebSecurity.git
cd yourrepository
mvn spring-boot:run
