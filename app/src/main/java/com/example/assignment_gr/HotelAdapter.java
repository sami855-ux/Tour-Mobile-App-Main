package com.example.assignment_gr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<Hotel> {
    private Context context;
    private List<Hotel> hotels;
    private LayoutInflater inflater;

    // Constructor
    public HotelAdapter(Context context, List<Hotel> hotels) {
        super(context, 0, hotels);
        this.context = context;
        this.hotels = hotels;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reuse view if possible
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_hotel, parent, false);
        }

        // Get the current hotel item
        Hotel hotel = hotels.get(position);

        // Find TextViews
        TextView hotelNameText = convertView.findViewById(R.id.hotelName);
        TextView positionNameText = convertView.findViewById(R.id.positionName);

        // Set data
        hotelNameText.setText(hotel.getHotelName());
        positionNameText.setText(hotel.getPositionName());

        return convertView;
    }
}
