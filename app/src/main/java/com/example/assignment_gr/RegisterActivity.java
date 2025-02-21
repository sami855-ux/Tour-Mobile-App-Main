package com.example.assignment_gr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText, emailEditText, passwordEditText;
    TextView errormsgTextView;
    CheckBox checkBox;
    dbconnect databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.eTextEmail);
        passwordEditText = findViewById(R.id.password);
        errormsgTextView = findViewById(R.id.textView6);
        checkBox = findViewById(R.id.checkBox);

        Button backToLoginButton = findViewById(R.id.back_to_login);
        Button registerButton = findViewById(R.id.register);

        databaseHelper = new dbconnect(this);

        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {

                    errormsgTextView.setText("All fields are required");
                } else {
                    if(!isValidEmail(email)) {
                        Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(checkBox.isChecked()) {
                            username = username.trim();
                            email = email.trim();
                            password = password.trim();

                            User newUser = new User(username, email, password); // Create User object
                            boolean isInserted = databaseHelper.addUser(newUser); // Insert into database

                            if (isInserted) {
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                // Navigate to LoginActivity
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Checkbox is not checked", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

    }
    public boolean isValidEmail(String email) {
        // Regular expression for validating an email
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Check if the input email matches the pattern
        return email != null && email.matches(emailPattern);
    }
}
