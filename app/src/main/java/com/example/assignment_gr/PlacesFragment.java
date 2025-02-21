package com.example.assignment_gr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class PlacesFragment extends Fragment {

    private String name;
    private double lat;
    private double lon;
    private String rating;
    private String ImageName;
    private String HotelName;
    private String HotelAddress;
    private String MainDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.places, container, false);
        Date currentDate = new Date();
        dbconnect dbHelper = new dbconnect(requireContext());

        // Initialize the TextView
        TextView textView = view.findViewById(R.id.countryName);
        TextView textView1 = view.findViewById(R.id.lat);
        TextView textView2 = view.findViewById(R.id.lon);
        TextView mainDesc = view.findViewById(R.id.mainDesc);
        TextView textViewHotelName = view.findViewById(R.id.textViewHotelName);
        TextView textViewHotelDescription = view.findViewById(R.id.textViewHotelDescription);
        ImageView image = view.findViewById(R.id.imageView1);
        Button book = view.findViewById(R.id.button5);

        // Get the arguments passed to this fragment
        Bundle arguments = getArguments();
        if (arguments != null) {
            name = arguments.getString("Country_name");
            lat = arguments.getDouble("lat", 12.0333); // Default value if not found
            lon = arguments.getDouble("lon", 39.0459); // Default value if not found
            MainDescription = arguments.getString("main", "");
            rating = arguments.getString("rating", "5");
            ImageName = arguments.getString("imageName", "");
            HotelName = arguments.getString("HotelName", "");
            HotelAddress = arguments.getString("HotelAddress", "");

            // For example, display data in a TextView
            textView.setText(name);
            textView1.setText(String.format("%s", lat));
            textView2.setText(String.format("%s", lon));
            mainDesc.setText(MainDescription);
            textViewHotelName.setText(HotelName);
            textViewHotelDescription.setText(HotelAddress);

            try {
                // Get the class field dynamically from R.drawable
                Field field = R.drawable.class.getField(ImageName); // image should be a valid drawable name
                int imageResId = field.getInt(null);

                // Set the image resource correctly
                image.setImageResource(imageResId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                image.setImageResource(R.drawable.axum); // Fallback image if not found
            }

            setRatingToFour(view, Integer.parseInt(rating));
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = getLoggedInUserId(requireContext());
                if(userId == -1) return;  // Checks if userId is null or empty


                boolean isAdded = dbHelper.addHotel(userId, HotelName, HotelAddress);

                if(isAdded){
                    Toast.makeText(requireContext(), "Hotel added successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireContext(), "Failed to add hotel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    public void setRatingToFour(View view, int number) {
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(number); // Set the rating to 4 stars
    }

    public int getLoggedInUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // -1 if no user is logged in
    }
}
