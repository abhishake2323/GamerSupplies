# GamerSupplies

> **Project by Star Mode Studios**

GamerSupplies is a comprehensive E-Commerce Platform dedicated to gaming retail. It is designed to manage inventory, track stock across multiple distribution centers, and provide a seamless customer ordering experience.

## Technology Stack

*   **Backend:** Java 21, Spring Boot 3.4.2
*   **Database:** PostgreSQL
*   **Frontend:** Thymeleaf
*   **Security:** Spring Security
*   **Build Tool:** Maven

## Team Members

| Name | Role |
| :--- | :--- |
| **Nicholas Carreiro** | Developer |
| **Musa Jama** | Developer |
| **Abhishek Masur Jayatheertha** | Developer |
| **Fahad Arif** | Developer |

## Prerequisites

Before running the application, ensure you have the following installed:

*   [Java 21 JDK](https://adoptium.net/)
*   [PostgreSQL](https://www.postgresql.org/download/)

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/abhishake2323/GamerSupplies.git
    cd GamerSupplies
    ```

2.  **Configure the Database:**
    *   Create a PostgreSQL database named `gamersupplies` (or update `src/main/resources/application.properties` with your database details).
    *   Ensure your PostgreSQL server is running.

3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Access the application:**
    *   Open your browser and navigate to `http://localhost:8080`.
