package com.example.assignment_gr;

public class Location {
    private String name;
    private double latitude;
    private double longitude;
    private String description;
    private String rating;
    private String ImageName;
    private String HotelName;
    private String HotelAddress;
    private String MainDescription;

    public Location(String name, double latitude, double longitude, String description, String hotelAddress, String rating,
                    String ImageNmame
    ,String HotelName, String MainDescription) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.HotelAddress = hotelAddress;
        this.rating = rating;
        this.ImageName = ImageNmame;
        this.HotelName = HotelName;
        this.MainDescription = MainDescription;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getImageName() {
        return ImageName;
    }

    public String getHotelName() {
        return HotelName;
    }

    public String getHotelAddress() {
        return HotelAddress;
    }

    public String getMainDescription() {
        return MainDescription;
    }
}
