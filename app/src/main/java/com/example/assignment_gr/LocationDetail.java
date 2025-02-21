package com.example.assignment_gr;

public class LocationDetail {

    private int id;
    private String placename;
    private String placecountry;
    private String placedesc;


    public LocationDetail(int id, String placename, String placecountry, String placedesc) {
        this.id = id;
        this.placename = placename;
        this.placecountry = placecountry;
        this.placedesc = placedesc;
    }

    public LocationDetail(String placename, String placecountry, String placedesc) {
        this.placename = placename;
        this.placecountry = placecountry;
        this.placedesc = placedesc;
    }

    public String getPlacename() {
        return placename;
    }

    public void setUsername(String placename) {
        this.placename = placename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacecountry() {
        return placecountry;
    }

    public void setPlacecountry(String placecountry) {
        this.placecountry = placecountry;
    }

    public String getPlacedesc() {
        return placedesc;
    }

    public void getPlacedesc(String placedesc) {
        this.placedesc = placedesc;
    }
    public boolean deleteUserById(dbconnect dbHelper, int userId) {
        return dbHelper.deleteUser(userId);
    }

}
