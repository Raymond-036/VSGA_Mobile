package com.raymondsimamora.myfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView textViewRegister = findViewById(R.id.textViewRegister);

        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isValidUser(email, password)) {
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                // Navigate to MainActivity upon successful login
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close Login activity
                Intent splashIntent = new Intent(Login.this, Splash.class);
                startActivity(splashIntent);
                finish(); // Close Login activity
            } else {
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        textViewRegister.setOnClickListener(v -> {
            // Navigate to RegisterActivity when "Register" is clicked
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private boolean isValidUser(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                DatabaseHelper.COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }
}
