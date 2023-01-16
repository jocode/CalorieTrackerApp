## Architecture :wrench:

### Clean Architecture

Clean Architecture is an architectural pattern for software development that is intended to make software systems more flexible, maintainable, and testable. It is based on the principles of separation of concerns and the dependency rule. The goal is to structure the application in such a way that the business logic is separate from the infrastructure, making it easy to test and modify the application without affecting the rest of the system.

The project is based on the Clean Architecture, which is a software architecture that separates the elements of a design into ring levels. This architecture is based on the Dependency Rule, which states that source code dependencies can only point inwards and nothing in an inner circle can know anything at all about something in an outer circle.

The clean architecture allow us to have a very good separation of concerns
- Well-scaling apps. Easy extendable
- Easy testable
- Quickly understandable for others

Fot this project, the architecture is divided into 3 layers:
- `data` - the data layer, which contains the data sources, such as the database and the network.
    - Database implementation, remote API, preferences, etc.
    - Mappers for DB entities & DTOs
- `domain` - the domain layer, which contains the use cases, models and the business logic.
    - The innermost layer
    - Contains use cases that contain business logic
    - Use cases just do a things in out app, for example, validate search request, making api calls, etc.
- `presentation` - the presentation layer, which contains the UI code and the view models.
    - Present something to the user
    - UI like composables, Fragments or Activities
    - Also contains viewModels, the viewModels allow to change the state to the UI


### Why use cases are important?

- Prevent viewModels from having too much logic
- Single responsibility principle
- Helps with readability
- Makes testing easier
- Makes the code more reusable
- It's a intermediate layer between the data and presentation layer

## What is a Multi-Module Architecture?

A multi-module architecture is a software architecture in which a software system is divided into multiple modules. Each module is a self-contained unit that can be independently developed, tested, and deployed. Modules are connected to each other to form a complete system.

A multi-module architecture is an application that have divided by features or layer each part of the application. One module is just a library, that can’t depend from other. One module is nothing else that a library. You can create your library to use locally on the project or from the other project.

### Advantages & Disadvantages of a Multi-Module Architecture

#### Pros of modularization

- Clear separation
- Faster Gradle builds
- Support for instant apps & dynamic features
- Makes parts of your app reusable

#### Cons of modularization

- Lots of initial setup involved
- Not knowing what you’re doing strongly backfire