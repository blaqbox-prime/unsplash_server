# Unsplash Clone - Full Stack Photo Sharing Application

This is a full-stack photo-sharing web application inspired by Unsplash. It features a Java Spring Boot backend with a RESTful API and a React frontend. This project is designed to showcase a modern, scalable, and well-documented application, perfect for a developer portfolio.

## Live Demo

[Link to the live application](https://your-live-demo-link.com)

## Features

*   **User Authentication:** Secure user registration and login using JWT authentication.
*   **Image Management:** Upload, view, and manage your photos.
*   **Image Collections:** Create and manage collections of photos.
*   **Social Features:** Follow other users.
*   **Search:** Powerful search functionality to discover photos.
*   **API Documentation:** Interactive API documentation powered by Swagger UI.

## Tech Stack

### Backend

*   **Java 17**
*   **Spring Boot 3**
*   **Spring Data MongoDB:** For database interaction.
*   **Spring Security:** For authentication and authorization with JWT.
*   **Azure Blob Storage:** For storing and managing image files.
*   **SpringDoc (Swagger UI):** For API documentation.
*   **Lombok:** To reduce boilerplate code.
*   **Gradle:** As the build tool.

### Frontend

*   **React**
*   **React Router**
*   **(Other frontend libraries...)**

## Project Structure

The backend is organized into the following key packages:

*   **Controllers:** Handle incoming HTTP requests and delegate to services.
*   **Models:** Define the data structures of the application (Entities, DTOs, and Requests).
*   **Services:** Contain the business logic of the application.
*   **Repositories:** Provide an abstraction over the database.
*   **Exceptions:** Custom exceptions for handling specific error scenarios.

## API Documentation

The API is documented using Swagger UI. Once you run the application, you can access the interactive API documentation at `http://localhost:8080/swagger-ui.html`.

### Authentication API (`/api/v1/auth`)

| Method   | Endpoint        | Description                               |
| :------- | :-------------- | :---------------------------------------- |
| `POST`   | `/register`     | Register a new user.                      |
| `POST`   | `/authenticate` | Authenticate a user and receive a JWT.    |
| `GET`    | `/me`           | Check if the session token is valid.      |

### Images API (`/api/v1/images`)

| Method | Endpoint                  | Description                               |
| :----- | :------------------------ | :---------------------------------------- |
| `GET`  | `/`                       | Get all images.                           |
| `GET`  | `/latest`                 | Get all images, sorted by the latest.     |
| `GET`  | `/{id}`                   | Get a specific image by its ID.           |
| `POST` | `/`                       | Upload a new image.                       |
| `GET`  | `/search?query={query}`   | Search for images.                        |
| `GET`  | `/profile/{username}`     | Get all images uploaded by a user.        |

### Collections API (`/api/v1/collections`)

| Method   | Endpoint                  | Description                       |
| :------- | :------------------------ | :-------------------------------- |
| `GET`    | `/`                       | Get all collections.              |
| `POST`   | `/`                       | Create a new collection.          |
| `POST`   | `/{collectionId}/images`  | Add an image to a collection.     |
| `PATCH`  | `/{id}`                   | Update a collection's details.    |
| `DELETE` | `/{id}`                   | Delete a collection.              |
| `DELETE` | `/{id}/images/{imageId}`  | Remove an image from a collection.|

### Profile API (`/api/v1/profiles`)

| Method   | Endpoint             | Description                   |
| :------- | :------------------- | :---------------------------- |
| `POST`   | `/`                  | Create a new profile.         |
| `GET`    | `/`                  | Get all profiles.             |
| `GET`    | `/{username}`        | Get a profile by username.    |
| `PUT`    | `/{username}/update` | Update a profile.             |
| `POST`   | `/follow`            | Follow a user.                |

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Java JDK 17 or later
*   Gradle 7.x or later
*   MongoDB instance (local or cloud)
*   Azure Storage Account

### Installation

1.  **Clone the repo**
    ```sh
    git clone https://github.com/your_username/your_repository.git
    ```
2.  **Navigate to the project directory**
    ```sh
    cd unsplash_server
    ```
3.  **Configure the application**
    Open `src/main/resources/application.properties` and update the following properties with your credentials:
    ```properties
    # MongoDB Configuration
    spring.data.mongodb.uri=mongodb://localhost:27017/your_db_name

    # JWT Secret
    jwt.secret=your_jwt_secret

    # Azure Storage Configuration
    azure.storage.connection-string=your_azure_storage_connection_string
    azure.storage.container-name=your_azure_container_name
    ```
4.  **Run the application**
    ```sh
    ./gradlew bootRun
    ```
    The application will be available at `http://localhost:8080`.

## License

Distributed under the MIT License. See `LICENSE` for more information.
