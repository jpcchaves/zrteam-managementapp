# zrteam-managementapp

# Explaining the Architecture

This documentation provides an overview of the project's structure based on the principles of Clean Architecture.

## Overview

### Application
- Encapsulates the core application logic and functionality and introduces three separated layers that have self-explaining names: DTO, service and utils.
  - Service:
    - Contains service interfaces and implementations.
    - Implements business logic and interacts with repositories.
  - DTO:
    - Contains DTO pojo classes.
  - Utils:
    - Contains utils methods.

### Domain
- Contains core domain entities and exceptions.

### Infra
- Introduces a separate layer for repository-related components.
- Repositories for data access and custom repository interfaces.
- Introduces a separate layer for configuration classes for the application.

### Service
- Contains service interfaces and implementations.
- Implements business logic and interacts with repositories.

### Presentation
- Handles incoming HTTP requests.
- Controllers for different entities and global error handling.
