package com.example.assignment_gr;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private dbconnect databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);

        loginBtn = findViewById(R.id.loginBtn);
        emailEditText = findViewById(R.id.eTextEmail);
        passwordEditText = findViewById(R.id.eTextPassword);

        databaseHelper = new dbconnect(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = databaseHelper.authenticateUser(email, password); // Call the authenticateUser method

        if (user != null) {
            // Successfully authenticated
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            // Proceed to next activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Replace HomeActivity with your main activity
            startActivity(intent);
            finish(); // Close the LoginActivity
        } else {
            // Authentication failed
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

}
