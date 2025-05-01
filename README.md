# Unsplash Clone Backend

## Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Configuration](#configuration)
- [Key Source Code Files](#key-source-code-files)
- [Build and Run](#build-and-run)
- [Service Documentation](#service-documentation)

## Overview
This project is a backend service for an Unsplash clone built with Spring Boot. It provides secure image storage and management capabilities with a cloud-native architecture. The backend integrates user authentication and authorization using Spring Security with JWT, MongoDB for data storage, and Azure Blob Storage for image hosting.

## Key Features
- **User Authentication and Authorization:** Implements Spring Security and JWT for secure access.
- **Image Storage:** Stores images efficiently using Azure Blob Storage.
- **Database Integration:** Utilizes MongoDB through Spring Data MongoDB.
- **RESTful API Endpoints:** Provides endpoints for user registration, image uploads, and retrieval.

## Technology Stack
- **Backend Framework:** Spring Boot  
- **Security:** Spring Security, JWT  
- **Database:** MongoDB  
- **Cloud Storage:** Azure Blob Storage  
- **Build Tool:** Maven

## Prerequisites
- **Java 17 or higher**
- **MongoDB (local or cloud instance)**
- **Azure Storage Account**
- **Maven**

## Configuration
The application uses the following configurations:
- **application.yml:** Holds environment-specific configurations.
- **Gradle Build:** Dependency management and build tool.

Ensure you have the necessary environment variables for database connections and JWT secret keys.

## Key Source Code Files

### `SecurityConfig.java`
This file handles the application's security configuration.
- Disables CSRF protection.
- Configures request authorization paths.
- Enables JWT-based stateless authentication.
- Adds a custom `JwtAuthFilter` to the security chain.

### `JwtService.java`
This service handles JWT operations.
- Generates JWT tokens.
- Extracts claims and validates tokens.
- Encodes the JWT using HMAC SHA-256.

### `JwtAuthFilter.java`
Custom filter to authenticate requests.
- Intercepts requests to validate JWT tokens.
- Loads user details and sets security context upon successful validation.

### `AppConfig.java`
Configuration class for beans related to authentication and password encoding.
- Provides `AuthenticationManager`, `UserDetailsService`, and password encoding using BCrypt.

### `Photo.java`
Model class representing a image document in MongoDB.
- Fields: `label`, `_id`, `url`, `date_added`, and `likes`.
- Methods for managing likes and image data.

### `Profile.java`
Model class representing a user profile document.
- Fields: `_id`, `userId`, `username`, `followers`, and `images`.
- Methods to manage followers and images.

### `PhotoController.java`
Handles image-related API endpoints.
- Retrieve all images or sorted images.
- Add a new image.
- Search images by text.

### `ProfileController.java`
Handles profile-related API endpoints.
- Create and update profiles.
- Fetch profiles by username.
- Follow other profiles.

## Build and Run
### Prerequisites
- Java 17 or higher
- MongoDB
- Gradle

### Commands
1. **Build:**
   ```bash
   ./gradlew build
   ```
2. **Run:**
   ```bash
   ./gradlew bootRun
   ```

The application should now be accessible at `http://localhost:8080`.


## API Endpoints
### **Authentication**
- `POST /api/auth/register`: Register a new user.
- `POST /api/auth/login`: Authenticate a user and receive a JWT.

### **Image Management**
- `POST /api/images/upload`: Upload a new image (Authenticated).
- `GET /api/images/{id}`: Retrieve an image by its ID (Authenticated).
- `DELETE /api/images/{id}`: Delete an image by its ID (Authenticated).

## Security Configuration
- **JWT Authentication:** Secures API endpoints by requiring a valid JWT.
- **Stateless Sessions:** Ensures the application does not maintain server-side sessions.
- **Role-based Access Control (RBAC):** Defines access rules for endpoints.

## Image Storage
- Images are stored in Azure Blob Storage.
- Ensure your Azure Storage Account and container are properly configured and accessible.

## Project Structure
```
unsplash-clone-backend/
|├── src/main/java/
|   ├── com.example.unsplashclone/
|       ├── controller/
|       ├── service/
|       ├── repository/
|       ├── model/
|       ├── security/
|├── application.properties
|├── pom.xml
|└── .env
```

## Example Request
### Upload Image
**Request:**
```bash
POST /api/images/upload
Authorization: Bearer <jwt-token>
Content-Type: multipart/form-data
```
**Response:**
```json
{
  "id": "abc123",
  "url": "https://yourstorageaccount.blob.core.windows.net/container-name/image.jpg",
  "uploadedAt": "2025-02-03T12:00:00Z"
}
```

## Service Documentation

### `ProfileService.java`
#### Overview
This service manages operations related to user profiles, including creation, updates, and follow functionality.

#### Methods
- `createProfile(ProfileRequest details)`: Creates a new user profile.
  - **Input:** ProfileRequest object containing `userId` and `username`.
  - **Output:** Persisted Profile object.

- `updateProfile(Profile updated)`: Updates an existing profile.
  - **Input:** Updated Profile object.
  - **Output:** Updated Profile object.

- `fetchProfile(String username)`: Fetches a profile by username.
  - **Input:** Username string.
  - **Output:** Optional Profile object.

- `followProfile(FollowRequest details)`: Adds a follower to a user profile.
  - **Input:** FollowRequest object with `currentUsername` and `usernameToFollow`.
  - **Output:** Boolean indicating success.

### `PhotoService.java`
#### Overview
This service handles operations related to image management, including adding images.

#### Methods
- `addPhoto(String url, String label, String username)`: Adds a image to a user's profile.
  - **Input:** Photo URL, label, and username.
  - **Output:** Persisted Photo object.
  - **Throws:** `ProfileNotFoundException` if the username does not exist.

### `ImageStorageClient.java`
#### Overview
Interface for image storage operations.

#### Methods
- `UploadImage(String containerName, String originalImageName, InputStream data, long length)`: Uploads an image to a storage container.
  - **Input:** Container name, image name, input stream, and length.
  - **Output:** URL of the uploaded image.

### `AzureImageStorageClient.java`
#### Overview
Azure-based implementation of the `ImageStorageClient` interface.

#### Methods
- `UploadImage(String containerName, String originalImageName, InputStream data, long length)`: Uploads an image to an Azure Blob Storage container.
  - **Input:** Container name, image name, input stream, and length.
  - **Output:** Blob URL.
  - **Details:** Generates a unique filename and uploads the image, ensuring overwrite protection.


## License
This project is licensed under the MIT License.


