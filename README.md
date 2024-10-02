# Customer Notification Address Facade System

The **Customer Notification Address Facade System** is a microservice designed to centralize and manage customer contact information and preferences for notifications. This system serves as a single source of truth for all recipient addresses and delivery statuses, enabling other systems within the ecosystem to efficiently fetch and update customer delivery data.

## Architecture

The architecture of the system consists of several key components:

### Gateway

The **Gateway** acts as a single entry point for all incoming requests to the microservices. It is configured with OAuth2 security, ensuring that only authenticated requests can access the underlying services.

In the provided Keycloak realm, there are three clients configured:

- **Client ID**: `addressManagement-service`
  - This is our primary microservice, responsible for managing customer addresses and notification preferences.
  - It has an admin role, allowing it to perform administrative functions related to address management.
  - Under this client, we have configured one user with admin privileges, who can access the management functionalities.

- **Client 2 (Client ID: `CustomerInfoProvider`)**
  - This client is designated to provide and update customer data.
  - It will interact with the Address Management Microservice to retrieve necessary customer information.

- **Client 3 (Client ID: `NotificationSenderAPI`)**
  - This client is responsible for providing and updating notification statuses.
  - It will utilize the data from the Address Management Microservice to ensure that notifications are sent to the correct recipients.

The configuration of these clients allows the system to interact with external services effectively while maintaining security through OAuth2 authentication. Each client is assigned specific roles to ensure that they have appropriate access to the functionalities they require.

### Address Management Microservice

This microservice is responsible for managing customer addresses and their associated preferences for notifications. It handles the storage, retrieval, and updating of address data.

### Discovery Service

The **Discovery service** facilitates service registration and discovery within the ecosystem. It enables microservices to dynamically find and communicate with each other without hardcoding their locations. This architecture allows for a scalable and efficient system where various microservices can interact seamlessly, providing a robust solution for managing customer notifications and contact information.

## Key Features

- Centralized management of customer contact information.
- OAuth2 security integration for secure access to microservices.
- Dynamic service discovery for efficient communication between services.

## Database Structure

The **Customer Notification Address Facade System** uses a PostgreSQL database to store customer contact information and notification preferences. The database design follows an object-oriented approach with a focus on flexibility and performance. Below we point out main points:

### Rationale for Address Inheritance

In the Customer Notification Address Facade System, we designed the **Address** abstraction to encapsulate the different types of addresses (email, phone, and postal) under a single parent class. This design choice was made for several reasons:

- **Separation of Concerns**: The design promotes a clear separation of concerns. Each specific address type (e.g., Email, Phone, Postal) can have its own unique attributes and validation logic while still adhering to the common structure defined by the Address class. This keeps the implementation clean and focused.

- **Extensibility**: The use of an abstract class allows for easy extensibility. If the system needs to accommodate additional types of addresses in the future (e.g., social media handles or other contact methods), we can easily create new subclasses that extend the Address class without modifying the existing structure.

- **Database Structure and Query Performance**: The Single Table Inheritance strategy used for the Address class allows all address types to be stored in a single database table. This improves query performance by reducing the need for complex joins and facilitating faster data retrieval. When fetching a customer’s addresses, the system can efficiently query a single table, resulting in a more responsive application.

## Getting Started

To run this project locally, you'll need to set up a PostgreSQL database and configure the following environment variables:

### 1) Create a PostgreSQL Database

1. Install PostgreSQL if you don't have it already. You can find installation instructions [here](https://www.postgresql.org/download/).
2. Create a new database.
3. Provide username, password, and URL as environment variables:

### 2) Keycloak Configuration

This application uses Keycloak for authentication and authorization. To run Keycloak locally, ensure you have Docker installed on your machine. You can find installation instructions [here](https://docs.docker.com/get-docker/).

- The Keycloak service is defined in the `docker-compose.yml` file, which is configured to run on port 9090.
- **Accessing Keycloak**: Once Keycloak is running, you can access the admin console at `http://localhost:9090/auth/admin/`.
- Username: `admin`
- Password: `admin`

### Importing the Realm Configuration

In case you change the Keycloak configuration or need to set up the realm quickly, a `keycloakFinal.json` file is included under the `Keycloak/realm` folder in the project. You can import this file into Keycloak to recreate the realm with the correct configuration.

#### **Importing the Realm**:
1. Go to the Keycloak admin console.
2. Navigate to the Realm Settings section.
3. Use the Import option to upload the `keycloakFinal.json` file.

This will set up the realm with the predefined clients, roles, and configurations. 
> **Important**: Please make sure you set up the realm correctly so OAuth2 security configuration under Gateway works properly.

### Running the Project

Once the project starts, please run the following endpoint to insert batch data into the database:
- **Batch Processor Endpoint**: `http://localhost:8081/notifications/batchProcessor`

If you want to access the project without OAuth2 security, use the following endpoint:
- **Without OAuth2 Security**: `http://localhost:8080`



