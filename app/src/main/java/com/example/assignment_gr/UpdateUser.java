package com.example.assignment_gr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UpdateUser extends AppCompatActivity {
    private Button btnUpdate, btnCancle;
    private EditText username, email, password;
    private User user;
    private int userId;
    private dbconnect dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuser);

        dbHelper = new dbconnect(this);

        btnCancle = findViewById(R.id.cancle);
        btnUpdate = findViewById(R.id.update);
        username = findViewById(R.id.updateusername);
        password = findViewById(R.id.updatepasswordname);
        email = findViewById(R.id.updateemailname);

        userId = getLoggedInUserId(this);

        if (userId != -1) { // Check if a valid user ID was found
            dbconnect dbHelper = new dbconnect(this);
            user = dbHelper.getUserById(userId);
        }

        btnUpdate.setOnClickListener(this::onUpdate);
        btnCancle.setOnClickListener(this::onCancle);
    }

    public void onUpdate(View view) {
        //Update Logic
        String user_value = String.valueOf(username.getText());
        String email_value = String.valueOf(password.getText());
        String password_value = String.valueOf(email.getText());

        if(user_value.isEmpty() || email_value.isEmpty() || password_value.isEmpty()){
            Toast.makeText(UpdateUser.this, "All field are required", Toast.LENGTH_SHORT).show();
        } else {
            if(!isValidEmail(String.valueOf(email.getText()))) {
                Toast.makeText(UpdateUser.this, "Invalid Email", Toast.LENGTH_SHORT).show();
            } else {
                user_value = user_value.trim();
                email_value = email_value.trim();
                password_value = password_value.trim();

                boolean isUpdated = dbHelper.updateUser(userId, user_value, password_value, email_value );

                if (isUpdated) {
                    Toast.makeText(UpdateUser.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    //Go back to setting fragment
                } else {
                    Toast.makeText(UpdateUser.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

        //Back to setting fragment
        finish();
    }
    public void onCancle(View view) {
        //Intent back to setting fragment
        finish();
    }

    public int getLoggedInUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // -1 if no user is logged in
    }
    public boolean isValidEmail(String email) {
            // Regular expression for validating an email
            String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            // Check if the input email matches the pattern
            return email != null && email.matches(emailPattern);
        }
}
