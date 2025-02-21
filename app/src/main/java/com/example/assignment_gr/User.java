package com.example.assignment_gr;

import java.util.List;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;

    private List<Hotel> hotels;

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addHotel(String hotelName, String positionName) {
        hotels.add(new Hotel(hotelName, positionName));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean deleteUserById(dbconnect dbHelper, int userId) {
        return dbHelper.deleteUser(userId);
    }

}
