package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    EditText email, pass;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        email = findViewById(R.id.loginemail);
        pass = findViewById(R.id.editTextTextPassword);
        btn = findViewById(R.id.button);

        // Handle login button click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String password = pass.getText().toString();

                // Validate login credentials
                if (validateCredentials(em, password)) {
                    // If credentials are valid, save login status in SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("flag", true);  // Set flag to true when the user logs in
                    editor.apply();

                    // Navigate to the next activity (e.g., HomePage or TodoList)
                    Intent intent = new Intent(login.this, todolist.class);
                    intent.putExtra("Email", em);  // Passing email as extra
                    intent.putExtra("Password", password);  // Passing password as extra
                    startActivity(intent);
                    finish();  // Close the login activity
                } else {
                    // Handle invalid credentials (show an error)
                    email.setError("Invalid credentials");
                }
            }
        });
    }

    // Simple validation function (you can modify it based on your requirements)
    private boolean validateCredentials(String email, String password) {
        // Check if email or password is empty or invalid
        return !email.isEmpty() && !password.isEmpty();
    }
}
