# Posts
A sample app to demonstrate the building of a good, modular and scalable Android app using Kotlin, Android Architecture Components (LiveData, ViewModel & Room), Dagger, RxJava and RxAndroid among others.

# Features
Some of the features of the app include

- `Effective Networking` - Using a combination of Retrofit, Rx, Room and LiveData, we are able to handle networking in the most effective way.

- `Modular` - The app is broken into modules of features and libraries which can be combined to build instant-apps, complete apps or lite version of apps.

- `MVVM architecture` - Using the lifecycle aware viewmodels, the view observes changes in the model / repository.

- `Kotlin` - This app is completely written in Kotlin.

- `Android Architecture Components` - Lifecycle awareness has been achieved using a combination of LiveData, ViewModels and Room.

 - `Offline first architecture` - All the data is first tried to be loaded from the db and then updated from the server. This ensures that the app is usable even in an offline mode.

 - `Dependency Injection` - Common elements like `context`, `networking` interface are injected using Dagger 2.

 - `Feature based packaging` - This screen-wise / feature-wise packaging makes code really easy to read and debug.

# Networking
![Data flow Diagram](DataFlow.png)


# Testing:
TODO

# Build info:
  - Android Studio - 3.1 Canary 8
  - Compile SDK - 27
  - MinSDK - 16, Target - 27

# Libraries used
* Support Libraries
* Dagger 2
* Retrofit
* OkHttp
* Stetho
* Room
* ViewModel
* LiveData
* RxJava
* RxAndroid

# License

    Copyright 2018 Karan Trehan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.