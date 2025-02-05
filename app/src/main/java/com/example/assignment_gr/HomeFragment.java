package com.example.assignment_gr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private MapView mapView;
    private ListView listView;
    private ArrayList<Location> locations;
    private LocationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity, container, false);

        // Load OSM configuration
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("osmdroid", getContext().MODE_PRIVATE);
        Configuration.getInstance().load(requireContext(), sharedPreferences);

        listView = view.findViewById(R.id.location_list);
        locations = new ArrayList<>();

        // Add locations to the list
        locations.add(new Location("Lalibela", 12.0333, 39.0459));
        locations.add(new Location("Addis Ababa", 9.03, 38.74));
        locations.add(new Location("Gondar", 12.6, 37.47));
        locations.add(new Location("Axum", 14.13, 38.72));

        // Set up the adapter and list
        adapter = new LocationAdapter(this, locations);
        listView.setAdapter(adapter);


        // Initialize the map
        mapView = view.findViewById(R.id.osm_map);
        mapView.setMultiTouchControls(true);

        // Set initial map position
        moveMapTo(new GeoPoint(12.0333, 39.0459), "Lalibela");

        // Handle item clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Location selectedLocation = locations.get(position);
            Toast.makeText(this, "Clicked: " + selectedLocation.getName(), Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    // **Method to move the map to a new position**
    public void moveMapTo(GeoPoint point, String title) {
        if (mapView != null) {
            mapView.getController().animateTo(point);  // Smooth animation to new location
            mapView.getController().setZoom(15.0);

            // Remove old markers
            mapView.getOverlays().clear();

            // Add new marker
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setTitle(title);
            mapView.getOverlays().add(marker);

            mapView.invalidate(); // Refresh the map
        }
    }
}
