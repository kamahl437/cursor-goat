# WebGoat User Database Schema Diagram

The following diagram illustrates the database schema for user management in WebGoat.

```mermaid
erDiagram
    WEB_GOAT_USER ||--o{ USER_PROGRESS : "has"
    USER_PROGRESS ||--o{ USER_PROGRESS_LESSON_PROGRESS : "contains"
    LESSON_PROGRESS ||--o{ USER_PROGRESS_LESSON_PROGRESS : "belongs to"
    LESSON_PROGRESS ||--o{ LESSON_PROGRESS_ASSIGNMENTS : "contains"
    ASSIGNMENT_PROGRESS ||--o{ LESSON_PROGRESS_ASSIGNMENTS : "belongs to"
    
    WEB_GOAT_USER {
        string username PK
        string password
        string role
    }
    
    USER_PROGRESS {
        bigint id PK
        string username
    }
    
    LESSON_PROGRESS {
        bigint id PK
        string lesson_name
        int number_of_attempts
        int version
    }
    
    ASSIGNMENT_PROGRESS {
        bigint id PK
        bigint assignment_id
        boolean solved
    }
    
    USER_PROGRESS_LESSON_PROGRESS {
        bigint user_progress_id PK,FK
        bigint lesson_progress_id PK,FK
    }
    
    LESSON_PROGRESS_ASSIGNMENTS {
        bigint lesson_progress_id PK,FK
        bigint assignments_id PK,FK
    }
    
    %% User-specific schema
    USER_SCHEMA {
        string schema_name "Username"
    }
    
    USER_SCHEMA ||--o{ LESSON_TABLES : "contains"
    
    LESSON_TABLES {
        string table_name
        string lesson_specific_data
    }
```

## Schema Explanation

1. **WEB_GOAT_USER**: Stores user credentials and roles
2. **USER_PROGRESS**: Tracks overall progress for each user
3. **LESSON_PROGRESS**: Tracks progress for specific lessons
4. **ASSIGNMENT_PROGRESS**: Tracks completion status of individual assignments
5. **Relationship Tables**: Connect users to lessons and assignments
6. **User-specific Schema**: Each user gets their own schema with lesson-specific tables

All tables except the user-specific lesson tables are in the CONTAINER schema. 