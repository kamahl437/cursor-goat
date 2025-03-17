# WebGoat User Database Documentation

This directory contains documentation about the WebGoat user database system.

## Contents

- [User Database Schema](user-database-schema.md) - Detailed documentation of the database schema for user management
- [Database Schema Diagram](images/user-database-schema.md) - Visual representation of the database schema using Mermaid

## Overview

WebGoat uses a multi-schema approach for user management:

1. A central `CONTAINER` schema stores user accounts, roles, and progress tracking
2. Each user gets their own schema (named after their username) for lesson-specific data

This approach provides several benefits:
- Isolation between users for security exercises
- Clean separation between application data and lesson data
- Ability to reset lesson data without affecting user accounts

## Key Features

- **User Authentication**: Stored in `CONTAINER.web_goat_user` table
- **Role-Based Access**: Users can have either `WEBGOAT_USER` or `WEBGOAT_ADMIN` roles
- **Progress Tracking**: User progress is tracked at both lesson and assignment levels
- **Per-User Data Isolation**: Each user has their own schema for lesson data

## Database Technology

WebGoat uses HSQLDB (HyperSQL Database) as its database engine, which is:
- Lightweight and embedded
- Written in Java
- Supports SQL standards
- Provides in-memory and file-based persistence options

## Schema Creation

When a new user is registered in WebGoat:
1. A record is created in the `CONTAINER.web_goat_user` table
2. A new schema is created with the username as the schema name
3. Lesson-specific tables are created in this schema through Flyway migrations 