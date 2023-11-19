# Spring Security API

This is a RESTful API built with Spring Boot and Spring Security. It provides user registration, login, and management functionalities.

## Features

- User registration: New users can register by providing a username and password.
- User login: Registered users can login by providing their username and password. Upon successful login, a JWT token is returned.
- User management: The API provides endpoints for retrieving a user by username, retrieving all users, deleting a user by ID, and updating a user by ID. These endpoints are only accessible to users with the `ROLE_ADMIN` role.
- Password change: Logged in users can change their password by providing their old password and the new password.
- Token renewal: Logged in users can renew their JWT token.

## Endpoints

- `POST /users/register`: Register a new user.
- `POST /users/login`: Login a user.
- `GET /users/{name}`: Get a user by username.
- `GET /users/all`: Get all users.
- `DELETE /users/{id}`: Delete a user by ID.
- `PUT /users/{id}`: Update a user by ID.
- `PUT /users/changePass`: Change the password of the logged in user.
- `POST /users/renewToken`: Renew the JWT token of the logged in user.

## Setup

To run this project, you need to have Java and Maven installed on your machine. You can then clone the repository and run the application with the following commands:

## Diagram

![Diagram](Diagram%2FSamer.png)


```bash
git clone https://github.com/Samer-Ismael/WebSecurity.git
cd yourrepository
mvn spring-boot:run
