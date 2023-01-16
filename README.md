# :fire: Calorie Tracker - Multi-Module Architecture App

## Project Structure

The project is divided into 6 modules:
- `app` - the main module, which contains the `MainActivity` and the `App` class.
- `buildSrc` - the module that contains the dependencies and the versions of the libraries used in the project. For build specific configurations.
- `core` - the core module, which contains all shared code between the modules. Contains data like classes that need in a lot or in all modules, share logic or share clases that could be use in other modules
- `core-ui` - the core-ui module, which contains all shared UI code between the modules.
- `onboarding` - the onboarding module, which contains the onboarding screens.
- `tracker` - the tracker module, which contains the tracker screens, such as the Overview screen and the Search screen.

```
CalorieTrackerApp/
├── :app
│
├── :buildSrc
│
├── :core
│
├── :core-ui
│
├── :onboarding
│   ├──:onboarding_domain
│   └──:onboarding_presentation
│
└── :tracker/
    ├──:tracker_data
    ├──:tracker_domain
    └──:tracker_presentation
```

> :bulb: The app create by modularization is very hard to build and you must to put much effort the first time. But after that, you will have a very good architecture and you will be able to add new features very fast.
> You can create your application using a single module, and if your app growth you can modularize your app to work with a large team.


To learn more about the project structure, check out the [Architecture Guide](architecture-guide.md).