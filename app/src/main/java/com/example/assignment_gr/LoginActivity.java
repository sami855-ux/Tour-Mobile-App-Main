package com.example.assignment_gr;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private SessionManager sessionManager;
    public Integer userId;
    public  String userEmail;
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

        if(!isValidEmail(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = databaseHelper.authenticateUser(email, password); // Call the authenticateUser method


        if (user != null) {
            // Successfully authenticated
            sessionManager = new SessionManager(this);
            userId = user.getId();
            userEmail = email;
            sessionManager.createLoginSession(userEmail);

            SharedPreferences sharedPreferences = this.getSharedPreferences("UserSession", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", userId); // Store the user ID
            editor.apply();


            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            // Proceed to next activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the LoginActivity

            // Clear the EditText fields
            emailEditText.setText("");
            passwordEditText.setText("");
        } else {
            // Authentication failed
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidEmail(String email) {
        // Regular expression for validating an email
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Check if the input email matches the pattern
        return email != null && email.matches(emailPattern);
    }

}
