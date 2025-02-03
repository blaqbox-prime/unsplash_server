# Unsplash Clone Backend

## Overview
This project is a backend service for an Unsplash clone built with Spring Boot. It provides secure image storage and management capabilities with a cloud-native architecture. The backend integrates user authentication and authorization using Spring Security with JWT, MongoDB for data storage, and Azure Blob Storage for image hosting.

## Features
- **User Authentication and Authorization:** Implements Spring Security and JWT for secure access.
- **Image Storage:** Stores images efficiently using Azure Blob Storage.
- **Database Integration:** Utilizes MongoDB through Spring Data MongoDB.
- **RESTful API Endpoints:** Provides endpoints for user registration, image uploads, and retrieval.

## Technology Stack
- **Backend Framework:** Spring Boot  
- **Security:** Spring Security, JWT  
- **Database:** MongoDB  
- **Cloud Storage:** Azure Blob Storage  
- **Build Tool:** Gradle

## Prerequisites
- **Java 17 or higher**
- **MongoDB (local or cloud instance)**
- **Azure Storage Account**
- **Gradle**

## Setup Instructions
### 1. Clone the Repository
```bash
$ git clone https://github.com/your-repo-url/unsplash-clone-backend.git
$ cd unsplash-clone-backend
```

### 2. Configure Environment Variables
Create a `.env` file in the root directory or configure environment variables manually:
```
MONGO_URI=<your-mongodb-uri>
JWT_SECRET=<your-jwt-secret>
AZURE_STORAGE_CONNECTION_STRING=<your-azure-connection-string>
AZURE_CONTAINER_NAME=<your-container-name>
```

### 3. Build the Project
```bash
$ ./gradlew clean build
```

### 4. Run the Application
```bash
$ ./gradlew bootRun
```
The backend service will start on `http://localhost:8080` by default.

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
|├── application.yml
|├── build.gradle
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

## Testing
Run unit and integration tests using Gradle:
```bash
$ ./gradlew test
```

## Deployment
Deploy the application to your preferred cloud platform. Ensure MongoDB and Azure Storage services are configured.

## Contribution
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License.
