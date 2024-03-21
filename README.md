# Treasure Hunt app

This app will be a simple treasure hunt game.  
The user will be able to see a list of POIs(Points of Interest) and the app will guide the user 
to the closest POI.  
When the user is close enough to the POI, the app will allow the user to take a picture of the POI and then the app will guide the user to the next POI. When the user has visited all the POIs, the app will show a congratulations screen.

## Input :
 
- A list of POIS ( id,lat, lng, description, image )
- phone current location

## Screens

- An arrow that points to the closest POI + distance to POI 
- Take picture button visible when POI < 25 meters —> selected = true
- When all selected set congratulations screen


## Modules

- Data + Repository + View
- Location Module on Background
- ViewModel
- Screen
    - Loading
    - Error Location
    - Next POI
    - Congratulations 