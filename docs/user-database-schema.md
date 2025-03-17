# WebGoat User Database Documentation

## Overview

WebGoat uses an HSQLDB database to store user information, progress tracking, and lesson-specific data. The database is structured with multiple schemas:

1. **CONTAINER schema**: Stores core application data including user accounts, progress tracking, and email functionality
2. **User-specific schemas**: Each user gets their own schema (named after their username) for lesson-specific data

## Database Configuration

WebGoat uses the following database configuration:

- **Database Type**: HSQLDB (Hyper SQL Database)
- **Connection URL**: `jdbc:hsqldb:file:${webgoat.server.directory}/webgoat`
- **Driver**: `org.hsqldb.jdbc.JDBCDriver`
- **Default Schema**: `CONTAINER`

## User Management Schema

The main user-related tables are stored in the `CONTAINER` schema:

### CONTAINER.web_goat_user

This table stores the core user account information:

| Column   | Type         | Description                                      |
|----------|--------------|--------------------------------------------------|
| username | VARCHAR(255) | Primary key, unique identifier for the user      |
| password | VARCHAR(255) | User's password (stored securely)                |
| role     | VARCHAR(255) | User's role (WEBGOAT_USER or WEBGOAT_ADMIN)      |

### CONTAINER.user_progress

This table tracks overall user progress in the application:

| Column   | Type         | Description                                      |
|----------|--------------|--------------------------------------------------|
| id       | BIGINT       | Primary key, auto-incremented                    |
| username | VARCHAR(255) | Username reference                               |

### CONTAINER.lesson_progress

This table tracks user progress for specific lessons:

| Column             | Type         | Description                              |
|--------------------|--------------|------------------------------------------|
| id                 | BIGINT       | Primary key, auto-incremented            |
| lesson_name        | VARCHAR(255) | Name of the lesson                       |
| number_of_attempts | INTEGER      | Number of attempts made by the user      |
| version            | INTEGER      | Version tracking                         |

### CONTAINER.assignment_progress

This table tracks progress on individual assignments within lessons:

| Column        | Type    | Description                                |
|---------------|---------|--------------------------------------------|
| id            | BIGINT  | Primary key, auto-incremented              |
| assignment_id | BIGINT  | Reference to the assignment                |
| solved        | BOOLEAN | Whether the assignment has been solved     |

### Relationship Tables

- **CONTAINER.lesson_progress_assignments**: Links lessons to assignments
- **CONTAINER.user_progress_lesson_progress**: Links users to their lesson progress

## User Schema Creation

When a new user is created in WebGoat:

1. A new schema is created with the username as the schema name
2. Lesson-specific tables are created in this schema
3. This allows each user to have isolated data for practicing lessons

## Database Initialization

The database is initialized using Flyway migrations:

1. Core container schema is initialized from `db/container/V1__init.sql`
2. Lesson-specific schemas are initialized from SQL files in the `lessons` directory

## Schema Diagram

```
+------------------------+       +------------------------+       +------------------------+
| CONTAINER.web_goat_user|       | CONTAINER.user_progress|       |CONTAINER.lesson_progress|
+------------------------+       +------------------------+       +------------------------+
| username (PK)          |<----->| id (PK)                |<----->| id (PK)                |
| password               |       | username               |       | lesson_name            |
| role                   |       +------------------------+       | number_of_attempts     |
+------------------------+                |                       | version                |
                                          |                       +------------------------+
                                          |                                 |
                                          |                                 |
                                          v                                 v
                         +---------------------------------------+  +------------------------+
                         |CONTAINER.user_progress_lesson_progress|  |CONTAINER.assignment_prog|
                         +---------------------------------------+  +------------------------+
                         | user_progress_id                      |  | id (PK)                |
                         | lesson_progress_id                    |  | assignment_id          |
                         +---------------------------------------+  | solved                 |
                                                                    +------------------------+
                                                                             |
                                                                             |
                                                                             v
                                                                    +------------------------+
                                                                    |CONTAINER.lesson_progress|
                                                                    |_assignments            |
                                                                    +------------------------+
                                                                    | lesson_progress_id     |
                                                                    | assignments_id         |
                                                                    +------------------------+
```

## User-Specific Schema Example

For a user named "john", the following would be created:

1. A schema named `john`
2. Lesson-specific tables within this schema, such as:
   - `john.servers` (for SQL injection lessons)
   - `john.user_data` (for user data lessons)
   - Other lesson-specific tables

This isolation ensures that each user can practice lessons independently without affecting other users' data. 