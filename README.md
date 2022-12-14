# godot-google-maps-android

Godot plugin to display and manipulate a google map view

To use this plugin you will need to compile it against your required version of Godot.

### Compile

You will need:

-   Android Studio https://developer.android.com/studio
-   Godot AAR Library for your desired version https://godotengine.org/download/

1. Open the project in Android Studio
2. Put your downloaded Godot AAR Library into the `app/libs` directory. (Ensure its filename matches `godot-lib*.aar`)

![image](https://user-images.githubusercontent.com/657135/207623420-03c938bd-bc76-467c-a8bf-dd74cd8d4c28.png)

3. Compile the project

### Install Plugin

1. Copy the `app/build/outputs/aar/MapPlugin.aar` file and the `app/MapPlugin.gdap` file to the `android/plugins` folder into your Godot app directory.

![image](https://user-images.githubusercontent.com/657135/207624388-f3ea7e87-8c85-4e7d-ad46-fd5b541d7181.png)

2. Go to Project -> Export, select the android export, check custom build, and enable the plugin.

![image](https://user-images.githubusercontent.com/657135/207625476-7051b119-240b-46b3-8bce-34078fded15e.png)

### Add the Map View

```gdscript
extends Node2D

func _ready():
    # Load The plugin
    var mapPlugin = Engine.get_singleton("MapPlugin")

    # Connect to the response signal
    mapPlugin.connect("onLocationUpdateResponse", self, "on_LocationUpdateResponse")

    # Create the map
    mapPlugin.createMap()

    # Set the current Location
    mapPlugin.setLocation(12.3456789, 12.3456789)

func on_LocationUpdateResponse(locationData: Dictionary):
    # Print the location details: { "latitude": 12.3456789, "longitude": 12.3456789 }
    print(locationData)

```
