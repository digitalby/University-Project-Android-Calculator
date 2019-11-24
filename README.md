# Android Calculator (University Project)

![screenshot][screenshot]

The project has been created in 10 days with no prior experience of building an Android app start-to-finish and no prior experience with Kotlin. The deadlines were rather strict, so development had to be rushed in order to meet them.

The end goal of the assigment was to create a Calculator app for Android. The following topics had to be covered:

  * **Android UI**
  * **Basic App Architecture**
  * **Activities & Views**
  * **Fragments**
  * **Product flavors**

For learning purposes, the calculator engine was built from scratch without using any third-party libraries. Due to time constraints, the app architecture is far from flawless *(initially, the project had a massive ```MainActivity.kt```, which contained the engine and every call to every fragment)*, and no thorough bugtesting of the engine was conducted.

The app UI is built of fragments that represent blocks of calculator functions. In landscape orientation, all of these blocks are displayed at once. In portrait orientation, the user has to switch between basic and scientific calculation blocks using a `MenuItem` button. `ConstraintLayout` is used in order to make a responsive UI. Both portrait and landscape layouts were tested on an emulator with screen resolution of 800&times;480, and worked with little to no imperfections.

Two product flavors have been created for the app: `full` and `demo`, with the latter having restricted scientific mode functionality.

[screenshot]: screenshot.png