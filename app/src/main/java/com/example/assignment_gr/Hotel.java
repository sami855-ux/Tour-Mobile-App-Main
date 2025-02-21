package com.example.assignment_gr;

public class Hotel {
    private String hotelName;
    private String positionName;

    // Constructor to initialize both fields
    public Hotel(String hotelName, String positionName) {
        this.hotelName = hotelName;
        this.positionName = positionName;
    }

    // Getter and setter methods for hotelName
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    // Getter and setter methods for positionName
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    // Override toString() to display hotel and position
    @Override
    public String toString() {
        return "Hotel: " + hotelName + ", Position: " + positionName;
    }
}
