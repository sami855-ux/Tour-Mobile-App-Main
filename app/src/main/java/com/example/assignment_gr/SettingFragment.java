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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private Button btnLogout, btnDelete, btnEdit;
    private TextView username, userEmail, userLocation;
    private dbconnect dbHelper;
    private  SessionManager sessionManager;
    private Integer userId;
    private User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting, container, false);
        btnLogout = view.findViewById(R.id.logout);
        btnDelete = view.findViewById(R.id.deleteaccount);
        btnEdit = view.findViewById(R.id.editprofile);

        username = view.findViewById(R.id.username);
        userEmail = view.findViewById(R.id.userEmail);
        userLocation = view.findViewById(R.id.userLocation);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager = new SessionManager(requireContext());
                sessionManager.logoutUser(); // Clear session

                Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }});

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteUser();
            }
        });

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//        );

        int userId = getLoggedInUserId(requireContext());

        if (userId != -1) { // Check if a valid user ID was found
            dbconnect dbHelper = new dbconnect(requireContext());
            user = dbHelper.getUserById(userId);
        }
        username.setText(String.valueOf(user.getUsername()));
        userEmail.setText(String.valueOf(user.getEmail()));
        return view;
    }

    public int getLoggedInUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // -1 if no user is logged in
    }

    public void deleteUser() {
        int userId = getLoggedInUserId(requireContext()); // Retrieve user ID from SharedPreferences
        if (userId != -1) { // Check if the user is logged in
            dbconnect dbHelper = new dbconnect(requireContext());
            boolean isDeleted = dbHelper.deleteUser(userId);

            if (isDeleted) {
                Toast.makeText(requireContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();

                // clear the SharedPreferences or log out the user
                sessionManager = new SessionManager(requireContext());
                sessionManager.logoutUser(); // Clear session

                Intent intent = new Intent(requireContext(), WelcomeActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(requireContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

}
