# Treasure Hunt Game

This app will be a simple treasure hunt game.  
The user will be able to see a map of POIs (Points of Interest = the treasures ) and the app will guide the user to the closest POI.  
When the user is close enough to any POI, the user can take a picture of the POI.
A picture taken is proof of the user visiting the POI.
then the app will guide the user to the next closest POI.
When the user has selected all the POIs, the app will show a congratulations screen.

## Features

- An arrow that points to the closest POI + distance to POI 
- button to take picture of POI visible when distance to POI < 5 meters 
- When all POI picture taken show congratulations screen


## Clean Architecture 

### Data Layer

#### Database
 
- TreasureHuntDatabase
  - holds the entities and the DAOs
  
##### Entities

  - PoiEntity
    - id
    - lat
    - lng
    - description
    - imageUrl
    - selected

##### DAOs

  - PoiDao
    - getAllPoi
    - getPoiById
    - insertPois(pois...PoiEntity)
    - updatePoi(poi)
    - getAllSelected
    - getAllUnselected

#### Network 

// TODO : create an API to fetch the POIs from the network

#### DTOs 
  - PoiDTO
      - id
      - lat
      - lng
      - description
      - image

#### PoiRepository (PoiDatabase, PoiNetwork)
  - getPois
  - getPoiById
  - insertPoisInDatabase(pois : List<PoiItem>)
  - updatePoi

### Domain Layer

  - GetPoisUseCase(repository : PoiRepository) : Flow<Result<List<PoiEntity>>>
  - GetClosestUnselectedPoiUseCase(userLocation : Flow<Result<UserLocation>>, repository : 
    PoiRepository) : 
    Flow<Result<PoiEntity>>
  - SelectPoiUseCase(userLocation : UserLocation, poiRepository : PoiRepository, poiId : Int) : 
    Result<Unit>
  - FlowUserLocationUseCase() : Flow<Result<UserLocation>>
  - UnselectPoisUseCase(poiRepository : PoiRepository) : Result<Unit>

### UI Layer

#### ViewModels 

  - MapViewModel(getPoisUseCase)
    - pois : Flow<Result<List<PoiItem>>>
  - CompassViewModel(getClosestUnselectedPoiUseCase, flowUserLocationUseCase)
    - closestPoiDirection : Flow<Result<Direction>>
    - closestPoiDistance : Flow<Result<Float>>
  - TakePictureViewModel(getClosestUnselectedPoiUseCase, selectPoiUseCase)
    - isButtonEnable : Flow<Result<Boolean>> // true if the closest POI is < 5 meters
    - onClick : (id : poiId) -> Unit
  - CongratulationsViewModel(getPoisUseCase, unselectPoisUseCase)
    - isAllPoiSelected : Flow<Result<Boolean>>
    -
#### Composables

  - ComposeMap(pois : Result<List<PoiItem>>) 
    - load a map with markers for each POI
  - ComposeCompass(closestPoiDirection : Flow<Result<Direction>>, closestPoiDistance :Flow<Result<Float>>) 
    - show a compass that points to the closest POI and shows the distance to the POI
  - ComposeButtonTakePicture(isButtonEnable, onClick) 
    - select a Poi by taking a picture
    - onClick save the picture and select the Poi
  - ComposeCongratulations(isAllPoiSelected, onClick)
    - show a congratulations screen if all Pois are selected
    - onClick unselect all Pois

### Wrapper classes + Data classes + Util Classes

- UserLocation
    - LatLng

- Result<T> : T is the entity or the value that the use case returns
    - Success<T> : T was successfully loaded
    - Loading<T> : T is loading
    - Error<T> : T could not be retrieved, the error message is in the exception

- Direction : a 360° angle that points to the closest POI
- 
### Dependency Injection and Unit Test

#### Mocks and Injects
  - PoiRepository
  - PoiDatabase
  - PoiNetwork

#### To Unit Test 

- GetPoisUseCaseTest
- GetClosestUnselectedPoiUseCaseTest
- SelectPoiUseCaseTest
- FlowUserLocationUseCaseTest
- UnselectPoisUseCaseTest

#### To Instrumented Test ??

#### To UI Test ??

