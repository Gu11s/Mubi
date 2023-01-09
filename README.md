# Mubi

## Retrievement of TmDb api

## Implementation

The permission used in the manifest to help us use the app is "android.permission.INTERNET"
This permission help us retrieve data from internet.
It also allows us to connect the app to firebase for authentication.

## Tech

This app uses [JetPackCompose](https://developer.android.com/jetpack/compose)

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [JetPackCompose](https://developer.android.com/jetpack/compose)
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
  - Firebase: for authenticate users

- Architecture
  - Clean Architecture
  - MVVM Architecture (Model - View - ViewModel)
  - Repository Pattern
  
## Images

![1](https://user-images.githubusercontent.com/16690851/211304634-cb7df413-1b0c-4ace-9a27-d8a8c1b070bc.png)

![2](https://user-images.githubusercontent.com/16690851/211304724-9689b0e5-06cb-41ef-b87f-57b310459d0d.png)

![3](https://user-images.githubusercontent.com/16690851/211304794-db448109-0bad-4837-aebe-7dd393599c79.png)

![4](https://user-images.githubusercontent.com/16690851/211304836-b10891d5-27e0-4493-9187-4ddc89fe120c.png)

![5](https://user-images.githubusercontent.com/16690851/211304878-e46c23ae-b1ac-4fcd-8f7b-b74cf12b4d9a.png)

![6](https://user-images.githubusercontent.com/16690851/211304932-767730dc-87d5-45dd-89a3-1227d4c246be.png)


## License

**FREE USE**
