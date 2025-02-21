package com.example.assignment_gr;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private MapView mapView;
    private ListView listView;
    private ArrayList<Location> locations;
    private LocationAdapter adapter;
    private LocationDetailHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity, container, false);

        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        // Initialize ListView
        listView = view.findViewById(R.id.location_list);
        locations = new ArrayList<>();

        // Add locations to the list
        addLocation();

        // Set up the adapter and ListView
        adapter = new LocationAdapter(requireContext(), locations);
        listView.setAdapter(adapter);


        mapView = view.findViewById(R.id.osm_map);
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        // Move map to the first location
        moveMapTo(new GeoPoint(12.0333, 39.0459), "Lalibela");

        // Handle ListView item clicks
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Location selectedLocation = locations.get(position);
            Toast.makeText(requireContext(), "Clicked: " + selectedLocation.getName(), Toast.LENGTH_SHORT).show();

            // Move map to the selected location
            moveMapTo(new GeoPoint(selectedLocation.getLatitude(), selectedLocation.getLongitude()), selectedLocation.getName());

            String countryName = selectedLocation.getName();
            float lat = (float) selectedLocation.getLatitude();
            float lon = (float) selectedLocation.getLongitude();
            String description = selectedLocation.getDescription();
            String rating = selectedLocation.getRating();
            String ImageName = selectedLocation.getImageName();
            String HotelName = selectedLocation.getHotelName();
            String HotelAddress = selectedLocation.getHotelAddress();
            String main = selectedLocation.getMainDescription();

            CheckPlace(countryName, lat, lon, description,rating, ImageName, HotelName, HotelAddress, main);
        });

        return view;
    }

    // Move map to the selected location
    public void moveMapTo(GeoPoint point, String title) {
        if (mapView != null) {
            mapView.getController().animateTo(point);
            mapView.getController().setZoom(15.0);

            // Remove old markers
            List<Overlay> overlays = mapView.getOverlays();
            overlays.removeIf(overlay -> overlay instanceof Marker);

            // Add new marker
            Marker marker = new Marker(mapView);
            marker.setPosition(point);
            marker.setTitle(title);
            mapView.getOverlays().add(marker);

            mapView.invalidate(); // Refresh map
        }
    }

    public void CheckPlace(String countryName, float lat, float lon, String description, String rating, String ImageName, String HotelName
    ,String HotelAddress, String main) {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog);

        TextView title = dialog.findViewById(R.id.title);
        TextView desc = dialog.findViewById(R.id.description);
        Button btnCheck = dialog.findViewById(R.id.button2);
        Button btnCancel = dialog.findViewById(R.id.button);

        title.setText(countryName);
        desc.setText(description);

        btnCheck.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("Country_name", countryName);
            bundle.putDouble("lat", lat);
            bundle.putDouble("lon", lon);
            bundle.putString("rating", rating);
            bundle.putString("imageName", ImageName);
            bundle.putString("HotelName", HotelName);
            bundle.putString("HotelAddress", HotelAddress);
            bundle.putString("main", main);

            // Create the destination fragment and set arguments
            PlacesFragment fragment = new PlacesFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getParentFragmentManager(); // or getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment); // Fragment container ID
            fragmentTransaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
            fragmentTransaction.commit();

            dialog.dismiss();
        });


        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void addLocation() {

        // Add locations to the list with descriptions
        locations.add(new Location(
                "Lalibela",
                12.0333,
                39.0459,
                "Famous for its rock-hewn churches.",
                "The main street of Lalibela.",
                "5",
                "@drawable/addis.png",
                "Lalibela Hotel",
                "Famous for its rock-hewn churches. A UNESCO World Heritage site."
        ));

        locations.add(new Location(
                "Addis Ababa",
                9.03,
                38.74,
                "The capital city of Ethiopia.",
                "Bole area with vibrant nightlife.",
                "4",
                "@drawable/addis.png",
                "Sheraton Addis",
                "The bustling capital with rich culture and history."
        ));

        locations.add(new Location(
                "Gondar",
                12.6070,
                37.4706,
                "Known for its medieval castles.",
                "Historic castles and churches.",
                "4",
                "@drawable/gondar.png",
                "Goha Hotel",
                "Home to the famous castles of the Gondar region."
        ));

        locations.add(new Location(
                "Axum",
                14.1397,
                38.7344,
                "An ancient city known for its obelisks.",
                "Archaeological sites and ancient churches.",
                "5",
                "@drawable/axum.png",
                "Axum Hotel",
                "The ruins of ancient civilization and Christianity."
        ));

        locations.add(new Location(
                "Bahir Dar",
                11.5922,
                37.3833,
                "Situated near Lake Tana, known for its monasteries.",
                "The waterfront of Lake Tana.",
                "4",
                "@drawable/bahir_dar.png",
                "Bahir Dar Hotel",
                "Explore the stunning natural beauty and rich history."
        ));

        locations.add(new Location(
                "Simien Mountains",
                13.2014,
                38.2042,
                "A UNESCO World Heritage site with stunning landscapes.",
                "Treks and views from the mountains.",
                "5",
                "@drawable/simien.png",
                "Simien Lodge",
                "Home to diverse wildlife and breathtaking views."
        ));

        locations.add(new Location(
                "Harar",
                9.3295,
                42.1058,
                "An ancient city with vibrant traditions and mosques.",
                "The old walled city of Harar.",
                "4",
                "@drawable/harar.png",
                "Ras Hotel",
                "Known for its history in Islamic culture."
        ));

        locations.add(new Location(
                "Dessalegn",
                11.5988,
                36.5387,
                "A less-traveled place with unique landscapes.",
                "Natural beauty with few tourists.",
                "3",
                "@drawable/dessalegn.png",
                "Dessalegn Hotel",
                "Explore the hidden gems of Ethiopia."
        ));

        locations.add(new Location(
                "Mekele",
                13.5000,
                39.4700,
                "Capital of the Tigray region, surrounded by mountains.",
                "The historic town with local markets.",
                "4",
                "@drawable/mekele.png",
                "Mekele Hotel",
                "A city rich in culture and history."
        ));

        locations.add(new Location(
                "Omo Valley",
                5.9060,
                36.7322,
                "Famous for its ethnic diversity.",
                "Cultural exhibits from various tribes.",
                "5",
                "@drawable/omo.png",
                "Omo Valley Lodge",
                "Home to diverse tribes and cultures."
        ));

        locations.add(new Location(
                "Konso",
                5.0407,
                37.3900,
                "Known for its terraced farming and unique culture.",
                "Terraces shaped by centuries of farming.",
                "4",
                "@drawable/konso.png",
                "Konso Lodge",
                "Experience the rich agricultural heritage."
        ));

        locations.add(new Location(
                "Lake Tana",
                11.5800,
                37.5170,
                "The largest lake in Ethiopia, rich in biodiversity.",
                "Islands with ancient monasteries.",
                "5",
                "@drawable/lake_tana.png",
                "Tana Hotel",
                "Explore the cultural islands of Lake Tana."
        ));

        locations.add(new Location(
                "Dodola",
                6.9402,
                39.5541,
                "A town near beautiful forests and landscapes.",
                "Forest-covered mountains.",
                "4",
                "@drawable/dodola.png",
                "Dodola Hotel",
                "Great for nature lovers."
        ));

        locations.add(new Location(
                "Jinka",
                5.7901,
                36.6647,
                "Gateway to the Mursi tribes.",
                "Visit local tribes and culture.",
                "4",
                "@drawable/jinka.png",
                "Jinka Resort",
                "Cultural experiences with indigenous tribes."
        ));

        locations.add(new Location(
                "Gambela",
                8.25,
                34.6,
                "Home to the Gambela National Park.",
                "Diverse wildlife in riverine forest.",
                "4",
                "@drawable/gambela.png",
                "Gambela Hotel",
                "Experience rich biodiversity."
        ));

        locations.add(new Location(
                "Bole Bulbula",
                8.8703,
                38.7832,
                "A serene place balancing nature and urban life.",
                "Quiet areas with local shops.",
                "3",
                "@drawable/bole_bulbula.png",
                "Bole Bulbula Hotel",
                "A peaceful suburban experience."
        ));

        locations.add(new Location(
                "Arba Minch",
                6.0569,
                37.4402,
                "Known for its lakes and wildlife.",
                "The setting near Lake Chamo.",
                "5",
                "@drawable/arba_minch.png",
                "Arba Minch Resort",
                "Famous for crocodiles and diverse wildlife."
        ));

        locations.add(new Location(
                "Lalibela",
                12.0333,
                39.0459,
                "Famous for its rock-hewn churches.",
                "The main street of Lalibela.",
                "5",
                "@drawable/addis.png",
                "Lalibela Hotel",
                "Famous for its rock-hewn churches."
        ));

        locations.add(new Location(
                "Nechisar National Park",
                6.0250,
                37.55,
                "Known for unique wildlife and landscapes.",
                "Home to diverse ecosystems.",
                "4",
                "@drawable/nechisar.png",
                "Nechisar Lodge",
                "Experience rare wildlife and beautiful nature."
        ));

        locations.add(new Location(
                "Keren",
                15.7873,
                38.2500,
                "Famous for its impressive landscapes.",
                "Charming city with historical significance.",
                "3",
                "@drawable/keren.png",
                "Keren Hotel",
                "Explore the unique landscapes."
        ));

        locations.add(new Location(
                "Tigray Churches",
                14.1,
                38.2,
                "Famous for rock-hewn churches similar to Lalibela.",
                "Dramatic cliff landscapes.",
                "5",
                "@drawable/tigray_churches.png",
                "Tigray Hotel",
                "Stunning ancient architecture."
        ));

        locations.add(new Location(
                "Sodo",
                6.8705,
                37.3559,
                "Known for coffee production.",
                "The heart of Ethiopia’s coffee culture.",
                "4",
                "@drawable/sodo.png",
                "Sodo Hotel",
                "Deep dive into coffee history and culture."
        ));

        locations.add(new Location(
                "Mursit",
                5.6,
                36.6,
                "The home of the Mursi tribe.",
                "Cultural exchanges and experiences.",
                "4",
                "@drawable/mursit.png",
                "Mursit Lodge",
                "Experience unique tribal traditions."
        ));

        locations.add(new Location(
                "Dallol",
                14.2250,
                40.3028,
                "A surreal landscape of colors and formations.",
                "Unbelievable geological formations.",
                "4",
                "@drawable/dallol.png",
                "Dallol Hotel",
                "A unique otherworldly trip."
        ));

        locations.add(new Location(
                "Woldia",
                12.0184,
                39.5970,
                "Known for its stunning landscapes and historical sites.",
                "Mountainous regions and ancient ruins.",
                "4",
                "@drawable/woldia.png",
                "Woldia Hotel",
                "Blend of history and nature."
        ));

        locations.add(new Location(
                "Tana Kirkos",
                11.5519,
                37.5631,
                "Known for its monasteries on Lake Tana.",
                "Cultural immersion and ancient history.",
                "5",
                "@drawable/tana_kirkos.png",
                "Tana Kirkos Resort",
                "Rich in history and spirituality."
        ));

        locations.add(new Location(
                "Kibish",
                5.30,
                36.73,
                "Known for its rich wildlife and nature.",
                "A natural wonder and paradise.",
                "4",
                "@drawable/kibish.png",
                "Kibish Hotel",
                "Experience untouched wilderness."
        ));

        locations.add(new Location(
                "Ambo",
                8.9822,
                37.8525,
                "Famous for mineral water and scenic views.",
                "Relaxing surroundings and local cafes.",
                "4",
                "@drawable/ambo.png",
                "Ambo Hotel",
                "Known for its carbonated mineral waters."
        ));

        locations.add(new Location(
                "Lalibela",
                12.0333,
                39.0459,
                "Famous for its rock-hewn churches.",
                "The main street of Lalibela.",
                "5",
                "@drawable/addis.png",
                "Lalibela Hotel",
                "Famous for its rock-hewn churches."
        ));
    }


    public void AddPlacesDetail() {

        addDetails("Lalibela", "Amhara", "Famous for its rock-hewn churches.", 5, "Best Hotel", "Amhara Street", "Luxury hotel");
    }

    public void addDetails(String place_name, String address, String description, Integer rating, String hotel_name, String hotel_address, String hotel_description) {
        // Ensure dbHelper is initialized
        // Create a new LocationDetail object
        LocationDetail newLocation = new LocationDetail(
                "Eiffel Tower",
                "France",
                "A wrought iron lattice tower on the Champ de Mars."
        );

        // Save the new location to the database
        if (dbHelper == null) {
            dbHelper = new LocationDetailHelper(requireContext());
        }
        Boolean result = dbHelper.addLocation(newLocation);

        if (result) {
            Toast.makeText(requireContext(), "Location added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to add location.", Toast.LENGTH_SHORT).show();
        }

    }
}
