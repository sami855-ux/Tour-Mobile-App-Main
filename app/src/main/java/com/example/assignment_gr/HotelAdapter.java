package com.example.assignment_gr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> hotelLocations;

    // Constructor
    public HotelAdapter(Context context, List<String> hotelLocations) {
        super(context, 0, hotelLocations);
        this.context = context;
        this.hotelLocations = hotelLocations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the hotel location for the current position
        String hotelLocation = hotelLocations.get(position);

        // Check if an existing view is being reused, otherwise inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_location, parent, false);
        }

        // Find the TextView in the list item layout and set the text
        TextView locationNameTextView = convertView.findViewById(R.id.location_name);
        locationNameTextView.setText(hotelLocation);

        return convertView;
    }
}


