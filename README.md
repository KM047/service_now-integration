# ServiceNow Integration with Spring Boot

This project demonstrates how to integrate ServiceNow's RESTful APIs with a Spring Boot application to perform CRUD (Create, Read, Update, Delete) operations on the `ticket` table.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.x
- Access to a ServiceNow instance

## Getting Started

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/KM047/service_now-integration.git
   ```


2. **Navigate to the Project Directory:**

   ```bash
   cd service_now-integration
   ```


3. **Configure Application Properties:**

   Update the `application.properties` file with your ServiceNow instance details:

   ```properties
   servicenow.instance.url=https://your-instance.service-now.com
   ```


*Note:* For security reasons, consider using environment variables or a secure vault to manage sensitive credentials.

4. **Build the Project:**

   ```bash
   mvn clean install
   ```


5. **Run the Application:**

   ```bash
   mvn spring-boot:run
   ```


## API Endpoints

The application exposes the following RESTful endpoints:

- **Create Ticket:**

    - **Endpoint:** `POST /api/tickets`
    - **Description:** Creates a new ticket in ServiceNow.
      - **Request Body:**
        ```json
        {
            "short_description": "Error while createing ticket 2",
            "description" : "Some issue at the end of the ticket creation",
            "state": "2",
            "priority": "3",
            "u_type": "incident"     
        }
        ```

- **Retrieve Ticket:**

    - **Endpoint:** `GET /api/tickets/{ticketId}`
    - **Description:** Retrieves details of a specific ticket by its ID.

- **Update Ticket:**

    - **Endpoint:** `PUT /api/tickets/{ticketId}`
    - **Description:** Updates specified fields of an existing ticket.
    - **Request Body:**
        ```json
        {
            "state": "3"
            // other fields to update
        }
        ```
    - **Request Body:** Include only the fields to be updated.

- **Delete Ticket:**

    - **Endpoint:** `DELETE /api/tickets/{ticketId}`
    - **Description:** Deletes a ticket by its ID.
  