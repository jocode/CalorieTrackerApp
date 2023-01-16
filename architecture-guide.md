# Architecture :wrench:

## Clean Architecture :tophat:

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


## Why use cases are important? :high_brightness:

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

#### :muscle: Pros of modularization

- Clear separation
- Faster Gradle builds
- Support for instant apps & dynamic features
- Makes parts of your app reusable

#### Cons of modularization :no_good:

- Lots of initial setup involved
- Not knowing what you’re doing strongly backfire



## How to choose the right modularization strategy? :pizza:

Each app is different, so there is no one-size-fits-all modularization strategy. The best modularization strategy for your app depends on your app’s architecture, the size of your team, and the complexity of your app.
But, here are some tips to help you choose the right modularization strategy for your app :smiley:.

### :one: Layer-Based modularization

Layer-based modularization is a modularization strategy that divides your app into modules based on the layers of your app’s architecture. For example, you can have a module for the data layer, a module for the domain layer, and a module for the presentation layer.
- Presentation
- Domain
- Data

**Why this approach could be bad? :thinking:**

- Modules are not reusable.
- Hard for devs to work in an isolated environment data.
- Big modules → Slow build.
- If you have a big team, you will have a lot of conflicts.

### :two: Feature-Based Modularization

Feature-based modularization is a modularization strategy that divides your app into modules based on the features of your app. For example, you can have a module for the onboarding feature, a module for the tracker feature, and a module for the settings feature.

This is a better modularization strategy, because features are more reusable than layers. For example, you can use the onboarding feature in another app, but you can’t use the data layer in another app.

- One module per feature → Size is limited
- Delegate by teams
- Devs can work better in isolation
- Reusable modules
- No clear separation of concerns

### :three: Layered-Feature Modularization (Hybrid modularization) :trophy:

Layered-feature modularization is a modularization strategy that combines the best of both layer-based and feature-based modularization. For example, you can have a module for the data layer, a module for the domain layer, and a module for the onboarding feature.

- Modularization by feature with layer sub-modules
- Each feature is modularize again (Data, Domain, Presentation)
- We can still reuse single feature, easily delegate work
- Combines advantages of layer and feature-based modularization



## How to create a module in Android Studio? :mega:

- File -> New -> New Module
- Select the type of module you want to create
- Select the module name
- Select the module package name

For this project, I have doing the following steps:
- Go to the project and Click Right → New → Module
- Module is as a Library. Then Select `Android Library` or `Java or Kotlin Library` this last one is when I need only kotlin or Java dependencies, for example this can be useful when we are creating project with KMM projects.
- Then we defined the name of the module (In this case is `core`)
- Then, the library is added to the project structure in the project.


> **Note:** For the `buildSrc` module, it's created as directory in the root of the project. This module is used to share dependencies between modules. This module is not a library, it's just a directory with a `build.gradle.kts` file. This module is not added to the project structure in the project.
> To create this module, we need to add the `src/main/java` folders and the `build.gradle.kts` file. In the `build.gradle.kts` file, we need to add the following code:

```kotlin
import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}
```


Each module in Android is located in the `settings.gradle` file where contains the path of the module. For example, the `core` module is located in the `:core` path.
```gradle
rootProject.name = "CaloryTrackerApp"
include ':app', ':core'
...
```

For the features modules, the module is created as `Java or Kotlin Library` because they don't have any code inside, they only contains the submodules and these are created as `Android Library`.


## Gradle configuration :star:

For each module, we need to add the `build.gradle.kts` file.
As all modules usually use the same dependencies it's create 2 based gradle modules for the project:
- [base-module.gradle](base-module.gradle) contains the dependencies for domain and data modules
- [compose-module.gradle](compose-module.gradle) contains the dependencies for compose modules

An then we can include this files in the `build.gradle.kts` file of each module. For example:

[tracker/tracker_data/build.gradle.kts](tracker/tracker_data/build.gradle.kts)
```kotlin
apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
    "implementation"(Retrofit.moshiConverter)

    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}
```


[tracker/tracker_domain/build.gradle.kts](tracker/tracker_domain/build.gradle.kts)
```kotlin
apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(Coroutines.coroutines)
}
```


[tracker/tracker_presentation/build.gradle.kts](tracker/tracker_presentation/build.gradle.kts)
```kotlin
apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))
    "implementation"(project(Modules.coreUi))
    "implementation"(Coil.coilCompose)
}
```

When we used **project** that means that we are using a module from the project. For example, the `core` module is located in the `:core` path. So, we can use the `project` function to use this module.

The `Modules.core` is the name of the module and it's located in the [buildSrc/src/main/java/Modules.kt](buildSrc/src/main/java/Modules.kt) file. This file contains the name of all modules in the project.

Like this :point_down:

```kotlin
object Modules {
    const val app = ":app"

    const val core = ":core"
    const val coreUi = ":core-ui"

    const val onboardingDomain = ":onboarding:onboarding_domain"
    const val onboardingPresentation = ":onboarding:onboarding_presentation"

    const val trackerData = ":tracker:tracker_data"
    const val trackerDomain = ":tracker:tracker_domain"
    const val trackerPresentation = ":tracker:tracker_presentation"
}
```

In the [**buildSrc**](buildSrc/src/main/java) is located all dependencies that are used in the project. Separated by type, for example, all Retrofit dependencies are located in the [Retrofit.kt](buildSrc/src/main/java/Retrofit.kt) file and all Coil dependencies are located in the [Coil.kt](buildSrc/src/main/java/Coil.kt) file, etc.




## Easy recap about modularization :rewind:

1. Which of the following modularization strategies is usually the worst?
   - **By layer**
   - By feature
   - Layered features

2. What is NOT a benefit of a multi-module architecture? 
   - Faster builds 
   - Good separation of layers
   - **Faster apps**
   - Good work delegation in a team

3. What is the benefit of the UiText class we created in this course? 
   - It helps us to introduce a consistent text style across all of our modules
   - **It helps us to be able to use string resources, even if we don't have a context**
   - It makes it easier for the Compose UI test library to find texts that should be tested

4. What is a use case?
   - **A class that contains business logic doing a single thing**
   - It's a wrapper class for our ViewModel states so they're properly restored when the app is relaunched 
   - It's a class that has the only purpose to access the repository and provide the results to the ViewModel 
   - It's an abstraction for classes we put in the data layer

5. What is the purpose of an end-to-end test? 
   - It tests how exactly two classes interact with each other 
   - It serves as a connecting layer between unit and integration tests 
   - Each app should have exactly one end-to-end test that tests the whole flow of the app from the first screen till the very last one in form of UI tests
   - **It should test a real user interaction as closely as possible**

6. Assume, you use a 3rd party SDK for video calling. In which layer would you put the calling logic (like the communication to their servers, handling the connections, etc.)? 
   - Presentation 
   - Domain
   - **Data**

7. Which of these things makes a module less reusable? 
   - Implementing a Dagger-Hilt module inside of it
   - **Handling navigation to other modules**
   - Handing exceptions