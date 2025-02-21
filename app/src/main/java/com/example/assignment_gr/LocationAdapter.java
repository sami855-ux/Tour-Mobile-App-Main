package com.example.assignment_gr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {
    private Context context;
    private List<Location> locations;

    public LocationAdapter(Context context, List<Location> locations) {
        super(context, R.layout.list_item_location, locations);
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the layout for the list item
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_location, parent, false);
        }

        // Get the current location
        Location location = locations.get(position);

        // Find the TextView in the list item layout
        TextView locationNameTextView = convertView.findViewById(R.id.location_name);

        // Set the location name to the TextView
        locationNameTextView.setText(location.getName());

        return convertView;
    }
}
