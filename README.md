# Posts
A sample app to demonstrate the building of a good and scalable Android app. The app currently consists of 2 screens. The first screen `ListActivity`, as the name suggests, pulls a list of posts from the server and `persists` the data into SQLite DB for later offline use. The second screen `DetailActivity`, as the name suggests, pulls details about that particular post and displays it. In the second screen too, data is persisted into the database for later offline use.

# Features
Some of the features of the app include

  - `MVP architecture` -> The `presenter` acts as the communication agent between a somewhat dumb `view` and the data aware / intelligent `model`.
  
  - `Offline first architecture` -> All the data is first tried to be loaded from the db and then updated from the server. This ensures that the app is usable even in an offline mode. See `StatefulCallback` for working.
  
  - `Dependency Injection` -> Common elements like `context`, `networking` interface are injected using `Dagger 2`
  
  - `Data binding` -> Data binding is used to bind xml elements to the java counterparts. No logic part is added to the xml though, as it makes code really hard to read.
  
  - `Transitions` - Shared element transitions are used whenever they make the experience better. `API 21+`
  
  - `Rotation persistance` - Due to the `scoped` injection of elements, rotation of the device does not rerun db or server calls.

# Decisions
  - `Sub-Modules` vs `Modules` - I decided to go ahead with `Sub-Module` as all our modules ,`List` and `Detail`, directly depend on `AppComponent`'s injections to function correctly.
  
  - `SugarORM` - The initial intuition was to go with `Room` as I wanted to play around with it, but it would require AS 3.0 to build. As it could require some waiting time to set that up for others, I decided to go ahead with `SugarORM` because it was second on my list of ORMs to play with. I am happy with its performance too for this simple usecase. I can use relationships better I think, but for now this suffices.
  
  - `MVP vs Clean` - MVP is a personal preference. 
  
  - `Feature based packaging` - This screen-wise / feature-wise packaging makes code really easy to read and debug. 

# Testing:
### Unit Testing
The `ListPresenterTest` has the implementation of unit tests. Mockito and JUnit have been used for testing. The tests can be build from the 
command line using `gradlew test`. The output of the test will be available in `app\build\test-results\testDebugUnitTest`

### Manual Testing
The app has been manually tested on `Nexus 5 (API 24)` (highend) 
and `Micromax (API 19)` (lowend) devices.

# Build info: 
  - Android Studio - 2.3.1
  - Compile SDK - 25
  - MinSDK - 16, Target - 25

# Libraries used
* Support Libraries
* Dagger 2
* Retrofit
* OkHttp
* Stetho
* StorIo
* Picasso
* Junit
* Espresso