# GamerSupplies

> **Project by Star Mode Studios** — *Web Application Development (CPAN-228)*

GamerSupplies is a full-stack gaming retail e-commerce platform built with Spring Boot and Thymeleaf. It supports a complete product catalog, role-based access control across three user roles (Admin, Staff, Customer), and runs in two environments: an H2 in-memory dev setup and a MySQL-backed QA environment via Docker.

---

## Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.4.2 |
| **Security** | Spring Security 6 (BCrypt, RBAC) |
| **Validation** | Spring Boot Starter Validation (Jakarta) |
| **Frontend** | Thymeleaf + Bootstrap 5.3.8 (Glassmorphism UI) |
| **Dev Database** | H2 In-Memory |
| **QA Database** | MySQL 8.0 (via Docker) |
| **Build Tool** | Maven (includes `./mvnw` wrapper — no install needed) |
| **Containerization** | Docker + Docker Compose |

---

## Features

- **Public Storefront** — Guests can browse the full product catalog, filter by category and price, and sort results without logging in.
- **Role-Based Access Control (RBAC)** — Three distinct roles enforced at both URL and method level:
  - `ROLE_ADMIN` — Full product management (add, edit, delete) via the Admin Panel.
  - `ROLE_STAFF` — Can add new products to the catalog.
  - `ROLE_CUSTOMER` — Authenticated browse-only access.
- **Product Management** — Full CRUD with server-side validation and inline form error messages.
- **User Registration & Login** — Passwords hashed with BCrypt. New users register as Customer by default.
- **Dynamic Navigation** — Navbar adapts in real time based on the authenticated user's role and session state.
- **Environment Profiles** — `dev` uses H2 (no external DB needed). `qa` uses MySQL via Docker.
- **Docker Support** — Single command to spin up the full app + MySQL database stack.

---

## Running with Docker (Recommended)

The only prerequisite is [Docker Desktop](https://www.docker.com/products/docker-desktop/). No Java, Maven, or database setup required.

```bash
# 1. Clone the repository
git clone https://github.com/abhishake2323/GamerSupplies.git
cd GamerSupplies

# 2. Start the full stack (app + MySQL)
docker compose up --build
```

Docker will:
1. Pull the MySQL 8.0 image and initialize the `gamersupplies` database.
2. Build the Spring Boot JAR inside a Maven container (dependencies are cached after first build).
3. Wait for MySQL to pass its healthcheck before starting the app.
4. Start the application on port **8080**.

| URL | Description |
| :--- | :--- |
| `http://localhost:8080` | Main application |
| `http://localhost:3306` | MySQL port (for external DB tools) |

```bash
# Stop the stack
docker compose down

# Stop and wipe all data
docker compose down -v
```

---

## Running Locally without Docker (Dev Mode)

**Prerequisite:** Java 17 JDK only.

```bash
git clone https://github.com/abhishake2323/GamerSupplies.git
cd GamerSupplies
./mvnw spring-boot:run
```

On Windows: `mvnw.cmd spring-boot:run`

The app starts at `http://localhost:8080`.

**H2 Console** (dev profile only): `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:gamersuppliesdb`
- Username: `sa` / Password: *(leave blank)*

---

## Environment Profiles

No code changes are needed to switch environments — only a flag.

| Profile | Database | How to Activate |
| :--- | :--- | :--- |
| `dev` *(default)* | H2 in-memory | `./mvnw spring-boot:run` |
| `qa` | MySQL via Docker | Set automatically by Docker Compose |

To manually run the QA profile (requires a running MySQL instance):
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=qa
```

| Config File | Purpose |
| :--- | :--- |
| `application.properties` | Shared settings; sets `spring.profiles.active=dev` |
| `application-dev.properties` | H2 datasource, H2 console enabled |
| `application-qa.properties` | MySQL datasource, H2 console disabled |

---

## Environment Variables

These are set automatically by Docker Compose. Override in `docker-compose.yml` if needed.

| Variable | Default | Description |
| :--- | :--- | :--- |
| `SPRING_PROFILES_ACTIVE` | `qa` | Active Spring profile in Docker |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://db:3306/gamersupplies?...` | MySQL connection string |
| `SPRING_DATASOURCE_USERNAME` | `appuser` | DB username |
| `SPRING_DATASOURCE_PASSWORD` | `apppass` | DB password |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Schema strategy |
| `SPRING_SQL_INIT_MODE` | `always` | Whether `data.sql` runs on startup |
| `MYSQL_ROOT_PASSWORD` | `rootpass` | MySQL root password (db container only) |

---

## Default Test Accounts

Seeded automatically on every startup via `data.sql`. Password for all accounts is `password`.

| Username | Role | Access |
| :--- | :--- | :--- |
| `admin` | `ROLE_ADMIN` | View, add, edit, delete products; Admin Panel |
| `staff` | `ROLE_STAFF` | Add products only |
| `customer` | `ROLE_CUSTOMER` | Browse only |

---

## Running Tests

```bash
./mvnw test
```

Covers models, repositories, services, and MVC controllers via JUnit 5.

---

## Team Contributions

| Member | Contributions |
| :--- | :--- |
| **Abhishek Masur Jayatheertha** | Built the Docker infrastructure including the Dockerfile and docker-compose.yml; configured MySQL as the QA database container; wired all Spring datasource environment variables for the Docker environment; implemented all forms and server-side validation in earlier deliverables; added Jakarta validation annotations to the Product entity with inline Thymeleaf error messages; built the user registration flow |
| **Musa Jama** | Configured the dev and QA Spring profiles by creating `application-dev.properties` and `application-qa.properties` and refactoring `application.properties` to shared-only settings; scaffolded the initial project; built product filtering and sorting on the product listing page; developed the admin interface with full product management; maintained UI consistency across pages |
| **Fahad Arif** | Audited and hardened app stability and security; added `@Valid` annotations and validation constraints across the Product entity; wired inline validation error messages into the add and edit Thymeleaf forms; fixed product edit and delete flows; conducted a full SecurityConfig route audit and manual role-based access testing across all three roles; built the initial frontend pages in earlier deliverables |
| **Nicholas Carreiro** | Rewrote the README with full Docker instructions, environment variable documentation, profile switching guide, and team contributions; audited all Thymeleaf templates for broken routes, missing Bootstrap JS, and layout inconsistencies; improved navigation with role-specific badges and an Admin Panel link; set up the Product entity, repository, datasource config, and seed data; implemented the full Spring Security configuration including `SecurityFilterChain`, role-based access rules, and `DaoAuthenticationProvider`; handled container orchestration updates |
