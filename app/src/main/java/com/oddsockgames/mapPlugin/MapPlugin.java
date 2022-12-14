package com.oddsockgames.mapPlugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;

public class MapPlugin extends GodotPlugin {
    private Context context;
    private Activity activity;
    private FrameLayout layout = null;
    private MapView mapView = null;

    public MapPlugin(Godot godot) {
        super(godot);
        context = godot.getContext();
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "MapPlugin";
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();
        signals.add(new SignalInfo("onLocationUpdateResponse", Dictionary.class));
        return signals;
    }

    @Nullable
    @Override
    public View onMainCreate(Activity activity) {
        layout = new FrameLayout(activity);
        return layout;
    }

    @UsedByGodot
    public void removeMap() {
        activity.runOnUiThread(() -> {
            if (mapView != null) {
                layout.removeView(mapView);
            }
        });
    }

    @UsedByGodot
    public void setLocation(float lat, float lng) {
        if (mapView == null) {
            return;
        }

        activity.runOnUiThread(() -> {
            mapView.getMapAsync(googleMap -> {
                LatLng position = new LatLng(lat, lng);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(position)
                );

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 20));

                googleMap.setOnCameraMoveListener(() -> {
                    LatLng midLatLng = googleMap.getCameraPosition().target;
                    if (marker != null) {
                        marker.setPosition(midLatLng);
                        Dictionary newPosition = new Dictionary();
                        newPosition.put("latitude", midLatLng.latitude);
                        newPosition.put("longitude", midLatLng.longitude);
                        emitSignal("onLocationUpdateResponse", newPosition);
                    }
                });

                mapView.onResume();
            });
        });
    }

    @UsedByGodot
    public void createMap() {
        activity.runOnUiThread(() -> {
            FrameLayout.LayoutParams mapLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    1000,
                    Gravity.BOTTOM
            );

            mapView = new MapView(activity);

            Bundle bundle = new Bundle();
            Bundle mapViewBundle = bundle.getBundle(BuildConfig.MAPS_API_KEY);

            mapView.onCreate(mapViewBundle);

            mapView.bringToFront();
            layout.addView(mapView, mapLayoutParams);
        });
    }
}
