# godot-google-maps-android

Godot plugin to display and manipulate a google map view

To use this plugin you will need to compile it against your required version of Godot.

### Compile

You will need:

-   Android Studio https://developer.android.com/studio
-   Godot AAR Library for your desired version https://godotengine.org/download/

1. Open the project in Android Studio
2. Put your downloaded Godot AAR Library into the `app/libs` directory. (Ensure its filename matches `godot-lib*.aar`)

3. Compile the project

### Install Plugin

1. Copy the `app/build/outputs/aar/MapPlugin.aar` file and the `app/MapPlugin.gdap` file to the `android/plugins` folder into your Godot app directory.

2. Go to Project -> Export, select the android export, check custom build, and enable the plugin.

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
